package HomeBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;

public class InvoicesNewFunctions {
	
	public static void addToInvoiceList(CopyOnWriteArrayList<Invoices> invoices)
	{
		try {
			FileInputStream fiut = new FileInputStream("allHomeBase.invoice");
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
				System.out.println(e.invoiceNo);
			}
			alreadyGot.addAll(invoices);
			System.out.println("after");

			for(Invoices e : alreadyGot)
			{
				System.out.println(e.invoiceNo);
			}


			FileOutputStream fout = new FileOutputStream("allHomeBase.invoice");
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
			FileInputStream fiut = new FileInputStream("allHomeBase.invoice");
			ObjectInputStream ois;
			ois = new ObjectInputStream(fiut);
			listOfInvoices = (ArrayList<Invoices>) ois.readObject();

		} catch (Exception e) {
			listOfInvoices = new ArrayList<Invoices>();
		}


	

		try {
			InputStream inputStream = new FileInputStream ("HomeBaseinvoice.xls");
			POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

			HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);

			HSSFSheet sheet  = workBook.getSheetAt (0);
			CreationHelper createHelper = workBook.getCreationHelper();

			for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i ++ )
			{
				try {
					Row row = sheet.getRow(i);
					String po = String.valueOf(row.getCell(1).getNumericCellValue());
					
					po = po.substring(0, 5);
					
					System.out.println(po);
					for(Invoices z : listOfInvoices)
					{
						
						if(z.invoiceNo.equals(po))
						{
							System.out.println("it worked");
							row.createCell(0).setCellValue(z.invDate);
							break;
						}


					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			inputStream.close();
			FileOutputStream fileOut1 = new FileOutputStream("HomeBaseinvoice.xls");
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
