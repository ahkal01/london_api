package testapp;

import java.util.List;

import io.swagger.annotations.Api;
import testapp.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import testapp.service.UserService;

/**
 * Class: UserController
 *
 * User REST Controller.
 *
 */

@Api(tags = {"London"})
@RestController
@RequestMapping(value = "/london")
public class UserController {
	
    private final UserService userService;

    // Auto-wire user service
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
    * Handles requests to return people who are listed as either living in London,
    * or whose current coordinates are within the specified number of miles of London.
    */
    @GetMapping(
            value = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            value = "Return people who are listed as either living in London, or whose current coordinates are within the specified number of miles of London.",
            notes = "If the number of miles of London is not specified, a default value of 50 miles will be used.",
            response = User.class,
            responseContainer = "List")
    public ResponseEntity<List<User>> getUsersInLondon (
           @ApiParam(value = "Number of miles of London", example = "50")
                @RequestParam(value = "miles", defaultValue = "50") Integer miles ) {
    	
           return new ResponseEntity<>(
        	   userService.getUsersInLondon(miles),
                   HttpStatus.OK);
    }
    
}
