/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.factory;

import cl.emendare.cleancodegenerator.domain.entity.Configuration;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class ConfigurationFactory {
    public Configuration create(
        String language,
        String domainPath,
        String persistencePath,
        String useCasePath,
        String module,
        String packg,
        String author
    ) {
        Configuration config = new Configuration();
        config.setDomainPath(domainPath);
        config.setLanguage(language);
        config.setPersistencePath(persistencePath);
        config.setUseCasePath(useCasePath);
        config.setModule(module);
        config.setPackage(packg);
        config.setAuthor(author);
        
        return config;
    }
}
