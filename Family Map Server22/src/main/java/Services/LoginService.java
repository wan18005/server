package Services;

import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.DataAccessException;
import DataAccessObjects.DAO;
import DataAccessObjects.UsersDAO;
import Models.AuthToken;
import Models.User;
import Results.*;
import Requests.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class that will handle Login business logic and determine if a request was successful.
 */
public class LoginService {

  private AuthTokenDAO authTokenDAO;
  private UsersDAO userDAO;
  private Connection connection;

  private DAO dao = new DAO();
  private User user = new User();

  private boolean vaildLogin(LoginRequest request)
  {
    if (request.getPassword() == null || request.getUsername() == null)
      return false;
    else
      return true;
  }
  public LoginResult login(LoginRequest request) throws DataAccessException, SQLException
  {
    connection = dao.getConnection();
    AuthToken authtoken = new AuthToken();
    authTokenDAO = new AuthTokenDAO(connection);
    userDAO = new UsersDAO(connection);

    if (!vaildLogin(request))
    {
      dao.closeConnection(false);
      return new LoginResult("Error: Input is invalid.", false);
    }

    user = userDAO.Find(request.getUsername());
    if (user == null)
    {
      dao.closeConnection(false);
      return new LoginResult("Error: user does not exist.", false);
    }
    else if (! user.getPassword().equals(request.getPassword()))
    {
      dao.closeConnection(false);
      return new LoginResult("Error: Password does not match.", false);
    }
    else
    {
      authtoken.setUsername(request.getUsername());
      authTokenDAO.Insert(authtoken);
      dao.closeConnection(true);
      return new LoginResult(authtoken.getAuthtoken(), user.getUsername(), user.getPersonID());
    }
  }
}

