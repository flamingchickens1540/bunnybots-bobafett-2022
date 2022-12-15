# Boba Fett

Team 1540's code for our 2022 Bunnybot Boba Fett.

Fett uses a claw to pick up tubes and bunnies off the ground, and an elevator to place them into the bins

## Subsystem overview

### Claw

The [claw subsystem](src/main/java/org/team1540/bobafett/commands/claw/Claw.java) is a pneumatics controlled claw that 
is used to grab tubes and bunnies.

### Elevator

The [elevator subsystem](src/main/java/org/team1540/bobafett/commands/elevator/Elevator.java) is a motorized elevator 
using a Spark Max motor controller and a brushless motor.

## Controller Bindings

### Pilot (Xbox controller)

Left stick: move left side of drivetrain forwards/backwards

Right stick: move right side of drivetrain forwards/backwards
 
A button: toggle brake mode

X button: DO NOT PRESS HAS TEMP TESTING CODE

Left trigger: move robot backwards

Right trigger: move robot forwards

### Copilot (Joystick)

Stick forward: move elevator down

Stick backward: move elevator up

Trigger: hold to close claw

Button #2: elevator to bottom limit switch

Button #3: elevator to top limit switch

Button #4: elevator to setpoint #1

Button #5 elevator to setpoint #2
