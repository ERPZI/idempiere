/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2012 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for ZI_HedgeContract
 *  @author iDempiere (generated) 
 *  @version Release 7.1 - $Id$ */
public class X_ZI_HedgeContract extends PO implements I_ZI_HedgeContract, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20200928L;

    /** Standard Constructor */
    public X_ZI_HedgeContract (Properties ctx, int ZI_HedgeContract_ID, String trxName)
    {
      super (ctx, ZI_HedgeContract_ID, trxName);
      /** if (ZI_HedgeContract_ID == 0)
        {
			setC_BankAccount_ID (0);
			setC_Bank_ID (0);
			setC_Currency_ID (0);
			setC_Currency_ID_To (0);
			setDateOrdered (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDatePromised (new Timestamp( System.currentTimeMillis() ));
			setMultiplyRate (Env.ZERO);
			setPayAmt (Env.ZERO);
			setPOReference (null);
			setTrxAmt (Env.ZERO);
			setZI_HedgeContract_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZI_HedgeContract (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_ZI_HedgeContract[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
    {
		return (org.compiere.model.I_AD_User)MTable.get(getCtx(), org.compiere.model.I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_BankAccount getC_BankAccount() throws RuntimeException
    {
		return (org.compiere.model.I_C_BankAccount)MTable.get(getCtx(), org.compiere.model.I_C_BankAccount.Table_Name)
			.getPO(getC_BankAccount_ID(), get_TrxName());	}

	/** Set Bank Account.
		@param C_BankAccount_ID 
		Account at the Bank
	  */
	public void setC_BankAccount_ID (int C_BankAccount_ID)
	{
		if (C_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankAccount_ID, Integer.valueOf(C_BankAccount_ID));
	}

	/** Get Bank Account.
		@return Account at the Bank
	  */
	public int getC_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Bank getC_Bank() throws RuntimeException
    {
		return (org.compiere.model.I_C_Bank)MTable.get(getCtx(), org.compiere.model.I_C_Bank.Table_Name)
			.getPO(getC_Bank_ID(), get_TrxName());	}

	/** Set Bank.
		@param C_Bank_ID 
		Bank
	  */
	public void setC_Bank_ID (int C_Bank_ID)
	{
		if (C_Bank_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Bank_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
	}

	/** Get Bank.
		@return Bank
	  */
	public int getC_Bank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Bank_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Currency getC_Currency_To() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID_To(), get_TrxName());	}

	/** Set Currency To.
		@param C_Currency_ID_To 
		Target currency
	  */
	public void setC_Currency_ID_To (int C_Currency_ID_To)
	{
		set_ValueNoCheck (COLUMNNAME_C_Currency_ID_To, Integer.valueOf(C_Currency_ID_To));
	}

	/** Get Currency To.
		@return Target currency
	  */
	public int getC_Currency_ID_To () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID_To);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Date Ordered.
		@param DateOrdered 
		Date of Order
	  */
	public void setDateOrdered (Timestamp DateOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Date Ordered.
		@return Date of Order
	  */
	public Timestamp getDateOrdered () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	/** Set Date Promised.
		@param DatePromised 
		Date Order was promised
	  */
	public void setDatePromised (Timestamp DatePromised)
	{
		set_ValueNoCheck (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Date Promised.
		@return Date Order was promised
	  */
	public Timestamp getDatePromised () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set Group1.
		@param Group1 Group1	  */
	public void setGroup1 (String Group1)
	{
		set_Value (COLUMNNAME_Group1, Group1);
	}

	/** Get Group1.
		@return Group1	  */
	public String getGroup1 () 
	{
		return (String)get_Value(COLUMNNAME_Group1);
	}

	/** Set Sold.
		@param IsSold 
		Organization sells this product
	  */
	public void setIsSold (boolean IsSold)
	{
		set_ValueNoCheck (COLUMNNAME_IsSold, Boolean.valueOf(IsSold));
	}

	/** Get Sold.
		@return Organization sells this product
	  */
	public boolean isSold () 
	{
		Object oo = get_Value(COLUMNNAME_IsSold);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Multiply Rate.
		@param MultiplyRate 
		Rate to multiple the source by to calculate the target.
	  */
	public void setMultiplyRate (BigDecimal MultiplyRate)
	{
		set_ValueNoCheck (COLUMNNAME_MultiplyRate, MultiplyRate);
	}

	/** Get Multiply Rate.
		@return Rate to multiple the source by to calculate the target.
	  */
	public BigDecimal getMultiplyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MultiplyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Payment amount.
		@param PayAmt 
		Amount being paid
	  */
	public void setPayAmt (BigDecimal PayAmt)
	{
		set_ValueNoCheck (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Payment amount.
		@return Amount being paid
	  */
	public BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Order Reference.
		@param POReference 
		Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public void setPOReference (String POReference)
	{
		set_ValueNoCheck (COLUMNNAME_POReference, POReference);
	}

	/** Get Order Reference.
		@return Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public String getPOReference () 
	{
		return (String)get_Value(COLUMNNAME_POReference);
	}

	/** Set Transaction Amount.
		@param TrxAmt 
		Amount of a transaction
	  */
	public void setTrxAmt (BigDecimal TrxAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TrxAmt, TrxAmt);
	}

	/** Get Transaction Amount.
		@return Amount of a transaction
	  */
	public BigDecimal getTrxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TrxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ZI_HedgeContract.
		@param ZI_HedgeContract_ID ZI_HedgeContract	  */
	public void setZI_HedgeContract_ID (int ZI_HedgeContract_ID)
	{
		if (ZI_HedgeContract_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ZI_HedgeContract_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ZI_HedgeContract_ID, Integer.valueOf(ZI_HedgeContract_ID));
	}

	/** Get ZI_HedgeContract.
		@return ZI_HedgeContract	  */
	public int getZI_HedgeContract_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZI_HedgeContract_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZI_HedgeContract_UU.
		@param ZI_HedgeContract_UU ZI_HedgeContract_UU	  */
	public void setZI_HedgeContract_UU (String ZI_HedgeContract_UU)
	{
		set_Value (COLUMNNAME_ZI_HedgeContract_UU, ZI_HedgeContract_UU);
	}

	/** Get ZI_HedgeContract_UU.
		@return ZI_HedgeContract_UU	  */
	public String getZI_HedgeContract_UU () 
	{
		return (String)get_Value(COLUMNNAME_ZI_HedgeContract_UU);
	}
}