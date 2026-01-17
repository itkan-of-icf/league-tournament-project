package org.firstinspires.ftc.teamcode.sorting;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
//BRAHIM COLOR SENSOR STUFF
import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ColorSensorObject{
    private NormalizedColorSensor sensor1, sensor2;

    float hsvValues1[] = {0F, 0F, 0F};
    final float values1[] = hsvValues1;

    float hsvValues2[] = {0F, 0F, 0F};
    final float values2[] = hsvValues2;



    public ColorSensorObject(String hardware1, String hardware2){


        sensor1 = hardwareMap.get(NormalizedColorSensor.class, hardware1);
        sensor2 = hardwareMap.get(NormalizedColorSensor.class, hardware2);

        sensor1.setGain(11);
        sensor2.setGain(11);
    }


    public char getColor(){

        NormalizedRGBA colors1 = sensor1.getNormalizedColors();
        NormalizedRGBA colors2 = sensor2.getNormalizedColors();

        //I dont know what this does I copy pasted from example code
        Color.colorToHSV(colors1.toColor(), hsvValues1);
        Color.colorToHSV(colors2.toColor(), hsvValues2);

        //THESE TOLERANCES MOST LIKELY NEED TO BE TUNED, I TESTED WITH A SENSOR THAT WASN'T IN THE CHAMBER
        //Conditions in the chamber are different, lighting and stuff impacts it a lot.
        if((colors1.blue >= 0.7 && colors1.red >= 0.35) || (colors2.blue >= 0.7 && colors2.red >= 0.35) ){
            return 'G';
        }else if(colors1.green > 0.35 || colors2.green > 0.35){
            return 'P';
        }else{
            return 'N';
        }
    }

    public float[] sensor1_RGB(){
        NormalizedRGBA colors1 = sensor1.getNormalizedColors();
        Color.colorToHSV(colors1.toColor(), hsvValues1);

        float[] rgb = new float[3];
        float red = colors1.red;
        rgb[0] = colors1.red;
        rgb[1] = colors1.blue;
        rgb[2] = colors1.green;

        return rgb;
    }

    public float[] sensor2_RGB(){
        NormalizedRGBA colors2 = sensor2.getNormalizedColors();
        Color.colorToHSV(colors2.toColor(), hsvValues2);

        float[] rgb = new float[3];
        float red = colors2.red;
        rgb[0] = colors2.red;
        rgb[1] = colors2.blue;
        rgb[2] = colors2.green;

        return rgb;
    }

    public float[] getHSV1(){
        return hsvValues1;
    }
    public float[] getHSV2(){
        return hsvValues2;
    }
}