# Boba Fett

Team 1540's code for our 2022 Bunnybot Boba Fett.

Fett uses a claw to pick up tubes and bunnies off the ground, and an elevator to place them into the bins

## Subsystem overview

### Claw

The [claw subsystem](src/main/java/org/team1540/bobafett/commands/claw/Claw.java) is a pneumatics controlled claw that is used to grab tubes and bunnies.

### Elevator

The [elevator subsystem](src/main/java/org/team1540/bobafett/commands/elevator/Elevator.java) is a motorized elevator using a Spark Max motor controller and a brushless motor.

## Controller Bindings

These controller bindings are temporary and are used only for testing

Left stick: move left side of drivetrain

Right stick: move right side of drivetrain

Left trigger: move elevator up

Right trigger: move elevator down

A button: move elvator to bottom limit switch

B button: hold to close claw

X button: Turn to an AprilTag with id of 154 (Camera not installed yet, doesn't work)

Y button: Move elevator to the top limit switch
