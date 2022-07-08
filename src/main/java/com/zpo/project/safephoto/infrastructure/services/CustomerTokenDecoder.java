package com.zpo.project.safephoto.infrastructure.services;


import com.zpo.project.safephoto.infrastructure.interfaces.ICustomerTokenDecoder;
import com.zpo.project.safephoto.infrastructure.models.CustomerModel;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustomerTokenDecoder implements ICustomerTokenDecoder {
    @Override
    public CustomerModel getCustomerFromToken(String token) throws Exception {
        Pattern tokenPattern = Pattern.compile("(.*)(\\.)(.*)(\\.)(.*)");
        Matcher m = tokenPattern.matcher(token);

        try {
            m.matches();
        } catch (Exception e) {
            throw new IllegalArgumentException("Nieodpowiedni format tokenu!");
        }

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String body = new String(decoder.decode(m.group(3)));
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(body);

        try {
            object.get("password");
        } catch (Exception e) {
            throw new IllegalArgumentException("Nie ma hasla.");
        }

        String customerToken = (String) object.get("customerToken");
        String password = (String) object.get("password");
        Pattern customerTokenPattern = Pattern.compile("([A-Z]{2})([0-9]+)ex_([a-z-0-9]{8}-[a-z-0-9]{4}-[a-z-0-9]{4}-[a-z-0-9]{4}-[a-z-0-9]{12})([0-9]+.*)");
        Matcher matcher = customerTokenPattern.matcher(customerToken);

        try {
            matcher.matches();
        } catch (Exception e) {
            throw new IllegalArgumentException("Nieodpowiedni format customerToken.");
        }
        matcher.matches();

        String countryCode = matcher.group(1);
        int id = Integer.parseInt(matcher.group(2));
        UUID externalId = UUID.fromString(matcher.group(3));
        String[] enabledServicesString = matcher.group(4).split("\\.");
        ArrayList<Integer> enabledServices = new ArrayList<>();
        for (String s : enabledServicesString) {
            enabledServices.add(Integer.valueOf(s));
        }

        return new CustomerModel(countryCode, id, externalId, enabledServices, password);
    }

}
