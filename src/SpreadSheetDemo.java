import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadSheetDemo {

	public static void main(String[] args) throws IOException {
		// Work with poi 3.11
		XSSFWorkbook  workbook = new XSSFWorkbook();

		XSSFSheet spreadsheet2019 = workbook.createSheet("TIOBE Index 2019");
		XSSFSheet spreadsheet2018 = workbook.createSheet("TIOBE Index 2018");
		
		XSSFRow row;

		Map<String, Object[]> tiobe2019 = new TreeMap <String, Object[]>();
		Map<String, Object[]> tiobe2018 = new TreeMap <String, Object[]>();

		tiobe2019.put("1", new Object[] { "Language", "Ratings %", "Change %" });
		tiobe2019.put("2", new Object[] { "Java", "14.880", "-0.06" });
		tiobe2019.put("3", new Object[] { "C", "13.305", "+0.55" });
		tiobe2019.put("4", new Object[] { "Python", "8.262", "+2.39" });
		tiobe2019.put("5", new Object[] { "C++", "8.126", "+1.67" });
		tiobe2019.put("6", new Object[] { "Visual Basic .NET", "6.429", "+2.34" });
		tiobe2019.put("7", new Object[] { "C#", "3.267", "-1.80" });
		tiobe2019.put("8", new Object[] { "JavaScript", "2.426", "-1.49" });
		tiobe2019.put("8", new Object[] { "PHP", "2.420", "-1.59" });

		tiobe2018.put("1", new Object[] { "Language" });
		tiobe2018.put("2", new Object[] { "Java" });
		tiobe2018.put("3", new Object[] { "C" });
		tiobe2018.put("4", new Object[] { "C++" });
		tiobe2018.put("5", new Object[] { "Python" });
		tiobe2018.put("6", new Object[] { "C#" });
		tiobe2018.put("7", new Object[] { "Visual Basic .NET" });
		tiobe2018.put("8", new Object[] { "JavaScript" });
		tiobe2018.put("8", new Object[] { "PHP" });

      Set < String > keyid2019 = tiobe2019.keySet();
      Set < String > keyid2018 = tiobe2018.keySet();
      
      int rowid = 0;

      for (String key : keyid2019) {
         row = spreadsheet2019.createRow(rowid++);
         Object [] objectArr = tiobe2019.get(key);
         int cellid = 0;

         for (Object obj : objectArr) {
            Cell cell = row.createCell(cellid++);
            cell.setCellValue((String)obj);
         }
      } 
      
      rowid = 0;

      for (String key : keyid2018) {
         row = spreadsheet2018.createRow(rowid++);
         Object [] objectArr = tiobe2018.get(key);
         int cellid = 0;

         for (Object obj : objectArr) {
            Cell cell = row.createCell(cellid++);
            cell.setCellValue((String)obj);
         }
      }
      
      FileOutputStream out = new FileOutputStream(new File("tiobe.xlsx"));
      
      workbook.write(out);
      out.close();
      workbook.close();
      
      System.out.println("tiobe.xlsx written successfully");
      
      

	}

}
