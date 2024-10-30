package exceptions;

// 自定义异常类，继承自 Exception
public class LoginExceptions extends Exception {
    // 默认构造函数
    public LoginExceptions() {
        super("登录错误");
    }

    // 带消息的构造函数
    public LoginExceptions(String message) {
        super(message);
    }

    // 带消息和原因的构造函数
    public LoginExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}