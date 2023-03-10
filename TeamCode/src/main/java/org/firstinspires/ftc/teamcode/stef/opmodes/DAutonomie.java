package org.firstinspires.ftc.teamcode.stef.opmodes;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.stef.resurse.SHardware;
import org.firstinspires.ftc.teamcode.stef.resurse.drives.Brat;
import org.firstinspires.ftc.teamcode.stef.resurse.drives.Intake;
import org.firstinspires.ftc.teamcode.stef.resurse.drives.Lift;
import org.firstinspires.ftc.teamcode.stef.resurse.tag.TagBase;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous( name = "AutonomieDreapta" )
public class DAutonomie extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SHardware.init(this, true);
        Lift.init();
        Intake.init();
        Brat.init();
        TagBase.init(this);

        Intake.setInchis(true);

        ElapsedTime et = new ElapsedTime();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(new Pose2d(31.22, -62, Math.toRadians(90)));

        TrajectorySequence TraiectorieStanga = drive.trajectorySequenceBuilder(new Pose2d(31.22, -61, Math.toRadians(90)))
                //Porneste
                .forward(2.5)


                .addDisplacementMarker( () -> {
                   Intake.setInchis(false);
                   Intake.loop(this);
                   Lift.setLiftLevel(3);
                })

                //Pleaca spre mijloc
                .strafeLeft(18)

                 .addDisplacementMarker(()->{
                Brat.input(true);
                Brat.loop();
                })

//                 Mege la pilon
                .lineTo(new Vector2d(12, -30))

                .splineTo(new Vector2d(18.7, -9.8), Math.toRadians(60) )



                .addTemporalMarker(() ->{
                    Intake.setInchis(true);
                    Intake.loop(this);

                Lift.setLiftLevel(10);
                })
                .waitSeconds(0.5)

                //Se aliniaza cu turnul de conuri
                .back(4)
                .turn(Math.toRadians(-60))
                .splineTo(new Vector2d(60, -14.5), Math.toRadians(0))

                //Porneste spre pilon
                .addTemporalMarker(()->{
                    Intake.setInchis(false);
                    Intake.loop(this);
                })
                .waitSeconds(0.3)
                .addTemporalMarker(() ->{
                Lift.setLiftLevel(3);
                })
                .waitSeconds(0.5)

                .setReversed(true)

                .lineTo(new Vector2d(49, -15))
                .addDisplacementMarker( ()->{
                    Intake.setRotire(false);
                    Intake.loop(this);

                    Brat.input(false);
                    Brat.loop();
                })

                .splineTo(new Vector2d(31, -13.2), Math.toRadians(120))
                .setReversed(false)

                .addTemporalMarker(() ->{

                    Intake.setInchis(true);
                    Intake.loop(this);
                })
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    Intake.setInchis(false);
                    Intake.loop(this);
                })
                .waitSeconds(0.5)
                .addDisplacementMarker(() ->{
                    Intake.setRotire(true);
                    Intake.loop(this);

                    Brat.input(true);
                    Brat.loop();

                    Lift.setLiftLevel(20);
                })

                //Se intoarce
                .splineTo(new Vector2d(48, -14), Math.toRadians(0))
                .addDisplacementMarker(() ->{
                    Intake.setInchis(true);
                    Intake.loop(this);
                })
                .splineTo(new Vector2d( 60, -14.5), Math.toRadians(0))
                .UNSTABLE_addTemporalMarkerOffset(0.5, () ->{
                    Intake.setInchis(false);
                    Intake.loop(this);
                })
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    Lift.setLiftLevel(3);
                })

                //Porneste spa puna con

                //  TODO: PUNE CON
                .waitSeconds(0.5)
                .setReversed(true)
                .lineTo(new Vector2d(49, -15))
                .addDisplacementMarker( ()->{
                        Intake.setRotire(false);
                        Intake.loop(this);

                        Brat.input(false);
                        Brat.loop();
                })
                .splineTo(new Vector2d(31, -13.2), Math.toRadians(120))
                .setReversed(false)

                .build();


        TrajectorySequence first = drive.trajectorySequenceBuilder(TraiectorieStanga.end())

                .addTemporalMarker(() ->{

                    Intake.setInchis(true);
                    Intake.loop(this);
                })
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    Intake.setInchis(false);
                    Intake.loop(this);
                })
                .waitSeconds(0.5)
                .addDisplacementMarker(() ->{
                    Intake.setRotire(true);
                    Intake.loop(this);

                    Brat.input(true);
                    Brat.loop();

                    Lift.setLiftLevel(0);
                })
                .splineTo(new Vector2d(-35, -16), Math.toRadians(180))
                .lineTo(new Vector2d(-60, -16))
                .build();

        TrajectorySequence second = drive.trajectorySequenceBuilder(TraiectorieStanga.end())

                .addTemporalMarker(() ->{

                    Intake.setInchis(true);
                    Intake.loop(this);
                })
                .waitSeconds(1)
                .addTemporalMarker(()->{
                    Intake.setInchis(false);
                    Intake.loop(this);
                })
                .waitSeconds(0.5)
                .addDisplacementMarker(() ->{
                    Intake.setRotire(true);
                    Intake.loop(this);

                    Brat.input(true);
                    Brat.loop();

                    Lift.setLiftLevel(0);
                })
                .splineTo(new Vector2d(-35, -16), Math.toRadians(180))

                .waitSeconds(10)

                .build();

        TrajectorySequence third = drive.trajectorySequenceBuilder(TraiectorieStanga.end())

                .lineTo(new Vector2d(-12,-16))
                .build();


        while (opModeInInit()){
            TagBase.update(this);
        }



        waitForStart();

        et.reset();

        if (opModeIsActive()){



            drive.followTrajectorySequence(TraiectorieStanga);

           /* drive.followTrajectorySequence(principala2);*/


            switch (TagBase.tag()){
                case 1:
                    drive.followTrajectorySequence(first);
                    break;
                case 2:
                    drive.followTrajectorySequence(second);
                    break;
                case 3:
                    drive.followTrajectorySequence(third);
                    break;

            }

            telemetry.addData("sec: ", et.seconds());
            telemetry.update();
        }


        if(isStopRequested()) {
            TagBase.stop();
            
        }
    }
}
