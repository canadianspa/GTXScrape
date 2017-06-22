import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



public class EDA {
	public static String seqNo = "171";
	String storeCode,purchOrderNo,custTellNo1,bqSuppNo,custName;
	String delDate,dateOrderPlaced;
	String custAdd1,custAdd2,custAdd3,custAdd4,custPostCode;
	ArrayList<String> eanCode1 = new ArrayList<String>();
	ArrayList<String> desc1 = new ArrayList<String>();
	ArrayList<String> qty1 = new ArrayList<String>();
	boolean readyToSend = false;
	String homestore = "HOME";
	String poVerNo = "00001";

	//no del date on the site
	public EDA(String storeCode, String purchOrderNo, String custTellNo1, String bqSuppNo,
			String custName, ArrayList<String> eanCode1, ArrayList<String> desc1, ArrayList<String> qty1,
			String dateOrderPlaced, String delDate,String custAdd1,String custAdd2,String custAdd3,String custAdd4,String custPostCode) {
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
		this.custAdd1 = custAdd1;
		this.custAdd2 = custAdd2;
		this.custAdd3 = custAdd3;
		this.custAdd4 = custAdd4;
		this.custPostCode = custPostCode;

	}

	
	public void createXLS() 
	{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("BNQ EDA"); 
		
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("SeqNo");
		row.createCell(1).setCellValue("Storecode");
		row.createCell(2).setCellValue("PurchOrderNo");
		row.createCell(3).setCellValue("CustTelNo1");
		row.createCell(4).setCellValue("CustTelNo2");
		row.createCell(5).setCellValue("DateOrderPlaced");
		row.createCell(6).setCellValue("DelDate");
		row.createCell(7).setCellValue("BQSuppNo");
		row.createCell(8).setCellValue("DelAdvNoteNo");
		row.createCell(9).setCellValue("CustName");
		row.createCell(10).setCellValue("CustAdd1");
		row.createCell(11).setCellValue("CustAdd2");
		row.createCell(12).setCellValue("CustAdd3");
		row.createCell(13).setCellValue("CustAdd4");
		row.createCell(14).setCellValue("CustPostCode");
		row.createCell(15).setCellValue("BQCode1");
		row.createCell(16).setCellValue("EanCode1");
		row.createCell(17).setCellValue("Desc1");
		row.createCell(18).setCellValue("Qty1");
		row.createCell(19).setCellValue("SectionNo");
		row.createCell(20).setCellValue("OrderComplete");
		row.createCell(21).setCellValue("PoVerNo");
		row.createCell(22).setCellValue("AsnRefNo");
		row.createCell(23).setCellValue("FinalDelFlag");
		row.createCell(24).setCellValue("FreeOfChargeItem");
		row.createCell(25).setCellValue("Home/StoreDelivery");
		
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("4321");
		row.createCell(1).setCellValue("'Storecode");
		row.createCell(2).setCellValue("'PurchOrderNo");
		row.createCell(3).setCellValue("'CustTelNo1");
		row.createCell(4).setCellValue("'CustTelNo2");
		row.createCell(5).setCellValue("29/11/2000");
		row.createCell(6).setCellValue("02/02/2001");
		row.createCell(7).setCellValue("200054");
		row.createCell(8).setCellValue("'DelAdvNoteNo");
		row.createCell(9).setCellValue("'CustName");
		row.createCell(10).setCellValue("'CustAdd1");
		row.createCell(11).setCellValue("'CustAdd2");
		row.createCell(12).setCellValue("'CustAdd3");
		row.createCell(13).setCellValue("'CustAdd4");
		row.createCell(14).setCellValue("'CustPostCode");
		row.createCell(15).setCellValue("'BQCode1");
		row.createCell(16).setCellValue("'EanCode1");
		row.createCell(17).setCellValue("'Desc1");
		row.createCell(18).setCellValue("'Qty1");
		row.createCell(19).setCellValue("'SectionNo");
		row.createCell(20).setCellValue("'OrderComplete");
		row.createCell(21).setCellValue("'PoVerNo");
		row.createCell(22).setCellValue("'AsnRefNo");
		row.createCell(23).setCellValue("'FinalDelFlag");
		row.createCell(24).setCellValue("'FreeOfChargeItem");
		row.createCell(25).setCellValue("'Home/StoreDelivery");
		
		for (int i = 2; i - 2< eanCode1.size(); i ++) {
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(EDA.seqNo);
			row.createCell(1).setCellValue(storeCode);
			row.createCell(2).setCellValue(purchOrderNo);
			row.createCell(3).setCellValue(custTellNo1);
			row.createCell(4).setCellValue("");
			row.createCell(5).setCellValue(dateOrderPlaced);
			row.createCell(6).setCellValue(delDate);
			row.createCell(7).setCellValue(bqSuppNo);
			row.createCell(8).setCellValue("");
			row.createCell(9).setCellValue(custName);
			row.createCell(10).setCellValue(custAdd1);
			row.createCell(11).setCellValue(custAdd2);
			row.createCell(12).setCellValue(custAdd3);
			row.createCell(13).setCellValue(custAdd4);
			row.createCell(14).setCellValue(custPostCode);
			row.createCell(15).setCellValue("");
			row.createCell(16).setCellValue(eanCode1.get(i-2));
			row.createCell(17).setCellValue(desc1.get(i-2));
			row.createCell(18).setCellValue(qty1.get(i-2 ));
			row.createCell(19).setCellValue("1");
			row.createCell(20).setCellValue("");
			row.createCell(21).setCellValue("00001");
			row.createCell(22).setCellValue(purchOrderNo + "00001");
			row.createCell(23).setCellValue("");
			row.createCell(24).setCellValue("NO");
			row.createCell(25).setCellValue("HOME");



		}
		try (FileOutputStream outputStream = new FileOutputStream(purchOrderNo + ".xls")) {
			workbook.write(outputStream);
			System.out.println("created EDA for " + purchOrderNo);
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}






}
