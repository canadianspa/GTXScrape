package HomeBase;

import java.io.Serializable;

public class Invoices implements Serializable {
	
	String invoiceNo,invDate;

	public Invoices(String invoiceNo, String invDate) {
		super();
		this.invoiceNo = invoiceNo;
		this.invDate = invDate;
	}

	

}
