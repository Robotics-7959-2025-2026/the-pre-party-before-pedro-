    package org.firstinspires.ftc.teamcode.Teleop;

    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.DcMotorEx;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    import com.qualcomm.robotcore.hardware.Gamepad;
    import com.qualcomm.robotcore.hardware.Servo;
    import com.qualcomm.robotcore.hardware.VoltageSensor;

    @TeleOp(name = "7959 Teleop")
    public class OfficialTeleop extends LinearOpMode {
        private DcMotor lfMotor = null;
        private DcMotor rfMotor = null;
        private DcMotor lbMotor = null;
        private DcMotor rbMotor = null;
        private DcMotor shooterMotor3;
        private DcMotor transfer;
        private double nominalVoltage = 12.5;
        private double desiredPower = 1;
        private double batteryVoltage, correctedPower;
        private DcMotor intakeMotor, shooterMotor2 = null;
        private Servo doorToucher = null;

        private double servoStart = 0.0;

        private double ctrlPow = 2.0;

        private double rx;
        private boolean doorToggle = false;

        @Override
        public void runOpMode() {
            Gamepad currentGamepad1 = new Gamepad();
            Gamepad previousGamepad1 = new Gamepad();

            lfMotor = hardwareMap.get(DcMotorEx.class, "front_left_drive");
            rfMotor = hardwareMap.get(DcMotorEx.class, "front_right_drive");
            lbMotor = hardwareMap.get(DcMotorEx.class, "back_left_drive");
            rbMotor = hardwareMap.get(DcMotorEx.class, "back_right_drive");
            intakeMotor = hardwareMap.get(DcMotor.class, "shooterMotor");
            shooterMotor2 = hardwareMap.get(DcMotor.class, "shooterMotor2");
            shooterMotor3 = hardwareMap.get(DcMotor.class, "shooterMotor3");


            transfer = hardwareMap.get(DcMotor.class, "transfer");

            doorToucher = hardwareMap.get(Servo.class, "door");

            lfMotor.setDirection(DcMotor.Direction.FORWARD);
            rfMotor.setDirection(DcMotor.Direction.REVERSE);
            lbMotor.setDirection(DcMotor.Direction.FORWARD);
            rbMotor.setDirection(DcMotor.Direction.REVERSE);

            intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            shooterMotor2.setDirection(DcMotorSimple.Direction.FORWARD);

            intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            telemetry.addData("Status", "Initialized");
            telemetry.addData("Debug", "Balulu3");
            telemetry.update();

            VoltageSensor battery = hardwareMap.voltageSensor.iterator().next();
            batteryVoltage = battery.getVoltage();


            waitForStart();

            while (opModeIsActive()) {
                telemetry.update();
                previousGamepad1.copy(currentGamepad1);
                currentGamepad1.copy(gamepad1);

                rx = (gamepad1.right_stick_x / 4) * 3 ;

                rfMotor.setPower(Math.pow(gamepad1.left_stick_y + gamepad1.left_stick_x + rx, ctrlPow) * Math.signum(gamepad1.left_stick_y + gamepad1.left_stick_x + rx));
                lfMotor.setPower(Math.pow(gamepad1.left_stick_y - gamepad1.left_stick_x - rx, ctrlPow) * Math.signum(gamepad1.left_stick_y - gamepad1.left_stick_x - rx));
                rbMotor.setPower(Math.pow(gamepad1.left_stick_y - gamepad1.left_stick_x + rx, ctrlPow) * Math.signum(gamepad1.left_stick_y - gamepad1.left_stick_x + rx));
                lbMotor.setPower(Math.pow(gamepad1.left_stick_y + gamepad1.left_stick_x  - rx, ctrlPow) * Math.signum(gamepad1.left_stick_y + gamepad1.left_stick_x - rx));


                // Hold left bumper to spin, then press the right bumper to shoot
                if (gamepad1.left_bumper) {
                    correctedPower = desiredPower * (nominalVoltage / Math.max(batteryVoltage, 1.0));
                    correctedPower = Math.max(-1.0, Math.min(correctedPower, 1.0));
                    intakeMotor.setPower(correctedPower);
                } else {
                    intakeMotor.setPower(0.0);
                }

                if(gamepad1.right_bumper){
                    shooterMotor2.setPower(0.85);
                    transfer.setPower(1);
                    shooterMotor3.setPower(1);
                }else{
                    shooterMotor2.setPower(0);
                    transfer.setPower(0);
                    shooterMotor3.setPower(0);
                }



                if (currentGamepad1.x && !previousGamepad1.x) {
                    doorToggle = !doorToggle;
                }

                if(gamepad1.x){
                    if (doorToggle) {
                        doorToucher.setPosition(0.3);
                    }else{
                        doorToucher.setPosition(0);
                    }
                }

//
//                if(gamepad1.left_trigger > 0){
//                    intakeMotor.setPower(-5);
//                }else{
//                    intakeMotor.setPower(0);
//                }

            }
        }
    }