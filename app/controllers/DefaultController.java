package controllers;

import controllers.base.BaseController;
import play.*;
import play.mvc.*;

import views.html.*;

public class DefaultController extends BaseController {
    public Result index() {
        return ok(configuration.getString("appversion"));
    }
}
