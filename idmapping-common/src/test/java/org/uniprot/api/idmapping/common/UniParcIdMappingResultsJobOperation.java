package org.uniprot.api.idmapping.common;

import static org.uniprot.api.idmapping.common.IdMappingUniParcITUtils.*;
import static org.uniprot.api.idmapping.common.IdMappingUniProtKBITUtils.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.uniprot.api.idmapping.common.model.IdMappingJob;
import org.uniprot.api.idmapping.common.service.IdMappingJobCacheService;
import org.uniprot.api.rest.download.model.JobStatus;

/**
 * @author uniprotdao

 */
public class UniParcIdMappingResultsJobOperation extends AbstractJobOperation {

    public UniParcIdMappingResultsJobOperation(IdMappingJobCacheService cacheService) {
        super(cacheService);
    }

    @Override
    public IdMappingJob createAndPutJobInCache(int idsCount) throws Exception {
        return createAndPutJobInCache(idsCount, JobStatus.FINISHED);
    }

    @Override
    public IdMappingJob createAndPutJobInCache(int idsCount, JobStatus jobStatus) throws Exception {
        Map<String, String> ids = new LinkedHashMap<>();
        for (int i = 1; i <= idsCount; i++) {
            String fromId = String.format("Q%05d", i);
            String toId = String.format(UPI_PREF + "%02d", i);
            ids.put(fromId, toId);
        }
        return createAndPutJobInCache(UNIPROTKB_AC_ID_STR, "UniParc", ids, jobStatus);
    }

    @Override
    public IdMappingJob createAndPutJobInCacheWithOneToManyMapping(
            int idsCount, JobStatus jobStatus) throws Exception {
        Map<String, String> ids = new LinkedHashMap<>();
        for (int i = 1; i <= idsCount; i++) {
            String fromId = String.format("Q%05d", i);
            // one id to 6 ids
            String toId =
                    (";" + String.format(UPI_PREF + "%02d", i)).repeat(6).replaceFirst(";", "");
            ;
            ids.put(fromId, toId);
        }
        return createAndPutJobInCache(UNIPROTKB_AC_ID_STR, "UniParc", ids, jobStatus);
    }
}
