package org.uniprot.api.idmapping.common;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.uniprot.api.idmapping.common.model.IdMappingJob;
import org.uniprot.api.idmapping.common.service.IdMappingJobCacheService;
import org.uniprot.api.rest.download.model.JobStatus;

/**
 * @author uniprotdao

 */
public interface JobOperation {
    IdMappingJob createAndPutJobInCache() throws Exception;

    IdMappingJob createAndPutJobInCacheForAllFields() throws Exception;

    IdMappingJob createAndPutJobInCache(int idsCount) throws Exception;

    IdMappingJob createAndPutJobInCache(JobStatus jobStatus) throws Exception;

    IdMappingJob createAndPutJobInCache(int idsCount, JobStatus jobStatus) throws Exception;

    IdMappingJob createAndPutJobInCache(String from, String to, String fromIds)
            throws InvalidKeySpecException, NoSuchAlgorithmException;

    IdMappingJob createAndPutJobInCacheWithOneToManyMapping(int idsCount, JobStatus jobStatus)
            throws Exception;

    IdMappingJobCacheService getIdMappingJobCacheService();
}
