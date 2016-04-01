package il.ac.huji.todolist.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_TODO = "todo";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DUE = "due";

    public DBHelper(Context context) {
        super(context, "todo_db", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_TODO + " (" +
                        COLUMN_ID + " integer primary key autoincrement, " +
                        COLUMN_TITLE + " string, " +
                        COLUMN_DUE + " long);"
        );
    }
    public void onUpgrade(
            SQLiteDatabase db, int oldVer, int newVer) {
    }
}
