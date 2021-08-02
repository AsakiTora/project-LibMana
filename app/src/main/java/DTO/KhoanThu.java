package DTO;

public class KhoanThu {
    int id;
    String name, date, note;
    byte[] image;
    double money;

    public static final String TB_NAME = "thunhap";
    public static final String COL_ID = "id";
    public static final String COL_IMG = "image";
    public static final String COL_NAME = "name";
    public static final String COL_DATE = "date";
    public static final String COL_MONEY = "money";
    public static final String COL_NOTE = "note";



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
