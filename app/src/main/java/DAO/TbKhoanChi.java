package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import CreateDb.CreateDb;
import DTO.KhoanChi;
import DTO.LoaiChi;

public class TbKhoanChi {
    private SQLiteDatabase database;
    private CreateDb createDb;

    public TbKhoanChi(Context context){
        createDb = new CreateDb(context);
    }

    public void open(){
        database = createDb.getWritableDatabase();
    }

    public void close(){
        createDb.close();
    }

    public long Add(KhoanChi khoanChi){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KhoanChi.COL_NAME, khoanChi.getName());
        contentValues.put(KhoanChi.COL_IMG, khoanChi.getImage());
        contentValues.put(KhoanChi.COL_DATE, khoanChi.getDate());
        contentValues.put(KhoanChi.COL_MONEY, khoanChi.getMoney());
        contentValues.put(KhoanChi.COL_NOTE, khoanChi.getNote());

        long res = database.insert(KhoanChi.TB_NAME, null, contentValues);
        return res;
    }

    public int Update(KhoanChi khoanChi){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KhoanChi.COL_NAME, khoanChi.getName());
        contentValues.put(KhoanChi.COL_IMG, khoanChi.getImage());
        contentValues.put(KhoanChi.COL_DATE, khoanChi.getDate());
        contentValues.put(KhoanChi.COL_MONEY, khoanChi.getMoney());
        contentValues.put(KhoanChi.COL_NOTE, khoanChi.getNote());

        int res = database.update(KhoanChi.TB_NAME, contentValues, "id =" + khoanChi.getId(), null);
        return res;
    }

    public int Delete(KhoanChi khoanChi){
        return database.delete(KhoanChi.TB_NAME,"id = "+ khoanChi.getId(), null);
    }

    public ArrayList<KhoanChi> GetAll(){
        ArrayList<KhoanChi> list = new ArrayList<>();
        String[] allCol = new String[]{KhoanChi.COL_ID, KhoanChi.COL_NAME, KhoanChi.COL_NOTE, KhoanChi.COL_DATE, KhoanChi.COL_MONEY, KhoanChi.COL_IMG};
        Cursor cursor = database.query(KhoanChi.TB_NAME, allCol, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String note = cursor.getString(2);
            String date = cursor.getString(3);
            double money = cursor.getDouble(4);
            byte[] image = cursor.getBlob(5);


            KhoanChi khoanChi = new KhoanChi();
            khoanChi.setId(id);
            khoanChi.setName(name);
            khoanChi.setMoney(money);
            khoanChi.setDate(date);
            khoanChi.setImage(image);
            khoanChi.setNote(note);

            list.add(khoanChi);
            cursor.moveToNext();
        }
        return list;
    }
}
