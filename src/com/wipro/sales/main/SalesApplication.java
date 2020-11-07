package com.wipro.sales.main;
import java.text.SimpleDateFormat;
import java.util.*;

import com.wipro.sales.bean.Product;
import com.wipro.sales.bean.Sales;
import com.wipro.sales.bean.SalesReport;
import com.wipro.sales.dao.SalesDao;
import com.wipro.sales.service.Administrator;

public class SalesApplication {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		Administrator admin = new Administrator();
		int choice =0;
		do
		{
			System.out.println("1. Insert Stock\n2. Delete Stock \n3. Insert Sales\n4. View Sales Report\n5. Exit\nEnter your Choice:");
			choice = sc.nextInt();
			switch(choice) {
			case 1:
			{
				Product stockobj = new Product();
				System.out.print("Product Name: ");
				stockobj.setProductName(sc.next());
				System.out.print("Quantity On Hand: ");
				stockobj.setQuantityOnHand(sc.nextInt());
				System.out.print("Product Unit Price: ");
				stockobj.setProductUnitPrice(sc.nextDouble());
				System.out.print("Reorder Level: ");
				stockobj.setReorderLevel(sc.nextInt());
				
				System.out.println(admin.insertStock(stockobj));
				break;
			}
			
			case 2:
			{
				System.out.print("Product ID: ");
				String productId = sc.next();
				System.out.println(admin.deleteStock(productId));
				break;
			}
			
			case 3:
			{
				Sales salesobj = new Sales();
				SalesDao salesdao = new SalesDao();
				System.out.print("Enter date (dd/mm/yyyy): ");
				String sdate = sc.next();
				Date date1 = new SimpleDateFormat("dd/mm/yyyy").parse(sdate);
				salesobj.setSalesDate(date1);
				salesobj.setSalesID(salesdao.generateSalesID(date1));
				System.out.print("Product ID: ");
				salesobj.setProductID(sc.next());
				System.out.print("Quantity Sold: ");
				salesobj.setQuantitySold(sc.nextInt());
				System.out.print("Sales Price Per Unit: ");
				salesobj.setSalesPricePerUnit(sc.nextDouble());
				
				System.out.println(admin.insertSales(salesobj));
				break;
			}
			
			case 4:
			{
				ArrayList<SalesReport> sales = new ArrayList<SalesReport>();
				sales = admin.getSalesReport();
				System.out.println("Sales_ID\t" + "Sales_Date\t" + "Product_ID\t" + "Product_Name\t" + "Quantity_Sold\t" + "Product_Unit_Price\t" + "Sales_Price_Per_Unit\t" + "Profit_Amount");
				for(SalesReport sr : sales) {
					System.out.println(sr.getSalesID() + "\t\t" + sr.getSalesDate() + "\t" + sr.getProductID() + "\t\t" + sr.getProductName() + "\t" + sr.getQuantitySold() + "\t\t" + sr.getProductUnitPrice() + "\t\t\t" + sr.getSalesPricePerUnit() + "\t\t\t" + sr.getProfitAmount());
				}
				break;
			}
			
			case 5:
			{
				System.out.println("Ended");
				break;
			}
			
			default:
			{
				System.out.println("Invalid input.");
				break;
			}
			
			}
			System.out.println();
		}while(choice!=5);
		
		sc.close();
	}

}
