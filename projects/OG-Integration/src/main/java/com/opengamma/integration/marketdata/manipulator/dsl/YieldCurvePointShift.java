/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.integration.marketdata.manipulator.dsl;

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
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

/**
 *
 */
@BeanDefinition
public final class YieldCurvePointShift implements ImmutableBean {

  @PropertyDefinition
  private final int _pointIndex;

  @PropertyDefinition
  private final double _shift;

  @ImmutableConstructor
  /* package */ YieldCurvePointShift(int pointIndex, double shift) {
    _pointIndex = pointIndex;
    _shift = shift;
  }
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code YieldCurvePointShift}.
   * @return the meta-bean, not null
   */
  public static YieldCurvePointShift.Meta meta() {
    return YieldCurvePointShift.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(YieldCurvePointShift.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static YieldCurvePointShift.Builder builder() {
    return new YieldCurvePointShift.Builder();
  }

  @Override
  public YieldCurvePointShift.Meta metaBean() {
    return YieldCurvePointShift.Meta.INSTANCE;
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
   * Gets the pointIndex.
   * @return the value of the property
   */
  public int getPointIndex() {
    return _pointIndex;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the shift.
   * @return the value of the property
   */
  public double getShift() {
    return _shift;
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
  public YieldCurvePointShift clone() {
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      YieldCurvePointShift other = (YieldCurvePointShift) obj;
      return (getPointIndex() == other.getPointIndex()) &&
          JodaBeanUtils.equal(getShift(), other.getShift());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getPointIndex());
    hash += hash * 31 + JodaBeanUtils.hashCode(getShift());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("YieldCurvePointShift{");
    buf.append("pointIndex").append('=').append(getPointIndex()).append(',').append(' ');
    buf.append("shift").append('=').append(JodaBeanUtils.toString(getShift()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code YieldCurvePointShift}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code pointIndex} property.
     */
    private final MetaProperty<Integer> _pointIndex = DirectMetaProperty.ofImmutable(
        this, "pointIndex", YieldCurvePointShift.class, Integer.TYPE);
    /**
     * The meta-property for the {@code shift} property.
     */
    private final MetaProperty<Double> _shift = DirectMetaProperty.ofImmutable(
        this, "shift", YieldCurvePointShift.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "pointIndex",
        "shift");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1245764898:  // pointIndex
          return _pointIndex;
        case 109407362:  // shift
          return _shift;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public YieldCurvePointShift.Builder builder() {
      return new YieldCurvePointShift.Builder();
    }

    @Override
    public Class<? extends YieldCurvePointShift> beanType() {
      return YieldCurvePointShift.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code pointIndex} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Integer> pointIndex() {
      return _pointIndex;
    }

    /**
     * The meta-property for the {@code shift} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> shift() {
      return _shift;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1245764898:  // pointIndex
          return ((YieldCurvePointShift) bean).getPointIndex();
        case 109407362:  // shift
          return ((YieldCurvePointShift) bean).getShift();
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
   * The bean-builder for {@code YieldCurvePointShift}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<YieldCurvePointShift> {

    private int _pointIndex;
    private double _shift;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(YieldCurvePointShift beanToCopy) {
      this._pointIndex = beanToCopy.getPointIndex();
      this._shift = beanToCopy.getShift();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1245764898:  // pointIndex
          return _pointIndex;
        case 109407362:  // shift
          return _shift;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 1245764898:  // pointIndex
          this._pointIndex = (Integer) newValue;
          break;
        case 109407362:  // shift
          this._shift = (Double) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
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
    public YieldCurvePointShift build() {
      return new YieldCurvePointShift(
          _pointIndex,
          _shift);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code pointIndex} property in the builder.
     * @param pointIndex  the new value
     * @return this, for chaining, not null
     */
    public Builder pointIndex(int pointIndex) {
      this._pointIndex = pointIndex;
      return this;
    }

    /**
     * Sets the {@code shift} property in the builder.
     * @param shift  the new value
     * @return this, for chaining, not null
     */
    public Builder shift(double shift) {
      this._shift = shift;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("YieldCurvePointShift.Builder{");
      buf.append("pointIndex").append('=').append(JodaBeanUtils.toString(_pointIndex)).append(',').append(' ');
      buf.append("shift").append('=').append(JodaBeanUtils.toString(_shift));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
