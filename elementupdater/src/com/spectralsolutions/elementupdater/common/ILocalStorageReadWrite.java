package com.spectralsolutions.elementupdater.common;

/**
 * Created by Tius on 9/29/2016.
 */
public interface ILocalStorageReadWrite {
    /*
     * Setter method for storage method
     * @param storage The method of retrieving the local variable
     */
    void SetStorage(ILocalStorage storage);
    /*
     * Getter method for storage object
     * returns object containing logic for setting and retrieving the local variable
     */
    ILocalStorage GetStorage();
}
