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
package org.adempiere.webui.apps.form;

import java.util.ArrayList;
import java.util.logging.Level;

import org.adempiere.webui.ClientInfo;
import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.Column;
import org.adempiere.webui.component.Columns;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.IFormController;
import org.adempiere.webui.util.ZKUpdateUtil;
import org.compiere.apps.form.InvoiceGen;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MOrder;
import org.compiere.model.MRMA;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.North;
//MPo, 26/5/18
import org.compiere.model.MTable;
//

/**
 * Generate Invoice (manual) view class
 *
 */
public class WInvoiceGen extends InvoiceGen implements IFormController, EventListener<Event>, ValueChangeListener
{
	private WGenForm form;

	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WInvoiceGen.class);
	//
	private Label lOrg = new Label();
	private WTableDirEditor fOrg;
	//MPo, 26/5/18 add original change: 2/8/2016 Add warehouse for selection
	private Label lWarehouse = new Label();
	private WTableDirEditor fWarehouse;
	//MPo, 26/5/18 add original change: 3/8/2016 Add branch for selection to make sure that multiple orders with different branches are not 
	//converted to one invoice with a single branch
	private Label lBranch = new Label();
	private WTableDirEditor fBranch;
	//MPo, 26/5/18 add original change: 4/8/2016 Add Invoicing document type for selection
	private Label lDT = new Label();
	private WTableDirEditor fDT;
	//
	private Label lBPartner = new Label();
	private WSearchEditor fBPartner;
	private Label     lDocType = new Label();
	private Listbox  cmbDocType = ListboxFactory.newDropdownListbox();
	private Label   lDocAction = new Label();
	private WTableDirEditor docAction;

	private int noOfColumn;
	
	public WInvoiceGen()
	{
		log.info("");

		form = new WGenForm(this);		
		Env.setContext(Env.getCtx(), form.getWindowNo(), "IsSOTrx", "Y");

		try
		{
			super.dynInit();
			dynInit();
			zkInit();

			form.postQueryEvent();
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, "init", ex);
		}
		
		ClientInfo.onClientInfo(form, this::onClientInfo);
	}	//	init

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
	void zkInit() throws Exception
	{
		setupColumns();
		
		lOrg.setText(Msg.translate(Env.getCtx(), "AD_Org_ID"));
		lBPartner.setText(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		//MPo, 26/5/18 add original change: 2/8/2016 Add Warehouse
		lWarehouse.setText(Msg.translate(Env.getCtx(), "M_Warehouse_ID"));
		//MPo, 26/5/18 add original change: 3/8/2016 Add Branch
		lBranch.setText(Msg.translate(Env.getCtx(), "ZI_Branch_ID"));
		//MPo, 26/5/18 add original change: 4/8/2016 Add Document Type
		lDT.setText(Msg.translate(Env.getCtx(), "C_DocType_ID"));
		//
		Row row = form.getParameterPanel().newRows().newRow();
		row.appendCellChild(lOrg.rightAlign());
		ZKUpdateUtil.setHflex(fOrg.getComponent(), "true");
		row.appendCellChild(fOrg.getComponent());
		//MPo, 26/5/18 add original change: 2/8/2016 Add Warehouse for selection
		row.appendCellChild(lWarehouse.rightAlign());
		ZKUpdateUtil.setHflex(fWarehouse.getComponent(), "true");
		row.appendCellChild(fWarehouse.getComponent());
		//row.appendCellChild(new Space());
		//
		row.appendCellChild(lBPartner.rightAlign());
		ZKUpdateUtil.setHflex(fBPartner.getComponent(), "true");
		row.appendCellChild(fBPartner.getComponent());

		row = new Row();
		form.getParameterPanel().getRows().appendChild(row);
		//MPo, 26/5/18 add original change: 3/8/2016 Add Branch for selection
		row.appendCellChild(lBranch.rightAlign());
		ZKUpdateUtil.setHflex(fBranch.getComponent(), "true");
		row.appendCellChild(fBranch.getComponent());
		//row.appendCellChild(new Space());
		//MPo, 26/5/18 add original change: 4/8/2016 Add Document type for selection
		row.appendCellChild(lDT.rightAlign());
		ZKUpdateUtil.setHflex(fDT.getComponent(), "true");
		row.appendCellChild(fDT.getComponent());
		//row.appendCellChild(new Space());
		//
		row.appendCellChild(lDocType.rightAlign());
		ZKUpdateUtil.setHflex(cmbDocType, "true");
		row.appendCellChild(cmbDocType);
		row.appendCellChild(lDocAction.rightAlign());
		ZKUpdateUtil.setHflex(docAction.getComponent(), "true");
		row.appendCellChild(docAction.getComponent());
		if (noOfColumn < 6)
			LayoutUtils.compactTo(form.getParameterPanel(), noOfColumn);
		else
			LayoutUtils.expandTo(form.getParameterPanel(), noOfColumn, true);
	}	//	jbInit

	protected void setupColumns() {
		noOfColumn = 6;
		if (ClientInfo.maxWidth(ClientInfo.MEDIUM_WIDTH-1))
		{
			if (ClientInfo.maxWidth(ClientInfo.SMALL_WIDTH-1))
				noOfColumn = 2;
			else
				noOfColumn = 4;
		}
		if (noOfColumn == 2)
		{
			Columns columns = new Columns();
			Column column = new Column();
			column.setWidth("35%");
			columns.appendChild(column);
			column = new Column();
			column.setWidth("65%");
			columns.appendChild(column);
			form.getParameterPanel().appendChild(columns);
		}
	}

	/**
	 *	Fill Picks.
	 *		Column_ID from C_Order
	 *  @throws Exception if Lookups cannot be initialized
	 */
	public void dynInit() throws Exception
	{
		MLookup orgL = MLookupFactory.get (Env.getCtx(), form.getWindowNo(), 0, 2163, DisplayType.TableDir);
		fOrg = new WTableDirEditor ("AD_Org_ID", false, false, true, orgL);
	//	lOrg.setText(Msg.translate(Env.getCtx(), "AD_Org_ID"));
		fOrg.addValueChangeListener(this);
		//
		//MPo, 26/5/18 add original change: 2/8/2016 Add Warehouse
		MLookup whL = MLookupFactory.get (Env.getCtx(), form.getWindowNo(), 0, 2202, DisplayType.TableDir);
		fWarehouse = new WTableDirEditor ("M_Warehouse_ID", false, false, true, whL);
		fWarehouse.addValueChangeListener(this);
		//
		//MPo, 26/5/18 add original change: 3/8/2016 Add Branch
		//MLookup brL = MLookupFactory.get (Env.getCtx(), form.getWindowNo(), 0, 1000168, DisplayType.TableDir);
		//fBranch = new WTableDirEditor ("ZI_Branch_ID", false, false, true, brL);
		//fBranch.addValueChangeListener(this);
		//
		//MPo, 26/5/18 add original change: 20/8/2016 This is to avoid issues when AD_Column_ID is different in DEV,PROTO,UAT and PROD
		int AD_Column_ID = MTable.get(Env.getCtx(), "ZI_Branch").getColumn("ZI_Branch_ID").getAD_Column_ID();
		//System.out.println(AD_Column_ID);
		MLookup brL = MLookupFactory.get (Env.getCtx(), form.getWindowNo(), 0, AD_Column_ID, DisplayType.TableDir);
		fBranch = new WTableDirEditor ("ZI_Branch_ID", false, false, true, brL);
		fBranch.addValueChangeListener(this);
		//
		//MPo, 26/5/18 add original change: 4/8/2016 Add Document type
		MLookup dtL = MLookupFactory.get (Env.getCtx(), form.getWindowNo(), 1501, 
			DisplayType.Table, Env.getLanguage(Env.getCtx()), "DocType", 170, 
			false, "C_DocType.docbasetype IN ('ARI','ARC')"); // AR Invoices and AR Credit Memos
			fDT = new WTableDirEditor ("C_DocType_ID", false, false, true, dtL);
			fDT.addValueChangeListener(this);
		//		
		MLookup bpL = MLookupFactory.get (Env.getCtx(), form.getWindowNo(), 0, 2762, DisplayType.Search);
		fBPartner = new WSearchEditor ("C_BPartner_ID", false, false, true, bpL);
	//	lBPartner.setText(Msg.translate(Env.getCtx(), "C_BPartner_ID"));
		fBPartner.addValueChangeListener(this);
		//      Document Action Prepared/ Completed
		lDocAction.setText(Msg.translate(Env.getCtx(), "DocAction"));
		MLookup docActionL = MLookupFactory.get(Env.getCtx(), form.getWindowNo(), 3495 /* C_Invoice.DocAction */,
				DisplayType.List, Env.getLanguage(Env.getCtx()), "DocAction", 135 /* _Document Action */,
				false, "AD_Ref_List.Value IN ('CO','PR')");
		docAction = new WTableDirEditor("DocAction", true, false, true,docActionL);
		//MPo, 26/5/18 add original change: 2/8/2016
		//docAction.setValue(DocAction.ACTION_Complete);
		docAction.setValue(DocAction.ACTION_Prepare);
		//
		// docAction.addValueChangeListener(this); // IDEMPIERE-768

//      Document Type Sales Order/Vendor RMA
        lDocType.setText(Msg.translate(Env.getCtx(), "C_DocType_ID"));
        cmbDocType.addItem(new KeyNamePair(MOrder.Table_ID, Msg.translate(Env.getCtx(), "Order")));
        cmbDocType.addItem(new KeyNamePair(MRMA.Table_ID, Msg.translate(Env.getCtx(), "CustomerRMA")));
        cmbDocType.addActionListener(this);
        cmbDocType.setSelectedIndex(0);

        form.getStatusBar().setStatusLine(Msg.getMsg(Env.getCtx(), "InvGenerateSel"));//@@
	}	//	fillPicks

	/**
	 *  Query Info
	 */
	public void executeQuery()
	{
		KeyNamePair docTypeKNPair = cmbDocType.getSelectedItem().toKeyNamePair();
		executeQuery(docTypeKNPair, form.getMiniTable());
		if (ClientInfo.maxHeight(ClientInfo.SMALL_HEIGHT-1))
		{
			Component comp = form.getParameterPanel().getParent();
			if (comp instanceof North)
				((North)comp).setOpen(false);
		}
		form.getMiniTable().repaint();
		form.invalidate();
	}   //  executeQuery

	protected void onClientInfo()
	{
		if (ClientInfo.isMobile() && form.getPage() != null) 
		{
			if (noOfColumn > 0 && form.getParameterPanel().getRows() != null)
			{
				int t = 6;
				if (ClientInfo.maxWidth(ClientInfo.MEDIUM_WIDTH-1))
				{
					if (ClientInfo.maxWidth(ClientInfo.SMALL_WIDTH-1))
						t = 2;
					else
						t = 4;
				}
				if (t != noOfColumn)
				{
					form.getParameterPanel().getRows().detach();
					if (form.getParameterPanel().getColumns() != null)
						form.getParameterPanel().getColumns().detach();
					try {
						zkInit();
						form.invalidate();
					} catch (Exception e1) {}
				}
			}
		}
	}
	
	/**
	 *	Action Listener
	 *  @param e event
	 */
	public void onEvent(Event e)
	{
		if (log.isLoggable(Level.INFO)) log.info("Cmd=" + e.getTarget().getId());
		//
		if(cmbDocType.equals(e.getTarget()))
		{
		    form.postQueryEvent();
		    return;
		}

		//
		validate();
	}	//	actionPerformed

	public void validate()
	{
		String docActionSelected = (String)docAction.getValue();
		if ( docActionSelected==null || docActionSelected.isEmpty() )
			throw new WrongValueException(docAction.getComponent(), Msg.translate(Env.getCtx(), "FillMandatory"));
		//MPo, 26/5/18 add original change: 2/8/2016 Add Warehouse
		if (m_M_Warehouse_ID == null)
		{
			throw new WrongValueException(fWarehouse.getComponent(), Msg.translate(Env.getCtx(), "FillMandatory"));
		}
		//MPo, 26/5/18 add original change: 4/8/2016 Add Document Type
		if (m_C_DocType_ID == null)
		{
			throw new WrongValueException(fDT.getComponent(), Msg.translate(Env.getCtx(), "FillMandatory"));
		}
		//
		form.saveSelection();

		ArrayList<Integer> selection = getSelection();
		if (selection != null && selection.size() > 0 && isSelectionActive())
			form.generate();
		else
			form.dispose();
	}

	/**
	 *	Value Change Listener - requery
	 *  @param e event
	 */
	public void valueChange(ValueChangeEvent e)
	{
		if (log.isLoggable(Level.INFO)) log.info(e.getPropertyName() + "=" + e.getNewValue());
		if (e.getPropertyName().equals("AD_Org_ID"))
			m_AD_Org_ID = e.getNewValue();
		//MPo, 26/5/18 add original change: 2/8/2016 Add Warehouse
		if (e.getPropertyName().equals("M_Warehouse_ID"))
			m_M_Warehouse_ID = e.getNewValue();
			fWarehouse.setValue(m_M_Warehouse_ID);
		//MPo, 26/5/18 add original change: 3/8/2016 Add Branch
		if (e.getPropertyName().equals("ZI_Branch_ID"))
			m_ZI_Branch_ID = e.getNewValue();
			fBranch.setValue(m_ZI_Branch_ID);	
		//MPo, 26/5/18 add original change: 4/8/2016 Add Document Type
		if (e.getPropertyName().equals("C_DocType_ID"))
			m_C_DocType_ID = e.getNewValue();
		fDT.setValue(m_C_DocType_ID);	
		//
		if (e.getPropertyName().equals("C_BPartner_ID"))
		{
			m_C_BPartner_ID = e.getNewValue();
			fBPartner.setValue(m_C_BPartner_ID);	//	display value
		}
		form.postQueryEvent();
	}	//	vetoableChange

	/**************************************************************************
	 *	Generate Shipments
	 */
	public String generate()
	{
		KeyNamePair docTypeKNPair = (KeyNamePair)cmbDocType.getSelectedItem().toKeyNamePair();
		String docActionSelected = (String)docAction.getValue();
		//MPo, 26/5/18 add original change: 4/8/2016
		//return generate(form.getStatusBar(), docTypeKNPair, docActionSelected);
		return generate(form.getStatusBar(), docTypeKNPair, docActionSelected, m_C_DocType_ID);
	}	//	generateShipments

	public ADForm getForm()
	{
		return form;
	}
}
