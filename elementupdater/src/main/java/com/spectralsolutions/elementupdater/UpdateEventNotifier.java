package com.spectralsolutions.elementupdater;
import com.spectralsolutions.elementupdater.common.*;
import com.spectralsolutions.elementupdater.objects.UpdateArgs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tius on 9/25/2016.
 *
 * An abstract class to enable the ability for an implmeneting class to register listeners
 * and forward event triggers to them
 */
public abstract class UpdateEventNotifier {
    private List<IUpdateEventsListener> listeners = new ArrayList<IUpdateEventsListener>();

    public void AddListener(IUpdateEventsListener listener) {
        listeners.add(listener);
    }

    protected void UpdateDetected(UpdateArgs UpdateArgs) {
        // Notify everybody that may be interested.
        for (IUpdateEventsListener l : listeners)
            l.UpdateDetectedHandler(UpdateArgs);
    }

    protected void UpdateSuccess() {
        // Notify everybody that may be interested.
        for (IUpdateEventsListener l : listeners)
            l.UpdateSuccessHandler();
    }

    protected void UpdateFailure(String message) {
        // Notify everybody that may be interested.
        for (IUpdateEventsListener l : listeners)
            l.UpdateFailureHandler(message);
    }

    protected void UpToDate(String currentVersion)
    {
        for(IUpdateEventsListener l : listeners)
            l.UpdateUpToDateHandler(currentVersion);
    }
}
