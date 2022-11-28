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
    boolean prevUpButtonState;
    boolean currUpButtonState;
    boolean prevDownButtonState;
    boolean currDownButtonState;
    int desiredLiftState; //0,1,2,3
    int prevLiftState;

    private boolean done;

    public static Lift init (HardwareMap hardwareMap, Telemetry telemetry) {
        Lift lift = new Lift();
        lift.liftMotor = hardwareMap.dcMotor.get("liftMotor");
        lift.liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.done = false;
        lift.Sensor0 = hardwareMap.digitalChannel.get("Sensor0");
        lift.Sensor1 = hardwareMap.digitalChannel.get("Sensor1");
        lift.Sensor2 = hardwareMap.digitalChannel.get("Sensor2");
        lift.Sensor3 = hardwareMap.digitalChannel.get("Sensor3");
        lift.sensors[0] = lift.Sensor0;
        lift.sensors[1] = lift.Sensor1;
        lift.sensors[2] = lift.Sensor2;
        lift.sensors[3] = lift.Sensor3;
        lift.prevUpButtonState = false;
        lift.currUpButtonState = false;
        lift.prevDownButtonState = false;
        lift.currDownButtonState = false;
        lift.desiredLiftState = 0;
        lift.prevLiftState = 0;
        return lift;
    }

    public void run() {
        try {
            while (true) {
                //first modify lift state
                if (prevUpButtonState == false && currUpButtonState == true) {
                    if (desiredLiftState < 3) {
                        desiredLiftState += 1;
                    }
                }
                if (prevDownButtonState == false && currDownButtonState == true) {
                    if (desiredLiftState > 0) {
                        desiredLiftState -= 1;
                    }
                }
                //then move towards the desired
                if(desiredLiftState > prevLiftState){
                    //go up
                    liftMotor.setPower(50);
                }else if (desiredLiftState < prevLiftState)
                {
                    //go Down
                    liftMotor.setPower(-50);
                    //TODO: set once at the end
                }
                else{
                    //if our prev and desired Lift state are the same, continue in the direction we were going
                    //TODO: keep track of what direction we were going and reverse it, even if in between states

                }

                //now check if we are at out destination
                for (int i = 0; i < 4; i++){
                    if(sensors[i].getState() == false){
                        //we are on a sensor
                        prevLiftState = i;
                        if (i == 0 || i==3 || i== desiredLiftState) {
                            liftMotor.setPower(0);
                        }
                    }else{
                        //if we are not on a sensor, dont update the previous lift state
                        //TODO: update via stepper motor
                    }
                }
                prevUpButtonState = currUpButtonState;
                prevDownButtonState = currDownButtonState;
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            // Maybe say if we're done or not?
        }
    }
    public void updateUpButton (boolean button){
        currUpButtonState = button;
    }
    public void updateDownButton(boolean button){
        currDownButtonState = button;
    }
}

