package pete.eremeykin.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pete.eremeykin.todo.model.Account;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {


  @Autowired
  private AccountService accountService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Account account = accountService.findByAccountUserName(username);
    if (account == null) {
      throw new UsernameNotFoundException(username);
    }
    return new AccountUserDetails(account);
  }

  private static class AccountUserDetails implements UserDetails {

    private final Account account;
    private final String encodedPassword;

    public AccountUserDetails(Account account) {
      this.account = account;
      this.encodedPassword = new BCryptPasswordEncoder().encode(account.getAccountPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return Collections.singleton((GrantedAuthority) () -> "USER");
    }

    @Override
    public String getPassword() {
      return this.encodedPassword;
    }

    @Override
    public String getUsername() {
      return account.getAccountUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
      return true;
    }

    @Override
    public boolean isAccountNonLocked() {
      return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
      return true;
    }

    @Override
    public boolean isEnabled() {
      return true;
    }
  }

}
