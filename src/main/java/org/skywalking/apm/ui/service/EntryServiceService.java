package org.skywalking.apm.ui.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.skywalking.apm.ui.creator.UrlCreator;
import org.skywalking.apm.ui.tools.HttpClientTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pengys5
 */
@Service
public class EntryServiceService {

    private Logger logger = LogManager.getFormatterLogger(EntryServiceService.class);

    private Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    private UrlCreator UrlCreator;

    public JsonObject load(int applicationId, String entryServiceName, long startTime, long endTime, int from,
        int size) throws IOException {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("applicationId", String.valueOf(applicationId)));
        params.add(new BasicNameValuePair("entryServiceName", entryServiceName));
        params.add(new BasicNameValuePair("startTime", String.valueOf(startTime)));
        params.add(new BasicNameValuePair("endTime", String.valueOf(endTime)));
        params.add(new BasicNameValuePair("from", String.valueOf(from)));
        params.add(new BasicNameValuePair("size", String.valueOf(size)));

        String entryServiceListLoadUrl = UrlCreator.compound("service/entry");
        String entryServiceListResponse = HttpClientTools.INSTANCE.get(entryServiceListLoadUrl, params);

        logger.debug("load entry service data: %s", entryServiceListResponse);
        return gson.fromJson(entryServiceListResponse, JsonObject.class);
    }
}