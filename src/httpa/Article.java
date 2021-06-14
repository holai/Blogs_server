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

public class Article extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        Data data = new Data();
        Mysqluse mysqluse = new Mysqluse(resp);
//        if (!mysqluse.NoNull(id)){
//            data.set(-1,"参数错误");
//            mysqluse.fa(data);
//            return;
//        }
        String sql = "select title,text,time,browse,labels,image from article where(id=?)";
        PreparedStatement statement = mysqluse.initperpare(sql);
        try {
            statement.setString(1,id);
            ResultSet rs = statement.executeQuery();
            Articlebook articlebook = new Articlebook();
            while (rs.next()){
                articlebook.setTitle(rs.getString("title"));
                articlebook.setBrowse(rs.getString("browse"));
                articlebook.setLabels(rs.getString("labels").split(","));
                articlebook.setText(rs.getString("text"));
                articlebook.setTime(rs.getString("time"));
                articlebook.setImage(rs.getString("image"));
            }
            data.set(0,articlebook);
            statement.close();
            rs.close();
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
    class Articlebook{
        private String title="空";
        private String text=".";
        private String time="0-0-0-0";
        private String browse="999";
        private String[] labels;
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setBrowse(String browse) {
            this.browse = browse;
        }

        public void setLabels(String[] labels) {
            this.labels = labels;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
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
    }
}

