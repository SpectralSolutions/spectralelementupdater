# elementupdater
Modular update system for spectral solutions

##Basic Usage

```java
import com.spectralsolutions.elementupdater;

UpdaterBase updater = new UpdateWithDropbox(new ActionExtractJar(),new LocalStorageXML());
//Run update
// [*] retrieve the update arguments using a Dropbox text file [UpdateBase]
// [*] update action to extract a jar to the current directory [IUpdateAction]
// [*] read and write version from an XML file stored in the current directory [ILocalStorage]
UpdateFactory uf = new UpdateFactory(updater);
uf.CheckUpdate();
```

##Respond to update events
Custom code can be written to respond to triggered update events - making it simple to pair a GUI with the updater. Extend the IUpdateEventsListener interface and pass it as an argument to the ActionExtractJar constructor.

```java
import com.spectralsolutions.elementupdater;

UpdaterBase updater = new UpdateWithDropbox(new ActionExtractJar(new UpdateEventsResponder()),new LocalStorageXML());
UpdateFactory uf = new UpdateFactory(updater);
uf.CheckUpdate();
```

Where "UpdateEventsResponder" is a simple class that responds to the events by printing some text to the console. 
```java
import com.spectralsolutions.elementupdater;
import com.spectralsolutions.elementupdater.common;

//Swap out the calls to "println" with label.setText() as a quick and dirty GUI
public class UpdateEventsResponder implements IUpdateEventsListener {
    
    @Override
    public void UpdateSuccessHandler() {
        System.out.println("Update successfully installed");
    }
    
    @Override
    public void UpdateFailureHandler(String message) {
        System.out.println(String.format("Ran into some problems: %s", message));
    }

    @Override
    public void UpdateDetectedHandler(UpdateArgs args) {
        System.out.println(String.format("Please wait... Updating to latest version: v %s", args.LatestVersion));
    }
}
```

## Maven Installation
To use elementupdater add the following repository and dependency to your pom.xml file
```xml
<!-- add this within your project's "dependencies" node -->
<dependency>
    <groupId>com.spectralsolutions</groupId>
    <artifactId>elementupdater</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>


<repositories>
<!-- of course if a "repositories" node already exists just add the following repository -->
    <repository>
        <id>elementupdater-mvn-repo</id>
        <url>https://raw.github.com/spectralsolutions/elementupdater/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```
