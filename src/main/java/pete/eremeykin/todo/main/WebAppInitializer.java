package pete.eremeykin.todo.main;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Order(1)
public class WebAppInitializer implements WebApplicationInitializer {

  static {
    System.out.println("On load");
  }

  @Override
  public void onStartup(ServletContext container) throws ServletException {
    AnnotationConfigWebApplicationContext ctx
        = new AnnotationConfigWebApplicationContext();
    ctx.register(WebMvcConfigure.class);
    ctx.setServletContext(container);

    ServletRegistration.Dynamic servlet = container.addServlet(
        "dispatcherExample", new DispatcherServlet(ctx));
    servlet.setLoadOnStartup(1);
    servlet.addMapping("/");
  }
}