/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  public static class LimeLight {
    public static String
      ledMode = "ledMode",
      cameraMode = "camMode",
      captureMode = "snapshot",
      streamSelection = "stream",
      targetX = "tx",
      targetY = "ty",
      targetArea = "ta",
      targetVisible = "tv";
  }

  public static class Buttons {
    public static class Logitech {
      public static int 
        Gamepad4 = 3,
        buttonA = 2,
        buttonB = 3,
        buttonX = 1,
        buttonY = 4,
        buttonLeftThumbstick = 11,
        buttonRightThumbstick = 12,
        leftJoystickHatAngle = 270,
        centerJoystickHatAngle = 0,
        rightJoystickHatAngle = 90,
        leftXAxis = 0,
        leftYAxis = 1;
      }

    public static int

      GamePad1 = 0,
      GamePad3 = 2, 
      buttonA = 1,
      buttonB = 2,
      buttonX = 3,            
      buttonY = 4,
      buttonLeftShoulder = 5,
      buttonRightShoulder = 6,
      buttonLeftThumbstick = 9,
      buttonRightThumbstick = 10,
      buttonBack = 7,
      buttonStart = 8,

      //AXIS PORTS
      leftXAxis = 0,
      leftYAxis = 1,
      rightXAxis = 4,
      rightYAxis = 5,
      buttonRightTrigger = 3,
      buttonLeftTrigger = 2,

      end_of_buttons;
  }

  public static class Ports {
    public static final SerialPort.Port AHRS = SerialPort.Port.kUSB;
    public static int
      //DRIVETRAIN TALON PORTS
      leftTalon = 4,
      rightTalon = 1,
      leftVictor1 = 5,
      rightVictor1 = 2,

      //BALL Shooter - Gatherer
      ballLeftMotor = 3,
      ballRightMotor = 6,

      //ARM PORTS
      armTalon = 23,

      // placeholder so we can always just end with commas :-)
      end_of_ports = 999;
  }

  public static class Values {
    public static int 
      ticksPerRev = 4096; // Protobot
      //PDriveToDistance PID Variables
    public static double
      driveDistanceP = .003, //placeholders
      driveDistanceI = 0.0,
      driveDistanceD = 0.0,
      protobotTickPerFoot= 2449,

      // PDriveToAngle PID Variables
      driveAngleP = .007, //placeholders
      driveAngleI = 0.0,
      driveAngleD = 0.0,

      wheelDiameter = 6.0,
      inchesPerTick = (wheelDiameter*Math.PI)/4096, //inches per encoder tick
      ticksPerFoot = ((49152/(wheelDiameter*Math.PI)))*.9, //3940, //encoder ticks per foot

      // Arm and Elevator Values
      armPidP = 0.007, //0.005
      armPidI = 0,
      armPidD = 0.00, // 0.001
      armPidK = 0,
      armMaxPidF = 0.0, // 0.002

      // Camera values
      leftAngleInDegrees = 75,
      rightAngleInDegrees = 115,
      
      //PathFnder values
      pf_timestep = 0.02,
      pf_max_vel = 2.5, // max velocity in m/sec.
      pf_max_acc = 6.79,
      pf_max_jerk = 60,
      pf_Kp = 0.001,
      pf_Ki = 0.0,
      pf_Kd = 0.06,
      pf_Kv = (1/pf_max_vel),
      pf_Ka = 0.0,
      //pf_Kt = 0.35,

      robotLength = 0.75, //in inches (includes bumpers)
      robotWidth = 0.8,	
      robotWheelBase = 0.62, // inches or 2.5ft or 0.6 meters.  Use 0.0254 meters/in or 39.37in/m
      robotWheelDia = 0.15;// Javadocs requests that this is in meters not feet-> 6/12; // remember all pf variables are in ft.  Need to convert when used.
      
    public static boolean
      pf_path_ready = false;
  }

  public static class ElevatorHeights {
    //in inches
    public static int
      elevatorMiddleHeight = 28360,

      elevatorBackTopHatchHeight = 0, //impossible
      elevatorBackMiddleHatchHeight = 0, //impossible
      elevatorBackBottomHatchHeight = 0, //impossible
      elevatorBackShipHatchHeight = 8900, //impossible

      elevatorBackTopCargoHeight = 52400, // probably higher
      elevatorBackMiddleCargoHeight = 34400,
      elevatorBackBottomCargoHeight = 9100,
      elevatorBackShipCargoHeight = 24650,

      elevatorFrontTopHatchHeight = 52317, // probably higher
      elevatorFrontMiddleHatchHeight = 28360,
      elevatorFrontBottomHatchHeight = 0,
      elevatorFrontShipHatchHeight = 1520,

      elevatorFrontTopCargoHeight = 52400,
      elevatorFrontMiddleCargoHeight = 45360,
      elevatorFrontBottomCargoHeight = 22200,
      elevatorFrontShipCargoHeight = 38125,

      elevatorCollectCargoHeight = 0;

      public static int[]
      elevatorFrontHatchHeightArray = {
        elevatorFrontTopHatchHeight, 
        elevatorFrontMiddleHatchHeight, 
        elevatorFrontBottomHatchHeight 
      },
    
      elevatorBackHatchHeightArray = {
        elevatorBackTopHatchHeight, 
        elevatorBackMiddleHatchHeight,
        elevatorBackBottomHatchHeight
      },

      elevatorFrontCargoHeightArray = {
        elevatorFrontTopCargoHeight,
        elevatorFrontMiddleCargoHeight,
        elevatorFrontBottomCargoHeight
      },

      elevatorBackCargoHeightArray = {
        elevatorBackTopCargoHeight,
        elevatorBackMiddleCargoHeight,
        elevatorBackBottomCargoHeight
      };
  }
}
