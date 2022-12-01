package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Hook {

    Servo servoHook;
    double hookServoPosition = 0.0;
    boolean subsystemExists = false;
    Telemetry telemetry;

    public static Hook init (HardwareMap hardwareMap, Telemetry telemetry) {
        Hook hook = new Hook();
        hook.telemetry = telemetry;

        try {
            hook.servoHook = hardwareMap.get(Servo.class, "cone_hook");
            hook.servoHook.setDirection(Servo.Direction.REVERSE);
            hook.subsystemExists = true;
            telemetry.addLine("Hook Subsystem: does exist and is initialized.");
        } catch (Exception e) {
            hook.subsystemExists = false;
            telemetry.addLine("Hook Subsystem: does not exist and is not initialized.");
        }
        telemetry.update();
        return hook;
    }

    public void bumpUp () {
        hookServoPosition += 0.01;
        telemetry.addLine("Hook Subsystem: bumping up to "+hookServoPosition);
        servoHook.setPosition(hookServoPosition);
    }

    public void bumpDown () {
        hookServoPosition -= 0.01;
        telemetry.addLine("Hook Subsystem: bumping down to "+hookServoPosition);
        servoHook.setPosition(hookServoPosition);
    }
}
