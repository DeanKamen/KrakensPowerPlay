package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Drive;

@TeleOp
public class MecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        // Get the subsystems initialized
        Drive drive = Drive.init(hardwareMap, telemetry);


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            // Get all of the current gamepad inputs.
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;
            drive.setBoost(gamepad1.left_stick_button);
            drive.setBrake(gamepad1.left_trigger);
            drive.setClawState(gamepad1.right_trigger > 0.01);
            drive.setHookState(gamepad1.right_bumper);
            // Send the inputs to the subsystems
            drive.setMotorSpeeds(x, y, rx);
        }
    }
}
