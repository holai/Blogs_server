package httpa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/Search")
public class Search extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = req.getParameter("key").trim();
        Mysqluse mysqluse = new Mysqluse(resp);
        Data data = new Data();
        if(key.equals("")){
            data.set(0,"搜索为空");
        }else{
            String sql = "select * from article where  title LIKE ?";
            try {
                PreparedStatement statement = mysqluse.initperpare(sql);
                statement.setString(1,key);
                ResultSet resultSet = statement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
