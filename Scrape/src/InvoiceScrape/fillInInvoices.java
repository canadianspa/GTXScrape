package InvoiceScrape;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;

import BNQ.EDA;

public class fillInInvoices {

	public static void main(String[] args) {

		ArrayList<Invoices> listOfInvoices;
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");

		try {
			FileInputStream fiut = new FileInputStream("all.invoice");
			ObjectInputStream ois;
			ois = new ObjectInputStream(fiut);
			listOfInvoices = (ArrayList<Invoices>) ois.readObject();

		} catch (Exception e) {
			listOfInvoices = new ArrayList<Invoices>();
		}


	

		try {
			InputStream inputStream = new FileInputStream ("invoice.xls");
			POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

			HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);

			HSSFSheet sheet  = workBook.getSheetAt (0);
			CreationHelper createHelper = workBook.getCreationHelper();

			for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i ++ )
			{
				try {
					Row row = sheet.getRow(i);
					String po = String.valueOf(row.getCell(0).getStringCellValue());
					for(Invoices z : listOfInvoices)
					{
						if(Integer.parseInt(po) < 83438767)
						{
							row.createCell(4).setCellValue("YES");
						}
						if(z.purchOrNo.substring(0, 8).equals(po))
						{

							row.createCell(4).setCellValue("YES");
							CellStyle cellStyle = workBook.createCellStyle();
						    cellStyle.setDataFormat(
						    createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
						    Cell cell = row.createCell(6);
							cell.setCellValue(form.parse(z.invDate));
							Cell cell2 = row.createCell(3);
							cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
							cell2.setCellValue(Double.parseDouble(z.amountOut));
							


						}


					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			inputStream.close();
			FileOutputStream fileOut1 = new FileOutputStream("invoice.xls");
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


