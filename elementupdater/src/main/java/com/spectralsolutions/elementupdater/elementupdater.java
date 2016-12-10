package com.spectralsolutions.elementupdater;

import com.spectralsolutions.elementupdater.action.ActionExtractJar;
import com.spectralsolutions.elementupdater.common.IUpdateEventsListener;
import com.spectralsolutions.elementupdater.objects.UpdateArgs;
import com.spectralsolutions.elementupdater.storage.LocalStorageXML;
import com.spectralsolutions.elementupdater.updater.UpdateWithDropbox;

/**
 * Created by Tius on 9/29/2016.
 */
public class elementupdater  {
    private static updaterTest test = new updaterTest();
    public static void main(String[] args)
    {
        UpdaterBase updater = new UpdateWithDropbox(new ActionExtractJar(),new LocalStorageXML(),test);
        //Run update with
        // [*] update action to extract a jar to the current directory
        // [*] read and write version from an XML file stored in the current directory
        // [*] retrieve the update arguments using a Dropbox text file
        UpdateFactory uf = new UpdateFactory(updater);//Proxy wrapper for convenience and to hide unneeded methods
        uf.SetDefaultProgressCallback(true);
        uf.CheckUpdate();
    }

    public static class updaterTest implements IUpdateEventsListener{

        @Override
        public void UpdateSuccessHandler(String installedVersion)  {
            System.out.println("Sick.");
        }

        @Override
        public void UpdateFailureHandler(String message) {
            System.out.println("Shit. " + message   );
        }

        @Override
        public void UpdateDetectedHandler(UpdateArgs args) {
            try{
                System.out.println("Would you like to install ze update?");
                System.in.read();
                args.BeginUpdate();

            }catch (Exception ex)
            {

            }
        }

        @Override
        public void UpdateUpToDateHandler(String currentVersion) {
            System.out.println("We are up to date!");
        }
    }
}
