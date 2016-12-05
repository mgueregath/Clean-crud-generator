/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.external.adapter;

import cl.emendare.cleancodegenerator.domain.adapter.JsonConverterAdapter;
import cl.emendare.cleancodegenerator.domain.entity.Configuration;
import cl.emendare.cleancodegenerator.domain.factory.ConfigurationFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileReader;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class GsonJsonConverter implements JsonConverterAdapter {
    
    private JsonParser parser;
    private String filePath;
    private ConfigurationFactory factory;
    
    public GsonJsonConverter (String filePath) {
         parser = new JsonParser();
         this.filePath = filePath;
         factory = new ConfigurationFactory();
    }
    
    @Override
    public Configuration toConfigurationObject() {
        
        JsonObject jsonObject = new JsonObject();
        
        try {
            JsonElement jsonElement = parser.parse(new FileReader(filePath));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (Exception e) {
            
        }
                
        Configuration config = factory.create(
                jsonObject.get("language").toString().replace("\"", ""),
                jsonObject.get("domain").toString().replace("\"", ""),
                jsonObject.get("persistence").toString().replace("\"", ""),
                jsonObject.get("usecase").toString().replace("\"", ""),
                jsonObject.get("module").toString().replace("\"", ""),
                jsonObject.get("package").toString().replace("\"", ""),
                jsonObject.get("author").toString().replace("\"", "")
        );       
        
        return config;
    }
}
