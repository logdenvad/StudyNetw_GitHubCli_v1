package my.studying.networking.githubclient_v1.ui.repolist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import my.studying.networking.githubclient_v1.data.model.Repository;
import my.studying.networking.githubclient_v1.data.repository.GitHubRepository;
import my.studying.networking.githubclient_v1.data.repository.RepoCallback;
import my.studying.networking.githubclient_v1.util.Resource;

//contains a behavior of the repolistfragment

public class RepoListViewModel extends ViewModel {

    //needs a githubrepository to receive data form GitHub
    private final GitHubRepository repository;
    //holds a data about orgnization repos received from GitHubRepository, and status of the search action within Resource
    private final MutableLiveData<Resource<List<Repository>>> reposLiveData = new MutableLiveData<>();
    // constructor, creates a new repository inside to make class more flexible for using and testing
    public RepoListViewModel() {
        this.repository = new GitHubRepository();
    }

    //method to subsctibe to receive a new repolist
    public LiveData<Resource<List<Repository>>> getRepos() {
        return reposLiveData;
    }

    //method sets status of the data package to loading and send a command to GitHubRepository class to search repos of organization with the entered name
    //listOfRepos required to define methods of a RepoCallback interface to receive back the data an what to do with it
    //depends on result, post error status with user's org name,
    //in case of success post a list of received repositories from Git API
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
