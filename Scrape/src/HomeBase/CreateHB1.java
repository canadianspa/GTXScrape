package HomeBase;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class CreateHB1 {

	public static void main(String[] args) throws Exception {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyy");
		LocalDate localDate = LocalDate.now();
		System.out.println(dtf.format(localDate));
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("CanadianSpaCompany_" + localDate +  ".HB1"), "utf-8"))) {

			try {
				InputStream inputStream = new FileInputStream ("HOMEBASE STATUS UPDATE.xls");
				POIFSFileSystem fileSystem = new POIFSFileSystem (inputStream);

				HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);

				HSSFSheet sheet  = workBook.getSheetAt (2);

				writer.write(sheet.getRow(0).getCell(0).getStringCellValue() + System.lineSeparator());
				for(int i = 2; i < sheet.getPhysicalNumberOfRows();i++)
				{
					if(!sheet.getRow(i).getCell(0).getStringCellValue().equals("00131/12/99160012345xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"))
					{
						writer.write(sheet.getRow(i).getCell(0).getStringCellValue() + System.lineSeparator());

					}
					else
					{
						break;
					}

				}
				writer.write(sheet.getRow(1).getCell(0).getStringCellValue() + System.lineSeparator());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		}
	}

}
