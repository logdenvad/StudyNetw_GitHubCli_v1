package my.studying.networking.githubclient_v1.ui.repodetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import my.studying.networking.githubclient_v1.R;
import my.studying.networking.githubclient_v1.data.model.Repository;

public class RepoDetailsFragment extends Fragment {

    private ProgressBar pbLoading;
    private TextView tvName;
    private TextView tvDescription;
    private TextView tvForks;
    private TextView tvWatchers;
    private TextView tvOpenIssues;
    private LinearLayout rowParent;
    private TextView tvParentFullName;

    private RepoDetailsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pbLoading = view.findViewById(R.id.pb_loading);
        tvName = view.findViewById(R.id.tv_name);
        tvDescription = view.findViewById(R.id.tv_description);
        tvForks = view.findViewById(R.id.tv_forks);
        tvWatchers = view.findViewById(R.id.tv_watchers);
        tvOpenIssues = view.findViewById(R.id.tv_open_issues);
        rowParent = view.findViewById(R.id.row_parent);
        tvParentFullName = view.findViewById(R.id.tv_parent_full_name);

        viewModel = new ViewModelProvider(this).get(RepoDetailsViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            String orgName = args.getString("orgName");
            String repoName = args.getString("repoName");
            if (orgName != null && repoName != null) {
                viewModel.loadRepoDetails(orgName, repoName);
            }
        }

        viewModel.getRepoDetails().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            switch (resource.getStatus()) {
                case LOADING:
                    pbLoading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    pbLoading.setVisibility(View.GONE);
                    populateDetails(resource.getData());
                    break;
                case ERROR:
                    pbLoading.setVisibility(View.GONE);
                    break;
            }
        });
    }

    private void populateDetails(Repository repo) {
        if (repo == null) return;

        tvName.setText(repo.getName());

        String desc = repo.getDescription();
        if (desc == null || desc.trim().isEmpty()) {
            tvDescription.setText("No description message");
        } else {
            tvDescription.setText(desc);
        }

        tvForks.setText(getString(R.string.label_forks, repo.getForksCount()));
        tvWatchers.setText(getString(R.string.label_watchers, repo.getWatchersCount()));
        tvOpenIssues.setText(getString(R.string.label_open_issues, repo.getOpenIssuesCount()));

        if (repo.isFork()) {
            rowParent.setVisibility(View.VISIBLE);
            if (repo.getParent() != null) {
                tvParentFullName.setText(repo.getParent().getFullName());
            } else {
                tvParentFullName.setText("");
            }
        } else {
            rowParent.setVisibility(View.GONE);
        }
    }
}
