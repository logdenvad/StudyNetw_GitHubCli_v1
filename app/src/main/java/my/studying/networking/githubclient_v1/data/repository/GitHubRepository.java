package my.studying.networking.githubclient_v1.data.repository;

import java.util.List;

import my.studying.networking.githubclient_v1.data.api.GitHubApiService;
import my.studying.networking.githubclient_v1.data.api.RetrofitClient;
import my.studying.networking.githubclient_v1.data.model.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GitHubRepository {

    private final GitHubApiService apiService;

    public GitHubRepository() {
        this.apiService = RetrofitClient.getInstance().create(GitHubApiService.class);
    }

    public void listOrgRepos(String org, RepoCallback<List<Repository>> callback) {
        apiService.listOrgRepos(org).enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(org);
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                callback.onError(org);
            }
        });
    }

    public void getRepoDetails(String org, String repo, RepoCallback<Repository> callback) {
        apiService.getRepo(org, repo).enqueue(new Callback<Repository>() {
            @Override
            public void onResponse(Call<Repository> call, Response<Repository> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Failed to fetch repository details");
                }
            }

            @Override
            public void onFailure(Call<Repository> call, Throwable t) {
                callback.onError(t.getMessage() != null ? t.getMessage() : "Error occurred");
            }
        });
    }
}
