package ru.y.pivo.entity;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="store")
    private Store store;

    private String name;

    public Product() {
    }

    public Product(Store store_id, String name) {
        this.store= store_id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Store getStore_id() {
        return store;
    }

    public void setStore_id(Store store_id) {
        this.store = store_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
