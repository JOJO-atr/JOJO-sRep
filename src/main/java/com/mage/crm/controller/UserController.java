package com.mage.crm.controller;

import com.mage.crm.base.BaseController;
import com.mage.crm.base.exceptions.ParamsException;
import com.mage.crm.dto.UserDto;
import com.mage.crm.model.MessageModle;
import com.mage.crm.model.UserModle;
import com.mage.crm.query.UserQuery;
import com.mage.crm.service.UserService;
import com.mage.crm.util.CookieUtil;
import com.mage.crm.util.CrmConstant;
import com.mage.crm.vo.User;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;
    @RequestMapping("/login")
    @ResponseBody
    public MessageModle login(String  userName, String userPwd){
        //创建messageModle对象
        MessageModle messageModle = new MessageModle();
        //调用service层
        UserModle userModle = userService.login(userName, userPwd);
        messageModle.setResult(userModle);
        return messageModle;
    }
    @RequestMapping("/updatePwd")
    @ResponseBody
    public MessageModle updatePwd(HttpServletRequest request,String oldPassword,String newPassword,String confirmPassword){
        MessageModle messageModle = new MessageModle();
        //获取参数
        String id = CookieUtil.getCookies(request,"userId");
        userService.updatePwd(id,oldPassword,newPassword,confirmPassword);
        return messageModle;
    }
    @RequestMapping("/queryAllCustomerManager")
    @ResponseBody
    public List<User> queryAllCustomerManager(){
        List<User> users = userService.queryAllCustomerManager();
        return users;
    }
    @RequestMapping("index")
    public String index(){
        return "user";
    }
    @RequestMapping("queryUsersByParams")
    @ResponseBody
    public Map<String,Object> queryUsersByParams(UserQuery userQuery){
        return userService.queryUsersByParams(userQuery);
    }
    @ResponseBody
    @RequestMapping("addUser")
    public MessageModle addUser(User user){
        userService.addUser(user);
        return createMessage("添加成功!");
    }
    @ResponseBody
    @RequestMapping("updateUser")
    public MessageModle updateUser(User user){
        userService.updateUser(user);
        return createMessage("添加成功!");
    }
    @ResponseBody
    @RequestMapping("deleteUser")
    public MessageModle deleteUser(Integer id){
        userService.deleteUser(id);
        return createMessage("删除成功!");
    }
}
