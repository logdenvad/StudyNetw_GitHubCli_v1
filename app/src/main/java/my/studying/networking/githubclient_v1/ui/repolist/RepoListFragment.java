package my.studying.networking.githubclient_v1.ui.repolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;

import my.studying.networking.githubclient_v1.R;

//main screen. Inflates edit text, search button, progress bar and recycler view with its adapter
//uses a viewModel for actual information and actions
public class RepoListFragment extends Fragment {

    //define elements names
    private EditText etOrgName;
    private Button btnSearch;
    private ProgressBar pbLoading;
    private RecyclerView rvRepos;
    private RepoAdapter adapter;
    private RepoListViewModel viewModel;

    // inflate a layout
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //find the elements and connect them to fragment names
        etOrgName = view.findViewById(R.id.et_org_name);
        btnSearch = view.findViewById(R.id.btn_search);
        pbLoading = view.findViewById(R.id.pb_loading);
        rvRepos = view.findViewById(R.id.rv_repos);

        //define a recycler view adapter
        //adapter requires a clickListener like an attribute - an action with a repository, inside the interface
        //lambda inside is a method (onItemClick) of the interface OnItemClickListene, and repository is an attribute of this method
        //onClick it should navigate to repo details fragment
        adapter = new RepoAdapter(repository -> {
            String orgName = etOrgName.getText().toString().trim();
            Bundle args = new Bundle();
            args.putString("orgName", orgName);
            args.putString("repoName", repository.getName());
            Navigation.findNavController(view).navigate(R.id.action_repoList_to_repoDetails, args);
        });

        rvRepos.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvRepos.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(RepoListViewModel.class);

        //when button clicked parse and send organization name to viewmodel and call searchOrg method
        btnSearch.setOnClickListener(v -> {
            String orgName = etOrgName.getText().toString().trim();
            if (!orgName.isEmpty()) {
                viewModel.searchOrg(orgName);
            }
        });

        //subscribe to viewmodel livedata updates
        viewModel.getRepos().observe(getViewLifecycleOwner(), resource -> {
            if (resource == null) return;

            //livedata is a container with a status and body (data)

            switch (resource.getStatus()) {
                case LOADING:
                    pbLoading.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    pbLoading.setVisibility(View.GONE);
                    adapter.submitList(resource.getData());
                    break;
                case ERROR:
                    pbLoading.setVisibility(View.GONE);
                    adapter.submitList(Collections.emptyList());
                    Toast.makeText(requireContext(),
                            "No repos found for organization " + resource.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }
}
