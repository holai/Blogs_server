package httpa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *     0成功  1参数空 或者 有特殊字符  2删除失败  3 SQL语句错误
 */
public class RemoveIntro extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String date = (String) req.getParameter("date");
        Data data = new Data();
        Mysqluse mysqluse = new Mysqluse(resp);
        if(mysqluse.NoNull(date)){
            String sqlml = "delete from datemethods where(indate='"+date+"')";
            Statement statement = mysqluse.init();
            try {
                int res = statement.executeUpdate(sqlml);
                if (res == 0){
                    data.set(1,"失败： 更新数据0");
                }else {
                    data.set(0,"添加成功");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            AddIntro.q(resp, sqlml, mysqluse,data);
        }else{
            data.set(-2,"参数为空");
        }
        mysqluse.close();
        mysqluse.fa(data);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


}
