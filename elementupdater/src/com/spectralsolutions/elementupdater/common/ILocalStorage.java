package com.spectralsolutions.elementupdater.common;

/**
 * Created by Tius on 9/25/2016.
 */
public interface ILocalStorage {
    /**
     * @return The current local version
     */
    String ReadVersion();

    /**
     * @param value The version to be saved
     * @return
     */
    boolean WriteVersion(String value);
}
