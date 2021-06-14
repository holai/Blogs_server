package httpa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Labels extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        Data data = new Data();
        List<String> list = new ArrayList<>();
        Mysqluse mysqluse = new Mysqluse(resp);
//        Statement statement= mysqluse.init();
        String  sql = "select * from labels";
        PreparedStatement  statement = mysqluse.initperpare(sql);
        try {
         ResultSet rs = statement.executeQuery();
         while (rs.next()){
            list.add(rs.getString(1));
         }
         data.set(0,list);
         rs.close();
         statement.close();
            mysqluse.close();
        } catch (SQLException e) {
            e.printStackTrace();
            data.set(-3,"数据库错误");
        }finally {
            mysqluse.fa(data);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
