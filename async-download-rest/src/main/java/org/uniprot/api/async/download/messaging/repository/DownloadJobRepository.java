package org.uniprot.api.async.download.messaging.repository;

import org.springframework.data.repository.CrudRepository;
import org.uniprot.api.async.download.model.job.DownloadJob;

/**
 * @author uniprotdao

 */
public interface DownloadJobRepository<R extends DownloadJob>
        extends CrudRepository<R, String>, DownloadJobPartialUpdateRepository {}
