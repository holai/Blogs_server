package httpa;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "CorsFilter",urlPatterns = "/*")
public class CorsFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {

        HttpServletResponse response =(HttpServletResponse)servletResponse;
        HttpServletRequest  request  = (HttpServletRequest) servletRequest;
        String originHeader=request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);

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

        chain.doFilter(servletRequest, servletResponse);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
