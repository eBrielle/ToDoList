package il.ac.huji.todolist;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import il.ac.huji.todolist.adapters.TaskAdapter;
import il.ac.huji.todolist.data.TaskItem;

public class ToDoListManagerActivity extends AppCompatActivity {

    private List<TaskItem> taskItems;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_manager);
        final ListView listView = (ListView) findViewById(R.id.listView);
        taskItems = new ArrayList<>();
        taskAdapter = new TaskAdapter(ToDoListManagerActivity.this,taskItems);
        listView.setAdapter(taskAdapter);
        registerForContextMenu(listView);
    }

    protected void onActivityResult(int reqCode, int resCode, Intent addGo) {
        if(resCode != RESULT_CANCELED){
            switch (reqCode) {
                case 1:
                    final TaskItem taskItem = new TaskItem();
                    String task_name = addGo.getStringExtra("task_name");
                    Date date = new Date();
                    date.setTime(addGo.getLongExtra("task_date", -1));
                    taskItem.setTask(task_name);
                    taskItem.setDate(date);
                    taskItems.add(taskItem);
                    taskAdapter.notifyDataSetChanged();
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
        switch(item.getItemId()) {
            case R.id.menuItemDelete:
                taskItems.remove(info.position);
                taskAdapter.notifyDataSetChanged();
                return true;
            case R.id.menuItemCall:
                TaskItem taskItem = taskItems.get(info.position);
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
                Intent addGo = new Intent(getApplicationContext(), AddNewToDoItemActivity.class);
                startActivityForResult(addGo, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
