package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Drive {

    /**
     * The four DcMotor members are references to the  robot's four drive motors.
     */
    private DcMotor motorFrontLeft;
    private DcMotor motorBackLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorBackRight;

    /**
     * This holds a reference to the owner opmode's telemetry object, it is used to send information
     * to the driver station.
     */
    private Telemetry telemetry;

    /**
     * Initializes and returns an instance of the Drive subsystem that an opmode can use.
     *
     * @param hardwareMap   the opmode's hardware map with the robot configuration
     * @param telemetry     the opmode's telemetry object that allows data to be shared with the driver station.
     */
    public static Drive init (HardwareMap hardwareMap, Telemetry telemetry) {

        // Create a new instance of Drive
        Drive drive = new Drive();

        drive.telemetry = telemetry;

        // Set get an instance of DcMotor from the hardware map for each of the drive motors.
        drive.motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft");
        drive.motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft");
        drive.motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight");
        drive.motorBackRight = hardwareMap.dcMotor.get("motorBackRight");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        //drive.motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        //drive.motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Return the initialized drive.
        return drive;
    }

    public void setMotorSpeeds (double x, double y, double rx) {
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        motorFrontLeft.setPower(frontLeftPower);
        motorBackLeft.setPower(backLeftPower);
        motorFrontRight.setPower(frontRightPower);
        motorBackRight.setPower(backRightPower);
    }
}