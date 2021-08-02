package DTO;

public class LoaiThu {
    String name;
    int id;
    byte[] image;

    public static final String TB_NAME = "loaithu";
    public static final String COL_ID = "id";
    public static final String COL_IMG = "image";
    public static final String COL_NAME = "name";

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
