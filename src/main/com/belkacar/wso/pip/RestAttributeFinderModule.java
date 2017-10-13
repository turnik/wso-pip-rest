package com.belkacar.wso.pip;

import com.belkacar.wso.pip.network.HttpClientFactory;
import com.belkacar.wso.pip.network.HttpService;
import retrofit2.Call;
import okhttp3.Response;
import org.wso2.carbon.identity.entitlement.pip.AbstractPIPAttributeFinder;
import retrofit2.Callback;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class RestAttributeFinderModule extends AbstractPIPAttributeFinder {

    private static final String KEY_ENDPOINT = "Endpoint";
    private static final String MODULE_NAME = "BelkaCar Attribute Finder";

    private HttpService httpService;

    @Override
    public Set<String> getAttributeValues(String subjectId, String resourceId, String actionId, String environmentId,
                                          String attributeId, String issuer) throws Exception {
        //init request object
        Call<Set<String>> request = httpService.
                getAttribute(attributeId, subjectId, resourceId, actionId, environmentId, issuer);

        try {
            /*
            try to make request in blocking mode
            example of usage @see https://futurestud.io/tutorials/retrofit-synchronous-and-asynchronous-requests
            we use retrofit2
             */
            retrofit2.Response<Set<String>> response = request.execute();

            /*
            here will be result of request converted to Set<String>
            you can use some methods like
            response.isSuccessful();
            response.code();
            for checking request status
             */

            return response.body();

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("fucking bullshit :)");

    }

    @Override
    public void init(Properties properties) throws Exception {
        String endpoint = (String) properties.get(KEY_ENDPOINT);

        if (endpoint == null || endpoint.trim().length() == 0) {
            throw new Exception("Endpoint can not be null. Please configure it in the entitlement.properties file.");
        }

        /*
        add http service to our finder module
         */
        httpService = HttpClientFactory.getHttpService(endpoint);
    }

    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }

    @Override
    public Set<String> getSupportedAttributes() {
        return null;
    }
}
