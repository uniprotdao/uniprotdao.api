package org.uniprot.api.common.repository.solrstream;

import java.util.Objects;

import org.apache.solr.client.solrj.io.SolrClientCache;
import org.apache.solr.client.solrj.io.stream.StreamContext;
import org.apache.solr.client.solrj.io.stream.TupleStream;
import org.apache.solr.client.solrj.io.stream.expr.DefaultStreamFactory;
import org.apache.solr.client.solrj.io.stream.expr.StreamFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is responsible for providing {@link StreamFactory} and {@link StreamContext} for the
 * creation of {@link TupleStream} instance.
 *
 * <p>Created 25/07/2020
 *
 * @author uniprotdao
 */
@Slf4j
public abstract class AbstractTupleStreamTemplate {
    private StreamFactory streamFactory;
    private StreamContext streamContext;

    protected StreamFactory getStreamFactory(String zkHost, String collection) {
        if (Objects.isNull(this.streamFactory)) {
            initTupleStreamFactory(zkHost, collection);
        } else {
            log.debug("DefaultStreamFactory already created for collection {}", collection);
        }
        return this.streamFactory;
    }

    protected StreamContext getStreamContext(String collection) {
        if (Objects.isNull(this.streamContext)) {
            initStreamContext(collection);
        } else {
            log.debug("StreamContext already created for collection {}", collection);
        }
        return this.streamContext;
    }

    private void initTupleStreamFactory(String zookeeperHost, String collection) {
        this.streamFactory =
                new DefaultStreamFactory().withCollectionZkHost(collection, zookeeperHost);
        log.info("Created new DefaultStreamFactory for {} and {}", zookeeperHost, collection);
    }

    /**
     * For tweaking, see: https://www.mail-archive.com/solr-user@lucene.apache.org/msg131338.html
     */
    private void initStreamContext(String collection) {
        StreamContext context = new StreamContext();
        // this should be the same for each collection, so that
        // they share client caches
        context.workerID = collection.hashCode();
        context.numWorkers = 1;
        SolrClientCache solrClientCache = new SolrClientCache();
        context.setSolrClientCache(solrClientCache);
        this.streamContext = context;
        log.info("Created new StreamContext for {} ", collection);
    }
}
