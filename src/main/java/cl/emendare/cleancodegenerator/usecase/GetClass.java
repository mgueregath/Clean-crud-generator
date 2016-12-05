/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.usecase;

import cl.emendare.cleancodegenerator.domain.adapter.FileReaderAdapter;
import cl.emendare.cleancodegenerator.domain.entity.Class;
import cl.emendare.cleancodegenerator.domain.contract.GetClassInterface;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class GetClass implements GetClassInterface {
    
    private FileReaderAdapter fileReader;
    
    public GetClass(FileReaderAdapter fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public Class getEntityClass(String name, String path, String module) {
        Class c = fileReader.readClassFromFile(name, path, module);
        
        return c;
    }
    
}
