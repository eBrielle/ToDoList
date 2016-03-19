package il.ac.huji.todolist.data;

import java.util.Date;

/**
 * Created by esther on 09/03/2016.
 */
public class TaskItem {
    private String task;
    private int year, month, day;
    private Date date;

    public TaskItem() {
    }

    public TaskItem(String task) {
        super();
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
