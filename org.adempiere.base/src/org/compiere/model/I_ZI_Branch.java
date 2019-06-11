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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for ZI_Branch
 *  @author iDempiere (generated) 
 *  @version Release 3.1
 */
public interface I_ZI_Branch 
{

    /** TableName=ZI_Branch */
    public static final String Table_Name = "ZI_Branch";

    /** AD_Table_ID=1000009 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/** Set Address.
	  * Location or Address
	  */
	public void setC_Location_ID (int C_Location_ID);

	/** Get Address.
	  * Location or Address
	  */
	public int getC_Location_ID();

	public I_C_Location getC_Location() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name ZI_Branch */
    public static final String COLUMNNAME_ZI_Branch = "ZI_Branch";

	/** Set Branch Code.
	  * Branch in an organization for Tax reporting purposes
	  */
	public void setZI_Branch (String ZI_Branch);

	/** Get Branch Code.
	  * Branch in an organization for Tax reporting purposes
	  */
	public String getZI_Branch();

    /** Column name ZI_Branch_ID */
    public static final String COLUMNNAME_ZI_Branch_ID = "ZI_Branch_ID";

	/** Set Branch	  */
	public void setZI_Branch_ID (int ZI_Branch_ID);

	/** Get Branch	  */
	public int getZI_Branch_ID();

    /** Column name ZI_Branch_UU */
    public static final String COLUMNNAME_ZI_Branch_UU = "ZI_Branch_UU";

	/** Set ZI_Branch_UU	  */
	public void setZI_Branch_UU (String ZI_Branch_UU);

	/** Get ZI_Branch_UU	  */
	public String getZI_Branch_UU();

    /** Column name ZI_Location2_ID */
    public static final String COLUMNNAME_ZI_Location2_ID = "ZI_Location2_ID";

	/** Set Address (local language).
	  * Location or Address in local language
	  */
	public void setZI_Location2_ID (int ZI_Location2_ID);

	/** Get Address (local language).
	  * Location or Address in local language
	  */
	public int getZI_Location2_ID();

	public I_C_Location getZI_Location2() throws RuntimeException;
}
