package com.spectralsolutions.elementupdater.common;

import com.spectralsolutions.elementupdater.objects.UpdateActionResult;
import com.spectralsolutions.elementupdater.objects.UpdateArgs;

/**
 * Created by Tius on 9/25/2016.
 */
public interface IUpdateAction {

    /**
     * @param args A collection of values necessary for the action operation
     * @param storage The storage mechanism for reading and writing local variables
     * @return UpdateActionResult - Did the action succeed and a message result
     */
    UpdateActionResult Run(UpdateArgs args, ILocalStorage storage);

    /**
     * Description: Set the local version variable
     */
    //Can read and write local variables to action to an action
    void SetLocalVersion(String version);
}
