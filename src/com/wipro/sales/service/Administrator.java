package com.wipro.sales.service;

import java.sql.*;
import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.dao.SalesDao;
import com.wipro.sales.dao.StockDao;
import com.wipro.sales.util.DBUtil;

public class Administrator {

	public String insertStock(Product stockobj) throws SQLException
	{
		if(stockobj==null) {
			System.out.println("Null values can't be inserted");
		}
		else if(stockobj.getProductName().length()<2) {
			System.out.println("Product Name should be of minimum 2 letters");
		}
		else {
			StockDao stockdao = new StockDao();
			String productId = stockdao.generateProductID(stockobj.getProductName());
			stockobj.setProductID(productId);
			int t = stockdao.insertStock(stockobj);
			return productId;
		}
		return "Data not valid for insertion";
	}
	
	public String deleteStock(String productID) throws SQLException
	{
		StockDao stockdao = new StockDao();
		int t = stockdao.deleteStock(productID);
		if(t==1)
			return "Deleted";
		else
			return "Record cannot be deleted";
	}
	
	public String insertSales(Sales salesobj) throws SQLException
	{		
		StockDao stockdao = new StockDao();
		if(salesobj == null) {
			return "Object not valid for insertion";
		}
		else if(stockdao.getStock(salesobj.getProductID()) == null)
		{
			return "Unknown Product for sales";
		}

		Connection con = DBUtil.getDBConnection();
		PreparedStatement ps = con.prepareStatement("select * from TBL_STOCK where Product_ID = ?");
		ps.setString(1, salesobj.getProductID());
		ResultSet rs = ps.executeQuery();
		int qtyonhold = 0;
		if(rs.next())
			qtyonhold=rs.getInt("Quantity_On_Hold");
		
		ps = con.prepareStatement("select * from TBL_SALES where Product_ID = ?");
		ps.setString(1, salesobj.getProductID());
		rs = ps.executeQuery();
		int qtysold = 0;
		if(rs.next())
			qtysold=rs.getInt("Quantity_Sold");
		
		if(qtysold>qtyonhold)
			return "Not enough stock on hand for sales";
		else {
			SalesDao salesdao = new SalesDao();
			salesdao.insertSales(salesobj);
			return "Insertion succesful";
		}
	}
}