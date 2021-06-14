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
/***
 *    code返回
 *    0成功    2驱动异常  3连接异常   4数据库命令
 */
public class Intro extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
//        resp.setHeader("Access-Control-Allow-Credentials", "true");
//        resp.setHeader("P3P", "CP=CAO PSA OUR");
//        if (req.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(req.getMethod())) {
//            resp.addHeader("Access-Control-Allow-Methods", "POST,GET,TRACE,OPTIONS");
//            resp.addHeader("Access-Control-Allow-Headers", "Content-Type,Origin,Accept");
//            resp.addHeader("Access-Control-Max-Age", "120");
//        }
        ArrayList<Jsoncontent> list = new ArrayList<Jsoncontent>();
        Data data = new Data();
        Mysqluse mysqluse = new Mysqluse(resp);
            try {
                String sql = "select * from datemethods order by indate DESC";
                PreparedStatement statement = mysqluse.initperpare(sql);
                data.setCode(mysqluse.getCode());
                //3.ResultSet类，用来存放获取的结果集！！
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    Jsoncontent jsoncontent = new Jsoncontent();
                    //获取stuname这列数据
                    jsoncontent.date = rs.getString("indate");
                    //获取stuid这列数据
                    jsoncontent.title = rs.getString("title");
                    jsoncontent.text = rs.getString("intext");
                    //输出结果
                    list.add(jsoncontent);
                }
                data.setData(0);
                data.setData(list);
                rs.close();
                statement.close();
                mysqluse.close();
            } catch (SQLException e) {
                //数据库连接失败异常处理
                data.setData(4);
                e.printStackTrace();
            } finally {
                mysqluse.fa(data);
            }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}


class Jsoncontent {
    String date =null;
    String title=null;
    String text=null;
    public void setData(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}


