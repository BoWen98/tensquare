package com.bowen.user.interceptor;

import com.bowen.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInperceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过拦截器");
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && !"".equals(authHeader)) {
            if (authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles != null && roles.equals("admin")){
                        request.setAttribute("claims_admin",token);
                    }
                    if (roles != null && roles.equals("user")){
                        request.setAttribute("claims_user",token);
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌不正确");
                }
            }


        }
        return true;
    }
}
