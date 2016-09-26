package com.spectralsolutions.elementupdater.objects;

/**
 * Created by Tius on 9/26/2016.
 */
public class UpdateActionResult {
    public boolean Success;
    public String Message = "";

    public UpdateActionResult(boolean Success)
    {
        this.Success = Success;
    }

    public UpdateActionResult(boolean Success, String Message)
    {
        this.Success = Success;
        this.Message = Message;
    }
}
