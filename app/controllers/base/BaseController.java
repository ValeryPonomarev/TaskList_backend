package controllers.base;

import play.Configuration;
import play.Environment;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;

import javax.inject.Inject;

/**
 * Created by lordp on 17.08.2017.
 */
public abstract class BaseController extends Controller {
    @Inject
    public MessagesApi messagesApi;

    @Inject
    public Configuration configuration;

    @Inject
    public Environment environment;

    @Inject
    public FormFactory formFactory;
}
