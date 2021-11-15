package com.lecture.interceptor;

import com.lecture.annotations.IsLogin;
import com.lecture.service.UserService;
import com.lecture.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @className: LoginInterceptor
 * @description: TODO
 * @author: carl
 * @date: 2021/11/15 18:45
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    /**
     * 是否进行拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
        String token = null;
        try {
            token = request.getHeader("token");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (DataUtils.isEmpty(token) || DataUtils.isEmpty(userService.getByToken(token, response))) {
            return false;
        }
        return true;
    }
}
