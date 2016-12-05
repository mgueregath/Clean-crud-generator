/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.entity;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class ClassProperty {
    protected String name;
    protected String type;
    protected String modifier;
    protected String packg;

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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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
     * @return the packg
     */
    public String getPackg() {
        return packg;
    }

    /**
     * @param packg the packg to set
     */
    public void setPackg(String packg) {
        this.packg = packg;
    }
}
