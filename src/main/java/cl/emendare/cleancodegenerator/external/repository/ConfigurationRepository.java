/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.external.repository;

import cl.emendare.cleancodegenerator.domain.adapter.JsonConverterAdapter;
import cl.emendare.cleancodegenerator.domain.entity.Configuration;
import cl.emendare.cleancodegenerator.domain.repository.ConfigurationRepositoryInterface;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class ConfigurationRepository implements ConfigurationRepositoryInterface {
    
    private final JsonConverterAdapter converter;
    
    public ConfigurationRepository (JsonConverterAdapter converter) {
        this.converter = converter;
    }

    @Override
    public Configuration get() {
        return converter.toConfigurationObject();
    }
    
}
