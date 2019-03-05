/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.apps.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.logging.Level;
//MPo, 4/8/2016
//import org.adempiere.webui.editor.WTableDirEditor;
import org.compiere.grid.ed.VComboBox;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MOrder;
import org.compiere.model.MRMA;
import org.compiere.swing.CLabel;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

/**
 * Generate Invoice (manual) view class
 * 
 */
public class VInvoiceGen extends InvoiceGen implements FormPanel, ActionListener, VetoableChangeListener
{
	private VGenPanel panel;
	
	/**	Window No			*/
	private int         	m_WindowNo = 0;
	/**	FormFrame			*/
	private FormFrame 		m_frame;

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(VInvoiceGen.class);
	//

	private CLabel lOrg = new CLabel();
	private VLookup fOrg;
	//MPo, 2/8/2016 Add warehouse for selection
	private CLabel lWarehouse = new CLabel();
	private VLookup fWarehouse;
	//MPo, 3/8/2016 Add Branch for selection
	private CLabel lBranch = new CLabel();
	private VLookup fBranch;
	//MPo, 4/8/2016 Add Invoicing document type for selection
	private CLabel lDT = new CLabel();
	private VLookup fDT;
	//
	private CLabel lBPartner = new CLabel();
	private VLookup fBPartner;	
	private CLabel     lDocType = new CLabel();
	private VComboBox  cmbDocType = new VComboBox();
	private CLabel     lDocAction = new CLabel();
	private VLookup    docAction;
	
	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame frame
	 */
	public void init (int WindowNo, FormFrame frame)
	{
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		Env.setContext(Env.getCtx(), m_WindowNo, "IsSOTrx", "Y");

		panel = new VGenPanel(this, WindowNo, frame);

		try
		{
			super.dynInit();
			dynInit();
			jbInit();
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, "init", ex);
		}
	}	//	init
	
	/**
	 * 	Dispose
	 */
	public void dispose()
	{
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	}	//	dispose
	
	/**
	 *	Static Init.
	 *  <pre>
	 *  selPanel (tabbed)
	 *      fOrg, fBPartner
	 *      scrollPane & miniTable
	 *  genPanel
	 *      info
	 *  </pre>
	 *  @throws Exception
	 */
	void jbInit() throws Exception
	{
		lOrg.setLabelFor(fOrg);
		lOrg.setText(Msg.translate(Env.getCtx(), "AD_Org_ID"));
		//MPo, 2/8/2016 Add Warehouse
		lWarehouse.setLabelFor(fWarehouse);
		lWarehouse.setText(Msg.translate(Env.getCtx(), "M_Warehouse_ID"));
		//MPo, 3/8/2016 Add Branch
		lBranch.setLabelFor(fBranch);
		lBranch.setText(Msg.translate(Env.getCtx(), "ZI_Branch_ID"));
		//MPo, 4/8/2016 Add Document Type
		lDT.setLabelFor(fDT);
		lDT.setText(Msg.translate(Env.getCtx(), "C_DocType_ID"));
		//
		lBPartner.setLabelFor(fBPartner);
		lBPartner.setText(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		lDocAction.setLabelFor(docAction);
		lDocAction.setText(Msg.translate(Env.getCtx(), "DocAction"));
		lDocType.setLabelFor(cmbDocType);
		
		panel.getParameterPanel().add(lOrg, null);
		panel.getParameterPanel().add(fOrg, null);
		//MPo, 2/8/2016
		panel.getParameterPanel().add(lWarehouse, null);
		panel.getParameterPanel().add(fWarehouse, null);
		//MPo, 3/8/2016
		panel.getParameterPanel().add(lBranch, null);
		panel.getParameterPanel().add(fBranch, null);
		//MPo, 4/8/2016
		panel.getParameterPanel().add(lDT, null);
		panel.getParameterPanel().add(fDT, null);
		//
		panel.getParameterPanel().add(lBPartner, null);
		panel.getParameterPanel().add(fBPartner, null);
		panel.getParameterPanel().add(lDocType, null);
		panel.getParameterPanel().add(cmbDocType, null);
		panel.getParameterPanel().add(lDocAction, null);
		panel.getParameterPanel().add(docAction, null);
	}	//	jbInit
	
	/**
	 *	Fill Picks.
	 *		Column_ID from C_Order
	 *  @throws Exception if Lookups cannot be initialized
	 */
	public void dynInit() throws Exception
	{
		MLookup orgL = MLookupFactory.get (Env.getCtx(), m_WindowNo, 0, 2163, DisplayType.TableDir);
		fOrg = new VLookup ("AD_Org_ID", false, false, true, orgL);
		//	lOrg.setText(Msg.translate(Env.getCtx(), "AD_Org_ID"));
		fOrg.addVetoableChangeListener(this);
		//MPo, 2/8/2016
		MLookup whL = MLookupFactory.get (Env.getCtx(), m_WindowNo, 0, 2202, DisplayType.TableDir);
		fWarehouse = new VLookup ("M_Warehouse_ID", false, false, true, whL);
		fWarehouse.addVetoableChangeListener(this);
		//MPo, 3/8/2016
		MLookup brL = MLookupFactory.get (Env.getCtx(), m_WindowNo, 0, 1000168, DisplayType.TableDir);
		fBranch = new VLookup ("ZI_Branch_ID", false, false, true, brL);
		fBranch.addVetoableChangeListener(this);
		//MPo, 4/8/2016
		MLookup dtL = MLookupFactory.get (Env.getCtx(), m_WindowNo, 1501, 
				DisplayType.Table, Env.getLanguage(Env.getCtx()), "DocType", 170, 
			    false, "C_DocType.docbasetype IN ('ARI','ARC')"); // AR Invoices and AR Credit Memos
		fDT = new VLookup ("C_DocType_ID", false, false, true, dtL);
		fDT.addVetoableChangeListener(this);
		//
		MLookup docActionL = MLookupFactory.get(Env.getCtx(), m_WindowNo, 3494 /* C_Invoice.DocStatus */, 
		DisplayType.List, Env.getLanguage(Env.getCtx()), "DocAction", 135 /* _Document Action */,
		false, "AD_Ref_List.Value IN ('CO','PR')");
		docAction = new VLookup("DocAction", true, false, true,docActionL);
		//  lDcoACtion.setText((Msg.translate(Env.getCtx(), "DocAction")););
		docAction.addVetoableChangeListener(this);
		
		//
		MLookup bpL = MLookupFactory.get (Env.getCtx(), m_WindowNo, 0, 2762, DisplayType.Search);
		fBPartner = new VLookup ("C_BPartner_ID", false, false, true, bpL);
	//	lBPartner.setText(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		fBPartner.addVetoableChangeListener(this);
		
		//Document Type Sales Order/Vendor RMA
        lDocType.setText(Msg.translate(Env.getCtx(), "C_DocType_ID"));
        cmbDocType.addItem(new KeyNamePair(MOrder.Table_ID, Msg.translate(Env.getCtx(), "Order")));
        cmbDocType.addItem(new KeyNamePair(MRMA.Table_ID, Msg.translate(Env.getCtx(), "CustomerRMA")));
        cmbDocType.addActionListener(this);
        
        panel.getStatusBar().setStatusLine(Msg.getMsg(Env.getCtx(), "InvGenerateSel"));//@@
	}	//	fillPicks
	
	public void executeQuery()
	{
		KeyNamePair docTypeKNPair = (KeyNamePair)cmbDocType.getSelectedItem();
		executeQuery(docTypeKNPair, panel.getMiniTable());
	}   //  executeQuery
	
	/**
	 *	Action Listener
	 *  @param e event
	 */
	public void actionPerformed(ActionEvent e)
	{
		if (cmbDocType.equals(e.getSource()))
		{
		   executeQuery();
		    return;
		}
		
		validate();
	}	//	actionPerformed
	
	public void validate()
	{
		panel.saveSelection();
		
		ArrayList<Integer> selection = getSelection();
		if (selection != null && selection.size() > 0 && isSelectionActive())		
			panel.generate();
		else
			panel.dispose();
	}

	/**
	 *	Vetoable Change Listener - requery
	 *  @param e event
	 */
	public void vetoableChange(PropertyChangeEvent e)
	{
		if (log.isLoggable(Level.INFO)) log.info(e.getPropertyName() + "=" + e.getNewValue());
		if (e.getPropertyName().equals("AD_Org_ID"))
			m_AD_Org_ID = e.getNewValue();
		//MPo, 2/8/2016 Add Warehouse
		if (e.getPropertyName().equals("M_Warehouse_ID"))
		{
			m_M_Warehouse_ID = e.getNewValue();
			fWarehouse.setValue(m_M_Warehouse_ID);	//	display value
		}
		//MPo, 3/8/2016 Add Branch
		if (e.getPropertyName().equals("ZI_Branch_ID"))
		{
			m_ZI_Branch_ID = e.getNewValue();
			fBranch.setValue(m_ZI_Branch_ID);	//	display value
		}
		//MPo, 4/8/2016 Add Document Type
		if (e.getPropertyName().equals("C_DocType_ID"))
			m_C_DocType_ID = e.getNewValue();
			fDT.setValue(m_C_DocType_ID);	
		//
			
		if (e.getPropertyName().equals("C_BPartner_ID"))
		{
			m_C_BPartner_ID = e.getNewValue();
			fBPartner.setValue(m_C_BPartner_ID);	//	display value
		}
		
		executeQuery();
	}	//	vetoableChange
	
	/**************************************************************************
	 *	Generate Shipments
	 */
	public String generate()
	{
		KeyNamePair docTypeKNPair = (KeyNamePair)cmbDocType.getSelectedItem();
		String docActionSelected = (String)docAction.getValue();
		//MPo, 4/8/2016
		// return generate(panel.getStatusBar(), docTypeKNPair, docActionSelected);
		return generate(panel.getStatusBar(), docTypeKNPair, docActionSelected, m_C_DocType_ID);
	}	//	generateShipments
}
