/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.factory;

import cl.emendare.cleancodegenerator.domain.entity.ClassProperty;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class ClassPropertyFactory {
    public ClassProperty create (
            String name,
            String type,
            String modifier
    ){
        ClassProperty property = new ClassProperty();
        property.setModifier(modifier);
        property.setName(name);
        property.setType(type);
        
        return property;
    }
}
