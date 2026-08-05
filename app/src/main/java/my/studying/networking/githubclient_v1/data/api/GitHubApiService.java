package my.studying.networking.githubclient_v1.data.api;

import java.util.List;

import my.studying.networking.githubclient_v1.data.model.Repository;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubApiService {

    @GET("orgs/{org}/repos")
    Call<List<Repository>> listOrgRepos(@Path("org") String org);

    @GET("repos/{owner}/{repo}")
    Call<Repository> getRepo(@Path("owner") String owner, @Path("repo") String repo);
}
