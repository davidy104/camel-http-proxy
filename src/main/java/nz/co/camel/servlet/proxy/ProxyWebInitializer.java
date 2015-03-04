package nz.co.camel.servlet.proxy;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import org.jolokia.jvmagent.JvmAgent;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ProxyWebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfiguration.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));
		servletContext.addListener(new ProxyWebListener());
	}

	public static class ProxyWebListener implements ServletContextListener {
		@Override
		public void contextInitialized(ServletContextEvent sce) {
			JvmAgent.agentmain(null);
		}

		@Override
		public void contextDestroyed(ServletContextEvent sce) {
			JvmAgent.agentmain("mode=stop");
		}

	}
}
