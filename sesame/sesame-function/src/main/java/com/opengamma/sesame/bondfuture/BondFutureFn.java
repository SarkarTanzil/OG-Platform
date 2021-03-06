/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.bondfuture;

import com.opengamma.analytics.financial.provider.sensitivity.multicurve.MultipleCurrencyParameterSensitivity;
import com.opengamma.analytics.util.amount.ReferenceAmount;
import com.opengamma.sesame.Environment;
import com.opengamma.sesame.OutputNames;
import com.opengamma.sesame.function.Output;
import com.opengamma.sesame.trade.BondFutureOptionTrade;
import com.opengamma.sesame.trade.BondFutureTrade;
import com.opengamma.util.money.Currency;
import com.opengamma.util.money.MultipleCurrencyAmount;
import com.opengamma.util.result.Result;
import com.opengamma.util.tuple.Pair;

/**
 * General interface for bond futures.
 */
public interface BondFutureFn {

  /**
   * Calculate the present value of a bond future.
   * 
   * @param env the environment that the PV will be calculate with.
   * @param bondFutureTrade the bond future trade to calculate the PV for.
   * @return result containing the present value if successful, a Failure otherwise.
   */
  @Output(value = OutputNames.PRESENT_VALUE)
  Result<MultipleCurrencyAmount> calculatePV(Environment env, BondFutureTrade bondFutureTrade);
  
  /**
   * Calculate the PV01 for a bond future security.
   * 
   * @param env the environment that the PV01 will be calculated with.
   * @param bondFutureTrade the bond future trade to calculate the PV01 for.
   * @return result containing the PV01 if successful, a Failure otherwise.
   */
  @Output(value = OutputNames.PV01)
  Result<ReferenceAmount<Pair<String, Currency>>> calculatePV01(Environment env, BondFutureTrade bondFutureTrade);

  /**
   * Calculate the Bucketed PV01 for a bond future security.
   *
   * @param env the environment that the bucketed PV01 will be calculated with.
   * @param bondFutureTrade the bond future trade to calculate the bucketed PV01 for.
   * @return result containing the bucketed PV01 if successful, a Failure otherwise.
   */
  @Output(value = OutputNames.BUCKETED_PV01)
  Result<MultipleCurrencyParameterSensitivity> calculateBucketedPV01(Environment env, BondFutureTrade bondFutureTrade);

  /**
   * Calculate the model price of the bond future option.
   * @param env the environment, not null.
   * @param bondFutureTrade the bond future option trade, not null.
   * @return the model price of the bond future option.
   */
  @Output(OutputNames.SECURITY_MODEL_PRICE)
  Result<Double> calculateSecurityModelPrice(Environment env, BondFutureTrade bondFutureTrade);
}
