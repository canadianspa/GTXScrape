package InvoiceScrape;

import java.util.ArrayList;

import BNQ.EDA;

public class Test {

	public static void main(String[] args) {

		ArrayList<Invoices> test = new ArrayList<Invoices>();
		test.add(new Invoices("1","1","1","1","1"));
 		Invoices.addToInvoiceList(test);
	}

}
