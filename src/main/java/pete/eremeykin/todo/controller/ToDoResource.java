package pete.eremeykin.todo.controller;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import pete.eremeykin.todo.model.Account;
import pete.eremeykin.todo.model.Task;
import pete.eremeykin.todo.model.UserModel;
import pete.eremeykin.todo.security.UserNameProvider;
import pete.eremeykin.todo.service.AccountService;
import pete.eremeykin.todo.service.TaskService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/")
public class ToDoResource {
  /*ToDo Add separate method
   * for each of following actions:
   *     find
   *     update
   *     insert
   *     remove
   *     count
   * */

  @Autowired
  private TaskService taskService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private UserNameProvider nameProvider;

  @POST
  @Path("/save")
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response saveTodos(String jsonTasks) {
    Account account = accountService.findByAccountUserName(nameProvider.get());
    long accountId = account.getAccountId();
    taskService.deleteByUserId(accountId);
    try {
      List<Task> newTasks = Task.fromJson(jsonTasks, account);
      newTasks.forEach(taskService::save); // TODO implement batch insert
    } catch (IOException exc) {
      return Response.status(500).build();
    }
    return Response.status(201).build();
  }

  @GET
  @Path("/load")
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Response loadTodos() {
    Account account = accountService.findByAccountUserName(nameProvider.get());
    long accountId = account.getAccountId();
    List<Task> tasks = taskService.findByUserId(accountId);
    return Response.status(200).entity(Task.toJson(tasks)).build();
  }


  @GET
  @Path("/index")
  public Viewable index() {
    return new Viewable("index");
  }


  @GET
  @Path("/edit")
  @Template(name = "edit")
  public UserModel edit(@AuthenticationPrincipal UserDetails activeUser) {
    return new UserModel(nameProvider.get());
  }

}
