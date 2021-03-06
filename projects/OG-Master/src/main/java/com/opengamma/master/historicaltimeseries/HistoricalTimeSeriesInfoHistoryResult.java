/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.historicaltimeseries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.OpenGammaRuntimeException;
import com.opengamma.master.AbstractHistoryResult;
import com.opengamma.util.PublicSPI;
import org.joda.beans.Bean;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectMetaProperty;

/**
 * Result from searching historical time-series information.
 * <p>
 * This class is mutable and not thread-safe.
 */
@PublicSPI
@BeanDefinition
public class HistoricalTimeSeriesInfoHistoryResult extends AbstractHistoryResult<HistoricalTimeSeriesInfoDocument> {

  /**
   * The number of items that were removed because the user is not authorized to see them.
   */
  @PropertyDefinition
  private int _unauthorizedCount;

  /**
   * Creates an instance.
   */
  public HistoricalTimeSeriesInfoHistoryResult() {
  }

  /**
   * Creates an instance from a collection of documents.
   * 
   * @param coll  the collection of documents to add, not null
   */
  public HistoricalTimeSeriesInfoHistoryResult(Collection<HistoricalTimeSeriesInfoDocument> coll) {
    super(coll);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the returned series information from within the documents.
   * 
   * @return the series, not null
   */
  public List<ManageableHistoricalTimeSeriesInfo> getInfoList() {
    List<ManageableHistoricalTimeSeriesInfo> result = new ArrayList<ManageableHistoricalTimeSeriesInfo>();
    if (getDocuments() != null) {
      for (HistoricalTimeSeriesInfoDocument doc : getDocuments()) {
        result.add(doc.getInfo());
      }
    }
    return result;
  }

  /**
   * Gets the first series information, or null if no documents.
   * 
   * @return the first series information, null if none
   */
  public ManageableHistoricalTimeSeriesInfo getFirstInfo() {
    return getDocuments().size() > 0 ? getDocuments().get(0).getInfo() : null;
  }

  /**
   * Gets the single result expected from a query.
   * <p>
   * This throws an exception if more than 1 result is actually available.
   * Thus, this method implies an assumption about uniqueness of the queried series.
   * 
   * @return the matching exchange, not null
   * @throws IllegalStateException if no series was found
   */
  public ManageableHistoricalTimeSeriesInfo getSingleInfo() {
    if (getDocuments().size() != 1) {
      throw new OpenGammaRuntimeException("Expecting zero or single resulting match, and was " + getDocuments().size());
    } else {
      return getDocuments().get(0).getInfo();
    }
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code HistoricalTimeSeriesInfoHistoryResult}.
   * @return the meta-bean, not null
   */
  public static HistoricalTimeSeriesInfoHistoryResult.Meta meta() {
    return HistoricalTimeSeriesInfoHistoryResult.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(HistoricalTimeSeriesInfoHistoryResult.Meta.INSTANCE);
  }

  @Override
  public HistoricalTimeSeriesInfoHistoryResult.Meta metaBean() {
    return HistoricalTimeSeriesInfoHistoryResult.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the number of items that were removed because the user is not authorized to see them.
   * @return the value of the property
   */
  public int getUnauthorizedCount() {
    return _unauthorizedCount;
  }

  /**
   * Sets the number of items that were removed because the user is not authorized to see them.
   * @param unauthorizedCount  the new value of the property
   */
  public void setUnauthorizedCount(int unauthorizedCount) {
    this._unauthorizedCount = unauthorizedCount;
  }

  /**
   * Gets the the {@code unauthorizedCount} property.
   * @return the property, not null
   */
  public final Property<Integer> unauthorizedCount() {
    return metaBean().unauthorizedCount().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public HistoricalTimeSeriesInfoHistoryResult clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      HistoricalTimeSeriesInfoHistoryResult other = (HistoricalTimeSeriesInfoHistoryResult) obj;
      return (getUnauthorizedCount() == other.getUnauthorizedCount()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = hash * 31 + JodaBeanUtils.hashCode(getUnauthorizedCount());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("HistoricalTimeSeriesInfoHistoryResult{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  @Override
  protected void toString(StringBuilder buf) {
    super.toString(buf);
    buf.append("unauthorizedCount").append('=').append(JodaBeanUtils.toString(getUnauthorizedCount())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code HistoricalTimeSeriesInfoHistoryResult}.
   */
  public static class Meta extends AbstractHistoryResult.Meta<HistoricalTimeSeriesInfoDocument> {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code unauthorizedCount} property.
     */
    private final MetaProperty<Integer> _unauthorizedCount = DirectMetaProperty.ofReadWrite(
        this, "unauthorizedCount", HistoricalTimeSeriesInfoHistoryResult.class, Integer.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "unauthorizedCount");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 2063040635:  // unauthorizedCount
          return _unauthorizedCount;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends HistoricalTimeSeriesInfoHistoryResult> builder() {
      return new DirectBeanBuilder<HistoricalTimeSeriesInfoHistoryResult>(new HistoricalTimeSeriesInfoHistoryResult());
    }

    @Override
    public Class<? extends HistoricalTimeSeriesInfoHistoryResult> beanType() {
      return HistoricalTimeSeriesInfoHistoryResult.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code unauthorizedCount} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Integer> unauthorizedCount() {
      return _unauthorizedCount;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 2063040635:  // unauthorizedCount
          return ((HistoricalTimeSeriesInfoHistoryResult) bean).getUnauthorizedCount();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 2063040635:  // unauthorizedCount
          ((HistoricalTimeSeriesInfoHistoryResult) bean).setUnauthorizedCount((Integer) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
