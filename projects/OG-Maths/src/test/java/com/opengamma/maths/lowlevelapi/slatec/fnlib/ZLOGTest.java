/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.maths.lowlevelapi.slatec.fnlib;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.opengamma.analytics.math.statistics.distribution.fnlib.D1MACH;
import com.opengamma.maths.commonapi.numbers.ComplexType;
import com.opengamma.maths.highlevelapi.datatypes.primitive.OGComplexMatrix;

/**
 * Tests complex square root
 */
public class ZLOGTest {
  private static double[][] realP = { {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 },
      {-10.00, -9.00, -8.00, -7.00, -6.00, -5.00, -4.00, -3.00, -2.00, -1.00, 0.00, 1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00, 8.00, 9.00, 10.00 } };

  private static double[][] imagP = {
      {-10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00, -10.00 },
      {-9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00, -9.00 },
      {-8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00, -8.00 },
      {-7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00, -7.00 },
      {-6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00, -6.00 },
      {-5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00, -5.00 },
      {-4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00, -4.00 },
      {-3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00, -3.00 },
      {-2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00, -2.00 },
      {-1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00, -1.00 },
      {0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00 },
      {1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00, 1.00 },
      {2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00, 2.00 },
      {3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00, 3.00 },
      {4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00, 4.00 },
      {5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00, 5.00 },
      {6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00, 6.00 },
      {7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00, 7.00 },
      {8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00, 8.00 },
      {9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00, 9.00 },
      {10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00, 10.00 } };

  private static double[][] answerReal = {
      {2.6491586832740182, 2.5992485156329130, 2.5499332139120994, 2.5019731529727296, 2.4563274428680262, 2.4141568686511508, 2.3767950955531822, 2.3456739411145717, 2.3221954495706862,
          2.3075602584206298, 2.3025850929940459, 2.3075602584206298, 2.3221954495706862, 2.3456739411145717, 2.3767950955531822, 2.4141568686511508, 2.4563274428680262, 2.5019731529727296,
          2.5499332139120994, 2.5992485156329130, 2.6491586832740182 },
      {2.5992485156329130, 2.5437981676161918, 2.4883668712102871, 2.4337672252277911, 2.3810869673988782, 2.3317195470560335, 2.2873554892516914, 2.2499048351651325, 2.2213256282451583,
          2.2033596236321267, 2.1972245773362196, 2.2033596236321267, 2.2213256282451583, 2.2499048351651325, 2.2873554892516914, 2.3317195470560335, 2.3810869673988782, 2.4337672252277911,
          2.4883668712102871, 2.5437981676161918, 2.5992485156329130 },
      {2.5499332139120994, 2.4883668712102871, 2.4260151319598084, 2.3636939093561704, 2.3025850929940459, 2.2443181848660698, 2.1910133173369410, 2.1452297205741955, 2.1097538525880535,
          2.0871936349478184, 2.0794415416798357, 2.0871936349478184, 2.1097538525880535, 2.1452297205741955, 2.1910133173369410, 2.2443181848660698, 2.3025850929940459, 2.3636939093561704,
          2.4260151319598084, 2.4883668712102871, 2.5499332139120994 },
      {2.5019731529727296, 2.4337672252277911, 2.3636939093561704, 2.2924837393352862, 2.2213256282451583, 2.1520325466020847, 2.0871936349478184, 2.0302215052732095, 1.9851459567760610,
          1.9560115027140730, 1.9459101490553132, 1.9560115027140730, 1.9851459567760610, 2.0302215052732095, 2.0871936349478184, 2.1520325466020847, 2.2213256282451583, 2.2924837393352862,
          2.3636939093561704, 2.4337672252277911, 2.5019731529727296 },
      {2.4563274428680262, 2.3810869673988782, 2.3025850929940459, 2.2213256282451583, 2.1383330595080277, 2.0554369320866557, 1.9756218592907135, 1.9033312448851600, 1.8444397270569681,
          1.8054589563221122, 1.7917594692280550, 1.8054589563221122, 1.8444397270569681, 1.9033312448851600, 1.9756218592907135, 2.0554369320866557, 2.1383330595080277, 2.2213256282451583,
          2.3025850929940459, 2.3810869673988782, 2.4563274428680262 },
      {2.4141568686511508, 2.3317195470560335, 2.2443181848660698, 2.1520325466020847, 2.0554369320866557, 1.9560115027140730, 1.8567860333521540, 1.7631802623080808, 1.6836479149932368,
          1.6290482690107408, 1.6094379124341003, 1.6290482690107408, 1.6836479149932368, 1.7631802623080808, 1.8567860333521540, 1.9560115027140730, 2.0554369320866557, 2.1520325466020847,
          2.2443181848660698, 2.3317195470560335, 2.4141568686511508 },
      {2.3767950955531822, 2.2873554892516914, 2.1910133173369410, 2.0871936349478184, 1.9756218592907135, 1.8567860333521540, 1.7328679513998633, 1.6094379124341003, 1.4978661367769956,
          1.4166066720281081, 1.3862943611198906, 1.4166066720281081, 1.4978661367769956, 1.6094379124341003, 1.7328679513998633, 1.8567860333521540, 1.9756218592907135, 2.0871936349478184,
          2.1910133173369410, 2.2873554892516914, 2.3767950955531822 },
      {2.3456739411145717, 2.2499048351651325, 2.1452297205741955, 2.0302215052732095, 1.9033312448851600, 1.7631802623080808, 1.6094379124341003, 1.4451858789480823, 1.2824746787307684,
          1.1512925464970230, 1.0986122886681098, 1.1512925464970230, 1.2824746787307684, 1.4451858789480823, 1.6094379124341003, 1.7631802623080808, 1.9033312448851600, 2.0302215052732095,
          2.1452297205741955, 2.2499048351651325, 2.3456739411145717 },
      {2.3221954495706862, 2.2213256282451583, 2.1097538525880535, 1.9851459567760610, 1.8444397270569681, 1.6836479149932368, 1.4978661367769956, 1.2824746787307684, 1.0397207708399181,
          0.8047189562170503, 0.6931471805599453, 0.8047189562170503, 1.0397207708399181, 1.2824746787307684, 1.4978661367769956, 1.6836479149932368, 1.8444397270569681, 1.9851459567760610,
          2.1097538525880535, 2.2213256282451583, 2.3221954495706862 },
      {2.3075602584206298, 2.2033596236321267, 2.0871936349478184, 1.9560115027140730, 1.8054589563221122, 1.6290482690107408, 1.4166066720281081, 1.1512925464970230, 0.8047189562170503,
          0.3465735902799727, 0.0000000000000000, 0.3465735902799727, 0.8047189562170503, 1.1512925464970230, 1.4166066720281081, 1.6290482690107408, 1.8054589563221122, 1.9560115027140730,
          2.0871936349478184, 2.2033596236321267, 2.3075602584206298 },
      {2.3025850929940459, 2.1972245773362196, 2.0794415416798357, 1.9459101490553132, 1.7917594692280550, 1.6094379124341003, 1.3862943611198906, 1.0986122886681098, 0.6931471805599453,
          0.0000000000000000, Double.NEGATIVE_INFINITY, 0.0000000000000000, 0.6931471805599453, 1.0986122886681098, 1.3862943611198906, 1.6094379124341003, 1.7917594692280550, 1.9459101490553132,
          2.0794415416798357, 2.1972245773362196, 2.3025850929940459 },
      {2.3075602584206298, 2.2033596236321267, 2.0871936349478184, 1.9560115027140730, 1.8054589563221122, 1.6290482690107408, 1.4166066720281081, 1.1512925464970230, 0.8047189562170503,
          0.3465735902799727, 0.0000000000000000, 0.3465735902799727, 0.8047189562170503, 1.1512925464970230, 1.4166066720281081, 1.6290482690107408, 1.8054589563221122, 1.9560115027140730,
          2.0871936349478184, 2.2033596236321267, 2.3075602584206298 },
      {2.3221954495706862, 2.2213256282451583, 2.1097538525880535, 1.9851459567760610, 1.8444397270569681, 1.6836479149932368, 1.4978661367769956, 1.2824746787307684, 1.0397207708399181,
          0.8047189562170503, 0.6931471805599453, 0.8047189562170503, 1.0397207708399181, 1.2824746787307684, 1.4978661367769956, 1.6836479149932368, 1.8444397270569681, 1.9851459567760610,
          2.1097538525880535, 2.2213256282451583, 2.3221954495706862 },
      {2.3456739411145717, 2.2499048351651325, 2.1452297205741955, 2.0302215052732095, 1.9033312448851600, 1.7631802623080808, 1.6094379124341003, 1.4451858789480823, 1.2824746787307684,
          1.1512925464970230, 1.0986122886681098, 1.1512925464970230, 1.2824746787307684, 1.4451858789480823, 1.6094379124341003, 1.7631802623080808, 1.9033312448851600, 2.0302215052732095,
          2.1452297205741955, 2.2499048351651325, 2.3456739411145717 },
      {2.3767950955531822, 2.2873554892516914, 2.1910133173369410, 2.0871936349478184, 1.9756218592907135, 1.8567860333521540, 1.7328679513998633, 1.6094379124341003, 1.4978661367769956,
          1.4166066720281081, 1.3862943611198906, 1.4166066720281081, 1.4978661367769956, 1.6094379124341003, 1.7328679513998633, 1.8567860333521540, 1.9756218592907135, 2.0871936349478184,
          2.1910133173369410, 2.2873554892516914, 2.3767950955531822 },
      {2.4141568686511508, 2.3317195470560335, 2.2443181848660698, 2.1520325466020847, 2.0554369320866557, 1.9560115027140730, 1.8567860333521540, 1.7631802623080808, 1.6836479149932368,
          1.6290482690107408, 1.6094379124341003, 1.6290482690107408, 1.6836479149932368, 1.7631802623080808, 1.8567860333521540, 1.9560115027140730, 2.0554369320866557, 2.1520325466020847,
          2.2443181848660698, 2.3317195470560335, 2.4141568686511508 },
      {2.4563274428680262, 2.3810869673988782, 2.3025850929940459, 2.2213256282451583, 2.1383330595080277, 2.0554369320866557, 1.9756218592907135, 1.9033312448851600, 1.8444397270569681,
          1.8054589563221122, 1.7917594692280550, 1.8054589563221122, 1.8444397270569681, 1.9033312448851600, 1.9756218592907135, 2.0554369320866557, 2.1383330595080277, 2.2213256282451583,
          2.3025850929940459, 2.3810869673988782, 2.4563274428680262 },
      {2.5019731529727296, 2.4337672252277911, 2.3636939093561704, 2.2924837393352862, 2.2213256282451583, 2.1520325466020847, 2.0871936349478184, 2.0302215052732095, 1.9851459567760610,
          1.9560115027140730, 1.9459101490553132, 1.9560115027140730, 1.9851459567760610, 2.0302215052732095, 2.0871936349478184, 2.1520325466020847, 2.2213256282451583, 2.2924837393352862,
          2.3636939093561704, 2.4337672252277911, 2.5019731529727296 },
      {2.5499332139120994, 2.4883668712102871, 2.4260151319598084, 2.3636939093561704, 2.3025850929940459, 2.2443181848660698, 2.1910133173369410, 2.1452297205741955, 2.1097538525880535,
          2.0871936349478184, 2.0794415416798357, 2.0871936349478184, 2.1097538525880535, 2.1452297205741955, 2.1910133173369410, 2.2443181848660698, 2.3025850929940459, 2.3636939093561704,
          2.4260151319598084, 2.4883668712102871, 2.5499332139120994 },
      {2.5992485156329130, 2.5437981676161918, 2.4883668712102871, 2.4337672252277911, 2.3810869673988782, 2.3317195470560335, 2.2873554892516914, 2.2499048351651325, 2.2213256282451583,
          2.2033596236321267, 2.1972245773362196, 2.2033596236321267, 2.2213256282451583, 2.2499048351651325, 2.2873554892516914, 2.3317195470560335, 2.3810869673988782, 2.4337672252277911,
          2.4883668712102871, 2.5437981676161918, 2.5992485156329130 },
      {2.6491586832740182, 2.5992485156329130, 2.5499332139120994, 2.5019731529727296, 2.4563274428680262, 2.4141568686511508, 2.3767950955531822, 2.3456739411145717, 2.3221954495706862,
          2.3075602584206298, 2.3025850929940459, 2.3075602584206298, 2.3221954495706862, 2.3456739411145717, 2.3767950955531822, 2.4141568686511508, 2.4563274428680262, 2.5019731529727296,
          2.5499332139120994, 2.5992485156329130, 2.6491586832740182 } };

  private static double[][] answerImag = {
      {-2.3561944901923448, -2.3036114285814033, -2.2455372690184494, -2.1815222911841055, -2.1112158270654806, -2.0344439357957027, -1.9513027039072615, -1.8622531212727638, -1.7681918866447774,
          -1.6704649792860586, -1.5707963267948966, -1.4711276743037347, -1.3734007669450159, -1.2793395323170296, -1.1902899496825317, -1.1071487177940904, -1.0303768265243125, -0.9600703624056880,
          -0.8960553845713439, -0.8379812250083900, -0.7853981633974483 },
      {-2.4087775518032868, -2.3561944901923448, -2.2974386674766221, -2.2318394956455836, -2.1587989303424640, -2.0778948311872334, -1.9890206563741257, -1.8925468811915389, -1.7894652726688385,
          -1.6814535479687922, -1.5707963267948966, -1.4601391056210009, -1.3521273809209546, -1.2490457723982544, -1.1525719972156676, -1.0636978224025597, -0.9827937232473291, -0.9097531579442097,
          -0.8441539861131711, -0.7853981633974483, -0.7328151017865066 },
      {-2.4668517113662407, -2.4149503129080676, -2.3561944901923448, -2.2896263264165211, -2.2142974355881808, -2.1293956421384590, -2.0344439357957027, -1.9295669970654687, -1.8157749899217608,
          -1.6951513213416580, -1.5707963267948966, -1.4464413322481351, -1.3258176636680326, -1.2120256565243244, -1.1071487177940904, -1.0121970114513341, -0.9272952180016122, -0.8519663271732721,
          -0.7853981633974483, -0.7266423406817256, -0.6747409422235526 },
      {-2.5308666892005847, -2.4805494847391065, -2.4227626539681686, -2.3561944901923448, -2.2794225989225669, -2.1910458127777179, -2.0899424410414196, -1.9756881130799799, -1.8490959858000080,
          -1.7126933813990606, -1.5707963267948966, -1.4288992721907328, -1.2924966677897853, -1.1659045405098132, -1.0516502125483738, -0.9505468408120752, -0.8621700546672264, -0.7853981633974483,
          -0.7188299996216245, -0.6610431688506869, -0.6107259643892086 },
      {-2.6011731533192091, -2.5535900500422257, -2.4980915447965089, -2.4329663814621232, -2.3561944901923448, -2.2655346029915999, -2.1587989303424640, -2.0344439357957027, -1.8925468811915389,
          -1.7359450042095235, -1.5707963267948966, -1.4056476493802699, -1.2490457723982544, -1.1071487177940904, -0.9827937232473291, -0.8760580505981934, -0.7853981633974483, -0.7086262721276703,
          -0.6435011087932844, -0.5880026035475675, -0.5404195002705842 },
      {-2.6779450445889870, -2.6344941491974563, -2.5829933382462307, -2.5213431676069717, -2.4468543773930902, -2.3561944901923448, -2.2455372690184494, -2.1112158270654806, -1.9513027039072615,
          -1.7681918866447774, -1.5707963267948966, -1.3734007669450159, -1.1902899496825317, -1.0303768265243125, -0.8960553845713439, -0.7853981633974483, -0.6947382761967031, -0.6202494859828215,
          -0.5585993153435624, -0.5070985043923369, -0.4636476090008061 },
      {-2.7610862764774282, -2.7233683240105639, -2.6779450445889870, -2.6224465393432701, -2.5535900500422257, -2.4668517113662407, -2.3561944901923448, -2.2142974355881808, -2.0344439357957027,
          -1.8157749899217608, -1.5707963267948966, -1.3258176636680326, -1.1071487177940904, -0.9272952180016122, -0.7853981633974483, -0.6747409422235526, -0.5880026035475675, -0.5191461142465229,
          -0.4636476090008061, -0.4182243295792291, -0.3805063771123649 },
      {-2.8501358591119264, -2.8198420991931510, -2.7828219833192209, -2.7367008673047097, -2.6779450445889870, -2.6011731533192091, -2.4980915447965089, -2.3561944901923448, -2.1587989303424640,
          -1.8925468811915389, -1.5707963267948966, -1.2490457723982544, -0.9827937232473291, -0.7853981633974483, -0.6435011087932844, -0.5404195002705842, -0.4636476090008061, -0.4048917862850834,
          -0.3587706702705722, -0.3217505543966422, -0.2914567944778671 },
      {-2.9441970937399127, -2.9229237077158512, -2.8966139904629289, -2.8632929945846817, -2.8198420991931510, -2.7610862764774282, -2.6779450445889870, -2.5535900500422257, -2.3561944901923448,
          -2.0344439357957027, -1.5707963267948966, -1.1071487177940904, -0.7853981633974483, -0.5880026035475675, -0.4636476090008061, -0.3805063771123649, -0.3217505543966422, -0.2782996590051113,
          -0.2449786631268641, -0.2186689458739420, -0.1973955598498807 },
      {-3.0419240010986313, -3.0309354324158977, -3.0172376590430319, -2.9996955989856291, -2.9764439761751662, -2.9441970937399127, -2.8966139904629289, -2.8198420991931510, -2.6779450445889870,
          -2.3561944901923448, -1.5707963267948966, -0.7853981633974483, -0.4636476090008061, -0.3217505543966422, -0.2449786631268641, -0.1973955598498807, -0.1651486774146268, -0.1418970546041639,
          -0.1243549945467614, -0.1106572211738956, -0.0996686524911620 },
      {3.1415926535897931, 3.1415926535897931, 3.1415926535897931, 3.1415926535897931, 3.1415926535897931, 3.1415926535897931, 3.1415926535897931, 3.1415926535897931, 3.1415926535897931,
          3.1415926535897931, 0.0000000000000000, 0.0000000000000000, 0.0000000000000000, 0.0000000000000000, 0.0000000000000000, 0.0000000000000000, 0.0000000000000000, 0.0000000000000000,
          0.0000000000000000, 0.0000000000000000, 0.0000000000000000 },
      {3.0419240010986313, 3.0309354324158977, 3.0172376590430319, 2.9996955989856291, 2.9764439761751662, 2.9441970937399127, 2.8966139904629289, 2.8198420991931510, 2.6779450445889870,
          2.3561944901923448, 1.5707963267948966, 0.7853981633974483, 0.4636476090008061, 0.3217505543966422, 0.2449786631268641, 0.1973955598498807, 0.1651486774146268, 0.1418970546041639,
          0.1243549945467614, 0.1106572211738956, 0.0996686524911620 },
      {2.9441970937399127, 2.9229237077158512, 2.8966139904629289, 2.8632929945846817, 2.8198420991931510, 2.7610862764774282, 2.6779450445889870, 2.5535900500422257, 2.3561944901923448,
          2.0344439357957027, 1.5707963267948966, 1.1071487177940904, 0.7853981633974483, 0.5880026035475675, 0.4636476090008061, 0.3805063771123649, 0.3217505543966422, 0.2782996590051113,
          0.2449786631268641, 0.2186689458739420, 0.1973955598498807 },
      {2.8501358591119264, 2.8198420991931510, 2.7828219833192209, 2.7367008673047097, 2.6779450445889870, 2.6011731533192091, 2.4980915447965089, 2.3561944901923448, 2.1587989303424640,
          1.8925468811915389, 1.5707963267948966, 1.2490457723982544, 0.9827937232473291, 0.7853981633974483, 0.6435011087932844, 0.5404195002705842, 0.4636476090008061, 0.4048917862850834,
          0.3587706702705722, 0.3217505543966422, 0.2914567944778671 },
      {2.7610862764774282, 2.7233683240105639, 2.6779450445889870, 2.6224465393432701, 2.5535900500422257, 2.4668517113662407, 2.3561944901923448, 2.2142974355881808, 2.0344439357957027,
          1.8157749899217608, 1.5707963267948966, 1.3258176636680326, 1.1071487177940904, 0.9272952180016122, 0.7853981633974483, 0.6747409422235526, 0.5880026035475675, 0.5191461142465229,
          0.4636476090008061, 0.4182243295792291, 0.3805063771123649 },
      {2.6779450445889870, 2.6344941491974563, 2.5829933382462307, 2.5213431676069717, 2.4468543773930902, 2.3561944901923448, 2.2455372690184494, 2.1112158270654806, 1.9513027039072615,
          1.7681918866447774, 1.5707963267948966, 1.3734007669450159, 1.1902899496825317, 1.0303768265243125, 0.8960553845713439, 0.7853981633974483, 0.6947382761967031, 0.6202494859828215,
          0.5585993153435624, 0.5070985043923369, 0.4636476090008061 },
      {2.6011731533192091, 2.5535900500422257, 2.4980915447965089, 2.4329663814621232, 2.3561944901923448, 2.2655346029915999, 2.1587989303424640, 2.0344439357957027, 1.8925468811915389,
          1.7359450042095235, 1.5707963267948966, 1.4056476493802699, 1.2490457723982544, 1.1071487177940904, 0.9827937232473291, 0.8760580505981934, 0.7853981633974483, 0.7086262721276703,
          0.6435011087932844, 0.5880026035475675, 0.5404195002705842 },
      {2.5308666892005847, 2.4805494847391065, 2.4227626539681686, 2.3561944901923448, 2.2794225989225669, 2.1910458127777179, 2.0899424410414196, 1.9756881130799799, 1.8490959858000080,
          1.7126933813990606, 1.5707963267948966, 1.4288992721907328, 1.2924966677897853, 1.1659045405098132, 1.0516502125483738, 0.9505468408120752, 0.8621700546672264, 0.7853981633974483,
          0.7188299996216245, 0.6610431688506869, 0.6107259643892086 },
      {2.4668517113662407, 2.4149503129080676, 2.3561944901923448, 2.2896263264165211, 2.2142974355881808, 2.1293956421384590, 2.0344439357957027, 1.9295669970654687, 1.8157749899217608,
          1.6951513213416580, 1.5707963267948966, 1.4464413322481351, 1.3258176636680326, 1.2120256565243244, 1.1071487177940904, 1.0121970114513341, 0.9272952180016122, 0.8519663271732721,
          0.7853981633974483, 0.7266423406817256, 0.6747409422235526 },
      {2.4087775518032868, 2.3561944901923448, 2.2974386674766221, 2.2318394956455836, 2.1587989303424640, 2.0778948311872334, 1.9890206563741257, 1.8925468811915389, 1.7894652726688385,
          1.6814535479687922, 1.5707963267948966, 1.4601391056210009, 1.3521273809209546, 1.2490457723982544, 1.1525719972156676, 1.0636978224025597, 0.9827937232473291, 0.9097531579442097,
          0.8441539861131711, 0.7853981633974483, 0.7328151017865066 },
      {2.3561944901923448, 2.3036114285814033, 2.2455372690184494, 2.1815222911841055, 2.1112158270654806, 2.0344439357957027, 1.9513027039072615, 1.8622531212727638, 1.7681918866447774,
          1.6704649792860586, 1.5707963267948966, 1.4711276743037347, 1.3734007669450159, 1.2793395323170296, 1.1902899496825317, 1.1071487177940904, 1.0303768265243125, 0.9600703624056880,
          0.8960553845713439, 0.8379812250083900, 0.7853981633974483 } };

  @Test
  public void correctnessTest() {
    OGComplexMatrix c = new OGComplexMatrix(realP, imagP);
    ComplexType ans, tabans;
    final int rows = c.getNumberOfRows();
    final int cols = c.getNumberOfColumns();
    double[] ansreal = new double[1];
    double[] ansimag = new double[1];
    int[] ierr = new int[1];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        ansreal[0] = 0;
        ansimag[0] = 0;
        ZLOG.zlog(c.getEntry(i, j).getReal(), c.getEntry(i, j).getImag(), ansreal, ansimag, ierr);
        ans = new ComplexType(ansreal[0], ansimag[0]);
        tabans = new ComplexType(answerReal[i][j], answerImag[i][j]);
        if (Double.isInfinite(tabans.getReal())) {
          assertTrue(Double.isInfinite(ans.getReal()));
          assertTrue(Math.copySign(1, tabans.getReal()) == Math.copySign(1, ans.getReal()));
        } else if (Double.isInfinite(tabans.getImag())) {
          assertTrue(Double.isInfinite(ans.getImag()));
          assertTrue(Math.copySign(1, tabans.getImag()) == Math.copySign(1, ans.getImag()));
        } else {
          assertTrue(Math.abs(ans.getReal() - tabans.getReal()) < 10 * D1MACH.four());
          assertTrue(Math.abs(ans.getImag() - tabans.getImag()) < 10 * D1MACH.four());
        }
      }
    }
  }
}
