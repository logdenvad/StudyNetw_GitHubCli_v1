package my.studying.networking.githubclient_v1.ui.repolist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import my.studying.networking.githubclient_v1.data.model.Repository;
import my.studying.networking.githubclient_v1.data.repository.GitHubRepository;
import my.studying.networking.githubclient_v1.data.repository.RepoCallback;
import my.studying.networking.githubclient_v1.util.Resource;

public class RepoListViewModel extends ViewModel {

    private final GitHubRepository repository;
    private final MutableLiveData<Resource<List<Repository>>> reposLiveData = new MutableLiveData<>();

    public RepoListViewModel() {
        this.repository = new GitHubRepository();
    }

    public LiveData<Resource<List<Repository>>> getRepos() {
        return reposLiveData;
    }

    public void searchOrg(String orgName) {
        reposLiveData.setValue(Resource.loading());
        repository.listOrgRepos(orgName, new RepoCallback<List<Repository>>() {
            @Override
            public void onSuccess(List<Repository> result) {
                reposLiveData.postValue(Resource.success(result));
            }

            @Override
            public void onError(String errorMessage) {
                reposLiveData.postValue(Resource.error(orgName));
            }
        });
    }
}
