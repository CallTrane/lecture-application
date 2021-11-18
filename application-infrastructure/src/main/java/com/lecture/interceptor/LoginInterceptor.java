package com.lecture.interceptor;

import com.lecture.annotations.IsLogin;
import com.lecture.utils.DataUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @className: LoginInterceptor
 * @description: 登录拦截器
 * @author: carl
 * @date: 2021/11/15 18:45
 */
@Service
public class LoginInterceptor implements HandlerInterceptor {

    private static final String UID = "uid";
    private static final String TOKEN = "token";

    /**
     * 是否进行拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 判断是否需要登录
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            IsLogin login = handlerMethod.getMethodAnnotation(IsLogin.class);
            if (DataUtils.isNotEmpty(login) && login.value()) {
                return loginJudge(request, response);
            }
            return true;
        }
        return false;
    }

    public boolean loginJudge(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        String token;
        try {
            token = request.getHeader(TOKEN);
            if (Objects.isNull(token)) {
                return false;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return true;
    }
}
