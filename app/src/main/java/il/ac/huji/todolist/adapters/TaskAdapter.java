package il.ac.huji.todolist.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

        TextView textViewTask = (TextView) convertView.findViewById(R.id.txtTodoTitle);
        textViewTask.setText(taskItem.getTask());

        TextView textViewDate = (TextView) convertView.findViewById(R.id.txtTodoDueDate);
        Date date = taskItem.getDate();
        StringBuilder taskDate = new StringBuilder();
                taskDate.append((String) android.text.format.DateFormat.format("dd", date));
                taskDate.append("/");
                taskDate.append((String) android.text.format.DateFormat.format("MM", date));
                taskDate.append("/");
                taskDate.append((String) android.text.format.DateFormat.format("yyyy", date));
        textViewDate.setText(taskDate);

        Calendar nowTime = Calendar.getInstance();
        nowTime.set(Calendar.HOUR_OF_DAY, 0);
        nowTime.set(Calendar.MINUTE, 0);
        nowTime.set(Calendar.SECOND, 0);
        nowTime.set(Calendar.MILLISECOND, 0);
        if (taskItem.getDate().before(nowTime.getTime())) {
            textViewTask.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.overdue_tasks));
            textViewDate.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.overdue_tasks));
        } else {
            textViewTask.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.ongoing_tasks));
            textViewDate.setTextColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.ongoing_tasks));
        }

        return convertView;
    }
}
