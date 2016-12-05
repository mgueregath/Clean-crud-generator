/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.entity;

import java.util.List;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class Method {
    protected String name;
    protected String modifier;
    protected String returnType;
    protected List<Parameter> parameters;
    protected Class entity;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the modifier
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * @param modifier the modifier to set
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * @return the returnType
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * @param returnType the returnType to set
     */
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    /**
     * @return the parameters
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }
    
    /**
     * @param parameter the parameters to add
     */
    public void addParameter(Parameter parameter) {
        this.parameters.add(parameter);
    }
    
    /**
     * @param parameter the parameters to remove
     */
    public void removeParameter(Parameter parameter) {
        this.parameters.remove(parameter);
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(Class entity) {
        this.entity = entity;
    }

    /**
     * @return the entity
     */
    public Class getEntity() {
        return entity;
    }
    
}
