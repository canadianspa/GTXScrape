package InvoiceScrape;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Invoices {

	String invoiceNo,purchOrNo,invDate,delDate,amountOut;
	
	public Invoices(String invoiceNo, String purchOrNo, String invDate, String delDate, String amountOut) {
		super();
		this.invoiceNo = invoiceNo;
		this.purchOrNo = purchOrNo;
		this.invDate = invDate;
		this.delDate = delDate;
		this.amountOut = amountOut;
	}


	public static void createInvoices(ArrayList<Invoices> listOfInvoices)
	{
		
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");

		try {
			InputStream inputStream = new FileInputStream ("BNQ.xls");
			POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

			HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);
			
			HSSFSheet sheet  = workBook.getSheetAt (0);
			for(int i = 24; i < sheet.getPhysicalNumberOfRows(); i ++ )
			{
				try {
					Row row = sheet.getRow(i);
					String po = String.valueOf(row.getCell(5).getNumericCellValue());
					System.out.println(po);
					for(Invoices z : listOfInvoices)
					{
						if(z.purchOrNo.equals(po))
						{
							try {
								row.getCell(7).setCellValue(form.parse(z.invDate));
								System.out.println("adding inv date");
							} catch (ParseException e) {
								
							}
						}
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			}
			
			FileOutputStream fileOut1 = new FileOutputStream(listOfInvoices.get(0).purchOrNo + " to " + listOfInvoices.get(listOfInvoices.size() -1).purchOrNo + ".xls");
            workBook.write(fileOut1);
            fileOut1.close();
            System.out.println("invoice report created");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
