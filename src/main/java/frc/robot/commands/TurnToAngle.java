/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;

public class TurnToAngle extends PIDCommand {
  double whereGo, left, right, postion, error, speedModifier;
  PIDController turnController;

  static final double kP = 0.03;
  static final double kI = 0.00;
  static final double kD = 0.00;
  static final double kF = 0.00;

  static final double kToleranceDegrees = 2.0f;    
  static final double kTargetAngleDegrees = 90.0f;
  
  public TurnToAngle(double whereGo) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    super(kP, kI, kD);
    requires(Robot.driveTrain);
    this.getPIDController().setAbsoluteTolerance(kToleranceDegrees);
    this.getPIDController().setSetpoint(whereGo);
  }

	@Override
	protected double returnPIDInput() {
		return Robot.driveTrain.getAngle();
	}

	@Override
	protected void usePIDOutput(double speed) {
		// these may need to be flipped so that left is reversed.
		Robot.driveTrain.setPower(speed, -speed);
	}

	@Override
	protected boolean isFinished() {
		return getPIDController().onTarget();
	}
}
