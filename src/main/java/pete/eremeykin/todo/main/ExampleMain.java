package pete.eremeykin.todo.main;

import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import pete.eremeykin.todo.main.view.ThymeleafViewProcessor;
import ru.hh.nab.starter.NabApplication;



public class ExampleMain {

  private static final String PATH_RESOURCES = "src/main/resources";
  private static final String NAME_DEFAULT_SERVLET = "default";
  private static final String NAME_DISPATCHER_SERVLET = "dispatcher";
  private static final String NAME_SECURITY_FILTER = "springSecurityFilterChain";
  private static final String PARAM_RESOURCE_BASE = "resourceBase";
  private static final String MAPPING_ROOT = "/*";
  private static final String MAPPING_APP = "/todo";
  private static final String NAME_PACKAGE;
  private static final String DOT = "\\.";

  static {
    String fullPackageName = ExampleMain.class.getPackage().getName();
    String[] tokens = fullPackageName.split(DOT);
    NAME_PACKAGE = tokens[0] + "." + tokens[1];
  }


  public static void main(String[] args) {
    NabApplication nab = NabApplication.builder().setContextPath("")
        .configureWebapp((webAppContext, webApplicationContext) -> {
          // thymeleaf
          webAppContext.setResourceBase(PATH_RESOURCES);
          // static resources
          ServletHolder defaultServlet = new ServletHolder(NAME_DEFAULT_SERVLET, new DefaultServlet());
          defaultServlet.setInitParameter(PARAM_RESOURCE_BASE, PATH_RESOURCES);
          webAppContext.getServletHandler().addServletWithMapping(defaultServlet, MAPPING_ROOT);
          SessionHandler sessionHandler = new SessionHandler();
          webAppContext.setSessionHandler(sessionHandler);
          // security
          FilterHolder filterHolder = new FilterHolder(new DelegatingFilterProxy(NAME_SECURITY_FILTER));
          webAppContext.getServletHandler().addFilterWithMapping(filterHolder, MAPPING_ROOT, 0);
        })
        .configureJersey(ExampleJerseyConfig.class)
        .registerResources(MvcFeature.class)
        .registerResources(ThymeleafViewProcessor.class)
        .addAllowedPackages(NAME_PACKAGE)
        .bindTo(MAPPING_APP+ "/*")
        .build();
    nab.run(ExampleConfig.class);
  }
}
