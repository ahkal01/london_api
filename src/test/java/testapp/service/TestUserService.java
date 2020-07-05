package testapp.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import testapp.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tests the UserService class.
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class TestUserService {

    @Mock
    UserServiceProvider userServiceProvider;

    @Mock
    CoordUtil coordUtil;

    @InjectMocks
    UserService userService;

    private static final int MILES_FROM_LONDON = 50;

    private List<User> allTestUsers;
    private User testUser1, testUser2;

    private static final int ID1 = 1;
    private static final int ID2 = 2;
    private static final String FIRST_NAME1 = "fn1";
    private static final String FIRST_NAME2 = "fn2";
    private static final String LAST_NAME1 = "ln1";
    private static final String LAST_NAME2 = "ln2";
    private static final String EMAIL1 = "fn1.ln1@mail.com";
    private static final String EMAIL2 = "fn2.ln2@mail.com";
    private static final String IP_ADDR1 = "10.10.10.1";
    private static final String IP_ADDR2 = "10.10.10.2";
    private static final double LAT1 = 51.6553959;
    private static final double LAT2 = -6.7098551;
    private static final double LONG1 = 0.0572553;
    private static final double LONG2 = 111.3479498;

    @Before
    public void init() {
        // Create two test users
        testUser1 = new User(ID1, FIRST_NAME1, LAST_NAME1, EMAIL1, IP_ADDR1, LAT1, LONG1);
        testUser2 = new User(ID2, FIRST_NAME2, LAST_NAME2, EMAIL2, IP_ADDR2, LAT2, LONG2);

        // Add the two test users to the allTestUsers list
        allTestUsers = new ArrayList<>();
        allTestUsers.add(testUser1);
        allTestUsers.add(testUser2);
    }

    @Test
    // When list of all users is empty
    // Then service returns an empty list.
    public void testGetUsersInLondon_NoUsers() {
        Mockito.when(userServiceProvider.getAllUsers()).thenReturn(new ArrayList<>());

        Assert.assertEquals(0, userService.getUsersInLondon(MILES_FROM_LONDON).size());
    }

    @Test
    // When there are no users whose current coordinates are within the specified number of miles of London
    // And there are no users listed as living in London
    // Then service returns an empty list.
    public void testGetUsersInLondon_NoUsersWithinDistance_NoUsersListedInLondon() {

        Mockito.when(userServiceProvider.getAllUsers()).thenReturn(allTestUsers);

        // Both users coordinates are not within the specified distance
        Mockito.when(coordUtil.withinLondon(LAT1, LONG1, MILES_FROM_LONDON)).thenReturn(false);
        Mockito.when(coordUtil.withinLondon(LAT2, LONG2, MILES_FROM_LONDON)).thenReturn(false);

        // No users are listed as living in London
        Mockito.when(userServiceProvider.getUsersInCity(UserService.LONDON)).thenReturn(new ArrayList<>());

        Assert.assertEquals(0, userService.getUsersInLondon(MILES_FROM_LONDON).size());
    }

    @Test
    // When there is one user whose current coordinates are within the specified number of miles of London
    // And there are no users listed as living in London
    // Then service returns a list with this one user whose current coordinates are within the specified number of miles of London.
    public void testGetUsersInLondon_OneUserWithinDistance_NoUsersListedInLondon() {

        Mockito.when(userServiceProvider.getAllUsers()).thenReturn(allTestUsers);

        // Coordinates of first user are within the specified distance
        Mockito.when(coordUtil.withinLondon(LAT1, LONG1, MILES_FROM_LONDON)).thenReturn(true);
        // Coordinates of second user are not within the specified distance
        Mockito.when(coordUtil.withinLondon(LAT2, LONG2, MILES_FROM_LONDON)).thenReturn(false);

        // No users are listed as living in London
        Mockito.when(userServiceProvider.getUsersInCity(UserService.LONDON)).thenReturn(new ArrayList<>());

        List<User> result = userService.getUsersInLondon(MILES_FROM_LONDON);

        // Result list contains first user
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(testUser1, result.get(0));

    }

    @Test
    // When there are no users whose current coordinates are within the specified number of miles of London
    // And there are is one user listed as living in London
    // Then service returns a list with this one user listed as living in London.
    public void testGetUsersInLondon_NoUserWithinDistance_OneUserListedInLondon() {

        Mockito.when(userServiceProvider.getAllUsers()).thenReturn(allTestUsers);

        // Coordinates of both users are not within the specified distance
        Mockito.when(coordUtil.withinLondon(LAT1, LONG1, MILES_FROM_LONDON)).thenReturn(false);
        Mockito.when(coordUtil.withinLondon(LAT2, LONG2, MILES_FROM_LONDON)).thenReturn(false);

        // Second user is listed as living in London
        Mockito.when(userServiceProvider.getUsersInCity(UserService.LONDON)).thenReturn(new ArrayList<>(Arrays.asList(testUser2)));

        List<User> result = userService.getUsersInLondon(MILES_FROM_LONDON);

        // Result list contains second user
        Assert.assertEquals(1, result.size());
        Assert.assertEquals(testUser2, result.get(0));
    }

    @Test
    // When there is one user whose current coordinates are within the specified number of miles of London
    // And there are is another user listed as living in London
    // Then service returns a list with the two users sorted on id.
    public void testGetUsersInLondon_OneUserWithinDistance_OneUserListedInLondon_Sorted() {

        Mockito.when(userServiceProvider.getAllUsers()).thenReturn(allTestUsers);

        // Coordinates of first user are not within the specified distance
        Mockito.when(coordUtil.withinLondon(LAT1, LONG1, MILES_FROM_LONDON)).thenReturn(false);

        // Coordinates of second user are within the specified distance
        Mockito.when(coordUtil.withinLondon(LAT2, LONG2, MILES_FROM_LONDON)).thenReturn(true);

        // First user is listed as living in London
        Mockito.when(userServiceProvider.getUsersInCity(UserService.LONDON)).thenReturn(new ArrayList<>(Arrays.asList(testUser1)));

        List<User> result = userService.getUsersInLondon(MILES_FROM_LONDON);

        // Result list contains both users
        Assert.assertEquals(2, result.size());
        // testUser1 is first in the list as this is sorted by id
        Assert.assertEquals(testUser1, result.get(0));
        Assert.assertEquals(testUser2, result.get(1));
    }

    @Test
    // When there are two users whose current coordinates are within the specified number of miles of London
    // And one of the users is also listed as living in London
    // Then service returns a list with the two users sorted on id and with no duplicates.
    public void testGetUsersInLondon_TwoUsersWithinDistance_OneSameUserListedInLondon_NoDuplicates() {

        Mockito.when(userServiceProvider.getAllUsers()).thenReturn(allTestUsers);

        // Coordinates of first user are within the specified distance
        Mockito.when(coordUtil.withinLondon(LAT1, LONG1, MILES_FROM_LONDON)).thenReturn(true);

        // Coordinates of second user are within the specified distance
        Mockito.when(coordUtil.withinLondon(LAT2, LONG2, MILES_FROM_LONDON)).thenReturn(true);

        // Second user is listed as living in London
        Mockito.when(userServiceProvider.getUsersInCity(UserService.LONDON)).thenReturn(new ArrayList<>(Arrays.asList(testUser2)));

        List<User> result = userService.getUsersInLondon(MILES_FROM_LONDON);

        // Result list contains the two users with no duplicates
        Assert.assertEquals(2, result.size());
        // testUser1 is first in the list as this is sorted by id
        Assert.assertEquals(testUser1, result.get(0));
        Assert.assertEquals(testUser2, result.get(1));
    }
}
