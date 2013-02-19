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
 * Tests complex inverse sin()
 */
public class ZASINTest {
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
      {-0.7841481699077881, -0.7314421231441870, -0.6732552344529046, -0.6091521827733313, -0.5388017392170560, -0.4620533595396762, -0.3790269849408001, -0.2902014875832347, -0.1964771156296125,
          -0.0991820438784927, 0.0000000000000000, 0.0991820438784927, 0.1964771156296125, 0.2902014875832347, 0.3790269849408001, 0.4620533595396762, 0.5388017392170560, 0.6091521827733313,
          0.6732552344529046, 0.7314421231441870, 0.7841481699077881 },
      {-0.8366070514604023, -0.7838549657711821, -0.7249311476979179, -0.6591819277990507, -0.5860350919197073, -0.5051034931522458, -0.4163211457920876, -0.3200949322490158, -0.2174331465655995,
          -0.1099938908884456, 0.0000000000000000, 0.1099938908884456, 0.2174331465655995, 0.3200949322490158, 0.4163211457920876, 0.5051034931522458, 0.5860350919197073, 0.6591819277990507,
          0.7249311476979179, 0.7838549657711821, 0.8366070514604023 },
      {-0.8945666909525283, -0.8424407165141807, -0.7834450632320008, -0.7166391540099444, -0.6411061828030549, -0.5560837203606075, -0.4611616352523473, -0.3565361597887050, -0.2432652330721690,
          -0.1234187096986845, 0.0000000000000000, 0.1234187096986845, 0.2432652330721690, 0.3565361597887050, 0.4611616352523473, 0.5560837203606075, 0.6411061828030549, 0.7166391540099444,
          0.7834450632320008, 0.8424407165141807, 0.8945666909525283 },
      {-0.9584911486181655, -0.9078866231610124, -0.8497716178664650, -0.7828471983241914, -0.7057236956882884, -0.6170642966759935, -0.5158519091936864, -0.4017981628514604, -0.2758372653981257,
          -0.1405169060721779, 0.0000000000000000, 0.1405169060721779, 0.2758372653981257, 0.4017981628514604, 0.5158519091936864, 0.6170642966759935, 0.7057236956882884, 0.7828471983241914,
          0.8497716178664650, 0.9078866231610124, 0.9584911486181655 },
      {-1.0287506471142285, -0.9808164862121164, -0.9248902126310973, -0.8592596342487793, -0.7819260807033326, -0.6907162341702312, -0.5835894870384394, -0.4592474034624190, -0.3180560498709162,
          -0.1629984728353687, 0.0000000000000000, 0.1629984728353687, 0.3180560498709162, 0.4592474034624190, 0.5835894870384394, 0.6907162341702312, 0.7819260807033326, 0.8592596342487793,
          0.9248902126310973, 0.9808164862121164, 1.0287506471142285 },
      {-1.1055429484834951, -1.0616878410214521, -1.0096627698016030, -0.9473406443130488, -0.8720181364422391, -0.7803985799853842, -0.6688165753259273, -0.5339990695941687, -0.3746708048255270,
          -0.1937931365549322, 0.0000000000000000, 0.1937931365549322, 0.3746708048255270, 0.5339990695941687, 0.6688165753259273, 0.7803985799853842, 0.8720181364422391, 0.9473406443130488,
          1.0096627698016030, 1.0616878410214521, 1.1055429484834951 },
      {-1.1887966397385479, -1.1506489896356062, -1.1046346199429429, -1.0483171885716398, -0.9783313793645634, -0.8900832616511113, -0.7775872521216609, -0.6339838656391766, -0.4538702099631224,
          -0.2383174618098661, 0.0000000000000000, 0.2383174618098661, 0.4538702099631224, 0.6339838656391766, 0.7775872521216609, 0.8900832616511113, 0.9783313793645634, 1.0483171885716398,
          1.1046346199429429, 1.1506489896356062, 1.1887966397385479 },
      {-1.2780697200654747, -1.2473679274683243, -1.2097562840831855, -1.1627552467227851, -1.1026596323318454, -1.0238217465117829, -0.9176168533514787, -0.7715181921218484, -0.5706527843210993,
          -0.3076036495307112, 0.0000000000000000, 0.3076036495307112, 0.5706527843210993, 0.7715181921218484, 0.9176168533514787, 1.0238217465117829, 1.1026596323318454, 1.1627552467227851,
          1.2097562840831855, 1.2473679274683243, 1.2780697200654747 },
      {-1.3724700129600576, -1.3508716667691572, -1.3240705564892392, -1.2899743850347756, -1.2452387535231115, -1.1842316842750220, -1.0969215488301429, -0.9646585044076028, -0.7542491446980459,
          -0.4270785863924761, 0.0000000000000000, 0.4270785863924761, 0.7542491446980459, 0.9646585044076028, 1.0969215488301429, 1.1842316842750220, 1.2452387535231115, 1.2899743850347756,
          1.3240705564892392, 1.3508716667691572, 1.3724700129600576 },
      {-1.4706339299419438, -1.4594638304797904, -1.4454838684587072, -1.4274787937403288, -1.4034133718392579, -1.3696012470939865, -1.3186169180785432, -1.2330952175293441, -1.0634400235777519,
          -0.6662394324925153, 0.0000000000000000, 0.6662394324925153, 1.0634400235777519, 1.2330952175293441, 1.3186169180785432, 1.3696012470939865, 1.4034133718392579, 1.4274787937403288,
          1.4454838684587072, 1.4594638304797904, 1.4706339299419438 },
      {-1.5707963267948966, -1.5707963267948966, -1.5707963267948966, -1.5707963267948966, -1.5707963267948966, -1.5707963267948966, -1.5707963267948966, -1.5707963267948966, -1.5707963267948966,
          -1.5707963267948966, 0.0000000000000000, 1.5707963267948966, 1.5707963267948966, 1.5707963267948966, 1.5707963267948966, 1.5707963267948966, 1.5707963267948966, 1.5707963267948966,
          1.5707963267948966, 1.5707963267948966, 1.5707963267948966 },
      {-1.4706339299419438, -1.4594638304797904, -1.4454838684587072, -1.4274787937403288, -1.4034133718392579, -1.3696012470939865, -1.3186169180785432, -1.2330952175293441, -1.0634400235777519,
          -0.6662394324925153, 0.0000000000000000, 0.6662394324925153, 1.0634400235777519, 1.2330952175293441, 1.3186169180785432, 1.3696012470939865, 1.4034133718392579, 1.4274787937403288,
          1.4454838684587072, 1.4594638304797904, 1.4706339299419438 },
      {-1.3724700129600576, -1.3508716667691572, -1.3240705564892392, -1.2899743850347756, -1.2452387535231115, -1.1842316842750220, -1.0969215488301429, -0.9646585044076028, -0.7542491446980459,
          -0.4270785863924761, 0.0000000000000000, 0.4270785863924761, 0.7542491446980459, 0.9646585044076028, 1.0969215488301429, 1.1842316842750220, 1.2452387535231115, 1.2899743850347756,
          1.3240705564892392, 1.3508716667691572, 1.3724700129600576 },
      {-1.2780697200654747, -1.2473679274683243, -1.2097562840831855, -1.1627552467227851, -1.1026596323318454, -1.0238217465117829, -0.9176168533514787, -0.7715181921218484, -0.5706527843210993,
          -0.3076036495307112, 0.0000000000000000, 0.3076036495307112, 0.5706527843210993, 0.7715181921218484, 0.9176168533514787, 1.0238217465117829, 1.1026596323318454, 1.1627552467227851,
          1.2097562840831855, 1.2473679274683243, 1.2780697200654747 },
      {-1.1887966397385479, -1.1506489896356062, -1.1046346199429429, -1.0483171885716398, -0.9783313793645634, -0.8900832616511113, -0.7775872521216609, -0.6339838656391766, -0.4538702099631224,
          -0.2383174618098661, 0.0000000000000000, 0.2383174618098661, 0.4538702099631224, 0.6339838656391766, 0.7775872521216609, 0.8900832616511113, 0.9783313793645634, 1.0483171885716398,
          1.1046346199429429, 1.1506489896356062, 1.1887966397385479 },
      {-1.1055429484834951, -1.0616878410214521, -1.0096627698016030, -0.9473406443130488, -0.8720181364422391, -0.7803985799853842, -0.6688165753259273, -0.5339990695941687, -0.3746708048255270,
          -0.1937931365549322, 0.0000000000000000, 0.1937931365549322, 0.3746708048255270, 0.5339990695941687, 0.6688165753259273, 0.7803985799853842, 0.8720181364422391, 0.9473406443130488,
          1.0096627698016030, 1.0616878410214521, 1.1055429484834951 },
      {-1.0287506471142285, -0.9808164862121164, -0.9248902126310973, -0.8592596342487793, -0.7819260807033326, -0.6907162341702312, -0.5835894870384394, -0.4592474034624190, -0.3180560498709162,
          -0.1629984728353687, 0.0000000000000000, 0.1629984728353687, 0.3180560498709162, 0.4592474034624190, 0.5835894870384394, 0.6907162341702312, 0.7819260807033326, 0.8592596342487793,
          0.9248902126310973, 0.9808164862121164, 1.0287506471142285 },
      {-0.9584911486181655, -0.9078866231610124, -0.8497716178664650, -0.7828471983241914, -0.7057236956882884, -0.6170642966759935, -0.5158519091936864, -0.4017981628514604, -0.2758372653981257,
          -0.1405169060721779, 0.0000000000000000, 0.1405169060721779, 0.2758372653981257, 0.4017981628514604, 0.5158519091936864, 0.6170642966759935, 0.7057236956882884, 0.7828471983241914,
          0.8497716178664650, 0.9078866231610124, 0.9584911486181655 },
      {-0.8945666909525283, -0.8424407165141807, -0.7834450632320008, -0.7166391540099444, -0.6411061828030549, -0.5560837203606075, -0.4611616352523473, -0.3565361597887050, -0.2432652330721690,
          -0.1234187096986845, 0.0000000000000000, 0.1234187096986845, 0.2432652330721690, 0.3565361597887050, 0.4611616352523473, 0.5560837203606075, 0.6411061828030549, 0.7166391540099444,
          0.7834450632320008, 0.8424407165141807, 0.8945666909525283 },
      {-0.8366070514604023, -0.7838549657711821, -0.7249311476979179, -0.6591819277990507, -0.5860350919197073, -0.5051034931522458, -0.4163211457920876, -0.3200949322490158, -0.2174331465655995,
          -0.1099938908884456, 0.0000000000000000, 0.1099938908884456, 0.2174331465655995, 0.3200949322490158, 0.4163211457920876, 0.5051034931522458, 0.5860350919197073, 0.6591819277990507,
          0.7249311476979179, 0.7838549657711821, 0.8366070514604023 },
      {-0.7841481699077881, -0.7314421231441870, -0.6732552344529046, -0.6091521827733313, -0.5388017392170560, -0.4620533595396762, -0.3790269849408001, -0.2902014875832347, -0.1964771156296125,
          -0.0991820438784927, 0.0000000000000000, 0.0991820438784927, 0.1964771156296125, 0.2902014875832347, 0.3790269849408001, 0.4620533595396762, 0.5388017392170560, 0.6091521827733313,
          0.6732552344529046, 0.7314421231441870, 0.7841481699077881 } };

  private static double[][] answerImag = {
      {-3.3423082075626018, -3.2925434814760544, -3.2434181591469957, -3.1956978516606149, -3.1503424784884602, -3.1085057043690791, -3.0715025569270047, -3.0407328278260111, -3.0175554791917567,
          -3.0031252437131291, -2.9982229502979698, -3.0031252437131291, -3.0175554791917567, -3.0407328278260111, -3.0715025569270047, -3.1085057043690791, -3.1503424784884602, -3.1956978516606149,
          -3.2434181591469957, -3.2925434814760544, -3.3423082075626018 },
      {-3.2922535079883675, -3.2369489203715971, -3.1817205225071623, -3.1273926375040930, -3.0750607679468684, -3.0261163654875274, -2.9822307095329315, -2.9452709724930921, -2.9171288698209166,
          -2.8994686991707197, -2.8934439858858716, -2.8994686991707197, -2.9171288698209166, -2.9452709724930921, -2.9822307095329315, -3.0261163654875274, -3.0750607679468684, -3.1273926375040930,
          -3.1817205225071623, -3.2369489203715971, -3.2922535079883675 },
      {-3.2427489292018197, -3.1813162536860236, -3.1191680344383270, -3.0571418389619964, -2.9964401392355255, -2.9387034887363708, -2.8860395049475405, -2.8409546699274197, -2.8061337001907227,
          -2.7840492640208856, -2.7764722807237177, -2.7840492640208856, -2.8061337001907227, -2.8409546699274197, -2.8860395049475405, -2.9387034887363708, -2.9964401392355255, -3.0571418389619964,
          -3.1191680344383270, -3.1813162536860236, -3.2427489292018197 },
      {-3.1945492820340240, -3.1264459241248415, -3.0565545070216835, -2.9856406810823723, -2.9149349663103048, -2.8462888282083862, -2.7823040403441328, -2.7263424970102745, -2.6822833445007306,
          -2.6539273355384174, -2.6441207610586290, -2.6539273355384174, -2.6822833445007306, -2.7263424970102745, -2.7823040403441328, -2.8462888282083862, -2.9149349663103048, -2.9856406810823723,
          -3.0565545070216835, -3.1264459241248415, -3.1945492820340240 },
      {-3.1486124158814870, -3.0734171726978485, -2.9950402175839939, -2.9140353877209031, -2.8314983232869921, -2.7493465969740996, -2.6706422224804807, -2.5998241937784723, -2.5425702259303136,
          -2.5049441482332582, -2.4917798526449122, -2.5049441482332582, -2.5425702259303136, -2.5998241937784723, -2.6706422224804807, -2.7493465969740996, -2.8314983232869921, -2.9140353877209031,
          -2.9950402175839939, -3.0734171726978485, -3.1486124158814870 },
      {-3.1061057542892012, -3.0236244624862745, -2.9362418228665041, -2.8440976626506527, -2.7478687371476949, -2.6491961778064712, -2.5513216254756985, -2.4598315216234345, -2.3830308809003258,
          -2.3309746530493123, -2.3124383412727525, -2.3309746530493123, -2.3830308809003258, -2.4598315216234345, -2.5513216254756985, -2.6491961778064712, -2.7478687371476949, -2.8440976626506527,
          -2.9362418228665041, -3.0236244624862745, -3.1061057542892012 },
      {-3.0683813163569509, -2.9787766619541034, -2.8822896953783657, -2.7783990940505472, -2.6669446837131048, -2.5486455784678337, -2.4261066521153900, -2.3055090312434769, -2.1985730279209359,
          -2.1225501238100715, -2.0947125472611012, -2.1225501238100715, -2.1985730279209359, -2.3055090312434769, -2.4261066521153900, -2.5486455784678337, -2.6669446837131048, -2.7783990940505472,
          -2.8822896953783657, -2.9787766619541034, -3.0683813163569509 },
      {-3.0369031980974488, -2.9408265783541698, -2.8357943742419178, -2.7203975979400625, -2.5931585970908038, -2.4529137425028118, -2.2999140408792695, -2.1386220863162211, -1.9833870299165355,
          -1.8641615441578825, -1.8184464592320668, -1.8641615441578825, -1.9833870299165355, -2.1386220863162211, -2.2999140408792695, -2.4529137425028118, -2.5931585970908038, -2.7203975979400625,
          -2.8357943742419178, -2.9408265783541698, -3.0369031980974488 },
      {-3.0131175744705696, -2.9118001066982249, -2.7996457775654280, -2.6742734458074566, -2.5325707993262854, -2.3705485373179198, -2.1835852165645639, -1.9686379257930964, -1.7343245214879666,
          -1.5285709194809982, -1.4436354751788103, -1.5285709194809982, -1.7343245214879666, -1.9686379257930964, -2.1835852165645639, -2.3705485373179198, -2.5325707993262854, -2.6742734458074566,
          -2.7996457775654280, -2.9118001066982249, -3.0131175744705696 },
      {-2.9982726947086702, -2.8935197103979768, -2.7765933641736349, -2.6443267863946120, -2.4921599681338353, -2.3132209417695293, -2.0965964572888911, -1.8241987021938828, -1.4693517443681852,
          -1.0612750619050357, -0.8813735870195430, -1.0612750619050357, -1.4693517443681852, -1.8241987021938828, -2.0965964572888911, -2.3132209417695293, -2.4921599681338353, -2.6443267863946120,
          -2.7765933641736349, -2.8935197103979768, -2.9982726947086702 },
      {2.9932228461263808, 2.8872709503576206, 2.7686593833135738, 2.6339157938496336, 2.4778887302884751, 2.2924316695611777, 2.0634370688955608, 1.7627471740390861, 1.3169578969248166,
          0.0000000000000000, 0.0000000000000000, 0.0000000000000000, -1.3169578969248166, -1.7627471740390861, -2.0634370688955608, -2.2924316695611777, -2.4778887302884751, -2.6339157938496336,
          -2.7686593833135738, -2.8872709503576206, -2.9932228461263808 },
      {2.9982726947086702, 2.8935197103979768, 2.7765933641736349, 2.6443267863946120, 2.4921599681338353, 2.3132209417695293, 2.0965964572888911, 1.8241987021938828, 1.4693517443681852,
          1.0612750619050357, 0.8813735870195430, 1.0612750619050357, 1.4693517443681852, 1.8241987021938828, 2.0965964572888911, 2.3132209417695293, 2.4921599681338353, 2.6443267863946120,
          2.7765933641736349, 2.8935197103979768, 2.9982726947086702 },
      {3.0131175744705696, 2.9118001066982249, 2.7996457775654280, 2.6742734458074566, 2.5325707993262854, 2.3705485373179198, 2.1835852165645639, 1.9686379257930964, 1.7343245214879666,
          1.5285709194809982, 1.4436354751788103, 1.5285709194809982, 1.7343245214879666, 1.9686379257930964, 2.1835852165645639, 2.3705485373179198, 2.5325707993262854, 2.6742734458074566,
          2.7996457775654280, 2.9118001066982249, 3.0131175744705696 },
      {3.0369031980974488, 2.9408265783541698, 2.8357943742419178, 2.7203975979400625, 2.5931585970908038, 2.4529137425028118, 2.2999140408792695, 2.1386220863162211, 1.9833870299165355,
          1.8641615441578825, 1.8184464592320668, 1.8641615441578825, 1.9833870299165355, 2.1386220863162211, 2.2999140408792695, 2.4529137425028118, 2.5931585970908038, 2.7203975979400625,
          2.8357943742419178, 2.9408265783541698, 3.0369031980974488 },
      {3.0683813163569509, 2.9787766619541034, 2.8822896953783657, 2.7783990940505472, 2.6669446837131048, 2.5486455784678337, 2.4261066521153900, 2.3055090312434769, 2.1985730279209359,
          2.1225501238100715, 2.0947125472611012, 2.1225501238100715, 2.1985730279209359, 2.3055090312434769, 2.4261066521153900, 2.5486455784678337, 2.6669446837131048, 2.7783990940505472,
          2.8822896953783657, 2.9787766619541034, 3.0683813163569509 },
      {3.1061057542892012, 3.0236244624862745, 2.9362418228665041, 2.8440976626506527, 2.7478687371476949, 2.6491961778064712, 2.5513216254756985, 2.4598315216234345, 2.3830308809003258,
          2.3309746530493123, 2.3124383412727525, 2.3309746530493123, 2.3830308809003258, 2.4598315216234345, 2.5513216254756985, 2.6491961778064712, 2.7478687371476949, 2.8440976626506527,
          2.9362418228665041, 3.0236244624862745, 3.1061057542892012 },
      {3.1486124158814870, 3.0734171726978485, 2.9950402175839939, 2.9140353877209031, 2.8314983232869921, 2.7493465969740996, 2.6706422224804807, 2.5998241937784723, 2.5425702259303136,
          2.5049441482332582, 2.4917798526449122, 2.5049441482332582, 2.5425702259303136, 2.5998241937784723, 2.6706422224804807, 2.7493465969740996, 2.8314983232869921, 2.9140353877209031,
          2.9950402175839939, 3.0734171726978485, 3.1486124158814870 },
      {3.1945492820340240, 3.1264459241248415, 3.0565545070216835, 2.9856406810823723, 2.9149349663103048, 2.8462888282083862, 2.7823040403441328, 2.7263424970102745, 2.6822833445007306,
          2.6539273355384174, 2.6441207610586290, 2.6539273355384174, 2.6822833445007306, 2.7263424970102745, 2.7823040403441328, 2.8462888282083862, 2.9149349663103048, 2.9856406810823723,
          3.0565545070216835, 3.1264459241248415, 3.1945492820340240 },
      {3.2427489292018197, 3.1813162536860236, 3.1191680344383270, 3.0571418389619964, 2.9964401392355255, 2.9387034887363708, 2.8860395049475405, 2.8409546699274197, 2.8061337001907227,
          2.7840492640208856, 2.7764722807237177, 2.7840492640208856, 2.8061337001907227, 2.8409546699274197, 2.8860395049475405, 2.9387034887363708, 2.9964401392355255, 3.0571418389619964,
          3.1191680344383270, 3.1813162536860236, 3.2427489292018197 },
      {3.2922535079883675, 3.2369489203715971, 3.1817205225071623, 3.1273926375040930, 3.0750607679468684, 3.0261163654875274, 2.9822307095329315, 2.9452709724930921, 2.9171288698209166,
          2.8994686991707197, 2.8934439858858716, 2.8994686991707197, 2.9171288698209166, 2.9452709724930921, 2.9822307095329315, 3.0261163654875274, 3.0750607679468684, 3.1273926375040930,
          3.1817205225071623, 3.2369489203715971, 3.2922535079883675 },
      {3.3423082075626018, 3.2925434814760544, 3.2434181591469957, 3.1956978516606149, 3.1503424784884602, 3.1085057043690791, 3.0715025569270047, 3.0407328278260111, 3.0175554791917567,
          3.0031252437131291, 2.9982229502979698, 3.0031252437131291, 3.0175554791917567, 3.0407328278260111, 3.0715025569270047, 3.1085057043690791, 3.1503424784884602, 3.1956978516606149,
          3.2434181591469957, 3.2925434814760544, 3.3423082075626018 } };

  @Test
  public void correctnessTest() {
    OGComplexMatrix c = new OGComplexMatrix(realP, imagP);
    ComplexType ans, tabans;
    final int rows = c.getNumberOfRows();
    final int cols = c.getNumberOfColumns();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        ans = new ComplexType(ZASIN.zasin(new double[] {c.getEntry(i, j).getReal(), c.getEntry(i, j).getImag() }));
        tabans = new ComplexType(answerReal[i][j], answerImag[i][j]);
        assertTrue(Math.abs(ans.getReal() - tabans.getReal()) < 1000 * D1MACH.four());
        assertTrue(Math.abs(ans.getImag() - tabans.getImag()) < 1000 * D1MACH.four());
      }
    }
  }

  @Test
  public void smallvaluebranchTest() {
    double[] expectedAns;
    double[] ans;
    double[] input;
    input = new double[] {0.05, 0.05 };
    expectedAns = new double[] {0.049958239863297, 0.050041572638602 };
    ans = ZASIN.zasin(input);
    assertTrue(Math.abs(ans[0] - expectedAns[0]) < 1000 * D1MACH.four());
    assertTrue(Math.abs(ans[1] - expectedAns[1]) < 1000 * D1MACH.four());

    expectedAns = new double[] {0.090121944559072, 0.000100407476928 };
    input = new double[] {0.09, 0.0001 };
    ans = ZASIN.zasin(input);
    assertTrue(Math.abs(ans[0] - expectedAns[0]) < 1000 * D1MACH.four());
    assertTrue(Math.abs(ans[1] - expectedAns[1]) < 1000 * D1MACH.four());
  }
}
