package org.frc5687.chassisbot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.frc5687.chassisbot.commands.DoNothing;
import org.frc5687.chassisbot.commands.ReachDefense;
import org.frc5687.chassisbot.commands.TraverseLowBar;
import org.frc5687.chassisbot.commands.TraverseLowBarAndBowl;
import org.frc5687.chassisbot.subsystems.*;
import org.frc5687.chassisbot.utils.*;

/*
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    /**
     * Represents the navX inertial measurement unit, used for measuring robot movement and orientation.
     */
    public static AHRS imu;

    /**
     * Represents the robot's drive chassis
     */
    public static DriveTrain driveTrain;


    /**
     * Represents the robot's boulder intake
     */
    public static Intake intake;


    /**
     * Represents the operator interface/ controls
     */
    public static OI oi;

    /**
     * Provides static access to the singleton Robot instance
     */
    public static Robot robot;

    /**
     * Represents the robot's lights
     */
    public static Lights lights;

    private CameraServer cameraServer;

    private SendableChooser autoChooser;

    private Command autonomousCommand;

    public Robot() {

    }

    public void robotInit() {
        robot = this;
        driveTrain = new DriveTrain();
        intake = new Intake();
        lights = new Lights();

        // Report commit info to dashboard and driver station
        SmartDashboard.putString("Git Info", Reader.gitInfo);

        try {
            // Try to connect to the navX imu.
            imu = new AHRS(SPI.Port.kMXP);

            // Report to both the logs and the dashboard.  We may not want to keep this permanently, but it's helpful for our initial testing.
            DriverStation.reportError(String.format("Connected to navX MXP with FirmwareVersion %1$s", imu.getFirmwareVersion()), false);
            SmartDashboard.putString("FirmwareVersion", imu.getFirmwareVersion());
        } catch (Exception ex) {
            // If there are any errors, null out the imu reference and report the error both to the logs and the dashboard.
            SmartDashboard.putString("FirmwareVersion", "navX not connected");
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
            imu = null;
        }

        // Commands need to be instantiated AFTER the subsystems.  Since the OI constructor instantiates several commands, we need it to be instantiated last.
        oi = new OI();

        DriverStation.reportError("Starting cameraserver on " + RobotMap.Cameras.main, false);
        cameraServer = CameraServer.getInstance();
        cameraServer.setQuality(50);
        cameraServer.startAutomaticCapture(RobotMap.Cameras.main);

        autoChooser = new SendableChooser();
        autoChooser.addDefault("Do Nothing", new DoNothing());
        autoChooser.addObject("Reach Defense", new ReachDefense());
        autoChooser.addObject("Traverse LowBar", new TraverseLowBar());
        autoChooser.addObject("Traverse LowBar and Bowl", new TraverseLowBarAndBowl());
        SmartDashboard.putData("Autonomous mode", autoChooser);

    }

    public void disabledInit() {
    }

    public void disabledPeriodic() {
        updateDashboard();
    }


    public void autonomousInit() {
        autoInitUsingButtons();
        if (autonomousCommand!=null) {
            autonomousCommand.start();
        }
    }

    private void autoInitUsingChooser() {
        autonomousCommand = (Command) autoChooser.getSelected();
    }

    private void autoInitUsingButtons() {
        if (SmartDashboard.getBoolean("DB/Button 1", false)) {
            autonomousCommand = new ReachDefense();
        } else if (SmartDashboard.getBoolean("DB/Button 2", false)) {
            autonomousCommand = new TraverseLowBar();
        } else if (SmartDashboard.getBoolean("DB/Button 3", false)) {
            autonomousCommand = new TraverseLowBarAndBowl();
        }
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        updateDashboard();
    }

    public void teleopInit() {
        if (autonomousCommand != null) autonomousCommand.cancel();
        driveTrain.setSafeMode(true);
        imu.reset();
    }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        updateDashboard();
        oi.endRumble();
    }

    public void testPeriodic() {
        LiveWindow.run();
    }

    protected void updateDashboard() {
        lights.updateDashboard();
        intake.updateDashboard();
        driveTrain.updateDashboard();
        sendIMUData();
    }
    protected void sendIMUData() {
        if (imu==null) {
            // If we can't find the imu, report that to the dashboard and return.
            SmartDashboard.putString("FirmwareVersion",      "navX not connected");
            return;
        }

        // Display 6-axis Processed Angle Data
        SmartDashboard.putBoolean(  "IMU/Connected",        imu.isConnected());
        SmartDashboard.putBoolean(  "IMU/IsCalibrating",    imu.isCalibrating());
        SmartDashboard.putNumber(   "IMU/Yaw",              imu.getYaw());
        SmartDashboard.putNumber(   "IMU/Pitch",            imu.getPitch());
        SmartDashboard.putNumber(   "IMU/Roll",             imu.getRoll());

        // Display tilt-corrected, Magnetometer-based heading (requires magnetometer calibration to be useful)
        SmartDashboard.putNumber(   "IMU/CompassHeading",   imu.getCompassHeading());

        // Display 9-axis Heading (requires magnetometer calibration to be useful)
        SmartDashboard.putNumber(   "IMU/FusedHeading",     imu.getFusedHeading());


        // These functions are compatible w/the WPI Gyro Class, providing a simple
        // path for upgrading from the Kit-of-Parts gyro to the navx MXP
        SmartDashboard.putNumber(   "IMU/TotalYaw",         imu.getAngle());
        SmartDashboard.putNumber(   "IMU/YawRateDPS",       imu.getRate());

        // Display Processed Acceleration Data (Linear Acceleration, Motion Detect)
        SmartDashboard.putNumber(   "IMU/Accel_X",          imu.getWorldLinearAccelX());
        SmartDashboard.putNumber(   "IMU/Accel_Y",          imu.getWorldLinearAccelY());
        SmartDashboard.putBoolean(  "IMU/IsMoving",         imu.isMoving());
        SmartDashboard.putBoolean(  "IMU/IsRotating",       imu.isRotating());

        // Display estimates of velocity/displacement.  Note that these values are not expected to be accurate enough
        // for estimating robot position on a FIRST FRC Robotics Field, due to accelerometer noise and the compounding
        // of these errors due to single (velocity) integration and especially double (displacement) integration.
        SmartDashboard.putNumber(   "IMU/Velocity_X",           imu.getVelocityX());
        SmartDashboard.putNumber(   "IMU/Velocity_Y",           imu.getVelocityY());
        SmartDashboard.putNumber(   "IMU/Displacement_X",       imu.getDisplacementX());
        SmartDashboard.putNumber(   "IMU/Displacement_Y",       imu.getDisplacementY());

        // Connectivity Debugging Support
        SmartDashboard.putNumber(   "IMU/Byte_Count",       imu.getByteCount());
        SmartDashboard.putNumber(   "IMU/Byte_Count",       imu.getByteCount());
        SmartDashboard.putNumber(   "IMU/Update_Count",     imu.getUpdateCount());
    }

}