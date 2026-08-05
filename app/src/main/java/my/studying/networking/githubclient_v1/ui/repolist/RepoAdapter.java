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

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Repository repository);
    }

    private final List<Repository> repositories = new ArrayList<>();
    private final OnItemClickListener listener;

    public RepoAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Repository> repos) {
        repositories.clear();
        if (repos != null) {
            repositories.addAll(repos);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repository, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        Repository repo = repositories.get(position);
        holder.bind(repo, listener);
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvRepoName;
        private final TextView tvRepoDescription;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRepoName = itemView.findViewById(R.id.tv_repo_name);
            tvRepoDescription = itemView.findViewById(R.id.tv_repo_description);
        }

        public void bind(Repository repo, OnItemClickListener listener) {
            tvRepoName.setText(repo.getName());

            String desc = repo.getDescription();
            if (desc == null || desc.trim().isEmpty()) {
                tvRepoDescription.setText("No description message");
            } else {
                tvRepoDescription.setText(desc);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(repo);
                }
            });
        }
    }
}
