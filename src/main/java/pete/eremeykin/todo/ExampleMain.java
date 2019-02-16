package pete.eremeykin.todo;

import org.glassfish.jersey.server.mvc.mustache.MustacheMvcFeature;
import pete.eremeykin.todo.view.ThymeleafViewProcessor;
import ru.hh.nab.starter.NabApplication;

public class ExampleMain {

  public static void main(String[] args) {

    NabApplication nab = NabApplication.builder()
        .configureWebapp((webAppContext, webApplicationContext) -> {
          webAppContext.setResourceBase("src/main/resources");
        })
        .configureJersey(ExampleJerseyConfig.class)
        .registerResources(ThymeleafViewProcessor.class)
        .registerProperty(MustacheMvcFeature.TEMPLATE_BASE_PATH, "templates")
        .registerResources(MustacheMvcFeature.class)
        .addAllowedPackages("pete.eremeykin")
        .bindToRoot()
        .build();
    nab.run(ExampleConfig.class);
  }
}
