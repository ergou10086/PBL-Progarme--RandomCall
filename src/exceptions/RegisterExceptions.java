package exceptions;

public class RegisterExceptions extends RuntimeException {
    // 用户名已存在异常
    public static class UsernameAlreadyExistsException extends RuntimeException {
        public UsernameAlreadyExistsException(String message) {
            super(message);
        }
    }

    // 无效密码异常
    public static class InvalidPasswordException extends RuntimeException {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }

    // 确认密码和密码不一样异常
    public static class ConfirmErrorPasswordException extends RuntimeException {
        public ConfirmErrorPasswordException(String message) {
            super(message);
        }
    }
}
