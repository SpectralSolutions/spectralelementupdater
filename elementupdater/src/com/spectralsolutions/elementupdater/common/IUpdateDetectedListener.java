package com.spectralsolutions.elementupdater.common;

import com.spectralsolutions.elementupdater.objects.UpdateArgs;

/**
 * Created by Tius on 9/25/2016.
 */
public interface IUpdateDetectedListener {
    /**
     * Description: A method signature for handling the UpdateDetected event
     * @param args
     */
    void UpdateDetectedHandler(UpdateArgs args);
}
