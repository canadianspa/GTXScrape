package HomeBase;

import java.util.ArrayList;

public class StatusReport {

	String orderNumber,transactionDate,originalCustomerOrderNumber;

	public StatusReport(String orderNumber, String transactionDate,
			String originalCustomerOrderNumber) {
		super();
		this.orderNumber = orderNumber;
		this.transactionDate = transactionDate;
		this.originalCustomerOrderNumber = originalCustomerOrderNumber;
	} 
	
	public static void createStatusReport(ArrayList<StatusReport> listOfReports)
	{
		
	}
	
}
