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

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ZI_Incoterm
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_ZI_Incoterm extends PO implements I_ZI_Incoterm, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160803L;

    /** Standard Constructor */
    public X_ZI_Incoterm (Properties ctx, int ZI_Incoterm_ID, String trxName)
    {
      super (ctx, ZI_Incoterm_ID, trxName);
      /** if (ZI_Incoterm_ID == 0)
        {
			setName (null);
			setValue (null);
			setZI_Incoterm_ID (0);
			setZI_Usage (null);
// B
        } */
    }

    /** Load Constructor */
    public X_ZI_Incoterm (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_ZI_Incoterm[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Incoterm.
		@param ZI_Incoterm_ID Incoterm	  */
	public void setZI_Incoterm_ID (int ZI_Incoterm_ID)
	{
		if (ZI_Incoterm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ZI_Incoterm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ZI_Incoterm_ID, Integer.valueOf(ZI_Incoterm_ID));
	}

	/** Get Incoterm.
		@return Incoterm	  */
	public int getZI_Incoterm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZI_Incoterm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZI_Incoterm_UU.
		@param ZI_Incoterm_UU ZI_Incoterm_UU	  */
	public void setZI_Incoterm_UU (String ZI_Incoterm_UU)
	{
		set_Value (COLUMNNAME_ZI_Incoterm_UU, ZI_Incoterm_UU);
	}

	/** Get ZI_Incoterm_UU.
		@return ZI_Incoterm_UU	  */
	public String getZI_Incoterm_UU () 
	{
		return (String)get_Value(COLUMNNAME_ZI_Incoterm_UU);
	}

	/** ZI_Usage AD_Reference_ID=53382 */
	public static final int ZI_USAGE_AD_Reference_ID=53382;
	/** Both = B */
	public static final String ZI_USAGE_Both = "B";
	/** Sales = S */
	public static final String ZI_USAGE_Sales = "S";
	/** Purchases = P */
	public static final String ZI_USAGE_Purchases = "P";
	/** Set Usage.
		@param ZI_Usage 
		Usage indicates if this payment term is used for sales, purchases or both.
	  */
	public void setZI_Usage (String ZI_Usage)
	{

		set_Value (COLUMNNAME_ZI_Usage, ZI_Usage);
	}

	/** Get Usage.
		@return Usage indicates if this payment term is used for sales, purchases or both.
	  */
	public String getZI_Usage () 
	{
		return (String)get_Value(COLUMNNAME_ZI_Usage);
	}
}