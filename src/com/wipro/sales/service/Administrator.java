package com.wipro.sales.service;

import java.sql.*;
import java.util.ArrayList;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.dao.SalesDao;
import com.wipro.sales.dao.StockDao;
import com.wipro.sales.util.DBUtil;

public class Administrator {

	public String insertStock(Product stockobj) throws SQLException
	{
		if(stockobj==null) {
			System.out.println("Null values can't be inserted.");
		}
		else if(stockobj.getProductName().length()<2) {
			System.out.println("Product Name should be of minimum 2 letters.");
		}
		else {
			StockDao stockdao = new StockDao();
			String productId = stockdao.generateProductID(stockobj.getProductName());
			stockobj.setProductID(productId);
			int t = stockdao.insertStock(stockobj);
			if(t==1)
			{
				System.out.print("Insertion succesfull. Generated Product ID: ");
				return productId;	
			}
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
		PreparedStatement ps = con.prepareStatement("select Quantity_On_Hand from HR.TBL_STOCK where Product_ID = ?");
		ps.setString(1, salesobj.getProductID());
		ResultSet rs = ps.executeQuery();
		int qtyonhand = 0;
		if(rs.next())
			qtyonhand=rs.getInt("Quantity_On_Hand");
		if(salesobj.getQuantitySold()>qtyonhand)
			return "Not enough stock on hand for sales";
		
		java.util.Date date = new Date(salesobj.getSalesDate().getTime());
		java.util.Date curDate = new Date(new java.util.Date().getTime());
		System.out.println(date);
		System.out.println(curDate);
		
		if(date.compareTo(curDate)>0) {
			return "Invalid date";
		}
		
		else {
			SalesDao salesdao = new SalesDao();
			String salesId = salesdao.generateSalesID(salesobj.getSalesDate());
			salesobj.setSalesID(salesId);
			int t = salesdao.insertSales(salesobj);
			if(t==1) {
				int t2 = stockdao.updateStock(salesobj.getProductID(), salesobj.getQuantitySold());
				if(t2==1) {
					return "Sales Completed";
				}
			}
			return "Error";
		}
	}
	
	public ArrayList<SalesReport> getSalesReport() throws SQLException
	{
		SalesDao salesdao = new SalesDao();
		return salesdao.getSalesReport();		
	}
}
