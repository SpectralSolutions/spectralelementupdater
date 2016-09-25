package com.spectralsolutions.elementupdater;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Tius on 9/25/2016.
 */
public class InstallUtility {

    public static void DownloadToFile()
    {

    }

    public static String DownloadToString(String urladdress)
    {
        String content = "";

        try (InputStream is = (new URL(urladdress)).openStream()) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            content = s.hasNext() ? s.next() : "";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return content;
    }

    public static void ExtractJarFile()
    {

    }

}
