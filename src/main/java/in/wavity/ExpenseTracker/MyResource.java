package in.wavity.ExpenseTracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("myresource")
public class MyResource {

	private static final Logger logger = LoggerFactory.getLogger(MyResource.class);

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {

		logger.info("*** getId() method is started execution. ***");
		logger.warn("This is the warning message.");
		logger.debug("This is debug message.");
		System.out.println("This is the method execution");
		logger.info("*** getId() method is Ended execution. ***");
		return "Got it!";

	}
}
