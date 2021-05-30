package Generators;

import Models.Event;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class GenerateLocations {

  public Event GenerateLocation() {
    try {
      Random r = new Random();
      FileReader fr = new FileReader(new File("json/locations.json"));
      JsonParser jp = new JsonParser();
      JsonObject jo = (JsonObject) jp.parse(fr);
      JsonArray ja = (JsonArray) jo.get("data");
      int i = r.nextInt(ja.size());

      JsonObject location = (JsonObject) ja.get(i);
      String city = location.get("city").toString();
      String country = location.get("country").toString();
      float latitude = location.get("latitude").getAsFloat();
      float longitude = location.get("longitude").getAsFloat();

      return new Event(city, country, latitude, longitude);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return new Event("bad", "bad", 00.00f, 00.00f);
  }
}
