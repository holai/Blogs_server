package httpa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *     0成功  1删除失败 -1验证失败  -2 参数有误  -3 SQL语句错误
 */
public class AddIntro extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        resp.setContentType("text/json;charset=UTF-8");
        String date = (String) req.getParameter("date");
        String title = (String) req.getParameter("title");
        String text = (String) req.getParameter("text");
        Data data = new Data();
        Mysqluse mysqluse = new Mysqluse(resp);
        if(mysqluse.NoNull(date,text,title)){
//            String sqlml = "insert into datemethods(indate,title,intext) values('"+date+"','"+title+"','"+text+"')";
//            q(resp, sqlml, mysqluse,data);
            String sqlml = "insert into datemethods(indate,title,intext) values(?,?,?)";
            PreparedStatement statement = mysqluse.initperpare(sqlml);
            try {
                statement.setString(1,date);
                statement.setString(2,title);
                statement.setString(3,text);
                int res = statement.executeUpdate();
                if (res == 1){
                    data.set(0,"添加成功");
                }else{
                    data.set(1,"添加失败");
                }
                statement.close();
                mysqluse.close();
            } catch (SQLException e) {
                data.set(-3,"参数错误!");
                e.printStackTrace();
            }
        }else{
            data.set(-2,"上传参数空缺");
        }
        mysqluse.fa(data);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        doGet(req, resp);
    }
}
