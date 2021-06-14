package httpa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/AddComment")
public class AddComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
      String articleId = req.getParameter("articleId");
      String text = req.getParameter("text");
      String login =req.getParameter("login");
      String userName = "";
      String id = "";
      String avatar = "";
      Cookie[] cookies = req.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(login)){
                id = cookie.getValue();
            }
            if(cookie.getName().equals("name")){
                userName = cookie.getValue();
            }
            if(cookie.getName().equals("avatar")){
                avatar = cookie.getValue();
            }
        }
      Mysqluse mysqluse = new Mysqluse(resp);
      Data data = new Data();
      if(!mysqluse.NoNull(articleId,text,login,userName,id)){
          data.set(-1,"参数不全");
      }else {
          String sql = "call addComment(?,?,?,?,?)";
          try {
              PreparedStatement initperpare = mysqluse.initperpare(sql);
              initperpare.setString(1, articleId);
              initperpare.setString(2, text);
              initperpare.setString(3, id);
              initperpare.setString(4, userName);
              initperpare.setString(5, avatar);

              initperpare.execute();
              data.set(0, "成功");
              initperpare.close();
          } catch (SQLException e) {
              data.set(-2,e.toString());
              System.out.println(e.toString());
          }
      }
        mysqluse.fa(data);
        mysqluse.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
