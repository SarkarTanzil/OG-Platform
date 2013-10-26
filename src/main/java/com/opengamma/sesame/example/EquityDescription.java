/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.example;

import com.opengamma.financial.security.equity.EquitySecurity;
import com.opengamma.sesame.function.Target;

/**
 * Returns the security name as the description.
 */
public class EquityDescription implements EquityDescriptionFunction {

  /**
   * @param security A security
   * @return The security name
   */
  @Override
  public String getDescription(@Target EquitySecurity security) {
    return security.getName();
  }
}
