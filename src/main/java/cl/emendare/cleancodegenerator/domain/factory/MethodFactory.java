/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.factory;

import cl.emendare.cleancodegenerator.domain.entity.Class;
import cl.emendare.cleancodegenerator.domain.entity.Method;
import cl.emendare.cleancodegenerator.domain.entity.Parameter;
import java.util.ArrayList;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class MethodFactory {
    public Method create(
            String name,
            String modifier,
            Class entity
    ) {
        Method method = new Method();
        method.setModifier(modifier);
        method.setName(name);
        method.setParameters(new ArrayList<Parameter>());
        method.setEntity(entity);
        
        return method;
    }
}
