package exceptions;

// 自定义异常类，用于点名相关操作中出现的特定异常情况
public class RandomExceptions extends Exception {

    public RandomExceptions(String message) {
        super(message);
    }

    public RandomExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    // 未找到学生异常
    public static class NotFindExceptions extends Exception {
        public NotFindExceptions(String message) {
            super(message);
        }

        public NotFindExceptions(String message, Throwable cause) {
            super(message, cause);
        }
    }

    // 成绩无效异常
    public static class ErrorScoreExceptions extends Exception {
        public ErrorScoreExceptions(String message) {
            super(message);
        }

        public ErrorScoreExceptions(String message, Throwable cause) {
            super(message, cause);
        }
    }


    // 更新成绩异常
    public static class UpdateScoreExceptions extends Exception{
        public UpdateScoreExceptions(String message) {
            super(message);
        }
        public UpdateScoreExceptions(String message, Throwable cause) {
            super(message, cause);
        }
        public UpdateScoreExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

    // 分数编辑异常
    public static class EditScoreExceptions extends Exception{
        public EditScoreExceptions(String message ,Throwable cause) {
            super(message, cause);
        }
    }
}
