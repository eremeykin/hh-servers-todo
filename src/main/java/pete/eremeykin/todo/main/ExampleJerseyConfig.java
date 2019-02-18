package pete.eremeykin.todo.main;

import org.springframework.context.annotation.*;

@Configuration
@Import({ExampleResource.class, ToDoResource.class})
public class ExampleJerseyConfig {

}

