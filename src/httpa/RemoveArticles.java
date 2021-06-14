package httpa;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class RemoveArticles extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        String id = req.getParameter("id");
        // 获取到  /img/xxxxxxxx.jpg
        String imgsrc = req.getParameter("imgsrc");
        // 需要将 / 替换为 \
        String dz = (req.getServletContext().getRealPath("./")+imgsrc).replace("/","\\");
        Data data = new Data();
        Mysqluse mysqluse  = new Mysqluse(resp);
        if (mysqluse.NoNull(id)){
           Statement statement = mysqluse.init();
            try {
                int code = statement.executeUpdate("delete from article where(id='"+id+"')");
                if (code>0){
                    File file = new File(dz);
                    if (file.exists() && file.isFile()) {
                        file.delete();
                    }
                    data.set(0,"删除成功");
                }else {
                    data.set(1,"删除失败");
                }
//                statement.close();
//                mysqluse.close();
            } catch (SQLException e) {
                e.printStackTrace();
                data.set(-3,"数据库错误");
            }
        }else {
            data.set(-2,"id错误");
        }
        mysqluse.fa(data);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
