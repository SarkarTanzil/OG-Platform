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
import com.opengamma.master.security.*;

/**
 * Wraps a security master to trap calls to record user based information to allow clean up and
 * hooks for access control logics if needed.
 */
public class FinancialUserSecurityMaster extends AbstractFinancialUserService implements SecurityMaster {

  /**
   * The underlying master.
   */
  private final SecurityMaster _underlying;

  /**
   * Creates an instance.
   *
   * @param client  the client, not null
   * @param underlying  the underlying master, not null
   */
  public FinancialUserSecurityMaster(FinancialClient client, SecurityMaster underlying) {
    super(client, FinancialUserDataType.SECURITY);
    _underlying = underlying;
  }

  //-------------------------------------------------------------------------
  @Override
  public SecurityMetaDataResult metaData(SecurityMetaDataRequest request) {
    return _underlying.metaData(request);
  }

  @Override
  public SecuritySearchResult search(SecuritySearchRequest request) {
    return _underlying.search(request);
  }

  @Override
  public SecurityDocument get(UniqueId uniqueId) {
    return _underlying.get(uniqueId);
  }

  @Override
  public SecurityDocument get(ObjectIdentifiable objectId, VersionCorrection versionCorrection) {
    return _underlying.get(objectId, versionCorrection);
  }

  @Override
  public SecurityDocument add(SecurityDocument document) {
    document = _underlying.add(document);
    if (document.getUniqueId() != null) {
      created(document.getUniqueId());
    }
    return document;
  }

  @Override
  public SecurityDocument update(SecurityDocument document) {
    return _underlying.update(document);
  }

  @Override
  public void remove(UniqueId uniqueId) {
    _underlying.remove(uniqueId);
    deleted(uniqueId);
  }

  @Override
  public SecurityHistoryResult history(SecurityHistoryRequest request) {
    return _underlying.history(request);
  }

  @Override
  public SecurityDocument correct(SecurityDocument document) {
    return _underlying.correct(document);
  }

  //-------------------------------------------------------------------------
  @Override
  public ChangeManager changeManager() {
    return _underlying.changeManager();
  }

  @Override
  public UniqueId addVersion(ObjectIdentifiable objectId, SecurityDocument documentToAdd) {
    documentToAdd = _underlying.add(documentToAdd);
    if (documentToAdd.getUniqueId() != null) {
      created(documentToAdd.getUniqueId());
    }
    return documentToAdd.getUniqueId();
  }

  @Override
  public List<UniqueId> replaceVersion(UniqueId uniqueId, List<SecurityDocument> replacementDocuments) {
    return _underlying.replaceVersion(uniqueId, replacementDocuments);
  }

  @Override
  public List<UniqueId> replaceAllVersions(ObjectIdentifiable objectId, List<SecurityDocument> replacementDocuments) {
    return _underlying.replaceAllVersions(objectId, replacementDocuments);
  }

  @Override
  public List<UniqueId> replaceVersions(ObjectIdentifiable objectId, List<SecurityDocument> replacementDocuments) {
    return _underlying.replaceVersions(objectId, replacementDocuments);
  }

  @Override
  public UniqueId replaceVersion(SecurityDocument replacementDocument) {
    return _underlying.replaceVersion(replacementDocument);
  }

  @Override
  public void removeVersion(UniqueId uniqueId) {
    _underlying.removeVersion(uniqueId);
    deleted(uniqueId);
  }

}
