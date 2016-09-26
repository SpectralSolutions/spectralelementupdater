package com.spectralsolutions.elementupdater.interfaces;

import com.spectralsolutions.elementupdater.objects.UpdateActionResult;
import com.spectralsolutions.elementupdater.objects.UpdateArgs;

/**
 * Created by Tius on 9/25/2016.
 */
public interface IUpdateAction {

    /**
     * @param args A collection of values necessary for the update operation
     * @param storage The storage mechanism for reading and writing local variables
     * @return UpdateActionResult - Did the action succeed and a message result
     */
    UpdateActionResult Run(UpdateArgs args, ILocalStorage storage);
}
