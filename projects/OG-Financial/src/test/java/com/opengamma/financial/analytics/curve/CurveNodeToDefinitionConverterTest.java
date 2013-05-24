/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.curve;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.Period;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.analytics.financial.instrument.InstrumentDefinition;
import com.opengamma.analytics.financial.instrument.cash.CashDefinition;
import com.opengamma.analytics.financial.instrument.cash.DepositIborDefinition;
import com.opengamma.analytics.financial.instrument.fra.ForwardRateAgreementDefinition;
import com.opengamma.analytics.financial.instrument.future.InterestRateFutureSecurityDefinition;
import com.opengamma.analytics.financial.instrument.future.InterestRateFutureTransactionDefinition;
import com.opengamma.analytics.financial.instrument.index.IborIndex;
import com.opengamma.analytics.financial.instrument.swap.SwapDefinition;
import com.opengamma.core.change.ChangeManager;
import com.opengamma.core.change.DummyChangeManager;
import com.opengamma.core.holiday.Holiday;
import com.opengamma.core.holiday.HolidaySource;
import com.opengamma.core.holiday.HolidayType;
import com.opengamma.core.holiday.impl.SimpleHoliday;
import com.opengamma.core.id.ExternalSchemes;
import com.opengamma.core.marketdatasnapshot.SnapshotDataBundle;
import com.opengamma.core.region.Region;
import com.opengamma.core.region.RegionSource;
import com.opengamma.core.region.impl.SimpleRegion;
import com.opengamma.financial.analytics.ircurve.strips.CashNode;
import com.opengamma.financial.analytics.ircurve.strips.ContinuouslyCompoundedRateNode;
import com.opengamma.financial.analytics.ircurve.strips.CreditSpreadNode;
import com.opengamma.financial.analytics.ircurve.strips.CurveNode;
import com.opengamma.financial.analytics.ircurve.strips.DiscountFactorNode;
import com.opengamma.financial.analytics.ircurve.strips.FRANode;
import com.opengamma.financial.analytics.ircurve.strips.RateFutureNode;
import com.opengamma.financial.analytics.ircurve.strips.SwapNode;
import com.opengamma.financial.convention.CMSLegConvention;
import com.opengamma.financial.convention.CompoundingIborLegConvention;
import com.opengamma.financial.convention.Convention;
import com.opengamma.financial.convention.ConventionSource;
import com.opengamma.financial.convention.DepositConvention;
import com.opengamma.financial.convention.EquityConvention;
import com.opengamma.financial.convention.FXForwardAndSwapConvention;
import com.opengamma.financial.convention.FXSpotConvention;
import com.opengamma.financial.convention.IMMFutureAndFutureOptionMonthlyExpiryCalculator;
import com.opengamma.financial.convention.IMMFutureAndFutureOptionQuarterlyExpiryCalculator;
import com.opengamma.financial.convention.IborIndexConvention;
import com.opengamma.financial.convention.InterestRateFutureConvention;
import com.opengamma.financial.convention.OISLegConvention;
import com.opengamma.financial.convention.OvernightIndexConvention;
import com.opengamma.financial.convention.StubType;
import com.opengamma.financial.convention.SwapFixedLegConvention;
import com.opengamma.financial.convention.VanillaIborLegConvention;
import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.businessday.BusinessDayConventionFactory;
import com.opengamma.financial.convention.calendar.Calendar;
import com.opengamma.financial.convention.calendar.MondayToFridayCalendar;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.daycount.DayCountFactory;
import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.id.ObjectId;
import com.opengamma.id.UniqueId;
import com.opengamma.id.VersionCorrection;
import com.opengamma.util.money.Currency;
import com.opengamma.util.test.TestGroup;
import com.opengamma.util.time.DateUtils;
import com.opengamma.util.time.Tenor;

/**
 * 
 */
@Test(groups = TestGroup.UNIT)
public class CurveNodeToDefinitionConverterTest {
  private static final MondayToFridayCalendar CALENDAR = new MondayToFridayCalendar("Weekend");
  private static final String SCHEME = "Test";
  private static final BusinessDayConvention MODIFIED_FOLLOWING = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Modified Following");
  private static final DayCount ACT_360 = DayCountFactory.INSTANCE.getDayCount("Actual/360");
  private static final DayCount THIRTY_360 = DayCountFactory.INSTANCE.getDayCount("30/360");
  private static final ExternalId US = ExternalSchemes.financialRegionId("US");
  private static final ExternalId NYLON = ExternalSchemes.financialRegionId("US+GB");
  private static final Currency USD = Currency.of("USD");
  private static final ExternalId FIXED_LEG_ID = ExternalId.of(SCHEME, "USD Swap Fixed Leg");
  private static final ExternalId DEPOSIT_1D_ID = ExternalId.of(SCHEME, "USD 1d Deposit");
  private static final ExternalId DEPOSIT_1M_ID = ExternalId.of(SCHEME, "USD 1m Deposit");
  private static final ExternalId LIBOR_1M_ID = ExternalId.of(SCHEME, "USD 1m Libor");
  private static final ExternalId LIBOR_3M_ID = ExternalId.of(SCHEME, "USD 3m Libor");
  private static final ExternalId LIBOR_6M_ID = ExternalId.of(SCHEME, "USD 6m Libor");
  private static final ExternalId RATE_FUTURE_3M_ID = ExternalId.of(SCHEME, "USD 3m Rate Future");
  private static final ExternalId RATE_FUTURE_1M_ID = ExternalId.of(SCHEME, "USD 1m Rate Future");
  private static final ExternalId SWAP_3M_IBOR_ID = ExternalId.of(SCHEME, "USD 3m Floating Leg");
  private static final ExternalId IMM_3M_EXPIRY_CONVENTION = ExternalId.of(SCHEME, IMMFutureAndFutureOptionQuarterlyExpiryCalculator.NAME);
  private static final ExternalId IMM_1M_EXPIRY_CONVENTION = ExternalId.of(SCHEME, IMMFutureAndFutureOptionMonthlyExpiryCalculator.NAME);
  private static final SwapFixedLegConvention FIXED_LEG = new SwapFixedLegConvention("USD Swap Fixed Leg", ExternalIdBundle.of(ExternalId.of(SCHEME, "USD Swap Fixed Leg")),
      Tenor.THREE_MONTHS, THIRTY_360, MODIFIED_FOLLOWING, 2, false, USD, NYLON, StubType.NONE);
  private static final VanillaIborLegConvention SWAP_3M_LIBOR = new VanillaIborLegConvention("USD 3m Floating Leg", ExternalIdBundle.of(ExternalId.of(SCHEME, "USD 3m Floating Leg")),
      DEPOSIT_1D_ID, false, null, SCHEME);
  private static final DepositConvention DEPOSIT_1D = new DepositConvention("USD 1d Deposit", ExternalIdBundle.of(DEPOSIT_1D_ID),
      ACT_360, MODIFIED_FOLLOWING, 0, false, USD, US, Tenor.ONE_DAY);
  private static final DepositConvention DEPOSIT_1M = new DepositConvention("USD 1m Deposit", ExternalIdBundle.of(DEPOSIT_1M_ID),
      ACT_360, MODIFIED_FOLLOWING, 2, false, USD, US, Tenor.ONE_MONTH);
  private static final IborIndexConvention LIBOR_1M = new IborIndexConvention("USD 1m Libor", ExternalIdBundle.of(LIBOR_1M_ID),
      THIRTY_360, MODIFIED_FOLLOWING, 2, false, USD, LocalTime.of(11, 0), US, US, "Page", Tenor.ONE_MONTH);
  private static final IborIndexConvention LIBOR_3M = new IborIndexConvention("USD 3m Libor", ExternalIdBundle.of(LIBOR_3M_ID),
      THIRTY_360, MODIFIED_FOLLOWING, 2, false, USD, LocalTime.of(11, 0), US, US, "Page", Tenor.THREE_MONTHS);
  private static final IborIndexConvention LIBOR_6M = new IborIndexConvention("USD 6m Libor", ExternalIdBundle.of(LIBOR_6M_ID),
      ACT_360, MODIFIED_FOLLOWING, 2, false, USD, LocalTime.of(11, 0), US, US, "Page", Tenor.SIX_MONTHS);
  private static final InterestRateFutureConvention RATE_FUTURE_3M = new InterestRateFutureConvention("USD 3m Rate Future", ExternalIdBundle.of(RATE_FUTURE_3M_ID),
      IMM_3M_EXPIRY_CONVENTION, NYLON, LIBOR_3M_ID);
  private static final InterestRateFutureConvention RATE_FUTURE_1M = new InterestRateFutureConvention("USD 1m Rate Future", ExternalIdBundle.of(RATE_FUTURE_1M_ID),
      IMM_1M_EXPIRY_CONVENTION, NYLON, LIBOR_3M_ID);
  private static final Map<ExternalId, Convention> CONVENTIONS = new HashMap<>();
  private static final CurveNodeToDefinitionConverter CONVERTER;

  static {
    CONVENTIONS.put(DEPOSIT_1D_ID, DEPOSIT_1D);
    CONVENTIONS.put(DEPOSIT_1M_ID, DEPOSIT_1M);
    CONVENTIONS.put(FIXED_LEG_ID, FIXED_LEG);
    CONVENTIONS.put(LIBOR_1M_ID, LIBOR_1M);
    CONVENTIONS.put(LIBOR_3M_ID, LIBOR_3M);
    CONVENTIONS.put(LIBOR_6M_ID, LIBOR_6M);
    CONVENTIONS.put(RATE_FUTURE_3M_ID, RATE_FUTURE_3M);
    CONVENTIONS.put(RATE_FUTURE_1M_ID, RATE_FUTURE_1M);
    CONVERTER = new CurveNodeToDefinitionConverter(new MyConventionSource(CONVENTIONS), new MyHolidaySource(CALENDAR, "US"), new MyRegionSource("US"));
  }

  @Test(expectedExceptions = UnsupportedOperationException.class)
  public void testContinuouslyCompoundedRateNode() {
    CONVERTER.getDefinitionForNode(new ContinuouslyCompoundedRateNode("name", Tenor.ONE_DAY), DEPOSIT_1D_ID, DateUtils.getUTCDate(2013, 5, 1), new SnapshotDataBundle());
  }

  @Test(expectedExceptions = UnsupportedOperationException.class)
  public void testCreditSpreadNode() {
    CONVERTER.getDefinitionForNode(new CreditSpreadNode("name", Tenor.ONE_DAY), DEPOSIT_1D_ID, DateUtils.getUTCDate(2013, 5, 1), new SnapshotDataBundle());
  }

  @Test(expectedExceptions = UnsupportedOperationException.class)
  public void testDiscountFactorNode() {
    CONVERTER.getDefinitionForNode(new DiscountFactorNode("name", Tenor.ONE_DAY), DEPOSIT_1D_ID, DateUtils.getUTCDate(2013, 5, 1), new SnapshotDataBundle());
  }

  @Test
  public void testOneDayDeposit() {
    final ExternalId marketDataId = ExternalId.of(SCHEME, "US1d");
    final double rate = 0.0012345;
    final SnapshotDataBundle marketValues = new SnapshotDataBundle();
    marketValues.setDataPoint(marketDataId, rate);
    final ZonedDateTime now = DateUtils.getUTCDate(2013, 5, 1);
    final CurveNode cashNode = new CashNode(new Tenor(Period.ZERO), Tenor.ONE_DAY, DEPOSIT_1D_ID, "Mapper");
    final InstrumentDefinition<?> definition = CONVERTER.getDefinitionForNode(cashNode, marketDataId, now, marketValues);
    assertTrue(definition instanceof CashDefinition);
    final CashDefinition cash = (CashDefinition) definition;
    final CashDefinition expectedCash = new CashDefinition(USD, DateUtils.getUTCDate(2013, 5, 1), DateUtils.getUTCDate(2013, 5, 2), 1, rate, 1. / 360);
    assertEquals(expectedCash, cash);
  }

  @Test
  public void testOneMonthDeposit() {
    final ExternalId marketDataId = ExternalId.of(SCHEME, "US1m");
    final double rate = 0.0012345;
    final SnapshotDataBundle marketValues = new SnapshotDataBundle();
    marketValues.setDataPoint(marketDataId, rate);
    ZonedDateTime now = DateUtils.getUTCDate(2013, 2, 4);
    CurveNode cashNode = new CashNode(new Tenor(Period.ZERO), Tenor.ONE_MONTH, DEPOSIT_1M_ID, "Mapper");
    InstrumentDefinition<?> definition = CONVERTER.getDefinitionForNode(cashNode, marketDataId, now, marketValues);
    assertTrue(definition instanceof CashDefinition);
    CashDefinition cash = (CashDefinition) definition;
    CashDefinition expectedCash = new CashDefinition(USD, DateUtils.getUTCDate(2013, 2, 6), DateUtils.getUTCDate(2013, 3, 6), 1, rate, 28. / 360);
    assertEquals(expectedCash, cash);
    now = DateUtils.getUTCDate(2013, 5, 2);
    cashNode = new CashNode(new Tenor(Period.ZERO), Tenor.ONE_MONTH, DEPOSIT_1M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(cashNode, marketDataId, now, marketValues);
    assertTrue(definition instanceof CashDefinition);
    cash = (CashDefinition) definition;
    expectedCash = new CashDefinition(USD, DateUtils.getUTCDate(2013, 5, 6), DateUtils.getUTCDate(2013, 6, 6), 1, rate, 31. / 360);
    assertEquals(expectedCash, cash);
    now = DateUtils.getUTCDate(2013, 5, 7);
    cashNode = new CashNode(Tenor.ONE_MONTH, Tenor.THREE_MONTHS, DEPOSIT_1M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(cashNode, marketDataId, now, marketValues);
    assertTrue(definition instanceof CashDefinition);
    cash = (CashDefinition) definition;
    expectedCash = new CashDefinition(USD, DateUtils.getUTCDate(2013, 6, 10), DateUtils.getUTCDate(2013, 9, 10), 1, rate, 92. / 360);
    assertEquals(expectedCash, cash);
  }

  @Test
  public void testLibor() {
    final ExternalId marketDataId = ExternalId.of(SCHEME, "US3mLibor");
    final double rate = 0.0012345;
    final SnapshotDataBundle marketValues = new SnapshotDataBundle();
    marketValues.setDataPoint(marketDataId, rate);
    final ZonedDateTime now = DateUtils.getUTCDate(2013, 2, 4);
    CurveNode iborNode = new CashNode(new Tenor(Period.ZERO), Tenor.THREE_MONTHS, LIBOR_3M_ID, "Mapper");
    InstrumentDefinition<?> definition = CONVERTER.getDefinitionForNode(iborNode, marketDataId, now, marketValues);
    assertTrue(definition instanceof DepositIborDefinition);
    DepositIborDefinition ibor = (DepositIborDefinition) definition;
    final IborIndex ibor3m = new IborIndex(USD, Tenor.THREE_MONTHS.getPeriod(), 0, THIRTY_360, MODIFIED_FOLLOWING, false);
    DepositIborDefinition expectedLibor = new DepositIborDefinition(USD, DateUtils.getUTCDate(2013, 2, 6), DateUtils.getUTCDate(2013, 5, 6), 1, rate, 90. / 360, ibor3m);
    assertEquals(expectedLibor, ibor);
    iborNode = new CashNode(new Tenor(Period.ZERO), Tenor.SIX_MONTHS, LIBOR_6M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(iborNode, marketDataId, now, marketValues);
    assertTrue(definition instanceof DepositIborDefinition);
    ibor = (DepositIborDefinition) definition;
    final IborIndex ibor6m = new IborIndex(USD, Tenor.SIX_MONTHS.getPeriod(), 0, ACT_360, MODIFIED_FOLLOWING, false);
    expectedLibor = new DepositIborDefinition(USD, DateUtils.getUTCDate(2013, 2, 6), DateUtils.getUTCDate(2013, 8, 6), 1, rate, 181. / 360, ibor6m);
    assertEquals(expectedLibor, ibor);
  }

  @Test
  public void testFRA() {
    final ExternalId marketDataId = ExternalId.of(SCHEME, "US6x9");
    final SnapshotDataBundle marketValues = new SnapshotDataBundle();
    final double rate = 0.01234;
    marketValues.setDataPoint(marketDataId, rate);
    final FRANode fraNode = new FRANode(Tenor.SIX_MONTHS, Tenor.NINE_MONTHS, LIBOR_3M_ID, "Mapper");
    final IborIndex index = new IborIndex(USD, Tenor.THREE_MONTHS.getPeriod(), 0, THIRTY_360, MODIFIED_FOLLOWING, false);
    final InstrumentDefinition<?> definition = CONVERTER.getDefinitionForNode(fraNode, marketDataId, DateUtils.getUTCDate(2013, 3, 1), marketValues);
    assertTrue(definition instanceof ForwardRateAgreementDefinition);
    final ForwardRateAgreementDefinition fra = (ForwardRateAgreementDefinition) definition;
    final ForwardRateAgreementDefinition expectedFRA = ForwardRateAgreementDefinition.from(DateUtils.getUTCDate(2013, 9, 5), DateUtils.getUTCDate(2013, 12, 2), 1, index, rate, CALENDAR);
    assertEquals(expectedFRA, fra);
  }

  @Test
  public void test3M3MIRFuture() {
    final ExternalId marketDataId = ExternalId.of(SCHEME, "3M3M future");
    final SnapshotDataBundle marketValues = new SnapshotDataBundle();
    final double rate = 0.98;
    marketValues.setDataPoint(marketDataId, rate);
    RateFutureNode futureNode = new RateFutureNode(1, new Tenor(Period.ZERO), Tenor.THREE_MONTHS, Tenor.THREE_MONTHS, RATE_FUTURE_3M_ID, LIBOR_3M_ID, "Mapper");
    final IborIndex index = new IborIndex(USD, Tenor.THREE_MONTHS.getPeriod(), 0, THIRTY_360, MODIFIED_FOLLOWING, false);
    final ZonedDateTime now = DateUtils.getUTCDate(2013, 5, 1);
    InstrumentDefinition<?> definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    InterestRateFutureTransactionDefinition future = (InterestRateFutureTransactionDefinition) definition;
    InterestRateFutureSecurityDefinition securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2013, 6, 17), index, 1, 0.25, "", CALENDAR);
    InterestRateFutureTransactionDefinition expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
    futureNode = new RateFutureNode(1, Tenor.TWO_MONTHS, Tenor.THREE_MONTHS, Tenor.THREE_MONTHS, RATE_FUTURE_3M_ID, LIBOR_3M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    future = (InterestRateFutureTransactionDefinition) definition;
    securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2013, 9, 16), index, 1, 0.25, "", CALENDAR);
    expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
    futureNode = new RateFutureNode(4, new Tenor(Period.ZERO), Tenor.THREE_MONTHS, Tenor.THREE_MONTHS, RATE_FUTURE_3M_ID, LIBOR_3M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    future = (InterestRateFutureTransactionDefinition) definition;
    securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2014, 3, 17), index, 1, 0.25, "", CALENDAR);
    expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
    futureNode = new RateFutureNode(5, Tenor.ONE_YEAR, Tenor.THREE_MONTHS, Tenor.THREE_MONTHS, RATE_FUTURE_3M_ID, LIBOR_3M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    future = (InterestRateFutureTransactionDefinition) definition;
    securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2015, 6, 15), index, 1, 0.25, "", CALENDAR);
    expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
  }

  @Test
  public void test1M3MIRFuture() {
    final ExternalId marketDataId = ExternalId.of(SCHEME, "1M3M future");
    final SnapshotDataBundle marketValues = new SnapshotDataBundle();
    final double rate = 0.98;
    marketValues.setDataPoint(marketDataId, rate);
    RateFutureNode futureNode = new RateFutureNode(1, new Tenor(Period.ZERO), Tenor.ONE_MONTH, Tenor.THREE_MONTHS, RATE_FUTURE_1M_ID, LIBOR_3M_ID, "Mapper");
    final IborIndex index = new IborIndex(USD, Tenor.THREE_MONTHS.getPeriod(), 0, THIRTY_360, MODIFIED_FOLLOWING, false);
    final ZonedDateTime now = DateUtils.getUTCDate(2013, 5, 1);
    InstrumentDefinition<?> definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    InterestRateFutureTransactionDefinition future = (InterestRateFutureTransactionDefinition) definition;
    InterestRateFutureSecurityDefinition securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2013, 5, 13), index, 1, 0.25, "", CALENDAR);
    InterestRateFutureTransactionDefinition expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
    futureNode = new RateFutureNode(1, Tenor.TWO_MONTHS, Tenor.ONE_MONTH, Tenor.THREE_MONTHS, RATE_FUTURE_1M_ID, LIBOR_3M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    future = (InterestRateFutureTransactionDefinition) definition;
    securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2013, 7, 15), index, 1, 0.25, "", CALENDAR);
    expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
    futureNode = new RateFutureNode(4, new Tenor(Period.ZERO), Tenor.ONE_MONTH, Tenor.THREE_MONTHS, RATE_FUTURE_1M_ID, LIBOR_3M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    future = (InterestRateFutureTransactionDefinition) definition;
    securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2013, 8, 19), index, 1, 0.25, "", CALENDAR);
    expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
    futureNode = new RateFutureNode(5, Tenor.ONE_YEAR, Tenor.ONE_MONTH, Tenor.THREE_MONTHS, RATE_FUTURE_1M_ID, LIBOR_3M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    future = (InterestRateFutureTransactionDefinition) definition;
    securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2014, 9, 15), index, 1, 0.25, "", CALENDAR);
    expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
  }

  @Test
  public void test3M1MIRFuture() {
    final ExternalId marketDataId = ExternalId.of(SCHEME, "3M1M future");
    final SnapshotDataBundle marketValues = new SnapshotDataBundle();
    final double rate = 0.98;
    marketValues.setDataPoint(marketDataId, rate);
    final double accrual = 1. / 12;
    RateFutureNode futureNode = new RateFutureNode(1, new Tenor(Period.ZERO), Tenor.THREE_MONTHS, Tenor.ONE_MONTH, RATE_FUTURE_3M_ID, LIBOR_1M_ID, "Mapper");
    final IborIndex index = new IborIndex(USD, Tenor.ONE_MONTH.getPeriod(), 0, THIRTY_360, MODIFIED_FOLLOWING, false);
    final ZonedDateTime now = DateUtils.getUTCDate(2013, 5, 1);
    InstrumentDefinition<?> definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    InterestRateFutureTransactionDefinition future = (InterestRateFutureTransactionDefinition) definition;
    InterestRateFutureSecurityDefinition securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2013, 6, 17), index, 1, accrual, "", CALENDAR);
    InterestRateFutureTransactionDefinition expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
    futureNode = new RateFutureNode(1, Tenor.TWO_MONTHS, Tenor.THREE_MONTHS, Tenor.ONE_MONTH, RATE_FUTURE_3M_ID, LIBOR_1M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    future = (InterestRateFutureTransactionDefinition) definition;
    securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2013, 9, 16), index, 1, accrual, "", CALENDAR);
    expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
    futureNode = new RateFutureNode(4, new Tenor(Period.ZERO), Tenor.THREE_MONTHS, Tenor.ONE_MONTH, RATE_FUTURE_3M_ID, LIBOR_1M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    future = (InterestRateFutureTransactionDefinition) definition;
    securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2014, 3, 17), index, 1, accrual, "", CALENDAR);
    expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
    futureNode = new RateFutureNode(5, Tenor.ONE_YEAR, Tenor.THREE_MONTHS, Tenor.ONE_MONTH, RATE_FUTURE_3M_ID, LIBOR_1M_ID, "Mapper");
    definition = CONVERTER.getDefinitionForNode(futureNode, marketDataId, now, marketValues);
    future = (InterestRateFutureTransactionDefinition) definition;
    securityDefinition = new InterestRateFutureSecurityDefinition(DateUtils.getUTCDate(2015, 6, 15), index, 1, accrual, "", CALENDAR);
    expectedFuture = new InterestRateFutureTransactionDefinition(securityDefinition, now, rate, 1).withNewNotionalAndTransactionPrice(1, rate);
    assertEquals(expectedFuture, future);
  }

  @Test
  public void testFixedIborSwap() {
    final ExternalId marketDataId = ExternalId.of(SCHEME, "3M1M future");
    final SnapshotDataBundle marketValues = new SnapshotDataBundle();
    final double rate = 0.02;
    marketValues.setDataPoint(marketDataId, rate);
    final SwapNode swapNode = new SwapNode(new Tenor(Period.ZERO), Tenor.TEN_YEARS, FIXED_LEG_ID, LIBOR_3M_ID, "Mapper");
    final InstrumentDefinition<?> definition = CONVERTER.getDefinitionForNode(swapNode, marketDataId, DateUtils.getUTCDate(2013, 3, 1), marketValues);
    assertTrue(definition instanceof SwapDefinition);
  }

  @Test
  public void testIborIborSwap() {

  }

  @Test
  public void testOIS() {

  }

  private static class MyConventionSource implements ConventionSource {
    private final Map<ExternalId, Convention> _conventions;

    public MyConventionSource(final Map<ExternalId, Convention> conventions) {
      _conventions = conventions;
    }

    @Override
    public Convention getConvention(final ExternalId identifier) {
      return _conventions.get(identifier);
    }

    @Override
    public CMSLegConvention getCMSLegConvention(final ExternalId identifier) {
      return null;
    }

    @Override
    public CompoundingIborLegConvention getCompoundingIborLegConvention(final ExternalId identifier) {
      return null;
    }

    @Override
    public DepositConvention getDepositConvention(final ExternalId identifier) {
      return null;
    }

    @Override
    public EquityConvention getEquityConvention(final ExternalId identifier) {
      return null;
    }

    @Override
    public InterestRateFutureConvention getInterestRateFutureConvention(final ExternalId identifier) {
      return null;
    }

    @Override
    public FXForwardAndSwapConvention getFXForwardAndSwapConvention(final ExternalId identifier) {
      return null;
    }

    @Override
    public FXSpotConvention getFXSpotConvention(final ExternalId identifier) {
      return null;
    }

    @Override
    public IborIndexConvention getIborIndexConvention(final ExternalId identifier) {
      return null;
    }

    @Override
    public OISLegConvention getOISLegConvention(final ExternalId identifier) {
      return null;
    }

    @Override
    public OvernightIndexConvention getOvernightIndexConvention(final ExternalId identifier) {
      return null;
    }

  }

  private static class MyHolidaySource implements HolidaySource {
    private final Calendar _calendar;
    private final ExternalId _regionId;
    private final UniqueId _uniqueId;
    private final SimpleHoliday _holiday;

    public MyHolidaySource(final Calendar calendar, final String country) {
      _calendar = calendar;
      _regionId = ExternalId.of(ExternalSchemes.ISO_COUNTRY_ALPHA2, country);
      _holiday = new SimpleHoliday();
      _uniqueId = UniqueId.of(UniqueId.EXTERNAL_SCHEME.getName(), _regionId.getValue());
      _holiday.setUniqueId(_uniqueId);
    }

    @Override
    public Holiday get(final UniqueId uniqueId) {
      if (uniqueId.equals(_uniqueId)) {
        return _holiday;
      }
      return null;
    }

    @Override
    public Holiday get(final ObjectId objectId, final VersionCorrection versionCorrection) {
      if (_regionId.equals(objectId)) {
        return _holiday;
      }
      return null;
    }

    @Override
    public Map<UniqueId, Holiday> get(final Collection<UniqueId> uniqueIds) {
      for (final UniqueId uniqueId : uniqueIds) {
        if (_uniqueId.equals(uniqueId)) {
          return Collections.<UniqueId, Holiday>singletonMap(uniqueId, _holiday);
        }
      }
      return Collections.emptyMap();
    }

    @Override
    public Map<ObjectId, Holiday> get(final Collection<ObjectId> objectIds, final VersionCorrection versionCorrection) {
      for (final ObjectId objectId : objectIds) {
        if (_regionId.equals(objectId)) {
          return Collections.<ObjectId, Holiday>singletonMap(objectId, _holiday);
        }
      }
      return Collections.emptyMap();
    }

    @Override
    public boolean isHoliday(final LocalDate dateToCheck, final Currency currency) {
      return !_calendar.isWorkingDay(dateToCheck);
    }

    @Override
    public boolean isHoliday(final LocalDate dateToCheck, final HolidayType holidayType, final ExternalIdBundle regionOrExchangeIds) {
      return !_calendar.isWorkingDay(dateToCheck);
    }

    @Override
    public boolean isHoliday(final LocalDate dateToCheck, final HolidayType holidayType, final ExternalId regionOrExchangeId) {
      return !_calendar.isWorkingDay(dateToCheck);
    }

  }

  private static class MyRegionSource implements RegionSource {
    private final ExternalIdBundle _regionBundle;
    private final Region _region;

    public MyRegionSource(final String countryId) {
      final SimpleRegion region = new SimpleRegion();
      final ExternalId id = ExternalId.of(ExternalSchemes.ISO_COUNTRY_ALPHA2, countryId);
      region.addExternalId(id);
      region.setUniqueId(UniqueId.of(UniqueId.EXTERNAL_SCHEME.getName(), id.getValue()));
      _regionBundle = ExternalIdBundle.of(id);
      _region = region;
    }

    @Override
    public Collection<Region> get(final ExternalIdBundle bundle, final VersionCorrection versionCorrection) {
      return Collections.singleton(_region);
    }

    @Override
    public Map<ExternalIdBundle, Collection<Region>> getAll(final Collection<ExternalIdBundle> bundles, final VersionCorrection versionCorrection) {
      for (final ExternalIdBundle bundle : bundles) {
        if(_regionBundle.equals(bundle)) {
          return Collections.<ExternalIdBundle, Collection<Region>>singletonMap(_regionBundle, Collections.singleton(_region));
        }
      }
      return Collections.emptyMap();
    }

    @Override
    public Collection<Region> get(final ExternalIdBundle bundle) {
      if (_regionBundle.equals(bundle)) {
        return Collections.singleton(_region);
      }
      return Collections.emptySet();
    }

    @Override
    public Region getSingle(final ExternalIdBundle bundle) {
      if (_regionBundle.equals(bundle)) {
        return _region;
      }
      return null;
    }

    @Override
    public Region getSingle(final ExternalIdBundle bundle, final VersionCorrection versionCorrection) {
      if (_regionBundle.equals(bundle)) {
        return _region;
      }
      return null;
    }

    @Override
    public Map<ExternalIdBundle, Region> getSingle(final Collection<ExternalIdBundle> bundles, final VersionCorrection versionCorrection) {
      for (final ExternalIdBundle bundle : bundles) {
        if(_regionBundle.equals(bundle)) {
          return Collections.<ExternalIdBundle, Region>singletonMap(_regionBundle, _region);
        }
      }
      return Collections.emptyMap();
    }

    @Override
    public Region get(final UniqueId uniqueId) {
      if (_region.getUniqueId().equals(uniqueId)) {
        return _region;
      }
      return null;
    }

    @Override
    public Region get(final ObjectId objectId, final VersionCorrection versionCorrection) {
      if (_region.getUniqueId().equals(objectId.atLatestVersion())) {
        return _region;
      }
      return null;
    }

    @Override
    public Map<UniqueId, Region> get(final Collection<UniqueId> uniqueIds) {
      for (final UniqueId uniqueId : uniqueIds) {
        if (_region.getUniqueId().equals(uniqueId)) {
          return Collections.singletonMap(uniqueId, _region);
        }
      }
      return null;
    }

    @Override
    public Map<ObjectId, Region> get(final Collection<ObjectId> objectIds, final VersionCorrection versionCorrection) {
      for (final ObjectId objectId : objectIds) {
        if (_region.getUniqueId().equals(objectId.atLatestVersion())) {
          return Collections.singletonMap(objectId, _region);
        }
      }
      return null;
    }

    @Override
    public ChangeManager changeManager() {
      return DummyChangeManager.INSTANCE;
    }

    @Override
    public Region getHighestLevelRegion(final ExternalId externalId) {
      return _region;
    }

    @Override
    public Region getHighestLevelRegion(final ExternalIdBundle bundle) {
      return _region;
    }

  }
}
