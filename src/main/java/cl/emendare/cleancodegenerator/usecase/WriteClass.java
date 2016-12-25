/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.FileWriterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.WriteClassInterface;
import cl.emendare.cleancodegenerator.domain.entity.Class;
import cl.emendare.cleancodegenerator.domain.entity.ClassProperty;
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
        
        for (String line: c.getImports()) {
            lines.add("import " + line + ";");
        }
        
        for (Interface inter: c.getInterfaces()) {
            lines.add("import " + inter.getPackage() + ";");
        }
        
        lines.add("");
        lines.add("/**");
        lines.add("*");
        lines.add("* @author " + author);
        lines.add("*/");
        lines.add(formatHeader(c.getName(),c.getInheritance(), c.getInterfaces()));
        lines.add("");
        
        for (ClassProperty property: c.getProperties()) {
            lines.add("    " + property.getType() + " " + property.getName() + ";");
        }
        
        for (Method method: c.getMethods()) {
            
            if (!c.getInterfaces().isEmpty() && !method.getName().equalsIgnoreCase(c.getName())) {
                lines.add("\n    @Override");
            } else {
                lines.add("");
            }
            
            lines.add(formatMethod(method, c.getName()));
            
            if (method.getReturnType() != null && method.getReturnType().equalsIgnoreCase("List") &&
                    !lines.contains("import java.util.List;")) {
                lines.add(2, "import java.util.List;");
            }
            
            String entityImport = "import " 
                    + method.getEntity().getPackage()
                    + ";";
            if (!lines.contains(entityImport)) {
                lines.add(2, entityImport);
            }
        }
        
        lines.add("}");        
        
        return fileWriter.writeToFile(lines, c.getName(), path);
    }
    
    private String formatHeader(
            String name,
            Class inheritance,
            List<Interface> interfaces
    ) {
        String headerLine = "";
        headerLine += "public class ";
        headerLine += name;
        
        if (inheritance != null) {
            headerLine += " extends ";
            headerLine += inheritance.getName();
        }
        
        
        if (!interfaces.isEmpty()) {
            headerLine += " implements ";
        
            for (Interface i: interfaces) {
                headerLine += i.getName();

                if (interfaces.indexOf(i) != interfaces.size() - 1) {
                    headerLine += ", ";
                }
            }
        }
        
        headerLine += " {";
        
        return headerLine;
    }
    
    private String formatMethod(Method method, String className) {
        String methodLine = "    public ";
        
        if (!method.getName().equals(className)) {
            if (method.getReturnType() != null && method.getReturnType().equalsIgnoreCase("List")) {
                methodLine += "List<";
                methodLine += method.getEntity().getName();
                methodLine += "> ";
            } else if (method.getReturnType() != null) {
                methodLine += method.getReturnType() + " ";
            } else {
                methodLine += "void ";
            }
        }
        
        
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
        
        if (method.getActions() != null) {
            methodLine += "\n\n        " + method.getActions();
        }
        
        methodLine += "\n    }";
        
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
