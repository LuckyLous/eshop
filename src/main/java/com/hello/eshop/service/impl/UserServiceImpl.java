package com.hello.eshop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.Order;
import com.hello.eshop.bean.User;
import com.hello.eshop.bean.UserExample;
import com.hello.eshop.bean.UserExample.Criteria;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.dao.UserMapper;
import com.hello.eshop.exception.MessageException;
import com.hello.eshop.service.UserService;
import com.hello.eshop.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Hello on 2018/3/20.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Value("${email.HOST}")
    private String HOST;
    @Value("${email.USERNAME}")
    private String USERNAME;
    @Value("${email.PASSWORD}")
    private String PASSWORD;
    @Value("${email.FROM}")
    private String FROM;
    @Value("${email.SUBJECT}")
    private String SUBJECT;
    @Value("${email.CONTENT}")
    private String CONTENT;

    /**
     * 用户注册
     */
    @Override
    public void regist(User user) throws MessageException {
        // 1.给密码加密，调用dao添加用户
        user.setPassword(MD5Utils.md5(user.getPassword()));
        userMapper.insertSelective(user);
        // 2.发送激活邮件
        // 2.1 创建会话
        Session session = MailUtils.createSession(HOST, USERNAME, PASSWORD);
        // 2.2 创建Mail对象
        // MessageForm.format方法会把第一个参数中的{0},使用第二个参数来替换
        String content = MessageFormat.format(CONTENT, user.getNickname(),user.getActiveCode());
        Mail mail = new Mail(FROM, user.getEmail(), SUBJECT, content);
        // 2.3 发送邮件
        try {
            MailUtils.send(session, mail);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("用户注册失败...");
        }
    }

    /**
     * 用户激活
     */
    @Override
    public User active(String activeCode) {
        // 通过activeCode获取用户
        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        criteria.andActiveCodeEqualTo(activeCode);
        List<User> list = userMapper.selectByExample(example);
        // 通过激活码没有找到用户
        User user = ListUtils.listToBean(list);
        if(user == null){
            return null;
        }
        // 若获取到了修改用户
        user.setStatus(Constant.USER_IS_ACTIVE);
        user.setActiveCode(null);
        userMapper.updateByPrimaryKey(user);// 不用selected，不然null值的字段不会更新
        return user;


    }

    /**
     * 用户登录
     */
    @Override
    public User login(String username,String password) {
        // 通过username、password获取用户
        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        // 如果实际输入的是邮箱，就选择邮箱查
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        if(Pattern.matches(regex, username)){
            criteria.andEmailEqualTo(username);
        }else{
            criteria.andUsernameEqualTo(username);
        }
        // MD5加密
        criteria.andPasswordEqualTo(MD5Utils.md5(password));
        List<User> list = userMapper.selectByExample(example);
        return ListUtils.listToBean(list);
    }

    /**
     * 校验用户名
     * @param username
     * @return
     */
    @Override
    public User checkUsername(String username) {
        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        return ListUtils.listToBean(list);
    }

    /**
     * 校验邮箱
     * @param email
     * @return
     */
    @Override
    public User checkEmail(String email) {
        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        criteria.andEmailEqualTo(email);
        List<User> list = userMapper.selectByExample(example);
        return ListUtils.listToBean(list);
    }

    /**
     * 更新用户信息
     * @param user
     */
    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);// 不强制更新，null值就不更新
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Override
    public User getById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据状态查询会员列表
     * @param status
     */
    @Override
    public PageInfo<User> list(Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        UserExample example = new UserExample();
        Criteria criteria = example.createCriteria();
        // 如果状态不为空，则增加查询条件
        if(status != null){
            criteria.andStatusEqualTo(status);
        }

        List<User> list = userMapper.selectByExample(example);
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return pageInfo;
    }

    /**
     * 根据id删除会员
     * @param id
     */
    @Override
    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }


}
