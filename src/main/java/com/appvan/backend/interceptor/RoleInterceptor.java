package com.appvan.backend.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("/login.html");
            return false;
        }

        String role = (String) session.getAttribute("role");

        String pagina = request.getRequestURI();

        if (pagina.contains("dashboard-admin.html")) {

            if (!"ADMIN".equals(role)) {
                response.sendRedirect("/dashboard.html");
                return false;
            }
        }

        return true;
    }
}
