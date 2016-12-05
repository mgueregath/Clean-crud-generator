/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.adapter;

import cl.emendare.cleancodegenerator.domain.entity.Class;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public interface FileReaderAdapter {
    Class readClassFromFile(String name, String path, String module);
}
