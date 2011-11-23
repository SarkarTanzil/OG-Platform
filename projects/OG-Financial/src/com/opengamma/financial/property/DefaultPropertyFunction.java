/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.property;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.ComputationTargetSpecification;
import com.opengamma.engine.ComputationTargetType;
import com.opengamma.engine.function.AbstractFunction;
import com.opengamma.engine.function.CompiledFunctionDefinition;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.function.FunctionExecutionContext;
import com.opengamma.engine.function.FunctionInputs;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueSpecification;

/**
 * Abstract function for injecting default properties into the dependency graph.
 */
public abstract class DefaultPropertyFunction extends AbstractFunction.NonCompiledInvoker {

  private static final Logger s_logger = LoggerFactory.getLogger(DefaultPropertyFunction.class);

  /**
   * The priority class of {@link DefaultPropertyFunction} instances, allowing them to
   * be ordered relative to each other.
   */
  public static enum PriorityClass {

    /**
     * Must apply before other default properties.
     */
    ABOVE_NORMAL(1),
    /**
     * Normal application.
     */
    NORMAL(0),
    /**
     * Must apply after other default properties.
     */
    BELOW_NORMAL(-1);

    private final int _level;

    private PriorityClass(final int level) {
      _level = 1;
    }

    /**
     * Returns the priority level adjuster - an integer MIN_ADJUST .. MAX_ADJUST
     * 
     * @return priority level adjustment
     */
    public int getPriorityAdjust() {
      return _level;
    }

    /**
     * Maximum integer that can be returned by {@link #getPriorityAdjust}.
     */
    public static final int MAX_ADJUST = 1;

    /**
     * Minimum integer that can be returned by {@link #getPriorityAdjust}.
     */
    public static final int MIN_ADJUST = -1;

  };

  private final ComputationTargetType _targetType;
  private final boolean _permitWithout;

  protected DefaultPropertyFunction(final ComputationTargetType targetType, final boolean permitWithout) {
    _targetType = targetType;
    _permitWithout = permitWithout;
  }

  public boolean isPermitWithout() {
    return _permitWithout;
  }

  @Override
  public ComputationTargetType getTargetType() {
    return _targetType;
  }

  /**
   * Callback object used by the implementation of {@link #getDefaults}.
   */
  public static final class PropertyDefaults {

    private final Map<String, Set<String>> _valueName2PropertyNames = new HashMap<String, Set<String>>();

    private PropertyDefaults() {
    }

    public void addValuePropertyName(final String valueName, final String propertyName) {
      Set<String> propertyNames = _valueName2PropertyNames.get(valueName);
      if (propertyNames == null) {
        propertyNames = new HashSet<String>();
        _valueName2PropertyNames.put(valueName, propertyNames);
      }
      propertyNames.add(propertyName);
    }

    private Map<String, Set<String>> getValueName2PropertyNames() {
      return _valueName2PropertyNames;
    }

  }

  /**
   * Returns the defaults that are available
   * 
   * @param context the compilation context, not null
   * @param target the target being evaluated, not null
   * @param defaults the callback object to return the property and value names on, not null
   */
  protected abstract void getDefaults(FunctionCompilationContext context, ComputationTarget target, PropertyDefaults defaults);

  private PropertyDefaults getDefaults(final FunctionCompilationContext context, final ComputationTarget target) {
    final PropertyDefaults defaults = new PropertyDefaults();
    getDefaults(context, target, defaults);
    if (defaults.getValueName2PropertyNames().isEmpty()) {
      s_logger.debug("No default properties for {}", target);
      return null;
    } else {
      s_logger.debug("Found {} values with default properties for {}", defaults.getValueName2PropertyNames().size(), target);
      return defaults;
    }
  }

  /**
   * Returns the default value(s) to set for the property. If a default value is
   * not available, must return null.
   * 
   * @param context the function compilation context, not null
   * @param target the computation target, not null
   * @param desiredValue the initial requirement, lacking the property to be injected, not null
   * @param propertyName the property name to be injected
   * @return the default values or null if there is no default to inject
   */
  protected abstract Set<String> getDefaultValue(FunctionCompilationContext context, ComputationTarget target, ValueRequirement desiredValue, String propertyName);

  @Override
  public Set<ComputedValue> execute(FunctionExecutionContext executionContext, FunctionInputs inputs, ComputationTarget target, Set<ValueRequirement> desiredValues) {
    throw new IllegalStateException("This function should never be executed");
  }

  /**
   * Performs the {@link CompiledFunctionDefinition#canApplyTo} test by checking whether any defaults
   * are returned for the target. If the {@link #getDefaults} cost is high, then consider overloading
   * this method with something cheaper.
   * 
   * @param context the compilation context, not null
   * @param target computation target, not null
   * @return true if applies (i.e. there are defaults available), false otherwise
   */
  @Override
  public boolean canApplyTo(final FunctionCompilationContext context, final ComputationTarget target) {
    return getDefaults(context, target) != null;
  }

  @Override
  public Set<ValueRequirement> getRequirements(final FunctionCompilationContext context, final ComputationTarget target, final ValueRequirement desiredValue) {
    final PropertyDefaults defaults = getDefaults(context, target);
    final ValueProperties.Builder constraints = desiredValue.getConstraints().copy();
    boolean matched = false;
    for (String propertyName : defaults.getValueName2PropertyNames().get(desiredValue.getValueName())) {
      if (desiredValue.getConstraints().getValues(propertyName) == null) {
        s_logger.debug("Matched default property {} for {}", propertyName, desiredValue);
        final Set<String> values = getDefaultValue(context, target, desiredValue, propertyName);
        if (values != null) {
          if (values.isEmpty()) {
            s_logger.debug("Matched ANY");
            constraints.withAny(propertyName);
          } else {
            s_logger.debug("Matched {}", values);
            constraints.with(propertyName, values);
          }
          matched = true;
        } else {
          s_logger.debug("No default values");
        }
      }
    }
    if (!matched) {
      // No default values were found
      s_logger.debug("No matched values");
      return null;
    }
    final ValueRequirement reduction = new ValueRequirement(desiredValue.getValueName(), desiredValue.getTargetSpecification(), constraints.get());
    s_logger.debug("Reduced to {}", reduction);
    return Collections.singleton(reduction);
  }

  @Override
  public Set<ValueSpecification> getResults(final FunctionCompilationContext context, final ComputationTarget target) {
    final PropertyDefaults defaults = getDefaults(context, target);
    if (defaults == null) {
      // If canApplyTo is overloaded, we can't assert that getDefaults produces something non-empty
      s_logger.debug("No defaults for {}", target);
      return null;
    }
    final ComputationTargetSpecification targetSpec = target.toSpecification();
    final Set<ValueSpecification> result = new HashSet<ValueSpecification>();
    for (Map.Entry<String, Set<String>> valueName2PropertyNames : defaults.getValueName2PropertyNames().entrySet()) {
      final String valueName = valueName2PropertyNames.getKey();
      for (String propertyName : valueName2PropertyNames.getValue()) {
        result.add(new ValueSpecification(valueName, targetSpec, ValueProperties.all().withoutAny(propertyName)));
      }
    }
    s_logger.debug("Produced results {} for {}", result, target);
    return result;
  }

  @Override
  public Set<ValueSpecification> getResults(final FunctionCompilationContext context, final ComputationTarget target, final Map<ValueSpecification, ValueRequirement> inputs) {
    // Pass the inputs through unchanged - will cause suppression of this node from the graph
    return inputs.keySet();
  }

  public PriorityClass getPriority() {
    return PriorityClass.NORMAL;
  }

}
