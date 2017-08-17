package controllers;

import com.avaje.ebean.Ebean;
import controllers.base.BaseController;
import models.Task;
import models.pojo.TaskContract;
import models.pojo.TaskFilter;
import models.service.TaskService;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import play.data.Form;
import play.libs.Json;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lordp on 17.08.2017.
 */
public class TaskController extends BaseController {
    public Result get(int id) {
        Task task = Task.find.byId(id);
        if(task == null) {
            return notFound();
        }

        TaskContract taskContract = new TaskContract(task);
        return ok(Json.toJson(taskContract));
    }

    public Result find() {
        Form<TaskFilter> taskRequest = formFactory.form(TaskFilter.class).bindFromRequest();
        if(taskRequest.hasErrors()) {
            return badRequest(taskRequest.errorsAsJson());
        }

        TaskFilter taskFilter = taskRequest.get();
        List<TaskContract> response = new ArrayList<>();
        for(Task task : TaskService.findTasks(taskFilter)) {
            response.add(new TaskContract(task));
        }

        return ok(Json.toJson(response));
    }

    public Result add() {
        Form<TaskContract> taskRequest = formFactory.form(TaskContract.class).bindFromRequest();

        if(taskRequest.hasErrors()) {
            return badRequest(taskRequest.errorsAsJson());
        }

        TaskContract taskContract = taskRequest.get();
        int id = TaskService.saveTask(taskContract);

        Task task = Task.find.byId(id);
        TaskContract responce = new TaskContract(task);
        return ok(Json.toJson(responce));
    }

    public Result edit(int id) {
        Form<TaskContract> taskRequest = formFactory.form(TaskContract.class).bindFromRequest();

        if(taskRequest.hasErrors()) {
            return badRequest(taskRequest.errorsAsJson());
        }

        Task task = Task.find.byId(id);
        if(task == null) {
            return notFound();
        }

        TaskContract taskContract = taskRequest.get();
        TaskService.editTask(task, taskContract);
        return ok();
    }

    public Result remove(int id) {
        Task task = Task.find.byId(id);
        if(task == null) {
            return notFound();
        }
        TaskService.deleteTask(task);
        return ok();
    }
}
