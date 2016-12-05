/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.factory;

import cl.emendare.cleancodegenerator.domain.entity.Class;
import cl.emendare.cleancodegenerator.domain.entity.Interface;
import cl.emendare.cleancodegenerator.domain.entity.Method;
import java.util.ArrayList;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class ClassFactory {
    public Class create(
            String name,
            String packg
    ) {
        Class c = new Class();
        c.setName(name);
        c.setPackage(packg);
        c.setInterfaces(new ArrayList<Interface>());
        c.setMethods(new ArrayList<Method>());
        c.setProperties(new ArrayList<>());
        c.setImports(new ArrayList<>());
        
        return c;        
    }
}
