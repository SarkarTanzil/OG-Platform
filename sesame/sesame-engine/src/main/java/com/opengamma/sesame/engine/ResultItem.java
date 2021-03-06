/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.engine;

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

import com.opengamma.sesame.trace.CallGraph;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.result.Result;

/**
*
*/
@BeanDefinition
public final class ResultItem implements ImmutableBean {

  // TODO include OutputName, in the current engine it's used for number formatting

  // TODO can this be non-nullable now? if we can't calculate a result do we return a failure?
  /**
   * The calculated result, which may be a failure.
   */
  @PropertyDefinition
  private final Result<Object> _result;
  // since ResultItem does not have generics, it is best to remove them and use Object
  // this avoids more complex code in callers of getResult()

  @PropertyDefinition
  private final CallGraph _callGraph;

  @SuppressWarnings("unchecked")
  @ImmutableConstructor
  /* package */ ResultItem(Result<?> result, CallGraph callGraph) {
    _result = (Result<Object>) ArgumentChecker.notNull(result, "result");
    _callGraph = callGraph;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ResultItem}.
   * @return the meta-bean, not null
   */
  public static ResultItem.Meta meta() {
    return ResultItem.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ResultItem.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static ResultItem.Builder builder() {
    return new ResultItem.Builder();
  }

  @Override
  public ResultItem.Meta metaBean() {
    return ResultItem.Meta.INSTANCE;
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
   * Gets the calculated result, which may be a failure.
   * @return the value of the property
   */
  public Result<Object> getResult() {
    return _result;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the callGraph.
   * @return the value of the property
   */
  public CallGraph getCallGraph() {
    return _callGraph;
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
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ResultItem other = (ResultItem) obj;
      return JodaBeanUtils.equal(getResult(), other.getResult()) &&
          JodaBeanUtils.equal(getCallGraph(), other.getCallGraph());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getResult());
    hash = hash * 31 + JodaBeanUtils.hashCode(getCallGraph());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("ResultItem{");
    buf.append("result").append('=').append(getResult()).append(',').append(' ');
    buf.append("callGraph").append('=').append(JodaBeanUtils.toString(getCallGraph()));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ResultItem}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code result} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Result<Object>> _result = DirectMetaProperty.ofImmutable(
        this, "result", ResultItem.class, (Class) Result.class);
    /**
     * The meta-property for the {@code callGraph} property.
     */
    private final MetaProperty<CallGraph> _callGraph = DirectMetaProperty.ofImmutable(
        this, "callGraph", ResultItem.class, CallGraph.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "result",
        "callGraph");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -934426595:  // result
          return _result;
        case -1068293744:  // callGraph
          return _callGraph;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public ResultItem.Builder builder() {
      return new ResultItem.Builder();
    }

    @Override
    public Class<? extends ResultItem> beanType() {
      return ResultItem.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code result} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Result<Object>> result() {
      return _result;
    }

    /**
     * The meta-property for the {@code callGraph} property.
     * @return the meta-property, not null
     */
    public MetaProperty<CallGraph> callGraph() {
      return _callGraph;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -934426595:  // result
          return ((ResultItem) bean).getResult();
        case -1068293744:  // callGraph
          return ((ResultItem) bean).getCallGraph();
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
   * The bean-builder for {@code ResultItem}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<ResultItem> {

    private Result<Object> _result;
    private CallGraph _callGraph;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(ResultItem beanToCopy) {
      this._result = beanToCopy.getResult();
      this._callGraph = beanToCopy.getCallGraph();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case -934426595:  // result
          return _result;
        case -1068293744:  // callGraph
          return _callGraph;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -934426595:  // result
          this._result = (Result<Object>) newValue;
          break;
        case -1068293744:  // callGraph
          this._callGraph = (CallGraph) newValue;
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
      super.setString(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public ResultItem build() {
      return new ResultItem(
          _result,
          _callGraph);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code result} property in the builder.
     * @param result  the new value
     * @return this, for chaining, not null
     */
    public Builder result(Result<Object> result) {
      this._result = result;
      return this;
    }

    /**
     * Sets the {@code callGraph} property in the builder.
     * @param callGraph  the new value
     * @return this, for chaining, not null
     */
    public Builder callGraph(CallGraph callGraph) {
      this._callGraph = callGraph;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("ResultItem.Builder{");
      buf.append("result").append('=').append(JodaBeanUtils.toString(_result)).append(',').append(' ');
      buf.append("callGraph").append('=').append(JodaBeanUtils.toString(_callGraph));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
