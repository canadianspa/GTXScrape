package BNQ;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;

public class fillInEDA {

	public static void main(String[] args) {

		try {
			FileInputStream fiut = new FileInputStream("all.EDA");
			ObjectInputStream ois;
			ArrayList<EDA> alreadyGot;
			SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
			
			try {
				//cant read first time
				ois = new ObjectInputStream(fiut);
				alreadyGot = (ArrayList<EDA>) ois.readObject();
			} catch (Exception f) {
				alreadyGot = new ArrayList<EDA>();
			}
			
			Collections.sort(alreadyGot, new Comparator<EDA>() {

				@Override
				public int compare(EDA o1, EDA o2) {
					return Integer.parseInt(o2.purchOrderNo)-Integer.parseInt(o1.purchOrderNo);
				}
		       
		    });
			
			
			try {
				System.out.println(alreadyGot.size());
				InputStream inputStream = new FileInputStream ("invoiceTemplate.xls");

				
				POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

				HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);
				CreationHelper createHelper = workBook.getCreationHelper();
				
				HSSFSheet sheet  = workBook.getSheetAt (0);
				sheet.getRow(0).createCell(8).setCellValue(alreadyGot.get(0).purchOrderNo);
				
				int cRow =1;
				
				for(EDA a : alreadyGot)
				{
					Row row;
					if(cRow ==1)
					{
						row = sheet.getRow(cRow);
					}
					else
					{
						row = sheet.createRow(cRow);
					}
					row.createCell(0).setCellValue(a.purchOrderNo);
					
					CellStyle cellStyle = workBook.createCellStyle();
				    cellStyle.setDataFormat(
				    createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				    Cell cell = row.createCell(1);
					cell.setCellValue(a.delDate);
				
				    cell.setCellStyle(cellStyle);
					row.createCell(2).setCellValue(a.custName);
					
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
