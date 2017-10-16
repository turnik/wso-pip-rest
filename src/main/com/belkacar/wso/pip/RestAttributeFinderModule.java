package com.belkacar.wso.pip;

import org.wso2.carbon.identity.entitlement.pip.AbstractPIPAttributeFinder;
import com.belkacar.wso.pip.network.HttpClientFactory;
import com.belkacar.wso.pip.network.HttpPipResolverService;
import com.belkacar.wso.pip.network.HttpService;
import retrofit2.Call;

import java.io.IOException;
import java.util.*;

public class RestAttributeFinderModule extends AbstractPIPAttributeFinder {

    private static final String KEY_ENDPOINT = "Endpoint";
    private static final String MODULE_NAME = "BelkaCar Attribute Finder";

    private HashMap<String, HttpService> attributesMap;

    private HashMap<String, HttpService> initPipServices(String endpoint)
    {
        HttpPipResolverService pipResolverService = HttpClientFactory.getHttpPipResolverService(endpoint);

        Call<Set<String>> request = pipResolverService.getPipList();
        Set<String> pipEndpoints = null;
        try {
            retrofit2.Response<Set<String>> response = request.execute();
            if (response.code() == 200) {
                pipEndpoints = response.body();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("fucking bullshit :)");
        }

        HashMap<String, HttpService> httpServices = new HashMap<String, HttpService>();
        if (pipEndpoints != null) {
            for (String pipEndpoint : pipEndpoints)
            {
                httpServices.put(pipEndpoint, HttpClientFactory.getHttpService(pipEndpoint));
            }
        }

        return httpServices;
    }

    private void loadAttributes(HashMap<String, HttpService> httpServices)
    {
        attributesMap = new HashMap<String, HttpService>();
        for (HashMap.Entry<String, HttpService> entry : httpServices.entrySet())
        {
            Set<String> pipAttributes = null;
            Call<Set<String>> request = entry.getValue().getAttributesList();
            try {
                retrofit2.Response<Set<String>> response = request.execute();
                if (response.code() == 200) {
                    pipAttributes = response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("fucking bullshit :)");
            }

            if (pipAttributes != null) {
                for (String pipAttribute : pipAttributes)
                {
                    attributesMap.put(pipAttribute, entry.getValue());
                }
            }
        }
    }

    @Override
    public void init(Properties properties) throws Exception {
        String endpoint = (String) properties.get(KEY_ENDPOINT);

        if (endpoint == null || endpoint.trim().length() == 0) {
            throw new Exception("Endpoint can not be null. Please configure it in the entitlement.properties file.");
        }

        HashMap<String, HttpService> httpServices = initPipServices(endpoint);
        loadAttributes(httpServices);
    }

    @Override
    public Set<String> getSupportedAttributes()
    {
        if (attributesMap == null) {
            return Collections.emptySet();
        }
        return attributesMap.keySet();
    }

    @Override
    public Set<String> getAttributeValues(String subjectId, String resourceId, String actionId,
                                          String environmentId, String attributeId, String issuer) throws Exception {
        HttpService httpService = attributesMap.get(attributeId);
        if (httpService == null) {
            return Collections.emptySet();
        }

        //init request object
        Call<Set<String>> request = httpService.getAttribute(
                attributeId,
                subjectId,
                resourceId,
                actionId,
                environmentId,
                issuer
        );

        try {
            retrofit2.Response<Set<String>> response = request.execute();
            if (response.code() == 200) {
                return response.body();
            } else {
                return Collections.emptySet();
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("fucking bullshit :)");
        }
    }

    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }
}
