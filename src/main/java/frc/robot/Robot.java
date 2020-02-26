/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
Joystick jStick = new Joystick(0);
Joystick jStick2 = new Joystick(1);
JoystickButton leftTrigger = new JoystickButton(jStick2, 8);
JoystickButton rightTrigger = new JoystickButton(jStick2, 7);
JoystickButton y = new JoystickButton(jStick2, 4);
JoystickButton a = new JoystickButton(jStick2, 2);
JoystickButton rtTrigger = new JoystickButton(jStick2, 8);

JoystickButton x = new JoystickButton(jStick, 1);
JoystickButton b = new JoystickButton(jStick, 3);
JoystickButton leftButton = new JoystickButton(jStick,5);
JoystickButton rightButton = new JoystickButton(jStick,6);
JoystickButton ltTrigger = new JoystickButton(jStick,7);

WPI_TalonSRX intake = new WPI_TalonSRX(6);
WPI_TalonSRX belt = new WPI_TalonSRX(7);
WPI_VictorSPX shooter = new WPI_VictorSPX(8);

WPI_VictorSPX m_frontLeft = new WPI_VictorSPX(5);
WPI_VictorSPX m_backLeft = new WPI_VictorSPX(3);
SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_backLeft);

WPI_VictorSPX m_frontRight = new WPI_VictorSPX(4);
WPI_VictorSPX m_backRight = new WPI_VictorSPX(2);
SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_backRight);
DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

Timer m_timer = new Timer();

//I2C.Port i2cPort = I2C.Port.kOnboard; //adressing I2C port  
//ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort); //adressing color sensor and placing 
ColorMatch m_colorMatcher = new ColorMatch();
Color blueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
Color greenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
Color redTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
Color yellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

//PowerDistributionPanel pdp1 = new PowerDistributionPanel(1);






  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
  m_colorMatcher.addColorMatch(blueTarget);
  m_colorMatcher.addColorMatch(greenTarget);
  m_colorMatcher.addColorMatch(redTarget);
  m_colorMatcher.addColorMatch(yellowTarget);
  
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  
 /* Color detectedColor = m_colorSensor.getColor();

  String colorString;
  ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);
  
  if (match.color == blueTarget) {
    colorString = "Blue";
  } else if (match.color == redTarget) {
    colorString = "Red";
  } else if (match.color == greenTarget) {
    colorString = "Green";
  } else if (match.color == yellowTarget) {
    colorString = "Yellow";
  } else {
    colorString = "Unknown";
  }

  double IR = m_colorSensor.getIR();

  SmartDashboard.putNumber("Red", detectedColor.red);
  SmartDashboard.putNumber("Green", detectedColor.green);
  SmartDashboard.putNumber("Blue", detectedColor.blue);
  SmartDashboard.putNumber("Confidence", match.confidence);
  SmartDashboard.putString("Detected Color", colorString);
  SmartDashboard.putNumber("IR", IR);

  SmartDashboard.putNumber("Total Current", pdp1.getTotalCurrent());

  /*SmartDashboard.putNumber("Right Bottom Motor", pdp1.getCurrent(15));
  SmartDashboard.putNumber("Right Top Motor", pdp1.getCurrent(14));
  SmartDashboard.putNumber("Left Bottom Motor", pdp1.getCurrent(13));
  SmartDashboard.putNumber("Left Top Motor", pdp1.getCurrent(12));
*/
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
   if (m_timer.get() < 0.5) {
     m_drive.tankDrive(-0.5, -0.5);

   } else {
     m_drive.stopMotor();
   }

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

  m_drive.tankDrive(-jStick.getRawAxis(1)*0.75, -jStick.getRawAxis(3)*0.75);
  
  if (rightButton.get()) {
    m_drive.tankDrive(jStick.getRawAxis(1)*0.75, jStick.getRawAxis(3)*0.75); //reverse drivetrain; shooter becomes forward
  }
  

  /*if (x.get()) { //reverse intake
    intake.set(1);
    belt.set(1);
  } else if (b.get()) { //intake 
    intake.set(-1);
    belt.set(-1); 
  } else {
    intake.set(0);
    belt.set(0);
  }
  */

  if (leftTrigger.get()) {
    belt.set(-1); //belt moves towards shooter
  } else if (rightTrigger.get()) {
    belt.set(1); //belt moves toward intake 
  } else {
    belt.set(0);
  }

  /*if (y.get()) {
    intake.set(-1); for 0.5 seconds
    while shooter.set(1) for 5 seconds 
          intake.set(1) will start running for 5 seconds
    else 
    intake.set(0);       
  }
*/

  if (y.get()) {
    belt.set(1); 
    shooter.set(-1);//shooter; spit balls out
    belt.set(-1);
  } else if (a.get()) { // reverse shooting; intakes ball from loading station 
    belt.set(-1);
    shooter.set(1);
    belt.set(1);
  } else {
    belt.set(0);
    shooter.set(0);
    belt.set(0);
  }

  if (leftButton.get()) {
    intake.set(1);
  } else if (ltTrigger.get()) {
    intake.set(-1);
  } else {
    intake.set(0);
  }

  if (x.get()) {
    // control panel (rotation control)
  } else if (b.get()) {
    // control panel (position control)
  } else {
    // nothing happens 
  }

  if (rtTrigger.get()) {
    // hanger goes up
  } else {
    // nothing happens
  }

  /*if (leftTrigger.get()) {
    belt.set(1);
  } else if (rightTrigger.get()) {
    belt.set(-1);
  } else {
    belt.set(0);
  }

*/ 

}

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
