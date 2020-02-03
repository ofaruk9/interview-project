package com.company;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	Customer c = new Customer();
        ObjectMapper mapper = new ObjectMapper();
        //CollectionType mapCollectionType = mapper.getTypeFactory()
        //      .constructCollectionType(List.class, Customer.class);
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        SimpleModule module =
                new SimpleModule("CustomDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(Customer.class, new CustomDeserializer());
        mapper.registerModule(module);
        File file = new File("src/main/resources/test.json");
        List<Customer> myObjects = null;
        Customer[] customers = null;

        try {
            c = (mapper.readValue(file, Customer.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        calculatePointsAndPrint(c);
        System.out.println("Test Point Calculator 120 " + calculatePoints(120));
    }

    private static void calculatePointsAndPrint(Customer c) {
        List<Customer> customers = c.getCustomers();
        for(Customer customer: customers) {
            System.out.println("Customer Name " + customer.getName());
            customer.getPurchases().entrySet().stream().forEach( e -> {
                        System.out.println("Month: " + e.getKey());
                        int points = e.getValue().stream().mapToInt(Main::calculatePoints).sum();
                        System.out.println("Earned points: " + points);
                    }

            );
        }
    }

    private static int calculatePoints(int number) {
        if(number < 50) return 0;
        int points = 0;
        if(number > 100) {
            int twoPoints = number - 100;
            points += twoPoints * 2  + ((number - twoPoints - 50) * 1);
        }
        else if(number > 50) {
            points += (number - 50) * 1;
        }
        return points;
    }
}
