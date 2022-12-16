// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.team1540.bobafett;

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.team1540.bobafett.commands.claw.*;
import org.team1540.bobafett.commands.drivetrain.*;
import org.team1540.bobafett.commands.elevator.*;
import org.team1540.bobafett.utils.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final Pigeon2 pigeon = new Pigeon2(0);
    private final Drivetrain drivetrain = new Drivetrain(pigeon);
    private final ChickenPhotonCamera camera = new ChickenPhotonCamera(Constants.VisionConstants.CAMERA_NAME);
    private final XboxController pilotController = new XboxController(0);
    private final Joystick copilotController = new Joystick(1);
    final Claw claw = new Claw();
    private final Elevator elevator = new Elevator();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        elevator.setDefaultCommand(new ElevatorCommand(elevator, copilotController));
        drivetrain.setDefaultCommand(new TankDrive(drivetrain, elevator, pilotController));
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        new JoystickButton(pilotController, XboxController.Button.kX.value)
                .whileActiveContinuous(new ApproachAprilTag(drivetrain, camera));

        new JoystickButton(pilotController, XboxController.Button.kA.value)
                .whenPressed(drivetrain::toggleBrakeMode);

        new JoystickButton(copilotController, 1)
                .whileActiveContinuous(new CloseClaw(claw));

        new JoystickButton(copilotController, 3)
                .whenPressed(new MoveToTop(elevator));

        new JoystickButton(copilotController, 4)
                .whenPressed(new ElevatorPID(elevator, 60)); // TODO adjust setpoints

        new JoystickButton(copilotController, 5)
                .whenPressed(new ElevatorPID(elevator, 100));

        new JoystickButton(copilotController, 2)
                .whenPressed(new MoveToBottom(elevator));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new PlaceTube(drivetrain, elevator, claw);
    }
}
