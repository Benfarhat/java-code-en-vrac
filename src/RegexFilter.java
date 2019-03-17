import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


enum SearchType {
    EXACTWORD,
    EXACT_NON_WORD,
    IN_THE_BEGINNING,
    IN_THE_END,
    INSIDE
}
public class RegexFilter {
    private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
    public static void main(String[] args) {

        // Demo with Strings
        String myText = "a,abc,abcd, abd def,abc, fgh,erf abc,Hey, Hello there, Hell no";
        String separator = ",";
        String toFind = "abc";
        

        System.out.println("\n------------------------------------------");
        System.out.println("SEARCHING TO: " + toFind);
        System.out.println("WITH : " + myText);
        System.out.println("------------------------------------------");
        for (SearchType searchType : SearchType.values()) { 
            LOGGER.log(Level.INFO, "Search Type: " + searchType);
            String result = filterInputByRegex(myText, separator, toFind, searchType);
            System.out.println("\tResults for " 
                    + searchType + " >\t" 
                    + (!result.isEmpty() ? result : "There is no match"));
            System.out.println("matches indexes: " + Arrays.toString(searchIndexes(myText, separator, toFind, searchType)));
            //LOGGER.log(Level.INFO, "matches indexes: " + Arrays.toString(searchIndexes(myText, separator, toFind, searchType)));
        }
        
        // Demo with integers
        myText = "  10  01   100   121 110 010 120 11";
        separator = " ";
        toFind = "10";
        
        System.out.println("\n------------------------------------------");
        System.out.println("SEARCHING TO: " + toFind);
        System.out.println("WITH : " + myText);
        System.out.println("------------------------------------------");
        for (SearchType searchType : SearchType.values()) { 
            //LOGGER.log(Level.INFO, "Search Type: " + searchType);
            String result = filterInputByRegex(myText, separator, toFind, searchType);
            System.out.println("\tResults for " 
                    + searchType + " >\t" 
                    + (!result.isEmpty() ? result : "There is no match"));
            System.out.println("matches indexes: " + Arrays.toString(searchIndexes(myText, separator, toFind, searchType)));
            //LOGGER.log(Level.INFO, "matches indexes: " + Arrays.toString(searchIndexes(myText, separator, toFind, searchType)));
        }
    }
    
    /**
     * test regex
     * @param regex
     * @param text
     * @return
     */
    public static boolean matches(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
    /**
     *  Prepare Regex by enum searchType (exact word, non exact word, in the beginning, etc.)
     * @param search
     * @param searchType
     * @return
     */
    public static String prepareRegex(String search, SearchType searchType) {
        String text = "";
        switch(searchType) {
            case EXACTWORD:
                text = ".*\\b" + search + "\\b.*";
                break;
            case EXACT_NON_WORD:
                text = ".*\\B" + search + "\\B.*";
                break;
            case IN_THE_BEGINNING:
                text = "\\A" + search + ".*";
                break;
            case IN_THE_END:
                text = ".*" + search + "\\z";
                break;
            case INSIDE:
                text = ".*" + search + ".*";
                break;
        }
        return text;
    }
    /**
     * Split String to List
     * @param input
     * @param separator "," for String or " " for integer list;
     * @return
     */
    public static List<String> splitToListString(String input, String separator){
        return (List<String>) Stream.of(input.split(separator))
                .filter(str -> !str.isEmpty())
                .map(elem -> new String(elem))
                .collect(Collectors.toList());
    }

    /**
     * Join List to String (only for demo)
     * @param input
     * @param separator
     * @return
     */
    public static String joinStringListWithSeparator(List<String> input, String separator){
        return input.stream().collect(Collectors.joining(separator));

    }

    /**
     * Get Indexes of matching elements
     * @param input
     * @param separator
     * @param search
     * @param searchType
     * @return
     */
    public static int[] searchIndexes(String input, String separator, String search, SearchType searchType) {
        
        final String toFind = prepareRegex(search, searchType);

        List<String> sentences = splitToListString(input, separator);

        int[] indexesOfResults = IntStream
            .range(0,  sentences.size())
            .filter(index -> matches(toFind, sentences.get(index)))
            .toArray();
           
        return indexesOfResults;

    }
    
    /**
     * Filter List (generated from String) by Regex
     * @param input
     * @param separator
     * @param search
     * @param searchType
     * @return
     */
    public static String filterInputByRegex(String input, String separator, String search, SearchType searchType) {
        
        final String toFind = prepareRegex(search, searchType);
        
        List<String> sentences = splitToListString(input, separator);

        List<String> results = sentences
            .stream()
            .parallel()
            .filter(elem -> matches(toFind, elem))
            .collect(Collectors.toList());

        return joinStringListWithSeparator(results, separator);

    }
}
