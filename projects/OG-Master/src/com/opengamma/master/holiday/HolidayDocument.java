/**
 * Copyright (C) 2009 - 2010 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.master.holiday;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.time.Instant;

import org.joda.beans.BeanDefinition;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.BasicMetaBean;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectMetaProperty;

import com.opengamma.core.holiday.Holiday;
import com.opengamma.id.Identifier;
import com.opengamma.id.UniqueIdentifier;
import com.opengamma.util.ArgumentChecker;

/**
 * A document used to pass into and out of the holiday master.
 * <p>
 * The holiday master provides full management of the holiday database.
 * Each element is stored in a document.
 */
@BeanDefinition
public class HolidayDocument extends DirectBean implements Serializable {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The holiday unique identifier.
   */
  @PropertyDefinition
  private UniqueIdentifier _holidayId;
  /**
   * The start of an interval that the version of the holiday is accurate for.
   * This field is populated and managed by the {@code HolidayMaster}.
   */
  @PropertyDefinition
  private Instant _versionFromInstant;
  /**
   * The end of an interval that the version of the holiday is accurate for.
   * Null indicates this is the latest version.
   * This field is populated and managed by the {@code HolidayMaster}.
   */
  @PropertyDefinition
  private Instant _versionToInstant;
  /**
   * The start of an interval that the correction of the version of the holiday is accurate for.
   * This field is populated and managed by the {@code HolidayMaster}.
   */
  @PropertyDefinition
  private Instant _correctionFromInstant;
  /**
   * The end of an interval that the correction of the version of the holiday is accurate for.
   * Null indicates this is the latest correction.
   * This field is populated and managed by the {@code HolidayMaster}.
   */
  @PropertyDefinition
  private Instant _correctionToInstant;
  /**
   * The name of the holiday.
   */
  @PropertyDefinition
  private String _name;
  /**
   * The identifier of the provider of the data.
   * This optional field can be used to capture the identifier used by the data provider.
   * This can be useful when receiving updates from the same provider.
   */
  @PropertyDefinition
  private Identifier _providerId;
  /**
   * The holiday.
   */
  @PropertyDefinition
  private ManageableHoliday _holiday;

  /**
   * Creates an instance.
   */
  public HolidayDocument() {
  }

  /**
   * Creates an instance from a holiday.
   * <p>
   * This will call {@link #createName()} to build a suitable name.
   * 
   * @param holiday  the holiday, not null
   */
  public HolidayDocument(final Holiday holiday) {
    ArgumentChecker.notNull(holiday, "holiday");
    setHolidayId(holiday.getUniqueIdentifier());
    setHoliday(new ManageableHoliday(holiday));
    createName();
  }

  //-------------------------------------------------------------------------
  /**
   * Creates a name based on the holiday.
   */
  public void createName() {
    ManageableHoliday holiday = getHoliday();
    switch (holiday.getType()) {
      case BANK:
        setName(holiday.getRegionId().getValue());
        break;
      case CURRENCY:
        setName(holiday.getCurrency().getISOCode());
        break;
      case SETTLEMENT:
      case TRADING:
        setName(holiday.getExchangeId().getValue());
        break;
      default:
        throw new IllegalArgumentException("Unsupported holiday type");
    }
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code HolidayDocument}.
   * @return the meta-bean, not null
   */
  public static HolidayDocument.Meta meta() {
    return HolidayDocument.Meta.INSTANCE;
  }

  @Override
  public HolidayDocument.Meta metaBean() {
    return HolidayDocument.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName) {
    switch (propertyName.hashCode()) {
      case 1349286803:  // holidayId
        return getHolidayId();
      case 2006263519:  // versionFromInstant
        return getVersionFromInstant();
      case 1577022702:  // versionToInstant
        return getVersionToInstant();
      case 1808757913:  // correctionFromInstant
        return getCorrectionFromInstant();
      case 973465896:  // correctionToInstant
        return getCorrectionToInstant();
      case 3373707:  // name
        return getName();
      case 205149932:  // providerId
        return getProviderId();
      case 1091905624:  // holiday
        return getHoliday();
    }
    return super.propertyGet(propertyName);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue) {
    switch (propertyName.hashCode()) {
      case 1349286803:  // holidayId
        setHolidayId((UniqueIdentifier) newValue);
        return;
      case 2006263519:  // versionFromInstant
        setVersionFromInstant((Instant) newValue);
        return;
      case 1577022702:  // versionToInstant
        setVersionToInstant((Instant) newValue);
        return;
      case 1808757913:  // correctionFromInstant
        setCorrectionFromInstant((Instant) newValue);
        return;
      case 973465896:  // correctionToInstant
        setCorrectionToInstant((Instant) newValue);
        return;
      case 3373707:  // name
        setName((String) newValue);
        return;
      case 205149932:  // providerId
        setProviderId((Identifier) newValue);
        return;
      case 1091905624:  // holiday
        setHoliday((ManageableHoliday) newValue);
        return;
    }
    super.propertySet(propertyName, newValue);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the holiday unique identifier.
   * @return the value of the property
   */
  public UniqueIdentifier getHolidayId() {
    return _holidayId;
  }

  /**
   * Sets the holiday unique identifier.
   * @param holidayId  the new value of the property
   */
  public void setHolidayId(UniqueIdentifier holidayId) {
    this._holidayId = holidayId;
  }

  /**
   * Gets the the {@code holidayId} property.
   * @return the property, not null
   */
  public final Property<UniqueIdentifier> holidayId() {
    return metaBean().holidayId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the start of an interval that the version of the holiday is accurate for.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @return the value of the property
   */
  public Instant getVersionFromInstant() {
    return _versionFromInstant;
  }

  /**
   * Sets the start of an interval that the version of the holiday is accurate for.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @param versionFromInstant  the new value of the property
   */
  public void setVersionFromInstant(Instant versionFromInstant) {
    this._versionFromInstant = versionFromInstant;
  }

  /**
   * Gets the the {@code versionFromInstant} property.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @return the property, not null
   */
  public final Property<Instant> versionFromInstant() {
    return metaBean().versionFromInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the end of an interval that the version of the holiday is accurate for.
   * Null indicates this is the latest version.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @return the value of the property
   */
  public Instant getVersionToInstant() {
    return _versionToInstant;
  }

  /**
   * Sets the end of an interval that the version of the holiday is accurate for.
   * Null indicates this is the latest version.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @param versionToInstant  the new value of the property
   */
  public void setVersionToInstant(Instant versionToInstant) {
    this._versionToInstant = versionToInstant;
  }

  /**
   * Gets the the {@code versionToInstant} property.
   * Null indicates this is the latest version.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @return the property, not null
   */
  public final Property<Instant> versionToInstant() {
    return metaBean().versionToInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the start of an interval that the correction of the version of the holiday is accurate for.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @return the value of the property
   */
  public Instant getCorrectionFromInstant() {
    return _correctionFromInstant;
  }

  /**
   * Sets the start of an interval that the correction of the version of the holiday is accurate for.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @param correctionFromInstant  the new value of the property
   */
  public void setCorrectionFromInstant(Instant correctionFromInstant) {
    this._correctionFromInstant = correctionFromInstant;
  }

  /**
   * Gets the the {@code correctionFromInstant} property.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @return the property, not null
   */
  public final Property<Instant> correctionFromInstant() {
    return metaBean().correctionFromInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the end of an interval that the correction of the version of the holiday is accurate for.
   * Null indicates this is the latest correction.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @return the value of the property
   */
  public Instant getCorrectionToInstant() {
    return _correctionToInstant;
  }

  /**
   * Sets the end of an interval that the correction of the version of the holiday is accurate for.
   * Null indicates this is the latest correction.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @param correctionToInstant  the new value of the property
   */
  public void setCorrectionToInstant(Instant correctionToInstant) {
    this._correctionToInstant = correctionToInstant;
  }

  /**
   * Gets the the {@code correctionToInstant} property.
   * Null indicates this is the latest correction.
   * This field is populated and managed by the {@code HolidayMaster}.
   * @return the property, not null
   */
  public final Property<Instant> correctionToInstant() {
    return metaBean().correctionToInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the name of the holiday.
   * @return the value of the property
   */
  public String getName() {
    return _name;
  }

  /**
   * Sets the name of the holiday.
   * @param name  the new value of the property
   */
  public void setName(String name) {
    this._name = name;
  }

  /**
   * Gets the the {@code name} property.
   * @return the property, not null
   */
  public final Property<String> name() {
    return metaBean().name().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the identifier of the provider of the data.
   * This optional field can be used to capture the identifier used by the data provider.
   * This can be useful when receiving updates from the same provider.
   * @return the value of the property
   */
  public Identifier getProviderId() {
    return _providerId;
  }

  /**
   * Sets the identifier of the provider of the data.
   * This optional field can be used to capture the identifier used by the data provider.
   * This can be useful when receiving updates from the same provider.
   * @param providerId  the new value of the property
   */
  public void setProviderId(Identifier providerId) {
    this._providerId = providerId;
  }

  /**
   * Gets the the {@code providerId} property.
   * This optional field can be used to capture the identifier used by the data provider.
   * This can be useful when receiving updates from the same provider.
   * @return the property, not null
   */
  public final Property<Identifier> providerId() {
    return metaBean().providerId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the holiday.
   * @return the value of the property
   */
  public ManageableHoliday getHoliday() {
    return _holiday;
  }

  /**
   * Sets the holiday.
   * @param holiday  the new value of the property
   */
  public void setHoliday(ManageableHoliday holiday) {
    this._holiday = holiday;
  }

  /**
   * Gets the the {@code holiday} property.
   * @return the property, not null
   */
  public final Property<ManageableHoliday> holiday() {
    return metaBean().holiday().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code HolidayDocument}.
   */
  public static class Meta extends BasicMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code holidayId} property.
     */
    private final MetaProperty<UniqueIdentifier> _holidayId = DirectMetaProperty.ofReadWrite(this, "holidayId", UniqueIdentifier.class);
    /**
     * The meta-property for the {@code versionFromInstant} property.
     */
    private final MetaProperty<Instant> _versionFromInstant = DirectMetaProperty.ofReadWrite(this, "versionFromInstant", Instant.class);
    /**
     * The meta-property for the {@code versionToInstant} property.
     */
    private final MetaProperty<Instant> _versionToInstant = DirectMetaProperty.ofReadWrite(this, "versionToInstant", Instant.class);
    /**
     * The meta-property for the {@code correctionFromInstant} property.
     */
    private final MetaProperty<Instant> _correctionFromInstant = DirectMetaProperty.ofReadWrite(this, "correctionFromInstant", Instant.class);
    /**
     * The meta-property for the {@code correctionToInstant} property.
     */
    private final MetaProperty<Instant> _correctionToInstant = DirectMetaProperty.ofReadWrite(this, "correctionToInstant", Instant.class);
    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> _name = DirectMetaProperty.ofReadWrite(this, "name", String.class);
    /**
     * The meta-property for the {@code providerId} property.
     */
    private final MetaProperty<Identifier> _providerId = DirectMetaProperty.ofReadWrite(this, "providerId", Identifier.class);
    /**
     * The meta-property for the {@code holiday} property.
     */
    private final MetaProperty<ManageableHoliday> _holiday = DirectMetaProperty.ofReadWrite(this, "holiday", ManageableHoliday.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<Object>> _map;

    @SuppressWarnings({"unchecked", "rawtypes" })
    protected Meta() {
      LinkedHashMap temp = new LinkedHashMap();
      temp.put("holidayId", _holidayId);
      temp.put("versionFromInstant", _versionFromInstant);
      temp.put("versionToInstant", _versionToInstant);
      temp.put("correctionFromInstant", _correctionFromInstant);
      temp.put("correctionToInstant", _correctionToInstant);
      temp.put("name", _name);
      temp.put("providerId", _providerId);
      temp.put("holiday", _holiday);
      _map = Collections.unmodifiableMap(temp);
    }

    @Override
    public HolidayDocument createBean() {
      return new HolidayDocument();
    }

    @Override
    public Class<? extends HolidayDocument> beanType() {
      return HolidayDocument.class;
    }

    @Override
    public Map<String, MetaProperty<Object>> metaPropertyMap() {
      return _map;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code holidayId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UniqueIdentifier> holidayId() {
      return _holidayId;
    }

    /**
     * The meta-property for the {@code versionFromInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> versionFromInstant() {
      return _versionFromInstant;
    }

    /**
     * The meta-property for the {@code versionToInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> versionToInstant() {
      return _versionToInstant;
    }

    /**
     * The meta-property for the {@code correctionFromInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> correctionFromInstant() {
      return _correctionFromInstant;
    }

    /**
     * The meta-property for the {@code correctionToInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> correctionToInstant() {
      return _correctionToInstant;
    }

    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> name() {
      return _name;
    }

    /**
     * The meta-property for the {@code providerId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Identifier> providerId() {
      return _providerId;
    }

    /**
     * The meta-property for the {@code holiday} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ManageableHoliday> holiday() {
      return _holiday;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
