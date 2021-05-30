package FakeDataAlgo;

import Models.Event;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class FakeLocation {
  public Event FakeLocation()
  {
    try {
      Random random = new Random();
      JsonParser JP = new JsonParser();
      FileReader FR = new FileReader(new File("json/locations.json"));
      JsonObject JO = (JsonObject) JP.parse(FR);
      JsonArray JA = (JsonArray) JO.get("data");
      int i = random.nextInt(JA.size());

      JsonObject location = (JsonObject) JA.get(i);
      String city = location.get("city").toString();
      String country = location.get("country").toString();
      float latitude = location.get("latitude").getAsFloat();
      float longitude = location.get("longitude").getAsFloat();

      return new Event(city,country,latitude,longitude);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    return new Event("null" , "null" , 0.00f,0.00f);
  }
}
