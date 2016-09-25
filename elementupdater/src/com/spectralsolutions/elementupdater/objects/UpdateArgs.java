package com.spectralsolutions.elementupdater.objects;

/**
 * Created by Tius on 9/25/2016.
 */
public class UpdateArgs {
    public String ServerVersion;
    public String UrlUpdatePackage;
    public String PackageCheckSum;

    public UpdateArgs(String ServerVersion, String UrlUpdatePackage)
    {
        this.ServerVersion = ServerVersion;
        this.UrlUpdatePackage = UrlUpdatePackage;
    }

}
