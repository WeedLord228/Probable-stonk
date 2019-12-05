package ru.y.pivo;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.file.Files;

public class LocationService {

    private DatabaseReader dbReader;


    public LocationService() throws IOException {
        //Resource db = new ClassPathResource("GeoLite2-City.mmdb");
        File db = new File("base.mmdb");

        if(!db.exists()){
            try (final InputStream stream =  Application.class.getResourceAsStream(System.lineSeparator() + "GeoLite2-City.mmdb")){
                Files.copy(stream, db.toPath());
            }
        }

        dbReader = new DatabaseReader.Builder(db).build();
    }

    public GeoIP getLocation(String ip)
            throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);

        String cityName = response.getCity().getName();
        String latitude =
                response.getLocation().getLatitude().toString();
        String longitude =
                response.getLocation().getLongitude().toString();
        return new GeoIP(ip, cityName, latitude, longitude);
    }
}
