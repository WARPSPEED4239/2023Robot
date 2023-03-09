package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Pigeon;
import frc.robot.subsystems.Shifter;

public class AutoBalanceV5 extends CommandBase {
  
  private final Drivetrain mDrivetrain;
  private final Shifter mShifter;
  private final Timer mTimer = new Timer();
  private double mStartingYaw;
  private double mRoll;
  private int mSequence = -1;

  public AutoBalanceV5(Drivetrain drivetrain, Shifter shifter) {
    mDrivetrain = drivetrain;
    mShifter = shifter;
    addRequirements(mDrivetrain);
  }

  @Override
  public void initialize() {
    mShifter.setShifterState(false);
    mStartingYaw = Pigeon.getYaw();
    mSequence = 0;
  }

  @Override
  public void execute() {
    mRoll = Pigeon.getRoll();
    System.out.println(mSequence);
    
    if(mSequence == 0) {
      moveUntilAngledUp(0.4);
    } else if(mSequence == 1) {
      moveStraightForTime(0.35, 1.5);
    } else if(mSequence == 2) {
      levelRobot(0.18);
    }
    //impliment safety for flying off / going to fast here
  }

  @Override
  public void end(boolean interrupted) {}
  
  @Override
  public boolean isFinished() {
    return false;
  }
  
  private void moveUntilAngledUp(double speed) {
    if (Math.abs(mRoll) > 10.0) {
      mSequence++;
      // reset timer for the next sequence
      mTimer.reset();
      mTimer.start();
    } else {
      mDrivetrain.moveStraightUsingGyro(speed, mStartingYaw);
    }
  }

  private void moveStraightForTime(double speed, double time) {
    if(mTimer.get() < time) {
      mDrivetrain.moveStraightUsingGyro(speed, mStartingYaw);
    } else {
      mSequence++;
    }
  }

  private void levelRobot(double adjustingSpeed) {
    if(mRoll < -3.0) {
      mDrivetrain.moveStraightUsingGyro(adjustingSpeed, mStartingYaw);
    } else if(mRoll > 3.0) {
      mDrivetrain.moveStraightUsingGyro(-adjustingSpeed, mStartingYaw);
    } else {
      mDrivetrain.stopAllMotors();
    }
  }
}