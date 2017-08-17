package models.service;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.avaje.ebean.Transaction;
import com.google.common.base.Strings;
import models.Task;
import models.pojo.TaskContract;
import models.pojo.TaskFilter;
import play.Configuration;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by lordp on 17.08.2017.
 */
public final class TaskService {

    public static final int DEFAULT_PAGE_SIZE = 500;

    public static List<Task> findTasks(TaskFilter filter) {

        List<Task> result = null;

        Query<Task> query = null;
        ExpressionList<Task> expressionList = Task.find.query().where();
        if(filter.dateBegin != null) {
            expressionList = expressionList.ge("create_time", filter.dateBegin);
        }
        if(filter.dateEnd != null) {
            expressionList = expressionList.le("create_time", filter.dateEnd);
        }
        if(filter.status != null) {
            expressionList = expressionList.eq("status", filter.status.name());
        }

        if(!Strings.isNullOrEmpty(filter.orderFiled)) {
            query = expressionList.orderBy(filter.orderFiled);
        } else {
            query = expressionList.query();
        }

        if(filter.page != null) {
            result = query.findPagedList(filter.page, filter.pageSize == null ? DEFAULT_PAGE_SIZE : filter.pageSize).getList();
        } else {
            result = query.setMaxRows(DEFAULT_PAGE_SIZE).findList();
        }
        return result;
    }

    public static int saveTask(TaskContract taskContract) {
        Task task = new Task();
        task.description = taskContract.description;
        task.status = taskContract.status == null ? Task.TastStatus.OPEN : taskContract.status;
        task.dateEnd = taskContract.dateEnd;

        Transaction transaction = Ebean.beginTransaction();
        try {
            task.save();
            task.number = generateTaskNumber(task);
            task.save();
            transaction.commit();
            return task.id;
        } catch (PersistenceException exc) {
            transaction.rollback();
            throw exc;
        } finally {
            transaction.end();
        }
    }

    public static void editTask(Task task, TaskContract taskContract) {
        task.description = taskContract.description;
        if(taskContract.status != null) {
            task.status = taskContract.status;
        }
        if(taskContract.dateEnd != null) {
            task.dateEnd = taskContract.dateEnd;
        }

        task.update();
    }

    public static void deleteTask(Task task) {
        task.delete();
    }

    public static String generateTaskNumber(Task task) {
        return String.format("TASK-%d", task.id);
    }
}
