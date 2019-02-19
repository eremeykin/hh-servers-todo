package pete.eremeykin.todo.controller;

import org.glassfish.jersey.server.mvc.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pete.eremeykin.todo.model.Account;
import pete.eremeykin.todo.model.SampleModel;
import pete.eremeykin.todo.model.Task;
import pete.eremeykin.todo.service.AccountService;
import pete.eremeykin.todo.service.TaskService;
import pete.eremeykin.todo.utils.ObjectUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
  public Response saveTodos(String data) {
    savedData = data;
    return Response.status(201).build();
  }


  @GET
  @Path("/load")
  @Produces(MediaType.APPLICATION_JSON)
  public Response loadTodos() {
    return Response.status(200).entity(savedData).build();
  }


  @GET
  @Path("/index")
  @Template(name = "index")
  public Object index(@DefaultValue("world") @QueryParam("name") String name) {
    return new SampleModel("ToDo", name);
  }


  @GET
  @Path("/edit")
  @Template(name = "edit")
  public Object edit(@DefaultValue("world") @QueryParam("name") String name) {
    return new SampleModel("ToDo", name);
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
