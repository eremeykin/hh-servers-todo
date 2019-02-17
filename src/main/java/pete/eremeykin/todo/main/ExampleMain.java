package pete.eremeykin.todo.main;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.mvc.MvcFeature;
import pete.eremeykin.todo.main.view.ThymeleafViewProcessor;
import ru.hh.nab.starter.NabApplication;

public class ExampleMain {

  private static final String PATH_RESOURCES = "src/main/resources";
  private static final String NAME_DEFAULT_SERVLET = "default";
  private static final String PARAM_RESOURCE_BASE = "resourceBase";
  private static final String MAPPING_ROOT = "/*";
  private static final String MAPPING_TODO = "/todo/*";
  private static final String NAME_PACKAGE;

  static {
    String fullPackageName = ExampleMain.class.getPackage().getName();
    String[] tokens = fullPackageName.split("\\.");
    NAME_PACKAGE = tokens[0] + "." + tokens[1];
  }


  public static void main(String[] args) {
    NabApplication nab = NabApplication.builder().setContextPath("")
        .configureWebapp((webAppContext, webApplicationContext) -> {
          webAppContext.setResourceBase(PATH_RESOURCES);
          ServletHolder defaultServlet = new ServletHolder(NAME_DEFAULT_SERVLET, new DefaultServlet());
          defaultServlet.setInitParameter(PARAM_RESOURCE_BASE, PATH_RESOURCES);
          webAppContext.getServletHandler().addServletWithMapping(defaultServlet, MAPPING_ROOT);
        })
        .configureJersey(ExampleJerseyConfig.class)
        .registerResources(MvcFeature.class)
        .registerResources(ThymeleafViewProcessor.class)
        .addAllowedPackages(NAME_PACKAGE)
        .bindTo(MAPPING_TODO)
        .build();
    nab.run(ExampleConfig.class);
  }
}
