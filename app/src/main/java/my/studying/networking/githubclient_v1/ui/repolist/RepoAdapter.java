package my.studying.networking.githubclient_v1.ui.repolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import my.studying.networking.githubclient_v1.R;
import my.studying.networking.githubclient_v1.data.model.Repository;

//#0. Adapter need a view holder, that defines how the element of the recyclerview will look like. It's an inflator.
//click RepoViewHolder to read next step
public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    //#3 define a listener with a method to send a repository to open
    public interface OnItemClickListener {
        void onItemClick(Repository repository);
    }

    //Adapter variables, repositories
    private final List<Repository> repositories = new ArrayList<>();
    private final OnItemClickListener clickAction;

    //#4 constructor, needs a listener for his only function - open a repository information
    public RepoAdapter(OnItemClickListener clickAction) {
        this.clickAction = clickAction;
    }

    //a method fills a list with information about repositories with notifying
    public void submitList(List<Repository> repos) {
        repositories.clear();
        if (repos != null) {
            repositories.addAll(repos);
        }
        notifyDataSetChanged();
    }


    // #5 onCreate inflates a new viewholder and return it wih his view context,
    // onBind uses it to set data through method bind

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repository, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        //receive data from repo according to view position
        Repository repo = repositories.get(position);
        holder.bind(repo, clickAction);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    //#1 A view holder, that defines how the element of the recyclerview will look like.
    //next step is a bind method
    static class RepoViewHolder extends RecyclerView.ViewHolder {

        //variable for a text for first layout textview
        //variable for a text for second layout textview
        private final TextView tvRepoName;
        private final TextView tvRepoDescription;
        // constructor, there textview will be found
        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRepoName = itemView.findViewById(R.id.tv_repo_name);
            tvRepoDescription = itemView.findViewById(R.id.tv_repo_description);
        }

        //#2 method to show data about repository from the repositoryList
        // needs a listener action to open a repository info, click and read
        //click bind for a next step
        public void bind(Repository repo, OnItemClickListener clickAction) {
            // set repo name
            tvRepoName.setText(repo.getName());
            // set repo description
            String desc = repo.getDescription();
            if (desc == null || desc.trim().isEmpty()) {
                tvRepoDescription.setText("No description message");
            } else {
                tvRepoDescription.setText(desc);
            }
            //if clicked on item, call listener and send him a repository to open
            itemView.setOnClickListener(v -> {
                if (clickAction != null) {
                    clickAction.onItemClick(repo);
                }
            });
        }
    }
}
