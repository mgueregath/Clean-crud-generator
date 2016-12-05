/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.CommandLineWriterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.GenerateRepositoryInterfaceInterface;
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
public class GenerateRepositoryInterface implements GenerateRepositoryInterfaceInterface {
    private InterfaceFactory interfaceFactory;
    private WriteInterfaceInterface writeInterface;
    private CommandLineWriterAdapter ciWriter;
    private PackageFormater packageFormater;
    private PathFormater pathFormater;

    
    public GenerateRepositoryInterface(
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
        ciWriter.sendLine("Generating code for " + name + "RepositoryInterface.java");
        
        String finalPackage = packageFormater.formatDomainRepositoryPackage(packg, module);
        
        Interface i = interfaceFactory.create(
                name + "RepositoryInterface",
                finalPackage                        
        );
        EntityFactory ef = new EntityFactory();
        ParameterFactory pf = new ParameterFactory();
        MethodFactory mf = new MethodFactory();
        
        Method getByIdMethod = mf.create("findById", "public", entity);
        getByIdMethod.setReturnType(entity.getName()); 
        
        for (ClassProperty property: entity.getProperties()) {
            if (!property.getName().equalsIgnoreCase("id")) {
                Parameter param = pf.create(property.getName(), property.getType());
                getByIdMethod.addParameter(param);
            }
        }
        
        Method getAllMethod = mf.create("findAll", "public", entity);
        getAllMethod.setReturnType("List");
        
        Method persistMethod = mf.create("persist", "public", entity);
        persistMethod.setReturnType("boolean");
        Parameter param = pf.create(entity.getName().toLowerCase(), entity.getName());
        persistMethod.addParameter(param);
        
        Method deleteMethod = mf.create("delete", "public", entity);
        deleteMethod.setReturnType("boolean");        
        deleteMethod.addParameter(param);
        
        i.addMethod(getByIdMethod);
        i.addMethod(getAllMethod);
        i.addMethod(persistMethod);
        i.addMethod(deleteMethod);
        
        String pathToArchiveDirectory = 
                pathFormater.formatDomainRepositoryPath(path, module);            
           
        if (!writeInterface.write(i, pathToArchiveDirectory, author)) {
            throw new Exception();
        }
        
        ciWriter.sendLine("Code for " + name + "RepositoryInterface.java generated");
        
        return finalPackage + "." + i.getName();
    }
}
