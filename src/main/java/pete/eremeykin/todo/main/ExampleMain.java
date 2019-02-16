package pete.eremeykin.todo.main;

import org.glassfish.jersey.server.mvc.MvcFeature;
import pete.eremeykin.todo.main.view.ThymeleafViewProcessor;
import ru.hh.nab.starter.NabApplication;

public class ExampleMain {

  public static void main(String[] args) {

    NabApplication nab = NabApplication.builder()
        .configureWebapp((webAppContext, webApplicationContext) -> {
          webAppContext.setResourceBase("src/main/resources");
        })
        .configureJersey(ExampleJerseyConfig.class)
        .registerResources(MvcFeature.class)
        .registerResources(ThymeleafViewProcessor.class)
        .addAllowedPackages("pete.eremeykin")
        .bindToRoot()
        .build();
    nab.run(ExampleConfig.class);
  }
}
