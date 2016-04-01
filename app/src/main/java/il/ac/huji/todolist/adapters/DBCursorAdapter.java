package il.ac.huji.todolist.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import il.ac.huji.todolist.data.TaskItem;
import il.ac.huji.todolist.helpers.DBHelper;


public class DBCursorAdapter {

    private SQLiteDatabase db;
    private DBHelper helper;
    private String[] allColumns = { DBHelper.COLUMN_ID, DBHelper.COLUMN_TITLE,
            DBHelper.COLUMN_DUE };

    public DBCursorAdapter(Context context) {
        helper = new DBHelper(context);
    }

    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public TaskItem createTaskItem(String title, Long due) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TITLE, title);
        values.put(DBHelper.COLUMN_DUE, due);
        long insertId = db.insert(DBHelper.TABLE_TODO, null,
                values);
        Cursor cursor = db.query(DBHelper.TABLE_TODO,
                allColumns, DBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToLast();
        TaskItem newTaskItem = cursorToTaskItem(cursor);
        cursor.close();
        return newTaskItem;
    }

    public void deleteTaskItem(TaskItem taskItem) {
        long id = taskItem.getId();
        System.out.println("Todo list item with id: " + id + "deleted.");
        db.delete(DBHelper.TABLE_TODO, DBHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<TaskItem> getAllTasks() {
        List<TaskItem> taskItems = new ArrayList<>();

        Cursor cursor = db.query(DBHelper.TABLE_TODO,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TaskItem taskItem = cursorToTaskItem(cursor);
            taskItems.add(taskItem);
            cursor.moveToNext();
        }
        cursor.close();
        return taskItems;
    }

    private TaskItem cursorToTaskItem(Cursor cursor) {
        TaskItem taskItem = new TaskItem();
        taskItem.setId(cursor.getLong(0));
        taskItem.setTask(cursor.getString(1));
        taskItem.setDate(cursor.getLong(2));
        return taskItem;
    }

}
