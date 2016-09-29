package com.spectralsolutions.elementupdater.storage;

import com.spectralsolutions.elementupdater.common.ILocalStorage;

/**
 * Created by Tius on 9/26/2016.
 */
public class LocalStorageWinRegistry implements ILocalStorage {
    @Override
    public String ReadVersion() {
        return null;
    }

    @Override
    public boolean WriteVersion(String value) {
        return false;
    }
}
