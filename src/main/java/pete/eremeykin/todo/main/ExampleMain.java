package pete.eremeykin.todo.main;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import pete.eremeykin.todo.main.view.ThymeleafViewProcessor;
import ru.hh.nab.starter.NabApplication;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

          AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
          ctx.register(WebMvcConfigure.class);

          DispatcherServlet ds = new DispatcherServlet(ctx);
          ServletHolder dsServletHolder = new ServletHolder("dispatcher", ds);
          webAppContext.getServletHandler().addServletWithMapping(dsServletHolder, "/spring/*");
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
