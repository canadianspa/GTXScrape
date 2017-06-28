package BNQ;
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

public class EDC {

	EDA start;
	String actualDeliveryDate;
	String delAdvNoteNo;


	public EDC(EDA start, String actualDeliveryDate, String delAdvNoteNo) {
		super();
		this.start = start;
		this.actualDeliveryDate = actualDeliveryDate;
		this.delAdvNoteNo = delAdvNoteNo;
	}


	public static void createEDCXLS(ArrayList<EDC> EDCs)
	{
		int cRow = 2;
		EDA cEDA;
		EDC cEDC;
		try {
			InputStream inputStream = new FileInputStream ("template.xls");
			POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

			HSSFWorkbook      workBook = new HSSFWorkbook (fileSystem);

			HSSFSheet  sheet  = workBook.getSheetAt (0);

			for(int z = 0; z < EDCs.size(); z ++)
			{
				cEDC = EDCs.get(z);
				cEDA = cEDC.start;
				for(int i = 0; i < cEDA.eanCode1.size(); i ++)
				{
					Row row = sheet.createRow(cRow);
					row.createCell(0).setCellValue(cEDA.seqNo);
					row.createCell(1).setCellValue(cEDA.storeCode);
					row.createCell(2).setCellValue(cEDA.purchOrderNo);
					row.createCell(3).setCellValue(cEDA.custTellNo1);
					row.createCell(4).setCellValue("");
					row.createCell(5).setCellValue(cEDA.dateOrderPlaced);
					row.createCell(6).setCellValue(cEDC.actualDeliveryDate);
					row.createCell(7).setCellValue(cEDA.bqSuppNo);
					row.createCell(8).setCellValue(cEDC.delAdvNoteNo);
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

			FileOutputStream fileOut1 = new FileOutputStream("EDC.xls");
			workBook.write(fileOut1);
			fileOut1.close();
			System.out.println("EDC.xls created");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


}


