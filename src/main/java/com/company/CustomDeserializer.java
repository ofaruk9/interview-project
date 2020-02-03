package com.company;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.*;

public class CustomDeserializer extends StdDeserializer<Customer> {

    public CustomDeserializer() {
        this(null);
    }

    public CustomDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Customer deserialize(JsonParser parser, DeserializationContext deserializer) {
        Customer customer = null;
        Customer eachCustomer = null;
        customer = new Customer();
        ObjectCodec codec = parser.getCodec();
        JsonNode node = null;
        try {
            node = codec.readTree(parser);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<Map.Entry<String, JsonNode>> it = node.fields();


        while(it.hasNext()) {
            eachCustomer = null;
            eachCustomer = new Customer();
            Map.Entry<String, JsonNode> m =  it.next();
            String customerName = null;
            String monthName = null;
            customerName = m.getKey().toString();
            ArrayNode node2 = (ArrayNode) m.getValue().get("purchases");
            Iterator<JsonNode> values = node2.elements();

            Map<String, List<Integer>> map = null;
            map = new HashMap<>();
            while(values.hasNext()) {
                List<Integer> integerList = new ArrayList<>();

                JsonNode current = values.next();
                monthName = current.get("month").asText();
                current.get("values").elements().forEachRemaining( e ->
                        integerList.add(e.intValue())
                );
                //
                map.put(monthName, integerList);
            }
            eachCustomer.setName(customerName);
            eachCustomer.setPurchases(map);
            customer.setCustomers(eachCustomer);

        }

        return customer;
    }
}

