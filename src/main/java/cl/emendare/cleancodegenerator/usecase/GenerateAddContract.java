/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.CommandLineWriterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.GenerateAddContractInterface;
import cl.emendare.cleancodegenerator.domain.contract.WriteInterfaceInterface;
import cl.emendare.cleancodegenerator.domain.entity.Entity;
import cl.emendare.cleancodegenerator.domain.entity.Interface;
import cl.emendare.cleancodegenerator.domain.entity.Method;
import cl.emendare.cleancodegenerator.domain.entity.Parameter;
import cl.emendare.cleancodegenerator.domain.factory.EntityFactory;
import cl.emendare.cleancodegenerator.domain.factory.InterfaceFactory;
import cl.emendare.cleancodegenerator.domain.factory.MethodFactory;
import cl.emendare.cleancodegenerator.domain.factory.ParameterFactory;
import cl.emendare.cleancodegenerator.domain.entity.Class;
import cl.emendare.cleancodegenerator.domain.entity.ClassProperty;
import cl.emendare.cleancodegenerator.external.util.PackageFormater;
import cl.emendare.cleancodegenerator.external.util.PathFormater;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class GenerateAddContract implements GenerateAddContractInterface {
    
    private InterfaceFactory interfaceFactory;
    private WriteInterfaceInterface writeInterface;
    private CommandLineWriterAdapter ciWriter;
    private PackageFormater packageFormater;
    private PathFormater pathFormater;

    
    public GenerateAddContract(
            InterfaceFactory interfaceFactory,
            WriteInterfaceInterface writeInterface,
            CommandLineWriterAdapter ciWriter
    ) {
        this.interfaceFactory = interfaceFactory;
        this.writeInterface = writeInterface;
        this.ciWriter = ciWriter;
        
        packageFormater = new PackageFormater();
        pathFormater = new PathFormater();
    }

    @Override
    public String generate (
            String packg,
            String path,
            String name,
            String module,
            String author,
            Class entity
    ) throws Exception {
        ciWriter.sendLine("Generating code for Add" + name + "Interface.java");
        
        String finalPackage = packageFormater.formatDomainUseCaseInterfacePackage(packg, module, name);
        
        Interface i = interfaceFactory.create(
                "Add" + name + "Interface",
                finalPackage                        
        );
        EntityFactory ef = new EntityFactory();
        ParameterFactory pf = new ParameterFactory();
        MethodFactory mf = new MethodFactory();
        
        Method addMethod = mf.create("add", "public", entity);
        addMethod.setReturnType(entity.getName()); 
        
        System.out.println(entity.getProperties().size());
        
        for (ClassProperty property: entity.getProperties()) {
            if (!property.getName().equalsIgnoreCase("id")) {
                Parameter param = pf.create(property.getName(), property.getType());
                addMethod.addParameter(param);
            }
        }
        
        i.addMethod(addMethod);  
        
        String pathToArchiveDirectory = 
                pathFormater.formatDomainUseCaseInterfacePath(path, module) 
                + name.toLowerCase();            
           
        if (!writeInterface.write(i, pathToArchiveDirectory, author)) {
            throw new Exception();
        }
        
        ciWriter.sendLine("Code for Add" + name + "Interface.java generated");
        
        return finalPackage + "." + i.getName();
    }
}
