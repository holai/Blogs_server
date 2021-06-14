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

@WebServlet("/statistics")
public class Statistics  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Data data = new Data();
        Mysqluse mysqluse = new Mysqluse(resp);
        String tj = "CALL statistics();";  // 调用 存储过程
        String q = "SELECT id,title,browse from article order by browse DESC LIMIT 10;";
        Statistic statistic = new Statistic();
        List<ArticleList> lists = new ArrayList<>();
        try {
            PreparedStatement zarticlesql = mysqluse.initperpare(tj);
            ResultSet zresult = zarticlesql.executeQuery();
            if(zresult.next()){
                String article = zresult.getString(1);
                String browse = zresult.getString(2);
                String comment = zresult.getString(3);
                statistic.setArticle(article);
                statistic.setBrowse(browse);
                statistic.setComment(comment);
            }else{
                data.set(1,"无数据");
            }
            zresult.close();
            zarticlesql.close();
            PreparedStatement qsql = mysqluse.initperpare(q);
            ResultSet qresult = qsql.executeQuery();
            while (qresult.next()){
                ArticleList articleList = new ArticleList();
                articleList.setId(qresult.getString(1));
                articleList.setTitle(qresult.getString(2));
                articleList.setBrowse(qresult.getString(3));
                lists.add(articleList);
            }
            statistic.setList(lists);
            data.set(0,statistic);
            qresult.close();
            qsql.close();
            mysqluse.close();
        } catch (SQLException e) {
            e.printStackTrace();
            data.set(3,"无数据");
        }
        mysqluse.fa(data);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
class Statistic{
   private String article,browse,comment;
   private List<ArticleList> list;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<ArticleList> getList() {
        return list;
    }
    public void setList(List<ArticleList> list) {
        this.list = list;
    }
    public void setArticle(String article) {
        this.article = article;
    }
    public void setBrowse(String browse) {
        this.browse = browse;
    }
    public String getArticle() {
        return article;
    }
    public String getBrowse() {
        return browse;
    }
}

class ArticleList{
    private String id,title,browse;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBrowse() {
        return browse;
    }
}
