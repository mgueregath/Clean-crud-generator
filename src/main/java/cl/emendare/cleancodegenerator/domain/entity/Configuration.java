/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.emendare.cleancodegenerator.domain.entity;

/**
 *
 * @author Mirko Gueregat <mgueregath@emendare.cl>
 */
public class Configuration {
    protected String domainPath;
    protected String language;
    protected String persistencePath;
    protected String useCasePath;
    protected String module;
    protected String packg;
    protected String author;

    /**
     * @return the domainPath
     */
    public String getDomainPath() {
        return domainPath;
    }

    /**
     * @param domainPath the domainPath to set
     */
    public void setDomainPath(String domainPath) {
        this.domainPath = domainPath;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the persistencePath
     */
    public String getPersistencePath() {
        return persistencePath;
    }

    /**
     * @param persistencePath the persistencePath to set
     */
    public void setPersistencePath(String persistencePath) {
        this.persistencePath = persistencePath;
    }

    /**
     * @return the useCasePath
     */
    public String getUseCasePath() {
        return useCasePath;
    }

    /**
     * @param useCasePath the useCasePath to set
     */
    public void setUseCasePath(String useCasePath) {
        this.useCasePath = useCasePath;
    }

    /**
     * @return the module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module the module to set
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * @return the packg
     */
    public String getPackage() {
        return packg;
    }

    /**
     * @param packg the packg to set
     */
    public void setPackage(String packg) {
        this.packg = packg;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }
}
