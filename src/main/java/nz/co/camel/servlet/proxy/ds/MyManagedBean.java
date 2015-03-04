package nz.co.camel.servlet.proxy.ds;

import org.apache.camel.api.management.ManagedAttribute;
import org.apache.camel.api.management.ManagedOperation;
import org.apache.camel.api.management.ManagedResource;

@ManagedResource(description = "My Managed Bean within Camel")
public class MyManagedBean {
	private int camelsSeenCount;

	public String doSomething(Object body) {
//		if (body.contains("Camel")) {
		camelsSeenCount++;
//		}
		return "Managed " + body;
	}

	@ManagedAttribute(description = "How many Camels Have been Seen")
	public int getCamelsSeenCount() {
		return camelsSeenCount;
	}

	@ManagedOperation(description = "Set Camels Seen Count to Zero")
	public void resetCamelsSeenCount() {
		camelsSeenCount = 0;
	}
}
