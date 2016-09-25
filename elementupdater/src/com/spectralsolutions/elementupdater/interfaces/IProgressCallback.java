package com.spectralsolutions.elementupdater.interfaces;

import com.spectralsolutions.elementupdater.model.CallbackByteChannel;

/**
 * Created by Tius on 9/25/2016.
 */
public interface IProgressCallback {
    void callback(CallbackByteChannel rbc, double progress);
}
