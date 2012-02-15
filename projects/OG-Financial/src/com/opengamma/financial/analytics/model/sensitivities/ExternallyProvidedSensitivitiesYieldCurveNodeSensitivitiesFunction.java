/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.sensitivities;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.time.calendar.Period;

import org.fudgemsg.FudgeMsgEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.collections.Lists;

import com.google.common.collect.Sets;
import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.core.security.Security;
import com.opengamma.core.security.SecuritySource;
import com.opengamma.core.value.ExternalDataRequirementNames;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.ComputationTargetType;
import com.opengamma.engine.function.AbstractFunction;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.function.FunctionExecutionContext;
import com.opengamma.engine.function.FunctionInputs;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValuePropertyNames;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.financial.analytics.DoubleLabelledMatrix1D;
import com.opengamma.financial.analytics.LabelledMatrix1D;
import com.opengamma.financial.analytics.StringLabelledMatrix1D;
import com.opengamma.financial.analytics.fixedincome.FixedIncomeInstrumentCurveExposureHelper;
import com.opengamma.financial.analytics.ircurve.FixedIncomeStripWithSecurity;
import com.opengamma.financial.analytics.ircurve.InterpolatedYieldAndDiscountCurveFunction;
import com.opengamma.financial.analytics.ircurve.InterpolatedYieldCurveSpecificationWithSecurities;
import com.opengamma.financial.analytics.ircurve.YieldCurveFunction;
import com.opengamma.financial.analytics.model.YieldCurveNodeSensitivitiesHelper;
import com.opengamma.financial.analytics.model.ircurve.MarketInstrumentImpliedYieldCurveFunction;
import com.opengamma.financial.interestrate.YieldCurveBundle;
import com.opengamma.financial.model.interestrate.curve.YieldAndDiscountCurve;
import com.opengamma.financial.security.FinancialSecurityUtils;
import com.opengamma.financial.sensitivities.FactorExposureData;
import com.opengamma.financial.sensitivities.FactorExposureDataComparator;
import com.opengamma.financial.sensitivities.FactorType;
import com.opengamma.financial.sensitivities.RawSecurityUtils;
import com.opengamma.financial.sensitivities.SecurityEntryData;
import com.opengamma.id.ExternalId;
import com.opengamma.id.UniqueId;
import com.opengamma.master.security.RawSecurity;
import com.opengamma.math.matrix.DoubleMatrix1D;
import com.opengamma.util.fudgemsg.OpenGammaFudgeContext;
import com.opengamma.util.money.Currency;

/**
 * 
 */
public class ExternallyProvidedSensitivitiesYieldCurveNodeSensitivitiesFunction extends AbstractFunction.NonCompiledInvoker {
  private static final Logger s_logger = LoggerFactory.getLogger(ExternallyProvidedSensitivitiesYieldCurveNodeSensitivitiesFunction.class);
  /**
   * The value name calculated by this function.
   */
  public static final String YCNS_REQUIREMENT = ValueRequirementNames.YIELD_CURVE_NODE_SENSITIVITIES;
  public static final String EXTERNAL_SENSITIVITIES_REQUIREMENT = ValueRequirementNames.EXTERNAL_SENSITIVITIES;
  private static final CharSequence SWAP_TEXT = "SWAP";

  @Override
  public void init(final FunctionCompilationContext context) {

  }

  @Override
  public ComputationTargetType getTargetType() {
    return ComputationTargetType.POSITION;
  }

  @Override
  public boolean canApplyTo(final FunctionCompilationContext context, final ComputationTarget target) {
    if (!(target.getPosition().getSecurity() instanceof RawSecurity)) {
      return false;
    }
    final RawSecurity security = (RawSecurity) target.getPosition().getSecurity();
    return security.getSecurityType().equals(SecurityEntryData.EXTERNAL_SENSITIVITIES_SECURITY_TYPE);
  }

  private ValueProperties.Builder createValueProperties(final ComputationTarget target) {
    final Security security = (Security) target.getPosition().getSecurity();
    final ValueProperties.Builder properties = createValueProperties();
    FixedIncomeInstrumentCurveExposureHelper.valuePropertiesForSecurity(security, properties);
    properties.with(ValuePropertyNames.CURVE_CALCULATION_METHOD, MarketInstrumentImpliedYieldCurveFunction.PRESENT_VALUE_STRING);
    return properties;
  }
  
  private ValueProperties.Builder createCurrencyValueProperties(final ComputationTarget target) {
    final RawSecurity security = (RawSecurity) target.getPosition().getSecurity();
    SecurityEntryData securityEntryData = RawSecurityUtils.deserialize(security);
    final Currency ccy = securityEntryData.getCurrency();
    final ValueProperties.Builder properties = createValueProperties();
    properties.with(ValuePropertyNames.CURRENCY, ccy.getCode());
    return properties;    
  }

  @Override
  public Set<ValueSpecification> getResults(final FunctionCompilationContext context, final ComputationTarget target) {
    final ValueProperties.Builder properties = createValueProperties(target);
    properties.withAny(ValuePropertyNames.CURVE);
    final Set<ValueSpecification> results = Sets.newHashSetWithExpectedSize(2);
    final ComputationTargetSpecification targetSpec = target.toSpecification();
    results.add(new ValueSpecification(YCNS_REQUIREMENT, targetSpec, properties.get()));
    
    final ValueProperties.Builder externalProperties = createValueProperties(target);
    externalProperties.withAny(ValuePropertyNames.CURRENCY);
    results.add(new ValueSpecification(EXTERNAL_SENSITIVITIES_REQUIREMENT, targetSpec, externalProperties.get()));
    s_logger.warn("getResults(1) = " + results);
    return results;
  }

  @Override
  public Set<ValueRequirement> getRequirements(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue) {
    final Set<String> curves = desiredValue.getConstraints().getValues(ValuePropertyNames.CURVE);
    
    if ((curves == null) || (curves.size() != 1)) {
      // Can't support an unbound request; an injection function must be used (or declare all as optional and use [PLAT-1771])
      return null;
    }
    // TODO: if "CURVE" is specified, check that it is one of the forward/funding curve names
    final String curve = curves.iterator().next();
    final Set<ValueRequirement> requirements = Sets.newHashSetWithExpectedSize(6);
    
    requirements.add(getCurveRequirement(target, curve, curve, curve));
    requirements.add(getCurveSpecRequirement(target, curve));
    
    requirements.addAll(getSensitivityRequirements(context.getSecuritySource(), (RawSecurity) target.getPosition().getSecurity()));

    return requirements;
  }
  
  protected Set<ValueRequirement> getSensitivityRequirements(SecuritySource secSource, RawSecurity rawSecurity) {
    Set<ValueRequirement> requirements = Sets.newHashSet();
    Collection<FactorExposureData> decodedSensitivities = decodeSensitivities(secSource, rawSecurity);
    for (FactorExposureData exposureEntry : decodedSensitivities) {
      requirements.add(getSensitivityRequirement(exposureEntry.getExposureExternalId()));
    }
    return requirements;
  }

  @Override
  public Set<ValueSpecification> getResults(final FunctionCompilationContext context, final ComputationTarget target, final Map<ValueSpecification, ValueRequirement> inputs) {
    String curveName = null;
    for (Map.Entry<ValueSpecification, ValueRequirement> input : inputs.entrySet()) {
      if (ValueRequirementNames.YIELD_CURVE.equals(input.getKey().getValueName())) {
        assert curveName == null;
        curveName = input.getKey().getProperty(ValuePropertyNames.CURVE);
      } 
    }
    assert curveName != null;
    final ValueProperties.Builder properties = createValueProperties(target);
    properties.with(ValuePropertyNames.CURVE, curveName);
    final ComputationTargetSpecification targetSpec = target.toSpecification();
    final Set<ValueSpecification> results = Sets.newHashSetWithExpectedSize(2);
    results.add(new ValueSpecification(YCNS_REQUIREMENT, targetSpec, properties.get()));
    results.add(new ValueSpecification(EXTERNAL_SENSITIVITIES_REQUIREMENT, targetSpec, createCurrencyValueProperties(target).get()));
    s_logger.warn("getResults(2) returning " + results);
    return results;
  }

  @Override
  public Set<ComputedValue> execute(final FunctionExecutionContext executionContext, final FunctionInputs inputs,
                                    final ComputationTarget target, final Set<ValueRequirement> desiredValues) {
    String curveName = null;
    for (ValueRequirement requirement : desiredValues) {
      final ValueProperties constraints = requirement.getConstraints();
      Set<String> values = constraints.getValues(ValuePropertyNames.CURVE);
      if (values != null) {
        curveName = values.iterator().next();
      }
    }
    assert curveName != null;
    final RawSecurity security = (RawSecurity) target.getPosition().getSecurity();
    final ValueRequirement curveRequirement = getCurveRequirement(target, curveName, null, null);
    final Object curveObject = inputs.getValue(curveRequirement);
    if (curveObject == null) {
      throw new OpenGammaRuntimeException("Could not get " + curveRequirement);
    }
    Object curveSpecObject = null;
    final ValueRequirement curveSpecRequirement = getCurveSpecRequirement(target, curveName);
    curveSpecObject = inputs.getValue(curveSpecRequirement);
    if (curveSpecObject == null) {
      throw new OpenGammaRuntimeException("Could not get " + curveSpecRequirement);
    }
    final YieldAndDiscountCurve curve = (YieldAndDiscountCurve) curveObject;
    final InterpolatedYieldCurveSpecificationWithSecurities curveSpec = (InterpolatedYieldCurveSpecificationWithSecurities) curveSpecObject;
    final LinkedHashMap<String, YieldAndDiscountCurve> interpolatedCurves = new LinkedHashMap<String, YieldAndDiscountCurve>();
    interpolatedCurves.put(curveName, curve);
    final YieldCurveBundle bundle = new YieldCurveBundle(interpolatedCurves);
    DoubleMatrix1D sensitivitiesForCurves = getSensitivities(executionContext.getSecuritySource(), inputs, security, curveSpec, curve);
    final ValueProperties.Builder properties = createValueProperties(target);
    properties.with(ValuePropertyNames.CURVE, curveName);
    final ComputationTargetSpecification targetSpec = target.toSpecification();
    final ValueSpecification resultSpec = new ValueSpecification(YCNS_REQUIREMENT, targetSpec, properties.with(ValuePropertyNames.CURVE, curveName).get());
    Set<ComputedValue> ycnsResults = YieldCurveNodeSensitivitiesHelper.getSensitivitiesForCurve(curveName, bundle, sensitivitiesForCurves, curveSpec, resultSpec);
    Set<ComputedValue> externalResults = getResultsForExternalRiskFactors(executionContext.getSecuritySource(), inputs, target, security);
    Set<ComputedValue> results = Sets.newHashSet();
    results.addAll(ycnsResults);
    results.addAll(externalResults);
    //s_logger.warn("execute, returning " + results);
    return results;
  }
  
  private Set<ComputedValue> getResultsForExternalRiskFactors(SecuritySource secSource, FunctionInputs inputs, ComputationTarget target, RawSecurity security) {
    List<FactorExposureData> factors = decodeSensitivities(secSource, security);
    Collections.sort(factors, new FactorExposureDataComparator());
    
    List<String> indices = Lists.newArrayList();
    List<String> labels = Lists.newArrayList();
    DoubleList values = new DoubleArrayList();
    for (FactorExposureData factor : factors) {
      ComputedValue computedValue = inputs.getComputedValue(getSensitivityRequirement(factor.getExposureExternalId()));
      if (computedValue != null) {
        indices.add(factor.getExposureExternalId().toString());
        labels.add(factor.getExposureExternalId().getValue());
        values.add((Double) computedValue.getValue());
      } else {
        s_logger.error("Value was null when getting required input data " + factor.getExposureExternalId());
      }
    }
    StringLabelledMatrix1D labelledMatrix = new StringLabelledMatrix1D(indices.toArray(new String[] {}), labels.toArray(), values.toDoubleArray());
    ValueSpecification valueSpecification = new ValueSpecification(EXTERNAL_SENSITIVITIES_REQUIREMENT, target.toSpecification(), createCurrencyValueProperties(target).get());
    return Collections.singleton(new ComputedValue(valueSpecification, labelledMatrix));
  }

  private DoubleMatrix1D getSensitivities(SecuritySource secSource, FunctionInputs inputs, RawSecurity rawSecurity, InterpolatedYieldCurveSpecificationWithSecurities curveSpec, 
                                          YieldAndDiscountCurve curve) {
    Collection<FactorExposureData> decodedSensitivities = decodeSensitivities(secSource, rawSecurity);
    double[] entries = new double[curveSpec.getStrips().size()];
    int i = 0;
    for (FixedIncomeStripWithSecurity strip : curveSpec.getStrips()) {
      FactorExposureData externalSensitivitiesData = searchForTenorMatch(decodedSensitivities, strip);
      if (externalSensitivitiesData != null) {
        ComputedValue computedValue = inputs.getComputedValue(getSensitivityRequirement(externalSensitivitiesData.getExposureExternalId()));
        if (computedValue != null) {
          Double value = (Double) computedValue.getValue();
          entries[i] = value;
        } else {
          s_logger.error("Value was null when getting required input data " + externalSensitivitiesData.getExposureExternalId());
          entries[i] = 0d;
        }
      } else {
        entries[i] = 0d;
      }
      i++;
    }
    return new DoubleMatrix1D(entries);
  }  
  
  private FactorExposureData searchForTenorMatch(Collection<FactorExposureData> exposures, FixedIncomeStripWithSecurity strip) {
    for (FactorExposureData exposure : exposures) {
      if (exposure.getFactorType().equals(FactorType.YIELD) && exposure.getFactorName().contains(SWAP_TEXT)) {
        if (exposure.getNode() != null && exposure.getNode().length() > 0) {
          Period nodePeriod = Period.parse("P" + exposure.getNode());
          if (strip.getTenor().getPeriod().totalMonths() == nodePeriod.totalMonths()) {
            return exposure;
          }
        }
      }
    }
    return null;
  }
  
  private List<FactorExposureData> decodeSensitivities(SecuritySource secSource, RawSecurity rawSecurity) {
    FudgeMsgEnvelope msg = OpenGammaFudgeContext.getInstance().deserialize(rawSecurity.getRawData());
    SecurityEntryData securityEntryData = OpenGammaFudgeContext.getInstance().fromFudgeMsg(SecurityEntryData.class, msg.getMessage());
    RawSecurity underlyingRawSecurity = (RawSecurity) secSource.getSecurity(securityEntryData.getFactorSetId().toBundle());
    if (underlyingRawSecurity != null) {
      FudgeMsgEnvelope factorIdMsg = OpenGammaFudgeContext.getInstance().deserialize(underlyingRawSecurity.getRawData());
      @SuppressWarnings("unchecked")
      List<FactorExposureData> factorExposureDataList = OpenGammaFudgeContext.getInstance().fromFudgeMsg(List.class, factorIdMsg.getMessage());     
      return factorExposureDataList;
    } else {
      throw new OpenGammaRuntimeException("Couldn't find factor list security " + securityEntryData.getFactorSetId());
    }
  }

  @Override
  public String getShortName() {
    return "ExternallyProvidedSensitivitiesYieldCurveNodeSensitivitiesFunction";
  }
  
  protected ValueRequirement getSensitivityRequirement(final ExternalId externalId) {
    return new ValueRequirement(/*ExternalDataRequirementNames.SENSITIVITY*/"EXPOSURE", ComputationTargetType.PRIMITIVE, UniqueId.of(externalId.getScheme().getName(), externalId.getValue()));
  }

  protected ValueRequirement getCurveRequirement(final ComputationTarget target, final String curveName, final String advisoryForwardCurve, final String advisoryFundingCurve) {
    final Currency currency = FinancialSecurityUtils.getCurrency(target.getPosition().getSecurity());
    final ValueProperties.Builder properties = ValueProperties.with(ValuePropertyNames.CURVE, curveName);
    if (advisoryForwardCurve != null) {
      properties.with(YieldCurveFunction.PROPERTY_FORWARD_CURVE, advisoryForwardCurve);
    }
    if (advisoryFundingCurve != null) {
      properties.with(YieldCurveFunction.PROPERTY_FUNDING_CURVE, advisoryFundingCurve);
    }
    properties.with(ValuePropertyNames.CURVE_CALCULATION_METHOD, MarketInstrumentImpliedYieldCurveFunction.PRESENT_VALUE_STRING);
    return new ValueRequirement(ValueRequirementNames.YIELD_CURVE, ComputationTargetType.PRIMITIVE, currency.getUniqueId(), properties.get());
  }

  protected ValueRequirement getCurveSpecRequirement(final ComputationTarget target, final String curveName) {
    final Currency currency = FinancialSecurityUtils.getCurrency(target.getPosition().getSecurity());
    final ValueProperties.Builder properties = ValueProperties.builder().with(ValuePropertyNames.CURVE, curveName);
    return new ValueRequirement(ValueRequirementNames.YIELD_CURVE_SPEC, ComputationTargetType.PRIMITIVE, currency.getUniqueId(), properties.get());
  }

}
