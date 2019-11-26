package ru.y.pivo.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.y.pivo.entity.Product;
import ru.y.pivo.entity.Store;
import ru.y.pivo.repos.ProductRepo;
import ru.y.pivo.repos.StoreRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MapController {
    @Autowired
    private ProductRepo ProductRepo;
    @Autowired
    private StoreRepo StoreRepo;

    @GetMapping("/map")
    public String map(Map<String,Object> model)
    {
        model.put("name","");
        model.put("response","");
        model.put("products",null);
        return "map";
    }
    @PostMapping("filter")
    public String nameFilter(
            @RequestParam String name,
            Map<String, Object> model
    ) {
        Iterable<Product> products;

        if (name != null && !name.isEmpty())
            products = ProductRepo.findByName(name);
        else
            return "map";

        ArrayList<Store> stores = new ArrayList<>();

        for (Product p : products){
            stores.add(p.getStore_id());
        }

        model.put("name", ((List<Product>) products).get(0).getName());

        model.put("stores", stores);

        model.put("response", "Данный товар отметили в таких магазинах:");

        return "map";
    }

    @PostMapping("add")
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

        Product product = ProductRepo.findByStoreAndName(store,name);

        if (product == null) {
            product = new Product(store, name);
            ProductRepo.save(product);
            response = "Ваш товар был успешно добавлен";
        } else
            response = "Кто то уже позаботился об этом благородном товаре в этом магазине!";

        model.put("name",null);
        model.put("store", null);
        model.put("response", response);

        return "map";
    }
}
