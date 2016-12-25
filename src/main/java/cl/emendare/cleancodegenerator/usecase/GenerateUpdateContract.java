/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.CommandLineWriterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.GenerateInterface;
import cl.emendare.cleancodegenerator.domain.contract.WriteInterfaceInterface;
import cl.emendare.cleancodegenerator.domain.entity.Class;
import cl.emendare.cleancodegenerator.domain.entity.ClassProperty;
import cl.emendare.cleancodegenerator.domain.entity.Interface;
import cl.emendare.cleancodegenerator.domain.entity.Method;
import cl.emendare.cleancodegenerator.domain.entity.Parameter;
import cl.emendare.cleancodegenerator.domain.factory.EntityFactory;
import cl.emendare.cleancodegenerator.domain.factory.InterfaceFactory;
import cl.emendare.cleancodegenerator.domain.factory.MethodFactory;
import cl.emendare.cleancodegenerator.domain.factory.ParameterFactory;
import cl.emendare.cleancodegenerator.external.util.PackageFormater;
import cl.emendare.cleancodegenerator.external.util.PathFormater;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class GenerateUpdateContract implements GenerateInterface {
    
    private InterfaceFactory interfaceFactory;
    private WriteInterfaceInterface writeInterface;
    private CommandLineWriterAdapter ciWriter;
    private PackageFormater packageFormater;
    private PathFormater pathFormater;

    
    public GenerateUpdateContract(
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
        ciWriter.sendLine("Generating code for Update" + name + "Interface.java");
        
        String finalPackage = packageFormater.formatDomainUseCaseInterfacePackage(packg, module, name);
        
        Interface i = interfaceFactory.create(
                "Update" + name + "Interface",
                finalPackage                        
        );
        EntityFactory ef = new EntityFactory();
        ParameterFactory pf = new ParameterFactory();
        MethodFactory mf = new MethodFactory();
        
        Method addMethod = mf.create("update", "public", entity);
        addMethod.setReturnType(entity.getName()); 
        
        for (ClassProperty property: entity.getProperties()) {
            Parameter param = pf.create(property.getName(), property.getType());
            addMethod.addParameter(param);
        }
        
        i.addMethod(addMethod);  
        
        String pathToArchiveDirectory = 
                pathFormater.formatDomainUseCaseInterfacePath(path, module) 
                + name.toLowerCase();            
           
        if (!writeInterface.write(i, pathToArchiveDirectory, author)) {
            throw new Exception();
        }
        
        ciWriter.sendLine("Code for Update" + name + "Interface.java generated");
        
        return finalPackage + "." + i.getName();
    }
}
