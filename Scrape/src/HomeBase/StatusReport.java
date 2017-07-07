package HomeBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class StatusReport {

	String orderNumber,transactionDate,originalCustomerOrderNumber,status;

	public StatusReport(String orderNumber, String transactionDate,
			String originalCustomerOrderNumber) {
		super();
		this.orderNumber = orderNumber;
		this.transactionDate = transactionDate;
		this.originalCustomerOrderNumber = originalCustomerOrderNumber;
		status = "C30";
	} 
	
	public StatusReport(String orderNumber, String transactionDate,
			String originalCustomerOrderNumber, String status) {
		super();
		this.orderNumber = orderNumber;
		this.transactionDate = transactionDate;
		this.originalCustomerOrderNumber = originalCustomerOrderNumber;
		this.status = status;
	} 

	public static void createStatusReport(CopyOnWriteArrayList<StatusReport> listOfReports)
	{
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");

		try {
			InputStream inputStream = new FileInputStream ("HOMEBASE STATUS UPDATE.xls");
			POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

			HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);

			HSSFSheet sheet  = workBook.getSheetAt(1);


			//find C30 and keepem
			for(int i = 0; i < sheet.getPhysicalNumberOfRows() -7;i++)
			{
				Row row = sheet.getRow(i + 7);
				//if(row.getCell(2).getStringCellValue().equals("C30"))
				//{

				if(row.getCell(2).getStringCellValue().equals(""))
				{
					break;
				}
				else
				{
					String orderNumber,transactionDate,originalCustomerOrderNumber,status;
					orderNumber = row.getCell(1).getStringCellValue();
					status = row.getCell(2).getStringCellValue();
					transactionDate = form.format(row.getCell(3).getDateCellValue());
					originalCustomerOrderNumber = row.getCell(6).getStringCellValue();
					StatusReport C30 =  new StatusReport(orderNumber,transactionDate,originalCustomerOrderNumber,status);
					listOfReports.add(C30);
				}



				//}

			}
			inputStream.close();

			inputStream = new FileInputStream ("homebasetemplate.xls");
			fileSystem = new POIFSFileSystem (inputStream);

			workBook = new HSSFWorkbook (fileSystem);

			sheet  = workBook.getSheetAt (1);

			int cRow = 7;
			for(int z = 0; z < listOfReports.size(); z ++)
			{
				
				Row row = sheet.getRow(cRow);
				StatusReport cReport = listOfReports.get(z);
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(1).setCellValue(cReport.orderNumber);
				row.getCell(2).setCellValue(cReport.status);
				try {
					row.getCell(3).setCellValue(form.parse(cReport.transactionDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
				row.getCell(6).setCellValue(cReport.originalCustomerOrderNumber);

				cRow +=1;

			}

			HSSFFormulaEvaluator.evaluateAllFormulaCells(workBook);
			FileOutputStream fileOut1 = new FileOutputStream("HOMEBASE STATUS UPDATE.xls");
			workBook.write(fileOut1);

			fileOut1.flush();
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
