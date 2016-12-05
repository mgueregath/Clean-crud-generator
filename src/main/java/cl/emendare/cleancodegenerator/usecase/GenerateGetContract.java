/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.CommandLineWriterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.GenerateGetContractInterface;
import cl.emendare.cleancodegenerator.domain.contract.WriteInterfaceInterface;
import cl.emendare.cleancodegenerator.domain.entity.Entity;
import cl.emendare.cleancodegenerator.domain.entity.Interface;
import cl.emendare.cleancodegenerator.domain.entity.Method;
import cl.emendare.cleancodegenerator.domain.entity.Parameter;
import cl.emendare.cleancodegenerator.domain.factory.EntityFactory;
import cl.emendare.cleancodegenerator.domain.factory.InterfaceFactory;
import cl.emendare.cleancodegenerator.domain.factory.MethodFactory;
import cl.emendare.cleancodegenerator.domain.factory.ParameterFactory;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class GenerateGetContract implements GenerateGetContractInterface {
    
    private InterfaceFactory interfaceFactory;
    private WriteInterfaceInterface writeInterface;
    private CommandLineWriterAdapter ciWriter;
    
    public GenerateGetContract(
            InterfaceFactory interfaceFactory,
            WriteInterfaceInterface writeInterface,
            CommandLineWriterAdapter ciWriter
    ) {
        this.interfaceFactory = interfaceFactory;
        this.writeInterface = writeInterface;
        this.ciWriter = ciWriter;
    }

    @Override
    public String generate(
            String packg,
            String path,
            String name,
            String module,
            String author
    ) throws Exception {
        ciWriter.sendLine("Generating code for Get" + name + "Interface.java");
        
        String finalPackage = packg;
        finalPackage += ".domain.usecaseinterface.";
        finalPackage += module;
        finalPackage += ".";
        finalPackage += name.toLowerCase();
        
        Interface i = interfaceFactory.create(
                "Get" + name + "Interface",
                finalPackage                        
        );
        EntityFactory ef = new EntityFactory();
        ParameterFactory pf = new ParameterFactory();
        MethodFactory mf = new MethodFactory();
                
        Entity entity = ef.create(name, packg + ".domain.entity." + module + "." + name);
                
        Method getAllMethod = mf.create("getAll", "public", entity);
        getAllMethod.setReturnType("List");        
        i.addMethod(getAllMethod);  

        Method getByIdMethod = mf.create("getById", "public", entity);
        getByIdMethod.setReturnType(entity.getName());
        Parameter param = pf.create("id", "int");
        getByIdMethod.addParameter(param);
        
        i.addMethod(getByIdMethod);
        
        
        String pathToArchiveDirectory =
                path
                + "\\usecaseinterface\\" 
                + module
                + "\\"
                + name.toLowerCase();            
           
        if (!writeInterface.write(i, pathToArchiveDirectory, author)) {
            throw new Exception();
        }
        
        ciWriter.sendLine("Code for Get" + name + "Interface.java generated");
        
        return finalPackage + "." + i.getName();
    }
    
}
