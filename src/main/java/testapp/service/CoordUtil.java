package testapp.service;

import org.springframework.stereotype.Component;

/**
 * CoordUtil - component providing utility methods on coordinates.
 *
 */

@Component
public class CoordUtil {

    private static final double LONDON_LATITUDE = 51.509865;
    private static final double LONDON_LONGITUDE = -0.118092;

    /**
     * Check if the distance between the given latitude and longitude and those of London
     * are within the given number of miles.
     * @param latitude latitude to check.
     * @param longitude longitude to check.
     * @param miles distance to check against.
     * @return true if distance is less or equal to given number of miles; otherwise false.
     */
    public boolean withinLondon(Double latitude, Double longitude, int miles) {
        return validLatitude(latitude)
                && validLongitude(longitude)
                && distance(latitude, longitude, LONDON_LATITUDE, LONDON_LONGITUDE) <= miles;
    }

    /**
     * Check if a latitude is valid.
     * @param latitude latitude to check.
     * @return true if latitude is valid; otherwise false.
     */
    private boolean validLatitude(Double latitude) {
        return (latitude != null) && (latitude < 90) && (latitude > -90);
    }

    /**
     * Check if a longitude is valid.
     * @param longitude longitude to check.
     * @return true if longitude is valid; otherwise false.
     */
    private boolean validLongitude(Double longitude) {
        return (longitude != null) && (longitude < 180) && (longitude > -180);
    }

    /**
     * Calculate the distance in miles between two coordinates.
     * @param lat1 latitude of first coordinate.
     * @param lon1 longitude of first coordinate.
     * @param lat2 latitude of second coordinate.
     * @param lon2 longitude of second coordinate.
     * @return distance in miles between the two coordinates.
     */
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(lon1 - lon2));
        return Math.toDegrees(Math.acos(dist)) * 60 * 1.1515;
    }
}
