package com.spectralsolutions.elementupdater;

import com.spectralsolutions.elementupdater.action.ActionExtractJar;
import com.spectralsolutions.elementupdater.storage.LocalStorageXML;

/**
 * Created by Tius on 9/29/2016.
 */
public class elementupdater {
    public static void main(String[] args)
    {
        UpdaterBase updater = new UpdateWithDropbox(new ActionExtractJar(),new LocalStorageXML());
        //Run update with
        // [*] update action to extract a jar to the current directory
        // [*] read and write version from an XML file stored in the current directory
        // [*] retrieve the update arguments using a Dropbox text file
        UpdateFactory uf = new UpdateFactory(updater);//Proxy wrapper for convenience and to hide unneeded methods
        uf.CheckUpdate();
    }
}
