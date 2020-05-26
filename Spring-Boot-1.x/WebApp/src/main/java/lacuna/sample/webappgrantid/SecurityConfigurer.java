package lacuna.sample.webappgrantid;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;

@EnableOAuth2Sso
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;
    
    @Autowired
    private OAuth2ClientContext clientContext;

    public OAuth2LogoutHandler oauth2LogoutHandler() throws URISyntaxException {
        String logoutRedirectUrl = env.getProperty("logoutRedirectUrl");
        String endSessionEndpoint = env.getProperty("endSessionEndpoint");

        return new OAuth2LogoutHandler(endSessionEndpoint, logoutRedirectUrl, clientContext);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests().antMatchers().permitAll()
                .and()
            .authorizeRequests().antMatchers("/Home/PrivateRoute").fullyAuthenticated()
                .and()
            .logout()
                .addLogoutHandler(oauth2LogoutHandler());
    }
    
    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext, OAuth2ProtectedResourceDetails details) {    
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }
}