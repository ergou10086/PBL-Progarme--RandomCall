package dao.impl;

import dao.UserDao;
import util.FileUtil;
import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl implements UserDao {
    private static final String USER_FILE = "users.txt";
    private Map<String, String> userDatabase;

    public UserDaoImpl() {
        this.userDatabase = new HashMap<>();
        loadUsers();
    }

    private void loadUsers() {
        Map<String, String> users = FileUtil.readUsers(USER_FILE);
        if (users != null) {
            this.userDatabase = users;
        }
    }

    @Override
    public boolean saveUser(String username, String password) {
        userDatabase.put(username, password);
        return FileUtil.writeUsers(userDatabase, USER_FILE);
    }

    @Override
    public boolean checkUser(String username, String password) {
        return userDatabase.containsKey(username) && 
               userDatabase.get(username).equals(password);
    }

    @Override
    public boolean isUserExists(String username) {
        return userDatabase.containsKey(username);
    }

    @Override
    public Map<String, String> getAllUsers() {
        return new HashMap<>(userDatabase);
    }
} 