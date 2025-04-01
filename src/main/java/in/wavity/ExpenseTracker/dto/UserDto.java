package in.wavity.ExpenseTracker.dto;

import java.util.List;

import in.wavity.ExpenseTracker.Response.UserResponse;
import in.wavity.ExpenseTracker.entity.User;
import in.wavity.ExpenseTracker.repo.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.core.Response;

public class UserDto implements UserRepo {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("csvPU");
    private EntityManager entityManager;

    // Initialize EntityManager when needed
    private EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    // Close EntityManager when done
    private void closeEntityManager() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    // Helper method for transaction management
    private void beginTransaction() {
        if (!getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().begin();
        }
    }

    // Helper method to commit transaction
    private void commitTransaction() {
        if (getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().commit();
        }
    }

    // Helper method to rollback transaction
    private void rollbackTransaction() {
        if (getEntityManager().getTransaction().isActive()) {
            getEntityManager().getTransaction().rollback();
        }
    }

    @Override
    public Response saveUser(User user) {
        UserResponse<User> response = new UserResponse<>();
        try {
            beginTransaction();
            getEntityManager().persist(user);
            commitTransaction();
            response.setData(user);
            response.setMessage("User successfully saved in the database.");
            return Response.status(Response.Status.CREATED).entity(response).build();
        } catch (Exception e) {
            rollbackTransaction();
            response.setMessage("Exception while saving user: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public Response fetchUserById(int userId) {
        UserResponse<User> response = new UserResponse<User>();
        try {
            User user = getEntityManager().find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("user not found or user id does not exits.")
                        .build();
            }
            response.setData(user);
            response.setMessage("User Found successfully.");
            return Response.ok().entity(response).build();
        } catch (Exception e) {
            response.setMessage("Exception while fetching user: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public Response fetchAllUsers() {
        UserResponse<User> response = new UserResponse<User>();
        try {
            List<User> users = getEntityManager().createQuery("select u from User u", User.class).getResultList();
            if (users.isEmpty()) {
                response.setMessage("No Users found!");
                return Response.status(Response.Status.NOT_FOUND).entity(response).build();
            } else {
                return Response.status(Response.Status.OK).entity(users).build();
            }
        } catch (Exception e) {
            response.setMessage("Exception while fetching users: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public Response deleteUserById(int id) {
        UserResponse<User> response = new UserResponse<User>();
        try {
            beginTransaction();
            User user = getEntityManager().find(User.class, id);
            if (user == null) {
                rollbackTransaction();
                response.setMessage("User not found with id: " + id);
                return Response.status(Response.Status.NOT_FOUND).entity(response).build();
            }
            getEntityManager().remove(user);
            commitTransaction();
            response.setData(user);
            response.setMessage("User : " + id + " deleted successfully");
            return Response.status(Response.Status.ACCEPTED).entity(response).build();
        } catch (Exception e) {
            rollbackTransaction();
            response.setMessage("Exception while deleting user: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public Response updateUserById(int id, User newUser) {
        UserResponse<User> response = new UserResponse<>();
        try {
            beginTransaction();
            newUser.setId(id);
            User updatedUser = getEntityManager().merge(newUser);
            commitTransaction();
            response.setData(updatedUser);
            response.setMessage("User " + id + " updated.");
            return Response.status(Response.Status.ACCEPTED).entity(response).build();
        } catch (Exception e) {
            rollbackTransaction();
            response.setMessage("Exception while updating user: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public Response fetchUserByUserName(String userName) {
        UserResponse<User> response = new UserResponse<User>();
        try {
            List<User> users = getEntityManager()
                    .createQuery("select u from User u where u.username = :userName", User.class)
                    .setParameter("userName", userName).getResultList();
            if (users.size() > 1) {
                response.setMessage("Multiple users found with same user name.");
                return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
            } else {
                if (!users.isEmpty()) {
                    User user = users.get(0);
                    response.setMessage("User found successfully.");
                    response.setData(user);
                    return Response.status(Response.Status.OK).entity(response).build();
                } else {
                    response.setMessage("User found is Empty.");
                    return Response.status(Response.Status.OK).entity(response).build();
                }
            }
        } catch (Exception e) {
            response.setMessage("Exception while updating user: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } finally {
            closeEntityManager();
        }
    }

    // Cleanup method to close EntityManagerFactory when application shuts down
    public void cleanup() {
        closeEntityManager();
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}