package pete.eremeykin.todo;

import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
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

  public static class SampleModel {
    public String greeting;
    public String name;

    public SampleModel(String greeting, String name) {
      this.greeting = greeting;
      this.name = name;
    }
  }
}
