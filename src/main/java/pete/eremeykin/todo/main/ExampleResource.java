package pete.eremeykin.todo.main;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;
import pete.eremeykin.todo.main.model.SampleModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class ExampleResource {

  @GET
  @Path("/hello")
  public String hello(@DefaultValue("world") @QueryParam("name") String name) {
    return String.format("Hello, %s!", name);
  }

  @GET
  @Path("/main")
  @Template(name = "/main")
  public Map<String, Object> data() {
    System.out.println("In main resource");
    Map<String, Object> context = new HashMap<>();
    context.put("name", "Test name");
    context.put("comment", "Test comment");
    return context;
  }

  @GET
  @Path("/thym")
  @Produces(MediaType.TEXT_HTML)
  public Viewable sayHello2(@DefaultValue("my friends") @QueryParam("name") String name) {
    SampleModel model = new SampleModel("Good morning", name);
    return new Viewable("sample2", model);
  }

}
