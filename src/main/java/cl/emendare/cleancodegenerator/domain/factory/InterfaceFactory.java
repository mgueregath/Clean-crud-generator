/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.factory;

import cl.emendare.cleancodegenerator.domain.entity.Interface;
import cl.emendare.cleancodegenerator.domain.entity.Method;
import java.util.ArrayList;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class InterfaceFactory {
    public Interface create(
            String name,
            String packg
    ) {
        Interface inter = new Interface();
        inter.setName(name);
        inter.setPackage(packg);
        inter.setInterfaces(new ArrayList<Interface>());
        inter.setMethods(new ArrayList<Method>());
        
        return inter;
    }
}
