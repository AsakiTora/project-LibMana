package CreateDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CreateDb extends SQLiteOpenHelper {

    public static final String DB_NAME = "QLCT";
    public static final int VERSION = 1;

    public CreateDb(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String chiTieu = "CREATE TABLE chitieu (id INTEGER NOT NULL UNIQUE, name TEXT NOT NULL, note TEXT NOT NULL, date DATE NOT NULL, money TEXT NOT NULL, image BLOB NOT NULL, PRIMARY KEY(id AUTOINCREMENT))";
        db.execSQL(chiTieu);

        String thuNhap = "CREATE TABLE thunhap (id INTEGER NOT NULL UNIQUE, name TEXT NOT NULL, note TEXT NOT NULL, date DATE NOT NULL, money TEXT NOT NULL, image BLOB NOT NULL, PRIMARY KEY(id AUTOINCREMENT))";
        db.execSQL(thuNhap);

        String loaiChi = "CREATE TABLE loaichi (id INTEGER NOT NULL UNIQUE, name TEXT NOT NULL, image BLOB NOT NULL, PRIMARY KEY(id AUTOINCREMENT))";
        db.execSQL(loaiChi);

        String loaiThu = "CREATE TABLE loaithu (id INTEGER NOT NULL UNIQUE, name TEXT NOT NULL, image BLOB NOT NULL, PRIMARY KEY(id AUTOINCREMENT))";
        db.execSQL(loaiThu);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
