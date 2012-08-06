/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.user;

import java.util.List;

import com.opengamma.core.change.ChangeManager;
import com.opengamma.id.ObjectIdentifiable;
import com.opengamma.id.UniqueId;
import com.opengamma.id.VersionCorrection;
import com.opengamma.master.marketdatasnapshot.*;

/**
 * Wraps a snapshot master to trap calls to record user based information to allow clean up and
 * hooks for access control logics if needed.
 */
public class FinancialUserSnapshotMaster extends AbstractFinancialUserService implements MarketDataSnapshotMaster {

  /**
   * The underlying master.
   */
  private final MarketDataSnapshotMaster _underlying;

  /**
   * Creates an instance.
   *
   * @param client  the client, not null
   * @param underlying  the underlying master, not null
   */
  public FinancialUserSnapshotMaster(FinancialClient client, MarketDataSnapshotMaster underlying) {
    super(client, FinancialUserDataType.MARKET_DATA_SNAPSHOT);
    _underlying = underlying;
  }

  //-------------------------------------------------------------------------
  @Override
  public MarketDataSnapshotDocument get(UniqueId uniqueId) {
    return _underlying.get(uniqueId);
  }

  @Override
  public MarketDataSnapshotDocument get(ObjectIdentifiable objectId, VersionCorrection versionCorrection) {
    return _underlying.get(objectId, versionCorrection);
  }

  @Override
  public MarketDataSnapshotDocument add(MarketDataSnapshotDocument document) {
    document = _underlying.add(document);
    if (document.getUniqueId() != null) {
      created(document.getUniqueId());
    }
    return document;
  }

  @Override
  public MarketDataSnapshotDocument update(MarketDataSnapshotDocument document) {
    return _underlying.update(document);
  }

  @Override
  public void remove(UniqueId uniqueId) {
    _underlying.remove(uniqueId);
    deleted(uniqueId);
  }

  @Override
  public MarketDataSnapshotDocument correct(MarketDataSnapshotDocument document) {
    return _underlying.correct(document);
  }

  @Override
  public MarketDataSnapshotSearchResult search(MarketDataSnapshotSearchRequest request) {
    return _underlying.search(request);
  }

  @Override
  public MarketDataSnapshotHistoryResult history(MarketDataSnapshotHistoryRequest request) {
    return _underlying.history(request);
  }

  @Override
  public ChangeManager changeManager() {
    return _underlying.changeManager();
  }

  @Override
  public UniqueId addVersion(ObjectIdentifiable objectId, MarketDataSnapshotDocument documentToAdd) {
    documentToAdd = _underlying.add(documentToAdd);
    if (documentToAdd.getUniqueId() != null) {
      created(documentToAdd.getUniqueId());
    }
    return documentToAdd.getUniqueId();
  }

  @Override
  public List<UniqueId> replaceVersion(UniqueId uniqueId, List<MarketDataSnapshotDocument> replacementDocuments) {
    return _underlying.replaceVersion(uniqueId, replacementDocuments);
  }

  @Override
  public List<UniqueId> replaceAllVersions(ObjectIdentifiable objectId, List<MarketDataSnapshotDocument> replacementDocuments) {
    return _underlying.replaceAllVersions(objectId, replacementDocuments);
  }

  @Override
  public List<UniqueId> replaceVersions(ObjectIdentifiable objectId, List<MarketDataSnapshotDocument> replacementDocuments) {
    return _underlying.replaceAllVersions(objectId, replacementDocuments);
  }

  @Override
  public UniqueId replaceVersion(MarketDataSnapshotDocument replacementDocument) {
    return _underlying.replaceVersion(replacementDocument);
  }

  @Override
  public void removeVersion(UniqueId uniqueId) {
    _underlying.removeVersion(uniqueId);
    deleted(uniqueId);
  }

}
