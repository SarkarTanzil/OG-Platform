/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sesame.function.scenarios.curvedata;

import static com.opengamma.sesame.config.ConfigBuilder.argument;
import static com.opengamma.sesame.config.ConfigBuilder.arguments;
import static com.opengamma.sesame.config.ConfigBuilder.config;
import static com.opengamma.sesame.config.ConfigBuilder.function;
import static com.opengamma.sesame.config.ConfigBuilder.implementations;
import static com.opengamma.util.result.ResultTestUtils.assertSuccess;

import java.util.Map;

import org.testng.annotations.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.financial.analytics.curve.CurveSpecification;
import com.opengamma.financial.currency.SimpleCurrencyMatrix;
import com.opengamma.id.ExternalIdBundle;
import com.opengamma.sesame.CurveSpecificationMarketDataFn;
import com.opengamma.sesame.DefaultCurveSpecificationMarketDataFn;
import com.opengamma.sesame.Environment;
import com.opengamma.sesame.SimpleEnvironment;
import com.opengamma.sesame.config.FunctionModelConfig;
import com.opengamma.sesame.function.Output;
import com.opengamma.sesame.function.scenarios.FilteredScenarioDefinition;
import com.opengamma.sesame.graph.FunctionModel;
import com.opengamma.sesame.marketdata.DefaultMarketDataFn;
import com.opengamma.sesame.marketdata.MapMarketDataBundle;
import com.opengamma.sesame.marketdata.MarketDataBundle;
import com.opengamma.sesame.marketdata.MarketDataEnvironmentBuilder;
import com.opengamma.sesame.marketdata.MarketDataFn;
import com.opengamma.sesame.marketdata.RawId;
import com.opengamma.util.result.Result;
import com.opengamma.util.test.TestGroup;

@Test(groups = TestGroup.UNIT)
@SuppressWarnings("unchecked")
public class CurveDataShiftDecoratorTest {

  private static final ZonedDateTime VALUATION_TIME = ZonedDateTime.now();
  private static final MarketDataBundle MARKET_DATA_BUNDLE =
      new MapMarketDataBundle(new MarketDataEnvironmentBuilder()
                                  .add(RawId.of(CurveTestUtils.ID1.toBundle()), 0.1)
                                  .add(RawId.of(CurveTestUtils.ID2.toBundle()), 0.2)
                                  .add(RawId.of(CurveTestUtils.ID3.toBundle()), 0.7)
                                  .add(RawId.of(CurveTestUtils.ID4.toBundle()), 0.4)
                                  .valuationTime(ZonedDateTime.now())
                                  .build());

  private static final FunctionModelConfig CONFIG =
      config(implementations(Fn.class, Impl.class,
                             MarketDataFn.class, DefaultMarketDataFn.class,
                             CurveSpecificationMarketDataFn.class, DefaultCurveSpecificationMarketDataFn.class),
             arguments(function(DefaultMarketDataFn.class, argument("currencyMatrix", new SimpleCurrencyMatrix()))));
  private static final FunctionModelConfig DECORATED_CONFIG = CONFIG.decoratedWith(CurveDataShiftDecorator.class);
  private static final Fn FN = FunctionModel.build(Fn.class, DECORATED_CONFIG);

  private static final CurveSpecificationMatcher MATCHER = CurveSpecificationMatcher.named(CurveTestUtils.CURVE_NAME);

  @Test
  public void absolute() {
    CurveDataParallelShift shift = new CurveDataParallelShift(0.1, MATCHER);
    FilteredScenarioDefinition scenarioDef = new FilteredScenarioDefinition(shift);
    SimpleEnvironment env = new SimpleEnvironment(VALUATION_TIME, MARKET_DATA_BUNDLE, scenarioDef);

    Result<Map<ExternalIdBundle, Double>> result = FN.foo(env, CurveTestUtils.CURVE_SPEC);
    assertSuccess(result);
    Map<ExternalIdBundle, Double> shiftedValues = result.getValue();
    CurveTestUtils.checkValues(shiftedValues, 0.2, 0.3, 0.6, 0.5);
  }

  @Test
  public void relative() {
    CurveDataRelativeShift shift = new CurveDataRelativeShift(0.1, MATCHER);
    FilteredScenarioDefinition scenarioDef = new FilteredScenarioDefinition(shift);
    SimpleEnvironment env = new SimpleEnvironment(VALUATION_TIME, MARKET_DATA_BUNDLE, scenarioDef);

    Result<Map<ExternalIdBundle, Double>> result = FN.foo(env, CurveTestUtils.CURVE_SPEC);
    assertSuccess(result);
    Map<ExternalIdBundle, Double> shiftedValues = result.getValue();
    CurveTestUtils.checkValues(shiftedValues, 0.11, 0.22, 0.67, 0.44);
  }

  @Test
  public void noMatch() {
    CurveDataParallelShift shift = new CurveDataParallelShift(0.1, MATCHER);
    FilteredScenarioDefinition scenarioDef = new FilteredScenarioDefinition(shift);
    SimpleEnvironment env = new SimpleEnvironment(VALUATION_TIME, MARKET_DATA_BUNDLE, scenarioDef);

    CurveSpecification curveSpec = new CurveSpecification(LocalDate.now(), "a different name", CurveTestUtils.NODES);
    Result<Map<ExternalIdBundle, Double>> result = FN.foo(env, curveSpec);
    assertSuccess(result);
    Map<ExternalIdBundle, Double> shiftedValues = result.getValue();
    CurveTestUtils.checkValues(shiftedValues, 0.1, 0.2, 0.7, 0.4);
  }

  @Test
  public void multipleSameType() {
    CurveDataParallelShift shift1 = new CurveDataParallelShift(0.1, MATCHER);
    CurveDataParallelShift shift2 = new CurveDataParallelShift(0.2, MATCHER);
    FilteredScenarioDefinition scenarioDef = new FilteredScenarioDefinition(shift1, shift2);
    SimpleEnvironment env = new SimpleEnvironment(VALUATION_TIME, MARKET_DATA_BUNDLE, scenarioDef);

    Result<Map<ExternalIdBundle, Double>> result = FN.foo(env, CurveTestUtils.CURVE_SPEC);
    assertSuccess(result);
    Map<ExternalIdBundle, Double> shiftedValues = result.getValue();
    CurveTestUtils.checkValues(shiftedValues, 0.4, 0.5, 0.4, 0.7);
  }

  @Test
  public void multipleDifferentTypes() {
    CurveDataParallelShift abs = new CurveDataParallelShift(0.1, MATCHER);
    CurveDataRelativeShift rel = new CurveDataRelativeShift(0.1, MATCHER);
    FilteredScenarioDefinition scenarioDef = new FilteredScenarioDefinition(abs, rel);
    SimpleEnvironment env = new SimpleEnvironment(VALUATION_TIME, MARKET_DATA_BUNDLE, scenarioDef);

    Result<Map<ExternalIdBundle, Double>> result = FN.foo(env, CurveTestUtils.CURVE_SPEC);
    assertSuccess(result);
    Map<ExternalIdBundle, Double> shiftedValues = result.getValue();
    CurveTestUtils.checkValues(shiftedValues, 0.22, 0.33, 0.56, 0.55);
  }

  @Test
  public void multipleReversed() {
    CurveDataRelativeShift rel = new CurveDataRelativeShift(0.1, MATCHER);
    CurveDataParallelShift abs = new CurveDataParallelShift(0.1, MATCHER);
    FilteredScenarioDefinition scenarioDef = new FilteredScenarioDefinition(rel, abs);
    SimpleEnvironment env = new SimpleEnvironment(VALUATION_TIME, MARKET_DATA_BUNDLE, scenarioDef);

    Result<Map<ExternalIdBundle, Double>> result = FN.foo(env, CurveTestUtils.CURVE_SPEC);
    assertSuccess(result);
    Map<ExternalIdBundle, Double> shiftedValues = result.getValue();
    CurveTestUtils.checkValues(shiftedValues, 0.21, 0.32, 0.57, 0.54);
  }

  public interface Fn {

    @Output("Foo")
    Result<Map<ExternalIdBundle, Double>> foo(Environment env, CurveSpecification curveSpec);
  }

  public static class Impl implements Fn {

    private final CurveSpecificationMarketDataFn _marketDataFn;

    public Impl(CurveSpecificationMarketDataFn marketDataFn) {
      _marketDataFn = marketDataFn;
    }

    @Override
    public Result<Map<ExternalIdBundle, Double>> foo(Environment env, CurveSpecification curveSpec) {
      return _marketDataFn.requestData(env, curveSpec);
    }
  }
}
