package by.softteco.hryharenka.testtask.views;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import by.softteco.hryharenka.testtask.R;

/**
 * Created by Andrei on 08.03.2018.
 */

public class AnimationFragment extends Fragment {
    Button saveLogcat;
    AnimationView animationView;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.animation_fragment, container, false);
        RelativeLayout myLayout = v.findViewById(R.id.logcatLayout);
        saveLogcat = myLayout.findViewById(R.id.btnSaveLogCat);
        saveLogcat.setOnClickListener(v1 -> {
            writeLogCat();
            Toast.makeText(getActivity(), "logcat сохранён",
                    Toast.LENGTH_LONG).show();

        });
        myLayout.addView(animationView);
        return myLayout;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        animationView = new AnimationView(context);
    }


    protected void writeLogCat() {
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder log = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
                log.append("\n");
            }

            //Convert log to string
            final String logString = log.toString();

            //Create txt file in SD Card
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + File.separator + "Log File");

            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, "logcat.txt");

            //To write logcat in text file
            FileOutputStream fout = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fout);

            //Writing the string to file
            osw.write(logString);
            osw.flush();
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setVisibleLogcatButton() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (saveLogcat != null) {
                        saveLogcat.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }

    public void setInvisibleLogcatButton() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                if (saveLogcat != null) {
                    saveLogcat.setVisibility(View.GONE);
                }
            });
        }
    }


    public class AnimationView extends SurfaceView implements SurfaceHolder.Callback {
        int animationViewWidth, animationViewHeight;

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            animationViewWidth = getWidth();
            animationViewHeight = getHeight();
        }

        // This is our thread
        public DrawThread drawThread = null;
        // This is new. We need a SurfaceHolder
        // When we use Paint and Canvas in a thread
        // We will see it in action in the draw method soon.
        SurfaceHolder ourHolder;
        volatile boolean playing;
        Paint paint;
        Bitmap bitmapBob;
        private int frameWidth = 184;
        private int frameHeight = 325;
        Rect frameToDraw;
        RectF whereToDraw;
        private int frameCount = 8;
        private int currentFrame = 0;
        private long lastFrameChangeTime = 0;
        private int frameLengthInMilliseconds = 100;
        private long finishTime = 5000;
        private long startFrameTime;
        private long time;


        public AnimationView(Context context) {
            super(context);
            ourHolder = getHolder();
            ourHolder.addCallback(this);
            paint = new Paint();
            bitmapBob = BitmapFactory.decodeResource(this.getResources(), R.drawable.bob);
            bitmapBob = Bitmap.createScaledBitmap(bitmapBob,
                    frameWidth * frameCount,
                    frameHeight,
                    false);
            frameToDraw = new Rect(0, 0, frameWidth, frameHeight);
            System.out.println("constructor");

        }


        public class DrawThread extends Thread {
            volatile Canvas canvas;
            private SurfaceHolder surfaceHolder;

            DrawThread(SurfaceHolder surfaceHolder) {
                this.surfaceHolder = surfaceHolder;
                System.out.println("thread constructor");
            }

            @Override
            public void run() {
                startFrameTime = System.currentTimeMillis();
                while (playing && time < startFrameTime + finishTime && !Thread.currentThread().isInterrupted()) {
                    draw();
                }
                setVisibleLogcatButton();
                if (!playing) {
                    setInvisibleLogcatButton();
                    System.out.println("inter inv");
                }
            }

            void getCurrentFrame() {

                time = System.currentTimeMillis();
                if (time > lastFrameChangeTime + frameLengthInMilliseconds) {
                    lastFrameChangeTime = time;
                    currentFrame++;
                    if (currentFrame >= frameCount) {

                        currentFrame = 0;
                    }
                }

                //update the left and right values of the source of
                //the next frame on the spritesheet
                frameToDraw.left = currentFrame * frameWidth;
                frameToDraw.right = frameToDraw.left + frameWidth;

            }

            // Draw the newly updated scene
             void draw() {

                // Make sure our drawing surface is valid or we crash
                if (surfaceHolder.getSurface().isValid() && playing && !Thread.currentThread().isInterrupted()) {
                    canvas = surfaceHolder.lockCanvas();
                    System.out.println("canvas");
                    if (canvas != null) {
                        canvas.drawColor(Color.argb(255, 26, 128, 182));
                        getCurrentFrame();
                        whereToDraw = new RectF(0, 0, animationViewWidth, animationViewHeight);
                        canvas.drawBitmap(bitmapBob,
                                frameToDraw,
                                whereToDraw, paint);
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }


                }

            }
        }


        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            playing = true;
            if(drawThread == null){
                drawThread = new DrawThread(ourHolder);
                drawThread.start();
                System.out.println("start thread");
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            setInvisibleLogcatButton();
            playing = false;
            if(drawThread != null){
                drawThread.interrupt();
                drawThread = null;
            }
            System.out.println("interupt all");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(animationView.drawThread != null){
            animationView.drawThread.interrupt();
            animationView.drawThread = null;
        }


    }
}
