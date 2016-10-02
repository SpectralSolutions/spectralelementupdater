package com.spectralsolutions.elementupdater;

import com.spectralsolutions.elementupdater.InstallUtility;
import com.spectralsolutions.elementupdater.common.*;
import com.spectralsolutions.elementupdater.objects.UpdateActionResult;
import com.spectralsolutions.elementupdater.objects.UpdateArgs;
import com.spectralsolutions.elementupdater.UpdaterBase;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Tius on 9/25/2016.
 *
 * Description: This class reads information from a formatted dropbox text file
 * and is very quick to implement
 *
 */
public class UpdateWithDropbox extends UpdaterBase implements IProgressCallback{
    private static double previousProgress = 0;
    //Url to a text file formatted with the current action information
    private final String dropboxurl = "https://www.dropbox.com/s/onvsnt5jvubkucb/action.txt?dl=1";
    public UpdateWithDropbox(IUpdateAction updateaction, ILocalStorage storage)
    {
        super(updateaction,storage);
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
        if(localversion.isEmpty())
        {
            //failed to retrieve local version
            //trigger update failure passing error message
            this.UpdateFailure("Could not retrieve the local version value.");
            return;
            //exit
        }
        //simple non equality check
        if(!ua.ServerVersion.equals(localversion))
        {
            //trigger update detected event
            this.UpdateDetected(this.GetUpdateArgs());
            //Run update
            UpdateActionResult uar = this.updateaction.Run(this.GetUpdateArgs(),this.storage, this);
            if(uar.Success)
            {
                this.UpdateSuccess();
            }else
            {
                this.UpdateFailure(uar.Message);
            }
        }
    }

    @Override
    public void UpdateDetectedHandler(UpdateArgs args) {
        System.out.println(String.format("New update detected for version: %s\n",args.ServerVersion));
    }

    @Override
    public void UpdateSuccessHandler() {
        System.out.println("Update complete!");
    }

    @Override
    public void UpdateFailureHandler(String message) {
        System.out.println(String.format("Update failed: %s",message));
    }

}
