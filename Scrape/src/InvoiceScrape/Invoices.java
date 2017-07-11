package InvoiceScrape;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Invoices implements Serializable {

	String invoiceNo,purchOrNo,invDate,delDate,amountOut;

	public Invoices(String invoiceNo, String purchOrNo, String invDate, String delDate, String amountOut) {
		super();
		this.invoiceNo = invoiceNo;
		this.purchOrNo = purchOrNo;
		this.invDate = invDate;
		this.delDate = delDate;
		this.amountOut = amountOut;
	}


	public static void addToInvoiceList(ArrayList<Invoices> invoices)
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
		try {
			FileInputStream fiut = new FileInputStream("all.invoice");
			ObjectInputStream ois;
			ois = new ObjectInputStream(fiut);
			listOfInvoices = (ArrayList<Invoices>) ois.readObject();
			
		} catch (Exception e) {
			listOfInvoices = new ArrayList<Invoices>();
		}


		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");

		try {
			InputStream inputStream = new FileInputStream ("invoice.xls");
			POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

			HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);

			HSSFSheet sheet  = workBook.getSheetAt (0);
			for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i ++ )
			{
				try {
					Row row = sheet.getRow(i);
					String po = String.valueOf(row.getCell(0).getStringCellValue());
					for(Invoices z : listOfInvoices)
					{
						if(z.purchOrNo.substring(0, 8).equals(po))
						{

							System.out.println("yes");
							row.createCell(2).setCellValue("YES");
							row.createCell(5).setCellValue(z.invDate);
							row.createCell(3).setCellValue(z.amountOut);


						}


					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
