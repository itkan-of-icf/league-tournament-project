package org.firstinspires.ftc.teamcode.sorting;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.LED;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

import java.util.List;
//this file has 3 LEDs, 3 kickers, and 6 color sensors
//the LEDs should be configured under digital devices. each led has two lights, green and purple (red). each light should have its own configuration port. i.e. green1 is digital devices 0, red1 is digital devices 1, and so on.

/* TODO in order of importance:
        1. [done] check which servo controls each servo. if when gamepad1.y is clicked which is supposed to move kicker1 actually moves kicker2, then change that kicker config to kicker1 or whatever name it is
        2. [done] allocate numbers for each slot, if needed change kicker values, and add comments for the color sensors in those slots
        3. [done] comment the kicker motions and uncomment the kicker pattern ones.
        4. check if Boolean (wrapper of boolean) method works. else:
            change return of the getDetectedSlot# type to int where green detected returns 1, purple detected returns 2, any other color detected returns 0.
        5. add LEDs
        6. add drive, intake, and shooter
    */

@TeleOp
public class SortArtifactsRunLL extends LinearOpMode {

    NormalizedColorSensor colorSensor1, colorSensor2;

    NormalizedColorSensor colorSensor3, colorSensor4;

    NormalizedColorSensor colorSensor5, colorSensor6;

    ColorSensorObject slot1 = new ColorSensorObject("cs11", "cs12");
    ColorSensorObject slot2 = new ColorSensorObject("cs21", "cs22");
    ColorSensorObject slot3 = new ColorSensorObject("cs31", "cs32");

    //there are not actually six LEDs - there are three and each devices uses an separate port to control that LED. red light refers to purple sensor
    public LED green1, green2, green3; //digital devices: ports 0, 2, 4
    public LED purple1, purple2, purple3; //digital devices: ports 1, 3, 5
    public Servo kicker1, kicker2, kicker3;
    //   public Limelight3A limelight;

    public DcMotorEx shooter;

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

    /*
    //public double kickerUpPos = 0.2;
    //public double kickerDownPos = 0.0;
    double limelightHeight  = 14; // cam height from floor in inches
    double targetHeight = 29.5; // // basketHeight - center of the april tag 29.5 - degrees
    double mountAngle = 20.5; // 17.5;//19.0;//  11.7; //27.0;// 17.5;// 25.0;// 32.0; // degrees

    int fiducialId = 0; */

    private static final double SHOOTING_POSITION_KICKER1 = 0.3;
    private static final double SHOOTING_POSITION_KICKER2 = 0.55;
    private static final double SHOOTING_POSITION_KICKER3 = 0.07;

    private static final double INIT_POSITION_KICKER1 = 0.71;
    private static final double INIT_POSITION_KICKER2 = 0.15;
    private static final double INIT_POSITION_KICKER3 = 0.45;

    //LLResult result = limelight.getLatestResult();

    int waitTime = 100;


    @Override
    public void runOpMode() throws InterruptedException {

        intitializeKickerServos();
        initializeShooterMotor();

        colorSensor1 = hardwareMap.get(NormalizedColorSensor.class, "cs11");
        colorSensor2 = hardwareMap.get(NormalizedColorSensor.class, "cs12");

        colorSensor3 = hardwareMap.get(NormalizedColorSensor.class, "cs21");
        colorSensor4 = hardwareMap.get(NormalizedColorSensor.class, "cs22");

        colorSensor5 = hardwareMap.get(NormalizedColorSensor.class, "cs31");
        colorSensor6 = hardwareMap.get(NormalizedColorSensor.class, "cs32");

        colorSensor1.setGain(11);
        colorSensor2.setGain(11);

      /*  kicker1 = hardwareMap.get(Servo.class, "kicker1");
        kicker2 = hardwareMap.get(Servo.class, "kicker2");
        kicker3 = hardwareMap.get(Servo.class, "kicker3"); */

   /*     green1 = hardwareMap.get(LED.class, "green1");
        purple1 = hardwareMap.get(LED.class, "red1");

        green2 = hardwareMap.get(LED.class, "green2");
        purple2 = hardwareMap.get(LED.class, "red2");

        green3 = hardwareMap.get(LED.class, "green3");
        purple3 = hardwareMap.get(LED.class, "red3"); */

  /*      limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0); // Assume pipeline 0 is AprilTag
        limelight.start(); */

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

        int id = 21; // default

        waitForStart();

        while (opModeIsActive()) {

         //   shooter.setVelocity(1600);

  /*          if (gamepad1.y) {
                kicker1.setPosition(SHOOTING_POSITION_KICKER1);
            }

            if (gamepad1.b) {
                kicker2.setPosition(SHOOTING_POSITION_KICKER2);
            }

            if (gamepad1.a) {
                kicker3.setPosition(SHOOTING_POSITION_KICKER3);
            } */

            /* setLED1Color();
            setLED2Color();
            setLED3Color(); */

         /*   List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fiducial : fiducials) {
                id = fiducial.getFiducialId();
                telemetry.addLine("April Tag:  " + id);
                telemetry.update();
            } */

            if (gamepad2.y) {
                id = 21;
                telemetry.addLine("id 21");
                telemetry.update();
            }

            if (gamepad2.b) {
                id = 22;
                telemetry.addLine("id 22");
                telemetry.update();
            }

            if (gamepad2.a) {
                id = 23;
                telemetry.addLine("id 23");
                telemetry.update();
            }

            slot1.getColor();
            slot2.getColor();
            slot3.getColor();


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

           if (gamepad1.x) {


               // telemetry.addLine();
              //  TeleGetDetectedColorSlot2();
               // telemetry.addLine();
             //   TeleGetDetectedColorSlot3();

                //case 1 and 8: all slots green, all slots purple, or no color detected in any slot
          /*      boolean v1 = ((getDetectedColorSlot1() == 1 && getDetectedColorSlot2() == 1 && getDetectedColorSlot3() == 1) ||
                        (getDetectedColorSlot1() == 2 && getDetectedColorSlot2() == 2 && getDetectedColorSlot3() == 2)
                        || (getDetectedColorSlot1() == 0 || getDetectedColorSlot2() == 0 || getDetectedColorSlot3() == 0)) ;

                // case 2: GGP - rand
                boolean v2 = (getDetectedColorSlot1() == 1 && getDetectedColorSlot2() == 1 && getDetectedColorSlot3() == 2);

                //case 3: GPG - rand
                boolean v3 = (getDetectedColorSlot1() == 1 && getDetectedColorSlot2() == 2 && getDetectedColorSlot3() == 1);

                //case 5: PGG - rand
                boolean v4 = (getDetectedColorSlot1() == 2 && getDetectedColorSlot2() == 1 && getDetectedColorSlot3() == 1); */


            /**   if(v2) { // v1 || v2 || v3 || v4) {

                    for (int i = 0; i < 1; i++ ) { // run once
                    kickersToPositionRandom();
                    }

                telemetry.addLine("Current Slot Pattern: GGP");
                telemetry.update();

                    
                } **/

          /*      if (id == 21) { //MOTIF PATTERN: GPP
                    //case 4: GPP (CORRECT PATTERN)

                    if (getDetectedColorSlot1() && !getDetectedColorSlot2() && !getDetectedColorSlot3()) {
                        kicker1.setPosition(SHOOTING_POSITION_KICKER1);
                        sleep(500); // wait for shooter to get the ball
                        kicker1.setPosition(INIT_POSITION_KICKER1);
                        sleep(200); // let the shooter pick up speed

                        kicker2.setPosition(SHOOTING_POSITION_KICKER2);
                        sleep(500);
                        kicker2.setPosition(INIT_POSITION_KICKER2);
                        sleep(200);

                        kicker3.setPosition(SHOOTING_POSITION_KICKER3);
                        sleep(500);
                        kicker3.setPosition(INIT_POSITION_KICKER3);
                        sleep(200);
                    }

                    //case 6: PGP - right colors, wrong order
                    if (!getDetectedColorSlot1() && getDetectedColorSlot2() && !getDetectedColorSlot3()) {
                        kicker2.setPosition(SHOOTING_POSITION_KICKER2);
                        sleep(500); // wait for shooter to get the ball
                        kicker2.setPosition(INIT_POSITION_KICKER2);
                        sleep(200); // let the shooter pick up speed

                        kicker1.setPosition(SHOOTING_POSITION_KICKER1);
                        sleep(500);
                        kicker1.setPosition(INIT_POSITION_KICKER1);
                        sleep(200);

                        kicker3.setPosition(SHOOTING_POSITION_KICKER3);
                        sleep(500);
                        kicker3.setPosition(INIT_POSITION_KICKER3);
                        sleep(200);

                    }

                    //case 7: PPG - right colors, wrong order
                    if (!getDetectedColorSlot1() && !getDetectedColorSlot2() && getDetectedColorSlot3()) {
                        kicker3.setPosition(SHOOTING_POSITION_KICKER3);
                        sleep(500); // wait for shooter to get the ball
                        kicker3.setPosition(INIT_POSITION_KICKER3);
                        sleep(200); // let the shooter pick up speed

                        kicker1.setPosition(SHOOTING_POSITION_KICKER1);
                        sleep(500);
                        kicker1.setPosition(INIT_POSITION_KICKER1);
                        sleep(200);

                        kicker2.setPosition(SHOOTING_POSITION_KICKER2);
                        sleep(500);
                        kicker2.setPosition(INIT_POSITION_KICKER2);
                        sleep(200);

                    }

                } */

               /*   if (id == 22) { //MOTIF PATTERN: PGP

                    //case 4: GPP - right colors, wrong order
                    if (getDetectedColorSlot1() && !getDetectedColorSlot2() && !getDetectedColorSlot3()) {

                    }


                    //case 6: PGP (CORRECT PATTERN)
                    if (!getDetectedColorSlot1() && getDetectedColorSlot2() && !getDetectedColorSlot3()) {


                        // send down
                    }

                    //case 7: PPG - right colors, wrong order
                    if (!getDetectedColorSlot1() && !getDetectedColorSlot2() && getDetectedColorSlot3()) {

                    }

                } */

          /*      if (id == 23) { //MOTIF PATTERN: PPG

                    //case 4: GPP - right colors, wrong order
                    if (getDetectedColorSlot1() && !getDetectedColorSlot2() && !getDetectedColorSlot3()) {

                    }

                    //case 6: PGP - right colors, wrong order
                    if (!getDetectedColorSlot1() && getDetectedColorSlot2() && !getDetectedColorSlot3()) {

                    }

                    //case 7: PPG (CORRECT ORDER)
                    if (!getDetectedColorSlot1() && !getDetectedColorSlot2() && getDetectedColorSlot3()) {

                    }

                } */

            }

        }

    }

    private void initializeShooterMotor() {
        shooter = hardwareMap.get(DcMotorEx.class, "shooter"); // shooter top motor
        shooter.setDirection(DcMotorEx.Direction.FORWARD);

        // Set motor to RUN_USING_ENCODER mode for velocity control
        shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        // Set zero power behavior
        shooter.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }
    private void intitializeKickerServos() {
        kicker1 = hardwareMap.get(Servo.class, "kicker1");
        kicker1.setDirection(Servo.Direction.REVERSE);
        kicker1.scaleRange(0.0, 1.0); // full PWM range used
        kicker1.setPosition(0.71); //RESTING_POSITION);

        kicker2 = hardwareMap.get(Servo.class, "kicker2");
        kicker2.setDirection(Servo.Direction.FORWARD);
        kicker2.scaleRange(0.0, 1.0); // full PWM range used
        kicker2.setPosition(0.15);

        kicker3 = hardwareMap.get(Servo.class, "kicker3");
        kicker3.setDirection(Servo.Direction.FORWARD);
        kicker3.scaleRange(0.0, 1.0); // full PWM range used
        kicker3.setPosition(0.45);
    }

    // if returns 1, at least one color sensor has seen green
    // if return 2, neither sensor has seen green so the ball is purple
    // if returns 0, no artifact in slot
    public void TeleGetDetectedColorSlot1() {
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

        telemetry.update();

    }

    public void TeleGetDetectedColorSlot2() {
        //cs3
        if (hue3 <= 50) {
            cs3 = "RED";
            telemetry.addData("ColorS3", "Red");
        } else if (hue3 >= 51 && hue3 <= 60) {
            cs3 = "ORANGE";
            telemetry.addData("ColorS3", "Orange");
        } else if (hue3 >= 61 && hue3 <= 90) {
            cs3 = "YELLOW";
            telemetry.addData("ColorS1", "Yellow");
        } else if (hue1 <= 165 && hue4 >= 150) {
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


        //cs4
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
            cs2 = "BLUE";
            telemetry.addData("ColorS4", "Blue");
        } else if (hue4 >= 223 && hue4 <= 350) {
            telemetry.addData("Color42", "Purple");
            cs4 = "PURPLE";
        } else {
            cs4 = "RED";
            telemetry.addData("ColorS4", "Red");
        }

        telemetry.update();

    }

    public void TeleGetDetectedColorSlot3() {
        //cs3
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


        //cs4
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
            cs2 = "BLUE";
            telemetry.addData("ColorS6", "Blue");
        } else if (hue6 >= 223 && hue6 <= 350) {
            telemetry.addData("Color6", "Purple");
            cs6 = "PURPLE";
        } else {
            cs6 = "RED";
            telemetry.addData("ColorS6", "Red");
        }

        telemetry.update();

    }

    public int getDetectedColorSlot1() {
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
            return 1;  // artifact green
        } else if (cs1.equals("PURPLE") || cs2.equals("PURPLE")) {
            return 2; // artifact purple
        } else if (!cs1.equals("GREEN") && !cs2.equals("GREEN") && !cs1.equals("PURPLE") && !cs2.equals("PURPLE") ) {
            // sensors are seeing colors other than green or purple, or no artifact in the slot
            return 0;
        }

        telemetry.update();
        return 0; //TODO: change this


    }

    public int getDetectedColorSlot2() {
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
            return 1;  // artifact green
        } else if (cs3.equals("PURPLE") || cs4.equals("PURPLE")) {
            return 2; // artifact purple
        } else if (!cs3.equals("GREEN") && !cs4.equals("GREEN") && !cs3.equals("PURPLE") && !cs4.equals("PURPLE") ) {
            // sensors are seeing colors other than green or purple, or no artifact in the slot
            return 0;
        }

        telemetry.update();
        return 0; //TODO: change this

    }

    public int getDetectedColorSlot3() {
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
            return 1;  // artifact green
        } else if (cs5.equals("PURPLE") || cs6.equals("PURPLE")) {
            return 2; // artifact purple
        } else if (!cs5.equals("GREEN") && !cs6.equals("GREEN") && !cs5.equals("PURPLE") && !cs6.equals("PURPLE") ) {
            // sensors are seeing colors other than green or purple, or no artifact in the slot
            return 0;
        }

        telemetry.update();
        return 0; //TODO: change this

    }

    public void kickersToPositionRandom() {
        kicker1.setPosition(SHOOTING_POSITION_KICKER1);
        sleep(500); // wait for shooter to get the ball
        kicker1.setPosition(INIT_POSITION_KICKER1);
        sleep(200); // let the shooter pick up speed

        kicker2.setPosition(SHOOTING_POSITION_KICKER2);
        sleep(500);
        kicker2.setPosition(INIT_POSITION_KICKER2);
        sleep(200);

        kicker3.setPosition(SHOOTING_POSITION_KICKER3);
        sleep(500);
        kicker3.setPosition(INIT_POSITION_KICKER3);
        sleep(200);
    }

    public void setLED1Color() {

        if (getDetectedColorSlot1() == 1) { // if true, green ball
            telemetry.addLine("seeing green");
            telemetry.update();

            purple1.on();
            green1.off();

        } else if (getDetectedColorSlot1() == 2){ // if false, purple ball
            telemetry.addLine("seeing purple");
            telemetry.update();

            green1.on();
            purple1.off();
        } else if (getDetectedColorSlot1() == 0) { // if null, no ball

            green1.off();
            purple1.off();
        }
    }

    public void setLED2Color() {

        if (getDetectedColorSlot2() == 1 ) { // if true, green ball
            telemetry.addLine("seeing green");
            telemetry.update();

            purple2.on();
            green2.off();

        } else if (getDetectedColorSlot2() == 2){ // if false, purple ball
            telemetry.addLine("seeing purple");
            telemetry.update();

            green2.on();
            purple2.off();
        } else if (getDetectedColorSlot2() == 0) { // if null, no ball

            green2.off();
            purple2.off();
        }
    }

    public void setLED3Color() {

        if (getDetectedColorSlot3() == 1) { // if true, green ball
            telemetry.addLine("seeing green");
            telemetry.update();

            purple3.on();
            green3.off();

        } else if (getDetectedColorSlot3() == 2 ){ // if false, purple ball
            telemetry.addLine("seeing purple");
            telemetry.update();

            green3.on();
            purple3.off();
        } else if (getDetectedColorSlot3() == 0) { // if null, no ball

            green3.off();
            purple3.off();
        }
    }

}
