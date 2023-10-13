import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PlayerSearchPrakash")
public class PlayerSearchPrakash extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public PlayerSearchPrakash() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Database Result";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title + "</h1>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionPrakash.getDBConnection();
         connection = DBConnectionPrakash.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM MySoccerTablePrakash";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM MySoccerTablePrakash WHERE PLAYERNAME LIKE ?";
            String PlayerName = "%" + keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, PlayerName);
         }
         ResultSet rs = preparedStatement.executeQuery();
         
         out.println("<button type=\"button\" onclick=\"window.location.href = '/SoccerDatabase/PlayerSearch.html';\">Search Player!</button> <br><br>");

         while (rs.next()) {
            int id = rs.getInt("ID");
            String playername = rs.getString("PLAYERNAME").trim();
            String age = rs.getString("AGE").trim();
            String nationality = rs.getString("NATIONALITY").trim();
            String position = rs.getString("POSITION").trim();
            String club = rs.getString("CLUB").trim();
            String league = rs.getString("LEAGUE").trim();
            String games_played = rs.getString("GAMES_PLAYED").trim();
            String goals = rs.getString("GOALS").trim();
            String assists = rs.getString("ASSISTS").trim();

            if (keyword.isEmpty() || playername.contains(keyword)) {
               out.println("<b>PLAYERNAME</b>: " + playername + "<br>");
               out.println("<b>AGE</b>: " + age + "<br>");
               out.println("<b>NATIONALITY</b>: " + nationality + "<br>");
               out.println("<b>POSITION</b>: " + position + "<br>");
               out.println("<b>CLUB</b>: " + club + "<br>");
               out.println("<b>LEAGUE</b>: " + league + "<br>");
               out.println("<b>GAMES_PLAYED</b>: " + games_played + "<br>");
               out.println("<b>GOALS</b>: " + goals + "<br>");
               out.println("<b>ASSISTS</b>: " + assists + "<br><br>");
            }
         }
         
         out.println("<button type=\"button\" onclick=\"window.location.href = '/SoccerDatabase/PlayerSearch.html';\">Search Player!</button> <br><br>");
         
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
