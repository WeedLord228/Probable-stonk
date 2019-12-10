package ru.y.pivo.LocationService;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

public class LocationService {

    private DatabaseReader dbReader;

    public LocationService() throws IOException {
        Resource db = new ClassPathResource("GeoLite2-City.mmdb");

        File dbFile = db.getFile();

        dbReader = new DatabaseReader.Builder(dbFile).build();
    }

    public GeoIP getLocation(String ip)
            throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);

        String cityName = response.getCity().getName();
        double latitude =
                response.getLocation().getLatitude();
        double longitude =
                response.getLocation().getLongitude();
        return new GeoIP(ip, cityName, latitude, longitude);
    }
}
