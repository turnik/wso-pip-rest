package com.belkacar.wso.pip.network;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.Set;

public interface HttpPipResolverService {
    @GET("pip")
    Call<Set<String>> getPipList();
}
