package httpa;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
public class Mysqluse {
    private Connection con;
    private int code=-1;
    private HttpServletResponse resp;
    //     -2 驱动异常  -3连接异常
    Mysqluse(HttpServletResponse resp){
        String driver = "com.mysql.cj.jdbc.Driver";
            //String url = "jdbc:mysql://localhost:3306/lu?useSSL=false";
            String url = "jdbc:mysql://localhost:3306/lu?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            String user = "root";
            String password = "";
            try {
                //加载驱动程序
                Class.forName(driver);
                //1.getConnection()方法，连接MySQL数据库！！
                this.con = DriverManager.getConnection(url,user,password);
                if(!this.con.isClosed())
                    this.code=0;
            } catch(ClassNotFoundException e) {
                //数据库驱动类异常处理
                this.code=-2;
                e.printStackTrace();
            } catch(SQLException e) {
                //数据库连接失败异常处理
                this.code = -3;
                e.printStackTrace();
            }
        this.resp = resp;

    }
//    static void setRes(HttpServletResponse resp){
//        Mysqluse.resp = resp;
//    }
    public Statement init(){
        Statement statement = null;
        try {
            //2.创建statement类对象，用来执行SQL语句！！
            statement = this.con.createStatement();
        } catch(SQLException e) {
            //数据库连接失败异常处理
            code = -3;
            e.printStackTrace();
        }
        return statement;
    }
    public PreparedStatement initperpare(String s) {
        PreparedStatement statement = null;
        try {
            //2.创建statement类对象，用来执行SQL语句！！
            statement = con.prepareStatement(s);
        }catch(SQLException e) {
            //数据库连接失败异常处理
            code = -3;
            e.printStackTrace();
        }
        return statement;
    }

    public int getCode() {
        return code;
    }

    public void close(){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fa(String code){
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
            writer.println(code);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 返回数据
    public void fa(Data data) {
        String textjson = JSON.toJSONString(data);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            out.append(textjson);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    // 判断 是否 为 空 或者 null
    public boolean  NoNull(String... str){
        for (String item : str){
//            || sqlValidate(item)  特殊字符
            if ( item == null || item.equals("") ){
                return false;
            }
        }
        return true;
    }

    // 过滤 特殊字符
    protected static   boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        String badStr = "'|select|update|and|or|delete|insert|truncate|char|into"
                + "|substr|declare|exec|master|drop|execute|"
                + "union|--|like|%|#|*|$|@|\"|http|lf|";//过滤掉的sql关键字，可以手动添加
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            if (str.contains(badStrs[i])) {
                return false;
            }
        }
        return true;
    }
}






