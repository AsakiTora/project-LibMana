package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import CreateDb.CreateDb;
import DTO.LoaiChi;
import DTO.LoaiThu;

public class TbLoaiThu {
    private SQLiteDatabase database;
    private CreateDb createDb;

    public TbLoaiThu(Context context){
        createDb = new CreateDb(context);
    }

    public void open(){
        database = createDb.getWritableDatabase();
    }

    public void close(){
        createDb.close();
    }

    public long Add(LoaiThu loaiThu){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoaiThu.COL_NAME, loaiThu.getName());
        contentValues.put(LoaiThu.COL_IMG, loaiThu.getImage());

        long res = database.insert(LoaiThu.TB_NAME, null, contentValues);
        return res;
    }

    public int Update(LoaiThu loaiThu){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoaiThu.COL_NAME, loaiThu.getName());
        contentValues.put(LoaiThu.COL_IMG, loaiThu.getImage());

        int res = database.update(LoaiThu.TB_NAME, contentValues, "id = " + loaiThu.getId(), null);
        return res;
    }

    public int Delete(LoaiThu loaiThu){
        return database.delete(LoaiThu.TB_NAME,"id = "+ loaiThu.getId(), null);
    }

    public ArrayList<LoaiThu> GetAll(){
        ArrayList<LoaiThu> list = new ArrayList<>();
        String[] allCol = {LoaiThu.COL_ID, LoaiThu.COL_NAME, LoaiThu.COL_IMG};
        Cursor cursor = database.query(LoaiThu.TB_NAME, allCol, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            LoaiThu loaiThu = new LoaiThu();
            loaiThu.setId(id);
            loaiThu.setImage(image);
            loaiThu.setName(name);
            list.add(loaiThu);
            cursor.moveToNext();
        }
        return list;
    }
}
