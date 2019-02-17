package pete.eremeykin.todo.main;

import org.glassfish.jersey.server.mvc.Template;
import pete.eremeykin.todo.main.model.SampleModel;

import javax.ws.rs.*;

@Path("/")
public class ExampleResource {

  @GET
  @Path("/hello")
  public String hello(@DefaultValue("world") @QueryParam("name") String name) {
    return String.format("Hello, %s!", name);
  }

  @GET
  @Path("/index")
  @Template(name = "index")
  public Object index(@DefaultValue("world") @QueryParam("name") String name) {
    return new SampleModel("ToDo", name);
  }
}
