package ru.y.pivo.contoller;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.y.pivo.LocationService.GeoIP;
import ru.y.pivo.LocationService.LocationService;
import ru.y.pivo.entity.Product;
import ru.y.pivo.entity.Store;
import ru.y.pivo.maps.JSONInfo;
import ru.y.pivo.maps.Pair;
import ru.y.pivo.repos.ProductRepo;
import ru.y.pivo.repos.StoreRepo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
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
        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        String ip = request.getRemoteAddr();
        GeoIP geoIP = new GeoIP("", "", 56.83, 60.61);

        if (!ip.equals("127.0.0.1") && !ip.equals("0:0:0:0:0:0:0:1")) {
            LocationService locationService = new LocationService();
            geoIP = locationService.getLocation(ip);
        }
        Product mostPopularProduct = ProductRepo.findTopByOrderByRequestedDesc();

        model.put("mostPopular", mostPopularProduct.getName());
        model.put("geoIP", geoIP);
        model.put("name", "");
        model.put("response", "");
        model.put("products", null);
        return "map";
    }

    @PostMapping("filter")
    public String nameFilter(@RequestParam String name, Map<String, Object> model) throws Exception {
        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        Iterable<Product> products;
        JSONInfo info = new JSONInfo();

        if (name != null && !name.isEmpty())
            products = ProductRepo.findByName(name);
        else
            return "map";

        ArrayList<Store> stores = new ArrayList<>();
        ArrayList<Pair> coordinates = new ArrayList<>();

        for (Product p : products) {
            stores.add(p.getStore_id());
        }
        for (Store s : stores) {
            Double[] coordiates = info.getCoordinates(s.getAddress());
            coordinates.add(new Pair(coordiates[1], coordiates[0]));
        }


        Product product = ProductRepo.findByName(name).get(0);

        product.setRequested(product.getRequested() + 1);
        ProductRepo.save(product);

        Product mostPopularProduct = ProductRepo.findTopByOrderByRequestedDesc();

        model.put("mostPopular", mostPopularProduct.getName());
        model.put("name", "Вы искали: " + name);
        model.put("stores", stores);
        model.put("response", "Данный товар отметили в таких магазинах:");
        model.put("coordinates", coordinates);
        return "map";
    }

    @PostMapping("add")
    public String add(@RequestParam String name, @RequestParam String address, Map<String, Object> model) throws Exception {

        model.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        String response;
        Store store = StoreRepo.findByAddress(address);

        if (store == null) {
            store = new Store(address, 0);
            StoreRepo.save(store);
        }

        Product product = ProductRepo.findByStoreAndName(store, name);

        if (product == null) {
            product = new Product(store, name);
            ProductRepo.save(product);
            response = "Ваш товар был успешно добавлен";
        } else
            response = "Кто то уже позаботился об этом благородном товаре в этом магазине!";


        Product mostPopularProduct = ProductRepo.findTopByOrderByRequestedDesc();

        model.put("mostPopular", mostPopularProduct.getName());
        model.put("name", "");
        model.put("store", "");
        model.put("response", response);

        return "map";
    }
}
