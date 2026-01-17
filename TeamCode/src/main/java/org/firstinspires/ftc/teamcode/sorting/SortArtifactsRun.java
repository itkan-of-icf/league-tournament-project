package org.firstinspires.ftc.teamcode.sorting;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.LED;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

//this is a testing file without the limelight stuff
/* TODO:
        allocate numbers for each slot and add comments for the color sensors in those slots (quicker debugging)
        check if Boolean (wrapper of boolean) method works. else:
            change return type to int, green detected returns 1, purple detected returns 2, any other color detected returns 0.
    */


@TeleOp
public class SortArtifactsRun extends LinearOpMode {

    NormalizedColorSensor colorSensor1, colorSensor2;

    NormalizedColorSensor colorSensor3, colorSensor4;

    NormalizedColorSensor colorSensor5, colorSensor6;

    public LED green1, green2, green3;
    public LED purple1, purple2, purple3;
    public Servo kicker1, kicker2, kicker3;

    double hue1, hue2;
    double hue3, hue4;
    double hue5, hue6;

    double saturation;
    public String cs1 = "UNKNOWN";
    public String cs2 = "UNKNOWN";
    public String cs3 = "UNKNOWN";
    public String cs4 = "UNKNOWN";
    public String cs5 = "UNKNOWN";
    public String cs6 = "UNKNOWN";

    public double kickerUpPos = 0.2; // change to pos that send artifact to turret


    @Override
    public void runOpMode() throws InterruptedException {

        colorSensor1 = hardwareMap.get(NormalizedColorSensor.class, "colorSensor1");
        colorSensor2 = hardwareMap.get(NormalizedColorSensor.class, "colorSensor2");

        colorSensor3 = hardwareMap.get(NormalizedColorSensor.class, "colorSensor3");
        colorSensor4 = hardwareMap.get(NormalizedColorSensor.class, "colorSensor4");

        colorSensor5 = hardwareMap.get(NormalizedColorSensor.class, "colorSensor5");
        colorSensor6 = hardwareMap.get(NormalizedColorSensor.class, "colorSensor6");

        colorSensor1.setGain(11);
        colorSensor2.setGain(11);

        kicker1 = hardwareMap.get(Servo.class, "kicker1");
        kicker2 = hardwareMap.get(Servo.class, "kicker2");
        kicker3 = hardwareMap.get(Servo.class, "kicker3");

        green1 = hardwareMap.get(LED.class, "green1");
        purple1 = hardwareMap.get(LED.class, "red1");

        green2 = hardwareMap.get(LED.class, "green2");
        purple2 = hardwareMap.get(LED.class, "red2");

        green3 = hardwareMap.get(LED.class, "green3");
        purple3 = hardwareMap.get(LED.class, "red3");

        NormalizedRGBA colors = colorSensor1.getNormalizedColors();
        NormalizedRGBA colors2 = colorSensor2.getNormalizedColors();

        NormalizedRGBA colors3 = colorSensor3.getNormalizedColors();
        NormalizedRGBA colors4 = colorSensor4.getNormalizedColors();

        NormalizedRGBA colors5 = colorSensor5.getNormalizedColors();
        NormalizedRGBA colors6 = colorSensor6.getNormalizedColors();

        //using hue to detect color, not NormalizedRGBA colors#
        hue1 = JavaUtil.colorToHue(colors.toColor());
        hue2 = JavaUtil.colorToHue(colors2.toColor());

        hue3 = JavaUtil.colorToHue(colors3.toColor());
        hue4 = JavaUtil.colorToHue(colors4.toColor());

        hue5 = JavaUtil.colorToHue(colors5.toColor());
        hue6 = JavaUtil.colorToHue(colors6.toColor());

        saturation = JavaUtil.colorToSaturation(colors.toColor());

        waitForStart();

        while (opModeIsActive()) {

            // setLED1Color();
            //setLED2Color();
            //setLED3Color();

            //TODO: after testing if-statement at line 149, change param of setPosition to position that pushes artifact to turret when green.
            //TODO: also add line for kicker to go back down
          /*  if(getDetectedColorSlot1()) {
                kicker1.setPosition(0.5); // kick up to turret
                sleep(500); // make as small as possible
                kicker1.setPosition(0); // down
                setLED1Color();
            } */

            //test code
            if (gamepad1.y) {
                if (getDetectedColorSlot1()) { //true, green
                    kicker1.setPosition(kickerUpPos);
                } else if (!getDetectedColorSlot1()) { //false, purple
                    telemetry.addLine("purple artifact");
                    telemetry.update();
                } else if (getDetectedColorSlot1() == null) { //null, no artifact in place
                    telemetry.addLine("no artifact detected");
                    telemetry.update();
                }
            }

            if (gamepad1.b) {
                if(getDetectedColorSlot2()) {
                    kicker2.setPosition(kickerUpPos);
                } else if (!getDetectedColorSlot2()) {
                    telemetry.addLine("purple artifact");
                    telemetry.update();
                } else if (getDetectedColorSlot2() == null) {
                    telemetry.addLine("no artifact detected");
                    telemetry.update();
                }
            }

            if (gamepad1.a) {
                if(getDetectedColorSlot3()) {
                    kicker3.setPosition(kickerUpPos);
                } else if (!getDetectedColorSlot3()) {
                    telemetry.addLine("purple artifact");
                    telemetry.update();
                } else if (getDetectedColorSlot3() == null) {
                    telemetry.addLine("no artifact detected");
                    telemetry.update();
                }
            }

        }

    }

    // if returns true, at least one color sensor has seen green
    // if return false, neither sensor has seen green so the ball is  purple
    // if returns null, no artifact in slot
    public Boolean getDetectedColorSlot1() {
        //cs1
        if (hue1 <= 50) {
            cs1 = "RED";
            telemetry.addData("ColorS1", "Red");
        } else if (hue1 >= 51 && hue1 <= 60) {
            cs1 = "ORANGE";
            telemetry.addData("ColorS1", "Orange");
        } else if (hue1 >= 61 && hue1 <= 90) {
            cs1 = "YELLOW";
            telemetry.addData("ColorS1", "Yellow");
        } else if (hue1 <= 165 && hue1 >= 150) {
            cs1 = "GREEN";
            telemetry.addData("ColorS1", "Green");
        } else if (hue1 >= 166 && hue1 <= 222) {
            cs1 = "BLUE";
            telemetry.addData("ColorS1", "Blue");
        } else if (hue1 >= 223 && hue1 <= 350) {
            cs1 = "PURPLE";
            telemetry.addData("ColorS1", "Purple");
        } else {
            cs1 = "RED";
            telemetry.addData("ColorS1", "Red");
        }


        //cs2
        if (hue2 <= 50) {
            cs2 = "RED";
            telemetry.addData("ColorS2", "Red");
        } else if (hue2 >= 51 && hue2 <= 60) {
            cs2 = "ORANGE";
            telemetry.addData("ColorS2", "Orange");
        } else if (hue2 >= 61 && hue2 <= 90) {
            cs2 = "YELLOW";
            telemetry.addData("ColorS2", "Yellow");
        } else if (hue2 <= 165 && hue2 >= 150) {
            cs2 = "GREEN";
            telemetry.addData("ColorS2", "Green");
        } else if (hue2 >= 166 && hue2 <= 222) {
            cs2 = "BLUE";
            telemetry.addData("ColorS2", "Blue");
        } else if (hue2 >= 223 && hue2 <= 350) {
            telemetry.addData("ColorS2", "Purple");
            cs2 = "PURPLE";
        } else {
            cs2 = "RED";
            telemetry.addData("ColorS2", "Red");
        }

        if (cs1.equals("GREEN") || cs2.equals("GREEN")) {
            return true;  // artifact green
        } else if (cs1.equals("PURPLE") || cs2.equals("PURPLE")) {
            return false; // artifact purple
        } else if (!cs1.equals("GREEN") && !cs2.equals("GREEN") && !cs1.equals("PURPLE") && !cs2.equals("PURPLE") ) {
            // sensors are seeing colors other than green or purple, or no artifact in the slot
            return null;
        }

        return null; //TODO: change this

    }

    public Boolean getDetectedColorSlot2() {
        //color sensor 3
        if (hue3 <= 50) {
            cs3 = "RED";
            telemetry.addData("ColorS3", "Red");
        } else if (hue3 >= 51 && hue3 <= 60) {
            cs3 = "ORANGE";
            telemetry.addData("ColorS3", "Orange");
        } else if (hue3 >= 61 && hue3 <= 90) {
            cs3 = "YELLOW";
            telemetry.addData("ColorS3", "Yellow");
        } else if (hue3 <= 165 && hue3 >= 150) {
            cs3 = "GREEN";
            telemetry.addData("ColorS3", "Green");
        } else if (hue3 >= 166 && hue3 <= 222) {
            cs3 = "BLUE";
            telemetry.addData("ColorS3", "Blue");
        } else if (hue3 >= 223 && hue3 <= 350) {
            cs3 = "PURPLE";
            telemetry.addData("ColorS3", "Purple");
        } else {
            cs3 = "RED";
            telemetry.addData("ColorS3", "Red");
        }


        //color sensor 4
        if (hue4 <= 50) {
            cs4 = "RED";
            telemetry.addData("ColorS4", "Red");
        } else if (hue4 >= 51 && hue4 <= 60) {
            cs4 = "ORANGE";
            telemetry.addData("ColorS4", "Orange");
        } else if (hue4 >= 61 && hue4 <= 90) {
            cs4 = "YELLOW";
            telemetry.addData("ColorS4", "Yellow");
        } else if (hue4 <= 165 && hue4 >= 150) {
            cs4 = "GREEN";
            telemetry.addData("ColorS4", "Green");
        } else if (hue4 >= 166 && hue4 <= 222) {
            cs4 = "BLUE";
            telemetry.addData("ColorS4", "Blue");
        } else if (hue4 >= 223 && hue4 <= 350) {
            telemetry.addData("ColorS4", "Purple");
            cs4 = "PURPLE";
        } else {
            cs4 = "RED";
            telemetry.addData("ColorS4", "Red");
        }

        if (cs3.equals("GREEN") || cs2.equals("GREEN")) {
            return true;  // artifact green
        } else if (cs3.equals("PURPLE") || cs4.equals("PURPLE")) {
            return false; // artifact purple
        } else if (!cs3.equals("GREEN") && !cs4.equals("GREEN") && !cs3.equals("PURPLE") && !cs4.equals("PURPLE") ) {
            // sensors are seeing colors other than green or purple, or no artifact in the slot
            return null;
        }

        return null; //TODO: change this

    }

    public Boolean getDetectedColorSlot3() {
        //color sensor 5
        if (hue5 <= 50) {
            cs5 = "RED";
            telemetry.addData("ColorS5", "Red");
        } else if (hue5 >= 51 && hue5 <= 60) {
            cs5 = "ORANGE";
            telemetry.addData("ColorS5", "Orange");
        } else if (hue5 >= 61 && hue5 <= 90) {
            cs5 = "YELLOW";
            telemetry.addData("ColorS5", "Yellow");
        } else if (hue5 <= 165 && hue5 >= 150) {
            cs5 = "GREEN";
            telemetry.addData("ColorS5", "Green");
        } else if (hue5 >= 166 && hue5 <= 222) {
            cs5 = "BLUE";
            telemetry.addData("ColorS5", "Blue");
        } else if (hue5 >= 223 && hue5 <= 350) {
            cs5 = "PURPLE";
            telemetry.addData("ColorS5", "Purple");
        } else {
            cs5 = "RED";
            telemetry.addData("ColorS5", "Red");
        }


        //color sensor 6
        if (hue6 <= 50) {
            cs6 = "RED";
            telemetry.addData("ColorS6", "Red");
        } else if (hue6 >= 51 && hue6 <= 60) {
            cs6 = "ORANGE";
            telemetry.addData("ColorS6", "Orange");
        } else if (hue6 >= 61 && hue6 <= 90) {
            cs6 = "YELLOW";
            telemetry.addData("ColorS6", "Yellow");
        } else if (hue6 <= 165 && hue6 >= 150) {
            cs6 = "GREEN";
            telemetry.addData("ColorS6", "Green");
        } else if (hue6 >= 166 && hue6 <= 222) {
            cs6 = "BLUE";
            telemetry.addData("ColorS6", "Blue");
        } else if (hue6 >= 223 && hue6 <= 350) {
            telemetry.addData("ColorS6", "Purple");
            cs6 = "PURPLE";
        } else {
            cs6 = "RED";
            telemetry.addData("ColorS6", "Red");
        }

        if (cs5.equals("GREEN") || cs2.equals("GREEN")) {
            return true;  // artifact green
        } else if (cs5.equals("PURPLE") || cs6.equals("PURPLE")) {
            return false; // artifact purple
        } else if (!cs5.equals("GREEN") && !cs6.equals("GREEN") && !cs5.equals("PURPLE") && !cs6.equals("PURPLE") ) {
            // sensors are seeing colors other than green or purple, or no artifact in the slot
            return null;
        }

        return null; //TODO: change this

    }
    public void setLED1Color() {

        if (getDetectedColorSlot1()) { // if true, green ball
            telemetry.addLine("seeing green");
            telemetry.update();

            purple1.on();
            green1.off();

        } else if (!getDetectedColorSlot1()){ // if false, purple ball
            telemetry.addLine("seeing purple");
            telemetry.update();

            green1.on();
            purple1.off();
        } else if (getDetectedColorSlot1() == null) { // if null, no ball

            green1.off();
            purple1.off();
        }
    }

    public void setLED2Color() {

        if (getDetectedColorSlot2()) { // if true, green ball
            telemetry.addLine("seeing green");
            telemetry.update();

            purple2.on();
            green2.off();

        } else if (!getDetectedColorSlot2()){ // if false, purple ball
            telemetry.addLine("seeing purple");
            telemetry.update();

            green2.on();
            purple2.off();
        } else if (getDetectedColorSlot2() == null) { // if null, no ball

            green2.off();
            purple2.off();
        }
    }

    public void setLED3Color() {

        if (getDetectedColorSlot3()) { // if true, green ball
            telemetry.addLine("seeing green");
            telemetry.update();

            purple3.on();
            green3.off();

        } else if (!getDetectedColorSlot3()){ // if false, purple ball
            telemetry.addLine("seeing purple");
            telemetry.update();

            green3.on();
            purple3.off();
        } else if (getDetectedColorSlot3() == null) { // if null, no ball

            green3.off();
            purple3.off();
        }
    }

}
