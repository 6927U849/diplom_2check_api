package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AnotherConfig {

    public static final String BASE_URL=loadFromPropertiesURL();
    public static final String ORDER_URL=loadFromPropertiesApiOrders();
    public static final String AUTH_USER= loadFromPropertiesAuthUser();

    private static String loadFromPropertiesAuthUser() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("BaseConfig.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("props" + props.getProperty("AUTH_USER"));
        return props.getProperty("AUTH_USER");
    }

    private static String loadFromPropertiesURL(){
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("BaseConfig.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("props" + props.getProperty("BASE_URL"));
        return props.getProperty("BASE_URL");
    }

    private static String loadFromPropertiesApiOrders(){
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("BaseConfig.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("props" + props.getProperty("ORDER_URL"));
        return props.getProperty("ORDER_URL");
    }

}

