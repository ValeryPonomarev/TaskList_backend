package models.pojo;

import models.Task;
import play.data.validation.Constraints;

import java.util.Date;

/**
 * Created by lordp on 17.08.2017.
 */
public class TaskContract {
    public int id;
    public String number;
    public String description;
    public Task.TastStatus status;
    public Date dateEnd;
    public Date createTime;

    public TaskContract() {}

    public TaskContract(Task task) {
        this();
        this.id = task.id;
        this.number = task.number;
        this.description = task.description;
        this.status = task.status;
        this.dateEnd = task.dateEnd;
        this.createTime = task.createTime;
    }
}
