/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.examples.loader;

import com.opengamma.examples.tool.AbstractTool;
import com.opengamma.financial.analytics.fxforwardcurve.FXForwardCurveConfigPopulator;
import com.opengamma.financial.analytics.ircurve.YieldCurveConfigPopulator;
import com.opengamma.financial.analytics.volatility.cube.VolatilityCubeConfigPopulator;
import com.opengamma.financial.analytics.volatility.surface.EquityOptionSurfaceConfigPopulator;
import com.opengamma.financial.analytics.volatility.surface.FXOptionVolatilitySurfaceConfigPopulator;
import com.opengamma.financial.analytics.volatility.surface.IRFutureOptionSurfaceConfigPopulator;
import com.opengamma.financial.analytics.volatility.surface.SwaptionVolatilitySurfaceConfigPopulator;
import com.opengamma.financial.currency.CurrencyMatrixConfigPopulator;
import com.opengamma.master.config.ConfigMaster;

/**
 * 
 */
public class ExampleCurveAndSurfaceDefinitionLoader extends AbstractTool {

  @Override
  protected void doRun() throws Exception {
    ConfigMaster configMaster = getToolContext().getConfigMaster();
    
    new YieldCurveConfigPopulator(configMaster);
    new CurrencyMatrixConfigPopulator(configMaster);
    new SwaptionVolatilitySurfaceConfigPopulator(configMaster);
    new IRFutureOptionSurfaceConfigPopulator(configMaster);
    new FXOptionVolatilitySurfaceConfigPopulator(configMaster);
    new EquityOptionSurfaceConfigPopulator(configMaster);
    new VolatilityCubeConfigPopulator(configMaster);
    new FXForwardCurveConfigPopulator(configMaster);
  }
  
  //-------------------------------------------------------------------------
  /**
   * Main method to run the tool.
   * 
   * @param args  the arguments, unused
   */
  public static void main(String[] args) {  // CSIGNORE
    if (init()) {
      new ExampleCurveAndSurfaceDefinitionLoader().run();
    }
    System.exit(0);
  }

}
