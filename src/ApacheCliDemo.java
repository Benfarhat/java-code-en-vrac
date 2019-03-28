
import java.io.PrintWriter;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ApacheCliDemo {

	public static void main(String[] args) throws ParseException {

	      Options options = new Options();
	      options.addOption("p", "print", false, "Send print request to printer.")
	         .addOption("g", "gui", false, "Show GUI Application")
	         .addOption("n", true, "No. of copies to print");

	      HelpFormatter formatter = new HelpFormatter();
	      
	      //***Parsing Stage***
	      //Create a parser
	      CommandLineParser parser = new DefaultParser();

	      //parse the options passed as command line arguments
	      CommandLine cmd = parser.parse( options, args);

	      //***Interrogation Stage***
	      //hasOptions checks if option is present or not
	      
	      if ( cmd.hasOption("p") || cmd.hasOption("print") ) { 
	         System.out.println("Send print request to printer");
	      } else if (cmd.hasOption("g") || cmd.hasOption("gui")) {
	         System.out.println("Show GUI Application");
	      }else if ( cmd.hasOption("n") ) {
		      System.out.println("No. of copies to print: " + cmd.getOptionValue("n"));
	      } else {
	    	     final PrintWriter writer = new PrintWriter(System.out);
	   	      formatter.printHelp("ApacheCliDemo", options);
	   	      
	   	      formatter.printUsage(writer,80,"ApacheCliDemo", options);
	   	      writer.flush();  
	      }


	}

}
