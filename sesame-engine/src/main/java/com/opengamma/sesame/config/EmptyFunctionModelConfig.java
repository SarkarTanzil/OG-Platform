/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.config;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.sesame.function.Parameter;

/**
 * Represents an empty FunctionModelConfig.
 */
// Class was originally an anonymous implementation inside {@link FunctionModelConfig}
// but that was amenable to fudge serialization
@BeanDefinition(builderScope = "private")
public final class EmptyFunctionModelConfig implements FunctionModelConfig, ImmutableBean {

  /**
   * Creates the singleton instance.
   */
  private static final EmptyFunctionModelConfig INSTANCE = new EmptyFunctionModelConfig();

  /**
   * Returns the singleton instance.
   * 
   * @return the singleton, not null
   */
  public static FunctionModelConfig getInstance() {
    return INSTANCE;
  }

  @Override
  public Class<?> getFunctionImplementation(Class<?> functionType) {
    return null;
  }

  @Override
  public Class<?> getFunctionImplementation(Parameter parameter) {
    return null;
  }

  @Override
  public FunctionArguments getFunctionArguments(Class<?> functionType) {
    return FunctionArguments.EMPTY;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code EmptyFunctionModelConfig}.
   * @return the meta-bean, not null
   */
  public static EmptyFunctionModelConfig.Meta meta() {
    return EmptyFunctionModelConfig.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(EmptyFunctionModelConfig.Meta.INSTANCE);
  }

  private EmptyFunctionModelConfig() {
  }

  @Override
  public EmptyFunctionModelConfig.Meta metaBean() {
    return EmptyFunctionModelConfig.Meta.INSTANCE;
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
  @Override
  public EmptyFunctionModelConfig clone() {
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(32);
    buf.append("EmptyFunctionModelConfig{");
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code EmptyFunctionModelConfig}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null);

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    public EmptyFunctionModelConfig.Builder builder() {
      return new EmptyFunctionModelConfig.Builder();
    }

    @Override
    public Class<? extends EmptyFunctionModelConfig> beanType() {
      return EmptyFunctionModelConfig.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code EmptyFunctionModelConfig}.
   */
  private static final class Builder extends DirectFieldsBeanBuilder<EmptyFunctionModelConfig> {

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      throw new NoSuchElementException("Unknown property: " + propertyName);
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      throw new NoSuchElementException("Unknown property: " + propertyName);
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public EmptyFunctionModelConfig build() {
      return new EmptyFunctionModelConfig();
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      return "EmptyFunctionModelConfig.Builder{}";
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
