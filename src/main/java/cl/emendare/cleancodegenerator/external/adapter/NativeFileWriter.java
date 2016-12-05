/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.external.adapter;

import cl.emendare.cleancodegenerator.domain.adapter.FileWriterAdapter;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class NativeFileWriter implements FileWriterAdapter {

    @Override
    public boolean writeToFile(List<String> lines, String name, String path) {
        try {
            File directory = new File(path);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Path file = Paths.get(path + "\\" + name + ".java");
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
}
