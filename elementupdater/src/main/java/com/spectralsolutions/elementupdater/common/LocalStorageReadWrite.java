package com.spectralsolutions.elementupdater.common;

/**
 * Created by Tius on 9/29/2016.
 */
public class LocalStorageReadWrite implements ILocalStorageReadWrite {
    private ILocalStorage storage;
    @Override
    public void SetStorage(ILocalStorage storage) {
        this.storage = storage;
    }

    @Override
    public ILocalStorage GetStorage() {
        return this.storage;
    }
}
