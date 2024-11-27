package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import service.UserService;
import exceptions.LoginExceptions;
import exceptions.RegisterExceptions.*;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public boolean register(String username, String password) {
        if (userDao.isUserExists(username)) {
            throw new UsernameAlreadyExistsException("用户名已存在！");
        }
        if (password.length() < 6 || password.length() > 16) {
            throw new InvalidPasswordException("密码长度必须在6-16位之间！");
        }
        return userDao.saveUser(username, password);
    }

    @Override
    public boolean login(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            try {
                throw new LoginExceptions("用户名和密码不能为空!");
            } catch (LoginExceptions e) {
                throw new RuntimeException(e);
            }
        }
        return userDao.checkUser(username, password);
    }

    @Override
    public boolean checkUserExists(String username) {
        return userDao.isUserExists(username);
    }
} 