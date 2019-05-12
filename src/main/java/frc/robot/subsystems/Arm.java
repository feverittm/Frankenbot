/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.MoveArm;
import frc.robot.commands.MoveArmDown;
import frc.robot.commands.MoveArmUp;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private WPI_TalonSRX armTalon;

  public Arm(){
    armTalon = new WPI_TalonSRX(RobotMap.armTalon);

		armTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		armTalon.setSensorPhase(true);
		armTalon.setNeutralMode(NeutralMode.Coast);
		
		/* set the peak, nominal outputs */
		armTalon.configNominalOutputForward(0, 10);
		armTalon.configNominalOutputReverse(0, 10);
		//armTalon.configPeakOutputForward(1, 10); //Use for PB
		//armTalon.configPeakOutputReverse(-1, 10); //Use for PB
		armTalon.configPeakOutputForward(0.6, 10);  //Use for extrasensitive CB
		armTalon.configPeakOutputReverse(-0.6, 10); //Use for extrasensitive CB
		
		armTalon.enableCurrentLimit(true);
		armTalon.configPeakCurrentLimit(40, 10);
		armTalon.configPeakCurrentDuration(100, 10);
    armTalon.configContinuousCurrentLimit(30, 10);
    
		//.25, 10);
		armTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 40, 10);
		//armTalon.configOpenloopRamp(0.25, 10);
		
		/* set closed loop gains in slot0 */
		armTalon.config_kF(0, 0.1097, 10);
		armTalon.config_kP(0, 0.113333, 10);
		armTalon.config_kI(0, 0, 10);
		armTalon.config_kD(0, 0, 10);	
  }

  public void SetPosition(){
    armTalon.set(ControlMode.Position, 0);
  }

  public void ArmSpeed(double armSpeed){
    armTalon.set(armSpeed);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new MoveArm());
	}
}
