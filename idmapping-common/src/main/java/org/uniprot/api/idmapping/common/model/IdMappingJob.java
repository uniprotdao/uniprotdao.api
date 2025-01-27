package org.uniprot.api.idmapping.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.uniprot.api.common.repository.search.ProblemPair;
import org.uniprot.api.idmapping.common.request.IdMappingJobRequest;
import org.uniprot.api.rest.download.model.JobStatus;

import lombok.Builder;
import lombok.Data;

/**
 * @author uniprotdao

 */
@Data
@Builder(toBuilder = true)
public class IdMappingJob implements Serializable {
    private static final long serialVersionUID = -9179860117114350789L;
    private String jobId;
    private JobStatus jobStatus;
    private IdMappingJobRequest idMappingRequest;
    private IdMappingResult idMappingResult;
    private List<ProblemPair> errors;
    @Builder.Default private Date created = new Date();
    @Builder.Default private Date updated = new Date();
}
