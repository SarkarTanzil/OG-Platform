/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.bondyield;

import static com.opengamma.engine.value.ValuePropertyNames.CALCULATION_METHOD;
import static com.opengamma.engine.value.ValuePropertyNames.CURVE_CONSTRUCTION_CONFIG;
import static com.opengamma.engine.value.ValuePropertyNames.CURVE_EXPOSURES;
import static com.opengamma.engine.value.ValueRequirementNames.CURVE_BUNDLE;
import static com.opengamma.engine.value.ValueRequirementNames.MARKET_YTM;
import static com.opengamma.financial.analytics.model.CalculationPropertyNamesAndValues.YIELD_METHOD;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZonedDateTime;

import com.google.common.collect.Iterables;
import com.opengamma.analytics.financial.interestrate.InstrumentDerivative;
import com.opengamma.analytics.financial.interestrate.bond.definition.BondFixedTransaction;
import com.opengamma.analytics.financial.provider.description.interestrate.IssuerProvider;
import com.opengamma.core.config.ConfigSource;
import com.opengamma.core.security.SecuritySource;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.function.AbstractFunction;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.function.FunctionExecutionContext;
import com.opengamma.engine.function.FunctionInputs;
import com.opengamma.engine.target.ComputationTargetType;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.financial.OpenGammaCompilationContext;
import com.opengamma.financial.analytics.curve.exposure.ConfigDBInstrumentExposuresProvider;
import com.opengamma.financial.analytics.model.BondFunctionUtils;
import com.opengamma.financial.security.FinancialSecurity;
import com.opengamma.financial.security.bond.BondSecurity;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.async.AsynchronousExecution;

/**
 *
 */
public abstract class BondFromYieldAndCurvesFunction extends AbstractFunction.NonCompiledInvoker {
  /** The logger */
  private static final Logger s_logger = LoggerFactory.getLogger(BondZSpreadFromYieldFunction.class);
  /** The value requirement name */
  private final String _valueRequirementName;

  /**
   * @param valueRequirementName The value requirement name, not null
   */
  public BondFromYieldAndCurvesFunction(final String valueRequirementName) {
    ArgumentChecker.notNull(valueRequirementName, "value requirement");
    _valueRequirementName = valueRequirementName;
  }

  @Override
  public Set<ComputedValue> execute(final FunctionExecutionContext executionContext, final FunctionInputs inputs, final ComputationTarget target,
      final Set<ValueRequirement> desiredValues) throws AsynchronousExecution {
    final ValueRequirement desiredValue = Iterables.getOnlyElement(desiredValues);
    final ValueProperties properties = desiredValue.getConstraints();
    final ZonedDateTime now = ZonedDateTime.now(executionContext.getValuationClock());
    final Double cleanPrice = (Double) inputs.getValue(MARKET_YTM);
    final InstrumentDerivative derivative = BondFunctionUtils.getDerivative(executionContext, target, now);
    final BondFixedTransaction bond = (BondFixedTransaction) derivative;
    final IssuerProvider issuerCurves = (IssuerProvider) inputs.getValue(CURVE_BUNDLE);
    final ValueSpecification spec = new ValueSpecification(_valueRequirementName, target.toSpecification(), properties);
    return getResult(inputs, bond, issuerCurves, cleanPrice, spec);
  }

  @Override
  public ComputationTargetType getTargetType() {
    return ComputationTargetType.TRADE;
  }

  @Override
  public boolean canApplyTo(final FunctionCompilationContext context, final ComputationTarget target) {
    return target.getTrade().getSecurity() instanceof BondSecurity;
  }

  @Override
  public Set<ValueSpecification> getResults(final FunctionCompilationContext context, final ComputationTarget target) {
    final ValueProperties properties = getResultProperties(target).get();
    return Collections.singleton(new ValueSpecification(_valueRequirementName, target.toSpecification(), properties));
  }

  @Override
  public Set<ValueRequirement> getRequirements(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue) {
    final ValueProperties constraints = desiredValue.getConstraints();
    final Set<String> curveExposureConfigs = constraints.getValues(CURVE_EXPOSURES);
    if (curveExposureConfigs == null || curveExposureConfigs.size() != 1) {
      return null;
    }
    final FinancialSecurity security = (FinancialSecurity) target.getTrade().getSecurity();
    final Set<ValueRequirement> requirements = new HashSet<>();
    requirements.add(new ValueRequirement(MARKET_YTM, ComputationTargetSpecification.of(security), ValueProperties.builder().get()));
    try {
      final ConfigSource configSource = OpenGammaCompilationContext.getConfigSource(context);
      final SecuritySource securitySource = OpenGammaCompilationContext.getSecuritySource(context);
      final ConfigDBInstrumentExposuresProvider exposureSource = new ConfigDBInstrumentExposuresProvider(configSource, securitySource);
      for (final String curveExposureConfig : curveExposureConfigs) {
        final Set<String> curveConstructionConfigurationNames = exposureSource.getCurveConstructionConfigurationsForConfig(curveExposureConfig, security);
        for (final String curveConstructionConfigurationName : curveConstructionConfigurationNames) {
          final ValueProperties properties = ValueProperties.builder()
              .with(CURVE_CONSTRUCTION_CONFIG, curveConstructionConfigurationName).get();
          requirements.add(new ValueRequirement(CURVE_BUNDLE, ComputationTargetSpecification.NULL, properties));
        }
      }
      return requirements;
    } catch (final Exception e) {
      s_logger.error(e.getMessage(), e);
      return null;
    }
  }

  /**
   * Gets the value properties of the result
   * @param target The computation target
   * @return The properties
   */
  protected ValueProperties.Builder getResultProperties(final ComputationTarget target) {
    return createValueProperties()
        .with(CALCULATION_METHOD, YIELD_METHOD)
        .withAny(CURVE_EXPOSURES);
  }

  /**
   * Calculates the result.
   * @param inputs The function inputs
   * @param bond The bond transaction
   * @param issuerCurves The issuer and discounting curves
   * @param cleanPrice The clean price of the bond
   * @param spec The result specification
   * @return The set of results
   */
  protected abstract Set<ComputedValue> getResult(FunctionInputs inputs, BondFixedTransaction bond, IssuerProvider issuerCurves,
      double cleanPrice, ValueSpecification spec);

}