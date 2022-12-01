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

public class Lift {

    private DcMotor liftMotor;
    private Telemetry telemetry;
    boolean prevUpButtonState;
    boolean currUpButtonState;
    boolean prevDownButtonState;
    boolean currDownButtonState;
    int desiredLiftState; //0,1,2,3
    int prevLiftState;

    private final double steps_per_rotation = 384.5;
    private final int spool_dia = 112;

    private final static double MOTOR_SPEED = 0.5;

    private final double target_ground_height = 0;
    private final double target_low_height = 355.6;
    private final double target_med_height = 609.6;
    private final double target_high_height = 863.6;

    private final double ground_pos = target_ground_height/spool_dia * steps_per_rotation;
    private final double low_pos = target_low_height/spool_dia * steps_per_rotation;
    private final double med_pos = target_med_height/spool_dia * steps_per_rotation;
    private final double high_pos = target_high_height/spool_dia * steps_per_rotation;

    private Telemetry tel;

    public static Lift init (HardwareMap hardwareMap, Telemetry telemetry) {
        Lift lift = new Lift();
        lift.tel = telemetry;
        lift.liftMotor = hardwareMap.dcMotor.get("liftMotor");
        lift.liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // SRD ADDED FOLLOWING LINE 11/30/2022
        lift.tel = telemetry;
        lift.tel.addData("Lift Position:", "%d steps", lift.liftMotor.getCurrentPosition());
        lift.tel.update();
        lift.liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.tel.update();
        // The previous line sets the current position to zero so make sure the lift is all the way down!
        // lift.liftMotor.setTargetPosition(0);
        // lift.liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Don't need to do the stuff above right now.

        lift.prevUpButtonState = false;
        lift.desiredLiftState = 0;
        lift.prevLiftState = 0;

        return lift;
    }

    //if (prevUpButtonState == false && upButton == true) {
    public void moveUp () {
        tel.addData("Current Press:", "DPAD UP");
        if (desiredLiftState < 3) {
            desiredLiftState += 1;
            go_to_position_based_on_state(desiredLiftState);
        } else {
            tel.addLine ("Lift: Could not move up anymore.");
        }
        tel.update();
    }


    public void moveDown() {
        tel.addData ("Current Press:", "DPAD DOWN");
        if (desiredLiftState > 0) {
            desiredLiftState -= 1;
            go_to_position_based_on_state(desiredLiftState);
        } else {
            tel.addLine("Lift: Could not move down anymore.");
        }
        tel.update();
    }

    public void go_to_position_based_on_state(int state){
        if(state == 0){
            liftMotor.setTargetPosition(((int) ground_pos));
        }else if(state == 1){
            liftMotor.setTargetPosition(((int) low_pos));
        }else if(state == 2){
            liftMotor.setTargetPosition(((int) med_pos));
        }else if(state == 3){
            liftMotor.setTargetPosition(((int) high_pos));
        }else{
            liftMotor.setTargetPosition(((int) ground_pos));
        }
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(MOTOR_SPEED);
        tel.update();
    }
}
