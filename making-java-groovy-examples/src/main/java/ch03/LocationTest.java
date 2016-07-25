package ch03;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Darshan on 7/17/16.
 */
public class LocationTest {
    @Test
    public void testLocation() {
        Location loc = new Location();
        loc.setStreet("Neptune Living Point");
        loc.setCity("Mumbai");
        loc.setState("Maharashtra");

        Geocoder geocoder = new Geocoder();
        geocoder.fillInLatLng(loc);
        assertEquals(19.0759837, loc.getLatitude(), 0.001);
        assertEquals(72.8776559, loc.getLongitude(), 0.001);
    }
}
