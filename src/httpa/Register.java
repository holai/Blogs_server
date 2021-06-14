package httpa;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获得参数  name 和 mm
        String name=(String) req.getParameter("name");
        String mm =(String) req.getParameter("mm");
        // 获取 session
        HttpSession session = req.getSession();
//        String namea= (String) session.getAttribute("name");
        Data data = new Data();
        data.setCode(-1);
        Mysqluse mysqluse = new Mysqluse(resp);
        // 判断 是否 登陆过 （ session）
//        if(namea != null){
//            data.set(0,"登陆过");
//        }else
    if(mysqluse.NoNull(mm,name)) {
            if(mm.length() < 6 || name.length()<6 || mm.length()>10 || name.length()>10){
                data.set(-6,"不符合安全规则");
            }else{
                try {
                    String isSql = "select count(*) from adminuse where(name=?)";
                    PreparedStatement statement=mysqluse.initperpare(isSql);
                    statement.setString(1,name);
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);
                    System.out.println("dddddddddddd:"+count);
                    if(count==0){
                        String sql = "INSERT INTO adminuse(`name`,mm) VALUES(?,?)";
                        PreparedStatement insetSt=mysqluse.initperpare(sql);
                        insetSt.setString(1,name);
                        insetSt.setString(2,mm);
                        insetSt.executeUpdate();
//                    ResultSet resultSets = insetSt.executeQuery();
//                    if(resultSets.next()){
//                        String mma = rs.getString(1);
//                        if (mm.equals(mma)) {
                        session.setAttribute("name",name);
                        data.setCode(0);
//                        }else{
//                            data.set(-5,"密码错误");
//                        }
//                    }else{
//                        data.set(-4,"账号错误");
//                    }
                    }else{
                        data.set(-2,"已经注册");
                    }

//                    rs.close();
                    statement.close();
                    mysqluse.close();
                } catch (SQLException e) {
                    data.set(-3,"数据库命令错误");
                    System.out.println(e);
                }
            }


        }else{
            data.set(-2,"账号密码为空！");
        }

        mysqluse.fa(data);
    }
}
