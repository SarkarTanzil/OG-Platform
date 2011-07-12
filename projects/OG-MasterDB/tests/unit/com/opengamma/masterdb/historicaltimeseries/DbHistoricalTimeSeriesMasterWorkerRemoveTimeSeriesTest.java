/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.masterdb.historicaltimeseries;

import static org.testng.AssertJUnit.assertEquals;

import java.util.TimeZone;

import javax.time.calendar.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.opengamma.DataNotFoundException;
import com.opengamma.id.ObjectIdentifier;
import com.opengamma.id.UniqueIdentifier;
import com.opengamma.master.historicaltimeseries.ManageableHistoricalTimeSeries;
import com.opengamma.util.test.DBTest;
import com.opengamma.util.timeseries.localdate.LocalDateDoubleTimeSeries;

/**
 * Tests DbHistoricalTimeSeriesMaster.
 */
public class DbHistoricalTimeSeriesMasterWorkerRemoveTimeSeriesTest extends AbstractDbHistoricalTimeSeriesMasterWorkerTest {
  // superclass sets up dummy database

  private static final Logger s_logger = LoggerFactory.getLogger(DbHistoricalTimeSeriesMasterWorkerRemoveTimeSeriesTest.class);

  @Factory(dataProvider = "databases", dataProviderClass = DBTest.class)
  public DbHistoricalTimeSeriesMasterWorkerRemoveTimeSeriesTest(String databaseType, String databaseVersion) {
    super(databaseType, databaseVersion);
    s_logger.info("running testcases for {}", databaseType);
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  //-------------------------------------------------------------------------
  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_remove_nullOID() {
    _htsMaster.removeTimeSeriesDataPoints((ObjectIdentifier) null, LocalDate.of(2011, 7, 1), LocalDate.of(2011, 7, 1));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_remove_nullDate1() {
    _htsMaster.removeTimeSeriesDataPoints(ObjectIdentifier.of("DbHts", "DP101"), null, LocalDate.of(2011, 7, 1));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_remove_nullDate2() {
    _htsMaster.removeTimeSeriesDataPoints(ObjectIdentifier.of("DbHts", "DP101"), LocalDate.of(2011, 7, 1), null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void test_remove_dateOrder() {
    _htsMaster.removeTimeSeriesDataPoints(ObjectIdentifier.of("DbHts", "DP101"), LocalDate.of(2011, 7, 1), LocalDate.of(2011, 3, 1));
  }

  @Test(expectedExceptions = DataNotFoundException.class)
  public void test_remove_versioned_notFoundId() {
    ObjectIdentifier oid = ObjectIdentifier.of("DbHts", "DP0");
    _htsMaster.removeTimeSeriesDataPoints(oid, LocalDate.of(2011, 7, 1), LocalDate.of(2011, 7, 1));
  }

  //-------------------------------------------------------------------------
  @Test
  public void test_remove_101_removeOne() {
    ObjectIdentifier oid = ObjectIdentifier.of("DbHts", "DP101");
    UniqueIdentifier uid = _htsMaster.removeTimeSeriesDataPoints(oid, LocalDate.of(2011, 1, 2), LocalDate.of(2011, 1, 2));
    
    ManageableHistoricalTimeSeries testCorrected = _htsMaster.getTimeSeries(uid, null, null);
    assertEquals(uid, testCorrected.getUniqueId());
    LocalDateDoubleTimeSeries timeSeries = testCorrected.getTimeSeries();
    assertEquals(2, timeSeries.size());
    assertEquals(LocalDate.of(2011, 1, 1), timeSeries.getTime(0));
    assertEquals(3.1d, timeSeries.getValueAt(0), 0.001d);
    assertEquals(LocalDate.of(2011, 1, 3), timeSeries.getTime(1));
    assertEquals(3.33d, timeSeries.getValueAt(1), 0.001d);
  }

  @Test
  public void test_remove_101_removeRange() {
    ObjectIdentifier oid = ObjectIdentifier.of("DbHts", "DP101");
    UniqueIdentifier uid = _htsMaster.removeTimeSeriesDataPoints(oid, LocalDate.of(2010, 7, 3), LocalDate.of(2011, 1, 2));
    
    ManageableHistoricalTimeSeries testCorrected = _htsMaster.getTimeSeries(uid, null, null);
    assertEquals(uid, testCorrected.getUniqueId());
    LocalDateDoubleTimeSeries timeSeries = testCorrected.getTimeSeries();
    assertEquals(1, timeSeries.size());
    assertEquals(LocalDate.of(2011, 1, 3), timeSeries.getTime(0));
    assertEquals(3.33d, timeSeries.getValueAt(0), 0.001d);
  }

  //-------------------------------------------------------------------------
  @Test
  public void test_toString() {
    assertEquals(_htsMaster.getClass().getSimpleName() + "[DbHts]", _htsMaster.toString());
  }

}
