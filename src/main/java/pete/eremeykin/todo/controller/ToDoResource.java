package pete.eremeykin.todo.controller;

import org.glassfish.jersey.server.mvc.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import pete.eremeykin.todo.model.Account;
import pete.eremeykin.todo.model.Model;
import pete.eremeykin.todo.model.Task;
import pete.eremeykin.todo.service.AccountService;
import pete.eremeykin.todo.service.TaskService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/")
public class ToDoResource {

  private String savedData = "[]";


  @Autowired
  private TaskService taskService;

  @Autowired
  private AccountService accountService;

  @POST
  @Path("/save")
  @Consumes(MediaType.APPLICATION_JSON)
  @Transactional
  public Response saveTodos(String jsonTasks) {

    Account account = accountService.findByAccountUserName(retriveUserName());
    synchronized (account) {
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
  }


  @GET
  @Path("/load")
  @Produces(MediaType.APPLICATION_JSON)
  @Transactional
  public Response loadTodos() {
    Account account = accountService.findByAccountUserName(retriveUserName());
    synchronized (account) {
      long accountId = account.getAccountId();
      List<Task> tasks = taskService.findByUserId(accountId);
      return Response.status(200).entity(Task.toJson(tasks)).build();
    }
  }


  @GET
  @Path("/index")
  @Template(name = "index")
  public Model index() {
    return new Model("");
  }


  @GET
  @Path("/edit")
  @Template(name = "edit")
  public Object edit(@DefaultValue("world") @QueryParam("name") String name) {
    return new Model(retriveUserName());
  }

  private static String retriveUserName() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
      return ((UserDetails) principal).getUsername();
    } else {
      return principal.toString();
    }
  }

}
