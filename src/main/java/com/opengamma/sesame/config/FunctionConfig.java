/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.BasicImmutableBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableMap;
import com.opengamma.sesame.function.EngineFunctionUtils;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.tuple.Pair;

/**
 * TODO better name - this is the config for one output (column/target type)
 * TreeConfig? GraphConfig?
 */
@BeanDefinition
public final class FunctionConfig implements ImmutableBean {

  public static final FunctionConfig EMPTY =
      new FunctionConfig(Collections.<Pair<String, Class<?>>, Class<?>>emptyMap(),
                         Collections.<Class<?>, Class<?>>emptyMap(),
                         Collections.<Class<?>, FunctionArguments>emptyMap());

  // TODO make the key a real class?
  /** Map of output name and target type to function type. */
  @PropertyDefinition(validate = "notNull", get = "private")
  private final ImmutableMap<Pair<String, Class<?>>, Class<?>> _outputFunctions;

  // TODO another set of mappings that are equivalent to these but qualified by the type of the object with the dependency?
  // would allow different impls of the same interface to be used at different points in the same tree
  // or is that too much complication to be worth it?

  /** Map of function types to their implementing classes where the implementing class isn't the default. */
  @PropertyDefinition(validate = "notNull", get = "private")
  private final ImmutableMap<Class<?>, Class<?>> _implementationOverrides;

  /** User-specified function arguments, keyed by the function implementation type. */
  @PropertyDefinition(validate = "notNull", get = "private")
  private final ImmutableMap<Class<?>, FunctionArguments> _arguments;

  public Class<?> getFunctionImplementation(Class<?> functionInterface) {
    if (_implementationOverrides.containsKey(functionInterface)) {
      return _implementationOverrides.get(functionInterface);
    } else {
      // TODO allow default impl to be specified without an annotation? or to be overridden in config?
      return EngineFunctionUtils.getDefaultImplementation(functionInterface);
    }
  }

  public FunctionArguments getFunctionArguments(Class<?> functionImplementationType) {
    FunctionArguments functionArguments = _arguments.get(functionImplementationType);
    if (functionArguments == null) {
      return FunctionArguments.EMPTY;
    } else {
      return functionArguments;
    }
  }

  @ImmutableConstructor
  public FunctionConfig(Map<Pair<String, Class<?>>, Class<?>> outputFunctions,
                        Map<Class<?>, Class<?>> implementationOverrides,
                        Map<Class<?>, FunctionArguments> arguments) {
    ArgumentChecker.notNull(outputFunctions, "outputFunctions");
    ArgumentChecker.notNull(implementationOverrides, "implementationOverrides");
    ArgumentChecker.notNull(arguments, "arguments");
    _outputFunctions = ImmutableMap.copyOf(outputFunctions);
    _implementationOverrides = ImmutableMap.copyOf(implementationOverrides);
    _arguments = ImmutableMap.copyOf(arguments);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code FunctionConfig}.
   * @return the meta-bean, not null
   */
  public static FunctionConfig.Meta meta() {
    return FunctionConfig.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(FunctionConfig.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   *
   * @return the builder, not null
   */
  public static FunctionConfig.Builder builder() {
    return new FunctionConfig.Builder();
  }

  @Override
  public FunctionConfig.Meta metaBean() {
    return FunctionConfig.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets map of output name and target type to function type.
   * @return the value of the property, not null
   */
  private ImmutableMap<Pair<String, Class<?>>, Class<?>> getOutputFunctions() {
    return _outputFunctions;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets map of function types to their implementing classes where the implementing class isn't the default.
   * @return the value of the property, not null
   */
  private ImmutableMap<Class<?>, Class<?>> getImplementationOverrides() {
    return _implementationOverrides;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets user-specified function arguments, keyed by the function implementation type.
   * @return the value of the property, not null
   */
  private ImmutableMap<Class<?>, FunctionArguments> getArguments() {
    return _arguments;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public FunctionConfig clone() {
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      FunctionConfig other = (FunctionConfig) obj;
      return JodaBeanUtils.equal(getOutputFunctions(), other.getOutputFunctions()) &&
          JodaBeanUtils.equal(getImplementationOverrides(), other.getImplementationOverrides()) &&
          JodaBeanUtils.equal(getArguments(), other.getArguments());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getOutputFunctions());
    hash += hash * 31 + JodaBeanUtils.hashCode(getImplementationOverrides());
    hash += hash * 31 + JodaBeanUtils.hashCode(getArguments());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("FunctionConfig{");
    buf.append("outputFunctions").append('=').append(getOutputFunctions()).append(',').append(' ');
    buf.append("implementationOverrides").append('=').append(getImplementationOverrides()).append(',').append(' ');
    buf.append("arguments").append('=').append(JodaBeanUtils.toString(getArguments()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code FunctionConfig}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code outputFunctions} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableMap<Pair<String, Class<?>>, Class<?>>> _outputFunctions = DirectMetaProperty.ofImmutable(
        this, "outputFunctions", FunctionConfig.class, (Class) ImmutableMap.class);
    /**
     * The meta-property for the {@code implementationOverrides} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableMap<Class<?>, Class<?>>> _implementationOverrides = DirectMetaProperty.ofImmutable(
        this, "implementationOverrides", FunctionConfig.class, (Class) ImmutableMap.class);
    /**
     * The meta-property for the {@code arguments} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableMap<Class<?>, FunctionArguments>> _arguments = DirectMetaProperty.ofImmutable(
        this, "arguments", FunctionConfig.class, (Class) ImmutableMap.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "outputFunctions",
        "implementationOverrides",
        "arguments");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -831152230:  // outputFunctions
          return _outputFunctions;
        case -542516587:  // implementationOverrides
          return _implementationOverrides;
        case -2035517098:  // arguments
          return _arguments;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public FunctionConfig.Builder builder() {
      return new FunctionConfig.Builder();
    }

    @Override
    public Class<? extends FunctionConfig> beanType() {
      return FunctionConfig.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code outputFunctions} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableMap<Pair<String, Class<?>>, Class<?>>> outputFunctions() {
      return _outputFunctions;
    }

    /**
     * The meta-property for the {@code implementationOverrides} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableMap<Class<?>, Class<?>>> implementationOverrides() {
      return _implementationOverrides;
    }

    /**
     * The meta-property for the {@code arguments} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ImmutableMap<Class<?>, FunctionArguments>> arguments() {
      return _arguments;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -831152230:  // outputFunctions
          return ((FunctionConfig) bean).getOutputFunctions();
        case -542516587:  // implementationOverrides
          return ((FunctionConfig) bean).getImplementationOverrides();
        case -2035517098:  // arguments
          return ((FunctionConfig) bean).getArguments();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code FunctionConfig}.
   */
  public static final class Builder extends BasicImmutableBeanBuilder<FunctionConfig> {

    private Map<Pair<String, Class<?>>, Class<?>> _outputFunctions = new HashMap<Pair<String, Class<?>>, Class<?>>();
    private Map<Class<?>, Class<?>> _implementationOverrides = new HashMap<Class<?>, Class<?>>();
    private Map<Class<?>, FunctionArguments> _arguments = new HashMap<Class<?>, FunctionArguments>();

    /**
     * Restricted constructor.
     */
    private Builder() {
      super(FunctionConfig.Meta.INSTANCE);
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(FunctionConfig beanToCopy) {
      super(FunctionConfig.Meta.INSTANCE);
      this._outputFunctions = new HashMap<Pair<String, Class<?>>, Class<?>>(beanToCopy.getOutputFunctions());
      this._implementationOverrides = new HashMap<Class<?>, Class<?>>(beanToCopy.getImplementationOverrides());
      this._arguments = new HashMap<Class<?>, FunctionArguments>(beanToCopy.getArguments());
    }

    //-----------------------------------------------------------------------
    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -831152230:  // outputFunctions
          this._outputFunctions = (Map<Pair<String, Class<?>>, Class<?>>) newValue;
          break;
        case -542516587:  // implementationOverrides
          this._implementationOverrides = (Map<Class<?>, Class<?>>) newValue;
          break;
        case -2035517098:  // arguments
          this._arguments = (Map<Class<?>, FunctionArguments>) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public FunctionConfig build() {
      return new FunctionConfig(
          _outputFunctions,
          _implementationOverrides,
          _arguments);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code outputFunctions} property in the builder.
     * @param outputFunctions  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder outputFunctions(Map<Pair<String, Class<?>>, Class<?>> outputFunctions) {
      JodaBeanUtils.notNull(outputFunctions, "outputFunctions");
      this._outputFunctions = outputFunctions;
      return this;
    }

    /**
     * Sets the {@code implementationOverrides} property in the builder.
     * @param implementationOverrides  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder implementationOverrides(Map<Class<?>, Class<?>> implementationOverrides) {
      JodaBeanUtils.notNull(implementationOverrides, "implementationOverrides");
      this._implementationOverrides = implementationOverrides;
      return this;
    }

    /**
     * Sets the {@code arguments} property in the builder.
     * @param arguments  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder arguments(Map<Class<?>, FunctionArguments> arguments) {
      JodaBeanUtils.notNull(arguments, "arguments");
      this._arguments = arguments;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("FunctionConfig.Builder{");
      buf.append("outputFunctions").append('=').append(_outputFunctions).append(',').append(' ');
      buf.append("implementationOverrides").append('=').append(_implementationOverrides).append(',').append(' ');
      buf.append("arguments").append('=').append(_arguments);
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
