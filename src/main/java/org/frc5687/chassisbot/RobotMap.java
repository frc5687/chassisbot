package org.frc5687.chassisbot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    /**
     * Drive Train ports
     */
    public static class Drive {
        public static final int LEFT_MOTOR_FRONT = 0;
        public static final int LEFT_MOTOR_REAR = 1;
        public static final int RIGHT_MOTOR_FRONT = 2;
        public static final int RIGHT_MOTOR_REAR = 3;

        public static final int PDP_LEFT_MOTOR_FRONT = 14;
        public static final int PDP_LEFT_MOTOR_REAR = 15;
        public static final int PDP_RIGHT_MOTOR_FRONT = 12;
        public static final int PDP_RIGHT_MOTOR_REAR = 13;

        // Encoder channel ports as of 03/02, left reversed with right
        public static final int LEFT_ENCODER_CHANNEL_A = 6;
        public static final int LEFT_ENCODER_CHANNEL_B = 7;
        public static final int RIGHT_ENCODER_CHANNEL_A = 8;
        public static final int RIGHT_ENCODER_CHANNEL_B = 9;
    }


    /**
     * Intake ports
     */
    public static class Intake {
        // Main Intake
        public static final int INTAKE_MOTOR = 9;
        public static final int INFRARED_SENSOR = 3;
    }

    public static class GearHandler {
        public static final int MOTOR_PORT = 4;
        public static final int HOME_SENSOR_PORT = 4;
        public static final int EXTENSION_SENSOR_PORT = 5;
    }
    /**
     * Lights ports
     */
    public static class Lights {
        public static final int RINGLIGHT = 18; // DIO ports are not consecutive!
    }

    /**
     * Camera ports
     */
    public static class Cameras {
        public static final int main = 0;
    }

    public static class Pneumatics {
        public static final int expandPort = 1;
        public static final int retractPort = 0;
    }

}
