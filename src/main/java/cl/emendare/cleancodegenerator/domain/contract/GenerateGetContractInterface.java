/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.contract;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public interface GenerateGetContractInterface {
    String generate (
            String packg,
            String path,
            String name,
            String module,
            String author
    ) throws Exception;
}
