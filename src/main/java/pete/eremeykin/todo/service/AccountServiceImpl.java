package pete.eremeykin.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pete.eremeykin.todo.model.Account;
import pete.eremeykin.todo.repository.AccountRepository;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  public List<Account> findByAccountUserName(String userName) {
    return accountRepository.findByAccountUserName(userName);
  }
}
