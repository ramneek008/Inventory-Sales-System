package com.wipro.sales.main;
import java.sql.SQLException;
import java.util.*;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.dao.StockDao;
import com.wipro.sales.service.Administrator;

public class SalesApplication {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		System.out.println("1. Insert Stock\n2. Delete Stock \n3. Insert Sales\n4. View Sales Report\nEnter your Choice:");
		int choice = sc.nextInt();
		Administrator admin = new Administrator();
		
		switch(choice) {
		case 1:
		{
			Product stockobj = new Product();
			StockDao stockdao = new StockDao();
			System.out.print("Product Name: ");
			stockobj.setProductName(sc.next());
			System.out.println();
			stockobj.setProductID(stockdao.generateProductID(stockobj.getProductName()));
			System.out.print("Quantity On Hand: ");
			stockobj.setQuantityOnHand(sc.nextInt());
			System.out.println();
			System.out.print("Product Unit Price: ");
			stockobj.setProductUnitPrice(sc.nextDouble());
			System.out.println();
			System.out.print("Reorder Level: ");
			stockobj.setReorderLevel(sc.nextInt());
			
			admin.insertStock(stockobj);
			break;
		}
		
		case 2:
		{
			String productId = sc.next();
			admin.deleteStock(productId);
			break;
		}
		
		case 3:
		{
			Sales salesobj = new Sales();
			salesobj.setSalesID(sc.next());
			salesobj.setSalesDate(new Date(new java.util.Date().getTime()));
			salesobj.setProductID(sc.next());
			salesobj.setQuantitySold(sc.nextInt());
			salesobj.setSalesPricePerUnit(sc.nextDouble());
			
			admin.insertSales(salesobj);
			break;
		}
		
		case 4:
		{
			ArrayList<SalesReport> sales = new ArrayList<SalesReport>();
			sales = admin.getSalesReport();
			System.out.println("Sales_ID\t" + "Sales_Date\t" + "Product_ID\t" + "Product_Name\t" + "Quantity_Sold\t" + "Product_Unit_Price\t" + "Sales_Price_Per_Unit\t" + "Profit_Amount");
			for(SalesReport sr : sales) {
				System.out.println(sr.getSalesID() + "\t" + sr.getSalesDate() + "\t" + sr.getProductID() + "\t" + sr.getProductName() + "\t" + sr.getQuantitySold() + "/t" + sr.getProductUnitPrice() + "\t" + sr.getSalesPricePerUnit() + "\t" + sr.getProfitAmount());
			}
			break;
		}
		
		default:
		{
			System.out.println("Invalid input");
			break;
		}
		}
	}

}
