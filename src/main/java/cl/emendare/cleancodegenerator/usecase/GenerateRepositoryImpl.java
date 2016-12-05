/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.CommandLineWriterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.GenerateRepositoryImplInterface;
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
public class GenerateRepositoryImpl implements GenerateRepositoryImplInterface {
    
    private ClassFactory cf;
    private WriteClassInterface writeClass;
    private CommandLineWriterAdapter ciWriter;
    private PackageFormater packageFormater;
    private PathFormater pathFormater;

    
    public GenerateRepositoryImpl(
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
        ciWriter.sendLine("Generating code for " + name + "Repository.java");
        
        String finalPackage = packageFormater.formatExternalRepositoryPackage(packg, module);
        
        Class c = cf.create(
                name + "RepositoryImpl",
                finalPackage                        
        );
                
        ParameterFactory pf = new ParameterFactory();
        MethodFactory mf = new MethodFactory();
        ClassPropertyFactory cpf = new ClassPropertyFactory();
        InterfaceFactory inf = new InterfaceFactory();
        
        Interface i = inf.create(
                entity.getName() + "RepositoryInterface",
                packageFormater.formatDomainRepositoryPackage(packg, module)
                        + "." + entity.getName() + "RepositoryInterface");
        
        c.addInterface(i);
        
        ClassProperty property = cpf.create("entityManager", "EntityManager", "private");
        c.addProperty(property);
        
        c.addImport("javax.persistence.EntityManager");
        
        Method constructor = mf.create(c.getName(), "public", entity);
        Parameter parameter = pf.create("entityManager", "EntityManager");
        constructor.addParameter(parameter);
        constructor.setActions("this.entityManager = entityManager;");
        c.addMethod(constructor);
        
        Method findAll = mf.create("findAll", "public", entity);
        findAll.setReturnType("List");
        findAll.setActions("List<" + entity.getName() + "> list = entityManager\n" +
                "                .createQuery(\"FROM " + entity.getName() + "Jpa\")\n" +
                "                .getResultList();\n" +
                "        \n" +
                "        if (list.isEmpty()) {\n" +
                "            return null;\n" +
                "        }     \n" +
                "        \n" +
                "        return list;");
        c.addMethod(findAll);
        
        Method findById = mf.create("findById", "public", entity);
        findById.setReturnType(entity.getName());
        findById.setActions("List<" + entity.getName() + "> list = entityManager\n" +
                "                .createQuery(\"SELECT n FROM " + entity.getName() + "Jpa n WHERE id = :id\")\n" +
                "                .setParameter(\"id\" , id)\n" +
                "                .getResultList();\n" +
                "        \n" +
                "        if (list.isEmpty()) {\n" +
                "            return null;\n" +
                "        }        \n" +
                "        \n" +
                "        return list.get(0);");
        for (ClassProperty proper: entity.getProperties()) {
            if (proper.getName().equalsIgnoreCase("id")) {
                parameter = pf.create(proper.getName(), proper.getType());
                findById.addParameter(parameter);
            }
        }
        
        
        c.addMethod(findById);
        
        Method persist = mf.create("persist", "public", entity);
        persist.setReturnType("boolean");
        parameter = pf.create(entity.getName().toLowerCase(), entity.getName());
        persist.addParameter(parameter);
        c.addMethod(persist);
        
        Method delete = mf.create("delete", "public", entity);
        delete.setReturnType("boolean");
        parameter = pf.create(entity.getName().toLowerCase(), entity.getName());
        delete.addParameter(parameter);
        c.addMethod(delete);
        
        String pathToArchiveDirectory = 
                pathFormater.formatExternalRepositoryPath(path, module);         
           
        if (!writeClass.write(c, pathToArchiveDirectory, author)) {
            throw new Exception();
        }
        
        ciWriter.sendLine("Code for " + name + "Repository.java generated");
        
        return finalPackage + "." + c.getName();
    }
    
}
