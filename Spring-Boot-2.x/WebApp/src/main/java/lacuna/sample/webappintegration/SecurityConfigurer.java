package lacuna.sample.webappintegration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private Environment env;

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;
    
    OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() { 
        OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        String redirectUrl = env.getProperty("logoutRedirectUrl");
        successHandler.setPostLogoutRedirectUri(redirectUrl);
        return successHandler;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf().disable()
            .authorizeRequests().antMatchers().permitAll()
                .and()
            .authorizeRequests().antMatchers("/Home/PrivateRoute").fullyAuthenticated()
                .and()
            .oauth2Login()
                .and()
            .logout()
                .logoutSuccessHandler(oidcLogoutSuccessHandler());
    }
}