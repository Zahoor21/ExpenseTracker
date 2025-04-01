package in.wavity.ExpenseTracker.resources;

import in.wavity.ExpenseTracker.entity.User;
import in.wavity.ExpenseTracker.service.UserService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("user")
public class UserResources {

	UserService service;

	public UserResources() {
		service = new UserService();
	}

	@POST
	@Path("save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveUser(User user) {
		return service.saveUser(user);
	}

	@GET
	@Path("getById")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchUserById(@QueryParam("userId") int userId) {
		return service.fetchUserById(userId);
	}

	@GET
	@Path("getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchAllUsers() {
		return service.fetchAllUsers();
	}

	@DELETE
	@Path("delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUserById(@QueryParam("userId") int userId) {
		return service.deleteUserById(userId);
	}

	@PUT
	@Path("update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserById(@QueryParam("userId") int userId, User newUser) {
		return service.updateUserById(userId, newUser);
	}

	@GET
	@Path("getByName")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchUserByUserName(@QueryParam("userName") String userName) {
		return service.fetchUserByUserName(userName);
	}

}
