package org.apache.rocketmq.console;

import org.apache.rocketmq.console.controller.LoginController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${server.contextPath}")
    private String contextPath;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        return sessionValidate(request);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public boolean sessionValidate(HttpServletRequest request) {
        String method = request.getMethod();
        if ("OPTIONS".equals(method.toUpperCase())) {
            return true;
        }
        HttpSession session = request.getSession();
        if (session == null || !LoginController.SESSION_VALUE.equals(session.getAttribute(LoginController.SESSION_KEY))) {
            return false;
        }
        return true;
    }

//    private final List<String> CONTROLER_Prefix = Arrays.asList("cluster", "consumer", "dashboard", "message", "monitor", "rocketmq", "ops", "producer", "test", "topic");
//
//    public boolean sessionValidate(HttpServletRequest request) {
//        String method = request.getMethod();
//        if ("OPTIONS".equals(method.toUpperCase())) {
//            return true;
//        }
//        String url = request.getRequestURI().toLowerCase();
//        String controllerPrefix = null;
//        if (!url.contains(contextPath.toLowerCase())) {
//            return false;
//        }
//        try {
//            String str = url.substring(contextPath.length());
//            if (str != null && str.startsWith("/")) {
//                str = str.substring(1);
//                if (str.contains("/")) {
//                    controllerPrefix = str.substring(0, str.indexOf("/"));
//                }
//            }
//        } catch (Exception e) {
//            controllerPrefix = null;
//        }
//        if (controllerPrefix != null && CONTROLER_Prefix.contains(controllerPrefix)) {
//            HttpSession session = request.getSession();
//            if (session == null) {
//                return false;
//            }
//            Object sessionUserIdObj = session.getAttribute(LoginController.SESSION_KEY);
//            if (sessionUserIdObj != null && (sessionUserIdObj + "").equals(LoginController.SESSION_VALUE)) {
//                return true;
//            }
//            return false;
//        }
//        return true;
//    }
}