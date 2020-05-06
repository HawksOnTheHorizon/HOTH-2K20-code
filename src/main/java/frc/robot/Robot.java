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

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
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
Joystick jStick2 = new Joystick(1);
JoystickButton lefttrigger = new JoystickButton(jStick2, 8);
JoystickButton righttrigger = new JoystickButton(jStick2, 7);
JoystickButton y = new JoystickButton(jStick2, 4);
JoystickButton a = new JoystickButton(jStick2, 2);

JoystickButton xDriver1 = new JoystickButton(jStick, 1);
JoystickButton bDriver1 = new JoystickButton(jStick, 3);
JoystickButton leftButton = new JoystickButton(jStick,5);
JoystickButton rightButton = new JoystickButton(jStick,6);
JoystickButton ltTrigger = new JoystickButton(jStick,7);
JoystickButton rtTrigger = new JoystickButton(jStick, 8);

WPI_TalonSRX intake = new WPI_TalonSRX(6);
WPI_TalonSRX belt = new WPI_TalonSRX(7);
WPI_VictorSPX shooter = new WPI_VictorSPX(8);
WPI_VictorSPX hanger = new WPI_VictorSPX(10);
WPI_VictorSPX m_frontLeft = new WPI_VictorSPX(5);
WPI_VictorSPX m_backLeft = new WPI_VictorSPX(3);
SpeedControllerGroup m_left = new SpeedControllerGroup(m_frontLeft, m_backLeft);

WPI_VictorSPX m_frontRight = new WPI_VictorSPX(4);
WPI_VictorSPX m_backRight = new WPI_VictorSPX(2);
SpeedControllerGroup m_right = new SpeedControllerGroup(m_frontRight, m_backRight);
DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);

Timer m_timer = new Timer();
Timer n_timer = new Timer();

ADXRS450_Gyro gyro = new ADXRS450_Gyro();
double kP = 0.05; // propotional control is a predeterminded number we use to control how much a mechanism can move (slows down the closer we get to the stop sign)
double heading; // variable used for auto init that will allow us to store the initialized angle
double rightHeading;

// Car analogy: error = distance to the stop sign. this kp vlaue allows us to put pressure on the pedal and slow down the mechanism the closer we get to 0

AutonomousBot auto = new AutonomousBot();


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

SmartDashboard.getNumber("Gyro Angle", gyro.getAngle());

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
    heading = gyro.getAngle(); // initialized angle (0)
    rightHeading = gyro.getAngle() + 90;
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

    double error = heading - gyro.getAngle(); // error = initialized angle (0) - current angle 
  
    switch (auto.autoSelector) {

      case ONE: System.out.println ("Auto 1"); // robot moves backwards (shooter facing low goal) towards low port, shoots 3 balls and stays stationary  
                if (m_timer.get() < 3.5) { // robot moves backward
                  m_drive.tankDrive(-0.50 + kP * error, -0.50 - kP * error); // the smaller the number, that is the direction we are turning in
                } else if (m_timer.get () > 3.5 && m_timer.get() < 6.5) { // belt moves towards shooter and shoots 3 balls  
                  m_drive.stopMotor();
                  belt.set(-1);  
                  shooter.set(-1);
                } else { // robot, belt, and shooter stops 
                  m_drive.stopMotor();
                  belt.set(0);
                  shooter.set(0);
                }
                break;

      case TWO: System.out.println ("Auto 2"); // robot shoots 3 balls (feeding other robot) and moves forward 
                if (m_timer.get() > 0 && m_timer.get() < 3.5) { // belt moves towards shooter and shoots 3 balls 
                  belt.set(-1);
                  shooter.set(-1);
                } else if (m_timer.get () > 3.5 && m_timer.get() < 4) { // belt and shooter stops, robot moves forward 
                  belt.set(0);
                  shooter.set(0);
                  m_drive.tankDrive(.50 + kP * error, .50 - kP * error);
                } else { // robot stops 
                  m_drive.stopMotor();
                }
                break;
                
      case THREE: System.out.println ("Auto 3"); // robot shoots 3 balls through intake (feeding other robot) and moves backwards
                  if (m_timer.get() > 0 && m_timer.get() < 3.5) { // robot shoots 3 balls through intake
                    belt.set(1);
                    shooter.set(1);
                  } else if (m_timer.get() > 3.5 && m_timer.get() < 4) { // belt and shooter stop; robot moves backwards 
                    belt.set(0);
                    shooter.set(0);
                    m_drive.tankDrive(-0.50 + kP * error, -0.50 - kP * error); 
                  } else { // robot stops
                    m_drive.stopMotor();
                  }
                  break;

      case FOUR:  System.out.println ("Auto 4"); // robot moves forward and stays stationary
                  if (m_timer.get() > 0 && m_timer.get() < 3.5) { // robot moves forward
                      m_drive.tankDrive(0.50 + kP * error, 0.50 - kP * error);
                  } else { // robot stops 
                      m_drive.stopMotor();
                  }
                  break;

      case FIVE:  System.out.println ("Auto 5"); // robot moves backwards (shooter facing low goal) towards low port, shoots 3 balls, moves forward, 
                  //turns 90 degrees to the right, and moves forward
                  if (m_timer.get() < 3.5) { // robot moves backward
                      m_drive.tankDrive(-0.50 + kP * error, -0.50 - kP * error); 
                  } else if (m_timer.get () > 3.5 && m_timer.get() < 6.5) { // belt moves towards shooter and shoots 3 balls  
                      m_drive.stopMotor();
                      belt.set(-1);  
                      shooter.set(-1);
                  } else if (m_timer.get() > 6.5 && m_timer.get() < 7.5) {
                      m_drive.tankDrive(0.50 + kP * error, 0.50 - kP * error); // robot moves forward //write function to turn 90-degrees
                  } else if (m_timer.get() > 7.5 && gyro.getAngle() < rightHeading) { //make sure the getAngle part is correct
                    while (gyro.getAngle() < rightHeading) {
                        m_drive.tankDrive(-0.50 + kP * error, 0.50 + kP * error); //robot turns 
                      }
                      heading = 90;
                      n_timer.start();
                  } else if (n_timer.get() < 0.5) {
                      error = heading - gyro.getAngle();
                      m_drive.tankDrive(0.50 + kP * error, 0.50 - kP * error);
                  } else { // robot, belt, and shooter stops 
                      m_drive.stopMotor();
                      belt.set(0);
                      shooter.set(0);
                      n_timer.reset();
                  }
                  break;

      
                  
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

  if (lefttrigger.get()) {
    belt.set(-1); //belt moves towards shooter
  } else if (righttrigger.get()) {
    belt.set(1); //belt moves toward intake 
  } else {
    belt.set(0);
  }


  if (y.get()) { //shooter; spit balls out
    shooter.set(-1);
  } else if (a.get()) { // reverse shooting; intakes ball from loading station 
    shooter.set(1);
  } else {
    shooter.set(0);
  }

  if (leftButton.get()) {
    intake.set(1);
  } else if (ltTrigger.get()) {
    intake.set(-1);
  } else {
    intake.set(0);
  }

  if (xDriver1.get()) {
    // control panel (rotation control)
  } else if (bDriver1.get()) {
    // control panel (position control)
  } else {
    // nothing happens 
  }

  if (rtTrigger.get()) {
    hanger.set(0.65);// hanger goes up
  } else {
    hanger.set(0);// nothing happens
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