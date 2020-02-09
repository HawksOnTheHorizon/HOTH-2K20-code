/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
JoystickButton x = new JoystickButton(jStick, 3);
JoystickButton b = new JoystickButton(jStick, 2);
WPI_TalonSRX intake = new WPI_TalonSRX(6);

WPI_VictorSPX m_frontLeft = new WPI_VictorSPX(2);
WPI_VictorSPX m_rearLeft = new WPI_VictorSPX(3);
SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_rearLeft);

WPI_VictorSPX m_frontRight = new WPI_VictorSPX(4);
WPI_VictorSPX m_rearRight = new WPI_VictorSPX(5);
SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_rearRight);
DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

I2C.Port i2cPort = I2C.Port.kOnboard;
ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);






  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
  
  
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
  
  Color detectedColor = m_colorSensor.getColor();
  double IR = m_colorSensor.getIR();

  SmartDashboard.putNumber("Red", detectedColor.red);
  SmartDashboard.putNumber("Green", detectedColor.green);
  SmartDashboard.putNumber("Blue", detectedColor.blue);
  SmartDashboard.putNumber("IR", IR);


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
    
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
   
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
  m_drive.tankDrive(jStick.getRawAxis(1), jStick.getRawAxis(5));
  
  if (x.get()) {
    intake.set(1);
  } else if (b.get()) {
    intake.set(-1); 
  } else {
    intake.set(0);
  }
}

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
