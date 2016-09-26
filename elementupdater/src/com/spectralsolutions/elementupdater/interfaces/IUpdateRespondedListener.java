package com.spectralsolutions.elementupdater.interfaces;

/**
 * Created by Tius on 9/25/2016.
 *
 * Description:
 * A class that implements this interface can respond to events triggered
 * when installing an update
 */
public interface IUpdateRespondedListener {
    void UpdateSuccessHandler();
    void UpdateFailureHandler(String message);
}
