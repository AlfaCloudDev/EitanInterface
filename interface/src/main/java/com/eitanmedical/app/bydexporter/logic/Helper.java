package com.eitanmedical.app.bydexporter.logic;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Helper {
    
    public Helper(){
    }

    public static String getAppName(){
        String filePath = "interface/manifest.yml";
        Map<String, Object> config = readConfig(filePath);
        
        String appName = "";
        if (config != null) {
            // Access values from the config map
            List<Object> applicationsList = castToArrayList(config.get("applications"));

            if (applicationsList != null && !applicationsList.isEmpty()) {
               // Get the first element from the "applications" list
               LinkedHashMap<Object, Object> firstElement = castToLinkedHashMap(applicationsList.get(0));

               if (firstElement != null && !firstElement.isEmpty()) {
                   // Get the first entry from the LinkedHashMap
                   Map.Entry<Object, Object> firstEntry = firstElement.entrySet().iterator().next();
                   if (firstEntry != null){
                    appName =  firstEntry.getValue().toString();
                   }
               }
            } 
        } 
        return appName;
    }


    private static Map<String, Object> readConfig(String filePath) {
        try (InputStream input = new FileInputStream(filePath)) {
            Yaml yaml = new Yaml();
            return yaml.load(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
  
    @SuppressWarnings("unchecked")
      private static <T> List<T> castToArrayList(Object obj) {
          return (List<T>) obj;
      }
  
      @SuppressWarnings("unchecked")
      private static <K, V> LinkedHashMap<K, V> castToLinkedHashMap(Object obj) {
          return (LinkedHashMap<K, V>) obj;
      }


}
