package my.studying.networking.githubclient_v1.ui.repodetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import my.studying.networking.githubclient_v1.data.model.Repository;
import my.studying.networking.githubclient_v1.data.repository.GitHubRepository;
import my.studying.networking.githubclient_v1.data.repository.RepoCallback;
import my.studying.networking.githubclient_v1.util.Resource;

public class RepoDetailsViewModel extends ViewModel {

    private final GitHubRepository repository;
    private final MutableLiveData<Resource<Repository>> repoDetailsLiveData = new MutableLiveData<>();

    public RepoDetailsViewModel() {
        this.repository = new GitHubRepository();
    }

    public LiveData<Resource<Repository>> getRepoDetails() {
        return repoDetailsLiveData;
    }

    public void loadRepoDetails(String org, String repo) {
        repoDetailsLiveData.setValue(Resource.loading());
        repository.getRepoDetails(org, repo, new RepoCallback<Repository>() {
            @Override
            public void onSuccess(Repository result) {
                repoDetailsLiveData.postValue(Resource.success(result));
            }

            @Override
            public void onError(String errorMessage) {
                repoDetailsLiveData.postValue(Resource.error(errorMessage));
            }
        });
    }
}
