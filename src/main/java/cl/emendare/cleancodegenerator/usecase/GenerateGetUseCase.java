/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.CommandLineWriterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.GenerateInterface;
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
public class GenerateGetUseCase implements GenerateInterface {

    private ClassFactory cf;
    private WriteClassInterface writeClass;
    private CommandLineWriterAdapter ciWriter;
    private PackageFormater packageFormater;
    private PathFormater pathFormater;
    
    public GenerateGetUseCase (
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
    public String generate(String packg, String path, String name, String module, String author, Class entity) throws Exception {
        ciWriter.sendLine("Generating code for Get" + name + "Impl.java");
        
        String finalPackage = packageFormater.formatUseCasePackage(packg, module, name);
        
        Class c = cf.create(
                "Get" + name + "Impl",
                finalPackage                        
        );
                
        ParameterFactory pf = new ParameterFactory();
        MethodFactory mf = new MethodFactory();
        ClassPropertyFactory cpf = new ClassPropertyFactory();
        InterfaceFactory inf = new InterfaceFactory();
        
        Interface i = inf.create(
                "Get" + entity.getName() + "Interface",
                packageFormater.formatDomainUseCaseInterfacePackage(packg, module, name)
                        + ".Get" + entity.getName() + "Interface");
        
        //packageFormater.formatDomainRepositoryPackage(packg, module)
        
        c.addInterface(i);
        
        ClassProperty property = cpf.create(
                "repository",
                entity.getName() + "RepositoryInterface", "private"
        );
        c.addProperty(property);
        
        c.addImport(
                packageFormater.formatDomainRepositoryPackage(packg, module) 
                        + "." + name + "RepositoryInterface"
        );
        
        Method constructor = mf.create(c.getName(), "public", entity);
        Parameter parameter = pf.create("repository", entity.getName() + "RepositoryInterface");
        constructor.addParameter(parameter);
        constructor.setActions("this.repository = repository;");
        c.addMethod(constructor);
        
        Method findAll = mf.create("getAll", "public", entity);
        findAll.setReturnType("List");
        findAll.setActions("List<" + name + "> " + name.toLowerCase() + " = repository.findAll();\n" +
                "        \n" +
                "        return " + name.toLowerCase() + ";");
        c.addMethod(findAll);
        
        Method findById = mf.create("getById", "public", entity);
        findById.setReturnType(entity.getName());
        findById.setActions("return repository.findById(id);");
        for (ClassProperty proper: entity.getProperties()) {
            if (proper.getName().equalsIgnoreCase("id")) {
                parameter = pf.create(proper.getName(), proper.getType());
                findById.addParameter(parameter);
            }
        }
        
        
        c.addMethod(findById);
                
        String pathToArchiveDirectory = 
                pathFormater.formatUseCasePath(path, module, name);         
           
        if (!writeClass.write(c, pathToArchiveDirectory, author)) {
            throw new Exception();
        }
        System.out.println(pathToArchiveDirectory);
        ciWriter.sendLine("Code for Get" + name + "Impl.java generated");
        
        return finalPackage + "." + c.getName();
    }
    
}
