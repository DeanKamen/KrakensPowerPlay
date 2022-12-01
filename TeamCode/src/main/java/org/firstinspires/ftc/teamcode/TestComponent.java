package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Lift;

@TeleOp
public class TestComponent extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Lift lift = Lift.init(hardwareMap, telemetry);

        Servo servoHook = hardwareMap.get(Servo.class, "cone_hook");
        double servohookpos = 0.0;
        boolean noBumper = true;

        servoHook.setDirection(Servo.Direction.REVERSE);
        waitForStart();
        getRuntime();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.right_bumper && noBumper) {
                servohookpos += 0.01;
                noBumper = false;
            } else if (gamepad1.left_bumper && noBumper) {
                servohookpos -= 0.01;
                noBumper = false;
            } else {
                noBumper = true;
            }
            servoHook.setPosition(servohookpos);
            lift.updatePosition(gamepad1.dpad_up, gamepad1.dpad_down);
            telemetry.update();
        }
    }
}
