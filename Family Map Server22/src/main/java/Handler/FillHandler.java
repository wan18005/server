package Handler;

import DataAccessObjects.DataAccessException;
import Results.FillResult;
import Service.Services.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {

  FillService fillService = new FillService();
  Gson gson = new Gson();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    FillResult fillResult;
    String response = "Error: Invalid request.";
    try {
      if (exchange.getRequestMethod().equalsIgnoreCase("post")) {

        String uri = exchange.getRequestURI().toString();
        uri = uri.substring(6);

        if (uri.contains("/")) {
          int i = uri.indexOf("/");
          fillResult = fillService.Fill(uri.substring(0, i), Integer.parseInt(uri.substring(i + 1)));
          response = gson.toJson(fillResult);
        } else {
          fillResult = fillService.Fill(uri, 4);
          response = gson.toJson(fillResult);
        }
        if (fillResult.getSuccess()) {
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
