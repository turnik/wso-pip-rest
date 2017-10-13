package com.belkacar.wso.pip.network;

import retrofit2.Call;

import java.util.Set;

@Deprecated
public class HttpClient implements HttpService {

    private HttpService httpService;

    @Override
    public Call<Set<String>> getAttribute(String attributeId, String subjectId, String resourceId, String actionId, String environmentId, String issuer) {
        return null;
    }

    @Override
    public Call<Set<String>> getAttributesList() {
        return null;
    }
}
