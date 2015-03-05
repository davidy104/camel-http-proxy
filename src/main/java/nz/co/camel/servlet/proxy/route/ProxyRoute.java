package nz.co.camel.servlet.proxy.route;

import nz.co.camel.servlet.proxy.ds.MyManagedBean;

import org.apache.camel.builder.RouteBuilder;

public class ProxyRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("servlet:///proxy")
				.routeId("proxyRoute")
//				.filter(xpath("/a/b=='xpression'"))
//				.to("xpression")
				.bean(MyManagedBean.class, "doSomething").id("myManagedBean")
				.to("https://www.google.co.nz/?bridgeEndpoint=true&amp;throwExceptionOnFailure=false")
				.to("log:output")
//				.setBody(constant("<a>hello</a>"))
//				.setHeader("content-type", constant("text/xml"))
				.end();
	}

}
