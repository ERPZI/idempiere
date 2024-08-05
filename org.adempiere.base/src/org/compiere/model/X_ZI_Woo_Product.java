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

/** Generated Model for ZI_Woo_Product
 *  @author iDempiere (generated) 
 *  @version Release 10 - $Id$ */
@org.adempiere.base.Model(table="ZI_Woo_Product")
public class X_ZI_Woo_Product extends PO implements I_ZI_Woo_Product, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20240805L;

    /** Standard Constructor */
    public X_ZI_Woo_Product (Properties ctx, int ZI_Woo_Product_ID, String trxName)
    {
      super (ctx, ZI_Woo_Product_ID, trxName);
      /** if (ZI_Woo_Product_ID == 0)
        {
        } */
    }

    /** Standard Constructor */
    public X_ZI_Woo_Product (Properties ctx, int ZI_Woo_Product_ID, String trxName, String ... virtualColumns)
    {
      super (ctx, ZI_Woo_Product_ID, trxName, virtualColumns);
      /** if (ZI_Woo_Product_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_ZI_Woo_Product (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_ZI_Woo_Product[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Sold.
		@param IsSold Organization sells this product
	*/
	public void setIsSold (boolean IsSold)
	{
		set_ValueNoCheck (COLUMNNAME_IsSold, Boolean.valueOf(IsSold));
	}

	/** Get Sold.
		@return Organization sells this product
	  */
	public boolean isSold()
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

	/** Set Featured in Web Store.
		@param IsWebStoreFeatured If selected, the product is displayed in the initial or any empty search
	*/
	public void setIsWebStoreFeatured (boolean IsWebStoreFeatured)
	{
		set_ValueNoCheck (COLUMNNAME_IsWebStoreFeatured, Boolean.valueOf(IsWebStoreFeatured));
	}

	/** Get Featured in Web Store.
		@return If selected, the product is displayed in the initial or any empty search
	  */
	public boolean isWebStoreFeatured()
	{
		Object oo = get_Value(COLUMNNAME_IsWebStoreFeatured);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set locator.
		@param locator locator
	*/
	public void setlocator (String locator)
	{
		set_ValueNoCheck (COLUMNNAME_locator, locator);
	}

	/** Get locator.
		@return locator	  */
	public String getlocator()
	{
		return (String)get_Value(COLUMNNAME_locator);
	}

	/** Set Product.
		@param M_Product_ID Product, Service, Item
	*/
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set org_name.
		@param org_name org_name
	*/
	public void setorg_name (String org_name)
	{
		set_ValueNoCheck (COLUMNNAME_org_name, org_name);
	}

	/** Get org_name.
		@return org_name	  */
	public String getorg_name()
	{
		return (String)get_Value(COLUMNNAME_org_name);
	}

	/** Set org_search.
		@param org_search org_search
	*/
	public void setorg_search (String org_search)
	{
		set_ValueNoCheck (COLUMNNAME_org_search, org_search);
	}

	/** Get org_search.
		@return org_search	  */
	public String getorg_search()
	{
		return (String)get_Value(COLUMNNAME_org_search);
	}

	/** Set price_list_version.
		@param price_list_version price_list_version
	*/
	public void setprice_list_version (String price_list_version)
	{
		set_ValueNoCheck (COLUMNNAME_price_list_version, price_list_version);
	}

	/** Get price_list_version.
		@return price_list_version	  */
	public String getprice_list_version()
	{
		return (String)get_Value(COLUMNNAME_price_list_version);
	}

	/** Set product_name.
		@param product_name product_name
	*/
	public void setproduct_name (String product_name)
	{
		set_ValueNoCheck (COLUMNNAME_product_name, product_name);
	}

	/** Get product_name.
		@return product_name	  */
	public String getproduct_name()
	{
		return (String)get_Value(COLUMNNAME_product_name);
	}

	/** Set product_name_local.
		@param product_name_local product_name_local
	*/
	public void setproduct_name_local (String product_name_local)
	{
		set_ValueNoCheck (COLUMNNAME_product_name_local, product_name_local);
	}

	/** Get product_name_local.
		@return product_name_local	  */
	public String getproduct_name_local()
	{
		return (String)get_Value(COLUMNNAME_product_name_local);
	}

	/** Set product_search.
		@param product_search product_search
	*/
	public void setproduct_search (String product_search)
	{
		set_ValueNoCheck (COLUMNNAME_product_search, product_search);
	}

	/** Get product_search.
		@return product_search	  */
	public String getproduct_search()
	{
		return (String)get_Value(COLUMNNAME_product_search);
	}

	/** Set On Hand Quantity.
		@param QtyOnHand On Hand Quantity
	*/
	public void setQtyOnHand (BigDecimal QtyOnHand)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOnHand, QtyOnHand);
	}

	/** Get On Hand Quantity.
		@return On Hand Quantity
	  */
	public BigDecimal getQtyOnHand()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOnHand);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Valid from.
		@param ValidFrom Valid from including this date (first day)
	*/
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_ValueNoCheck (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom()
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set warehouse.
		@param warehouse warehouse
	*/
	public void setwarehouse (String warehouse)
	{
		set_ValueNoCheck (COLUMNNAME_warehouse, warehouse);
	}

	/** Get warehouse.
		@return warehouse	  */
	public String getwarehouse()
	{
		return (String)get_Value(COLUMNNAME_warehouse);
	}

	/** Set woo_brand.
		@param woo_brand woo_brand
	*/
	public void setwoo_brand (String woo_brand)
	{
		set_ValueNoCheck (COLUMNNAME_woo_brand, woo_brand);
	}

	/** Get woo_brand.
		@return woo_brand	  */
	public String getwoo_brand()
	{
		return (String)get_Value(COLUMNNAME_woo_brand);
	}

	/** Set woo_enable_reviews.
		@param woo_enable_reviews woo_enable_reviews
	*/
	public void setwoo_enable_reviews (boolean woo_enable_reviews)
	{
		set_ValueNoCheck (COLUMNNAME_woo_enable_reviews, Boolean.valueOf(woo_enable_reviews));
	}

	/** Get woo_enable_reviews.
		@return woo_enable_reviews	  */
	public boolean iswoo_enable_reviews()
	{
		Object oo = get_Value(COLUMNNAME_woo_enable_reviews);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set woo_product_categories.
		@param woo_product_categories woo_product_categories
	*/
	public void setwoo_product_categories (String woo_product_categories)
	{
		set_ValueNoCheck (COLUMNNAME_woo_product_categories, woo_product_categories);
	}

	/** Get woo_product_categories.
		@return woo_product_categories	  */
	public String getwoo_product_categories()
	{
		return (String)get_Value(COLUMNNAME_woo_product_categories);
	}

	/** Set woo_product_description.
		@param woo_product_description woo_product_description
	*/
	public void setwoo_product_description (String woo_product_description)
	{
		set_ValueNoCheck (COLUMNNAME_woo_product_description, woo_product_description);
	}

	/** Get woo_product_description.
		@return woo_product_description	  */
	public String getwoo_product_description()
	{
		return (String)get_Value(COLUMNNAME_woo_product_description);
	}

	/** Set woo_product_image.
		@param woo_product_image woo_product_image
	*/
	public void setwoo_product_image (String woo_product_image)
	{
		set_ValueNoCheck (COLUMNNAME_woo_product_image, woo_product_image);
	}

	/** Get woo_product_image.
		@return woo_product_image	  */
	public String getwoo_product_image()
	{
		return (String)get_Value(COLUMNNAME_woo_product_image);
	}

	/** Set woo_product_name.
		@param woo_product_name woo_product_name
	*/
	public void setwoo_product_name (String woo_product_name)
	{
		set_ValueNoCheck (COLUMNNAME_woo_product_name, woo_product_name);
	}

	/** Get woo_product_name.
		@return woo_product_name	  */
	public String getwoo_product_name()
	{
		return (String)get_Value(COLUMNNAME_woo_product_name);
	}

	/** Set woo_product_short_description.
		@param woo_product_short_description woo_product_short_description
	*/
	public void setwoo_product_short_description (String woo_product_short_description)
	{
		set_ValueNoCheck (COLUMNNAME_woo_product_short_description, woo_product_short_description);
	}

	/** Get woo_product_short_description.
		@return woo_product_short_description	  */
	public String getwoo_product_short_description()
	{
		return (String)get_Value(COLUMNNAME_woo_product_short_description);
	}

	/** Set woo_quantity.
		@param woo_quantity woo_quantity
	*/
	public void setwoo_quantity (BigDecimal woo_quantity)
	{
		set_ValueNoCheck (COLUMNNAME_woo_quantity, woo_quantity);
	}

	/** Get woo_quantity.
		@return woo_quantity	  */
	public BigDecimal getwoo_quantity()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_woo_quantity);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set woo_regular_price.
		@param woo_regular_price woo_regular_price
	*/
	public void setwoo_regular_price (BigDecimal woo_regular_price)
	{
		set_Value (COLUMNNAME_woo_regular_price, woo_regular_price);
	}

	/** Get woo_regular_price.
		@return woo_regular_price	  */
	public BigDecimal getwoo_regular_price()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_woo_regular_price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set woo_sale_price.
		@param woo_sale_price woo_sale_price
	*/
	public void setwoo_sale_price (BigDecimal woo_sale_price)
	{
		set_Value (COLUMNNAME_woo_sale_price, woo_sale_price);
	}

	/** Get woo_sale_price.
		@return woo_sale_price	  */
	public BigDecimal getwoo_sale_price()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_woo_sale_price);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set woo_stock_management.
		@param woo_stock_management woo_stock_management
	*/
	public void setwoo_stock_management (boolean woo_stock_management)
	{
		set_ValueNoCheck (COLUMNNAME_woo_stock_management, Boolean.valueOf(woo_stock_management));
	}

	/** Get woo_stock_management.
		@return woo_stock_management	  */
	public boolean iswoo_stock_management()
	{
		Object oo = get_Value(COLUMNNAME_woo_stock_management);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set woo_trashed.
		@param woo_trashed woo_trashed
	*/
	public void setwoo_trashed (boolean woo_trashed)
	{
		set_ValueNoCheck (COLUMNNAME_woo_trashed, Boolean.valueOf(woo_trashed));
	}

	/** Get woo_trashed.
		@return woo_trashed	  */
	public boolean iswoo_trashed()
	{
		Object oo = get_Value(COLUMNNAME_woo_trashed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}