
/**
 * @file InsertPrakash.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PlayerInsertPrakash")
public class PlayerInsertPrakash extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public PlayerInsertPrakash() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String playername = request.getParameter("PLAYERNAME");
      String age = request.getParameter("AGE");
      String nationality = request.getParameter("NATIONALITY");
      String position = request.getParameter("POSITION");
      String club = request.getParameter("CLUB");
      String league = request.getParameter("LEAGUE");
      String games_played = request.getParameter("GAMES_PLAYED");
      String goals = request.getParameter("GOALS");
      String assists = request.getParameter("ASSISTS");
      

      Connection connection = null;
      String insertSql = " INSERT INTO MySoccerTablePrakash (id, PLAYERNAME, AGE, NATIONALITY, POSITION, CLUB, LEAGUE, GAMES_PLAYED, GOALS, ASSISTS) values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      try {
         DBConnectionPrakash.getDBConnection();
         connection = DBConnectionPrakash.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, playername);
         preparedStmt.setString(2, age);
         preparedStmt.setString(3, nationality);
         preparedStmt.setString(4, position);
         preparedStmt.setString(5, club);
         preparedStmt.setString(6, league);
         preparedStmt.setString(7, games_played);
         preparedStmt.setString(8, goals);
         preparedStmt.setString(9, assists);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Insert Player Information to Database";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //

            "  <li><b>Player Name</b>: " + playername + "\n" + //
            "  <li><b>Age</b>: " + age + "\n" + //
            "  <li><b>Nationality</b>: " + nationality + "\n" + //
            "  <li><b>Position</b>: " + position + "\n" + //
            "  <li><b>Club</b>: " + club + "\n" + //
            "  <li><b>League</b>: " + league + "\n" + //
            "  <li><b>Games Played</b>: " + games_played + "\n" + //
            "  <li><b>Goals</b>: " + goals + "\n" + //
            "  <li><b>Assists</b>: " + assists + "\n" + //

            "</ul>\n");

      out.println("<a href=/SoccerDatabase/PlayerSearch.html>Search Data</a> <br>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
