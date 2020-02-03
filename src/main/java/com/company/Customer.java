package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer implements Serializable {
    String name;
    Map<String, List<Integer>> purchases = new HashMap<>();
    ArrayList<Customer> customers = new ArrayList<>();
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPurchases(Map<String, List<Integer>> purchases) {
        this.purchases = purchases;
    }

    public Map<String, List<Integer>> getPurchases() {
        return purchases;
    }

    public void setCustomers(Customer customer) {
        customers.add(customer);
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

}
