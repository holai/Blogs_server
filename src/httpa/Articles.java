package httpa;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Articles extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        // page<0 则为请求 第一页文章列表 或 搜索第一页
        // mode   0为选择第page页列表  -1为第一页文章列表 -2为搜索第page页
        int page = Integer.parseInt(req.getParameter("page"));
        int size = Integer.parseInt(req.getParameter("size"));
        String type = req.getParameter("type");
        int mode = Integer.parseInt(req.getParameter("mode"));
        Data data = new Data();
        Mysqluse mysqluse = new Mysqluse(resp);
        List<ArticleData> list = new ArrayList<>();
        if(page==-1){
            Countx countx = new Countx();
            try {
           //     select id,title,image,labels from article LIMIT 0,10;
                // 获取 一段范围 列表
                String sqlArticle,sqlCount;
                if(mode==-1){
                    //时间排序 ORDER by time DESC,id DESC
                    sqlArticle= "SELECT id,title,brief,browse,image,labels,time from article WHERE binary labels like ? ORDER by time DESC,id DESC  LIMIT 0,?";
                    sqlCount = "SELECT COUNT(*) from article WHERE binary labels like ? ORDER by time DESC,id DESC";
                }else{
                    sqlArticle= "select id,title,brief,browse,image,labels,time from article WHERE title like ? ORDER by time DESC,id DESC LIMIT 0,?";
                    sqlCount = "SELECT COUNT(*) from article WHERE  title like ? ORDER by time DESC,id DESC";
                }
                PreparedStatement statement = mysqluse.initperpare(sqlArticle);
                statement.setString(1,"%"+type+"%");
                statement.setInt(2,size);
                ResultSet rs= statement.executeQuery();
                whil(list, rs,size);
                countx.setList(list);
                rs.close();
                statement.close();
                //获取 总数
                PreparedStatement statementCount = mysqluse.initperpare(sqlCount);
                statementCount.setString(1,"%"+type+"%");
                ResultSet resultSet =  statementCount.executeQuery();
                resultSet.next();
                countx.setCount(resultSet.getInt(1));
                data.set(0,countx);
                resultSet.close();
                statementCount.close();
                mysqluse.close();
            } catch (SQLException e) {
                e.printStackTrace();
                data.set(-3,"数据库错误");
            }
        }else {
            int pageMin = (page-1)*10;
            int pageMax = page*10;
//            SELECT id,title,image,labels from article WHERE labels like "%CSS%" LIMIT 0,10;;
            try {
                String sql;
                if (mode == 0){
                    sql= "SELECT id,title,brief,browse,image,labels,time from article WHERE binary labels like ? ORDER by time DESC,id DESC LIMIT ?,?";
                }else{
                    sql= "SELECT id,title,brief,browse,image,labels,time from article WHERE title like ? ORDER by time DESC,id DESC LIMIT ?,?";
                }
                PreparedStatement statement = mysqluse.initperpare(sql);
                statement.setString(1,"%"+type+"%");
                statement.setInt(2,pageMin);
                statement.setInt(3,pageMax);
                ResultSet rs= statement.executeQuery();
                whil(list, rs,size);
                data.set(0,list);
                statement.close();
                mysqluse.close();
            } catch (SQLException e) {
                e.printStackTrace();
                data.set(-2,"数据库错误");
            }
        }

        mysqluse.fa(data);


    }

    private void whil(List<ArticleData> list, ResultSet rs,int size) throws SQLException {
        while (rs.next() && list.size()<size) {
            ArticleData articleData = new ArticleData();
            articleData.setId(rs.getInt(1));
            articleData.setTitle(rs.getString(2));
            articleData.setBrief(rs.getString(3));
            articleData.setBrowse(rs.getString(4));
            articleData.setImage(rs.getString(5));
            articleData.setLabels((rs.getString(6)).split(","));
            articleData.setTime(rs.getString(7));
            list.add(articleData);
        }
        rs.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        doPost(req, resp);
    }


    class ArticleData{
        private int id;
        private String title;
        private String[] labels;
        private String image;
        private String time;
        private String brief;
        private String browse;

        public void setBrowse(String browse) {
            this.browse = browse;
        }
        public void setBrief(String brief) {
            this.brief = brief;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setTitle(String title) {
            this.title = title;
        }
        public void setLabels(String[] labels) {
            this.labels = labels;
        }
        public void setImage(String image) {
            this.image = image;
        }
        public int getId() {
            return id;
        }
        public String getTime() {
            return time;
        }
        public String getBrowse() {
            return browse;
        }
        public String[] getLabels() {
            return labels;
        }
        public String getBrief() {
            return brief;
        }
        public String getTitle() {
            return title;
        }
        public String getImage() {
            return image;
        }
    }

    class Countx {
        private List<?> list;
        private int count;
        public void setList(List<?> list) {
            this.list = list;
        }
        public void setCount(int count) {
            this.count = count;
        }

        public List<?> getList() {
            return list;
        }

        public int getCount() {
            return count;
        }
    }
}
