/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.external.util;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class PathFormater {
    
    public String formatDomainEntityPath(String path, String module) {
        String domainPath = "";
        domainPath += path;
        domainPath += "\\entity\\";
        
        if(module != null && !module.isEmpty()) {
            domainPath += module;
            domainPath += "\\";
        }
        return domainPath;
    }
    
     public String formatDomainFactoryPath(String path, String module) {
        String domainPath = "";
        domainPath += path;
        domainPath += "\\factory\\";
        
        if(module != null && !module.isEmpty()) {
            domainPath += module;
            domainPath += "\\";
        }
        return domainPath;
    }
    
    public String formatDomainUseCaseInterfacePath(String path, String module) {
        String domainPath = "";
        domainPath += path;
        domainPath += "\\usecaseinterface\\";
        
        if(module != null && !module.isEmpty()) {
            domainPath += module;
            domainPath += "\\";
        }
        return domainPath;
    }
    
    public String formatDomainRepositoryPath(String path, String module) {
        String domainPath = "";
        domainPath += path;
        domainPath += "\\repository\\";
        
        if(module != null && !module.isEmpty()) {
            domainPath += module;
            domainPath += "\\";
        }
        return domainPath;
    }
    
    public String formatExternalEntityPath(String path, String module) {
        String domainPath = "";
        domainPath += path;
        domainPath += "\\entity";
        
        if(module != null && !module.isEmpty()) {
            domainPath += "\\";
            domainPath += module;
        }
        return domainPath;
    }
    
    public String formatExternalRepositoryPath(String path, String module) {
        String domainPath = "";
        domainPath += path;
        domainPath += "\\repository";
        
        if(module != null && !module.isEmpty()) {
            domainPath += "\\";
            domainPath += module;
        }
        return domainPath;
    }
    
}
