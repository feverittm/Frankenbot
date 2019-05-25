/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 * Add your docs here.
 */
public class LimeLight {

    public double x = 0, y = 0;
    public boolean hasTarget;

    NetworkTable limeLightTable;
    boolean lightOn = false;

    public LimeLight() {
        limeLightTable = NetworkTableInstance.getDefault().getTable("limelight");
        // limeLightTable.getEntry("<value>").getDouble(0);
    }

    public void ApplyErrorCorrection() {
        double mod = 0.01;
        double forward = Robot.m_oi.GetLeftY();
        Robot.driveTrain.SetSpeed(forward + (x * mod), forward + (-x * mod));

        SmartDashboard.putNumber("Correction Value", x * mod);
    }

    public void getDat() {
        x = limeLightTable.getEntry("tx").getDouble(0);
        y = limeLightTable.getEntry("ty").getDouble(0);
        hasTarget = limeLightTable.getEntry("tv").getDouble(0) == 1 ? true : false;

        SmartDashboard.putBoolean("Is Valid", hasTarget);
        SmartDashboard.putNumber("Target X", x);
        SmartDashboard.putNumber("Target Y", y);
    }

    public int getLED() {
        return (int)limeLightTable.getEntry("ledMode").getDouble(0);
    }

    public void setLED(double a) {
        limeLightTable.getEntry("ledMode").setDouble(a);
    }

}
