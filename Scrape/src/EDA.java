import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class EDA {
	
	String seqNo, storeCode,purchOrderNo,custTellNo1,bqSuppNo,custName;
	Date delDate,dateOrderPlaced;
	ArrayList<String> eanCode1 = new ArrayList<String>();
	ArrayList<String> desc1 = new ArrayList<String>();
	ArrayList<String> qty1 = new ArrayList<String>();
	boolean[] readyToSend = new boolean[2];
	String homestore = "HOME";
	String poVerNo = "00001";
	
	//no del date on the site
	public EDA(String storeCode, String purchOrderNo, String custTellNo1, String bqSuppNo,
			String custName, ArrayList<String> eanCode1, ArrayList<String> desc1, ArrayList<String> qty1,
			Date dateOrderPlaced) {
		super();
		this.storeCode = storeCode;
		this.purchOrderNo = purchOrderNo;
		this.custTellNo1 = custTellNo1;
		this.bqSuppNo = bqSuppNo;
		this.custName = custName;
		this.eanCode1 = eanCode1;
		this.desc1 = desc1;
		this.qty1 = qty1;
		this.dateOrderPlaced = dateOrderPlaced;
		Arrays.fill(readyToSend, Boolean.FALSE);
		
	}
	
	public void addSeqNO(String seqNO)
	{
		readyToSend[0] = true;
	}
	
	public void addDelDate()
	{
		readyToSend[1] = true;
	}
	
	public void createCSV()
	{
		
	}
	
	
}
