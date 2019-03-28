import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class PoiWordDemo {

	public static void main(String[] args) throws IOException {
	      //Blank Document
	      XWPFDocument document = new XWPFDocument(); 
	      
	      //Write the Document in file system
	      FileOutputStream out = new FileOutputStream(new File("loremIpsum.docx"));
	      
	      //create Paragraph
	      XWPFParagraph paragraphOne = document.createParagraph();
	      
	      //Set Bold an Italic
	      XWPFRun paragraphOneRunOne = paragraphOne.createRun();
	      paragraphOneRunOne.setBold(true);
	      paragraphOneRunOne.setItalic(true);
	      paragraphOneRunOne.setText("Lorem Ipsum");
	      paragraphOneRunOne.addBreak();

	      //Set bottom border to paragraph
	      paragraphOne.setBorderBottom(Borders.BASIC_BLACK_DASHES);
	      
	      //create Paragraph
	      XWPFParagraph paragraph = document.createParagraph();	      
	      XWPFRun run = paragraph.createRun();
	      run.setText("\r\n" + 
	      		"\r\n" + 
	      		"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut consequat massa justo, vitae commodo magna dictum vel. Proin aliquam dictum convallis. Fusce et sapien sed nisi venenatis tempor. Donec dolor justo, varius quis volutpat vel, sollicitudin at urna. Cras ut elit posuere, vehicula arcu in, aliquam arcu. Pellentesque vel mauris cursus dui dictum pharetra. Duis facilisis rutrum metus, bibendum accumsan magna rhoncus a. Ut eget suscipit sem. Vestibulum massa sapien, scelerisque vitae volutpat non, ultricies in risus. Vestibulum vitae leo aliquam metus accumsan sagittis eu at tortor. Vivamus et enim id lacus luctus tristique. Praesent posuere leo non orci auctor, quis posuere eros pharetra. Ut in lorem non ante dictum viverra. Sed ultricies turpis eget venenatis pellentesque. Vivamus non posuere sem.\r\n" + 
	      		"\r\n" + 
	      		"Maecenas at mi pellentesque, consectetur purus in, tempor orci. Mauris vulputate tincidunt augue nec sodales. In efficitur magna vitae velit pulvinar sollicitudin. Vivamus enim urna, sodales ut mattis vitae, varius ut diam. Fusce malesuada est dui, quis tincidunt ex placerat in. Suspendisse fringilla, mi lobortis iaculis auctor, elit urna dictum metus, eu rutrum felis arcu vulputate risus. Sed sodales nibh ut accumsan sodales. Duis maximus egestas purus, vel cursus massa suscipit ac. Maecenas libero neque, facilisis vitae est a, commodo molestie diam. Quisque efficitur lectus et elementum gravida.\r\n" + 
	      		"\r\n" + 
	      		"Vivamus viverra velit quis leo eleifend, eget scelerisque tellus congue. Mauris semper sit amet magna sit amet porta. In lectus neque, elementum vitae tellus sed, mollis tristique nunc. Duis a erat nibh. Vivamus pellentesque id dui a molestie. Aliquam ipsum justo, elementum et aliquet quis, fermentum ac nisl. Nam vel orci id ex facilisis tempor. Mauris interdum vestibulum felis, consectetur vehicula arcu mollis in. Ut consectetur ac nisl convallis vehicula. Nunc rhoncus, felis et ultricies fringilla, augue ligula vulputate dolor, non ornare metus lectus sed erat. Morbi turpis elit, bibendum non arcu in, rutrum pretium metus. Integer tempor ligula metus, at sollicitudin tellus volutpat sed. Curabitur elementum dignissim fermentum.\r\n" + 
	      		"\r\n" + 
	      		"Suspendisse potenti. Nulla eleifend erat sed consectetur porta. Nam vitae euismod mi. Integer accumsan risus ante, tempus fringilla arcu porttitor vel. Duis eu lacus leo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Sed faucibus, metus a venenatis pellentesque, magna lacus fermentum felis, id sagittis libero est sed lacus. Vivamus fermentum ex a justo rhoncus, id egestas justo consequat. Proin ultricies accumsan elit, et porta nisl facilisis et. Pellentesque eu tincidunt est. Donec tempus, odio eu efficitur auctor, ipsum est aliquet nisi, quis molestie felis enim at leo. Quisque eget justo rutrum erat viverra facilisis. Phasellus congue ac augue ac condimentum. Integer enim neque, tincidunt eget imperdiet in, tincidunt nec nulla. Praesent at fermentum lacus, eget aliquam justo.\r\n" + 
	      		"\r\n" + 
	      		"Maecenas ornare sit amet velit nec mattis. Curabitur eros dui, tempor nec erat ac, scelerisque vestibulum orci. Duis et nunc nec nibh maximus fringilla ut nec nibh. Phasellus sollicitudin vestibulum massa, et suscipit metus cursus vel. Suspendisse vehicula mattis tincidunt. Sed at posuere tellus, at egestas mauris. Integer augue sapien, dictum vel tempus sed, aliquet nec nisi. Suspendisse ac nisl quis nunc iaculis molestie nec quis leo. Integer aliquam interdum nulla cursus commodo. Suspendisse eu facilisis turpis. Phasellus non gravida sem, ut euismod nisi. In tristique tristique nisl, eget posuere elit. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Praesent mi erat, elementum ac commodo nec, tristique nec odio. Vestibulum justo leo, efficitur vitae sapien sit amet, ultrices dignissim nulla. Nulla mi dui, bibendum at faucibus commodo, gravida non nibh. ");
			
	      //create table
	      XWPFTable table = document.createTable();
			
	      //create first row
	      XWPFTableRow tableRowOne = table.getRow(0);
	      tableRowOne.getCell(0).setText("col one, row one");
	      tableRowOne.addNewTableCell().setText("col two, row one");
	      tableRowOne.addNewTableCell().setText("col three, row one");
			
	      //create second row
	      XWPFTableRow tableRowTwo = table.createRow();
	      tableRowTwo.getCell(0).setText("col one, row two");
	      tableRowTwo.getCell(1).setText("col two, row two");
	      tableRowTwo.getCell(2).setText("col three, row two");
			
	      //create third row
	      XWPFTableRow tableRowThree = table.createRow();
	      tableRowThree.getCell(0).setText("col one, row three");
	      tableRowThree.getCell(1).setText("col two, row three");
	      tableRowThree.getCell(2).setText("col three, row three");
		
	      
	      document.write(out);
	      out.close();
	      System.out.println("loremIpsum.docx written successfully");

	}

}
