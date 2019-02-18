package pete.eremeykin.todo.main;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;



@Controller
public class TestController {

  @GetMapping("/tc/*")
  public String initCustomerForm(Map<String, Object> model) {
    System.out.println("TC TC TC TC");
    return "index";
  }

}
