package BNQ;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



public class EDA implements Serializable {
	String seqNo,storeCode,purchOrderNo,custTellNo1,bqSuppNo,custName;
	String delDate,dateOrderPlaced;
	String custAdd1,custAdd2,custAdd3,custAdd4,custPostCode;
	ArrayList<String> eanCode1 = new ArrayList<String>();
	ArrayList<String> desc1 = new ArrayList<String>();
	ArrayList<String> qty1 = new ArrayList<String>();
	String salesNumber;
	String homestore = "HOME";
	String poVerNo = "00001";

	//no del date on the site
	public EDA(String seqNo, String storeCode, String purchOrderNo, String custTellNo1, String bqSuppNo,
			String custName, ArrayList<String> eanCode1, ArrayList<String> desc1, ArrayList<String> qty1,
			String dateOrderPlaced, String delDate,String custAdd1,String custAdd2,String custAdd3,String custAdd4,String custPostCode,String salesNumber) {
		super();
		this.seqNo = seqNo;
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
		this.salesNumber = salesNumber;

	}
	//TODO use this to make sure u dont double up dudes
	public static void addToEDAList(ArrayList<EDA> EDAs)
	{
		try {
			FileInputStream fiut = new FileInputStream("all.EDA");
			ObjectInputStream ois;
			ArrayList<EDA> alreadyGot;
			try {
				//cant read first time
				ois = new ObjectInputStream(fiut);
				alreadyGot = (ArrayList<EDA>) ois.readObject();
			} catch (Exception e) {
				alreadyGot = new ArrayList<EDA>();
			}
			System.out.println("before");
			for(EDA e : alreadyGot)
			{
				System.out.println(e.purchOrderNo);
			}
			alreadyGot.addAll(EDAs);
			System.out.println("after");

			for(EDA e : alreadyGot)
			{
				System.out.println(e.purchOrderNo);
			}
			
			
			FileOutputStream fout = new FileOutputStream("all.EDA");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(alreadyGot);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//TODO cust tel should be sales number for some dumb ass reason
	public static void createAllXLS(ArrayList<EDA> EDAs)
	{
		int cRow = 2;
		EDA cEDA;
		try {
			InputStream inputStream = new FileInputStream ("template.xls");
            POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

            HSSFWorkbook      workBook = new HSSFWorkbook (fileSystem);

			HSSFSheet  sheet  = workBook.getSheetAt (0);
			
			for(int z = 0; z < EDAs.size(); z ++)
			{
				cEDA = EDAs.get(z);
				for(int i = 0; i < cEDA.eanCode1.size(); i ++)
				{
					Row row = sheet.createRow(cRow);
					row.createCell(0).setCellValue(cEDA.seqNo);
					row.createCell(1).setCellValue(cEDA.storeCode);
					row.createCell(2).setCellValue(cEDA.purchOrderNo);
					row.createCell(3).setCellValue(cEDA.salesNumber);
					row.createCell(4).setCellValue("");
					row.createCell(5).setCellValue(cEDA.dateOrderPlaced);
					row.createCell(6).setCellValue(cEDA.delDate);
					row.createCell(7).setCellValue(cEDA.bqSuppNo);
					row.createCell(8).setCellValue("");
					row.createCell(9).setCellValue(cEDA.custName);
					row.createCell(10).setCellValue(cEDA.custAdd1);
					row.createCell(11).setCellValue(cEDA.custAdd2);
					row.createCell(12).setCellValue(cEDA.custAdd3);
					row.createCell(13).setCellValue(cEDA.custAdd4);
					row.createCell(14).setCellValue(cEDA.custPostCode);
					row.createCell(15).setCellValue("");
					row.createCell(16).setCellValue(cEDA.eanCode1.get(i));
					row.createCell(17).setCellValue(cEDA.desc1.get(i));
					row.createCell(18).setCellValue(cEDA.qty1.get(i));
					row.createCell(19).setCellValue("1");
					row.createCell(20).setCellValue("");
					row.createCell(21).setCellValue("00001");
					row.createCell(22).setCellValue(cEDA.purchOrderNo + "00001");
					row.createCell(23).setCellValue("");
					row.createCell(24).setCellValue("NO");
					row.createCell(25).setCellValue("HOME");
					cRow += 1;
				}
			}
			
			FileOutputStream fileOut1 = new FileOutputStream(EDAs.get(0).purchOrderNo + " to " + EDAs.get(EDAs.size() -1).purchOrderNo + ".xls");
            workBook.write(fileOut1);
            fileOut1.close();
            System.out.println(EDAs.get(0).purchOrderNo + " to " + EDAs.get(EDAs.size() -1).purchOrderNo + ".xls created");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void createXLS() 
	{
		
		try {
			InputStream inputStream = new FileInputStream ("template.xls");
            POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

            HSSFWorkbook      workBook = new HSSFWorkbook (fileSystem);

			HSSFSheet  sheet  = workBook.getSheetAt (0);
			
			for (int i = 2; i - 2< eanCode1.size(); i ++) {
				Row row = sheet.createRow(i);
				row.createCell(0).setCellValue(seqNo);
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
			
			FileOutputStream fileOut1 = new FileOutputStream(purchOrderNo + ".xls");
            workBook.write(fileOut1);
            fileOut1.close();
            System.out.println(purchOrderNo + " EDA created");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}






}
