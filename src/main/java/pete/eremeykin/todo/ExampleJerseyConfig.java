package pete.eremeykin.todo;

import org.springframework.context.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Configuration
@Import(ExampleResource.class)
public class ExampleJerseyConfig {

  public ExampleJerseyConfig() {
  }

}

