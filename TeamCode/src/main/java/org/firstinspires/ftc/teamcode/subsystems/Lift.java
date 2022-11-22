package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.DigitalChannel;

public class Lift implements Runnable {

    private DcMotor liftMotor;
    private Telemetry telemetry;
    DigitalChannel Sensor0;
    DigitalChannel Sensor1;
    DigitalChannel Sensor2;
    DigitalChannel Sensor3;
    DigitalChannel sensors[];
    boolean up;
    boolean down;
    int liftState; //0,1,2,3

    private boolean done;

    public static Lift init (HardwareMap hardwareMap, Telemetry telemetry) {
        Lift lift = new Lift();
        lift.liftMotor = hardwareMap.dcMotor.get("liftMotor");
        lift.liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.done = false;
        lift.sensors[0] = lift.Sensor0;
        return lift;
    }

    public void run(){
        try {
            while (true) {
                // If we're done, get the heck out of here!
                if (done) {
                    throw new InterruptedException(liftMotor.getDeviceName() + " done.");
                }
                if(up){
                    if(liftState < 3){
                        liftMotor.setPower(1.0);
                        liftState += 1;
                    }
                }
                else if (down){
                    if(liftState > 0){
                        liftMotor.setPower(-1.0);
                        liftState -= 1;
                    }
                }

                // Check to see if the user has asked for any of the services.
                if (sensors[liftState].getState() == false) {
                    liftMotor.setPower(0);
                }
                // We've moved to this position.  Wait for another command.
                theMotor.setPower(this.newPower);
                Thread.sleep (napLength);
            }
        } catch (InterruptedException e) {
            // Maybe say if we're done or not?
        }
    }
}
