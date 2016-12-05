/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.FileWriterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.WriteClassInterface;
import cl.emendare.cleancodegenerator.domain.entity.Class;
import cl.emendare.cleancodegenerator.domain.entity.Interface;
import cl.emendare.cleancodegenerator.domain.entity.Method;
import cl.emendare.cleancodegenerator.domain.entity.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class WriteClass implements WriteClassInterface {
    
    private FileWriterAdapter fileWriter;
    
    public WriteClass(FileWriterAdapter fileWriter) {
        this.fileWriter = fileWriter;
    }

    @Override
    public boolean write(Class c, String path, String author) {
        List<String> lines = new ArrayList<String>();;
        lines.add("package " + c.getPackage() + ";");
        lines.add("");
        lines.add("/**");
        lines.add("*");
        lines.add("* @author " + author);
        lines.add("*/");
        lines.add(formatHeader(c.getName(),c.getInheritance().getName(), c.getInterfaces()));
        lines.add("");
        
        for (Method method: c.getMethods()) {
            lines.add(formatMethod(method));
        }
        
        lines.add("");
        lines.add("}");        
        
        return fileWriter.writeToFile(lines, c.getName(), path);
    }
    
    private String formatHeader(
            String name,
            String inheritance,
            List<Interface> interfaces
    ) {
        String headerLine = "";
        headerLine += "public class ";
        headerLine += name;
        headerLine += " extends ";
        headerLine += inheritance;
        headerLine += " implements ";
        
        for (Interface i: interfaces) {
            headerLine += i.getName();
            
            if (interfaces.indexOf(i) != interfaces.size() - 1) {
                headerLine += ", ";
            }
        }
        
        headerLine += " {";
        
        return headerLine;
    }
    
    private String formatMethod(Method method) {
        String methodLine = "    ";
        
        methodLine += method.getName();
        methodLine += "(";
        
        for (Parameter parameter: method.getParameters()) {
            methodLine += formatParameter(parameter);
            if (method.getParameters().indexOf(parameter) 
                    != method.getParameters().size() - 1) {
                methodLine += ", ";
            }
        }
        methodLine += ") {";
        methodLine += "\n\n}";
        
        return methodLine;
    }
    
    private String formatParameter(Parameter parameter) {
        String parameterLine = "";
        
        parameterLine += parameter.getType();
        parameterLine += " ";
        parameterLine += parameter.getName();
        
        return parameterLine;
    }
}
