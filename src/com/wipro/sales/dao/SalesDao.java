package com.wipro.sales.dao;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.util.DBUtil;

public class SalesDao {
	

	public int insertSales(Sales sales) throws SQLException
	{
		Connection con = DBUtil.getDBConnection();
		int t=0;
		PreparedStatement ps = con.prepareStatement("insert into HR.TBL_SALES values(?,?,?,?,?)");
		ps.setString(1,sales.getSalesID());
		java.sql.Date sqlDate = new java.sql.Date(sales.getSalesDate().getTime());
		ps.setDate(2, sqlDate);
		ps.setString(3,sales.getProductID());
		ps.setInt(4, sales.getQuantitySold());
		ps.setDouble(5, sales.getSalesPricePerUnit());
		t=ps.executeUpdate();
		return t;
	}
	
	public String generateSalesID(java.util.Date salesDate) throws SQLException
	{
		Connection con = DBUtil.getDBConnection();
		PreparedStatement ps = con.prepareStatement("select HR.SEQ_SALES_ID.NEXTVAL from dual");
		ResultSet rs = ps.executeQuery();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		String sdate = dateFormat.format(salesDate);
		String year = sdate.substring(2,4);
		System.out.println(year);
		String salesId = "";
		
		if(rs.next()) {
			salesId =  "" + year + rs.getInt(1);
		}
		
		return salesId;
		
	}
	
	public ArrayList<SalesReport> getSalesReport() throws SQLException
	{
		Connection con = DBUtil.getDBConnection();
		PreparedStatement ps = con.prepareStatement("select * from HR.V_SALES_REPORT");
		ResultSet rs = ps.executeQuery();
		ArrayList<SalesReport> sales = new ArrayList<SalesReport>();
		
		while(rs.next()) {
			SalesReport salrep = new SalesReport();
			
			salrep.setSalesID(rs.getString("Sales_ID"));
			salrep.setSalesDate(rs.getDate("Sales_Date"));
			salrep.setProductID(rs.getString("Product_ID"));
			salrep.setProductName(rs.getString("Product_Name"));
			salrep.setQuantitySold(rs.getInt("Quantity_Sold"));
			salrep.setProductUnitPrice(rs.getInt("Product_Unit_Price"));
			salrep.setSalesPricePerUnit(rs.getDouble("Sales_Price_Per_Unit"));
			salrep.setProfitAmount(rs.getDouble("Profit_Amount"));
			
			sales.add(salrep);
		}
		
		return sales;
	}
}
