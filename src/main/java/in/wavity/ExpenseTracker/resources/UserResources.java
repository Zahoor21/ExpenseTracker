package in.wavity.ExpenseTracker.resources;

import in.wavity.ExpenseTracker.entity.User;
import in.wavity.ExpenseTracker.service.UserService;
import jakarta.ws.rs.core.Response;

public class UserResources {

	UserService service;

	public UserResources() {
		service = new UserService();
	}

	public Response saveUser(User user) {
		return service.saveUser(user);
	}

	public Response fetchUserById(int userId) {
		return service.fetchUserById(userId);
	}

	public Response fetchAllUsers() {
		return service.fetchAllUsers();
	}

	public Response deleteUserById(int id) {
		return service.deleteUserById(id);
	}

	public Response updateUserById(int id, User newUser) {
		return service.deleteUserById(id);
	}

	public Response fetchUserByUserName(String userName) {
		return service.fetchUserByUserName(userName);
	}

}
