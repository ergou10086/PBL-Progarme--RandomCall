package exceptions;

public class OIFileOrStreamEXception extends Exception {
    public OIFileOrStreamEXception() {
        super("数据文件出现错误");
    }

    public static class ReadFileException extends Exception{
        public ReadFileException(String message) {
            super("数据文件读取出现异常");
        }
    }

    public static class LoadFileException extends Exception{
        public LoadFileException() {
            super("数据文件保存出现异常");
        }
    }

    public static class FileBreakException extends Exception{
        public FileBreakException() {
            super("数据文件损坏");
        }
    }
}
