/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.sesame.credit;

import static com.opengamma.sesame.config.ConfigBuilder.argument;
import static com.opengamma.sesame.config.ConfigBuilder.arguments;
import static com.opengamma.sesame.config.ConfigBuilder.columns;
import static com.opengamma.sesame.config.ConfigBuilder.config;
import static com.opengamma.sesame.config.ConfigBuilder.configureView;
import static com.opengamma.sesame.config.ConfigBuilder.function;
import static com.opengamma.sesame.config.ConfigBuilder.nonPortfolioOutput;
import static com.opengamma.sesame.config.ConfigBuilder.nonPortfolioOutputs;
import static com.opengamma.sesame.config.ConfigBuilder.output;
import static org.mockito.Mockito.mock;
import static org.testng.AssertJUnit.assertNotNull;

import java.util.EnumSet;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.codahale.metrics.MetricRegistry;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.opengamma.core.config.ConfigSource;
import com.opengamma.core.convention.ConventionSource;
import com.opengamma.core.historicaltimeseries.HistoricalTimeSeriesSource;
import com.opengamma.core.holiday.HolidaySource;
import com.opengamma.core.link.SnapshotLink;
import com.opengamma.core.marketdatasnapshot.NamedSnapshot;
import com.opengamma.core.region.RegionSource;
import com.opengamma.core.security.SecuritySource;
import com.opengamma.financial.analytics.isda.credit.CreditCurveData;
import com.opengamma.financial.analytics.isda.credit.CreditCurveDataKey;
import com.opengamma.financial.analytics.isda.credit.CreditCurveDataSnapshot;
import com.opengamma.financial.analytics.isda.credit.YieldCurveDataSnapshot;
import com.opengamma.financial.convention.ConventionBundleSource;
import com.opengamma.master.historicaltimeseries.HistoricalTimeSeriesResolver;
import com.opengamma.sesame.DirectExecutorService;
import com.opengamma.sesame.OutputNames;
import com.opengamma.sesame.cache.NoOpCacheInvalidator;
import com.opengamma.sesame.config.FunctionModelConfig;
import com.opengamma.sesame.config.ViewConfig;
import com.opengamma.sesame.credit.snapshot.SnapshotCreditCurveDataProviderFn;
import com.opengamma.sesame.credit.snapshot.SnapshotYieldCurveDataProviderFn;
import com.opengamma.sesame.engine.ComponentMap;
import com.opengamma.sesame.engine.FunctionService;
import com.opengamma.sesame.engine.View;
import com.opengamma.sesame.engine.ViewFactory;
import com.opengamma.sesame.function.AvailableImplementations;
import com.opengamma.sesame.function.AvailableImplementationsImpl;
import com.opengamma.sesame.function.AvailableOutputs;
import com.opengamma.sesame.function.AvailableOutputsImpl;
import com.opengamma.sesame.function.scenarios.curvedata.FunctionTestUtils;
import com.opengamma.sesame.graph.FunctionModel;
import com.opengamma.sesame.marketdata.HistoricalMarketDataFn;
import com.opengamma.sesame.marketdata.MarketDataFn;

/**
 * Tests graph building for credit curve functions.
 */
public class StandardIsdaCompliantCreditCurveGraphTest {
  
  private ViewFactory _viewFactory;

  @BeforeMethod
  public void beforeMethod() {
    
    ComponentMap componentMap = componentMap(
        ConfigSource.class,
        ConventionSource.class,
        ConventionBundleSource.class,
        HistoricalTimeSeriesResolver.class,
        SecuritySource.class,
        HolidaySource.class,
        HistoricalTimeSeriesSource.class,
        MarketDataFn.class,
        HistoricalMarketDataFn.class,
        RegionSource.class);

    AvailableOutputs availableOutputs = new AvailableOutputsImpl();
    availableOutputs.register(IsdaCompliantYieldCurveFn.class,
                              IsdaCompliantCreditCurveFn.class);
    
    AvailableImplementations availableImplementations = new AvailableImplementationsImpl();
    availableImplementations.register(DefaultIsdaCompliantYieldCurveFn.class,
                                      SnapshotYieldCurveDataProviderFn.class,
                                      SnapshotCreditCurveDataProviderFn.class,
                                      StandardIsdaCompliantCreditCurveFn.class);

    _viewFactory = new ViewFactory(new DirectExecutorService(),
                                   componentMap,
                                   availableOutputs,
                                   availableImplementations,
                                   FunctionModelConfig.EMPTY,
                                   EnumSet.noneOf(FunctionService.class),
                                   FunctionTestUtils.createCacheBuilder(),
                                   new NoOpCacheInvalidator(),
                                   Optional.<MetricRegistry>absent());
    
  }
  
  @Test
  public void testBuildYieldCurve() {
    ViewConfig config = createViewConfig();
    
    View view = _viewFactory.createView(config);
    
    FunctionModel functionModel = view.getFunctionModel("Yield curve");
    assertNotNull(functionModel);
    System.out.println(functionModel.prettyPrint());
    
  }

  @Test
  public void testBuildCreditCurve() {
    
    ViewConfig config = createViewConfig();
    
    View view = _viewFactory.createView(config);
    
    FunctionModel functionModel = view.getFunctionModel("Credit curve");
    assertNotNull(functionModel);
    System.out.println(functionModel.prettyPrint());
    
  }

  private ViewConfig createViewConfig() {
    //note: ideally should use mock(CreditCurveDataSnapshot.class) for this.
    //despite CreditCurveDataSnapshot being final, powermock still allows it to be mocked.
    //however due to a bug in Java Version 7 Update 65, this is disabled for now.
    //see:
    //https://code.google.com/p/powermock/issues/detail?id=504
    //https://bugs.openjdk.java.net/browse/JDK-8051012
    Map<CreditCurveDataKey, CreditCurveData> emptyData = Maps.newHashMap();
    NamedSnapshot snapshot = CreditCurveDataSnapshot.builder().creditCurves(emptyData).name("test").build();

    return configureView(
        "Yield curve",
        config(
            arguments(
                function(
                    SnapshotYieldCurveDataProviderFn.class,
                    argument("snapshotLink", SnapshotLink.resolved(mock(YieldCurveDataSnapshot.class)))),
                function(
                    SnapshotCreditCurveDataProviderFn.class,
                    argument("snapshotLink",
                             SnapshotLink.resolved(snapshot))))),
        columns(),
        nonPortfolioOutputs(
            nonPortfolioOutput("Yield curve", output(OutputNames.ISDA_YIELD_CURVE)),
            nonPortfolioOutput("Credit curve", output(OutputNames.ISDA_CREDIT_CURVE))));

  }
  
  private static ComponentMap componentMap(Class<?>... componentTypes) {
    Map<Class<?>, Object> compMap = Maps.newHashMap();
    for (Class<?> componentType : componentTypes) {
      compMap.put(componentType, mock(componentType));
    }
    return ComponentMap.of(compMap);
  }
  
}
