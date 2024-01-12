package top.mitrecx.dazhixianxian.common.dataformat;

public class DataFormatException extends RuntimeException {

    private static final long serialVersionUID = -1L;

    public DataFormatException(String m) {
        super(m);
    }

    public DataFormatException(Throwable t) {
        super(t);
    }

    public DataFormatException(String m, Throwable t) {
        super(m, t);
    }

}
