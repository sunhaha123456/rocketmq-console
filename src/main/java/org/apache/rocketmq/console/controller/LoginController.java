package org.apache.rocketmq.console.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Value("${login.userName}")
    private String loginUserName;

    @Value("${login.userPwd}")
    private String loginUserPwd;

    public static final String SESSION_KEY = "rocketMqLoginFlag";
    public static final String SESSION_VALUE = "rocketMqLoginSuccessSSS";

    @GetMapping(value = "/login")
    public String login() {
        return "static/login.html";
    }

    @GetMapping(value = "/")
    public String index(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null || !SESSION_VALUE.equals(session.getAttribute(LoginController.SESSION_KEY))) {
            return "static/login.html";
        }
        return "static/index.html";
    }

    @ResponseBody
    @PostMapping("/loginValidate")
    public Map<String, Object> validate(HttpServletRequest request, @RequestParam String userName, @RequestParam String userPwd) {
        Map<String, Object> map = new HashMap<>();
        if (loginUserName.equals(userName) && loginUserPwd.equals(userPwd)) {
            HttpSession session = request.getSession();
            session.setAttribute(SESSION_KEY, SESSION_VALUE);
            map.put("code", "1");
        } else {
            map.put("code", "0");
        }
        return map;
    }

    @GetMapping(value = "/out")
    public String out(HttpServletRequest request) throws Exception {
        request.getSession().invalidate();
        return "static/login.html";
    }
}