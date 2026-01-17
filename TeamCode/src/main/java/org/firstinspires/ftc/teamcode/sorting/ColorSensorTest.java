package org.firstinspires.ftc.teamcode.sorting;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class ColorSensorTest extends OpMode {
    Color_SensorV3 bench = new Color_SensorV3();

    @Override
    public void init(){
        bench.init(hardwareMap);
    }

    @Override
    public void loop(){
        bench.getDetectedColor(telemetry);


    }
}