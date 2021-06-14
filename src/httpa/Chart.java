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

@WebServlet("/chart")
public class Chart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        int type = Integer.parseInt(req.getParameter("type"));
//        int size = Integer.parseInt(req.getParameter("size"));
//        String type = req.getParameter("type");
//        int mode = Integer.parseInt(req.getParameter("mode"));
//        Data data = new Data();
        Mysqluse mysqluse = new Mysqluse(resp);
        Data data = new Data();
        List<PageViews> list = new ArrayList<>();
//            Articles.Countx countx = new Articles.Countx();
            try {
                //     select id,title,image,labels from article LIMIT 0,10;
                // 获取 一段范围 列表
                String sqlArticle = "";
                    //时间排序 ORDER by time DESC,id DESC
                switch (type){
                    case 0:
                        sqlArticle= "SELECT city,count(*) as count FROM pageview GROUP BY city";
                        break;
                    case 1:
                        sqlArticle= "SELECT province,count(*) as count FROM pageview GROUP BY province";
                        break;
                    case 3:
                        sqlArticle = "SELECT label,sum(article.browse) AS 'sum',count(*) AS 'count'  FROM `article`  LEFT JOIN labels on FIND_IN_SET(labels.label,article.labels) GROUP BY labels.label";
                        break;
                }


                PreparedStatement statement = mysqluse.initperpare(sqlArticle);
                ResultSet rs= statement.executeQuery();
                if(type == 3){
                    while (rs.next()) {
                        PageViews articleData = new PageViews();
                        articleData.setCount(rs.getString(3));
                        articleData.setId(rs.getString(2));
                        articleData.setCity(rs.getString(1));
                        list.add(articleData);
                    }
                    rs.close();
                }else{
                    whil(list, rs);
                }

                data.set(0,list);
                rs.close();
                statement.close();
                mysqluse.close();
            } catch (SQLException e) {
                e.printStackTrace();
                data.set(-3,"数据库错误");
            }


        mysqluse.fa(data);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void whil(List<PageViews> list, ResultSet rs) throws SQLException {
        while (rs.next()) {
            PageViews articleData = new PageViews();
            articleData.setCount(rs.getString(2));
//            articleData.setId(rs.getString(2));
//            articleData.setIp(rs.getString(3));
            articleData.setCity(rs.getString(1).replace("市",""));

//            articleData.setProvince(rs.getString(2));
            list.add(articleData);
        }
        rs.close();
    }



}

class PageViews{
   private   String count;
   private String id;
   private String ip;
   private String city;
   private String province;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
    public PageViews(){}
    public PageViews(String count, String id, String ip, String city, String province) {
        this.count = count;
        this.id = id;
        this.ip = ip;
        this.city = city;
        this.province = province;
    }
}
