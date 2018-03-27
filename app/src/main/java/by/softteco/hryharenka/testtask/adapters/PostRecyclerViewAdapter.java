package by.softteco.hryharenka.testtask.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import by.softteco.hryharenka.testtask.R;
import by.softteco.hryharenka.testtask.TestApp;
import by.softteco.hryharenka.testtask.api.TestApi;
import by.softteco.hryharenka.testtask.models.Post;
import by.softteco.hryharenka.testtask.views.SecondActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Andrei on 18.03.2018.
 */

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {
    private List<Post> mDataset;
    private Activity activity;
    @Inject
    TestApi testApi;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView postIdTextView;
        TextView postTitleTextView;

        ViewHolder(View v) {
            super(v);
            postIdTextView = v.findViewById(R.id.postId);
            postTitleTextView = v.findViewById(R.id.postTitle);
        }
    }

    public PostRecyclerViewAdapter(List<Post> results, Activity activity) {
        mDataset = results;
        this.activity = activity;
        ((TestApp) activity.getApplicationContext()).getNetComponent().inject(this);
    }


    @NonNull
    @Override
    public PostRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                 int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_content, parent, false);
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = null;
        if (wm != null) {
            display = wm.getDefaultDisplay();
        }
        Point size = new Point();
        if (display != null) {
            display.getSize(size);
        }
        v.getLayoutParams().width = size.x / 3;


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.postIdTextView.setText(String.valueOf(mDataset.get(position).getId()));
        holder.postTitleTextView.setText(mDataset.get(position).getTitle().substring(0, 10));
        holder.itemView.setOnClickListener(v -> testApi.getUsersData(mDataset.get(position).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userResponse -> {
                            Intent intent = new Intent(activity, SecondActivity.class);
                            intent.putExtra("user", userResponse);
                            intent.putExtra("postId", mDataset.get(position).getId());
                            activity.startActivity(intent);
                        },
                        throwable -> {
                            Toast.makeText(activity, throwable.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();

    }


}
