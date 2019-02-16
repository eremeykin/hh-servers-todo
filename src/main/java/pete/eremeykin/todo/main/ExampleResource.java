package pete.eremeykin.todo.main;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;
import pete.eremeykin.todo.main.model.SampleModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/")
public class ExampleResource {

  @GET
  @Path("/hello")
  public String hello(@DefaultValue("world") @QueryParam("name") String name) {
    return String.format("Hello, %s!", name);
  }

  @GET
  @Path("/thym")
  @Template(name = "ind.old")
  public Object data(@DefaultValue("world") @QueryParam("name") String name) {
    return new SampleModel("Good morning", name);
  }
}
