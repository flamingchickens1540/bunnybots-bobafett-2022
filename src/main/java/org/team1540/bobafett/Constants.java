// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.team1540.bobafett;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class DriveConstants {
        public static final int LEFT_FRONT = 1;
        public static final int RIGHT_FRONT = 2;
        public static final int LEFT_REAR = 3;
        public static final int RIGHT_REAR = 4;
    }

    public static final class ElevatorConstants {
        public static final int MOTOR_ID = 5;
        public static final int TOP_LIMIT_SWITCH_ID = 0;
        public static final int BOTTOM_LIMIT_SWITCH_ID = 1;
    }

    public static final class ClawConstants {
        public static final int SOLENOID_CHANNEL = 4;
    }

    public static final class VisionConstants{
        public static final String CAMERA_NAME = "Photonvision";
        public static final double KP = 0.02;
        public static final double KI = 0;
        public static final double KD = 0;
    }
}
