/**
 * This class is used to create a Mongo client, access database and
 * store documents.
 * 
 */
package weatherforecast;


import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDateTime;
import java.util.Iterator;
import org.bson.Document;

/**
 *
 * @author kavyareddy
 */
public class Database {
    MongoDatabase database;
    MongoCollection<Document> collection;
    MongoCollection<Document> collection2;
    public void connectData() {
        // Creating Mongo client 
      MongoClient mongo = new MongoClient( "localhost" , 27017 ); 

      // Creating Credentials 
      MongoCredential credential; 
      credential = MongoCredential.createCredential("sampleUser", "myDb", 
         "password".toCharArray()); 
      System.out.println("Connected to the database successfully");  
      
      // Accessing the database 
      database = mongo.getDatabase("myDb");
      
      //Creating Collection
      //database.createCollection("fiveCollection");
      //database.createCollection("sixteenCollection");
      
      // Retrieving a collection
      collection = database.getCollection("fiveCollection"); 
      collection2 = database.getCollection("sixteenCollection"); 
        
    }
    
    public void storeFiveDayData(int i, double temps, String fiveDate, String cityName, LocalDateTime date) {
      
        Document document = new Document("title", "Weather") 
                    .append("id", i)
                    .append("DateUserAccessed", date)
                    .append("description", "FiveDay Weather Data") 
                    .append("temp", temps) 
                    .append("date", fiveDate)
                    .append("cityName", cityName);  
                    collection.insertOne(document); 
    }
    
    public void storeSixteenDayData(int j, double min_temp16, double max_temp16, String cityName) {
        Document document = new Document("title", "Weather") 
                    .append("id", j)
                    .append("description", "FiveDay Weather Data") 
                    .append("min_temp", min_temp16) 
                    .append("max_temp", max_temp16)
                    .append("cityName", cityName);
                    collection2.insertOne(document); 
    }
    
    public void showFiveDayData() {
        FindIterable<Document> iterDoc = collection.find(); 
        int k = 1; 
        Iterator it = iterDoc.iterator(); 
        while (it.hasNext()) {  
            System.out.println(it.next());  
            k++; 
        }
    }
    
    public void showSixteenDayData() {
        FindIterable<Document> iterDoc2 = collection2.find(); 
        int m = 1; 
        Iterator it2 = iterDoc2.iterator(); 
        while (it2.hasNext()) {  
            System.out.println(it2.next());  
            m++; 
        }
    }
}
