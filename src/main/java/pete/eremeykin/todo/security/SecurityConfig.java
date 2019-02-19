package pete.eremeykin.todo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pete.eremeykin.todo.service.AccountDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String USER_NAME = "user";
  private static final String USER_PASSWORD = "password";
  private static final String USER_ROLE = "USER";
  private static final String MAPPING_APP = "/todo";
  private static final String MAPPING_INDEX = "/index";
  private static final String MAPPING_EDIT = "/edit";
  private static final String PAGE_INDEX = MAPPING_APP + MAPPING_INDEX;
  private static final String PAGE_EDIT = MAPPING_APP + MAPPING_EDIT;
  private static final String COOKIE_JSESSIONID = "JSESSIONID";
  private static final String LOGIN_PROCESSING_URL = MAPPING_APP + "/login";
  private static final String LOGOUT_PROCESSING_URL = MAPPING_APP + "/logout";

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Autowired
  private AccountDetailsServiceImpl accountDetailsService;

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(accountDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }


  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.csrf().disable()
        .sessionManagement()
        .sessionFixation().none()
        .and()
        .authorizeRequests()
        .antMatchers(PAGE_INDEX).permitAll()
        .antMatchers(PAGE_EDIT).authenticated()
        .and()
        .formLogin()
        .loginProcessingUrl(LOGIN_PROCESSING_URL)
        .loginPage(PAGE_INDEX)
        .defaultSuccessUrl(PAGE_EDIT)
        .and()
        .logout()
        .clearAuthentication(true)
        .logoutUrl(LOGOUT_PROCESSING_URL)
        .deleteCookies(COOKIE_JSESSIONID)
        .invalidateHttpSession(true)
        .logoutSuccessUrl(PAGE_INDEX);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}