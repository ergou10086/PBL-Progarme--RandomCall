package exceptions;

public class StudentManageExceptions extends RuntimeException{
    public StudentManageExceptions(String message){
        super(message);
    }

    public StudentManageExceptions(String message, Throwable cause){
        super(message, cause);
    }

    public StudentManageExceptions(Throwable cause){
        super(cause);
    }


    public static class InvalidNumberOfStudentException extends StudentManageExceptions{
        public InvalidNumberOfStudentException(String message, Throwable cause){
            super(message, cause);
        }
    }
}
