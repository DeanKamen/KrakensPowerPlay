package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.*;

@TeleOp
public class TestComponent extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Lift lift = Lift.init(hardwareMap, telemetry);
        Hook hook = Hook.init(hardwareMap, telemetry);

        boolean noBumper = true;

        telemetry.addData ("Runtime:", "%f.1 seconds", this.getRuntime());
        telemetry.addData ("Current Press:", "N/A");
        telemetry.update();


        waitForStart();
        getRuntime();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.right_bumper && noBumper) {
                telemetry.addData("Current Press:","New Right Bumper");
                hook.bumpUp();
                noBumper = false;
            } else if (gamepad1.left_bumper && noBumper) {
                telemetry.addData("Current Press:","New Left Bumper");
                hook.bumpDown();
                noBumper = false;
            } else {
                telemetry.addData("Current Press:","N/A");
                noBumper = true;
            }

            telemetry.addLine ("In while loop.");
            /*
            if (gamepad1.dpad_up) {
                telemetry.addLine ("USER PUSHED DPAD UP");
                lift.moveUp();
            }

            if (gamepad1.dpad_down) {
                telemetry.addLine("USER PUSHED DPAD DOWN");
                lift.moveDown();
            }
            */
            lift.move_conditionally(gamepad1.dpad_up,gamepad1.dpad_down);
            telemetry.update();
            hook.toggleHook(gamepad1.cross);
        }
    }
}
