package org.firstinspires.ftc.teamcode.ThugActions;


import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class KeirsRRParts {

    //ENUMS!!!
    public enum Partlist{
        DOOR,
        Shooter,
        Intake,

    }


    public static class Intake {
        private final DcMotor intakeMotor;
        private Telemetry telemetry;
        private double nominalVoltage = 12.5;
        private double desiredPower = 1;
        private double correctedPower;
        private double batteryVoltage;

        public Intake(HardwareMap hardwareMap) {
            VoltageSensor battery = hardwareMap.voltageSensor.iterator().next();
            intakeMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
            batteryVoltage = battery.getVoltage();
        }


        public class TurnOnIntake implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                correctedPower = desiredPower * (nominalVoltage / Math.max(batteryVoltage, 1.0));
                correctedPower = Math.max(-1.0, Math.min(correctedPower, 1.0));
                intakeMotor.setPower(correctedPower);
                return false;
            }
        }

        public Action turnOnIntake() {
            return new TurnOnIntake();
        }

        public class TurnOffIntake implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                intakeMotor.setPower(0);
                return false;
            }
        }

        public Action turnOffIntake() {
            return new TurnOffIntake();
        }

    }


    public static class Outtake {
        private final DcMotor shooterMotor2;
        private Telemetry telemetry;

        public Outtake(HardwareMap hardwareMap, Telemetry telemetry) {
            shooterMotor2 = hardwareMap.get(DcMotor.class, "shooterMotor2");
            this.telemetry = telemetry;
        }

        public class TurnOnShooter implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                shooterMotor2.setPower(0.9);
                return false;
            }
        }

        public Action turnOnShooter() {
            return new TurnOnShooter();
        }

        public class TurnOffShooter implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                shooterMotor2.setPower(0);
                return false;
            }
        }

        public Action turnOffShooter() {
            return new TurnOnShooter();
        }

    }

    public static class Door {
        private final Servo doorToucher;
        private Telemetry telemetry;

        public Door(HardwareMap hardwareMap, Telemetry telemetry) {
            doorToucher = hardwareMap.get(Servo.class, "door");
            this.telemetry = telemetry;
        }

        public class OpenDoor implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                doorToucher.setPosition(0);
                return false;
            }
        }

        public Action openDoor() {
            return new OpenDoor();
        }

        public class CloseDoor implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                doorToucher.setPosition(0.5);
                return false;
            }
        }

        public Action closeDoor() {
            return new CloseDoor();
        }

    }


}
