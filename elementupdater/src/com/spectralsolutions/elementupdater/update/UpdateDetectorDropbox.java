package com.spectralsolutions.elementupdater.update;

import com.spectralsolutions.elementupdater.InstallUtility;
import com.spectralsolutions.elementupdater.interfaces.ILocalStorage;
import com.spectralsolutions.elementupdater.interfaces.IUpdateDetectable;
import com.spectralsolutions.elementupdater.objects.UpdateArgs;

/**
 * Created by Tius on 9/25/2016.
 *
 * Description: This class reads update information from a formatted dropbox text file
 * and is very quick to implement
 *
 */
public abstract class UpdateDetectorDropbox implements IUpdateDetectable {
    //Url to a text file formatted with the current update information
    private final String dropboxurl = "https://www.dropbox.com/s/onvsnt5jvubkucb/update.txt?dl=1";
    public UpdateArgs GetUpdateArgs() {
        String serverresult = InstallUtility.DownloadToString(dropboxurl);
        String[] temp = serverresult.split("\\|");
        UpdateArgs args = null;
        args = new UpdateArgs(temp[0], temp[1]);
        return args;
    }
}
