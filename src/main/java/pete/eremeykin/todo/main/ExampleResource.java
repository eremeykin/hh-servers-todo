package pete.eremeykin.todo.main;

import org.glassfish.jersey.server.mvc.Template;
import pete.eremeykin.todo.main.model.SampleModel;

import javax.ws.rs.*;

@Path("/")
public class ExampleResource {

  @GET
  @Path("/status")
  public String status(@DefaultValue("") @QueryParam("param") String name) {
    return String.format("I am ok, param = %s!", name);
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
