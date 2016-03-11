package il.ac.huji.todolist.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import il.ac.huji.todolist.R;
import il.ac.huji.todolist.data.TaskItem;

/**
 * Created by esther on 09/03/2016.
 */
public class TaskAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<TaskItem> taskItems;

    public TaskAdapter(Activity activity, List<TaskItem> taskItems) {
        this.activity = activity;
        this.taskItems = taskItems;
    }

    private int[] colors = new int[] { R.color.even_tasks, R.color.odd_tasks };

    @Override
    public int getCount() {
        return taskItems.size();
    }

    @Override
    public Object getItem(int location) {
        return taskItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.task_item, null);

        final TaskItem taskItem = taskItems.get(position);

        TextView textView = (TextView) convertView.findViewById(R.id.textView);
        textView.setText(taskItem.getTask());
        if (position % 2 == 1) {
            textView.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.odd_tasks));
        } else {
            textView.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.even_tasks));
        }
        return convertView;
    }
}
