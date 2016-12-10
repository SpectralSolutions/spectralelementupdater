package com.spectralsolutions.elementupdater.objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spectralsolutions.elementupdater.UpdaterBase;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Created by Tius on 9/25/2016.
 */
public class UpdateArgs {
    @MongoObjectId
    public String _id;
    public String LatestVersion;
    public String UrlUpdatePackage;
    public String PackageCheckSum;
    private UpdaterBase updater;

    @JsonCreator
    public UpdateArgs(@JsonProperty("LatestVersion") String LatestVersion, @JsonProperty("UrlUpdatePackage") String UrlUpdatePackage)
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
