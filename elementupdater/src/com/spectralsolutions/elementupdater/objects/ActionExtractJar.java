package com.spectralsolutions.elementupdater.objects;

import com.spectralsolutions.elementupdater.InstallUtility;
import com.spectralsolutions.elementupdater.interfaces.ILocalStorage;
import com.spectralsolutions.elementupdater.interfaces.IUpdateAction;
import com.spectralsolutions.elementupdater.interfaces.IUpdateRespondable;

import java.io.File;

/**
 * Created by Tius on 9/25/2016.
 */
public class ActionExtractJar implements IUpdateAction {

    String InstallerLocation = "";
    public ActionExtractJar(String installerlocation)
    {
        this.InstallerLocation = installerlocation;
    }

    public ActionExtractJar()
    {
        this.InstallerLocation = ".";//Current working directory
    }

    //Might be worth returning a result object in case errors are encountered
    @Override
    public UpdateActionResult Run(UpdateArgs args, ILocalStorage storage) {
        if(args != null)
        {
            String newversion = args.ServerVersion;
            String updatepackageurl = args.UrlUpdatePackage;
            String[] errors = new String[]{"Install path is broken or missing", "Update package failed to download", "Failed to extract update package", "Had a problem updating version subkey"};
            String errormessage = "";

            String updatepackage = InstallerLocation + File.separator + "updatepackage.jar";
            if (InstallUtility.DownloadToFile(updatepackageurl,updatepackage)) {
                //Unpack downloaded package to installer location
                if (InstallUtility.ExtractJarFile(updatepackage, InstallerLocation)) {
                    //Update value of local installed version
                    if (storage.WriteVersion(newversion)) {
                        String message = String.format("Package: %s\nInstalled to: %s", updatepackage, InstallerLocation);
                        new File(updatepackage).deleteOnExit();
                        return new UpdateActionResult(true, message);
                    }else {
                        errormessage = errors[3];
                    }
                }else {
                    errormessage = errors[2];
                }
            }else {
                errormessage = errors[1];
            }
            return new UpdateActionResult(false, errormessage);
        } else{
            return new UpdateActionResult(false, "Error");
        }
    }
}
