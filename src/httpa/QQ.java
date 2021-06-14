package httpa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/qq")
public class QQ extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String ress = "";
//        String token = URI.create(String.valueOf(req.getRequestURL())).getFragment();
        String code = req.getParameter("code");
//        String requestURI = req.getRequestURL();
//        System.out.println(token);
//        System.out.println(req.getRequestURI()+"   dddd");
//        System.out.println(req.getRequestURL());
//        System.out.println("ddd");
//        String atoke = token.split("&")[0];
        // 取回 token
        String tokenBody = HttpUtil.get(" https://graph.qq.com/oauth2.0/token","grant_type=authorization_code&client_id=&client_secret=&code="+code+"&redirect_uri=https://houlai.fun/qq&fmt=json");
        String token = JSONObject.parseObject(tokenBody).getString("access_token");

        String openidBody = HttpUtil.get("https://graph.qq.com/oauth2.0/me","access_token="+token+"&fmt=json");
        String openid = JSONObject.parseObject(openidBody).getString("openid");
        String client_id = JSONObject.parseObject(openidBody).getString("client_id");
        Mysqluse mysqluse = new Mysqluse(resp);
//        String name=atoke;

//        System.out.println(openid);
//        if(atoke.contains("token")){
            // 传递 token  获取  用户信息
        String str = HttpUtil.get("https://graph.qq.com/user/get_user_info", "access_token=" + token + "&oauth_consumer_key=" + client_id + "&openid=" + openid,"UTF-8");
        JSONObject info = JSONObject.parseObject(str);
        String nickname = info.getString("nickname");
        String figureurl = info.getString("figureurl");
//        System.out.println(nickname);
        //用户信息接口
//        System.out.println(  str);
//        Data data = new Data();
//        data.set(0,token+"     \n"+"access_token=" + token + "&oauth_consumer_key=" + client_id + "&openid=" + openid +" \n" +str);
//        mysqluse.fa(data);

//            JSONObject jsonObject = JSON.parseObject(str);
//            name = jsonObject.getString("login");
//            String id = jsonObject.getString("id");
        String sql ="insert into githubuser (w_id,name,avatar,type) values (?,?,?,?);";

            PreparedStatement preparedStatement =  mysqluse.initperpare(sql);
            try {
                preparedStatement.setString(1,openid);
                preparedStatement.setString(2,nickname);
                preparedStatement.setString(3, figureurl);
                preparedStatement.setInt(4,1);
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
//                    System.out.println(name);

                    // 创建Cookie
                    Cookie cookie2 = new Cookie("name", nickname);
                    cookie2.setPath("/");
                    // 有效期,秒为单位
                    cookie2.setMaxAge(10*24*60*60);
                    // 设置cookie
                    resp.addCookie(cookie2);
                    resp.sendRedirect(req.getContextPath()+"succeed.jsp?login=qq&id="+openid+"&name="+ (URLEncoder.encode(nickname, "utf-8")) +"&avatar="+figureurl);
                } catch (SQLException e) {
                    System.out.println(e.getLocalizedMessage());
                } catch (IOException e) {
                    System.out.println(e);
                }
            }



//        }else{
//            try {
//                resp.sendRedirect(req.getContextPath()+"failure.jsp?token"+token);
//            } catch (IOException e) {
//                System.out.println(e);
//            }
//        }

        mysqluse.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }



}
