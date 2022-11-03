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
    private boolean boost;
    private boolean brake;
    private boolean slowmode;

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
        drive.setMotorBraking();


        // Return the initialized drive.
        return drive;
    }

    public void setMotorFloating()
    {
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }
    public void setMotorBraking()
    {
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void setMotorSpeeds (double x, double y, double rx) {
        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]

        double denominator = -1*(Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1));
        if(!boost || slowmode){
            denominator = denominator*1.4;
        }
        if(brake){
            x=0;
            y=0;
        }else if(slowmode){
            x=x/2;
            y=y/2;
        }
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        motorFrontLeft.setPower(frontLeftPower);
        motorBackLeft.setPower(backLeftPower);
        motorFrontRight.setPower(frontRightPower);
        motorBackRight.setPower(backRightPower);
    }
    public void setBoost(boolean boost1){
        boost = boost1;
    }
    public void setBrake(float howMuchBrake){
        if(howMuchBrake > 0.03 && howMuchBrake < 0.7){
            slowmode = true;
            brake = false;
        }else if(howMuchBrake > 0.7) {
            slowmode = false;
            brake = true;
        }else{
            slowmode = false;
            brake = false;
        }
    }
}