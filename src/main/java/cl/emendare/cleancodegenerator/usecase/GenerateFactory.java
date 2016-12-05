/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.CommandLineWriterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.GenerateFactoryInterface;
import cl.emendare.cleancodegenerator.domain.contract.WriteClassInterface;
import cl.emendare.cleancodegenerator.domain.entity.Class;
import cl.emendare.cleancodegenerator.domain.entity.ClassProperty;
import cl.emendare.cleancodegenerator.domain.entity.Interface;
import cl.emendare.cleancodegenerator.domain.entity.Method;
import cl.emendare.cleancodegenerator.domain.entity.Parameter;
import cl.emendare.cleancodegenerator.domain.factory.ClassFactory;
import cl.emendare.cleancodegenerator.domain.factory.ClassPropertyFactory;
import cl.emendare.cleancodegenerator.domain.factory.InterfaceFactory;
import cl.emendare.cleancodegenerator.domain.factory.MethodFactory;
import cl.emendare.cleancodegenerator.domain.factory.ParameterFactory;
import cl.emendare.cleancodegenerator.external.util.PackageFormater;
import cl.emendare.cleancodegenerator.external.util.PathFormater;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class GenerateFactory implements GenerateFactoryInterface {

    private ClassFactory cf;
    private WriteClassInterface writeClass;
    private CommandLineWriterAdapter ciWriter;
    private PackageFormater packageFormater;
    private PathFormater pathFormater;

    
    public GenerateFactory (
            ClassFactory cf,
            WriteClassInterface writeClass,
            CommandLineWriterAdapter ciWriter
    ) {
        this.cf = cf;
        this.writeClass = writeClass;
        this.ciWriter = ciWriter;
        
        packageFormater = new PackageFormater();
        pathFormater = new PathFormater();
    }
    
    @Override
    public String generate(
            String packg,
            String path,
            String name,
            String module,
            String author,
            Class entity
    ) throws Exception {
        ciWriter.sendLine("Generating code for " + name + "Factory.java");
        
        String finalPackage = packageFormater.formatDomainFactoryPackage(packg, module);
        
        Class c = cf.create(
                name + "Factory",
                finalPackage                        
        );
                
        ParameterFactory pf = new ParameterFactory();
        MethodFactory mf = new MethodFactory();
        ClassPropertyFactory cpf = new ClassPropertyFactory();
        InterfaceFactory inf = new InterfaceFactory();
             
        Method create = mf.create("create", "public", entity);
        
        String actions = entity.getName() + " ";
        actions += entity.getName().toLowerCase() + " = ";
        actions += "new ";
        actions += entity.getName() + "();";
        
        for (ClassProperty proper: entity.getProperties()) {
            System.out.println(proper.getName());
            if (!proper.getName().equalsIgnoreCase("List")) {
                Parameter parameter = pf.create(proper.getName(), proper.getType());
                create.addParameter(parameter);
                
                actions += "\n        " + entity.getName().toLowerCase() 
                        + ".set" + proper.getName()
                        + "(" + proper.getName() 
                        + ");";
            }
        }
        
        actions += "\n\n        return " + entity.getName().toLowerCase();
        
        create.setActions(actions);
        
        
        c.addMethod(create);
        
        String pathToArchiveDirectory = 
                pathFormater.formatDomainFactoryPath(path, module);         
           
        System.out.println(pathToArchiveDirectory);
        
        if (!writeClass.write(c, pathToArchiveDirectory, author)) {
            throw new Exception();
        }
        
        ciWriter.sendLine("Code for " + name + "Factory.java generated");
        
        return finalPackage + "." + c.getName();
    }
    
}
