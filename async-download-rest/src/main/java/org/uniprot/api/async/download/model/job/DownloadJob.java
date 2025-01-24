package org.uniprot.api.async.download.model.job;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.uniprot.api.rest.download.model.JobStatus;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author uniprotdao

 *     redis-cli 2. to get the data for a hash run hgetall
 *     <prefix>:1e8e33be0c54af8ba15db116e2e6c63b26acd7cd
 */
@Data
@AllArgsConstructor
public class DownloadJob implements Serializable {
    private static final long serialVersionUID = 4548782902533470468L;
    @Id private String id;
    private JobStatus status;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime created;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updated;

    private String error;
    private int retried;
    private String query;
    private String fields;
    private String sort;
    private String resultFile;

    private String format;
    private long totalEntries;
    private long processedEntries;
    private long updateCount;
}
