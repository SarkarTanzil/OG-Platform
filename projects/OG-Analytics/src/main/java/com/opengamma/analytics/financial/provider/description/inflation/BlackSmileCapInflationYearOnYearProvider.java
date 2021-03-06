/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.provider.description.inflation;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;

import com.opengamma.analytics.financial.model.option.parameters.BlackSmileCapInflationYearOnYearParameters;
import com.opengamma.analytics.financial.provider.description.interestrate.MulticurveProviderInterface;
import com.opengamma.analytics.financial.provider.sensitivity.multicurve.ForwardSensitivity;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.tuple.DoublesPair;

/**
 * Implementation of a provider of Black smile for year on year inflation options. The volatility is time to expiration/strike/delay dependent.
 * The "delay" is the time between expiration of the option and last trading date of the underlying.
 */
public class BlackSmileCapInflationYearOnYearProvider implements BlackSmileCapInflationYearOnYearProviderInterface {

  /**
   * The inflation provider.
   */
  private final InflationProviderInterface _inflation;
  /**
   * The Black parameters.
   */
  private final BlackSmileCapInflationYearOnYearParameters _parameters;

  /**
   * Constructor.
   * @param inflation The inflation provider.
   * @param parameters The Black parameters.
   */
  public BlackSmileCapInflationYearOnYearProvider(final InflationProviderInterface inflation, final BlackSmileCapInflationYearOnYearParameters parameters) {
    ArgumentChecker.notNull(inflation, "inflation");
    ArgumentChecker.notNull(parameters, "parameters");
    _inflation = inflation;
    _parameters = parameters;
  }

  @Override
  public MulticurveProviderInterface getMulticurveProvider() {
    return _inflation.getMulticurveProvider();
  }

  @Override
  public InflationProviderInterface getInflationProvider() {
    return _inflation;
  }

  @Override
  public BlackSmileCapInflationYearOnYearProviderInterface copy() {
    final InflationProviderInterface inflation = _inflation.copy();
    return new BlackSmileCapInflationYearOnYearProvider(inflation, _parameters);
  }

  @Override
  public BlackSmileCapInflationYearOnYearParameters getBlackParameters() {
    return _parameters;
  }

  @Override
  public double[] parameterSensitivity(final String name, final List<DoublesPair> pointSensitivity) {
    return _inflation.parameterSensitivity(name, pointSensitivity);
  }

  @Override
  public double[] parameterForwardSensitivity(final String name, final List<ForwardSensitivity> pointSensitivity) {
    return _inflation.parameterForwardSensitivity(name, pointSensitivity);
  }

  @Override
  public double[] parameterInflationSensitivity(final String name, final List<DoublesPair> pointSensitivity) {
    return _inflation.parameterInflationSensitivity(name, pointSensitivity);
  }

  @Override
  public Set<String> getAllCurveNames() {
    return _inflation.getAllCurveNames();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + _inflation.hashCode();
    result = prime * result + _parameters.hashCode();
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof BlackSmileCapInflationYearOnYearProvider)) {
      return false;
    }
    final BlackSmileCapInflationYearOnYearProvider other = (BlackSmileCapInflationYearOnYearProvider) obj;
    if (!ObjectUtils.equals(_inflation, other._inflation)) {
      return false;
    }
    if (!ObjectUtils.equals(_parameters, other._parameters)) {
      return false;
    }
    return true;
  }

}
