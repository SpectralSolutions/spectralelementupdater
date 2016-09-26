package com.spectralsolutions.elementupdater.interfaces;
import com.spectralsolutions.elementupdater.objects.*;
/**
 * Created by Tius on 9/25/2016.
 *
 * Implementation of this interface covers functionality for checking if the current
 * installed version is the latest and triggering an event if it isn't
 */
public interface IUpdateDetectable extends IUpdateDetectedListener {

    /**
     * Description: Makes a request to the server to query the latest update
     * @return UpdateArgs
     */
    UpdateArgs GetUpdateArgs();

    /**
     * Description: Obtains the local installed version for comparison against the
     * server's latest version
     * @param storage The method of retrieving the local variable
     */
    void GetLocalVersion(ILocalStorage storage);
}
