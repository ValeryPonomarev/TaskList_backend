package models.pojo;

import models.Task;

import java.util.Date;

/**
 * Created by lordp on 17.08.2017.
 */
public class TaskFilter {
    public Date dateBegin;
    public Date dateEnd;
    public Integer page;
    public Integer pageSize;
    public Task.TastStatus status;
    public String orderFiled;
}
