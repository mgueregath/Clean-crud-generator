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
public class WriteJpaClass implements WriteClassInterface {
    private FileWriterAdapter fileWriter;
    
    public WriteJpaClass(FileWriterAdapter fileWriter) {
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
        
        lines.add("");
        lines.add("/**");
        lines.add("*");
        lines.add("* @author " + author);
        lines.add("*/");
        lines.add("@Entity");
        lines.add("@Table(name = \"" + formatTableName(c.getName().replace("Jpa", "")) + "\")");
        lines.add(formatHeader(c.getName(), c.getInheritance().getName(), c.getInterfaces()));
        
        for (ClassProperty property: c.getProperties()) {
            lines.add("");
            if (property.getName().equalsIgnoreCase("id")) {
                lines.add("    @Id");
            }
            lines.add("    @Column(name=\"" + formatColumnName(c.getName(), property.getName()) + "\")");
            if (property.getName().equalsIgnoreCase("id")) {
                lines.add("    @GeneratedValue(strategy=GenerationType.TABLE)");
            }
            lines.add("    " + formatClassProperty(property));
        }
        
        for (Method method: c.getMethods()) {
            lines.add(formatMethod(method));
        }
        
        lines.add("");
        lines.add("}");        
        
        System.out.println(path);
        
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
    
    private String formatClassProperty(ClassProperty property) {
        String propertyLine = "";
        
        propertyLine += "private";//property.getModifier();
        propertyLine += " ";
        propertyLine += property.getType();
        propertyLine += " ";
        propertyLine += property.getName();        
        propertyLine += ";";
        
        return propertyLine;
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
    
    private String formatTableName(String className) {
        return replaceUpperByUnderscore(className).toLowerCase();
    }
    
    private String formatColumnName(String className, String propertyName) {
        return className.substring(0, 3).toLowerCase() + "_" + replaceUpperByUnderscore(propertyName);
    }
    
    private String replaceUpperByUnderscore(String string){
        StringBuffer formatted = new StringBuffer();
        for (int i = 0; i < string.length(); i++)
        {
            if (Character.isUpperCase(string.charAt(i))) {
                formatted.append("_" + Character.toLowerCase(string.charAt(i)));
            } else {
              formatted.append(string.charAt(i));
            }
        }
        
        return formatted.toString();
    }
}
