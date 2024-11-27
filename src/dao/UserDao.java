package dao;

import java.util.Map;

public interface UserDao {
    boolean saveUser(String username, String password);
    boolean checkUser(String username, String password);
    boolean isUserExists(String username);
    Map<String, String> getAllUsers();
} 