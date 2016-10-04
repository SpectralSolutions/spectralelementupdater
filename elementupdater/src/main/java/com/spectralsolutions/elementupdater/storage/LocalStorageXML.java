package com.spectralsolutions.elementupdater.storage;

import com.spectralsolutions.elementupdater.common.ILocalStorage;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Tius on 9/26/2016.
 */
public class LocalStorageXML implements ILocalStorage {
    private String xmlfilelocation ="";

    public LocalStorageXML(String xmlfilelocation)
    {
        this.xmlfilelocation = xmlfilelocation;
    }
    public LocalStorageXML()
    {
        this.xmlfilelocation = "version.xml";//Current working directory;
    }

    @Override
    public String ReadVersion() {
        String version = "";
        try
        {
            File xmlfile = new File(this.xmlfilelocation);
            if(xmlfile.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlfile);
                //optional, but recommended
                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                doc.getDocumentElement().normalize();

                version = doc.getElementsByTagName("Version").item(0).getTextContent();
            }else
            {
                throw new FileNotFoundException(String.format("Cannot find xml file: %s",xmlfilelocation));
            }

        }catch(Exception ex){System.out.println(ex.getMessage());}
        return  version;
    }

    public String ReadVersion(String nodename) {
        String version = "";
        try
        {
            File xmlfile = new File(this.xmlfilelocation);
            if(xmlfile.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlfile);
                //optional, but recommended
                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                doc.getDocumentElement().normalize();

                version = doc.getElementsByTagName(nodename).item(0).getTextContent();
            }else
            {
                throw new FileNotFoundException(String.format("Cannot find xml file: %s",xmlfilelocation));
            }

        }catch(Exception ex){System.out.println(ex.getMessage());}
        return  version;
    }

    @Override
    public boolean WriteVersion(String value) {
        boolean success = false;
        try
        {
            File xmlfile = new File(this.xmlfilelocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlfile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            doc.getElementsByTagName("Version").item(0).setTextContent(value);
            //write new xml out
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlfile);
            t.transform(source, result);

            success = true;

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return success;
    }
    
    public boolean WriteVersion(String value, String nodename) {
        boolean success = false;
        try
        {
            File xmlfile = new File(this.xmlfilelocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlfile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            doc.getElementsByTagName("Version").item(0).setTextContent(value);
            success = true;

        }catch(Exception ex){}
        return success;
    }
}
