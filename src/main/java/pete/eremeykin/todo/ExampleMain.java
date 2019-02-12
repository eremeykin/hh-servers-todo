package pete.eremeykin.todo;

import ru.hh.nab.starter.NabApplication;

public class ExampleMain {

  public static void main(String[] args) {
    NabApplication nab = NabApplication.builder()
        .configureJersey(ExampleJerseyConfig.class)
        .addAllowedPackages("pete.eremeykin")
        .bindToRoot()
        .build();
    nab.run(ExampleConfig.class);
  }
}
