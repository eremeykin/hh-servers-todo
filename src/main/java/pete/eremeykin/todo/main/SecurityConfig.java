package pete.eremeykin.todo.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.ServletContext;

@Configuration
// @ImportResource({ "classpath:webSecurityConfig.xml" })
@EnableWebSecurity
//@Profile("!https")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  static {
    System.out.println("Load SecurityConfig");
  }


  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    System.out.println("In method configureGlobal");
    auth
        .inMemoryAuthentication()
        .withUser("user").password(passwordEncoder().encode("password")).roles("USER");
  }


  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    System.out.println("In conf");
//    http.sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    http.csrf().disable()
        .sessionManagement()
        .sessionFixation().none()
        .and()
        .authorizeRequests()
        .antMatchers("/todo/index*").permitAll()
        .antMatchers("/todo/edit*").authenticated()
        .antMatchers("/spring/tc*").authenticated()
        .and()
        .formLogin()
        .loginProcessingUrl("/login")
        .loginPage("/todo/index")
        .defaultSuccessUrl("/todo/edit")
        .and()
        .logout()
        .clearAuthentication(true)
        .logoutUrl("/logout")
        .deleteCookies("JSESSIONID")
        .invalidateHttpSession(true)
        .logoutSuccessUrl("/todo/index")
//        .logoutSuccessHandler(logoutSuccessHandler())
    ;
//        .and()
//        .httpBasic();

//    http
//        .csrf().disable()
//        .authorizeRequests()
//        .antMatchers("/**").denyAll()
//        .antMatchers("/admin/**").hasRole("ADMIN")
//        .antMatchers("/anonymous*").anonymous()
//        .antMatchers("/login*").permitAll()
//        .anyRequest().authenticated()
//        .and()
//        .formLogin()
//        .loginPage("/login.html")
//        .loginProcessingUrl("/perform_login")
//        .defaultSuccessUrl("/homepage.html", true)
//        .failureUrl("/login.html?error=true")
//        .and()
//        .logout()
//        .logoutUrl("/perform_logout")
//        .deleteCookies("JSESSIONID")
//        .and()
//        .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
////////////////////////////////////////
    //.and()
    //.exceptionHandling().accessDeniedPage("/accessDenied");
//    .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    // @formatter:on
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }


  @Bean
  public LogoutSuccessHandler logoutSuccessHandler() {
    return new CustomLogoutSuccessHandler();
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}