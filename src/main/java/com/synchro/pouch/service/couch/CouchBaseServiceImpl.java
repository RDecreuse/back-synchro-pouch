package com.synchro.pouch.service.couch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class CouchBaseServiceImpl implements CouchBaseService {

    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final String URL_SEPARATOR = "/";

    @Value("${url.syncgateway}")
    private String syncGatewayUrl;

    @Override
    public boolean insertOrUpdate(Object obj, String documentId, String bucketName) {

        // WIP
        return false;
    }

    @Override
    public JsonObject findAsGsonObject(String documentId, String bucketName) {
        String document = findAsString(documentId, bucketName);
        JsonParser parser = new JsonParser();
        return parser.parse(document).getAsJsonObject();
    }

    public String findAsString(String documentId, String bucketName) {
        try {
            GenericUrl url = new GenericUrl(syncGatewayUrl + bucketName + URL_SEPARATOR + documentId);
            HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);

            HttpResponse response = request.execute();
            System.out.println(response.getStatusCode());
            InputStream is = response.getContent();

            String result = null;
            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
                result = buffer.lines().collect(Collectors.joining("\n"));
            }

            response.disconnect();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T findDocumentAs(String documentId, String bucketName, Class<T> clazz) {
        String jsonObject = findAsString(documentId, bucketName);

        try {
            return new ObjectMapper().readValue(jsonObject, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
