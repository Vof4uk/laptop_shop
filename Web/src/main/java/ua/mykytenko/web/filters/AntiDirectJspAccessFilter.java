package ua.mykytenko.web.filters;

import javax.servlet.*;
import java.io.IOException;

public class AntiDirectJspAccessFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.getRequestDispatcher("404.jsp").forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
