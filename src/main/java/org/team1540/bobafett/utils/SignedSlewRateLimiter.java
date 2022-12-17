// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.team1540.bobafett.utils;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.util.WPIUtilJNI;

/**
 * A class that limits the rate of change of an input value. Useful for implementing voltage,
 * setpoint, and/or output ramps. A slew-rate limit is most appropriate when the quantity being
 * controlled is a velocity or a voltage; when controlling a position, consider using a {@link
 * edu.wpi.first.math.trajectory.TrapezoidProfile} instead.
 */
public class SignedSlewRateLimiter {

  private double m_rateLimitAcc;
  private double m_rateLimitDecc;
  private double m_prevVal;
  private double m_prevTime;

  public void setAcc(double m_rateLimitAcc) {
    this.m_rateLimitAcc = m_rateLimitAcc;
  }

  public void setDec(double m_rateLimitDecc) {
    this.m_rateLimitDecc = m_rateLimitDecc;
  }


  /**
   * Creates a new SlewRateLimiter with the given rate limit and initial value.
   *
   * @param rateLimit The rate-of-change limit, in units per second.
   * @param initialValue The initial value of the input.
   */
  public SignedSlewRateLimiter(double rateLimitAcc, double rateLimitDecc, double initialValue) {
    m_rateLimitAcc = rateLimitAcc;
    m_rateLimitDecc = rateLimitDecc;
    m_prevVal = initialValue;
    m_prevTime = WPIUtilJNI.now() * 1e-6;
  }



  /**
   * Creates a new SlewRateLimiter with the given rate limit and an initial value of zero.
   *
   * @param rateLimit The rate-of-change limit, in units per second.
   */
  public SignedSlewRateLimiter(double rateLimitAcc, double rateLimitDecc) {
    this(rateLimitAcc, rateLimitDecc, 0);
  }

  /**
   * Filters the input to limit its slew rate.
   *
   * @param input The input value whose slew rate is to be limited.
   * @return The filtered value, which will not change faster than the slew rate.
   */
  public double calculate(double input) {
    double currentTime = WPIUtilJNI.now() * 1e-6;
    double elapsedTime = currentTime - m_prevTime;
    // if(Math.signum(input)==Math.signum(m_prevVal) && (Math.abs(input) - Math.abs(m_prevVal)) > 0) { // if accelerating (input and prev have same signs and input is larger than prev)
      if((Math.abs(input) - Math.abs(m_prevVal)) > 0) { // NO SIGN CHECKING- if accelerating (input mag is larger than prev mag)
    // if((Math.abs(input) - Math.abs(m_prevVal)) > 0 || Math.signum(input)!=Math.signum(m_prevVal)) { // Magnitude is larger OR signs are different- if accelerating (input mag is larger than prev mag)
        m_prevVal +=
            MathUtil.clamp(input - m_prevVal, -m_rateLimitAcc * elapsedTime, m_rateLimitAcc * elapsedTime);
        m_prevTime = currentTime;
    } else {
        m_prevVal +=
            MathUtil.clamp(input - m_prevVal, -m_rateLimitDecc * elapsedTime, m_rateLimitDecc * elapsedTime);
        m_prevTime = currentTime;
    }
    // m_prevVal +=
    //     MathUtil.clamp(input - m_prevVal, -m_rateLimitDecc * elapsedTime, m_rateLimitAcc * elapsedTime);
    // m_prevTime = currentTime;
    return m_prevVal;
  }

  /**
   * Resets the slew rate limiter to the specified value; ignores the rate limit when doing so.
   *
   * @param value The value to reset to.
   */
  public void reset(double value) {
    m_prevVal = value;
    m_prevTime = WPIUtilJNI.now() * 1e-6;
  }
}
