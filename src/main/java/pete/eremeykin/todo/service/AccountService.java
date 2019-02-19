package pete.eremeykin.todo.service;

import pete.eremeykin.todo.model.Account;

import java.util.List;

public interface AccountService {
  List<Account> findByAccountUserName(String userName);
}
