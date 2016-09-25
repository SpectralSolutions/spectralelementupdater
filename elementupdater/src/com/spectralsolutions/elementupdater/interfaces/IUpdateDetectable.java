package com.spectralsolutions.elementupdater.interfaces;
import com.spectralsolutions.elementupdater.objects.*;
/**
 * Created by Tius on 9/25/2016.
 */
public interface IUpdateDetectable extends IUpdateDetectedListener {
    //Can detect server update
    //CheckUpdate()
    //GetUpdateArgs()
    UpdateArgs GetUpdateArgs();
    void GetLocalVersion(ILocalStorage storage);
}
