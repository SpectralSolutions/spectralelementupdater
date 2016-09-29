package com.spectralsolutions.elementupdater.common;
import com.spectralsolutions.elementupdater.objects.*;
/**
 * Created by Tius on 9/25/2016.
 *
 * Implementation of this interface covers functionality for checking if the current
 * installed version is the latest and triggering an event if it isn't
 */
public interface IUpdateDetectable extends IUpdateDetectedListener, ILocalStorageReadWrite {

    /**
     * Description: Makes a request to the server to query the latest action
     * @return UpdateArgs
     */
    UpdateArgs GetUpdateArgs();

    /**
     * Description: Obtains the local installed version for comparison against the
     * server's latest version
     */
    String GetLocalVersion();

    /**
     * Description: Logic for comparing local version with server version to determine if update is needed
     * Triggers update event when an update is detected
     *
     */
    void CheckUpdate();
}
