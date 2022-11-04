// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.team1540.bobafett;

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.photonvision.PhotonCamera;
import org.team1540.bobafett.commands.claw.Claw;
import org.team1540.bobafett.commands.drivetrain.Drivetrain;
import org.team1540.bobafett.commands.drivetrain.TankDrive;
import org.team1540.bobafett.commands.elevator.Elevator;
import org.team1540.bobafett.commands.elevator.MoveToBottom;
import org.team1540.bobafett.commands.elevator.MoveToTop;
import org.team1540.bobafett.commands.vision.AprilTagPIDTurn;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Pigeon2 pigeon = new Pigeon2(0);
  private final Drivetrain drivetrain = new Drivetrain();
  private final PhotonCamera camera = new PhotonCamera(Constants.VisionConstants.CAMERA_NAME);
  private final XboxController controller = new XboxController(0);
  private final Claw claw = new Claw();
  private final Elevator elevator = new Elevator();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    drivetrain.setDefaultCommand(new TankDrive(drivetrain, controller));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(controller, XboxController.Button.kX.value)
            .whenPressed(new AprilTagPIDTurn(drivetrain, camera, pigeon, 154));
    new JoystickButton(controller, XboxController.Button.kB.value)
            .whenPressed(new InstantCommand(claw::toggleClosed, claw));
    new JoystickButton(controller, XboxController.Button.kY.value)
            .whenPressed(new MoveToTop(elevator));
    new JoystickButton(controller, XboxController.Button.kA.value)
            .whenPressed(new MoveToBottom(elevator));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
