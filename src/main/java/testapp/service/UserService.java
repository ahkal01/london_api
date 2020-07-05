package testapp.service;

import testapp.model.User;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class: UserService
 *
 * Provides a user service.
 * It is injected into UserController.
 *
 */
@Service
public class UserService {

    private final UserServiceProvider userServiceProvider;
    private final CoordUtil coordUtil;

    static final String LONDON = "London";

    public UserService(UserServiceProvider userServiceProvider, CoordUtil coordUtil) {
        this.userServiceProvider = userServiceProvider;
        this.coordUtil = coordUtil;
    }
    
    /**
     * Get list of users who are listed as either living in London,
     * or whose current coordinates are within the specified number of miles of London.
     * @param miles distance to check against.
     * @return list of users.
     */
    public List<User> getUsersInLondon(int miles) {

        List<User> allUsers = userServiceProvider.getAllUsers();
        if (allUsers.isEmpty()) {
            // if the list of all users is empty, then result will be empty.
            return allUsers;
        }

        // Filter list of users with current coordinates within the specified number of miles of London.
        List<User> usersByDistance = allUsers
                .stream()
                .filter(user -> coordUtil.withinLondon(user.getLatitude(), user.getLongitude(), miles))
                .collect(Collectors.toList());

        // Get list of people who are listed as living in London.
        List<User> usersListed = userServiceProvider.getUsersInCity(LONDON);

        // Concatenate the two lists, removing any duplicates and sorting on user id.
        return Stream.concat(usersByDistance.stream(), usersListed.stream())
                .distinct()
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
    }
}
