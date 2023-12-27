package com.hcl.ask_buddy.service;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.stereotype.Component;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//@Component
//public class EmailValidator {
//
//    private final OkHttpClient client = new OkHttpClient();
//
//    private final String apiKey = "acaf0ec805cb43b59e3688fd63b0cb2a";
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    public boolean isValidEmail(String email) throws Exception {
//        Request request = new Request.Builder()
//                .url("https://api.zerobounce.net/v2/validate?api_key=" + apiKey + "&email=" + email)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            String jsonBody = response.body().string();
//            JsonNode rootNode = objectMapper.readTree(jsonBody);
//
//            JsonNode statusNode = rootNode.get("status");
//            System.out.println(statusNode);
//            if (statusNode != null && !statusNode.isNull()) { 
//                String status = statusNode.asText();
//                return status.equalsIgnoreCase("valid");
//            }
//
//            return false; // Invalid email if status is missing or null
//        }
//    }
//
//}

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import org.springframework.stereotype.Component;

@Component
public class EmailValidator {

    public boolean isValidEmail(String email) {
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();

            String domain = email.substring(email.indexOf("@") + 1);
            String host = domain;

            Properties props = new Properties();
            props.put("mail.smtp.host", host);

            Session session = Session.getInstance(props);
            Transport transport = session.getTransport("smtp");
            System.out.println(transport);
            transport.connect();

            transport.close();

            return true;
        } catch (AddressException | NoSuchProviderException | AuthenticationFailedException e) {
        	System.out.println(e);
            return false; 
        } catch (Exception e) {
            // Handle other exceptions
        	System.out.println(e);
            return false;
        }
    }
}
