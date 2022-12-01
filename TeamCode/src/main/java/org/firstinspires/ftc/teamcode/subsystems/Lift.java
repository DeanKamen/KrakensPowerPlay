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

    private double steps_per_rotation = 384.5;
    private int spool_dia = 112;

    private double target_ground_height = 25.4;
    private double target_low_height = 355.6;
    private double target_med_height = 609.6;
    private double target_high_height = 863.6;

    private double ground_pos = target_ground_height/spool_dia * steps_per_rotation;
    private double low_pos = target_low_height/spool_dia * steps_per_rotation;
    private double med_pos = target_med_height/spool_dia * steps_per_rotation;
    private double high_pos = target_high_height/spool_dia * steps_per_rotation;

    private boolean done;

    public static Lift init (HardwareMap hardwareMap, Telemetry telemetry) {
        Lift lift = new Lift();
        lift.liftMotor = hardwareMap.dcMotor.get("liftMotor");
        lift.liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lift.liftMotor.setTargetPosition(0);
        lift.liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.done = false;
        lift.prevUpButtonState = false;
        lift.desiredLiftState = 0;
        lift.prevLiftState = 0;
        return lift;
    }

    public void updatePosition(boolean upButton, boolean downButton) {
        //first modify lift state
        if (prevUpButtonState == false && upButton == true) {
            if (desiredLiftState < 3) {
                desiredLiftState += 1;
            }
            go_to_position_based_on_state(desiredLiftState);
        }
        if (prevDownButtonState == false && downButton == true) {
            if (desiredLiftState > 0) {
                desiredLiftState -= 1;
            }
            go_to_position_based_on_state(desiredLiftState);
        }

        prevUpButtonState = upButton;
        prevDownButtonState = downButton;
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
    }
}
