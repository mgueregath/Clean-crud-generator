/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.factory;

import cl.emendare.cleancodegenerator.domain.entity.Parameter;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class ParameterFactory {
    public Parameter create(
            String name,
            String type
    ){
        Parameter parameter= new Parameter();
        parameter.setName(name);
        parameter.setType(type);
        
        return parameter;
    }
}
