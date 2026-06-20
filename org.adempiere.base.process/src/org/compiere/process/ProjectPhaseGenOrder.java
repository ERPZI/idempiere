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


import java.util.logging.Level;

import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProcessPara;
import org.compiere.model.MProduct;
import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;
import org.compiere.model.MProjectPhase;
import org.compiere.model.MProjectTask;
import org.compiere.util.Env;


/**
 *  Generate Order from Project Phase
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectPhaseGenOrder.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
@org.adempiere.base.annotation.Process
public class ProjectPhaseGenOrder  extends SvrProcess
{
	private int		m_C_ProjectPhase_ID = 0;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			if (para[i].getParameter() == null)
				;
			else
				MProcessPara.validateUnknownParameter(getProcessInfo().getAD_Process_ID(), para[i]);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		m_C_ProjectPhase_ID = getRecord_ID();
		if (log.isLoggable(Level.INFO)) log.info("doIt - C_ProjectPhase_ID=" + m_C_ProjectPhase_ID);
		if (m_C_ProjectPhase_ID == 0)
			throw new IllegalArgumentException("C_ProjectPhase_ID == 0");
		MProjectPhase fromPhase = new MProjectPhase (getCtx(), m_C_ProjectPhase_ID, get_TrxName());
		MProject fromProject = ProjectGenOrder.getProject (getCtx(), fromPhase.getC_Project_ID(), get_TrxName());
		//MPo, 8/6/2026 Warehouse order allows separate billing to change document type, roll-up 
		//MOrder order = new MOrder (fromProject, true, MOrder.DocSubTypeSO_OnCredit);
		MOrder order = new MOrder (fromProject, true, MOrder.DocSubTypeSO_Warehouse);
		//
		order.setDescription(order.getDescription() + " - " + fromPhase.getName());
		//MPo, 7/5/2026 PrCtr mandatory in sales order
		if (fromProject.getUser1_ID() != 0)
			order.setUser1_ID(fromProject.getUser1_ID()); 
		if (fromProject.getZI_Branch_ID() != 0)
			order.setZI_Branch_ID(fromProject.getZI_Branch_ID());
		//
		
		if (!order.save())
			throw new Exception("Could not create Order");

		//	Create an order on Phase Level
		if (fromPhase.getM_Product_ID() != 0)
		{
			MOrderLine ol = new MOrderLine(order);
			ol.setLine(fromPhase.getSeqNo());
			StringBuilder sb = new StringBuilder ().append(fromPhase.getName());
			if (fromPhase.getDescription() != null && fromPhase.getDescription().length() > 0)
				sb.append(" - ").append(fromPhase.getDescription());
			ol.setDescription(sb.toString());
			//
			ol.setM_Product_ID(fromPhase.getM_Product_ID(), true);
			ol.setQty(fromPhase.getQty());
			ol.setPrice();
			if (fromPhase.getPriceActual() != null && fromPhase.getPriceActual().compareTo(Env.ZERO) != 0)
				ol.setPrice(fromPhase.getPriceActual());
			ol.setTax();
			//MPo, 6/5/2026 PrCtr mandatory in sales order line
			if (fromPhase.getC_Project().getUser1_ID() != 0) 
				ol.setUser1_ID(fromPhase.getC_Project().getUser1_ID()); //Save as PrCtr mandatory in C_Project
			//
			if (!ol.save())
				log.log(Level.SEVERE, "doIt - Lines not generated");
			else {
				//MPo, 14/5/2026 Update Phase
				fromPhase.setC_Order_ID(order.getC_Order_ID());
				fromPhase.saveEx();
			}	
			StringBuilder msgreturn = new StringBuilder("@C_Order_ID@ ").append(order.getDocumentNo()).append(" (1)");
			return msgreturn.toString();
		}
		
		//	Project Phase Lines
		int count = 0;
		MProjectLine[] lines = fromPhase.getLines();
		for (int i = 0; i < lines.length; i++)
		{
			//MPo, 14/5/2026 Order already created for phase line?
			if (lines[i].getC_Order_ID() != 0) continue;
			//MPo, 8/6/2026 Skip product not sold
			MProduct product  = new MProduct(getCtx(), lines[i].getM_Product_ID(), get_TrxName());
			if (!product.isSold()) continue;
			//	
			MOrderLine ol = new MOrderLine(order);
			ol.setLine(lines[i].getLine());
			ol.setDescription(lines[i].getDescription());
			//
			ol.setM_Product_ID(lines[i].getM_Product_ID(), true);
			ol.setQty(lines[i].getPlannedQty().subtract(lines[i].getInvoicedQty()));
			ol.setPrice();
			if (lines[i].getPlannedPrice() != null && lines[i].getPlannedPrice().compareTo(Env.ZERO) != 0)
				ol.setPrice(lines[i].getPlannedPrice());
			ol.setDiscount();
			ol.setTax();
			//MPo, 6/5/2026 Add PrCtr in Order Line
			if (lines[i].getC_Project().getUser1_ID() != 0)
				ol.setUser1_ID(lines[i].getC_Project().getUser1_ID()); //Save as PrCtr mandatory in C_Project
			//
			if (ol.save())
				count++;
			//MPo, 14/5/2026 Update Phase Line
			lines[i].setC_Order_ID(order.getC_Order_ID());
			lines[i].saveEx();
		}	//	for all lines
		if (lines.length != count)
			log.log(Level.SEVERE, "Lines difference - ProjectLines=" + lines.length + " <> Saved=" + count);

		//	Project Tasks
		MProjectTask[] tasks = fromPhase.getTasks ();
		for (int i = 0; i < tasks.length; i++)
		{
			MOrderLine ol = new MOrderLine(order);
			ol.setLine(tasks[i].getSeqNo());
			StringBuilder sb = new StringBuilder ().append(tasks[i].getName());
			if (tasks[i].getDescription() != null && tasks[i].getDescription().length() > 0)
				sb.append(" - ").append(tasks[i].getDescription());
			ol.setDescription(sb.toString());
			//
			ol.setM_Product_ID(tasks[i].getM_Product_ID(), true);
			ol.setQty(tasks[i].getQty());
			ol.setPrice();
			ol.setTax();
			if (ol.save())
				count++;
		}	//	for all lines
		if (tasks.length != count - lines.length)
			log.log(Level.SEVERE, "doIt - Lines difference - ProjectTasks=" + tasks.length + " <> Saved=" + count);

		StringBuilder msgreturn = new StringBuilder("@C_Order_ID@ ").append(order.getDocumentNo()).append(" (").append(count).append(")");
		return msgreturn.toString();
	}	//	doIt

}	//	ProjectPhaseGenOrder
