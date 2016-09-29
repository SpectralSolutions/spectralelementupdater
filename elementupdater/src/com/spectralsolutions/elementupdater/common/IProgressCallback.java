package com.spectralsolutions.elementupdater.common;

import com.spectralsolutions.elementupdater.InstallUtility;

/**
 * Created by Tius on 9/25/2016.
 */
public interface IProgressCallback {
    /**
     * @param rbc Readable Byte Channel (Stream)
     * @param progress How many bytes read out of the total
     */
    void callback(InstallUtility.CallbackByteChannel rbc, double progress);
}
