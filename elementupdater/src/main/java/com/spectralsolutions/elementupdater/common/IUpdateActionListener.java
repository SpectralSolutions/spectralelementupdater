package com.spectralsolutions.elementupdater.common;

/**
 * Created by Tius on 9/25/2016.
 *
 * Description:
 * A class that implements this interface can action to events triggered
 * when installing an action
 */
public interface IUpdateActionListener {
    /**
     * Description: Respond when the update is installed successfully
     */
    void UpdateSuccessHandler();
    /**
     * Description: Respond when the update isn't installed successfully.
     * @param message A simple message describing the error
     */
    void UpdateFailureHandler(String message);
}
