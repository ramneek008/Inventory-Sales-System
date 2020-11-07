package com.wipro.sales.dao;

import java.sql.*;

import com.wipro.sales.bean.Product;
import com.wipro.sales.util.DBUtil;

public class StockDao {

	public int insertStock(Product sales) throws SQLException
	{
		Connection con = DBUtil.getDBConnection();
		int t=0;
		PreparedStatement ps = con.prepareStatement("insert into HR.TBL_STOCK values(?,?,?,?,?)");
		ps.setString(1,sales.getProductID());
		ps.setString(2,sales.getProductName());
		ps.setInt(3,sales.getQuantityOnHand());
		ps.setDouble(4,sales.getProductUnitPrice());
		ps.setInt(5,sales.getReorderLevel());
		t=ps.executeUpdate();
		return t;
	}
	
	public String generateProductID(String productName) throws SQLException
	{
		Connection con = DBUtil.getDBConnection();
		PreparedStatement ps = con.prepareStatement("select HR.SEQ_PRODUCT_ID.NEXTVAL from dual");
		ResultSet rs = ps.executeQuery();
		String part1 = productName.substring(0,2);
		String productId ="";
		if(rs.next()) {
			productId = "" + part1 + rs.getInt(1);
		}
		return productId;
	}
	
	public int updateStock(String productID, int soldQty) throws SQLException
	{
		Connection con = DBUtil.getDBConnection();
		int t=0;
		PreparedStatement ps = con.prepareStatement("select Quantity_On_Hand from HR.TBL_STOCK where Product_ID = ?");
		ps.setString(1, productID);
		ResultSet rs = ps.executeQuery();
		int curQty=0;
		if(rs.next()){
			curQty = rs.getInt("Quantity_On_Hand");
		}
		ps = con.prepareStatement("update HR.TBL_STOCK set Quantity_On_Hand = ? where Product_ID = ?");
		ps.setInt(1, curQty-soldQty);
		ps.setString(2,productID);
		t=ps.executeUpdate();
		return t;
	}

	public Product getStock(String productID) throws SQLException
	{
		Connection con = DBUtil.getDBConnection();
		PreparedStatement ps = con.prepareStatement("select * from HR.TBL_STOCK where Product_ID = ?");
		ps.setString(1, productID);
		ResultSet rs = ps.executeQuery();
		Product product = new Product();
		if(rs.next()) {
			product.setProductID(rs.getString("Product_ID"));
			product.setProductName(rs.getString("Product_Name"));
			product.setQuantityOnHand(rs.getInt("Quantity_On_Hand"));
			product.setProductUnitPrice(rs.getDouble("Product_Unit_Price"));
			product.setReorderLevel(rs.getInt("Reorder_Level"));
			return product;
		}
		return null;
	}
	
	public int deleteStock(String productID) throws SQLException
	{
		Connection con = DBUtil.getDBConnection();
		PreparedStatement ps = con.prepareStatement("delete from HR.TBL_STOCK where Product_ID = ?");
		ps.setString(1, productID);
		int t= 0;
		t=ps.executeUpdate();
		return t;
	}
}
