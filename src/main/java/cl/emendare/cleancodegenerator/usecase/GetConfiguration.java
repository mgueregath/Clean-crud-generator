/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.contract.GetConfigurationInterface;
import cl.emendare.cleancodegenerator.domain.entity.Configuration;
import cl.emendare.cleancodegenerator.domain.repository.ConfigurationRepositoryInterface;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class GetConfiguration implements GetConfigurationInterface{

    private ConfigurationRepositoryInterface repository;
    
    public GetConfiguration (ConfigurationRepositoryInterface repository) {
        this.repository = repository;
    }
    
    @Override
    public Configuration getConfiguration() {
        return repository.get();
    }
    
}
