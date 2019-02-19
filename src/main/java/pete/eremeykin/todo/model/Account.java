package pete.eremeykin.todo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long accountId;

  private String accountUserName;
  private String accountPassword;

  @Override
  public String toString() {
    return "Account{" +
        "accountId=" + accountId +
        ", accountUserName='" + accountUserName + '\'' +
        ", accountPassword='" + accountPassword + '\'' +
        '}';
  }
}
