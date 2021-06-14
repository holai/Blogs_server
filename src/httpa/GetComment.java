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

@WebServlet("/GetComment")
public class GetComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        String articleId = req.getParameter("articleId");
        Mysqluse mysqluse = new Mysqluse(resp);
        Data data = new Data();
        String sql = "select txt,userName,date,avatar from comment where articleId = ?";
        ArrayList<CommentData> arrayList = new ArrayList<>();
        try {
            PreparedStatement initperpare = mysqluse.initperpare(sql);
            initperpare.setString(1,articleId);
            ResultSet resultSet = initperpare.executeQuery();

            while (resultSet.next()){
                CommentData commentData = new CommentData();
                commentData.setTxt(resultSet.getString("txt"));
                commentData.setUserName(resultSet.getString("userName"));
                commentData.setDate(resultSet.getString("date"));
                commentData.setAvatar(resultSet.getString("avatar"));
                arrayList.add(commentData);
            }
            data.set(0,arrayList);
            resultSet.close();
            initperpare.close();
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println(e.toString());
        }
        mysqluse.fa(data);
        mysqluse.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }


    class CommentData{
        private String txt;
        private String userName;
        private String date;
        private String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTxt() {
            return txt;
        }

        public String getUserName() {
            return userName;
        }

        public String getDate() {
            return date;
        }
    }

}
