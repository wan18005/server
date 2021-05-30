package Handler;

import DataAccessObjects.DataAccessException;
import Requests.RegisterRequest;
import Results.RegisterResult;
import Service.Services.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class RegisterHandler implements HttpHandler {

  RegisterService service = new RegisterService();
  Gson gson = new Gson();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      System.out.println("Attempting to register in Handler.\n");
      if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
        String requestBody = StringConversion(exchange.getRequestBody());
        RegisterRequest registerRequest = gson.fromJson(requestBody, RegisterRequest.class);
        RegisterResult registerResult = service.Register(registerRequest);
        String response = gson.toJson(registerResult);
        if (registerResult.getSuccess()) {
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

  private void ToString(String sentResponse, OutputStream out) throws IOException {
    OutputStreamWriter s = new OutputStreamWriter(out);
    s.write(sentResponse);
    s.flush();
  }

  public String StringConversion(InputStream is) {
    Scanner scanner = new java.util.Scanner(is).useDelimiter("\\A");
    return scanner.hasNext() ? scanner.next() : "";
  }
}
