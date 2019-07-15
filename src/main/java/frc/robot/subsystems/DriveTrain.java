package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.ArcadeDrive;
import frc.robot.misc.GearBox;
import frc.robot.misc.RoboMisc;

import static frc.robot.Robot.deltaTime;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

/**
 * This is our drivetrain. This year I split up the configuration for the
 * drivetrain into another class so this one isn't comically long. We have a few
 * different methods such as normal set volts and stop volts. Along with
 * SetVoltsDecel and SetPosition. NOTE: Grayson hates deceleration code but he
 * reeeealllly needs it. Also not sure if the decel code works so definetly
 * check that first. Make sure the dampRate doesn't need adjusting.
 */
public class DriveTrain extends Subsystem {

  public boolean decell = true;

  public double maxSpeed = 0.5;

  // Decell Data
  public double ramp = 0.25;
  private double prevY = 0;
  public boolean gyropresent = false;

  // GearBox class stores information for the motor controllers for one gearbox
  private final WPI_TalonSRX leftTalon, rightTalon;
  private final WPI_VictorSPX leftVictor1, rightVictor1;
  private AHRS gyro;

  private NetworkTable table;

  /**
   * A default constructor for the drivetrain to use from the robot program.
   */
  public DriveTrain() {
    // This uses the RoboMisc function standTalonSRXSetup(int, int, int, boolean) to
    // initialize a Talon and 2 slave victors
    this(RoboMisc.standTalonSRXSetup(RobotMap.Ports.leftTalon, RobotMap.Ports.leftVictor1, false),
        RoboMisc.standTalonSRXSetup(RobotMap.Ports.rightTalon, RobotMap.Ports.rightVictor1, true), null,
        NetworkTableInstance.getDefault().getTable("SmartDashboard"));
  }


  /**
   * Drivetrain constructor to use for testing purposes.
   * 
   * @param leftBox
   * @param rightBox
   * @param gyro
   * @param smartDashboardNetworkTable
   */
  public DriveTrain(GearBox leftBox, GearBox rightBox, AHRS gyro, NetworkTable smartDashboardNetworkTable) {

    // Grab the objects created by the RoboMisc function and store them in this
    // class
    leftTalon = leftBox.talon;
    rightTalon = rightBox.talon;
    leftVictor1 = leftBox.victor1;
    rightVictor1 = rightBox.victor1;
 
    initAHRS();
    resetEncoders();
    setCoast();

    table = smartDashboardNetworkTable;
  }

  public void initAHRS() {
    if (gyro == null) {
      try {
        this.gyro = new AHRS(RobotMap.Ports.AHRS);
        this.gyro.reset();
        this.gyro.zeroYaw();
      } catch (RuntimeException e) {
        System.out.println("DT- The gyro initialization broke.");
        gyro = null;
      }
    }
  }

  /**
   * Stop the robot!  Simple command.
   */
  public void stop() {
    // Set Motor Volts to 0
    leftTalon.set(ControlMode.PercentOutput, 0);
    rightTalon.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Set a percent output power for the left and right sides
   * of the drivetrain
   * 
   * @param left  Percentage input for the left talon.
   * @param right Percentage input for the right talon.
   */
  public void setPower(double left, double right) {
    leftTalon.set(ControlMode.PercentOutput, left);
    rightTalon.set(ControlMode.PercentOutput, right);
  }


  public void setRampArcadePower(double front, double turn) {
    double newY = front;

    // prevY = (leftTalon.getMotorOutputPercent() +
    // rightTalon.getMotorOutputPercent()) / 2;

    double maxIncrement = deltaTime * ramp;

    if (Math.abs(front - prevY) > maxIncrement) {
      double sign = (front - prevY) / Math.abs(front - prevY);
      newY = (maxIncrement * sign) + prevY;
    }

    if (Math.abs(newY) > maxSpeed) {
      newY = (Math.abs(newY) / newY) * maxSpeed;
    }

    leftTalon.set(ControlMode.PercentOutput, newY + turn);
    rightTalon.set(ControlMode.PercentOutput, newY - turn);

    prevY = newY;
  }

  /**
   * Sets the desired tick position for the left and right talon
   * 
   * @param left  Desired tick position for the left talon
   * @param right Desired tick position for the right talon
   */
  public void setPosition(double left, double right) {
    leftTalon.set(ControlMode.Position, left);
    rightTalon.set(ControlMode.Position, right);
  }

  public void resetGyro() {
    if (gyro != null) {
      gyro.reset();
      gyro.zeroYaw();
    } else {
      // programmer.sadness() public boolean decell = true;
    }
  }

  public double getAngle() {
    if (gyro != null) {
      return gyro.getAngle() % 360;
      // return (gyro.getAngle()-Math.floor(gyro.getAngle()/360)*360);
      // Not TESTED
    } else {
      return 0;
    }
  }

  /**
   * Gets the left talon's position output
   * 
   * @return Current tick position of the left talon's encoder
   */
  public double leftEncoderTicks() {
    leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return leftTalon.getSelectedSensorPosition(0);
  }

  /**
   * Gets the right talon's position output
   * 
   * @return Current tick position of the right talon's encoder
   */
  public double rightEncoderTicks() {
    rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return rightTalon.getSelectedSensorPosition(0);
  }

  /**
   * Gets the left talon's velocity output
   * 
   * @return Current tick velocity of the left talon's encoder
   */
  public double leftEncoderVelocity() {
    leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return leftTalon.getSelectedSensorVelocity(0);
  }

  /**
   * Gets the right talon's velocity output
   * 
   * @return Current tick velocity of the right talon's encoder
   */
  public double rightEncoderVelocity() {
    rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    return rightTalon.getSelectedSensorVelocity(0);
  }

  /**
   * Resets the encoders for both talons to 0
   */
  public void resetEncoders() {
    leftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); /* PIDLoop=0,timeoutMs=0 */
    leftTalon.setSelectedSensorPosition(0, 0, 10);
    rightTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0); /* PIDLoop=0,timeoutMs=0 */
    rightTalon.setSelectedSensorPosition(0, 0, 10);
  }

  public double getHeading() {
		if (gyro != null) {
			return( gyro.getYaw() );
		} else {
			return 0.0;
		}
	}
	  
  /**
   * Gets PID constants from the SmartDashboard and then uses setPIDValues(double,
   * double, double)
   */
  public void setPIDValues() {
    // Make these reference a passed in table so we can write tests.
    double p = table.getEntry("P").getDouble(1);
    double i = table.getEntry("I").getDouble(0);
    double d = table.getEntry("D").getDouble(0);
    setPIDValues(p, i, d);
  }

  /**
   * Sets the parameter PID constants to the talons
   * 
   * @param p Proportional PID constant
   * @param i Integral PID constant
   * @param d derivative PID constant
   */
  public void setPIDValues(double p, double i, double d) {
    // Calling the correct config_kx for each parameter might make this work better...;-)
    leftTalon.config_kP(0, p, 0);
    rightTalon.config_kP(0, p, 0);
    leftTalon.config_kI(0, i, 0);
    rightTalon.config_kI(0, i, 0);
    leftTalon.config_kD(0, d, 0);
    rightTalon.config_kD(0, d, 0);
  }

  /**
   * Sets the talons and their follow victors into BrakeMode
   */
  public void setBrake() {
    leftTalon.setNeutralMode(NeutralMode.Brake);
    rightTalon.setNeutralMode(NeutralMode.Brake);

    leftVictor1.setNeutralMode(NeutralMode.Brake);
    rightVictor1.setNeutralMode(NeutralMode.Brake);
  }

  /**
   * Sets the talons and their follow victors into CoastMode
   */
  public void setCoast() {
    leftTalon.setNeutralMode(NeutralMode.Coast);
    rightTalon.setNeutralMode(NeutralMode.Coast);

    leftVictor1.setNeutralMode(NeutralMode.Coast);
    rightVictor1.setNeutralMode(NeutralMode.Coast);
  }

  /**
   * Updates the SmartDashboard with subsystem data
   */
  public void updateSmartDashboard() {
    // Factor out direct calls to SmartDashboard and use passed in table
    // so that we can test.
    table.getEntry("Left Ticks DriveTrain").setDouble(leftEncoderTicks());
    table.getEntry("Right Ticks DriveTrain").setDouble(rightEncoderTicks());
    table.getEntry("Left Velocity Drivetrain").setDouble(leftEncoderVelocity());
    table.getEntry("Right Velocity Drivetrain").setDouble(rightEncoderVelocity());
    table.getEntry("Gyro angle").setDouble(getAngle());

    SmartDashboard.putNumber("Gyro Angle", getAngle());
    SmartDashboard.putNumber("Prev Y", prevY);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ArcadeDrive());
    // setDefaultCommand(new TankDrive());
  }
}
