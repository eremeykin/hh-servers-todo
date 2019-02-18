package pete.eremeykin.todo.main;

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
//    String data = "[{\"id\":1550492449892,\"title\":\"tESt\",\"completed\":false},{\"id\":1550492476165,\"title\":\"test2\",\"completed\":false},{\"id\":1550492504711,\"title\":\"test3\",\"completed\":false},{\"id\":1550492540689,\"title\":\"test4\",\"completed\":false},{\"id\":1550492600847,\"title\":\"test5\",\"completed\":false},{\"id\":1550492636250,\"title\":\"test6\",\"completed\":false},{\"id\":1550492677128,\"title\":\"test7\",\"completed\":false}]\n";
    return Response.status(200).entity(savedData).build();
  }
}
