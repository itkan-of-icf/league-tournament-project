package org.firstinspires.ftc.teamcode.sorting;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

@TeleOp
public class TestColorValues extends LinearOpMode {

    ColorSensorObject slot1 = new ColorSensorObject("cs11", "cs12");
    ColorSensorObject slot2 = new ColorSensorObject("cs21", "cs22");
    ColorSensorObject slot3 = new ColorSensorObject("cs31", "cs32");


    @Override
    public void runOpMode() throws InterruptedException {



        waitForStart();

        while (opModeIsActive()) {

            telemetry.addLine()
                    .addData("Red", "%.3f", slot1.sensor1_RGB()[0])
                    .addData("Green", "%.3f", slot1.sensor1_RGB()[0])
                    .addData("Blue", "%.3f", slot1.sensor1_RGB()[0]);
            telemetry.addLine()
                    .addData("Hue", "%.3f", slot1.getHSV1()[0])
                    .addData("Saturation", "%.3f", slot1.getHSV1()[1])
                    .addData("Value", "%.3f", slot1.getHSV1()[2]);

            telemetry.addLine()
                    .addData("Red", "%.3f", slot1.sensor2_RGB()[0])
                    .addData("Green", "%.3f", slot1.sensor2_RGB()[0])
                    .addData("Blue", "%.3f", slot1.sensor2_RGB()[0]);
            telemetry.addLine()
                    .addData("Hue", "%.3f", slot1.getHSV2()[0])
                    .addData("Saturation", "%.3f", slot1.getHSV2()[1])
                    .addData("Value", "%.3f", slot1.getHSV2()[2]);


        }


    }
}
