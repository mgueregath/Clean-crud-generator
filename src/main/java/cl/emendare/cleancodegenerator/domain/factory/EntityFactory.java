/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.factory;

import cl.emendare.cleancodegenerator.domain.entity.Entity;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class EntityFactory {
    public Entity create(
            String name,
            String packg
    ){
        Entity entity = new Entity();
        entity.setName(name);
        entity.setPackage(packg);
        
        return entity;
    }
}
