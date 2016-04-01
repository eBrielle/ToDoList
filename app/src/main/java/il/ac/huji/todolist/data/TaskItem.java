package il.ac.huji.todolist.data;

public class TaskItem {
    private String task;
    private Long date;
    private Long id;

    public TaskItem() {
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

}
