package httpa;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
/***
 *    code返回
 *    0成功   1账号或密码为空 或者 有特殊字符   2驱动异常  3连接异常   4数据库提取错误
 */
public class admin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        // 获得参数  name 和 mm
        String name=(String) req.getParameter("name");
        String mm =(String) req.getParameter("mm");
        // 获取 session
        HttpSession session = req.getSession();
        String namea= (String) session.getAttribute("name");
        Data data = new Data();
        data.setCode(-1);
        Mysqluse mysqluse = new Mysqluse(resp);
        // 判断 是否 登陆过 （ session）
        if(namea != null){
            data.set(0,"登陆过");
        }else if(mysqluse.NoNull(mm,name)) {
            String sql = "select mm from adminuse where(name=?)";
            PreparedStatement statement=mysqluse.initperpare(sql);
                try {
                    statement.setString(1,name);
                    ResultSet rs = statement.executeQuery();
                    if(rs.next()){
                        String mma = rs.getString(1);
                        if (mm.equals(mma)) {
                            session.setAttribute("name",name);
                            data.setCode(0);
                        }else{
                            data.set(-5,"密码错误");
                        }
                    }else{
                        data.set(-4,"账号错误");
                    }
//                    rs.close();
                    statement.close();
                    mysqluse.close();
                } catch (SQLException e) {
                    data.set(-3,"数据库命令错误");
                    System.out.println(e);
                }

        }else{
            data.set(-2,"账号密码为空！");
        }

        mysqluse.fa(data);
    }




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
        //        Cookie cookie = new Cookie("use", "123456");
//        //URLEncoder.encode("中文转 url格式");
//        cookie.setMaxAge(-1);
//        PrintWriter writer = resp.getWriter();
//        writer.print("dddd发");
//        resp.addCookie(cookie);
//        Cookie[] cookies = req.getCookies();
//        for (Cookie cok : cookies){
//            System.out.println(cok.getName() + "："+cok.getValue());
//        }

    }
}
