package com.spectralsolutions.elementupdater.interfaces;

import com.spectralsolutions.elementupdater.objects.UpdateAction;

/**
 * Created by Tius on 9/25/2016.
 */
public interface IUpdateRespondable extends IUpdateRespondedListener {
    //Can read and write local variables to respond to an update
    void SetLocalVersion(ILocalStorage storage);
    void StartUpdate(UpdateAction update);

}
