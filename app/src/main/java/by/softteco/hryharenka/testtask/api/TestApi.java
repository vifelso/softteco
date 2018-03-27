package by.softteco.hryharenka.testtask.api;

import java.util.List;

import by.softteco.hryharenka.testtask.models.Post;
import by.softteco.hryharenka.testtask.models.User;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Andrei on 13.02.2018.
 */


public interface TestApi {
    @GET("posts")
    Observable<List<Post>> getPosts();
    @GET("users/{id}")
    Single<User> getUsersData(@Path("id") int id);
}