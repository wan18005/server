package FakeDataAlgo;
import com.google.gson.*;

import java.util.*;
import java.io.*;

public class FakeName
{
  public String FakeMale()
  {
    try {
      Random random = new Random();
      JsonParser JP = new JsonParser();
      FileReader FR = new FileReader(new File("json/mnames.json"));
      JsonObject JO = (JsonObject) JP.parse(FR);
      JsonArray JA = (JsonArray) JO.get("data");
      int i = random.nextInt(JA.size());
      String name = JA.get(i).toString();
      name = name.substring(1,name.length() -1);
      return name;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    return "No able to generate a male name from json file\n";
  }

  public String FakeFemale()
  {
    try {
      Random random = new Random();
      JsonParser JP = new JsonParser();
      FileReader FR = new FileReader(new File("json/fnames.json"));
      JsonObject JO = (JsonObject) JP.parse(FR);
      JsonArray JA = (JsonArray) JO.get("data");
      int i = random.nextInt(JA.size());
      String name = JA.get(i).toString();
      name = name.substring(1,name.length() -1);
      return name;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    return "No able to generate a female name from json file\n";
  }

  public String FakeLastName()
  {
    try {
      Random random = new Random();
      JsonParser JP = new JsonParser();
      FileReader FR = new FileReader(new File("json/snames.json"));
      JsonObject JO = (JsonObject) JP.parse(FR);
      JsonArray JA = (JsonArray) JO.get("data");
      int i = random.nextInt(JA.size());
      String name = JA.get(i).toString();
      name = name.substring(1,name.length() -1);
      return name;
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    return "No able to generate a surname from json file\n";
  }




}
