package example.hs.haihai.bean;

public class Contacts {
    private String contactId;
    private String name;
    private String mobile;
    private String pinyin;//拼音
    private String letter;//首字母

    public Contacts() {
    }

    public Contacts(String contactId, String name, String mobile) {
        this.contactId = contactId;
        this.name = name;
        this.mobile = mobile;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
