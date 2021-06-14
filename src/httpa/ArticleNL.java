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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ArticleNL")
public class ArticleNL extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        int id = Integer.parseInt(req.getParameter("id"));
        Data data = new Data();
        Mysqluse mysqluse = new Mysqluse(resp);
        String sql = "select id,title from article where (id = (select min(id) from article where id > ?) or id = (select max(id) from article where id < ?))";
        PreparedStatement statement = mysqluse.initperpare(sql);
        try {
            statement.setInt(1,id);
            statement.setInt(2,id);
            ResultSet resultSet = statement.executeQuery();
            int i = 0;
            ArticleNLdata[] articleArray = new ArticleNLdata[2];
            while (resultSet.next()){
                ArticleNLdata articleNLdata = new ArticleNLdata();
                articleNLdata.setId(resultSet.getInt(1));
                articleNLdata.setTitle(resultSet.getString(2));
                articleArray[i]=articleNLdata;
                i+=1;
            }

            // 判断 是否为 第一个 或者 最后一个
//            if( i == 1){
//                if(articleArray[0].getId() > id){
//                    articleArray[1] = articleArray[0];
//                    articleArray[0] = new ArticleNLdata();
//                }else{
//                    articleArray[1] = new ArticleNLdata();
//                }
//            }
            resultSet.close();
            statement.close();
            mysqluse.close();
            data.set(i,articleArray);
        } catch (SQLException e) {
            e.printStackTrace();
            data.set(-2,"数据库出现错误");
        }
        mysqluse.fa(data);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
class ArticleNLdata{
    private String title="";
    private int id=-1;
    public void setTitle(String title) {
        this.title = title;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public int getId() {
        return id;
    }
}
