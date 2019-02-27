package pete.eremeykin.todo.view;


import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.server.mvc.Viewable;
import org.glassfish.jersey.server.mvc.spi.TemplateProcessor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import pete.eremeykin.todo.utils.ObjectUtils;

@Provider
public final class ThymeleafViewProcessor implements TemplateProcessor<String> {

  @Context
  ServletContext servletContext;

  @Context
  HttpServletRequest request;

  @Context
  HttpServletResponse response;

  private TemplateEngine templateEngine;

  @PostConstruct
  public void postConstruct() {
    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
    templateResolver.setPrefix("/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML");
    templateResolver.setCacheTTLMs(3600000L);

    templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
  }


  @Override
  public String resolve(String name, MediaType mediaType) {
    if (name == null) {
      return null;
    }
    String[] tokens = name.split("/");
    if (tokens.length < 1) {
      return null;
    }
    return tokens[tokens.length - 1];
  }

  @Override
  public void writeTo(String templateReference, Viewable viewable, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream out) throws IOException {
    out.flush();
    WebContext context = new WebContext(request, response, servletContext, request.getLocale());
    Map<String, Object> variables = ObjectUtils.transformToMap(viewable.getModel());
    context.setVariables(variables);
    templateEngine.process(templateReference, context, response.getWriter());
  }
}