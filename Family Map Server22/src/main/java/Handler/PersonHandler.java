package Handler;

import DataAccessObjects.DataAccessException;
import Results.PersonResult;
import Service.Services.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {

  PersonService personService = new PersonService();
  Gson gson = new Gson();
  String response = "Error: Request denied.";
  PersonResult personResult;

  @Override
  public void handle(HttpExchange exchange) throws IOException {

    try {
      if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
        if (exchange.getRequestHeaders().containsKey("Authorization")) {
          String authtoken = exchange.getRequestHeaders().getFirst("Authorization");
          String uri = exchange.getRequestURI().toString();

          if (uri.equals("/person")) {
            personResult = personService.Person(authtoken);
            response = gson.toJson(personResult);
          } else if (uri.substring(0, 8).equals("/person/")) {
            personResult = personService.Person(uri.substring(8), authtoken);
            response = gson.toJson(personResult);
          } else {
            response = "Error: Request is not valid.";
          }

          if (personResult.getSuccess()) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream responseBody = exchange.getResponseBody();
            ToString(response, responseBody);
            responseBody.close();
          } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            OutputStream responseBody = exchange.getResponseBody();
            ToString(response, responseBody);
            responseBody.close();
          }
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
    }
  }

  private void ToString(String in, OutputStream out) throws IOException {
    OutputStreamWriter s = new OutputStreamWriter(out);
    s.write(in);
    s.flush();
  }
}
