package by.softteco.hryharenka.testtask.factories;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import by.softteco.hryharenka.testtask.api.TestApi;
import by.softteco.hryharenka.testtask.view_models.PostsViewModel;

/**
 * Created by Andrei on 21.03.2018.
 */
@Singleton
public class PostsViewModelFactory implements ViewModelProvider.Factory {

        private final TestApi testApi;



        @Inject
        public PostsViewModelFactory(TestApi testApi) {
            this.testApi = testApi;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(PostsViewModel.class)) {
                return (T) new PostsViewModel(testApi);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
}
