package by.softteco.hryharenka.testtask.view_models;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;


import java.util.List;

import by.softteco.hryharenka.testtask.api.TestApi;
import by.softteco.hryharenka.testtask.models.Post;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Andrei on 21.03.2018.
 */

public class PostsViewModel extends ViewModel {
        private final TestApi testApi;

        private final CompositeDisposable disposablesPosts = new CompositeDisposable();


        private MutableLiveData<List<Post>> postsResponse = new MutableLiveData<>();


        public PostsViewModel(TestApi testApi) {
            this.testApi = testApi;
        }

        @Override
        protected void onCleared() {
            disposablesPosts.clear();
        }

        public void loadPostData() {
            loadPosts(testApi);
        }


        public MutableLiveData<List<Post>> postResponse() {
            return postsResponse;
        }


        @SuppressWarnings("unchecked")
        private void loadPosts(final TestApi testApi) {
            disposablesPosts.add(testApi.getPosts()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            meta -> postsResponse.postValue(meta)
                    )
            );
        }

}
