package in.wavity.ExpenseTracker.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.wavity.ExpenseTracker.dto.UserDto;
import in.wavity.ExpenseTracker.entity.User;
import in.wavity.ExpenseTracker.repo.UserRepo;
import jakarta.ws.rs.core.Response;

public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private final UserRepo userRepo;

	public UserService() {
		logger.info("Initializing UserService...");
		userRepo = new UserDto();
		logger.info("UserRepo instance created successfully.");
	}

	public Response saveUser(User user) {
		logger.info("saveUser method called with user: {}", user);
		try {
			// Setting the current date and time.
			Date date = new Date();
			user.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
			Response response = userRepo.saveUser(user);
			logger.info("User saved successfully: {}", user);
			return response;
		} catch (Exception e) {
			logger.error("Error saving user: {}", user, e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error saving user").build();
		}
	}

	public Response fetchUserById(int userId) {
		logger.info("fetchUserById method called with userId: {}", userId);
		Response response = userRepo.fetchUserById(userId);
		logger.debug("fetchUserById response: {}", response);
		return response;
	}

	public Response fetchAllUsers() {
		logger.info("fetchAllUsers method called");
		Response response = userRepo.fetchAllUsers();
		logger.debug("fetchAllUsers response: {}", response);
		return response;
	}

	public Response deleteUserById(int id) {
		logger.info("deleteUserById method called with id: {}", id);
		Response response = userRepo.deleteUserById(id);
		logger.info("User with id {} deleted successfully", id);
		return response;
	}

	public Response updateUserById(int id, User newUser) {
		Date date = new Date();
		newUser.setUpdatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
		logger.info("updateUserById method called with id: {} and user: {}", id, newUser);
		Response response = userRepo.updateUserById(id, newUser);
		logger.info("User with id {} updated successfully", id);
		return response;
	}

	public Response fetchUserByUserName(String userName) {	
		logger.info("fetchUserByUserName method called with userName: {}", userName);
		Response response = userRepo.fetchUserByUserName(userName);
		logger.debug("fetchUserByUserName response: {}", response);
		return response;
	}
}
