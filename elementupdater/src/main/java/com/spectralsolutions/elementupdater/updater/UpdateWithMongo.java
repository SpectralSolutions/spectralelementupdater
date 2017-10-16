package com.spectralsolutions.elementupdater.updater;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.spectralsolutions.elementupdater.UpdaterBase;
import com.spectralsolutions.elementupdater.common.ILocalStorage;
import com.spectralsolutions.elementupdater.common.IUpdateAction;
import com.spectralsolutions.elementupdater.common.IUpdateEventsListener;
import com.spectralsolutions.elementupdater.objects.MongoConfig;
import com.spectralsolutions.elementupdater.objects.UpdateActionResult;
import com.spectralsolutions.elementupdater.objects.UpdateArgs;
import org.jongo.Jongo;

/**
 * Created by Tius on 9/25/2016.
 *
 * Description: This class reads information from a record in a MongoDB database
 *
 */
public class UpdateWithMongo extends UpdaterBase {
    private boolean UseDefaultProgressCallback = false;
    private UpdateArgs updateArgs;

    public UpdateWithMongo(IUpdateAction updateaction, ILocalStorage storage)
    {
        super(updateaction,storage);
    }

    public UpdateWithMongo(IUpdateAction updateaction, ILocalStorage storage, IUpdateEventsListener listener)
    {
        super(updateaction,storage,listener);
    }

    @Override
    public UpdateArgs GetUpdateArgs() {
        //
        if(updateArgs != null)
        {
            return updateArgs;
        }else
        {
            MongoConfig config = MongoConfig.PROD;
            MongoClient mongoClient = new MongoClient(config.getPrimaryMongoURI());
            DB database = mongoClient.getDB(config.getDatabase());
            Jongo jongo = new Jongo(database);
            //Pull the first version object
            UpdateArgs updateArgs = jongo.getCollection("updates").findOne("{}").as(UpdateArgs.class);
            this.updateArgs = updateArgs;
            return  updateArgs;
        }
    }

    /***
     * Compare local and remote versions ignoring the last digits of the version number
     * @param RemoteVersion
     * @param LocalVersion
     * @return
     */
    public boolean RunningLatestVersion(String RemoteVersion, String LocalVersion)
    {
        String latev = RemoteVersion.substring(0, RemoteVersion.lastIndexOf('.'));
        String localv = LocalVersion.substring(0,LocalVersion.lastIndexOf('.'));
        return localv.equals(latev);
    }

    /***
     * Description: Logic for comparing local version with server version to determine if update is needed
     * Triggers update event when an update is detected
     */
    @Override
    public void CheckUpdate() {
        //if update is detected trigger event
        UpdateArgs ua = this.GetUpdateArgs();
        String localversion = this.GetLocalVersion();
        System.out.println(String.format("Local version is: %s",localversion));
        //print
        if(localversion.isEmpty())
        {
            UpdateFailure("Could not retrieve the local version - Does version.xml exist?");
            return;
        }
        String remoteversion = ua.LatestVersion;
        if(!this.RunningLatestVersion(remoteversion,localversion))
        {
            ua.setUpdater(this);
            UpdateDetected(ua);
        }else{
            UpToDate(localversion);
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
