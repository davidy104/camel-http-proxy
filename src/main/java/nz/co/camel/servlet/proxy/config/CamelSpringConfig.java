package nz.co.camel.servlet.proxy.config;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import nz.co.camel.servlet.proxy.route.ProxyRoute;

import org.apache.camel.CamelContext;
import org.apache.camel.ThreadPoolRejectedPolicy;
import org.apache.camel.management.DefaultManagementNamingStrategy;
import org.apache.camel.spi.ThreadPoolProfile;
import org.apache.camel.spring.CamelBeanPostProcessor;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelSpringConfig {

	@Resource
	private ApplicationContext context;

	@Bean
	public CamelBeanPostProcessor camelBeanPostProcessor() {
		CamelBeanPostProcessor camelBeanPostProcessor = new CamelBeanPostProcessor();
		camelBeanPostProcessor.setApplicationContext(context);
		return camelBeanPostProcessor;
	}

	@Bean
	public CamelContext camelContext() throws Exception {
		SpringCamelContext camelContext = new SpringCamelContext(context);

		final DefaultManagementNamingStrategy naming = (DefaultManagementNamingStrategy) camelContext.getManagementStrategy().getManagementNamingStrategy();
		naming.setHostName("localhost");
		naming.setDomainName("org.apache.camel");

		camelContext.getExecutorServiceManager().registerThreadPoolProfile(
				custThreadPoolProfile());
		camelContext.addRoutes(new ProxyRoute());
		return camelContext;
	}

	@Bean
	public ThreadPoolProfile custThreadPoolProfile() {
		ThreadPoolProfile profile = new ThreadPoolProfile();
		profile.setId("genericThreadPool");
		profile.setKeepAliveTime(120L);
		profile.setPoolSize(2);
		profile.setMaxPoolSize(10);
		profile.setTimeUnit(TimeUnit.SECONDS);
		profile.setRejectedPolicy(ThreadPoolRejectedPolicy.Abort);
		return profile;
	}

}
