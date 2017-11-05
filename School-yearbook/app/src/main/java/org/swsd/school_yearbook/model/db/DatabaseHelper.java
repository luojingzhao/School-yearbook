package org.swsd.school_yearbook.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 张昭锡 on 2017/11/5.
 */

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String CREATE_BOOK = "create table Book("
    + "id integer primary key autoincream,"
    + "name text,"
    + "address text,"
    + "phone text,"
    + "wechat text,"
    + "email text,"
    + "qq text,"
    + "signature text"
    + "avatarpath text)";

    private Context mContext;

    public DatabaseHelper(Context context,String name,SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
