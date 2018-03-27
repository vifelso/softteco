package by.softteco.hryharenka.testtask;

import android.app.Application;

import by.softteco.hryharenka.testtask.di.components.DaggerNetComponent;
import by.softteco.hryharenka.testtask.di.components.NetComponent;
import by.softteco.hryharenka.testtask.di.modules.NetModule;

/**
 * Created by Andrei on 21.03.2018.
 */

public class TestApp extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule("http://jsonplaceholder.typicode.com/"))
                .build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
