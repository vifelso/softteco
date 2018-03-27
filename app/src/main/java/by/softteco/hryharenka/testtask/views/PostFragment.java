package by.softteco.hryharenka.testtask.views;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import by.softteco.hryharenka.testtask.LinePagerIndicatorDecoration;
import by.softteco.hryharenka.testtask.R;
import by.softteco.hryharenka.testtask.TestApp;
import by.softteco.hryharenka.testtask.adapters.PostRecyclerViewAdapter;
import by.softteco.hryharenka.testtask.factories.PostsViewModelFactory;
import by.softteco.hryharenka.testtask.models.Post;
import by.softteco.hryharenka.testtask.view_models.PostsViewModel;

/**
 * Created by Andrei on 18.03.2018.
 */

public class PostFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Post> posts;
    @Inject
    PostsViewModelFactory postsModelFactory;
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        ((TestApp) getActivity().getApplicationContext()).getNetComponent().inject(this);
        PostsViewModel viewModel = ViewModelProviders.of(this, postsModelFactory).get(PostsViewModel.class);
        viewModel.postResponse().observe(this, this::setPosts);
        viewModel.loadPostData();
        View v = inflater.inflate(R.layout.post_fragment, container, false);
        recyclerView = v.findViewById(R.id.result_recycler_view);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager
                = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        posts = new ArrayList<>();
        adapter = new PostRecyclerViewAdapter(posts, getActivity());
        recyclerView.setAdapter(adapter);
        // add pager behavior
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new LinePagerIndicatorDecoration());
        return v;
    }

    private void setPosts(List<Post> posts) {
        this.posts = posts;
        recyclerView.swapAdapter(new PostRecyclerViewAdapter(this.posts, getActivity()), true);
    }

}
