package org.firstinspires.ftc.teamcode.sorting;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class SortTeleOp extends LinearOpMode {
    private DcMotor frontLeft, backLeft, frontRight, backRight;


    @Override
    public void runOpMode() {

        frontLeft  = hardwareMap.get(DcMotor.class, "fl");
        backLeft  = hardwareMap.get(DcMotor.class, "bl");
        frontRight = hardwareMap.get(DcMotor.class, "fr");
        backRight = hardwareMap.get(DcMotor.class, "br");


        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);


        waitForStart();

        while (opModeIsActive()) {
            double denominator;

            double y   = -gamepad1.left_stick_y;
            double x =  gamepad1.left_stick_x;
            double z     =  gamepad1.right_stick_x;


            double flPower  = y + x + z;
            double frPower = y - x - z;
            double blPower   = y - x + z;
            double brPower  = y + x - z;


            denominator = Math.max(Math.abs(flPower), Math.abs(frPower));
            denominator = Math.max(denominator, Math.abs(blPower));
            denominator = Math.max(denominator, Math.abs(brPower));


            if (denominator > 1.0) {
                flPower  /= denominator;
                frPower /= denominator;
                blPower   /= denominator;
                brPower  /= denominator;
            }

            frontLeft.setPower(flPower);
            frontRight.setPower(frPower);
            backLeft.setPower(blPower);
            backRight.setPower(brPower);
        }

    }}
