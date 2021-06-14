package httpa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AddLabels extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
//        resp.setContentType("text/json;charset=UTF-8");
//        String str = new String (req.getParameter("value").getBytes("iso-8859-1"),"utf-8");
        String str = (String) req.getParameter("value");
        Data data = new Data();
        Mysqluse mysqluse = new Mysqluse(resp);
        try {
            String sql = "insert into labels(label) values(?)";
            PreparedStatement statement = mysqluse.initperpare(sql);
            statement.setString(1,str);
            int rs = statement.executeUpdate();
//            int rs = statement.executeUpdate("insert into labels(label) values('"+str+"')");
            if (rs>0){
                data.set(0,"成功");
            }else {
                data.set(1,"添加错误");
            }
            statement.close();
            mysqluse.close();
        } catch (SQLException e) {
            e.printStackTrace();
            data.set(-3,"添加错误");
        }finally {
            mysqluse.fa(data);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
