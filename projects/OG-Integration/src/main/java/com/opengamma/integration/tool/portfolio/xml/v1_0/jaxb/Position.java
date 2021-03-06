/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.integration.tool.portfolio.xml.v1_0.jaxb;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableMap;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { }) // Indicate we don't care about element ordering
@BeanDefinition
public class Position extends DirectBean {

  @XmlAttribute
  @XmlID
  @PropertyDefinition
  private String _id;

  @XmlElement(name = "externalSystemId")
  @PropertyDefinition
  private IdWrapper _externalSystemId;

  @XmlElement(name = "quantity")
  @PropertyDefinition
  private BigDecimal _quantity;

  @XmlElementWrapper(name = "trades")
  @XmlElement(name = "trade")
  @XmlJavaTypeAdapter(TradeRefAdapter.class)
  @PropertyDefinition
  private List<Trade> _trades;

  @XmlElement(name = "security")
  @PropertyDefinition
  private IdWrapper _security;

  @XmlElementRef
  @PropertyDefinition
  private ListedSecurityDefinition _listedSecurityDefinition;

  @XmlElement(name = "additionalAttributes")
  @XmlJavaTypeAdapter(AttributeMapAdapter.class)
  @PropertyDefinition(get = "manual")
  private Map<String, String> _additionalAttributes;

  /**
   * Gets the additionalAttributes.
   * @return the value of the property
   */
  public Map<String, String> getAdditionalAttributes() {
    return _additionalAttributes == null ? ImmutableMap.<String, String>of() : _additionalAttributes;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code Position}.
   * @return the meta-bean, not null
   */
  public static Position.Meta meta() {
    return Position.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(Position.Meta.INSTANCE);
  }

  @Override
  public Position.Meta metaBean() {
    return Position.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the id.
   * @return the value of the property
   */
  public String getId() {
    return _id;
  }

  /**
   * Sets the id.
   * @param id  the new value of the property
   */
  public void setId(String id) {
    this._id = id;
  }

  /**
   * Gets the the {@code id} property.
   * @return the property, not null
   */
  public final Property<String> id() {
    return metaBean().id().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the externalSystemId.
   * @return the value of the property
   */
  public IdWrapper getExternalSystemId() {
    return _externalSystemId;
  }

  /**
   * Sets the externalSystemId.
   * @param externalSystemId  the new value of the property
   */
  public void setExternalSystemId(IdWrapper externalSystemId) {
    this._externalSystemId = externalSystemId;
  }

  /**
   * Gets the the {@code externalSystemId} property.
   * @return the property, not null
   */
  public final Property<IdWrapper> externalSystemId() {
    return metaBean().externalSystemId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the quantity.
   * @return the value of the property
   */
  public BigDecimal getQuantity() {
    return _quantity;
  }

  /**
   * Sets the quantity.
   * @param quantity  the new value of the property
   */
  public void setQuantity(BigDecimal quantity) {
    this._quantity = quantity;
  }

  /**
   * Gets the the {@code quantity} property.
   * @return the property, not null
   */
  public final Property<BigDecimal> quantity() {
    return metaBean().quantity().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the trades.
   * @return the value of the property
   */
  public List<Trade> getTrades() {
    return _trades;
  }

  /**
   * Sets the trades.
   * @param trades  the new value of the property
   */
  public void setTrades(List<Trade> trades) {
    this._trades = trades;
  }

  /**
   * Gets the the {@code trades} property.
   * @return the property, not null
   */
  public final Property<List<Trade>> trades() {
    return metaBean().trades().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the security.
   * @return the value of the property
   */
  public IdWrapper getSecurity() {
    return _security;
  }

  /**
   * Sets the security.
   * @param security  the new value of the property
   */
  public void setSecurity(IdWrapper security) {
    this._security = security;
  }

  /**
   * Gets the the {@code security} property.
   * @return the property, not null
   */
  public final Property<IdWrapper> security() {
    return metaBean().security().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the listedSecurityDefinition.
   * @return the value of the property
   */
  public ListedSecurityDefinition getListedSecurityDefinition() {
    return _listedSecurityDefinition;
  }

  /**
   * Sets the listedSecurityDefinition.
   * @param listedSecurityDefinition  the new value of the property
   */
  public void setListedSecurityDefinition(ListedSecurityDefinition listedSecurityDefinition) {
    this._listedSecurityDefinition = listedSecurityDefinition;
  }

  /**
   * Gets the the {@code listedSecurityDefinition} property.
   * @return the property, not null
   */
  public final Property<ListedSecurityDefinition> listedSecurityDefinition() {
    return metaBean().listedSecurityDefinition().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Sets the additionalAttributes.
   * @param additionalAttributes  the new value of the property
   */
  public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
    this._additionalAttributes = additionalAttributes;
  }

  /**
   * Gets the the {@code additionalAttributes} property.
   * @return the property, not null
   */
  public final Property<Map<String, String>> additionalAttributes() {
    return metaBean().additionalAttributes().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public Position clone() {
    return JodaBeanUtils.cloneAlways(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      Position other = (Position) obj;
      return JodaBeanUtils.equal(getId(), other.getId()) &&
          JodaBeanUtils.equal(getExternalSystemId(), other.getExternalSystemId()) &&
          JodaBeanUtils.equal(getQuantity(), other.getQuantity()) &&
          JodaBeanUtils.equal(getTrades(), other.getTrades()) &&
          JodaBeanUtils.equal(getSecurity(), other.getSecurity()) &&
          JodaBeanUtils.equal(getListedSecurityDefinition(), other.getListedSecurityDefinition()) &&
          JodaBeanUtils.equal(getAdditionalAttributes(), other.getAdditionalAttributes());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(getId());
    hash = hash * 31 + JodaBeanUtils.hashCode(getExternalSystemId());
    hash = hash * 31 + JodaBeanUtils.hashCode(getQuantity());
    hash = hash * 31 + JodaBeanUtils.hashCode(getTrades());
    hash = hash * 31 + JodaBeanUtils.hashCode(getSecurity());
    hash = hash * 31 + JodaBeanUtils.hashCode(getListedSecurityDefinition());
    hash = hash * 31 + JodaBeanUtils.hashCode(getAdditionalAttributes());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(256);
    buf.append("Position{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("id").append('=').append(JodaBeanUtils.toString(getId())).append(',').append(' ');
    buf.append("externalSystemId").append('=').append(JodaBeanUtils.toString(getExternalSystemId())).append(',').append(' ');
    buf.append("quantity").append('=').append(JodaBeanUtils.toString(getQuantity())).append(',').append(' ');
    buf.append("trades").append('=').append(JodaBeanUtils.toString(getTrades())).append(',').append(' ');
    buf.append("security").append('=').append(JodaBeanUtils.toString(getSecurity())).append(',').append(' ');
    buf.append("listedSecurityDefinition").append('=').append(JodaBeanUtils.toString(getListedSecurityDefinition())).append(',').append(' ');
    buf.append("additionalAttributes").append('=').append(JodaBeanUtils.toString(getAdditionalAttributes())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code Position}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code id} property.
     */
    private final MetaProperty<String> _id = DirectMetaProperty.ofReadWrite(
        this, "id", Position.class, String.class);
    /**
     * The meta-property for the {@code externalSystemId} property.
     */
    private final MetaProperty<IdWrapper> _externalSystemId = DirectMetaProperty.ofReadWrite(
        this, "externalSystemId", Position.class, IdWrapper.class);
    /**
     * The meta-property for the {@code quantity} property.
     */
    private final MetaProperty<BigDecimal> _quantity = DirectMetaProperty.ofReadWrite(
        this, "quantity", Position.class, BigDecimal.class);
    /**
     * The meta-property for the {@code trades} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<List<Trade>> _trades = DirectMetaProperty.ofReadWrite(
        this, "trades", Position.class, (Class) List.class);
    /**
     * The meta-property for the {@code security} property.
     */
    private final MetaProperty<IdWrapper> _security = DirectMetaProperty.ofReadWrite(
        this, "security", Position.class, IdWrapper.class);
    /**
     * The meta-property for the {@code listedSecurityDefinition} property.
     */
    private final MetaProperty<ListedSecurityDefinition> _listedSecurityDefinition = DirectMetaProperty.ofReadWrite(
        this, "listedSecurityDefinition", Position.class, ListedSecurityDefinition.class);
    /**
     * The meta-property for the {@code additionalAttributes} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Map<String, String>> _additionalAttributes = DirectMetaProperty.ofReadWrite(
        this, "additionalAttributes", Position.class, (Class) Map.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "id",
        "externalSystemId",
        "quantity",
        "trades",
        "security",
        "listedSecurityDefinition",
        "additionalAttributes");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3355:  // id
          return _id;
        case -1924302699:  // externalSystemId
          return _externalSystemId;
        case -1285004149:  // quantity
          return _quantity;
        case -865715313:  // trades
          return _trades;
        case 949122880:  // security
          return _security;
        case -163631792:  // listedSecurityDefinition
          return _listedSecurityDefinition;
        case -1075726114:  // additionalAttributes
          return _additionalAttributes;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends Position> builder() {
      return new DirectBeanBuilder<Position>(new Position());
    }

    @Override
    public Class<? extends Position> beanType() {
      return Position.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code id} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> id() {
      return _id;
    }

    /**
     * The meta-property for the {@code externalSystemId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<IdWrapper> externalSystemId() {
      return _externalSystemId;
    }

    /**
     * The meta-property for the {@code quantity} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<BigDecimal> quantity() {
      return _quantity;
    }

    /**
     * The meta-property for the {@code trades} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<List<Trade>> trades() {
      return _trades;
    }

    /**
     * The meta-property for the {@code security} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<IdWrapper> security() {
      return _security;
    }

    /**
     * The meta-property for the {@code listedSecurityDefinition} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ListedSecurityDefinition> listedSecurityDefinition() {
      return _listedSecurityDefinition;
    }

    /**
     * The meta-property for the {@code additionalAttributes} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Map<String, String>> additionalAttributes() {
      return _additionalAttributes;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3355:  // id
          return ((Position) bean).getId();
        case -1924302699:  // externalSystemId
          return ((Position) bean).getExternalSystemId();
        case -1285004149:  // quantity
          return ((Position) bean).getQuantity();
        case -865715313:  // trades
          return ((Position) bean).getTrades();
        case 949122880:  // security
          return ((Position) bean).getSecurity();
        case -163631792:  // listedSecurityDefinition
          return ((Position) bean).getListedSecurityDefinition();
        case -1075726114:  // additionalAttributes
          return ((Position) bean).getAdditionalAttributes();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3355:  // id
          ((Position) bean).setId((String) newValue);
          return;
        case -1924302699:  // externalSystemId
          ((Position) bean).setExternalSystemId((IdWrapper) newValue);
          return;
        case -1285004149:  // quantity
          ((Position) bean).setQuantity((BigDecimal) newValue);
          return;
        case -865715313:  // trades
          ((Position) bean).setTrades((List<Trade>) newValue);
          return;
        case 949122880:  // security
          ((Position) bean).setSecurity((IdWrapper) newValue);
          return;
        case -163631792:  // listedSecurityDefinition
          ((Position) bean).setListedSecurityDefinition((ListedSecurityDefinition) newValue);
          return;
        case -1075726114:  // additionalAttributes
          ((Position) bean).setAdditionalAttributes((Map<String, String>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
