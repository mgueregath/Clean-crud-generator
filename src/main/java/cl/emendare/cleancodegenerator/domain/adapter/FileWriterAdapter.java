/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.adapter;

import java.util.List;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public interface FileWriterAdapter {
    public boolean writeToFile(List<String> lines, String name, String path);
}
