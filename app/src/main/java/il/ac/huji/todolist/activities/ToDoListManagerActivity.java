package il.ac.huji.todolist.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import il.ac.huji.todolist.R;
import il.ac.huji.todolist.adapters.DBCursorAdapter;
import il.ac.huji.todolist.adapters.TaskAdapter;
import il.ac.huji.todolist.data.TaskItem;

public class ToDoListManagerActivity extends AppCompatActivity {

    private List<TaskItem> taskItems;
    private TaskAdapter taskAdapter;
    private DBCursorAdapter dbCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_manager);

        dbCursorAdapter = new DBCursorAdapter(this);
        dbCursorAdapter.open();

        final ListView listView = (ListView) findViewById(R.id.listView);
        taskItems = dbCursorAdapter.getAllTasks();
        taskAdapter = new TaskAdapter(ToDoListManagerActivity.this,taskItems);
        listView.setAdapter(taskAdapter);
        registerForContextMenu(listView);
    }

    protected void onActivityResult(int reqCode, int resCode, Intent addGo) {
        if(resCode != RESULT_CANCELED){
            switch (reqCode) {
                case 1:
                    dbCursorAdapter.open();
                    TaskItem taskItem = dbCursorAdapter.createTaskItem(addGo.getStringExtra("task_name"),addGo.getLongExtra("task_date", -1));
                    taskItems.add(taskItem);
                    taskAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.listView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            TaskItem taskItem = taskItems.get(info.position);
            menu.setHeaderTitle(taskItem.getTask());
            menu.add(1, R.id.menuItemDelete, 1, R.string.menu_delete);
            if (taskItem.getTask().matches("^(?i)Call\\s+.*$")){
                menu.add(1, R.id.menuItemCall, 1, R.string.menu_call);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TaskItem taskItem;
        switch(item.getItemId()) {
            case R.id.menuItemDelete:
                if (taskAdapter.getCount() > 0) {
                    taskItem = taskItems.get(info.position);
                    dbCursorAdapter.deleteTaskItem(taskItem);
                    taskItems.remove(info.position);
                }
                taskAdapter.notifyDataSetChanged();
                return true;
            case R.id.menuItemCall:
                taskItem = taskItems.get(info.position);
                Pattern pattern = Pattern.compile("\\*?\\(?(\\d{1,3})\\)?[- ]?(\\d{1,3})[- ]?(\\d{1,4})");
                Matcher matcher = pattern.matcher(taskItem.getTask());
                if (matcher.find()) {
                    Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + matcher.group()));
                    Log.d("ActionDial", "Phone number is" + matcher.group());
                    startActivity(dial);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        taskAdapter = new TaskAdapter(ToDoListManagerActivity.this,taskItems);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(taskAdapter);
        switch (item.getItemId()) {
            case R.id.add:
                dbCursorAdapter.close();
                Intent addGo = new Intent(getApplicationContext(), AddNewToDoItemActivity.class);
                startActivityForResult(addGo, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        dbCursorAdapter.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        dbCursorAdapter.close();
        super.onPause();
    }
}