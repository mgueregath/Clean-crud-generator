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
public class Class {
    protected String name;
    protected List<Interface> interfaces;
    protected List<Method> methods;
    protected Class inheritance;
    protected String packg;
    protected List<ClassProperty> properties;
    protected List<String> imports;

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
     * @return the interfaces
     */
    public List<Interface> getInterfaces() {
        return interfaces;
    }

    /**
     * @param interfaces the interfaces to set
     */
    public void setInterfaces(List<Interface> interfaces) {
        this.interfaces = interfaces;
    }
    
    /**
     * @param inter the interfaces to add
     */
    public void addInterface(Interface inter) {
        this.interfaces.add(inter);
    }
    
    /**
     * @param index the interface to remove
     */
    public void removeInterface(Interface inter) {
        this.interfaces.remove(inter);
    }

    /**
     * @return the inheritance
     */
    public Class getInheritance() {
        return inheritance;
    }

    /**
     * @param inheritance the inheritance to set
     */
    public void setInheritance(Class inheritance) {
        this.inheritance = inheritance;
    }

    /**
     * @return the methods
     */
    public List<Method> getMethods() {
        return methods;
    }

    /**
     * @param methods the methods to set
     */
    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }
    
    /**
     * @param method the method to add
     */
    public void addMethod(Method method) {
        this.methods.add(method);
    }
    
    /**
     * @param method the method to remove
     */
    public void removeMethod(Method method) {
        this.methods.remove(method);
    }
    
    /**
     * @return the packg
     */
    public String getPackage() {
        return packg;
    }

    /**
     * @param packg the packg to set
     */
    public void setPackage(String packg) {
        this.packg = packg;
    }

    /**
     * @return the properties
     */
    public List<ClassProperty> getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(List<ClassProperty> properties) {
        this.properties = properties;
    }
    
    /**
     * @param property the method to add
     */
    public void addProperty(ClassProperty property) {
        this.properties.add(property);
    }
    
    /**
     * @param property the method to remove
     */
    public void removeProperty(ClassProperty property) {
        this.properties.remove(property);
    }
    
    /**
     * @return the imports
     */
    public List<String> getImports() {
        return imports;
    }

    /**
     * @param imports the imports to set
     */
    public void setImports(List<String> imports) {
        this.imports = imports;
    }
    
    /**
     * @param imp the import to add
     */
    public void addImport(String imp) {
        this.imports.add(imp);
    }
    
    /**
     * @param imp the import to remove
     */
    public void removeImport(String imp) {
        this.imports.remove(imp);
    }
}
