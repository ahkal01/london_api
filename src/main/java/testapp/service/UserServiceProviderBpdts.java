package testapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import testapp.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class: UserServiceProviderBpdts
 *
 * Implementation of user service provider using the Bpdts API.
 *
 */

@Component
public class UserServiceProviderBpdts implements UserServiceProvider {

    private static final String url = "https://bpdts-test-app.herokuapp.com/";
    private static final String usersEndpoint = url + "users";
    private static final String usersInCityEndpoint = url + "city/%s/users";

    public List<User> getAllUsers() {
        return getRequest(usersEndpoint);
    }

    public List<User> getUsersInCity(String city) {
        return getRequest(String.format(usersInCityEndpoint, city));
    }

    private List<User> getRequest(String endPoint) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User[]> response =
                restTemplate.getForEntity(
                        endPoint,
                        User[].class);
        User[] users = response.getBody();
        if (users == null) {
            return new ArrayList<>();
        }
        else {
            return Arrays.asList(users);
        }
    }

}
