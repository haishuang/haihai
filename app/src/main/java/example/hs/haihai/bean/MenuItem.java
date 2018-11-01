package example.hs.haihai.bean;

public class MenuItem {
    //标题
    private String title;
    //描述
    private String desc;

    public MenuItem() {

    }
    public MenuItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
