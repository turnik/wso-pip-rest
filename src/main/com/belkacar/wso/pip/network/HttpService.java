package com.belkacar.wso.pip.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.Set;

public interface HttpService {

    @GET("attribute/{attributeId}")
    Call<Set<String>> getAttribute(
            @Path("attributeId") String attributeId,
            @Query("subject_id") String subjectId,
            @Query("resource_id") String resourceId,
            @Query("action_id") String actionId,
            @Query("environment_id") String environmentId,
            @Query("issuer") String issuer);

    @GET("attribute")
    Call<Set<String>> getAttributesList();
}
