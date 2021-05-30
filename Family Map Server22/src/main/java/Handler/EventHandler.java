package Handler;

import DataAccessObjects.DataAccessException;
import Results.EventResult;
import Service.Services.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String response;
    Gson gson = new Gson();
    EventService eventService = new EventService();
    EventResult eventResult = new EventResult("Error: Fatal result error.", false);

    try {
      if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
        if (exchange.getRequestHeaders().containsKey("Authorization")) {
          String authToken = exchange.getRequestHeaders().getFirst("Authorization");
          String uri = exchange.getRequestURI().toString();

          if (uri.equals("/event")) {
            eventResult = eventService.event(authToken);
            if (eventResult == null) {
              response = "{ \"message\": \"" + eventResult.getMessage() + "\"}";
            } else {
              response = gson.toJson(eventResult);
            }
          } else if (uri.substring(0, 7).equals("/event/")) {
            eventResult = eventService.event(uri.substring(7), authToken);
            if (! (eventResult.getMessage() == null)) {
              response = "{ \"message\": \"" + eventResult.getMessage() + "\"}";
            } else {
              response = gson.toJson(eventResult);
            }
          } else {
            response = "Error: Request is invalid.";
          }
          if (eventResult.getSuccess()) {
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
    } catch (IOException | DataAccessException e) {
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
