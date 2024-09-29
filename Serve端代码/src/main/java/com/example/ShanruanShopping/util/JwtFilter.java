package com.example.ShanruanShopping.util;

import com.auth0.jwt.interfaces.Claim;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * JWT过滤器，拦截除了登录和注册之外的其他所有URL请求
 */
@Slf4j
@WebFilter(filterName = "JwtFilter", urlPatterns = "/*") // 拦截所有URL
public class JwtFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        response.setCharacterEncoding("UTF-8");
        String requestURI = request.getRequestURI();

        // 排除登录和注册请求
        if (requestURI.startsWith("/user/login") || requestURI.startsWith("/user/register")
        || requestURI.startsWith("/sell/login") || requestURI.startsWith("/sell/register")
        || requestURI.startsWith("/admin/login") || requestURI.startsWith("/commodity") || requestURI.startsWith("/kind")) {
            chain.doFilter(request, response);
            return;
        }

        // 获取 header 里的 token
        final String token = request.getHeader("authorization");

        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
        } else {
            if (token == null) {
                response.getWriter().write("没有token！");
              //  System.out.println("没有token！");
                return;
            }

            Map<String, Claim> userData = JwtUtil.verifyToken(token);
            if (userData == null) {
                response.getWriter().write("token不合法！");
            //    System.out.println("token不合法！");
                return;
            }

            Integer id = userData.get("id").asInt();
            String userName = userData.get("name").asString();
            String password = userData.get("password").asString();

            // 将用户信息放到 request 中
            request.setAttribute("id", id);
            request.setAttribute("name", userName);
            request.setAttribute("password", password);

       //     System.out.println("成功token");
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }
}
