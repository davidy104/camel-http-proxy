package nz.co.camel.servlet.proxy.route;

import nz.co.camel.servlet.proxy.ds.MyManagedBean;

import org.apache.camel.builder.RouteBuilder;

public class ProxyRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("servlet:///proxy")
				.routeId("proxyRoute")
				.bean(MyManagedBean.class, "doSomething").id("myManagedBean")
				.to("https://www.google.co.nz/?bridgeEndpoint=true&amp;throwExceptionOnFailure=false")
				.to("log:output")
				.end();
	}

}
