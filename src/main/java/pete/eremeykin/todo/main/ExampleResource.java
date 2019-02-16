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
  @Path("/main")
  @Template(name = "sample2")
  public Object data() {
    return new SampleModel("Goooooood morning", "TWTWT");
  }

  @GET
  @Path("/thym")
  @Produces(MediaType.TEXT_HTML)
  public Viewable sayHello2(@DefaultValue("my friends") @QueryParam("name") String name) {
    SampleModel model = new SampleModel("Good morning", name);
    return new Viewable("sample2", model);
  }

}
