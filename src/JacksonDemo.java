import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

class Util{
    public static List<String> splitToListString(String input, String separator){
        return (List<String>) Stream.of(input.split(separator))
                .filter(str -> !str.isEmpty())
                .map(elem -> new String(elem))
                .collect(Collectors.toList());
    }    
    public static String joinStringListWithSeparator(List<String> input, String separator){
        return input.stream().collect(Collectors.joining(separator));

    }
    public static String capitalize(String input) {
        return Character.toUpperCase(input.charAt(0)) + input.toLowerCase().substring(1);
     }
    public static String capitalizeFirstLetterOfWords(String input, String separator) {
        List<String> sentences = Util.splitToListString(input, separator);
        
        List<String> results = sentences
            .stream()
            .parallel()
            .map(elem -> Util.capitalize(elem))
            .collect(Collectors.toList());

        return Util.joinStringListWithSeparator(results, separator);
    }
    /*
     * Only for demo
     */
    public static void printLineSeparator() {
        System.out.println("-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-=x=-");
    }
    public static void printLineSeparator(String message) {
        System.out.println("-=x=-=x=-=x=[" + message + "]=x=-=x=-=x=-");
    }
}
class CustomDateSerializer extends StdSerializer<Date> {
    
    private static final long serialVersionUID = 1L;
    private static SimpleDateFormat formatter 
      = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
 
    public CustomDateSerializer() { 
        this(null); 
    } 
 
    public CustomDateSerializer(Class<Date> t) {
        super(t); 
    }
 
    @Override
    public void serialize(
      Date value, JsonGenerator gen, SerializerProvider arg2) 
      throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }
    
}

class CustomDateDeserializer extends StdDeserializer<Date> {
    
    private static final long serialVersionUID = 1L;

  private static SimpleDateFormat formatter
    = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

  public CustomDateDeserializer() { 
      this(null); 
  } 

  public CustomDateDeserializer(Class<?> vc) { 
      super(vc); 
  }

  @Override
  public Date deserialize(
    JsonParser jsonparser, DeserializationContext context) 
    throws IOException {
       
      String date = jsonparser.getText();
      try {
          return formatter.parse(date);
      } catch (ParseException e) {
          throw new RuntimeException(e);
      }
  }
}

@JsonRootName(value = "customer")
@JsonPropertyOrder({"username", "email", "birthdate", "password", "id1", "id2", "nullProperties"})
@JsonIgnoreProperties({ "id1" })
@JsonInclude(Include.NON_NULL)
class User{
    int id1 = 42;
    @JsonIgnore
    int id2 = 42;
    String nullProperties = null;
    @JsonAlias({"pseudo", "pseudonyme", "pseudoName", "name"})
    String username;
    String password;
    String email;
    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    Date birthdate;

    public String getUsername() {
        return username;
    }
    @JsonGetter("username")
    public String getTheUsername() {
        return Util.capitalizeFirstLetterOfWords(username, " ");
    }
    @JsonSetter("username")
    public void setTheUsername(String username) {
        this.username = Util.capitalizeFirstLetterOfWords(username, " ");
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    /**
     * Constructor
     * @param username
     * @param password
     * @param email
     * @param birthdate
     */
    public User(String username, String password, String email, Date birthdate) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthdate = birthdate;
    }
    public User() {}
    @Override
    public String toString() {
        return "User [username=" + username + ", email=" + email + "]";
    }
    
}

class Account{
    @JsonProperty("customer")
    User user;
    int type;
    long balance;
    
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public long getBalance() {
        return balance;
    }
    public void setBalance(long balance) {
        this.balance = balance;
    }
    public Account(User user, int type, long balance) {
        super();
        this.user = user;
        this.type = type;
        this.balance = balance;
    }
    public Account() {}
}
public class JacksonDemo {
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        // setup
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // creating users objects
        List<User> users = new ArrayList<User>();
        
        users.add(new User("John Doe", 
                    "SuperSecretPassword", 
                    "john.doe@example.com", 
                    new GregorianCalendar(1980, 01, 01).getTime())
                );
        users.add(new User("Mehdi Harrison", 
                    "SuperSecretPassword", 
                    "mehdi.harrison@example.com", 
                    new GregorianCalendar(1990, 03, 20).getTime())
                );
        users.add(new User("Ben Fetcher", 
                    "SuperSecretPassword", 
                    "ben.doe@fetcher.com", 
                    new GregorianCalendar(1995, 06, 13).getTime())
                );
        
        
        List<Account> accounts = new ArrayList<Account>();
        
        accounts.add(new Account(new User("AAAéàîö BBB", 
                    "SSSSSS", 
                    "email@fetcher.com", 
                    new GregorianCalendar(1975, 10, 13).getTime()),
            1,
            200000
                ));        
        accounts.add(new Account(new User("CCCéàîö DDD", 
                "SSSSSS", 
                "email@fetcher.com", 
                new GregorianCalendar(1975, 10, 13).getTime()),
            1,
            150000
            ));        
        accounts.add(new Account(new User("EEEéàîö FFF", 
                "SSSSSS", 
                "email@fetcher.com", 
                new GregorianCalendar(1975, 10, 13).getTime()),
            2,
            170000
            ));
        
        // writing data to json files
        File dir = new File("target/json/users/");
        dir.mkdirs();

        dir = new File("target/json/userAccounts/");
        dir.mkdirs();
        
        for(User user: users) {
            mapper.writeValue(new File("target/json/users/" + user.getUsername().trim().replaceAll("\\s", "_").toLowerCase() + ".json"), user);  
        }
        
        mapper.writeValue(new File("target/json/users/all.json"), users);
        System.out.println(mapper.writeValueAsString(users)); 
        
        mapper.writeValue(new File("target/json/userAccounts/all.json"), accounts);
        System.out.println(mapper.writeValueAsString(accounts));
        
        // writing data to yaml files
        dir = new File("target/yaml/users/");
        dir.mkdirs();

        dir = new File("target/yaml/userAccounts/");
        dir.mkdirs();
        
        ObjectMapper yamlObjectMapper = new ObjectMapper(new YAMLFactory()); 
        
        for(User user: users) {
            yamlObjectMapper.writeValue(new File("target/yaml/users/" + user.getUsername().trim().replaceAll("\\s", "_").toLowerCase() + ".yml"), user);  
        }
        
        // Multiple objects
        yamlObjectMapper.writeValue(new File("target/yaml/users/all.yml"), users);
        
        // Nested objects
        yamlObjectMapper.writeValue(new File("target/yaml/userAccounts/all.yml"), accounts);
        
        Util.printLineSeparator();
        
        // reading from json string
        String json = "{\"username\":\"John Doe\",\"email\":\"john.doe@example.com\",\"birthdate\":\"01-02-1980 12:00:00\"}";

        try {
            User demo = mapper.readerFor(User.class).readValue(json);
            System.out.println(demo.getUsername());
            System.out.println(demo.getBirthdate());
            System.out.println(demo.getEmail());
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        };
        
        Util.printLineSeparator();
        
        // reading from json containing multiple users
        // i change username key to pseudo (testing @JsonAlias annotation)
        String jsons = "[{\"pseudo\":\"John Doe\",\"email\":\"john.doe@example.com\",\"birthdate\":\"01-02-1980 12:00:00\"},{\"username\":\"Mehdi Harrison\",\"email\":\"mehdi.harrison@example.com\",\"birthdate\":\"20-04-1990 12:00:00\"},{\"username\":\"Ben Fetcher\",\"email\":\"ben.doe@fetcher.com\",\"birthdate\":\"13-07-1995 12:00:00\"}]";
        List<User> lists = new ArrayList<User>();

        try {
            TypeReference<List<User>> typeReference = new TypeReference<>() {};
            lists = mapper.readValue(jsons, typeReference);
            
            for(User elem: lists) {
                System.out.println(elem.getClass());
                System.out.println(elem);
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        };
        
        Util.printLineSeparator();
        
        // Nested Object / JSON
        
        String nestedJson = "[ {\r\n" + 
                "  \"type\" : 1,\r\n" + 
                "  \"balance\" : 200000,\r\n" + 
                "  \"customer\" : {\r\n" + 
                "    \"username\" : \"AAA BBB\",\r\n" + 
                "    \"email\" : \"email@fetcher.com\",\r\n" + 
                "    \"birthdate\" : \"13-11-1975 12:00:00\"\r\n" + 
                "  }\r\n" + 
                "}, {\r\n" + 
                "  \"type\" : 1,\r\n" + 
                "  \"balance\" : 150000,\r\n" + 
                "  \"customer\" : {\r\n" + 
                "    \"username\" : \"CCC DDD\",\r\n" + 
                "    \"email\" : \"email@fetcher.com\",\r\n" + 
                "    \"birthdate\" : \"13-11-1975 12:00:00\"\r\n" + 
                "  }\r\n" + 
                "}, {\r\n" + 
                "  \"type\" : 2,\r\n" + 
                "  \"balance\" : 170000,\r\n" + 
                "  \"customer\" : {\r\n" + 
                "    \"username\" : \"EEE FFF\",\r\n" + 
                "    \"email\" : \"email@fetcher.com\",\r\n" + 
                "    \"birthdate\" : \"13-11-1975 12:00:00\"\r\n" + 
                "  }\r\n" + 
                "} ]";
     
        List<Account> accountsList = new ArrayList<Account>();

        try {
            TypeReference<List<Account>> typeReference = new TypeReference<>() {};
            accountsList = mapper.readValue(nestedJson, typeReference);
            
            for(Account elem: accountsList) {
                System.out.println(elem.getClass());
                System.out.println(elem.getUser().getUsername() + ": " + elem.getBalance());
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        };
        
        Util.printLineSeparator();

        // Read from JSON file
        
        try {
            TypeReference<List<Account>> typeReference = new TypeReference<>() {};
            File accountsFromFile = new File("target/json/userAccounts/all.json");
            accountsList = mapper.readValue(accountsFromFile, typeReference);
            
            for(Account elem: accountsList) {
                System.out.println(elem.getClass());
                System.out.println(elem.getUser().getTheUsername() + ", " + elem.getUser().getEmail() + ": " + elem.getBalance());
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        };
        

        Util.printLineSeparator();
        
        // Read from JSON URL
        
        try {
            TypeReference<List<Account>> typeReference = new TypeReference<>() {};
            URL accountsURL = new URL("file:target/json/userAccounts/all.json");
            accountsList = mapper.readValue(accountsURL, typeReference);
            
            for(Account elem: accountsList) {
                System.out.println(elem.getClass());
                System.out.println(elem.getUser().getTheUsername() + ", " + elem.getUser().getEmail() + ": " + elem.getBalance());
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        };


        Util.printLineSeparator("From Yaml File");
        // Read from YAML file
        
        try {
            TypeReference<List<Account>> typeReference = new TypeReference<>() {};
            File accountsFromFile = new File("target/yaml/userAccounts/all.yml");
            accountsList = yamlObjectMapper.readValue(accountsFromFile, typeReference);
            
            for(Account elem: accountsList) {
                System.out.println(elem.getClass());
                System.out.println(elem.getUser().getTheUsername() + ", " + elem.getUser().getEmail() + ": " + elem.getBalance());
            }
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        };    

        
    }

}
