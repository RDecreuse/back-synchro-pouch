package com.synchro.pouch.service.couch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CouchBaseServiceImpl implements CouchBaseService {

    private static final String URL_SEPARATOR = "/";

    @Value("${url.syncgateway.sheme}")
    private String syncGatewayUrlSheme;

    @Value("${url.syncgateway.host}")
    private String syncGatewayUrlHost;

    @Value("${url.syncgateway.port}")
    private Integer syncGatewayUrlPort;

    @Override
    public boolean insertOrUpdate(Object newDoc, String documentId, String bucketName) {

        Optional<JsonObject> document = findAsGsonObject(documentId, bucketName);

        CloseableHttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        builder.setScheme(syncGatewayUrlSheme)
                .setHost(syncGatewayUrlHost)
                .setPort(syncGatewayUrlPort)
                .setPath(URL_SEPARATOR + bucketName + URL_SEPARATOR + documentId);

        if (document.isPresent()) {
            JsonElement element = document.get().get("_rev");
            if (element != null) {
                builder.setParameter("rev", element.getAsString());
            }
        }

        HttpPut httpPut = null;
        try {
            httpPut = new HttpPut(builder.build());
            HttpEntity entity = new StringEntity(new ObjectMapper().writeValueAsString(newDoc));
            httpPut.setEntity(entity);
            httpPut.setHeader(HTTP.CONTENT_TYPE, "application/json");
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            LOGGER.error("Error processing given document", e);
        } catch (URISyntaxException e) {
            LOGGER.error("Error creating URL for document insertion", e);
        }
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPut);
            if (HttpStatus.SC_CREATED == response.getStatusLine().getStatusCode()) {
                return true;
            }
        } catch (IOException e) {
            LOGGER.error("Error executing insertion.", e);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOGGER.info("Error while closing insertion response.");
                }
            }
        }
        return false;
    }

    @Override
    public Optional<JsonObject> findAsGsonObject(String documentId, String bucketName) {
        Optional<String> document = findAsString(documentId, bucketName);
        if (document.isPresent()) {
            JsonParser parser = new JsonParser();
            return Optional.of(parser.parse(document.get()).getAsJsonObject());
        } else {
            return Optional.empty();
        }
    }

    private Optional<String> findAsString(String documentId, String bucketName) {
        CloseableHttpClient client = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder();
        builder.setScheme(syncGatewayUrlSheme)
                .setHost(syncGatewayUrlHost)
                .setPort(syncGatewayUrlPort)
                .setPath(URL_SEPARATOR + bucketName + URL_SEPARATOR + documentId);

        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(builder.build());
            response = client.execute(httpGet);
            InputStream is = response.getEntity().getContent();

            String result;
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
                result = buffer.lines().collect(Collectors.joining("\n"));
            }
            return Optional.of(result);
        } catch (URISyntaxException | IOException e) {
            LOGGER.info("Error while retrieving a document.");
            return Optional.empty();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOGGER.info("Error while closing insertion response.");
                }
            }
        }
    }

    @Override
    public <T> T findDocumentAs(String documentId, String bucketName, Class<T> clazz) {
        Optional<String> document = findAsString(documentId, bucketName);
        if (document.isPresent()) {
            try {
                return new ObjectMapper().readValue(document.get(), clazz);
            } catch (IOException e) {
                LOGGER.error("Error for document deserialization", e);
            }
        }
        return null;
    }
}
