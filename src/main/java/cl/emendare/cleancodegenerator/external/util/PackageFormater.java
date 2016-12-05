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
public class PackageFormater {
    
    public String formatDomainUseCaseInterfacePackage(String parent, String module, String folder) {
        String finalPackage = parent;
        finalPackage += ".domain.usecaseinterface.";
        if(module != null && !module.isEmpty()) {
            finalPackage += module;
            finalPackage += ".";
        }
        finalPackage += folder.toLowerCase();
        
        return finalPackage;
    }
    
    public String formatDomainRepositoryPackage(String parent, String module) {
        String finalPackage = parent;
        finalPackage += ".domain.repository";
        if(module != null && !module.isEmpty()) {            
            finalPackage += ".";
            finalPackage += module;
        }
        
        return finalPackage;
    }
}
