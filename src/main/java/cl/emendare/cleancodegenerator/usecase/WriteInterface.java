/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.FileWriterAdapter;
import cl.emendare.cleancodegenerator.domain.contract.WriteInterfaceInterface;
import cl.emendare.cleancodegenerator.domain.entity.Interface;
import cl.emendare.cleancodegenerator.domain.entity.Method;
import cl.emendare.cleancodegenerator.domain.entity.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class WriteInterface implements WriteInterfaceInterface {
    
    private FileWriterAdapter fileWriter;
    
    public WriteInterface(FileWriterAdapter fileWriter) {
        this.fileWriter = fileWriter;
    }

    @Override
    public boolean write(Interface i, String path, String author) {
        List<String> lines = new ArrayList<String>();;
        lines.add("package " + i.getPackage() + ";");
        lines.add("");
        lines.add("");
        lines.add("/**");
        lines.add("*");
        lines.add("* @author " + author);
        lines.add("*/");
        lines.add("public interface " + i.getName() + " {");
        lines.add("");
        for (Method method: i.getMethods()) {
            lines.add(formatMethod(method));
            
            if (method.getReturnType().equalsIgnoreCase("List") &&
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
        lines.add("");
        lines.add("}");        
        
        return fileWriter.writeToFile(lines, i.getName(), path);
        
    }
    
    private String formatMethod(Method method) {
        String methodLine = "    " +  method.getModifier() + " ";
        
        if (method.getReturnType().equalsIgnoreCase("List")) {
            methodLine += "List<";
            methodLine += method.getEntity().getName();
            methodLine += "> ";
        } else if (method.getReturnType() != null) {
            methodLine += method.getReturnType() + " ";
        } else {
            methodLine += "void ";
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
        methodLine += ");";
        
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
