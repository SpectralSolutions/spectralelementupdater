package com.spectralsolutions.elementupdater.objects;

import com.spectralsolutions.elementupdater.UpdaterBase;

/**
 * Created by Tius on 9/25/2016.
 */
public class UpdateArgs {
    public String LatestVersion;
    public String UrlUpdatePackage;
    public String PackageCheckSum;
    private UpdaterBase updater;

    public UpdateArgs(String LatestVersion, String UrlUpdatePackage)
    {
        this.LatestVersion = LatestVersion;
        this.UrlUpdatePackage = UrlUpdatePackage;
    }

    public void setUpdater(UpdaterBase updater)
    {
        this.updater = updater;
    }
    public void BeginUpdate()
    {
        updater.InstallUpdate(this);
    }

}
