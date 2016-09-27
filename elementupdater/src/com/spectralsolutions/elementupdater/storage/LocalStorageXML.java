package com.spectralsolutions.elementupdater.storage;

import com.spectralsolutions.elementupdater.interfaces.ILocalStorage;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

/**
 * Created by Tius on 9/26/2016.
 */
public class LocalStorageXML implements ILocalStorage {
    private String xmlfilelocation ="";

    public LocalStorageXML(String xmlfilelocation)
    {
        this.xmlfilelocation = xmlfilelocation;
    }

    @Override
    public String ReadVersion() {
        String version = "";
        try
        {
            File xmlfile = new File(this.xmlfilelocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlfile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            version =  doc.getElementsByTagName("Version").item(0).getTextContent();

        }catch(Exception ex){}
        return  version;
    }

    public String ReadVersion(String nodename) {
        String version = "";
        try
        {
            File xmlfile = new File(this.xmlfilelocation);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlfile);
            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            version =  doc.getElementsByTagName(nodename).item(0).getTextContent();

        }catch(Exception ex){}
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

        }catch(Exception ex){}
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

        }catch(Exception ex){}
        return success;
    }
}
