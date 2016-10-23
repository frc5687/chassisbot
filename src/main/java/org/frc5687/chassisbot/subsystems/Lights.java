package org.frc5687.chassisbot.subsystems;

import org.frc5687.chassisbot.RobotMap;
import org.frc5687.chassisbot.utils.LEDSwitch;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem to control lights for vision tracking and shooter aid
 * @author wil
 */
public class Lights extends Subsystem {
    private LEDSwitch ringLight;

    public Lights() {
        ringLight = new LEDSwitch(RobotMap.Lights.RINGLIGHT);
    }

    @Override
    protected void initDefaultCommand() {
    }

    public boolean getRingLight() {
        return ringLight.get();
    }

    public void turnRingLightOn() {
        ringLight.set(true);
    }

    public void turnRingLightOff() { ringLight.set(false); }

    public void toggleRingLight() {
        ringLight.toggle();
    }

    public void updateDashboard() {
        SmartDashboard.putBoolean("lights/ringlight", ringLight.get());
    }
}
