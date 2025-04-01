package in.wavity.ExpenseTracker.repo;

import in.wavity.ExpenseTracker.entity.User;
import jakarta.ws.rs.core.Response;

public interface UserRepo {
	Response saveUser(User user);

	Response fetchUserById(int userId);

	Response fetchAllUsers();

	Response deleteUserById(int id);

	Response updateUserById(int id, User newUser);

	Response fetchUserByUserName(String userName);
}
