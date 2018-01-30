package com.example.jimi.mystroke;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * Created by jimi on 08/12/2017.
 */

public class ThreadExecutor implements Executor{
    @Override
    public void execute(@NonNull Runnable command) {
        new Thread(command).run();
    }
}
