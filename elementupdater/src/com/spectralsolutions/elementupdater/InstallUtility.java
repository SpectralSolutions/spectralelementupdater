package com.spectralsolutions.elementupdater;

import com.spectralsolutions.elementupdater.interfaces.IProgressCallback;
import com.spectralsolutions.elementupdater.model.CallbackByteChannel;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by Tius on 9/25/2016.
 *
 * Description:
 * A helper class that contains functionality common to update operations
 *
 */
public class InstallUtility {

    //Overload to accept a callback for showing progress to the user
    public static boolean DownloadToFile(String urladdress, String destination, IProgressCallback callback)
    {
        boolean result = false;
        //assert urladdress = "https://www.dropbox.com/s/w8y8u4vadc1wgcm/elementupdate.jar?dl=1"
        if(!urladdress.equals("https://www.dropbox.com/s/w8y8u4vadc1wgcm/elementupdate.jar?dl=1"))
        {
            return false;
        }
        URL website = null;
        try {
            website = new URL(urladdress);
            String outputlocation = destination;
            try (CallbackByteChannel rbc = new CallbackByteChannel(Channels.newChannel(website.openStream()), InstallUtility.HttpContentLength(website), callback);
                 FileOutputStream fos = new FileOutputStream(outputlocation)) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                result = true;
            } catch (Exception ex) {
                result = false;
            }
        } catch (Exception ex) {
        }
        if (website.equals(null)) {
            result = false;
        }
        return result;
    }

    public static boolean DownloadToFile(String urladdress, String destination)
    {
        boolean result = false;
        //assert urladdress = "https://www.dropbox.com/s/w8y8u4vadc1wgcm/elementupdate.jar?dl=1"
        if(!urladdress.equals("https://www.dropbox.com/s/w8y8u4vadc1wgcm/elementupdate.jar?dl=1"))
        {
            return false;
        }
        URL website = null;
        try {
            website = new URL(urladdress);
            String outputlocation = destination;
            try (ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                 FileOutputStream fos = new FileOutputStream(outputlocation)) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                result = true;
            } catch (Exception ex) {
                result = false;
            }
        } catch (Exception ex) {
        }
        if (website.equals(null)) {
            result = false;
        }
        return result;
    }

    private static int HttpContentLength(URL url)
    {
        HttpURLConnection connection;
        int contentLength = -1;
        try {
            connection = (HttpURLConnection) url.openConnection();
            contentLength = connection.getContentLength();
        } catch (Exception e) {
        }
        return contentLength;
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

    public static boolean ExtractJarFile(String jarArchive, String installDirectory)
    {
        boolean result = true;
        java.util.jar.JarFile jar = null;
        try {
            jar = new java.util.jar.JarFile(jarArchive);
            java.util.Enumeration enumEntries = jar.entries();
            while (enumEntries.hasMoreElements()) {
                java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();
                java.io.File f = new java.io.File(installDirectory + java.io.File.separator + file.getName());
                System.out.println(String.format("Extracting %s", f.getName()));
                if (file.isDirectory()) { // if its a directory, create it
                    f.getParentFile().mkdirs();
                    continue;
                }
                try (java.io.InputStream is = jar.getInputStream(file); java.io.FileOutputStream fos = new FileOutputStream(f, false)) {
                    byte[] buf = new byte[4096];
                    int r;
                    while ((r = is.read(buf)) != -1) {  // write contents of 'is' to 'fos'
                        fos.write(buf, 0, r);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    result = false;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            result = false;
        }
        return result;
    }

}
