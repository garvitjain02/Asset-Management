package org.assetmanagement.util;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyUtil {
    
    public static String getPropertyString(String fileName)
    {
    	
    	String connectionString = null;
        Properties props = new Properties();
        try(InputStream input = DBPropertyUtil.class.getResourceAsStream(fileName)) {
           
                props.load(input);
                connectionString = props.getProperty("url") + "?user=" + props.getProperty("username") + "&password=" + props.getProperty("password");
                return connectionString;
         } catch (IOException e) {
        	 e.printStackTrace();
        } 
        return connectionString;

    }
   
}