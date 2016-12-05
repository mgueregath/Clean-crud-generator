/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator;

import cl.emendare.cleancodegenerator.domain.adapter.CommandLineWriterAdapter;
import cl.emendare.cleancodegenerator.domain.adapter.FileReaderAdapter;
import cl.emendare.cleancodegenerator.domain.adapter.FileWriterAdapter;
import cl.emendare.cleancodegenerator.domain.adapter.JsonConverterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.GetConfigurationInterface;
import cl.emendare.cleancodegenerator.domain.contract.WriteClassInterface;
import cl.emendare.cleancodegenerator.domain.contract.WriteInterfaceInterface;
import cl.emendare.cleancodegenerator.domain.entity.Configuration;
import cl.emendare.cleancodegenerator.domain.entity.Class;
import cl.emendare.cleancodegenerator.domain.factory.ClassFactory;
import cl.emendare.cleancodegenerator.domain.factory.InterfaceFactory;
import cl.emendare.cleancodegenerator.domain.repository.ConfigurationRepositoryInterface;
import cl.emendare.cleancodegenerator.external.adapter.GsonJsonConverter;
import cl.emendare.cleancodegenerator.external.adapter.NativeCommanLineWriter;
import cl.emendare.cleancodegenerator.external.adapter.NativeFileReader;
import cl.emendare.cleancodegenerator.external.adapter.NativeFileWriter;
import cl.emendare.cleancodegenerator.external.repository.ConfigurationRepository;
import cl.emendare.cleancodegenerator.usecase.GenerateAddContract;
import cl.emendare.cleancodegenerator.usecase.GenerateDeleteContract;
import cl.emendare.cleancodegenerator.usecase.GenerateFactory;
import cl.emendare.cleancodegenerator.usecase.GenerateGetContract;
import cl.emendare.cleancodegenerator.usecase.GenerateJpaEntity;
import cl.emendare.cleancodegenerator.usecase.GenerateRepositoryImpl;
import cl.emendare.cleancodegenerator.usecase.GenerateRepositoryInterface;
import cl.emendare.cleancodegenerator.usecase.GenerateUpdateContract;
import cl.emendare.cleancodegenerator.usecase.GetClass;
import cl.emendare.cleancodegenerator.usecase.GetConfiguration;
import cl.emendare.cleancodegenerator.usecase.WriteClass;
import cl.emendare.cleancodegenerator.usecase.WriteInterface;
import cl.emendare.cleancodegenerator.usecase.WriteJpaClass;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class CleanCodeGenerator {
    
    private static final String JSON_CONFIGURATION = "cleancodegenerator.json";
    private static GetConfigurationInterface configurationService;
    private static ConfigurationRepositoryInterface repository;
    private static JsonConverterAdapter converter;
    private static InterfaceFactory interfaceFactory = new InterfaceFactory();
    private static ClassFactory classFactory = new ClassFactory();
    private static FileWriterAdapter writer = new NativeFileWriter();
    private static FileReaderAdapter reader = new NativeFileReader();
    private static WriteInterfaceInterface writeInterface = new WriteInterface(writer);
    private static WriteClassInterface writeClass = new WriteClass(writer);
    private static WriteClassInterface writeJpaClass = new WriteJpaClass(writer);
    private static CommandLineWriterAdapter ciWriter = new NativeCommanLineWriter();
    
    public static void main (String[] args) {
        String path, name, type;
        try {
            if (args.length < 3) throw new Exception();
            
            path = args[0];
            name = args[1];
            type = args[2];
                        
            converter = new GsonJsonConverter(path + "\\" + JSON_CONFIGURATION);
            repository = new ConfigurationRepository(converter);
            configurationService = new GetConfiguration(repository);
            
            Configuration configuration = configurationService.getConfiguration();
            
            GetClass getClass = new GetClass(reader);
            
            Class entity = getClass
                    .getEntityClass(
                            name,
                            configuration.getDomainPath(),
                            configuration.getModule()
                    );
            
            switch(type) {
                case "-domain":
                    generateDomainInterfaces(configuration, name, entity);
                    break;
                case "-jpa":
                    generateJpaEntity(configuration, name, entity);
                    break;
                case "-repository":
                    generateRepository(configuration, name, entity);
                    break;
                case "-factory":
                    generateFactory(configuration, name, entity);
                    break;
            }
                       
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
	}
    
    public static void generateDomainInterfaces (
            Configuration configuration,
            String name,
            Class entity
    ) throws Exception {
        GenerateGetContract generateGetService = new GenerateGetContract(
                    interfaceFactory,
                    writeInterface,
                    ciWriter
            );
            
            String getServicePackage = generateGetService.generate(
                    configuration.getPackage(),
                    configuration.getDomainPath(),
                    name,
                    configuration.getModule(),
                    configuration.getAuthor()
            );
            
            GenerateAddContract generateAddService = new GenerateAddContract(
                    interfaceFactory,
                    writeInterface,
                    ciWriter
            );
            
            String addServicePackage = generateAddService.generate(
                    configuration.getPackage(),
                    configuration.getDomainPath(),
                    name,
                    configuration.getModule(),
                    configuration.getAuthor(), 
                    entity
            );
            
            GenerateUpdateContract generateUpdateService = new GenerateUpdateContract(
                    interfaceFactory,
                    writeInterface,
                    ciWriter
            );
            
            String updateServicePackage = generateUpdateService.generate(
                    configuration.getPackage(),
                    configuration.getDomainPath(),
                    name,
                    configuration.getModule(),
                    configuration.getAuthor(), 
                    entity
            );
            
            GenerateDeleteContract generateDeleteService = new GenerateDeleteContract(
                    interfaceFactory,
                    writeInterface,
                    ciWriter
            );
            
            String deteleServicePackage = generateDeleteService.generate(
                    configuration.getPackage(),
                    configuration.getDomainPath(),
                    name,
                    configuration.getModule(),
                    configuration.getAuthor(), 
                    entity
            );
            
            GenerateRepositoryInterface generateRepositoryInterface = new GenerateRepositoryInterface(
                    interfaceFactory,
                    writeInterface,
                    ciWriter
            );
            
            String repositoryServicePackage = generateRepositoryInterface.generate(
                    configuration.getPackage(),
                    configuration.getDomainPath(),
                    name,
                    configuration.getModule(),
                    configuration.getAuthor(), 
                    entity
            );
    }
    
    public static void generateJpaEntity (
            Configuration configuration,
            String name,
            Class entity
    ) throws Exception {
        
        GenerateJpaEntity generateJpaService = new GenerateJpaEntity(
                classFactory,
                writeJpaClass,
                ciWriter
        );
            
        String jpaServicePackage = generateJpaService.generate(
                configuration.getPackage(),
                configuration.getPersistencePath(),
                name,
                configuration.getModule(),
                configuration.getAuthor(),
                entity
        );
    }
    
    public static void generateRepository (
            Configuration configuration,
            String name,
            Class entity
    ) throws Exception {
        
        GenerateRepositoryImpl generateRepositoryService = new GenerateRepositoryImpl (
                classFactory,
                writeClass,
                ciWriter
        );
            
        String repositoryServicePackage = generateRepositoryService.generate(
                configuration.getPackage(),
                configuration.getPersistencePath(),
                name,
                configuration.getModule(),
                configuration.getAuthor(),
                entity
        );
    }
    
    public static void generateFactory (
            Configuration configuration,
            String name,
            Class entity
    ) throws Exception {
        
        GenerateFactory generateFactoryService = new GenerateFactory(
                classFactory,
                writeClass,
                ciWriter
        );
            
        String factoryServicePackage = generateFactoryService.generate(
                configuration.getPackage(),
                configuration.getDomainPath(),
                name,
                configuration.getModule(),
                configuration.getAuthor(),
                entity
        );
    }
}
