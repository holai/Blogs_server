package httpa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AlterArticles extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        String title =req.getParameter("title");
        String text =req.getParameter("text");
        String labels =req.getParameter("labels");
        String imageUrl =req.getParameter("image");
        String brief;
        if(text.length()<100){
            brief=   text;
        }else{
            brief=text.substring(0,100);
        }
        brief = Htmltool.delHtmlTags(brief).replaceAll(" ","").replaceAll("&nbsp;","");
        Data data = new Data();
        Mysqluse mysqluse = new Mysqluse(resp);
        if (mysqluse.NoNull(id)){
            String sql = "update article set title=?,brief=?,text=?,labels=?,image=? where id=?";
            PreparedStatement statement = mysqluse.initperpare(sql);
            try {
                statement.setString(1,title);
                statement.setString(2,brief);
                statement.setString(3,text);
                statement.setString(4,labels);
                statement.setString(5,imageUrl);
                statement.setString(6,id);
                int code = statement.executeUpdate();
                if (code>0){
                    data.set(0,"修改成功");
                }else {
                    data.set(1,"修改失败");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                data.set(-3,"数据库错误");
            }finally {
                try {
                    statement.close();
                    mysqluse.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else {
            data.set(-2,"参数错误");
        }
        mysqluse.fa(data);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
