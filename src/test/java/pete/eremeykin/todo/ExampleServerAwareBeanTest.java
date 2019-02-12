package pete.eremeykin.todo;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import ru.hh.nab.starter.NabApplication;
import ru.hh.nab.testbase.NabTestBase;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = ExampleTestConfig.class)
public class ExampleServerAwareBeanTest extends NabTestBase {

  @Inject
  private Function<String, String> serverPortAwareBean;

  @Test
  public void testBeanWithNabTestContext() {
    try (Response response = createRequestFromAbsoluteUrl(serverPortAwareBean.apply("/hello")).get()) {
      assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
      assertEquals("Hello, world!", response.readEntity(String.class));
    }
  }

  @Override
  protected NabApplication getApplication() {
    return NabApplication.builder().configureJersey().registerResources(ExampleResource.class).bindToRoot().build();
  }
}
