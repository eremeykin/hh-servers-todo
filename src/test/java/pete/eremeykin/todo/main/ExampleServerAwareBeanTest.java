package pete.eremeykin.todo.main;

import org.springframework.test.context.ContextConfiguration;
import ru.hh.nab.testbase.NabTestBase;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = ExampleTestConfig.class)
public class ExampleServerAwareBeanTest extends NabTestBase {

//  @Inject
//  private Function<String, String> serverPortAwareBean;
//
//  @Test
//  public void testBeanWithNabTestContext() {
//    try (Response response = createRequestFromAbsoluteUrl(serverPortAwareBean.apply("/hello")).get()) {
//      assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//      assertEquals("Hello, world!", response.readEntity(String.class));
//    }
//  }
//
//  @Override
//  protected NabApplication getApplication() {
//    return NabApplication.builder().configureJersey().registerResources(ExampleResource.class).bindToRoot().build();
//  }
}
