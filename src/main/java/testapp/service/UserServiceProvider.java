package testapp.service;

import testapp.model.User;
import java.util.List;

/**
 * Interface: UserServiceProvider
 *
 * User service provider interface
 *
 */

public interface UserServiceProvider {

    // Returns the list of all users.
    List<User> getAllUsers();

    // Returns the list of users listed as living in the given city.
    List<User> getUsersInCity(String city);
}
