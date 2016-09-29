package com.spectralsolutions.elementupdater.common;

/**
 * Created by Tius on 9/25/2016.
 *
 * Description:
 * A class that implements this interface can action to events triggered
 * when installing an action
 */
public interface IUpdateActionListener {
    void UpdateSuccessHandler();
    void UpdateFailureHandler(String message);
}
