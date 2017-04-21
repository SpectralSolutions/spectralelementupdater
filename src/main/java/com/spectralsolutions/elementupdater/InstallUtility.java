package com.spectralsolutions.elementupdater;

import com.spectralsolutions.elementupdater.common.IProgressCallback;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
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

    /**
     * Description: Downloads the resource at a given url and saves it to a file on the local disk
     *
     * @param urladdress a string properly formatted url (http://)
     * @param destination a string full path to a destination on the local drive including the name and extension
     * @param callback the callback method to update download progress (implements IProgressCallback)
     * @return
     */
    public static boolean DownloadToFile(String urladdress, String destination, IProgressCallback callback)
    {
        boolean result = false;
        URL website = null;
        try {
            website = new URL(urladdress);
            String outputlocation = destination;
            try (CallbackByteChannel rbc = new CallbackByteChannel(Channels.newChannel(website.openStream()), InstallUtility.HttpContentLength(website), callback);
                 FileOutputStream fos = new FileOutputStream(outputlocation)) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                result = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                result = false;
            }
        } catch (Exception ex) {
        }
        if (website.equals(null)) {
            result = false;
        }
        return result;
    }

    /**
     * Description: Downloads the resource at a given url and saves it to a file on the local disk
     *
     * @param urladdress a string properly formatted url (http://)
     * @param destination a string full path to a destination on the local drive including the name and extension
     * @return success: boolean value whether the download completed without errors
     */
    public static boolean DownloadToFile(String urladdress, String destination)
    {
        boolean result = false;
        URL website = null;
        try {
            website = new URL(urladdress);
            String outputlocation = destination;
            try (ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                 FileOutputStream fos = new FileOutputStream(outputlocation)) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                result = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                result = false;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        if (website.equals(null)) {
            result = false;
        }
        return result;
    }

    /**
     * Description: Returns the size of a resource at a given URL by querying the header response
     *
     * @param url a string properly formatted url (http://)
     * @return int the value of the HTTP header ContentLength
     */
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

    /**
     * Description: Returns the resource at a URL as a String
     *
     * @param urladdress a string properly formatted url (http://)
     * @return String the text content of the web resource
     */
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

                java.io.File f = new java.io.File(installDirectory,file.getName());
                System.out.println(String.format("Extracting %s", f.getName()));//might be worth seperating logging logic like this
                if (file.isDirectory()) { // if its a directory, create it

                    if(!f.mkdirs())
                    {
                        if(!f.exists())
                        {
                            return false;
                        }
                    }

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

    /**
     * Description: Custom implementation of ReadableByteChannel to capture progress events in
     * a download stream
     * Created by Tius on 9/25/2016.
     */
    public static class CallbackByteChannel implements ReadableByteChannel{
        IProgressCallback delegate;
        long size;
        ReadableByteChannel rbc;
        long sizeRead;

        public CallbackByteChannel(ReadableByteChannel rbc, long expectedSize,
                            IProgressCallback delegate) {
            this.delegate = delegate;
            this.size = expectedSize;
            this.rbc = rbc;
        }

        public void close() throws IOException {
            rbc.close();
        }

        public long getReadSoFar() {
            return sizeRead;
        }

        public boolean isOpen() {
            return rbc.isOpen();
        }

        public int read(ByteBuffer bb) throws IOException {
            int n;
            double progress;
            if ((n = rbc.read(bb)) > 0) {
                sizeRead += n;
                progress = size > 0 ? (double) sizeRead / (double) size
                        * 100.0 : -1.0;
                delegate.callback(this, round(progress,0));
            }
            return n;
        }

        private static double round(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }
    }
}
