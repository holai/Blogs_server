package httpa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.net.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/github")
public class Github  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String code = req.getParameter("code");
//        String code = req.getParameter("code");
        // 取回 token
        String token = HttpUtil.get("https://github.com/login/oauth/access_token","code="+code+"&client_id=&client_secret=");
        String atoke = token.split("&")[0];
        Mysqluse mysqluse = new Mysqluse(resp);
        String name=atoke;
        System.out.println(atoke);
        if(atoke.contains("token")){
            // 传递 token  获取  用户信息
            String str = HttpUtil.get("https://api.github.com/user",atoke);
            JSONObject jsonObject = JSON.parseObject(str);
            name = jsonObject.getString("login");
            String id = jsonObject.getString("id");
            String sql ="insert into githubuser (w_id,name,avatar,type) values (?,?,?,?);";
            PreparedStatement preparedStatement =  mysqluse.initperpare(sql);
            try {
                preparedStatement.setString(1,id);
                preparedStatement.setString(2,name);
                preparedStatement.setString(3,"https://avatars0.githubusercontent.com/"+name);
                preparedStatement.setInt(4,0);
                boolean is = preparedStatement.execute();
                if(is){
                    //已经登陆过
                }else{
                    // 从来没有登陆过
                }
            } catch (Exception e) {
                System.out.println(e);
            }finally {
                try {
                    preparedStatement.close();
                    System.out.println(name);
                    resp.sendRedirect(req.getContextPath()+"succeed.jsp?login=github&id="+id+"&name="+ (URLEncoder.encode(name, "utf-8")) +"&avatar=https://avatars0.githubusercontent.com/"+name);

//                    resp.sendRedirect(req.getContextPath()+"succeed.jsp?login=github&id="+id+"&name="+name);
                } catch (SQLException e) {
                    System.out.println(e.getLocalizedMessage());
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }else{
            try {
                resp.sendRedirect(req.getContextPath()+"failure.jsp?token"+token);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

//        Data data = new Data();
//        data.set(0,name);
//        mysqluse.fa(data);
        mysqluse.close();
//        URL urlx = new URL("https://api.github.com/user?"+token);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
