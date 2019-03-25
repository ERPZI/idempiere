/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.compiere.model.MPaySelection;
import org.compiere.model.MPaySelectionLine;
import org.compiere.model.X_C_Order;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 *	Create Payment Selection Lines from AP Invoices
 *	
 *  @author Jorg Janke
 *  @version $Id: PaySelectionCreateFrom.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class PaySelectionCreateFrom extends SvrProcess
{
	/**	Only When Discount			*/
	private boolean 	p_OnlyDiscount = false;
	/** Only when Due				*/
	private boolean		p_OnlyDue = false;
	/** Include Disputed			*/
	private boolean		p_IncludeInDispute = false;
	/** Match Requirement			*/
	private String		p_MatchRequirement = "N";
	/** Payment Rule				*/
	private String		p_PaymentRule = null;
	/** BPartner					*/
	private int			p_C_BPartner_ID = 0;
	/** BPartner Group				*/
	private int			p_C_BP_Group_ID = 0;
	/**	Payment Selection			*/
	private int			p_C_PaySelection_ID = 0;

	private Timestamp p_DueDate = null;
	
	//MPo,31/10/2016 Add PrCtr to payment selection
	//MPo,31/10/2016 Add WHT exclusion parameter (automatic payment currently does not support WHT deduction
	private int p_User1_ID = 0;
	private boolean p_IncludeWHT = false;
	//
	//MPo,15/11/17 Add Organization for selection
	private int p_AD_Org_ID = 0;
	//
	//MPo, 18/11/17 Add Currency for selection
	private int p_C_Currency_ID = 0;
	//
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("OnlyDiscount"))
				p_OnlyDiscount = "Y".equals(para[i].getParameter());
			else if (name.equals("OnlyDue"))
				p_OnlyDue = "Y".equals(para[i].getParameter());
			else if (name.equals("IncludeInDispute"))
				p_IncludeInDispute = "Y".equals(para[i].getParameter());
			else if (name.equals("MatchRequirement"))
				p_MatchRequirement = (String)para[i].getParameter();
			else if (name.equals("PaymentRule"))
				p_PaymentRule = (String)para[i].getParameter();
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("C_BP_Group_ID"))
				p_C_BP_Group_ID = para[i].getParameterAsInt();
			else if (name.equals("DueDate"))
				p_DueDate = (Timestamp) para[i].getParameter();
			//MPo, Add PrCtr and Include WHT
			else if (name.equals("ZI_IncludeWHT"))
				p_IncludeWHT = "Y".equals(para[i].getParameter());
			else if (name.equals("User1_ID"))
				p_User1_ID = para[i].getParameterAsInt();
			//
			//MPo, 18/11/17 Add Currency
			else if (name.equals("C_Currency_ID"))
				p_C_Currency_ID = para[i].getParameterAsInt();
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = para[i].getParameterAsInt();
			//
			
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_C_PaySelection_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		if (log.isLoggable(Level.INFO)) log.info ("C_PaySelection_ID=" + p_C_PaySelection_ID
			+ ", OnlyDiscount=" + p_OnlyDiscount + ", OnlyDue=" + p_OnlyDue
			+ ", IncludeInDispute=" + p_IncludeInDispute
			+ ", MatchRequirement=" + p_MatchRequirement
			+ ", PaymentRule=" + p_PaymentRule
			+ ", C_BP_Group_ID=" + p_C_BP_Group_ID + ", C_BPartner_ID=" + p_C_BPartner_ID);
		
		MPaySelection psel = new MPaySelection (getCtx(), p_C_PaySelection_ID, get_TrxName());
		if (psel.get_ID() == 0)
			throw new IllegalArgumentException("Not found C_PaySelection_ID=" + p_C_PaySelection_ID);
		if (psel.isProcessed())
			throw new IllegalArgumentException("@Processed@");

		if ( p_DueDate == null )
			p_DueDate = psel.getPayDate();
		
		//MPo, 15/11/17
		//p_AD_Org_ID = psel.getAD_Org_ID();
		//
		
	//	psel.getPayDate();

		StringBuilder sql = new StringBuilder("SELECT C_Invoice_ID,") // 1
			//	Open
			.append(" currencyConvert(invoiceOpen(i.C_Invoice_ID, i.C_InvoicePaySchedule_ID)")
				.append(",i.C_Currency_ID, ?,?, i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID) AS PayAmt,")	//	2 ##p1/p2 Currency_To,PayDate
			//	Discount
			.append(" currencyConvert(invoiceDiscount(i.C_Invoice_ID,?,i.C_InvoicePaySchedule_ID)")	//	##p3 PayDate
				.append(",i.C_Currency_ID, ?,?,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID) AS DiscountAmt,")	//	3 ##p4/p5 Currency_To,PayDate
			.append(" PaymentRule, IsSOTrx, ") // 4..5
			.append(" currencyConvert(invoiceWriteOff(i.C_Invoice_ID) ")
			    .append(",i.C_Currency_ID, ?,?,i.C_ConversionType_ID,i.AD_Client_ID,i.AD_Org_ID) AS WriteOffAmt ")	//	6 ##p6/p7 Currency_To,PayDate
			//MPo,01/11/2016 Add PrCtr
			.append(", User1_ID ") // 7
			//MPo,17/10/18 Add Remit-To BP and Remit-To Location
			.append(", ZI_Pay_BPartner_ID, ZI_Pay_Location_ID " ) // p8/p9 Remit-To BP, Remit-To Location
			//
			.append("FROM C_Invoice_v i WHERE ");
		if (X_C_Order.PAYMENTRULE_DirectDebit.equals(p_PaymentRule))
			sql.append("IsSOTrx='Y'");
		else
			sql.append("IsSOTrx='N'");
		sql.append(" AND IsPaid='N' AND DocStatus IN ('CO','CL')")
			.append(" AND AD_Client_ID=?")				//	##p8
			//	Existing Payments - Will reselect Invoice if prepared but not paid 
			.append(" AND NOT EXISTS (SELECT * FROM C_PaySelectionLine psl")
						.append(" INNER JOIN C_PaySelectionCheck psc ON (psl.C_PaySelectionCheck_ID=psc.C_PaySelectionCheck_ID)")
						.append(" LEFT OUTER JOIN C_Payment pmt ON (pmt.C_Payment_ID=psc.C_Payment_ID)")
						.append(" WHERE i.C_Invoice_ID=psl.C_Invoice_ID AND psl.IsActive='Y'")
						.append(" AND (pmt.DocStatus IS NULL OR pmt.DocStatus NOT IN ('VO','RE')) )")
			//	Don't generate again invoices already on this payment selection 
			.append(" AND i.C_Invoice_ID NOT IN (SELECT i.C_Invoice_ID FROM C_PaySelectionLine psl WHERE psl.C_PaySelection_ID=?)"); //	##p9
		//	Disputed
		if (!p_IncludeInDispute)
			sql.append(" AND i.IsInDispute='N'");
		//	PaymentRule (optional)
		if (p_PaymentRule != null)
			sql.append(" AND PaymentRule=?");		//	##
		//	OnlyDiscount
		if (p_OnlyDiscount)
		{
			if (p_OnlyDue)
				sql.append(" AND (");
			else
				sql.append(" AND ");
			sql.append("invoiceDiscount(i.C_Invoice_ID,?,i.C_InvoicePaySchedule_ID) > 0");	//	##
		}
		//	OnlyDue
		if (p_OnlyDue)
		{
			if (p_OnlyDiscount)
				sql.append(" OR ");
			else
				sql.append(" AND ");
			// sql.append("paymentTermDueDays(C_PaymentTerm_ID, DateInvoiced, ?) >= 0");	//	##
			sql.append("i.DueDate<=?");	//	##
			if (p_OnlyDiscount)
				sql.append(")");
		}
		//	Business Partner
		if (p_C_BPartner_ID != 0)
			sql.append(" AND C_BPartner_ID=?");	//	##
		//	Business Partner Group
		else if (p_C_BP_Group_ID != 0)
			sql.append(" AND EXISTS (SELECT * FROM C_BPartner bp ")
				.append("WHERE bp.C_BPartner_ID=i.C_BPartner_ID AND bp.C_BP_Group_ID=?)");	//	##
		//	PO Matching Requirement
		if (p_MatchRequirement.equals("P") || p_MatchRequirement.equals("B"))
		{
			sql.append(" AND EXISTS (SELECT * FROM C_InvoiceLine il ")
				.append("WHERE i.C_Invoice_ID=il.C_Invoice_ID")
				.append(" AND QtyInvoiced=(SELECT SUM(Qty) FROM M_MatchPO m ")
					.append("WHERE il.C_InvoiceLine_ID=m.C_InvoiceLine_ID))");
		}
		//	Receipt Matching Requirement
		if (p_MatchRequirement.equals("R") || p_MatchRequirement.equals("B"))
		{
			sql.append(" AND EXISTS (SELECT * FROM C_InvoiceLine il ")
				.append("WHERE i.C_Invoice_ID=il.C_Invoice_ID")
				.append(" AND QtyInvoiced=(SELECT SUM(Qty) FROM M_MatchInv m ")
					.append("WHERE il.C_InvoiceLine_ID=m.C_InvoiceLine_ID))");
		}
		// MPo,31/10/2016 PrCtr and IncludeWHT
		if (p_User1_ID != 0)
			sql.append(" AND User1_ID=?");
		if (!p_IncludeWHT)
			sql.append(" AND i.c_invoice_id NOT IN "
					+ "(SELECT c_invoice_id FROM LCO_InvoiceWithholding "
					+ "WHERE c_invoice_id = i.c_invoice_id)");
		
		//
		// MPo, 15/11/17 Only select invoices from payment selection header organization
		//if (p_AD_Org_ID != 0)
		//	sql.append(" AND AD_Org_ID=?");
		//
		// MPo, 18/11/17 Select invoices by Currency
		if (p_C_Currency_ID != 0)
			sql.append(" AND C_Currency_ID=?");
		if (p_AD_Org_ID != 0)
			sql.append(" AND AD_Org_ID=?");
		//
		int lines = 0;
		int C_CurrencyTo_ID = psel.getC_Currency_ID();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			int index = 1;
			pstmt.setInt (index++, C_CurrencyTo_ID);
			pstmt.setTimestamp(index++, psel.getPayDate());
			//
			pstmt.setTimestamp(index++, psel.getPayDate());
			pstmt.setInt (index++, C_CurrencyTo_ID);
			pstmt.setTimestamp(index++, psel.getPayDate());
			pstmt.setInt (index++, C_CurrencyTo_ID);
			pstmt.setTimestamp(index++, psel.getPayDate());
			//
			pstmt.setInt(index++, psel.getAD_Client_ID());
			pstmt.setInt(index++, p_C_PaySelection_ID);
			if (p_PaymentRule != null)
				pstmt.setString(index++, p_PaymentRule);
			if (p_OnlyDiscount)
				pstmt.setTimestamp(index++, psel.getPayDate());
			if (p_OnlyDue)
				pstmt.setTimestamp(index++, p_DueDate);
			if (p_C_BPartner_ID != 0)
				pstmt.setInt (index++, p_C_BPartner_ID);
			else if (p_C_BP_Group_ID != 0)
				pstmt.setInt (index++, p_C_BP_Group_ID);
			//if (p_C_BP_Group_ID != 0)
			//	pstmt.setInt (index++, p_C_BP_Group_ID);
			//MPo,31/10/2016 Add PrCtr selection
			if (p_User1_ID != 0)
				pstmt.setInt (index++, p_User1_ID);
			//
			//MPo,15/11/17 Add Organization
			//if (p_AD_Org_ID != 0)
			//	pstmt.setInt(index++, p_AD_Org_ID);
			//
			//MPo,18/11/18 Add Currency and Organization
			if (p_C_Currency_ID != 0)
				pstmt.setInt(index++, p_C_Currency_ID);
			if (p_AD_Org_ID != 0)
				pstmt.setInt(index++, p_AD_Org_ID);
			//
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				int C_Invoice_ID = rs.getInt(1);
				BigDecimal PayAmt = rs.getBigDecimal(2);
				if (C_Invoice_ID == 0 || Env.ZERO.compareTo(PayAmt) == 0)
					continue;
				BigDecimal DiscountAmt = rs.getBigDecimal(3);
				BigDecimal WriteOffAmt = rs.getBigDecimal(6);
				String PaymentRule  = rs.getString(4);
				boolean isSOTrx = "Y".equals(rs.getString(5));
				//MPo,01/11/2016 Add PrCtr
				//int User1_ID = rs.getInt(6);
				//MPo,10/1/17 Resolve merge conflict with BigDecimal WriteOffAmt = rs.getBigDecimal(6);
				int User1_ID = rs.getInt(7);
				//
				//MPo,17/10/18 Add Remit-To BP and Remit-To Location
				int ZI_Pay_BPartner_ID = rs.getInt(8);
				int ZI_Pay_Location_ID = rs.getInt(9);
				lines++;
				MPaySelectionLine pselLine = new MPaySelectionLine (psel, lines*10, PaymentRule);
				pselLine.setInvoice (C_Invoice_ID, isSOTrx,
					PayAmt, PayAmt.subtract(DiscountAmt).subtract(WriteOffAmt), DiscountAmt, WriteOffAmt);
					//MPo,01/11/2016 Add PrCtr
					pselLine.setUser1_ID(User1_ID);
					//
					//MPo,17/10/18 Add Remit-To BP and Remit-To Location
					pselLine.setZI_Pay_BPartner_ID(ZI_Pay_BPartner_ID);
					pselLine.setZI_Pay_Location_ID(ZI_Pay_Location_ID);
					//
				if (!pselLine.save())
				{
					throw new IllegalStateException ("Cannot save MPaySelectionLine");
				}
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		StringBuilder msgreturn = new StringBuilder("@C_PaySelectionLine_ID@  - #").append(lines);
		return msgreturn.toString();
	}	//	doIt

}	//	PaySelectionCreateFrom
