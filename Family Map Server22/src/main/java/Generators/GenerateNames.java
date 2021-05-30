package Generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.Random;

public class GenerateNames {

  public String MaleName() {

    try {
      Random r = new Random();
      JsonParser jp = new JsonParser();
      FileReader fr = new FileReader(new File("json/mnames.json"));
      JsonObject jo = (JsonObject) jp.parse(fr);
      JsonArray ja = (JsonArray) jo.get("data");
      int i = r.nextInt(ja.size());
      String name = ja.get(i).toString();
      name = name.substring(1, name.length() - 1);
      return name;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return "There was an error generating a male name.\n";
  }

  public String FemaleName() {

    try {
      Random r = new Random();
      JsonParser jp = new JsonParser();
      FileReader fr = new FileReader(new File("json/fnames.json"));
      JsonObject jo = (JsonObject) jp.parse(fr);
      JsonArray ja = (JsonArray) jo.get("data");
      int i = r.nextInt(ja.size());
      String name = ja.get(i).toString();
      name = name.substring(1, name.length() - 1);
      return name;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return "There was an error generating a female name.\n";
  }

  public String SurName() {

    try {
      Random r = new Random();
      JsonParser jp = new JsonParser();
      FileReader fr = new FileReader(new File("json/snames.json"));
      JsonObject jo = (JsonObject) jp.parse(fr);
      JsonArray ja = (JsonArray) jo.get("data");
      int i = r.nextInt(ja.size());
      String name = ja.get(i).toString();
      name = name.substring(1, name.length() - 1);
      return name;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    return "There was an error generating a surname.\n";
  }
}
