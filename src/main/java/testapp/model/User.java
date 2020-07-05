package testapp.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;

/**
 * User - models a user.
 */

@JsonInclude(Include.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", position=1, required=true)
    private int id;
    @ApiModelProperty(value = "first_name", position=2, required=true)
    private String first_name;
    @ApiModelProperty(value = "last_name", position=3, required=true)
    private String last_name;
    @ApiModelProperty(value = "email", position=4, required=true)
    private String email;
    @ApiModelProperty(value = "ip_address", position=5, required=true)
    private String ip_address;
    @ApiModelProperty(value = "latitude", position=6, required=true)
    private Double latitude;
    @ApiModelProperty(value = "longitude", position=7, required=true)
    private Double longitude;

    // default constructor
    public User() {
    }

    public User(int id, String firstName, String lastName, String email, String ipAddress, Double latitude, Double longitude) {
        setId(id);
        setFirst_name(firstName);
        setLast_name(lastName);
        setEmail(email);
        setIp_address(ipAddress);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * Defines two users to be equal if they have the same id, first name and last name.
     * @param o object to compare to.
     * @return true if object is a user and has same id, first name and last name; otherwise false.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;
        return this.getId() == user.getId() &&
                Objects.equals(this.getFirst_name(), user.getFirst_name()) &&
                Objects.equals(this.getLast_name(), user.getLast_name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getFirst_name(), this.getLast_name());
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String firstName) {
        this.first_name = firstName;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String lastName) {
        this.last_name = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIp_address() {
        return this.ip_address;
    }

    public void setIp_address(String ipAddress) {
        this.ip_address = ipAddress;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}
