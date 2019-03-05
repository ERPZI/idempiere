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
import org.compiere.util.KeyNamePair;

/** Generated Model for ZI_Branch
 *  @author iDempiere (generated) 
 *  @version Release 3.1 - $Id$ */
public class X_ZI_Branch extends PO implements I_ZI_Branch, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20160803L;

    /** Standard Constructor */
    public X_ZI_Branch (Properties ctx, int ZI_Branch_ID, String trxName)
    {
      super (ctx, ZI_Branch_ID, trxName);
      /** if (ZI_Branch_ID == 0)
        {
			setC_Location_ID (0);
			setName (null);
			setZI_Branch (null);
			setZI_Branch_ID (0);
        } */
    }

    /** Load Constructor */
    public X_ZI_Branch (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_ZI_Branch[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_Location getC_Location() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getC_Location_ID(), get_TrxName());	}

	/** Set Address.
		@param C_Location_ID 
		Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID)
	{
		if (C_Location_ID < 1) 
			set_Value (COLUMNNAME_C_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_Location_ID, Integer.valueOf(C_Location_ID));
	}

	/** Get Address.
		@return Location or Address
	  */
	public int getC_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Branch Code.
		@param ZI_Branch 
		Branch in an organization for Tax reporting purposes
	  */
	public void setZI_Branch (String ZI_Branch)
	{
		set_Value (COLUMNNAME_ZI_Branch, ZI_Branch);
	}

	/** Get Branch Code.
		@return Branch in an organization for Tax reporting purposes
	  */
	public String getZI_Branch () 
	{
		return (String)get_Value(COLUMNNAME_ZI_Branch);
	}

	/** Set Branch.
		@param ZI_Branch_ID Branch	  */
	public void setZI_Branch_ID (int ZI_Branch_ID)
	{
		if (ZI_Branch_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ZI_Branch_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ZI_Branch_ID, Integer.valueOf(ZI_Branch_ID));
	}

	/** Get Branch.
		@return Branch	  */
	public int getZI_Branch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZI_Branch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ZI_Branch_UU.
		@param ZI_Branch_UU ZI_Branch_UU	  */
	public void setZI_Branch_UU (String ZI_Branch_UU)
	{
		set_Value (COLUMNNAME_ZI_Branch_UU, ZI_Branch_UU);
	}

	/** Get ZI_Branch_UU.
		@return ZI_Branch_UU	  */
	public String getZI_Branch_UU () 
	{
		return (String)get_Value(COLUMNNAME_ZI_Branch_UU);
	}

	public I_C_Location getZI_Location2() throws RuntimeException
    {
		return (I_C_Location)MTable.get(getCtx(), I_C_Location.Table_Name)
			.getPO(getZI_Location2_ID(), get_TrxName());	}

	/** Set Address (local language).
		@param ZI_Location2_ID 
		Location or Address in local language
	  */
	public void setZI_Location2_ID (int ZI_Location2_ID)
	{
		if (ZI_Location2_ID < 1) 
			set_Value (COLUMNNAME_ZI_Location2_ID, null);
		else 
			set_Value (COLUMNNAME_ZI_Location2_ID, Integer.valueOf(ZI_Location2_ID));
	}

	/** Get Address (local language).
		@return Location or Address in local language
	  */
	public int getZI_Location2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ZI_Location2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}