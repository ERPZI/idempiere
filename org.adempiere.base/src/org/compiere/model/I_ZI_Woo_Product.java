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

/** Generated Interface for ZI_Woo_Product
 *  @author iDempiere (generated) 
 *  @version Release 10
 */
public interface I_ZI_Woo_Product 
{

    /** TableName=ZI_Woo_Product */
    public static final String Table_Name = "ZI_Woo_Product";

    /** AD_Table_ID=1000098 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Tenant.
	  * Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within tenant
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within tenant
	  */
	public int getAD_Org_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

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

    /** Column name IsSold */
    public static final String COLUMNNAME_IsSold = "IsSold";

	/** Set Sold.
	  * Organization sells this product
	  */
	public void setIsSold (boolean IsSold);

	/** Get Sold.
	  * Organization sells this product
	  */
	public boolean isSold();

    /** Column name IsWebStoreFeatured */
    public static final String COLUMNNAME_IsWebStoreFeatured = "IsWebStoreFeatured";

	/** Set Featured in Web Store.
	  * If selected, the product is displayed in the initial or any empty search
	  */
	public void setIsWebStoreFeatured (boolean IsWebStoreFeatured);

	/** Get Featured in Web Store.
	  * If selected, the product is displayed in the initial or any empty search
	  */
	public boolean isWebStoreFeatured();

    /** Column name locator */
    public static final String COLUMNNAME_locator = "locator";

	/** Set locator	  */
	public void setlocator (String locator);

	/** Get locator	  */
	public String getlocator();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

    /** Column name org_name */
    public static final String COLUMNNAME_org_name = "org_name";

	/** Set org_name	  */
	public void setorg_name (String org_name);

	/** Get org_name	  */
	public String getorg_name();

    /** Column name org_search */
    public static final String COLUMNNAME_org_search = "org_search";

	/** Set org_search	  */
	public void setorg_search (String org_search);

	/** Get org_search	  */
	public String getorg_search();

    /** Column name price_list_version */
    public static final String COLUMNNAME_price_list_version = "price_list_version";

	/** Set price_list_version	  */
	public void setprice_list_version (String price_list_version);

	/** Get price_list_version	  */
	public String getprice_list_version();

    /** Column name product_name */
    public static final String COLUMNNAME_product_name = "product_name";

	/** Set product_name	  */
	public void setproduct_name (String product_name);

	/** Get product_name	  */
	public String getproduct_name();

    /** Column name product_name_local */
    public static final String COLUMNNAME_product_name_local = "product_name_local";

	/** Set product_name_local	  */
	public void setproduct_name_local (String product_name_local);

	/** Get product_name_local	  */
	public String getproduct_name_local();

    /** Column name product_search */
    public static final String COLUMNNAME_product_search = "product_search";

	/** Set product_search	  */
	public void setproduct_search (String product_search);

	/** Get product_search	  */
	public String getproduct_search();

    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/** Set On Hand Quantity.
	  * On Hand Quantity
	  */
	public void setQtyOnHand (BigDecimal QtyOnHand);

	/** Get On Hand Quantity.
	  * On Hand Quantity
	  */
	public BigDecimal getQtyOnHand();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/** Set Valid from.
	  * Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom);

	/** Get Valid from.
	  * Valid from including this date (first day)
	  */
	public Timestamp getValidFrom();

    /** Column name warehouse */
    public static final String COLUMNNAME_warehouse = "warehouse";

	/** Set warehouse	  */
	public void setwarehouse (String warehouse);

	/** Get warehouse	  */
	public String getwarehouse();

    /** Column name woo_brand */
    public static final String COLUMNNAME_woo_brand = "woo_brand";

	/** Set woo_brand	  */
	public void setwoo_brand (String woo_brand);

	/** Get woo_brand	  */
	public String getwoo_brand();

    /** Column name woo_enable_reviews */
    public static final String COLUMNNAME_woo_enable_reviews = "woo_enable_reviews";

	/** Set woo_enable_reviews	  */
	public void setwoo_enable_reviews (boolean woo_enable_reviews);

	/** Get woo_enable_reviews	  */
	public boolean iswoo_enable_reviews();

    /** Column name woo_product_categories */
    public static final String COLUMNNAME_woo_product_categories = "woo_product_categories";

	/** Set woo_product_categories	  */
	public void setwoo_product_categories (String woo_product_categories);

	/** Get woo_product_categories	  */
	public String getwoo_product_categories();

    /** Column name woo_product_description */
    public static final String COLUMNNAME_woo_product_description = "woo_product_description";

	/** Set woo_product_description	  */
	public void setwoo_product_description (String woo_product_description);

	/** Get woo_product_description	  */
	public String getwoo_product_description();

    /** Column name woo_product_image */
    public static final String COLUMNNAME_woo_product_image = "woo_product_image";

	/** Set woo_product_image	  */
	public void setwoo_product_image (String woo_product_image);

	/** Get woo_product_image	  */
	public String getwoo_product_image();

    /** Column name woo_product_name */
    public static final String COLUMNNAME_woo_product_name = "woo_product_name";

	/** Set woo_product_name	  */
	public void setwoo_product_name (String woo_product_name);

	/** Get woo_product_name	  */
	public String getwoo_product_name();

    /** Column name woo_product_short_description */
    public static final String COLUMNNAME_woo_product_short_description = "woo_product_short_description";

	/** Set woo_product_short_description	  */
	public void setwoo_product_short_description (String woo_product_short_description);

	/** Get woo_product_short_description	  */
	public String getwoo_product_short_description();

    /** Column name woo_quantity */
    public static final String COLUMNNAME_woo_quantity = "woo_quantity";

	/** Set woo_quantity	  */
	public void setwoo_quantity (BigDecimal woo_quantity);

	/** Get woo_quantity	  */
	public BigDecimal getwoo_quantity();

    /** Column name woo_regular_price */
    public static final String COLUMNNAME_woo_regular_price = "woo_regular_price";

	/** Set woo_regular_price	  */
	public void setwoo_regular_price (BigDecimal woo_regular_price);

	/** Get woo_regular_price	  */
	public BigDecimal getwoo_regular_price();

    /** Column name woo_sale_price */
    public static final String COLUMNNAME_woo_sale_price = "woo_sale_price";

	/** Set woo_sale_price	  */
	public void setwoo_sale_price (BigDecimal woo_sale_price);

	/** Get woo_sale_price	  */
	public BigDecimal getwoo_sale_price();

    /** Column name woo_stock_management */
    public static final String COLUMNNAME_woo_stock_management = "woo_stock_management";

	/** Set woo_stock_management	  */
	public void setwoo_stock_management (boolean woo_stock_management);

	/** Get woo_stock_management	  */
	public boolean iswoo_stock_management();

    /** Column name woo_trashed */
    public static final String COLUMNNAME_woo_trashed = "woo_trashed";

	/** Set woo_trashed	  */
	public void setwoo_trashed (boolean woo_trashed);

	/** Get woo_trashed	  */
	public boolean iswoo_trashed();
}
