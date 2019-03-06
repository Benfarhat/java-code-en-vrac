import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dir {
    
    static final int MAX_CHAR = 50;
    static final char DELIM = '-';

    public static void main(String[] args) {

        String[] paths = {"."};
        if (args.length > 0) {
            paths = args ;
        }
        
        printLine(6,12,21,MAX_CHAR+2);
        printTitleLine(" Type"," Size"," Date       Time", " File name",6,12,21,MAX_CHAR+2);
        
        for(String path: paths) {
            scanDir(path);
        }
        
        printLine(6,12,21,MAX_CHAR+2);
        
    }

    /**
     * @param path
     */
    private static void scanDir(String path) {
        File[] files;
        files = new File(path).listFiles();

        printLine(6,12,21,MAX_CHAR+2);
        for(File file: files) {
            printDetails(file, true);
        }
    }


    /**
     * Print file details
     * @param file
     * @param recursive     if true we recursively scan directory
     */
    private static void printDetails(File file, boolean recursive) {
        BasicFileAttributes attr = null;
        try {
            attr = Files.readAttributes(Paths.get(file.getAbsolutePath()), BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        char type = file.isDirectory() ? 'D' : 'F';
        long sizeKo = file.length() / 1024;
        FileTime fileTime=attr.creationTime();
        String absolutePath;
        Date creationTime = new Date((fileTime.toMillis()));
        String name = file.getName();
        System.out.printf( "| %c    | %,10d | %tF %tT | %-" + Integer.toString(MAX_CHAR) + "s |\n", 
                type, 
                sizeKo, 
                creationTime, 
                creationTime, 
                (String) name.substring(0, Math.min(MAX_CHAR, name.length()))
        );
        
        if (recursive && file.isDirectory()) {
            printLine(MAX_CHAR+44);
            absolutePath = file.getAbsolutePath() + File.separator;
            printTitleLine((String) absolutePath.substring(0,Math.min(MAX_CHAR+44, absolutePath.length())),MAX_CHAR+44);
            scanDir(file.getAbsolutePath());
        }
    }
    
    /**
     * Repeat a character "c", "number" times
     * for example repeatChar('a',3) will return "aaa"
     * @param c
     * @param number
     * @return
     */
    private static String repeatChar(char c, int number) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < number; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
    
    /**
     * Print a delimiter line
     * we should have something equivalent to this:
     * System.out.printf("+%6s+%12s+%21s+%52s+\n", repeatChar(DELIM, 6), repeatChar(DELIM, 12), repeatChar(DELIM, 21), repeatChar(DELIM, 52));
     * @param args
     */
    private static void printLine(int ...args) {
        System.out.print("+");
        for(int arg: args) {
            System.out.printf("%" + Integer.toString(arg) + "s+", repeatChar(DELIM, arg));
        }
        System.out.println();
    }
    /**
     * Print line with titles
     * method start by extracting from varargs titles and columns length
     * @param args
     */
    private static void printTitleLine(Object ...args) {
        List<String> titles = new ArrayList<String>();
        List<Integer> lengths = new ArrayList<Integer>();
        
        for(Object elements: args) {
            if(elements instanceof java.lang.String) {
                titles.add(elements.toString());
                continue;
            }
            if(elements instanceof java.lang.Integer) {
                lengths.add((Integer) elements);
                continue;
            }
        }
        int position = 0;
        System.out.print("|");
        for(int arg: lengths) {
            if(position < titles.size()) {
                System.out.printf("%-" + Integer.toString(arg) + "s|", titles.get(position++));                
            } else {
                System.out.printf("%" + Integer.toString(arg) + "s|", "");
            }
            
        }
        System.out.println();
        
    }

}
