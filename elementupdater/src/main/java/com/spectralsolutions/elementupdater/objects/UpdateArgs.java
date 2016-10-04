package com.spectralsolutions.elementupdater.objects;

/**
 * Created by Tius on 9/25/2016.
 */
public class UpdateArgs {
    public String LatestVersion;
    public String UrlUpdatePackage;
    public String PackageCheckSum;

    public UpdateArgs(String LatestVersion, String UrlUpdatePackage)
    {
        this.LatestVersion = LatestVersion;
        this.UrlUpdatePackage = UrlUpdatePackage;
    }

}
