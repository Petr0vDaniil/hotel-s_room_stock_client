package com.example.applic_client.models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    InputStream inputStream;


    private Properties open_properties() throws IOException {
        try {
            Properties prop = new Properties();
            String propFileName = "application.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Properties file " + propFileName + "not found in the classpath");
            }

            return prop;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert inputStream != null;
            inputStream.close();
        }
    }

    public String getMySqlUrl() {
        try {
            Properties prop = open_properties();
            return prop.getProperty("db_url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getMySqlUsername(){
        try {
            Properties prop = open_properties();
            return prop.getProperty("db_username");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };

    public String getMySqlPass(){
        try {
            Properties prop = open_properties();
            return prop.getProperty("db_password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };

    public String getProp(String prop_name){
        try {
            Properties prop = open_properties();
            return prop.getProperty(prop_name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
