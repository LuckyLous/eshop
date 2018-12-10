package com.hello.eshop.controller;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.User;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台会员管理
 * Created by Hello on 2018/4/28.
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id")Integer id, HttpServletRequest request,
                      @RequestParam(value="page",required=true,defaultValue="1") Integer page){

        userService.delete(id);
        request.setAttribute("msg","删除成功！");
        request.setAttribute("newUrl","user/list?page="+page);
        return "forward:/admin/common/msg.jsp";

    }

    @RequestMapping("/list")
    public String list(@RequestParam(value="page",required=true,defaultValue="1") Integer pageNum,
                       @RequestParam(value="status", required = false) Integer status,Model model){

        PageInfo<User> pageInfo = userService.list(status, pageNum, Constant.USER_LIST_PAGE);
        model.addAttribute("pageInfo",pageInfo);
        return "forward:/admin/user/list.jsp";
    }

}
