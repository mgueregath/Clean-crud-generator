/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.CommandLineWriterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.GenerateJpaEntityInterface;
import cl.emendare.cleancodegenerator.domain.contract.WriteClassInterface;
import cl.emendare.cleancodegenerator.domain.entity.Class;
import cl.emendare.cleancodegenerator.domain.factory.ClassFactory;
import cl.emendare.cleancodegenerator.domain.factory.ClassPropertyFactory;
import cl.emendare.cleancodegenerator.domain.factory.MethodFactory;
import cl.emendare.cleancodegenerator.domain.factory.ParameterFactory;
import cl.emendare.cleancodegenerator.external.util.PackageFormater;
import cl.emendare.cleancodegenerator.external.util.PathFormater;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class GenerateJpaEntity implements GenerateJpaEntityInterface {

    private ClassFactory cf;
    private WriteClassInterface writeClass;
    private CommandLineWriterAdapter ciWriter;
    private PackageFormater packageFormater;
    private PathFormater pathFormater;

    
    public GenerateJpaEntity(
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
        ciWriter.sendLine("Generating code for " + name + "Jpa.java");
        
        String finalPackage = packageFormater.formatExternalRepositoryPackage(packg, module);
        
        Class c = cf.create(
                name + "Jpa",
                finalPackage                        
        );
        ParameterFactory pf = new ParameterFactory();
        MethodFactory mf = new MethodFactory();
        ClassPropertyFactory cpf = new ClassPropertyFactory();
        
        c.setProperties(entity.getProperties());
        c.setImports(entity.getImports());
        c.setInheritance(entity);
        c.addImport(entity.getPackage());
        c.addImport("java.io.Serializable");
        c.addImport("javax.persistence.*");

        String pathToArchiveDirectory = 
                pathFormater.formatExternalEntityPath(path, module);         
           
        if (!writeClass.write(c, pathToArchiveDirectory, author)) {
            throw new Exception();
        }
        
        ciWriter.sendLine("Code for " + name + "Jpa.java generated");
        
        return finalPackage + "." + c.getName();
    }
    
}
