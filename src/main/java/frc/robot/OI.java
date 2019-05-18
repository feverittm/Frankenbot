/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.MoveArmDown;
import frc.robot.commands.MoveArmUp;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //public JoystickButton armDown, armUp;
  private Joystick gamepad;

  public OI() {
    gamepad = new Joystick(0);
    //armDown = new JoystickButton(gamepad, RobotMap.buttonA);
    // buttonB = new JoystickButton(gamepad, 2);
    // buttonX = new JoystickButton(gamepad, 3);
    //armUp = new JoystickButton(gamepad, RobotMap.buttonY);

    //armUp.whileHeld(new MoveArmUp());
    //armDown.whileHeld(new MoveArmDown());
  }

  public double GetLeftY() {
    return -gamepad.getRawAxis(1);
  }

  public double GetRightY() {
    return -gamepad.getRawAxis(5);
  }

  public double GetLeftX() {
    return gamepad.getRawAxis(0);
  }

  public double GetRightX() {
    return gamepad.getRawAxis(4);
  }
  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}