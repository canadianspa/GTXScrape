package InvoiceScrape;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

public class Invoices {

	String invoiceNo,purchOrNo,invDate;

	public Invoices(String invoiceNo, String purchOrNo, String invDate) {
		super();
		this.invoiceNo = invoiceNo;
		this.purchOrNo = purchOrNo;
		this.invDate = invDate;
	}

	public static void createInvoices(ArrayList<Invoices> listOfInvoices)
	{
		try {
			InputStream inputStream = new FileInputStream ("invoiceTemplate.xls");
			POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

			HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);
			
			HSSFSheet sheet  = workBook.getSheetAt (0);
			int cRow = 1;
			for(int z = 0; z < listOfInvoices.size(); z ++)
			{
				System.out.println(listOfInvoices.size());
				Row row = sheet.createRow(cRow);
				Invoices cInvoice = listOfInvoices.get(z);
				row.createCell(0).setCellValue(cInvoice.invDate);
				row.createCell(1).setCellValue(cInvoice.invoiceNo);
				row.createCell(2).setCellValue(cInvoice.purchOrNo);
				cRow +=1;
				
			}
			FileOutputStream fileOut1 = new FileOutputStream(listOfInvoices.get(0).purchOrNo + " to " + listOfInvoices.get(listOfInvoices.size() -1).purchOrNo + ".xls");
            workBook.write(fileOut1);
            fileOut1.close();
            System.out.println("status report created");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
