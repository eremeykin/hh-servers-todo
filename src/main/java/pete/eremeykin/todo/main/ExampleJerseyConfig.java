package pete.eremeykin.todo.main;

import org.springframework.context.annotation.*;
import pete.eremeykin.todo.controller.ToDoResource;

@Configuration
@Import({ToDoResource.class})
public class ExampleJerseyConfig {

}

