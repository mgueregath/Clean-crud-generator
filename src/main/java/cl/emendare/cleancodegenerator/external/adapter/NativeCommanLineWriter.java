/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.external.adapter;

import cl.emendare.cleancodegenerator.domain.adapter.CommandLineWriterAdapter;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class NativeCommanLineWriter implements CommandLineWriterAdapter {

    @Override
    public void sendLine(String message) {
        System.out.println(message);
    }

    @Override
    public void send(String message) {
        System.out.print(message);
    }
    
}
