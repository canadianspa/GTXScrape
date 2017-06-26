package HomeBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

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
		try {
			InputStream inputStream = new FileInputStream ("homebasetemplate.xls");
			POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

			HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);

			HSSFSheet sheet  = workBook.getSheetAt (1);
			sheet.getRow(4).getCell(7).setCellValue("CanadianSpaCompany");
			sheet.getRow(3).getCell(7).setCellValue("CanadianSpaCompany");
			
			
			int cRow = 7;
			for(int z = 0; z < listOfReports.size(); z ++)
			{
				System.out.println(listOfReports.size());
				Row row = sheet.getRow(cRow);
				StatusReport cReport = listOfReports.get(z);
				row.getCell(1).setCellValue(cReport.orderNumber);
				row.getCell(2).setCellValue("C30");
				row.getCell(3).setCellValue(cReport.transactionDate);
				row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(6).setCellValue(cReport.originalCustomerOrderNumber);
				
				cRow +=1;
				
			}
			FileOutputStream fileOut1 = new FileOutputStream(listOfReports.get(0).orderNumber + " to " + listOfReports.get(listOfReports.size() -1).orderNumber + ".xls");
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
