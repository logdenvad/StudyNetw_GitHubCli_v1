package my.studying.networking.githubclient_v1.data.repository;

public interface RepoCallback<T> {
    void onSuccess(T result);
    void onError(String errorMessage);
}
