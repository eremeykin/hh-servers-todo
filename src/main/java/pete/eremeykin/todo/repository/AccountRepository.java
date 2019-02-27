package pete.eremeykin.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pete.eremeykin.todo.model.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Account findByAccountUserName(String userName);
}
