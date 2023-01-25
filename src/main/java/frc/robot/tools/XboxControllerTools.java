package frc.robot.tools;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;

public class XboxControllerTools {

    private final static CommandXboxController mController = new CommandXboxController(Constants.XBOX_CONTROLLER_PORT);

    public static double triggersAxis() {
        double triggersAxis = mController.getRightTriggerAxis() - mController.getLeftTriggerAxis();
        return triggersAxis;
    }
}
