package httpa;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "LoginFilter",urlPatterns = "/admin/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig)  {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response =(HttpServletResponse)servletResponse;
        HttpServletRequest  request  = (HttpServletRequest) servletRequest;
//        response.setHeader("Access-Control-Allow-Origin","http://localhost:8081");
//        response.setHeader("Access-Control-Allow-Origin","http://localhost:8080");
//        response.setContentType("text/json;charset=UTF-8");
        /* 允许跨域的请求方法GET, POST, HEAD 等 */
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, accept, content-type, xxxx");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        /* 重新预检验跨域的缓存时间 (s) */
        response.setHeader("Access-Control-Max-Age", "3600");
        /* 允许跨域的请求头 */
        response.setHeader("Access-Control-Allow-Headers", "*");
        /* 是否携带cookie */
        response.setHeader("Access-Control-Allow-Credentials", "true");
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String name= (String) session.getAttribute("name");
        // 判断 是否 登陆过 （ session）
        if(name != null){
            filterChain.doFilter(request,response);
        }else{
            PrintWriter writer = response.getWriter();
            writer.print(-1);
            writer.close();
        }
    }

    @Override
    public void destroy() {

    }
}
