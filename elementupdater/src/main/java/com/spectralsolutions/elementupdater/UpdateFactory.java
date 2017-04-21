package com.spectralsolutions.elementupdater;

import com.spectralsolutions.elementupdater.common.IProgressCallback;
import com.spectralsolutions.elementupdater.common.IUpdateEventsListener;

/**
 * Created by Tius on 9/29/2016.
 */
public class UpdateFactory {
    private final UpdaterBase updater;
    private boolean usedefaultprogresscallback;
    public UpdateFactory(UpdaterBase updater)
    {
        this.updater = updater;
    }

    public void SetDefaultProgressCallback(boolean enable)
    {
        this.usedefaultprogresscallback = enable;
    }

    public void CheckUpdate()
    {
        if(this.usedefaultprogresscallback)
        {
            this.updater.CheckUpdate(true);

        }else {
            this.updater.CheckUpdate();
        }
    }

    public void AddEventsListener(IUpdateEventsListener callback)
    {
        this.updater.AddListener(callback);
    }
}
