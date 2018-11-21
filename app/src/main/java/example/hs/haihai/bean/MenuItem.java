package example.hs.haihai.bean;

public class MenuItem {
    //id,标识任务
    private int id;
    //标题
    private String title;
    //描述
    private String desc;

    public MenuItem() {

    }

    public MenuItem(int id,String title) {
        this.title = title;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
