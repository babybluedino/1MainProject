package com.verizon.tsp.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import com.verizon.tsp.services.AppUserDetailsService;
import com.verizon.tsp.viewResolver.PdfViewResolver;



@Configurable
@EnableWebSecurity
// Modifying or overriding the default spring boot security.
public class WebConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AppUserDetailsService appUserDetailsService;

	// This method is for overriding the default AuthenticationManagerBuilder.
	// We can specify how the user details are kept in the application. It may
	// be in a database, LDAP or in memory.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(appUserDetailsService);
	}
	
	 @Bean
	    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
	        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
	        resolver.setContentNegotiationManager(manager);

	        // Define all possible view resolvers
	        List<ViewResolver> resolvers = new ArrayList<>();

	        
	      
	        resolvers.add(pdfViewResolver());

	        resolver.setViewResolvers(resolvers);
	        return resolver;
	    }

	  
	  

	    /*
	     * Configure View resolver to provide Pdf output using iText library to
	     * generate pdf output for an object content
	     */
	    @Bean
	    public ViewResolver pdfViewResolver() {
	        return new PdfViewResolver();
	    }

	// this configuration allow the client app to access the this api 
	// all the domain that consume this api must be included in the allowed o'rings 
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	    return new WebMvcConfigurerAdapter() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	            registry.addMapping("/**").allowedOrigins("http://localhost:4200");
	          
	        }
	    };
	}
	// This method is for overriding some configuration of the WebSecurity
	// If you want to ignore some request or request patterns then you can
	// specify that inside this method
	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	// This method is used for override HttpSecurity of the web Application.
	// We can specify our authorization criteria inside this method.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
		// starts authorizing configurations
		.authorizeRequests()
		// ignoring the guest's urls "
		.antMatchers("/tickets/**","/csa/**","/telecom/**","/plan/**","/logout","/users/**").permitAll()
		// authenticate all remaining URLS
		.anyRequest()
		.fullyAuthenticated().and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
		.invalidateHttpSession(true)
		.deleteCookies("remove")
        
        //.clearAuthentication(true)
        
        
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login")
        
        .permitAll()		
        .and()
		// enabling the basic authentication
		.httpBasic().and()
		// configuring the session on the server
		//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
		// disabling the CSRF - Cross Site Request Forgery
		.csrf().disable();
		

	}

}
