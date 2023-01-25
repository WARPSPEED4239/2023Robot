
package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Pigeon;

public class AutoBalance extends CommandBase {

  private final Drivetrain mDrivetrain;
  double pitch = 0.0;
  double speed = 0.0;
  double startingYaw = 0.0;
  boolean ascend = true;
  boolean activatedVinceBalanceTestMode = false;

  public AutoBalance(Drivetrain drivetrain) {
    mDrivetrain = drivetrain;

    addRequirements(mDrivetrain);
  }

  @Override
  public void initialize() {
    startingYaw = Pigeon.getYaw();
  }

  @Override
  public void execute() {
    pitch = Pigeon.getRoll();

    if (ascend) {
      ascend(pitch, startingYaw);
    } else {
      if (pitch > 0.0 && activatedVinceBalanceTestMode == false) {
        speed = 0.00269 * Math.pow(pitch, 2);
      } else if (pitch < 0.0 && activatedVinceBalanceTestMode == false) {
        speed = -0.00269 * Math.pow(pitch, 2);
      } else if (pitch > 0.0) {
        speed = Math.pow((0.0111 * pitch), 0.5);
      } else if (pitch < 0.0) {
        speed = -Math.pow((0.0111 * Math.abs(pitch)), 0.5);
      }

      if(pitch > -3.1 && pitch < 3.1) {
        mDrivetrain.stopAllMotors();
        activatedVinceBalanceTestMode = true;
      } else {
        mDrivetrain.moveStraightUsingGyro(speed, startingYaw);
      }

      SmartDashboard.putNumber("Auto Balance Speed", speed);
    }
  }

  public void ascend (double pitch, double startingYaw) {
    if (pitch > -12 && pitch < 12) {
      mDrivetrain.moveStraightUsingGyro(1, startingYaw);
    } else {
      ascend = false;
    }
  }



  @Override
  public void end(boolean interrupted) {}

 
  @Override
  public boolean isFinished() {
    return false;
  }
}
