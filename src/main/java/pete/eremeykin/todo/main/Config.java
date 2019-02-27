package pete.eremeykin.todo.main;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.hh.nab.starter.NabProdConfig;

@Configuration
@Import(NabProdConfig.class)
@ComponentScan({ "pete.eremeykin" })
class Config {

}
