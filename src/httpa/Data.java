package httpa;

public class Data {
    private int code=-1;
    private Object data=null;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public void set(int code,Object data) {
        this.code = code;
        this.data = data;
    }
}
