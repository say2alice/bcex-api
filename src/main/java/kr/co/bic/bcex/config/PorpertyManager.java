package kr.co.bic.bcex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class PorpertyManager {
	private String SERVER_FILE = "file:" + System.getProperty("user.dir") + "\\config\\service";
	private static final String CLASSPATH_FILE = "classpath:config";
	
	@Bean
	ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource(){
	    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasenames(CLASSPATH_FILE, SERVER_FILE);
	    messageSource.setCacheMillis(5000);
	    
	    return messageSource;
	}
}
