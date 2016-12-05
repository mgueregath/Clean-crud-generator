/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.external.adapter;

import cl.emendare.cleancodegenerator.domain.adapter.FileReaderAdapter;
import cl.emendare.cleancodegenerator.domain.entity.Class;
import cl.emendare.cleancodegenerator.domain.factory.ClassFactory;
import cl.emendare.cleancodegenerator.domain.factory.ClassPropertyFactory;
import cl.emendare.cleancodegenerator.external.util.PathFormater;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class NativeFileReader implements FileReaderAdapter {
    
    PathFormater pathFormater;
    ClassFactory cf;
    ClassPropertyFactory pf;
    
    public NativeFileReader() {
        pathFormater = new PathFormater();
        cf = new ClassFactory();
        pf = new ClassPropertyFactory();
    }

    @Override
    public Class readClassFromFile(String name, String path, String module) {
                
        Class c = null;
        
        try (Stream<String> stream = Files.lines(Paths.get(
                pathFormater.formatDomainEntityPath(path, module) + name + ".java")
        )) {
			Object lines[] = stream.toArray();
            
            for (Object line: lines) {
                if (line.toString().toLowerCase().contains("package ")) {
                    c = cf.create(
                            name,
                            line
                                .toString()
                                .replace("package ", "")
                                .replace(";", "")
                                + "." + name
                    );
                    
                } else if (line.toString().toLowerCase().contains("import ")) {
                    c.addImport(line
                            .toString()
                            .replace("import ", "")
                            .replace(";", "")
                    );
                    
                } else if (line.toString().replace("    ", "").split(" ").length == 3 
                        && !line.toString().contains("(")
                        && !line.toString().contains(" = ")) {
                    
                    String property[] = line
                            .toString()
                            .replace("    ", "")
                            .replace(";", "")
                            .split(" ");
                    c.addProperty(pf.create(property[2], property[1], property[0]));
                    
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return c;
    }
    
}
