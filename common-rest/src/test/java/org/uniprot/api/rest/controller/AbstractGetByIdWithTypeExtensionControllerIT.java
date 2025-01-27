package org.uniprot.api.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.uniprot.api.rest.controller.AbstractStreamControllerIT.SAMPLE_RDF;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.uniprot.api.rest.output.UniProtMediaType;

/**
 * @author uniprotdao

 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractGetByIdWithTypeExtensionControllerIT
        extends AbstractGetByIdControllerIT {

    protected abstract RestTemplate getRestTemple();

    protected abstract String getSearchAccession();

    protected abstract String getRdfProlog();

    protected abstract String getIdRequestPathWithoutPathVariable();

    @BeforeAll
    void init() {
        saveEntry();
    }

    @BeforeEach
    void setUp() {
        when(getRestTemple().getUriTemplateHandler()).thenReturn(new DefaultUriBuilderFactory());
        when(getRestTemple().getForObject(any(), any())).thenReturn(SAMPLE_RDF);
    }

    @Test
    void getByIdWithRdfExtensionSuccess() throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder =
                get(getIdRequestPathWithoutPathVariable() + getSearchAccession() + ".rdf")
                        .header(ACCEPT, UniProtMediaType.RDF_MEDIA_TYPE);

        ResultActions response = getMockMvc().perform(requestBuilder);
        // then
        response.andDo(log())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(
                        header().string(
                                        HttpHeaders.CONTENT_TYPE,
                                        UniProtMediaType.RDF_MEDIA_TYPE_VALUE))
                .andExpect(
                        content()
                                .string(
                                        equalTo(
                                                getRdfProlog()
                                                        + "\n"
                                                        + "    <sample>text</sample>\n"
                                                        + "    <anotherSample>text2</anotherSample>\n"
                                                        + "    <someMore>text3</someMore>\n"
                                                        + "</rdf:RDF>\n")));
    }

    @ParameterizedTest(name = "[{index}] for {0}")
    @MethodSource("getContentTypes")
    void testGetByIdWithTypeExtension(MediaType mediaType) throws Exception {
        // when
        MockHttpServletRequestBuilder requestBuilder =
                get(
                        getIdRequestPathWithoutPathVariable()
                                + getSearchAccession()
                                + "."
                                + UniProtMediaType.getFileExtension(mediaType));

        ResultActions response = getMockMvc().perform(requestBuilder);

        // then
        response.andDo(log())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, mediaType.toString()))
                .andExpect(content().contentTypeCompatibleWith(mediaType));
    }

    protected Stream<Arguments> getContentTypes() {
        return ControllerITUtils.getContentTypes(
                        getIdRequestPath(), getRequestMappingHandlerMapping())
                .stream()
                .map(Arguments::of);
    }
}
