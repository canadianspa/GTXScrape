package InvoiceScrape;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;

public class InvoicesNewFunctions {



	public static void checkPayments() {

		HashMap<String,Boolean> invoices = new HashMap<String,Boolean>();
		try {
			InputStream inputStream = new FileInputStream ("BNQTransactions.xls");
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
							String invno = row.getCell(7).getStringCellValue();
							for(String s : invoices.keySet())
							{
								if(s.equals(invno))
								{
									row.createCell(8).setCellValue(invoices.get(s));
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
				
				}
				inputStream.close();
				FileOutputStream fileOut1 = new FileOutputStream("invoice.xls");
				workBook.write(fileOut1);
				fileOut1.close();
				System.out.println("added payment");
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		

	}
	public static void addToInvoiceList(CopyOnWriteArrayList<Invoices> invoices)
	{
		try {
			FileInputStream fiut = new FileInputStream("all.invoice");
			ObjectInputStream ois;
			ArrayList<Invoices> alreadyGot;
			try {
				//cant read first time
				ois = new ObjectInputStream(fiut);
				alreadyGot = (ArrayList<Invoices>) ois.readObject();
			} catch (Exception e) {
				alreadyGot = new ArrayList<Invoices>();
			}
			System.out.println("before");
			for(Invoices e : alreadyGot)
			{
				System.out.println(e.purchOrNo);
			}
			alreadyGot.addAll(invoices);
			System.out.println("after");

			for(Invoices e : alreadyGot)
			{
				System.out.println(e.purchOrNo);
			}


			FileOutputStream fout = new FileOutputStream("all.invoice");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(alreadyGot);
			System.out.println("Invoices added");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void createInvoices()
	{
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
							row.createCell(7).setCellValue(z.invoiceNo);



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
