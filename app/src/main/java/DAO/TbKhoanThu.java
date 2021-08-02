package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import CreateDb.CreateDb;
import DTO.KhoanThu;
import DTO.LoaiChi;

public class TbKhoanThu {
    private SQLiteDatabase database;
    private CreateDb createDb;

    public TbKhoanThu(Context context){
        createDb = new CreateDb(context);
    }

    public void open(){
        database = createDb.getWritableDatabase();
    }

    public void close(){
        createDb.close();
    }

    public long Add(KhoanThu KhoanThu){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KhoanThu.COL_NAME, KhoanThu.getName());
        contentValues.put(KhoanThu.COL_IMG, KhoanThu.getImage());
        contentValues.put(KhoanThu.COL_DATE, KhoanThu.getDate());
        contentValues.put(KhoanThu.COL_MONEY, KhoanThu.getMoney());
        contentValues.put(KhoanThu.COL_NOTE, KhoanThu.getNote());

        long res = database.insert(KhoanThu.TB_NAME, null, contentValues);
        return res;
    }

    public int Update(KhoanThu KhoanThu){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KhoanThu.COL_NAME, KhoanThu.getName());
        contentValues.put(KhoanThu.COL_IMG, KhoanThu.getImage());
        contentValues.put(KhoanThu.COL_DATE, KhoanThu.getDate());
        contentValues.put(KhoanThu.COL_MONEY, KhoanThu.getMoney());
        contentValues.put(KhoanThu.COL_NOTE, KhoanThu.getNote());

        int res = database.update(KhoanThu.TB_NAME, contentValues, "id =" + KhoanThu.getId(), null);
        return res;
    }

    public int Delete(KhoanThu KhoanThu){
        return database.delete(KhoanThu.TB_NAME,"id = "+ KhoanThu.getId(), null);
    }

    public ArrayList<KhoanThu> GetAll(){
        ArrayList<KhoanThu> list = new ArrayList<>();
        String[] allCol = new String[]{KhoanThu.COL_ID, KhoanThu.COL_NAME, KhoanThu.COL_NOTE, KhoanThu.COL_DATE, KhoanThu.COL_MONEY, KhoanThu.COL_IMG};
        Cursor cursor = database.query(KhoanThu.TB_NAME, allCol, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String note = cursor.getString(2);
            String date = cursor.getString(3);
            double money = cursor.getDouble(4);
            byte[] image = cursor.getBlob(5);


            KhoanThu KhoanThu = new KhoanThu();
            KhoanThu.setId(id);
            KhoanThu.setName(name);
            KhoanThu.setMoney(money);
            KhoanThu.setDate(date);
            KhoanThu.setImage(image);
            KhoanThu.setNote(note);

            list.add(KhoanThu);
            cursor.moveToNext();
        }
        return list;
    }
}
