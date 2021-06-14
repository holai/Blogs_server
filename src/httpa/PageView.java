package httpa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//  浏览文章时请求   判断ip 是否存在  如果没有则访问量 +1
public class PageView extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        String id = req.getParameter("id");
        String ip = null;
        try {
            ip = IPUtil.getIpAddress(req);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Mysqluse mysqluse = new Mysqluse(resp);
        if (mysqluse.NoNull(id+ip)){
           Statement statement = mysqluse.init();
            try {
               ResultSet resultSet = statement.executeQuery(" select * from pageview where id="+id+" and ip='"+ip+"'");
                resultSet.last();
               if (resultSet.getRow()==0){

                   if(ip.equals("0:0:0:0:0:0:0:1")){
                       ip="114.247.50.2";
                   }
                   String str = HttpUtil.get("https://api.map.baidu.com/location/ip","ak=qkwcApQmdZAgDVS7IGzSvsrGLWKlqBM8&coor=bd09ll&ip="+ip);
                   String doc = decode1(str);
                   JSONObject jsonObject = JSON.parseObject(doc);
                   System.out.println(jsonObject);
                   JSONObject content = jsonObject.getJSONObject("content");
                   JSONObject address_detail = content.getJSONObject("address_detail");
                   String province = (String) address_detail.getString("province");
                   String city = (String) address_detail.getString("city");

                   statement.executeUpdate(" insert into pageview(id,ip,city,province) values("+id+",'"+ip+"','"+city+"','"+province+"')");
                   ResultSet rs = statement.executeQuery("select browse from article where id="+id);
                   rs.next();
                   int browse = Integer.parseInt(rs.getString("browse"))+1;
                   statement.executeUpdate("update article set browse='"+browse+"' where id="+id);

               }

//                System.out.println(ip+"\n"+str+"\n"+doc);
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    statement.close();
                    mysqluse.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    static final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");


    public static String decode1(String s) {

        Matcher m = reUnicode.matcher(s);

        StringBuffer sb = new StringBuffer(s.length());

        while (m.find()) {

            m.appendReplacement(sb,

                    Character.toString((char) Integer.parseInt(m.group(1), 16)));

        }

        m.appendTail(sb);

        return sb.toString();

    }

}


