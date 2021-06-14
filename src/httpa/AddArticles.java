package httpa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AddArticles extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

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
            if (mysqluse.NoNull(title,text,labels)){
                String sql = "insert into article(title,brief,text,image,labels) values(?,?,?,?,?)";
                PreparedStatement statement = mysqluse.initperpare(sql);
                try {
                    statement.setString(1,title);
                    statement.setString(2,brief);
                    statement.setString(3,text);
                    statement.setString(4,imageUrl);
                    statement.setString(5,labels);
                    int res =statement.executeUpdate();
                    if(res == 1){
                        data.set(0,"添加成功");
                    }else{
                        data.set(1,"添加失败");
                    }
                    statement.close();
                    mysqluse.close();
                } catch (SQLException e) {
                    data.set(-3,"写入参数出现错误！");
                    e.printStackTrace();
                }
            }else {
                data.set(-2,"参数错误！");
            }
        mysqluse.fa(data);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }
}
