package org.firstinspires.ftc.teamcode.DecodeOttoYay;


import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.ThugActions.KeirsRRParts.*;

@Config
@Autonomous(name = "12 Ball auto Red", group = "Autonomous")
public class NineBallRed extends LinearOpMode {

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-50, 50, Math.toRadians(130));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Door door = new Door(hardwareMap, telemetry);
        Intake intake = new Intake(hardwareMap);
        Outtake shooter = new Outtake(hardwareMap, telemetry);

        TrajectoryActionBuilder goToShootPose = drive.actionBuilder(initialPose)
                .splineToConstantHeading(new Vector2d(-26, 26), 0);

        TrajectoryActionBuilder goPickUpBalls = drive.actionBuilder(new Pose2d(-26, 26, Math.toRadians(135)))
                .splineToSplineHeading(new Pose2d(-10, 30, Math.toRadians(90)), 90)
                .splineToConstantHeading(new Vector2d(-10, 60), 0);

        TrajectoryActionBuilder goToShootPose2 = drive.actionBuilder(new Pose2d(-10, 60, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-26, 26, Math.toRadians(130)), 60);

        TrajectoryActionBuilder goPickUpBalls2 = drive.actionBuilder(new Pose2d(-26, 26, Math.toRadians(130)))
                .splineToLinearHeading(new Pose2d(12, 38, Math.toRadians(90)), 90)
                .splineToConstantHeading(new Vector2d(12, 60), 0);

        TrajectoryActionBuilder goToShootPose3 = drive.actionBuilder(new Pose2d(12,50, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-26, 26, Math.toRadians(130)), 60);

        TrajectoryActionBuilder goPickUpBalls3 = drive.actionBuilder(new Pose2d(-26, 26, Math.toRadians(130)))
                .splineToLinearHeading(new Pose2d(37.5, 38, Math.toRadians(90)), 90)
                .splineToConstantHeading(new Vector2d(37.5, 55), 0);

        TrajectoryActionBuilder goToShootPose4 = drive.actionBuilder(new Pose2d(40, 50, Math.toRadians(90)))
                .splineToSplineHeading(new Pose2d(-26, 26, Math.toRadians(130)), 45);

        // tightens the claw around the sample to start auton
        //Actions.runBlocking(outtake.closeOutClaw());
        Actions.runBlocking(door.closeDoor());

        waitForStart();

        if (isStopRequested()) return;

        Action goShoot = goToShootPose.build();
        Action goPickUpBallsA = goPickUpBalls.build();
        Action goShoot2 = goToShootPose2.build();
        Action goPickUpBalls2A = goPickUpBalls2.build();
        Action goShoot3 = goToShootPose3.build();
        Action goPickUpBalls3A = goPickUpBalls3.build();
        Action goShoot4 = goToShootPose4.build();
        //Action goDumpPreLoad = GoDumpPreLoad.build();
        //intakes
        Action TurnOffIntake = intake.turnOffIntake();
        Action TurnOnIntake = intake.turnOnIntake();

        Action TurnOnShooting = shooter.turnOnShooter();
        Action TurnOffShooting = shooter.turnOffShooter();

        //open na noor
        Action OpenDoor = door.openDoor();
        Action CloseDoor = door.closeDoor();


        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                TurnOnIntake,
                                TurnOnShooting
                        ),
                        goShoot,
                        OpenDoor,
                        new SleepAction(2),
                        CloseDoor,
                        new SleepAction(0.5),
                        goPickUpBallsA,
                        new SleepAction(1),
                        goShoot2,
                        OpenDoor,
                        new SleepAction(2),
                        CloseDoor,
                        new SleepAction(1),
                        goPickUpBalls2A,
                        new SleepAction(1),
                        goShoot3,
                        OpenDoor,
                        new SleepAction(2),
                        CloseDoor,
                        new SleepAction(1),
                        goPickUpBalls3A,
                        new SleepAction(1),
                        goShoot4,
                        OpenDoor,
                        new SleepAction(2),
                        CloseDoor,
                        new SleepAction(1)


                )
        );


    }
}
