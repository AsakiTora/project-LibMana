package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import CreateDb.CreateDb;
import DTO.LoaiChi;

public class TbLoaiChi {
    private SQLiteDatabase database;
    private CreateDb createDb;

    public TbLoaiChi(Context context){
        createDb = new CreateDb(context);
    }

    public void open(){
        database = createDb.getWritableDatabase();
    }

    public void close(){
        createDb.close();
    }

    public long Add(LoaiChi loaiChi){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoaiChi.COL_NAME, loaiChi.getName());
        contentValues.put(LoaiChi.COL_IMG, loaiChi.getImage());

        long res = database.insert(LoaiChi.TB_NAME, null, contentValues);
        return res;
    }

    public int Update(LoaiChi loaiChi){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LoaiChi.COL_NAME, loaiChi.getName());
        contentValues.put(LoaiChi.COL_IMG, loaiChi.getImage());

        int res = database.update(LoaiChi.TB_NAME, contentValues, "id = " + loaiChi.getId(), null);
        return res;
    }

    public int Delete(LoaiChi loaiChi){
        return database.delete(LoaiChi.TB_NAME,"id = "+ loaiChi.getId(), null);
    }

    public ArrayList<LoaiChi> GetAll(){
        ArrayList<LoaiChi> list = new ArrayList<>();
        String[] allCol = new String[]{LoaiChi.COL_ID, LoaiChi.COL_NAME, LoaiChi.COL_IMG};
        Cursor cursor = database.query(LoaiChi.TB_NAME, allCol, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            LoaiChi loaiChi = new LoaiChi();
            loaiChi.setId(id);
            loaiChi.setImage(image);
            loaiChi.setName(name);
            list.add(loaiChi);
            cursor.moveToNext();
        }
        return list;
    }
}
