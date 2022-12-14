package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.subsystems.*;

@Autonomous
public class MecanumAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.update();
        waitForStart();
        telemetry.addData("Done:", "%S", "no");
        telemetry.update();
        Drive drive = Drive.init(hardwareMap, telemetry);
        drive.setMotorSpeeds(0, 0.4, 0);
        sleep(1500);
        drive.setBrake(0.9f);
        telemetry.addData("Done:", "%S", "yes");
        telemetry.update();
    }
}
