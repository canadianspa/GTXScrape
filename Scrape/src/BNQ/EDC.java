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


	public static void createEDCXLS(ArrayList<EDC> EDCs,String seqNo)
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
					Row row = sheet.getRow(cRow);
					row.getCell(0).setCellValue(seqNo);
					row.getCell(1).setCellValue(cEDA.storeCode);
					row.getCell(2).setCellValue(cEDA.purchOrderNo);
					row.getCell(3).setCellValue(cEDA.custTellNo1);
					row.getCell(4).setCellValue("");
					row.getCell(5).setCellValue(cEDA.dateOrderPlaced);
					row.getCell(6).setCellValue(cEDC.actualDeliveryDate);
					row.getCell(7).setCellValue(cEDA.bqSuppNo);
					row.getCell(8).setCellValue(cEDC.delAdvNoteNo);
					row.getCell(9).setCellValue(cEDA.custName);
					row.getCell(10).setCellValue(cEDA.custAdd1);
					row.getCell(11).setCellValue(cEDA.custAdd2);
					row.getCell(12).setCellValue(cEDA.custAdd3);
					row.getCell(13).setCellValue(cEDA.custAdd4);
					row.getCell(14).setCellValue(cEDA.custPostCode);
					row.getCell(15).setCellValue("");
					row.getCell(16).setCellValue(cEDA.eanCode1.get(i));
					row.getCell(17).setCellValue(cEDA.desc1.get(i));
					row.getCell(18).setCellValue(cEDA.qty1.get(i));
					row.getCell(19).setCellValue("1");
					row.getCell(20).setCellValue("YES");
					row.getCell(21).setCellValue("00001");
					row.getCell(22).setCellValue(cEDA.purchOrderNo + "00001");
					row.getCell(23).setCellValue("Y");
					row.getCell(24).setCellValue("NO");
					row.getCell(25).setCellValue("HOME");
					cRow += 1;
				}
			}

			FileOutputStream fileOut1 = new FileOutputStream(EDCs.get(0).start.purchOrderNo + " to " + EDCs.get(EDCs.size() -1).start.purchOrderNo + ".xls");
			workBook.write(fileOut1);
			fileOut1.close();
			System.out.println(EDCs.get(0).start.purchOrderNo + " to " + EDCs.get(EDCs.size() -1).start.purchOrderNo + ".xls created");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


}


