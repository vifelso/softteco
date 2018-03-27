package by.softteco.hryharenka.testtask.di.components;

import javax.inject.Singleton;

import by.softteco.hryharenka.testtask.adapters.PostRecyclerViewAdapter;
import by.softteco.hryharenka.testtask.views.FirstActivity;
import by.softteco.hryharenka.testtask.di.modules.NetModule;
import by.softteco.hryharenka.testtask.views.PostFragment;
import dagger.Component;


@Singleton
@Component(modules={NetModule.class})
public interface NetComponent {
    void inject(FirstActivity activity);
    void inject(PostFragment postFragment);
    void inject(PostRecyclerViewAdapter adapter);
}
