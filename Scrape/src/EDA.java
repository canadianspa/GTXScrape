import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class EDA {
	public static String seqNo = "1";
	
	private static final String FILE_HEADER = "SeqNo,Storecode,PurchOrderNo,CustTelNo1,CustTelNo2,DateOrderPlaced,DelDate,BQSuppNo,DelAdvNoteNo,CustName,CustAdd1,CustAdd2,CustAdd3,CustAdd4,CustPostCode,BQCode1,EanCode1,Desc1,Qty1,SectionNo,OrderComplete,PoVerNo,AsnRefNo,FinalDelFlag,FreeOfChargeItem,Home/StoreDelivery";
    private static final String FILE_HEADER2 = "4321,Storecode,PurchOrderNo,CustTelNo1,CustTelNo2,29/11/2000,02/02/2001,200054,DelAdvNoteNo,CustName,CustAdd1,CustAdd2,CustAdd3,CustAdd4,CustPostCode,BQCode1,EanCode1,Desc1,Qty1,SectionNo,OrderComplete,PoVerNo,AsnRefNo,FinalDelFlag,FreeOfChargeItem,Home/StoreDelivery";
	String storeCode,purchOrderNo,custTellNo1,bqSuppNo,custName;
	String delDate,dateOrderPlaced;
	ArrayList<String> eanCode1 = new ArrayList<String>();
	ArrayList<String> desc1 = new ArrayList<String>();
	ArrayList<String> qty1 = new ArrayList<String>();
	boolean readyToSend = false;
	String homestore = "HOME";
	String poVerNo = "00001";
	
	//no del date on the site
	public EDA(String storeCode, String purchOrderNo, String custTellNo1, String bqSuppNo,
			String custName, ArrayList<String> eanCode1, ArrayList<String> desc1, ArrayList<String> qty1,
			String dateOrderPlaced, String delDate) {
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
		this.delDate = delDate;
		
	}

	
	
	public void createCSV()
	{
		if(readyToSend = true)
		{
			try {
				FileWriter fileWriter = new FileWriter(purchOrderNo + ".csv");
				fileWriter.append(FILE_HEADER.toString() + System.lineSeparator());
				fileWriter.append(FILE_HEADER2.toString()+ System.lineSeparator());
				for(int i = 0; i < eanCode1.size(); i ++)
				{
					fileWriter.append(EDA.seqNo + ",");
					fileWriter.append(storeCode + ",");
					fileWriter.append(purchOrderNo + ",");
					fileWriter.append(custTellNo1 + ",");
					fileWriter.append(",");
					fileWriter.append(dateOrderPlaced + ",");
					fileWriter.append(delDate + ",");
					fileWriter.append(bqSuppNo + ",");
					fileWriter.append(",");
					fileWriter.append(custName + ",");
					fileWriter.append(",");
					fileWriter.append(",");
					fileWriter.append(",");
					fileWriter.append(",");	
					fileWriter.append(",");
					fileWriter.append(",");
					fileWriter.append(eanCode1.get(i) + ",");
					fileWriter.append(desc1.get(i) + ",");
					fileWriter.append(qty1.get(i) + ",");
					fileWriter.append("1" + ",");
					fileWriter.append(",");
					fileWriter.append("00001" + ",");
					fileWriter.append(purchOrderNo + "00001" + ",");
					fileWriter.append(",");
					fileWriter.append("NO" + ",");
					fileWriter.append("HOME" + ",");
					
					fileWriter.append(System.lineSeparator());
				}
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EDA.seqNo += 1;
			
		}
	}
	
	
}
