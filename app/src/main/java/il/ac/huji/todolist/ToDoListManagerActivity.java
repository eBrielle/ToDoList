package il.ac.huji.todolist;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int position, long id) {
                final Dialog deleteDialog = new Dialog(ToDoListManagerActivity.this);
                deleteDialog.setContentView(R.layout.dialog_delete);
                deleteDialog.setCancelable(true);
                TextView deleteTextView = (TextView)deleteDialog.findViewById(R.id.textViewDelete);
                deleteTextView.setText(getResources().getString(R.string.deleteTitle) + "  \"" + taskItems.get(position).getTask() + "\"?");
                Button deleteDialogYesButton = (Button)deleteDialog.findViewById(R.id.deleteYes);
                deleteDialogYesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        taskItems.remove(position);
                        taskAdapter.notifyDataSetChanged();
                        deleteDialog.dismiss();                    }
                });
                Button deleteDialogNoButton = (Button)deleteDialog.findViewById(R.id.deleteNo);
                deleteDialogNoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();                    }
                });
                deleteDialog.show();
                return true;
            }
        });
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
                EditText editText = (EditText) findViewById(R.id.editText);
                if (editText.getText().toString().equals("")) {
                } else {
                    TaskItem taskItem = new TaskItem();
                    taskItem.setTask(editText.getText().toString());
                    taskItems.add(taskItem);
                    taskAdapter.notifyDataSetChanged();
                    editText.setText("");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
