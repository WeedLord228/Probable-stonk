package ru.y.pivo.contoller;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.y.pivo.GeoIP;
import ru.y.pivo.LocationService;
import ru.y.pivo.entity.Product;
import ru.y.pivo.entity.Store;
import ru.y.pivo.repos.ProductRepo;
import ru.y.pivo.repos.StoreRepo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
public class MapController {
    @Autowired
    private ProductRepo ProductRepo;
    @Autowired
    private StoreRepo StoreRepo;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/map")
    public String map(Map<String, Object> model) throws IOException, GeoIp2Exception {
        String ip = request.getRemoteAddr();
        System.out.println(ip);
        if (!ip.equals("127.0.0.1")) {
            LocationService locationService = new LocationService();
            GeoIP geoIP = locationService.getLocation(ip);
            model.put("GeoIP", geoIP);
        }
        model.put("response", "");
        model.put("products", null);
        return "map";
    }

    @PostMapping("/filterName")
    public String nameFilter(
            @RequestParam String name,
            Map<String, Object> model
    ) {
        Iterable<Product> products;

        if (name != null && !name.isEmpty())
            products = ProductRepo.findByName(name);
        else
            return "map";

        model.put("products", products);

        model.put("response", "");

        return "map";
    }

//    @PostMapping("/map")
//    public  String add(
//            @RequestParam String name,
//            @RequestParam String address,
//            Map<String, Object> model
//    )
//    {
//        Store store = new Store(address,0);
//
//        Product product = new Product(store,name);
//
//        return "map";
//    }

    @PostMapping("/map")
    public String add(
            @RequestParam String name,
            @RequestParam String address,
            Map<String, Object> model
    ) {
        String response;
        Store store = StoreRepo.findByAddress(address);

        if (store == null) {
            store = new Store(address, 0);
            StoreRepo.save(store);
        }

        Product product = ProductRepo.findByNameAndAddress(name, address);

        if (product == null) {
            product = new Product(store, name);
            ProductRepo.save(product);
            response = "Ваш товар был успешно добавлен";
        } else
            response = "Кто то уже позаботился об этом благородном товаре в этом магазине!";

        model.put("products", null);
        model.put("response", response);

        return "map";
    }
}
