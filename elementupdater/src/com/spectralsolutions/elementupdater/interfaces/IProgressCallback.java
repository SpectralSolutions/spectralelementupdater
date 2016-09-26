package com.spectralsolutions.elementupdater.interfaces;

import com.spectralsolutions.elementupdater.model.CallbackByteChannel;

/**
 * Created by Tius on 9/25/2016.
 */
public interface IProgressCallback {
    /**
     * @param rbc Readable Byte Channel (Stream)
     * @param progress How many bytes read out of the total
     */
    void callback(CallbackByteChannel rbc, double progress);
}
