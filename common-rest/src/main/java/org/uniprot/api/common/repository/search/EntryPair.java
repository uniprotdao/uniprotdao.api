package org.uniprot.api.common.repository.search;

import java.io.Serializable;

/**
 * @author uniprotdao

 */
public interface EntryPair<T> extends Serializable {
    String getFrom();

    T getTo();
}
