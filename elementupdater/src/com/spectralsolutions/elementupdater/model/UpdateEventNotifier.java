package com.spectralsolutions.elementupdater.model;
import com.spectralsolutions.elementupdater.interfaces.*;
import com.spectralsolutions.elementupdater.objects.UpdateArgs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tius on 9/25/2016.
 */
public class UpdateEventNotifier {
    private List<IUpdateEventsListener> listeners = new ArrayList<IUpdateEventsListener>();

    public void addListener(IUpdateEventsListener listener) {
        listeners.add(listener);
    }

    public void UpdateDetected(UpdateArgs UpdateArgs) {
        // Notify everybody that may be interested.
        for (IUpdateEventsListener l : listeners)
            l.UpdateDetectedHandler(UpdateArgs);
    }

    public void UpdateSuccess() {
        // Notify everybody that may be interested.
        for (IUpdateEventsListener l : listeners)
            l.UpdateSuccessHandler();
    }

    public void UpdateFailure(String message) {
        // Notify everybody that may be interested.
        for (IUpdateEventsListener l : listeners)
            l.UpdateFailureHandler(message);
    }
}
