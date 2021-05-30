package Handler;

import DataAccessObjects.DataAccessException;
import Results.ClearResult;
import Services.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler
{

  private void ToString(String in, OutputStream out) throws IOException
  {
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
    outputStreamWriter.write(in);
    outputStreamWriter.flush();
  }
  @Override
  public void handle(HttpExchange exchange) throws IOException
  {

    try {
      if (exchange.getRequestMethod().equalsIgnoreCase("post"))
      {
        ClearService clearService = new ClearService();
        ClearResult clearResult = clearService.ClearDatabase();

        String response = "{ \"message\": \"" + clearResult.getMessageOutput() + "\"}";
        System.out.println("\"" + response + "\" \n");

        if (clearResult.getSuccess())
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          OutputStream responseBody = exchange.getResponseBody();
          ToString(response, responseBody);
          responseBody.close();
        } else
        {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
          OutputStream responseBody = exchange.getResponseBody();
          ToString(response, responseBody);
          responseBody.close();
        }
      }
    } catch (IOException | DataAccessException e)
    {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }


}
