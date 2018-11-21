package example.hs.haihai.bean;

public class DeviceInfo {
    //名称
    private String title;
    //信息
    private String info;
    //备注
    private String mark;

    public DeviceInfo() {

    }

    public DeviceInfo(String title, String info, String mark) {
        this.title = title;
        this.info = info;
        this.mark = mark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
