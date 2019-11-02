package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.PermissionDao;
import com.mage.crm.dao.UserDao;
import com.mage.crm.dao.UserRoleDao;
import com.mage.crm.dto.UserDto;
import com.mage.crm.model.UserModle;
import com.mage.crm.query.UserQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.Base64Util;
import com.mage.crm.util.EmptyUtil;
import com.mage.crm.util.Md5Util;
import com.mage.crm.vo.Permission;
import com.mage.crm.vo.Role;
import com.mage.crm.vo.User;
import com.mage.crm.vo.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class UserService {
    @Resource
    private UserRoleDao userRoleDao;
    @Resource
    private UserDao userDao;
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private HttpSession session;
    public UserModle login(String userName, String userPwd) {
        //非空判断
        AssertUtil.isTrue(EmptyUtil.isEmpty(userName), "用户名不能为空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(userPwd), "用户密码不能为空!");
        //通过userName查询
        User user = userDao.queryUserByName(userName);
        //判断用户是否为空
        AssertUtil.isTrue(user==null,"用户不存在!");
        AssertUtil.isTrue("0".equals(user.getIsValid()),"用户已注销!");
        //对输入密码进行加密操作
        String encode = Md5Util.encode(userPwd);
        AssertUtil.isTrue(EmptyUtil.isEmpty(encode), 306, "操作异常!");
        //判断密码是否相等
        AssertUtil.isTrue(!encode.equals(user.getUserPwd()), "用户名或密码不正确!");
        List<String> permissions=permissionDao.queryPermissionByUid(user.getId());
        if(permissions!=null&&permissions.size()>0){
            session.setAttribute("userPermission",permissions);
        }
        return createUserMoudle(user);
    }
    public UserModle createUserMoudle(User user){
        UserModle userModle = new UserModle();
        userModle.setUserId(Base64Util.encode(user.getId()));
        userModle.setUserName(user.getUserName());
        userModle.setTrueName(user.getTrueName());
        return  userModle;
    }
    public void updatePwd(String id,String oldPwd,String newPwd,String confirmPwd){
        //非空判断
        AssertUtil.isTrue(EmptyUtil.isEmpty(id),"修改异常!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(oldPwd),"原始密码不能为空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(newPwd),"新密码不能为空!");
        AssertUtil.isTrue(EmptyUtil.isEmpty(confirmPwd),"确认密码不能为空!");
        //判断新密码和原密码是否相等
        AssertUtil.isTrue(oldPwd.equals(newPwd),"新密码不能与原密码相同!");
        //判断两次输入密码是否一致
        AssertUtil.isTrue(!newPwd.equals(confirmPwd),"两次输入密码不一致!");
        //通过ID查找对象
        User user = queryUserById(Integer.parseInt(Base64Util.decode(id)));
        //加密原始密码
        String encode = Md5Util.encode(oldPwd);
        //判断密码是否正确
        AssertUtil.isTrue(!encode.equals(user.getUserPwd()),"用户密码错误!");
        //判断用户是否注销
        AssertUtil.isTrue("0".equals(user.getIsValid()),"用户已注销");
        //执行修改密码操作
        int i = userDao.updatePwd(Integer.parseInt(user.getId()), Md5Util.encode(newPwd));
        AssertUtil.isTrue(i<1,"操作失败");
    }
    public  User queryUserById(Integer id){
        User user = userDao.queryUserById(id);
        return user;
    }
    public List<User> queryAllCustomerManager(){
        List<User> users = userDao.queryAllCustomerManager();
        return  users;
    }

    public Map<String,Object> queryUsersByParams(UserQuery userQuery) {
        PageHelper.startPage(userQuery.getPage(),userQuery.getRows());
        List<UserDto> users=userDao.queryUsersByParams(userQuery);
        if(users.size()>0&&users!=null){
            for(UserDto userDto:users){
                List<Integer> roleIds = userDto.getRoleIds();
                String str = userDto.getRoleIdsStr();
               if(str!=null){
                   String[] roleIdsStr=str.split(",");
                   for(int i=0;i<roleIdsStr.length;i++){
                       roleIds.add(Integer.parseInt(roleIdsStr[i]));
                   }
               }
            }
        }
        PageInfo<UserDto> pageInfo = new PageInfo<>(users);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }

    public void addUser(User user) {
        checkParams(user.getUserName(), user.getTrueName(), user.getPhone());
        AssertUtil.isTrue(userDao.queryUserByName(user.getUserName())!=null,"用户名已存在!");
        user.setIsValid(1);
        user.setUserPwd(Md5Util.encode("123456"));
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(userDao.addUser(user)<1,"添加用户失败!");
        String id = user.getId();
        List<Integer> roleIds = user.getRoleIds();
        if(roleIds!=null&roleIds.size()>0){
            relateRoles(id,roleIds);
        }
    }

    private void relateRoles(String userId, List<Integer> roleIds) {
        List<UserRole> userRoles = new ArrayList<>();
        for(int roleId:roleIds){
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(Integer.parseInt(userId));
            userRole.setIsValid(1);
            userRole.setCreateDate(new Date());
            userRole.setUpdateDate(new Date());
            userRoles.add(userRole);
        }
        AssertUtil.isTrue(userRoleDao.insertBatch(userRoles)<1,"用户角色添加失败!");
    }

    public void checkParams(String userName, String trueName, String phone) {
        AssertUtil.isTrue(EmptyUtil.isEmpty(userName), "用户名不能为空");
        AssertUtil.isTrue(EmptyUtil.isEmpty(trueName), "真实姓名不能为空");
        AssertUtil.isTrue(EmptyUtil.isEmpty(phone), "手机号不能为空");
    }

    public void updateUser(User user) {
        checkParams(user.getUserName(), user.getTrueName(), user.getPhone());
        User u = userDao.queryUserByName(user.getUserName());
        AssertUtil.isTrue(u!=null&&!u.getId().equals(user.getId()),"用户名已存在!");
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(userDao.updateUser(user)<1,"修改用户失败!");
        String userId= user.getId();
        int count = userRoleDao.queryRoleCountsByUserId(user.getId());
        if(count>0){
            AssertUtil.isTrue(userRoleDao.deleteUserRoleByUserId(user.getId())<1,"用户更新失败！");
        }
        List<Integer> roleIds = user.getRoleIds();
        if (roleIds!=null&&roleIds.size()>0){
            relateRoles(userId,roleIds);
        }
    }

    public void deleteUser(Integer id) {
        AssertUtil.isTrue(userDao.deleteUser(id) < 1, "删除用户失败!");
        int count = userRoleDao.queryRoleCountsByUserId(String.valueOf(id));
        if (count > 0) {
            AssertUtil.isTrue(userRoleDao.deleteUserRoleByUserId(String.valueOf(id)) < count, "用户删除失败！");
        }
    }
}
