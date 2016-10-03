package com.spectralsolutions.elementupdater;

import com.spectralsolutions.elementupdater.common.*;
import com.spectralsolutions.elementupdater.objects.UpdateArgs;

/**
 * Created by Tius on 9/29/2016.
 */
public abstract class UpdaterBase extends UpdateEventNotifier implements IUpdateDetectable,IUpdateActionListener,IUpdateEventsListener,IProgressCallback {
    protected ILocalStorage storage;
    protected final IUpdateAction updateaction;
    private double previousProgress;

    public UpdaterBase(IUpdateAction updateaction, ILocalStorage storage)
    {
        this.updateaction = updateaction;
        this.SetStorage(storage);
        this.AddListener(this);
    }
    public UpdaterBase(IUpdateAction updateaction, ILocalStorage storage, IUpdateEventsListener listener)
    {
        this.updateaction = updateaction;
        this.SetStorage(storage);
        this.AddListener(listener);
    }

    @Override
    public void SetStorage(ILocalStorage storage) {
        this.storage = storage;
    }

    @Override
    public ILocalStorage GetStorage() {
        return this.storage;
    }

    /**
     * Description: Makes a request to the server to query the latest action
     *
     * @return UpdateArgs
     */
    @Override
    public UpdateArgs GetUpdateArgs() {
        //server request
        //return args from server response
        return new UpdateArgs("1.0.0.1","http://download.com");
    }

    /**
     * Description: Obtains the local installed version for comparison against the
     * server's latest version
     */
    @Override
    public String GetLocalVersion() {
        return this.storage.ReadVersion();
    }

    /**
     * Description: Logic for comparing local version with server version to determine if update is needed
     * Triggers update event when an update is detected
     */
    @Override
    public void CheckUpdate() {
        //if update is detected trigger event
        UpdateArgs ua = this.GetUpdateArgs();
        String localversion = this.GetLocalVersion();
        //simple non equality check
        if(!ua.LatestVersion.equals(localversion))
        {
            //trigger update detected event
            this.UpdateDetected(this.GetUpdateArgs());
        }
    }

    /**
     * Description: A method signature for handling the UpdateDetected event
     *
     * @param args
     */
    @Override
    public void UpdateDetectedHandler(UpdateArgs args) {
        System.out.println("A new update is available. Installing now");
        this.updateaction.Run(this.GetUpdateArgs(),this.storage);
    }

    /**
     * Respond to download events to print its progress to the console
     * Print out download progress
     * @param rbc      Readable Byte Channel (Stream)
     * @param progress How many bytes read out of the total (rounded to 0dp)
     */
    @Override
    public void callback(InstallUtility.CallbackByteChannel rbc, double progress) {

        double currentProgress = progress;
        if(previousProgress != currentProgress) {
            System.out.println(String.format("Download progress: %.0f %%", progress));
            previousProgress = progress;
        }
    }

}
