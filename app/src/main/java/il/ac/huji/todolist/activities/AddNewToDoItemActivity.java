package il.ac.huji.todolist.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import il.ac.huji.todolist.R;

public class AddNewToDoItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo_item);

        final EditText editText = (EditText) findViewById(R.id.editText);

        final Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int currentMonth = c.get(Calendar.MONTH);
        int currentDay = c.get(Calendar.DAY_OF_MONTH);

        final DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        datePicker.updateDate(currentYear, currentMonth, currentDay);

        Button btnAddCancel = (Button)findViewById(R.id.btnAddCancel);
        btnAddCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        Button btnAddOK = (Button)findViewById(R.id.btnAddOK);
        btnAddOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().trim().length() > 0) {
                    long dateTime = datePicker.getCalendarView().getDate();
                    Date date = new Date(dateTime);
                    String task = editText.getText().toString();
                    Intent result = new Intent();
                    result.putExtra("task_name", task);
                    result.putExtra("task_date", date.getTime());
                    setResult(RESULT_OK, result);
                }
                else {
                    setResult(RESULT_CANCELED);
                }
                finish();
            }
        });

    }
}
