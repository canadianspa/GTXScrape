package HomeBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;

public class HomeBaseInvoicePayer {

	public static HashMap<String,Boolean> checkPayments() {
		
		HashMap<String,Boolean> invoices = new HashMap<String,Boolean>();
			try {
				InputStream inputStream = new FileInputStream ("HomeBaseTransactions.xls");
				POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

				HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);

				HSSFSheet sheet  = workBook.getSheetAt(1);
				CreationHelper createHelper = workBook.getCreationHelper();

				for(int i = 2; i < sheet.getPhysicalNumberOfRows() ; i ++ )
				{
					try {
						Row row = sheet.getRow(i);
						String po = row.getCell(3).getStringCellValue();
						double money = row.getCell(7).getNumericCellValue();
						if(money == 0)
						{
							invoices.put(po, true);
						}
						else
						{
							invoices.put(po, false);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				inputStream.close();
				
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return invoices;

	}

}
