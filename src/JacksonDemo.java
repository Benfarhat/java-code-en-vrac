import java.io.File;
import java.io.IOException;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

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
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
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

@JsonRootName(value = "user")
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
public class JacksonDemo {
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        // setup
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
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
        
        // saving json data to files
        File dir = new File("target/json/users/");
        dir.mkdirs();
        
        for(User user: users) {
            mapper.writeValue(new File("target/json/users/" + user.getUsername().trim().replaceAll("\\s", "_").toLowerCase() + ".json"), user);  
        }
        
        mapper.writeValue(new File("target/json/users/all.json"), users);
        System.out.println(mapper.writeValueAsString(users));
        
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
        
        // reading from json containing multiple users
        // i change username key to pseudo (testing @JsonAlias annotation)
        String jsons = "[{\"pseudo\":\"John Doe\",\"email\":\"john.doe@example.com\",\"birthdate\":\"01-02-1980 12:00:00\"},{\"username\":\"Mehdi Harrison\",\"email\":\"mehdi.harrison@example.com\",\"birthdate\":\"20-04-1990 12:00:00\"},{\"username\":\"Ben Fetcher\",\"email\":\"ben.doe@fetcher.com\",\"birthdate\":\"13-07-1995 12:00:00\"}]";
        List<User> lists = new ArrayList<User>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            TypeReference<List<User>> typeReference = new TypeReference<>() {};
            lists = objectMapper.readValue(jsons, typeReference);
            
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
        
    }

}
