package BNQ;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;

public class fillInEDA {

	public static void main(String[] args) {

		try {
			FileInputStream fiut = new FileInputStream("all.EDA");
			ObjectInputStream ois;
			ArrayList<EDA> alreadyGot;
			
			try {
				//cant read first time
				ois = new ObjectInputStream(fiut);
				alreadyGot = (ArrayList<EDA>) ois.readObject();
			} catch (Exception f) {
				alreadyGot = new ArrayList<EDA>();
			}
			
			try {
				System.out.println(alreadyGot.size());
				InputStream inputStream = new FileInputStream ("invoiceTemplate.xls");

				
				POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

				HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);
				
				HSSFSheet sheet  = workBook.getSheetAt (0);
				
				int cRow =1;
				for(EDA a : alreadyGot)
				{
					Row row = sheet.createRow(cRow);
					row.createCell(0).setCellValue(a.purchOrderNo);
					row.createCell(1).setCellValue(a.delDate);
					
					cRow+=1;
				}
				
				FileOutputStream fileOut1 = new FileOutputStream("invoice.xls");
				workBook.write(fileOut1);
				fileOut1.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
