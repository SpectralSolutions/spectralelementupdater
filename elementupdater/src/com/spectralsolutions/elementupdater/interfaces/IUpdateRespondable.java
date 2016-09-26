package com.spectralsolutions.elementupdater.interfaces;

/**
 * Created by Tius on 9/25/2016.
 *
 * Description:
 * An interface that when implemented covers functionality for installing an update
 * in response to a new update being detected
 */
public interface IUpdateRespondable extends IUpdateRespondedListener {
    /**
     * Description: Set the local version variable
     * @param storage The storage mechanism for local install variables
     */
    //Can read and write local variables to respond to an update
    void SetLocalVersion(ILocalStorage storage);

    /**
     * Description: Run the provided "action" to install the update
     * @param update A class that implements IUpdateAction to run the update logic
     */
    void StartUpdate(IUpdateAction update);

}
