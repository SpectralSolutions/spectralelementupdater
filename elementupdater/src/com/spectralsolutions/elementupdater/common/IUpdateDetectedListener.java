package com.spectralsolutions.elementupdater.common;

import com.spectralsolutions.elementupdater.objects.UpdateArgs;

/**
 * Created by Tius on 9/25/2016.
 */
public interface IUpdateDetectedListener {
    /**
     * Description: Respond when an update is successfully detected
     * @param args provides further information on the detected update arguments
     */
    void UpdateDetectedHandler(UpdateArgs args);
}
