package org.firstinspires.ftc.teamcode.sorting;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Color_SensorV3 extends OpMode {

    NormalizedColorSensor colorSensor, colorSensor2;
    Servo servo1;
    double hue, hue2, saturation;
    String currColor = "";

    public enum DetectedColor {
        RED,
        BLUE,
        GREEN,
        UNKNOWN
    }

    public void init(HardwareMap hardwareMap) {
        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensor_color");
        colorSensor2 = hardwareMap.get(NormalizedColorSensor.class, "sensor_color2");
        servo1 = hardwareMap.get(ServoImplEx.class, "servo_1");

        servo1.setPosition(0);
        colorSensor.setGain(11);
        colorSensor2.setGain(11);
    }

 /*   public void moveServo() {
        if (returnColor() == "GREEN") {
            servo1.setPosition(1);
        }
    } */
 /*   public String returnColor() {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        float normGreen, unknown;

        normGreen = colors.green / colors.alpha;

        if (normGreen > 0.6) {
            //telemetry.addData("Color: ", "GREEN");
            currColor = "GREEN";
        } else {
            //telemetry.addData("Color: ", "NOT GREEN");
            currColor = "PURPLE"; //any color ball that is not green is purple
        }

        return currColor;
    } */

    public DetectedColor getDetectedColor(Telemetry telemetry) {
        NormalizedRGBA colors = colorSensor.getNormalizedColors();
        NormalizedRGBA colors2 = colorSensor2.getNormalizedColors();
        hue = JavaUtil.colorToHue(colors.toColor());
        hue2 = JavaUtil.colorToHue(colors2.toColor());
        saturation = JavaUtil.colorToSaturation(colors.toColor());


        //sensor 1
        float normRed, normGreen, normBlue;
        normRed = colors.red / colors.alpha;
        normGreen = colors.green / colors.alpha;
        normBlue = colors.blue / colors.alpha;

        //sensor 2
        float normRed2, normGreen2, normBlue2;
        normRed2 = colors2.red / colors2.alpha;
        normGreen2 = colors2.green / colors2.alpha;
        normBlue2 = colors2.blue / colors2.alpha;

        telemetry.addData("Sensor #", "1");
        telemetry.addData("Red: ", normRed);
        telemetry.addData("Green: ", normGreen);
        telemetry.addData("Blue: ", normBlue);
        telemetry.addData("Hue", JavaUtil.colorToHue(colors.toColor()));
        telemetry.addData("Saturation", "%.3f", JavaUtil.colorToSaturation(colors.toColor()));
        //Using hue to detect color
        if (hue < 30) {
            telemetry.addData("ColorS1", "Red");
        } else if (hue < 60) {
            telemetry.addData("ColorS1", "Orange");
        } else if (hue < 90) {
            telemetry.addData("ColorS1", "Yellow");
        } else if (hue < 161 && hue >= 150) {
            telemetry.addData("ColorS1", "Green");
            return DetectedColor.GREEN;
        } else if (hue < 222) {
            telemetry.addData("ColorS1", "Blue");
        } else if (hue < 350) {
            telemetry.addData("ColorS1", "Purple");
        } else {
            telemetry.addData("ColorS1", "Red");
        }
        telemetry.addData("Sensor #", "2");
        telemetry.addData("Red: ", normRed2);
        telemetry.addData("Green: ", normGreen2);
        telemetry.addData("Blue: ", normBlue2);
        telemetry.addData("Hue", JavaUtil.colorToHue(colors2.toColor()));
        telemetry.addData("Saturation", "%.3f", JavaUtil.colorToSaturation(colors2.toColor()));

        //Using hue to detect color
        if (hue2 < 30) {
            telemetry.addData("ColorS2", "Red");
        } else if (hue2 < 60) {
            telemetry.addData("ColorS2", "Orange");
        } else if (hue2 < 90) {
            telemetry.addData("ColorS2", "Yellow");
        } else if (hue2 < 161 && hue >= 150) {
            telemetry.addData("ColorS2", "Green");
            return DetectedColor.GREEN;
        } else if (hue2 < 222) {
            telemetry.addData("ColorS2", "Blue");
        } else if (hue2 < 350) {
            telemetry.addData("ColorS2", "Purple");
        } else {
            telemetry.addData("ColorS2", "Red");
        }

    /* BOTH sensors will detect GREEN up till 11.0 inches
    when only ONE sensor has GREEN DIRECTLY on it, the max distance is 10.0 inces */

      /*  if (normGreen >//normGreen > 0.2 && normBlue < 0.65) {
            telemetry.addData("Color: ", "GREEN");
            telemetry.update();
           // return DetectedColor.GREEN;
        }

        if (normGreen2 > 0.2 && normBlue < 0.65) {
                telemetry.addData("Color: ", "GREEN");
                telemetry.update();
             //   return DetectedColor.GREEN;
            } */
        /* when GAIN = 4
        normGreen = 0.6-4.0.
        */

        telemetry.update();

        return DetectedColor.UNKNOWN;

    }

    public void moveServo() {
        if (getDetectedColor(telemetry) == DetectedColor.GREEN) {
            servo1.setPosition(1);
        }
    }

    @Override
    public void loop () {
    }

    @Override
    public void init () {
    }

}