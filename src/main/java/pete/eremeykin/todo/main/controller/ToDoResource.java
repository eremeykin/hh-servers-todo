package pete.eremeykin.todo.main.controller;

import org.glassfish.jersey.server.mvc.Template;
import pete.eremeykin.todo.main.model.SampleModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class ToDoResource {

  private String savedData = "[]";


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

}
