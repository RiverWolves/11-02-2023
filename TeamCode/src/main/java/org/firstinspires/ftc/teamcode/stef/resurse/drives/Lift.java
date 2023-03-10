package org.firstinspires.ftc.teamcode.stef.resurse.drives;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.stef.resurse.SHardware;

public class Lift {

    public static final int LIMITARE_SUS_LIFT = 6800,
                            LIMITARE_JOS_LIFT = 0,
                            NIVEL_0 = 200,
                            NIVEL_1 = 2500,
                            NIVEL_2 = 4400,
                            NIVEL_3 = 6800,
                            NIVEL_CON1 = 1040,
                            NIVEL_CON2 = 725;

    private static float y = 0;
    private static float putere = 1;
    private static float pow_nivel = 1;
    public static float pozitie_lift = 0;
    public static int target = 1;

    private static DcMotor lift1 = null,
                           lift2 = null;

    public static boolean enivel = false,
                          nivel0 = false,
                          nivel1 = false,
                          nivel2 = false,
                          nivel3 = false;

    private static Util util_lift = new Util();

    public static  void init() {
        if (!SHardware.initializat) return;

        lift1 = SHardware.lift1;
        lift2 = SHardware.lift2;

        lift1.setTargetPosition(0);
        lift2.setTargetPosition(0);
        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public static void loop(OpMode opMode) {
        float input = y;
        pozitie_lift = lift1.getCurrentPosition();

        if (input > 0.3f && pozitie_lift < LIMITARE_SUS_LIFT) {
            putere = 1;
        }
        else if (input < -0.3f && pozitie_lift > LIMITARE_JOS_LIFT) {
           putere = -1;
        }
        else {
            putere = 0;
        }

        putere = util_lift.fineControl(opMode.gamepad2.left_bumper, putere);

        if (input != 0) {
            lift1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lift2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            lift1.setPower(putere);
            lift2.setPower(putere);
        }


        if (input > 0.3f && pozitie_lift >= LIMITARE_SUS_LIFT){
            util_lift.rumble(opMode.gamepad2);
        }
        else if (input < -0.3f && pozitie_lift <= LIMITARE_JOS_LIFT){
            util_lift.rumble(opMode.gamepad2);
        }
    }

    public static void nivelLoop(OpMode opMode) {
        int poz_lift = lift1.getCurrentPosition();

        if (y == 0) {
            if (nivel0) {
                target = NIVEL_0;
                pow_nivel = 1;
            }
            else if (nivel1) {
                target = NIVEL_1;
                pow_nivel = 1;
            }
            else if (nivel2) {
                target = NIVEL_2;
                pow_nivel = 1;
            }
            else if (nivel3) {
                target = NIVEL_3;
                pow_nivel = 1;
            }
        }
        else {
            target = 0;
            pow_nivel = 0;
        }

        lift1.setTargetPosition(target);
        lift2.setTargetPosition(target);

        if (y == 0) {
            lift1.setPower(pow_nivel);
            lift2.setPower(pow_nivel);

            lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        if (poz_lift <= target+10 && poz_lift >= target-10){
            pow_nivel = 0;
            enivel = true;
        }
        else {
            enivel = false;
        }

        if (poz_lift <= target+10 && poz_lift >= target-10 && lift1.getPower() != 0){
            util_lift.rumble(opMode.gamepad2);
        }
    }

    public static void setLiftLevel(int nivel) {
        int target_position = 0, lift_power_level = 1;

        switch (nivel) {
            case 0:
                target_position = LIMITARE_JOS_LIFT;
                break;

            case 1:
                target_position = NIVEL_1;
                break;

            case 2:
                target_position = NIVEL_2;
                break;

            case 3:
                target_position = NIVEL_3;
                break;

            case 10 :
                target_position = NIVEL_CON1;
                break;

            case 20 :
                target_position = NIVEL_CON2;
                break;
        }

        lift1.setTargetPosition(target_position);
        lift2.setTargetPosition(target_position);

        lift1.setPower(lift_power_level);
        lift2.setPower(lift_power_level);

        lift1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public static void setVal(float stick, boolean bool0, boolean bool1, boolean bool2, boolean bool3) {
        nivel0 = bool0;
        nivel1 = bool1;
        nivel2 = bool2;
        nivel3 = bool3;
        y = stick;
    }

    public static float getPoz() {
        return pozitie_lift;
    }
}

