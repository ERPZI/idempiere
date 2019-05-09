package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MDepreciationEntry;
import org.compiere.model.MDepreciationExp;
import org.compiere.util.Env;
//MPo, 25/11/18
import org.compiere.model.MAsset;
//


/**
 *  @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *  @version  $Id$
 *
 */
public class Doc_DepreciationEntry extends Doc
{
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@parem trxName trx
	 */
	public Doc_DepreciationEntry (MAcctSchema as, ResultSet rs, String trxName)
	{
		super(as, MDepreciationEntry.class, rs, null, trxName);
	}	//	Doc_A_Depreciation_Entry

	/** Posting Type				*/
	private String						m_PostingType = null;
	private int							m_C_AcctSchema_ID = 0;
	
	
	protected String loadDocumentDetails ()
	{
		MDepreciationEntry entry = (MDepreciationEntry)getPO();
		m_PostingType = entry.getPostingType();
		m_C_AcctSchema_ID = entry.getC_AcctSchema_ID();
		
		return null;
	}
	
	private DocLine createLine(MDepreciationExp depexp)
	{
		if (!depexp.isProcessed())
			return null;
		DocLine docLine = new DocLine (depexp, this);
		return docLine;
	}
	
	
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}   //  getBalance

	
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		ArrayList<Fact> facts = new ArrayList<Fact>();
		//	Other Acct Schema
		if (as.getC_AcctSchema_ID() != m_C_AcctSchema_ID)
			return facts;
		
		//  create Fact Header
		Fact fact = new Fact (this, as, m_PostingType);

		MDepreciationEntry entry = (MDepreciationEntry)getPO();
		Iterator<MDepreciationExp> it = entry.getLinesIterator(false);
		while(it.hasNext())
		{
			MDepreciationExp depexp = it.next();
			DocLine line = createLine(depexp);
			BigDecimal expenseAmt = depexp.getExpense();
			//
			MAccount dr_acct = MAccount.get(getCtx(), depexp.getDR_Account_ID());
			MAccount cr_acct = MAccount.get(getCtx(), depexp.getCR_Account_ID());
			FactUtil.createSimpleOperation(fact, line, dr_acct, cr_acct, as.getC_Currency_ID(), expenseAmt, false);
			//MPo, 25/11/2018 CCtr, PrCtr, FArea for depreciation posting
			MAsset asset = MAsset.get(getCtx(), depexp.getA_Asset_ID(), null);
			//System.out.println("Asset: " + asset.getA_Asset_ID());
			FactLine[] lines = fact.getLines();
			for (int i = lines.length-1; i > lines.length-3; i--) { // Get the last 2 fact lines
				lines[i].setUser1_ID(asset.getUser1_ID()); //PrCtr
				lines[i].setUser2_ID(asset.getUser2_ID()); //CCtr
				lines[i].setC_Activity_ID(asset.getC_Activity_ID()); //FArea
				//System.out.println("i: " + i);
				//System.out.println("FactLine User1_ID: " + lines[i].getUser1_ID());
				//System.out.println("FactLine User2_ID: " + lines[i].getUser2_ID());
				//System.out.println("FactLine C_Activity_ID: " + lines[i].getC_Activity_ID());
			}
			//MPo
		}
		//
		facts.add(fact);
		return facts;
	}
}

