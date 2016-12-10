package com.spectralsolutions.elementupdater.updater;

import com.spectralsolutions.elementupdater.InstallUtility;
import com.spectralsolutions.elementupdater.UpdaterBase;
import com.spectralsolutions.elementupdater.common.*;
import com.spectralsolutions.elementupdater.objects.UpdateActionResult;
import com.spectralsolutions.elementupdater.objects.UpdateArgs;

/**
 * Created by Tius on 9/25/2016.
 *
 * Description: This class reads information from a formatted dropbox text file
 * and is very quick to implement
 *
 */
public class UpdateWithDropbox extends UpdaterBase {
    //Url to a text file formatted with the current action information
    private final String dropboxurl = "https://www.dropbox.com/s/onvsnt5jvubkucb/update.txt?dl=1";
    private boolean UseDefaultProgressCallback = false;

    public UpdateWithDropbox(IUpdateAction updateaction, ILocalStorage storage)
    {
        super(updateaction,storage);
    }

    public UpdateWithDropbox(IUpdateAction updateaction, ILocalStorage storage, IUpdateEventsListener listener)
    {
        super(updateaction,storage,listener);
    }

    @Override
    public UpdateArgs GetUpdateArgs() {
        String serverresult = InstallUtility.DownloadToString(dropboxurl);
        String[] temp = serverresult.split("\\|");//the string is '|' delimited
        UpdateArgs args = null;
        args = new UpdateArgs(temp[0], temp[1]);
        return args;
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
        System.out.println(String.format("Local version is: %s",localversion));
        if(localversion.isEmpty())
        {
            //failed to retrieve local version
            //trigger update failure passing error message
            this.UpdateFailure("Could not retrieve the local version value.");
            return;
            //exit
        }
        //simple non equality check
        if(!ua.LatestVersion.equals(localversion))
        {
            //trigger update detected event
            ua.setUpdater(this);
            this.UpdateDetected(ua);
        }else
        {
            //System.out.println(String.format("We are running the latest version: %s", localversion));
            this.UpToDate(localversion);
        }
    }

    /**
     * Description: Logic for comparing local version with server version to determine if update is needed
     * Triggers update event when an update is detected
     */
    public void CheckUpdate(boolean UseDefaultProgressCallback) {
        this.UseDefaultProgressCallback = UseDefaultProgressCallback;
       CheckUpdate();
    }

    @Override
    public void UpdateDetectedHandler(UpdateArgs args) {
        System.out.println(String.format("New update detected for version: %s",args.LatestVersion));
    }

    @Override
    public void InstallUpdate(UpdateArgs ua) {
        //Run update
        UpdateActionResult uar;
        if(!this.UseDefaultProgressCallback) {
            uar = this.updateaction.Run(ua, this.storage);
        }else
        {
            uar = this.updateaction.Run(ua, this.storage, this);
        }
        if(uar.Success)
        {
            this.UpdateSuccess(ua.LatestVersion);
        }else
        {
            this.UpdateFailure(uar.Message);
        }
    }

    @Override
    public void UpdateSuccessHandler(String installedVersion) {
        System.out.println("Update complete!");
    }

    @Override
    public void UpdateFailureHandler(String message) {
        System.out.println(String.format("Update failed: %s",message));
    }

    @Override
    public void UpdateUpToDateHandler(String currentVersion) {
        System.out.println(String.format("We are running the latest version: %s",currentVersion));
    }
}
