package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.subsystems.*;

import java.util.Objects;


@Autonomous

public class MecanumAuto extends LinearOpMode {

    Drive drive;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.update();
        waitForStart();
        telemetry.addData("Done:", "%S", "no");
        telemetry.update();
        drive = Drive.init(hardwareMap, telemetry);
        WebcamObjectDetection camera = WebcamObjectDetection.init(hardwareMap, telemetry);
        goForMillis(1250,0, 0.4, 0);
        drive.setMotorSpeeds(0.4,0,0 );
        sleep(377);
        drive.setBrake(0.9f);
        //now scan the cone.
        String objectRecognized = camera.getRecognizedObject();
        drive.setMotorSpeeds(-0.4,0,0 );
        drive.setBrake(0.0f);
        sleep(377);
        drive.setMotorSpeeds(0,0.4,0);
        sleep (500);
        //TODO; function drive for time

        if (Objects.equals(objectRecognized, "one")) {
            //something like goLeft
            drive.setMotorSpeeds(-0.4, 0, 0);
            sleep(1714);
        }


        drive.setBrake(0.9f);
        telemetry.addData("Done:", "%S", "yes");
        telemetry.update();
    }

    void goForMillis(int time, double x, double y, double rx){
        drive.setBrake(0.0f);
        drive.setMotorSpeeds(x ,y, rx);
        sleep(time);
        drive.setBrake(0.9f);
//something,something, and another something
    }
    void goForInches(float inches, direction dir){
        int local_millis = (int) Math.floor(inches * 14);
        if(dir == direction.forward){
            goForMillis(local_millis, 0, 0.4, 0);
        }else if(dir == direction.backward){
            goForMillis(local_millis, 0, -0.4, 0);
        }else if(dir == direction.left){
            goForMillis(local_millis, -0.4, 0, 0);
        }else if(dir == direction.right){
            goForMillis(local_millis, 0.4, 0.4, 0);
        }

    }
    enum direction{
        forward,
        backward,
        left,
        right
    }

}
