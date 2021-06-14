package httpa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class RemoveLabels extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String value = req.getParameter("value");
        Data data = new Data();
        Mysqluse mysqluse = new Mysqluse(resp);
        Statement statement= mysqluse.init();
        try {
            int sr = statement.executeUpdate("delete from labels where(label='"+value+"')");
            if (sr>0){
                data.set(0,"成功");
            }else {
                data.set(1,"删除0");
            }
//            mysqluse.close();
//            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            data.set(-3,"数据库错误");
        }finally {
            mysqluse.fa(data);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
