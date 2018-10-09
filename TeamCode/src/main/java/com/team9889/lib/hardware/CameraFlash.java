package com.team9889.lib.hardware;

import android.hardware.Camera;

import com.qualcomm.robotcore.hardware.Light;

import static android.hardware.Camera.Parameters.FLASH_MODE_OFF;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;

/**
 * Created by joshua9889 on 11/15/2017.
 */

public class CameraFlash implements Light {

    private Camera mCamera = null;
    private Camera.Parameters parm = null;
    private boolean isLightOn = false;

    public CameraFlash(){}

    public void on(){
        if(!isLightOn()) {
            if(mCamera == null)
                open();

            parm = mCamera.getParameters();
            parm.setFlashMode(FLASH_MODE_TORCH);
            mCamera.setParameters(parm);
            isLightOn = true;
        }
    }

    public void off(){
        if(isLightOn()){
            parm.setFlashMode(FLASH_MODE_OFF);
            mCamera.setParameters(parm);
            isLightOn = false;
        }
    }

    public void open(){
        mCamera = Camera.open();
    }

    public void release(){
        off();
        mCamera.release();
    }

    @Override
    public boolean isLightOn() {
        return isLightOn;
    }
}
