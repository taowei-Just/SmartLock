package com.it_tao.smartlock.service.audio;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;

import com.it_tao.smartlock.service.audio.handle.AudioHandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import huanyang.gloable.gloable.Path;
import huanyang.gloable.gloable.Shared;
import huanyang.gloable.gloable.utils.FileUtils;
import huanyang.gloable.gloable.utils.LogUtil;
import huanyang.gloable.gloable.utils.SharedUtlis;

public class AudioService extends Service implements AudioHandle {

    private SoundPool soundPool;
    private HashMap<String, Integer> audioMap;
    String TAG = "AudioService";
    private static MediaPlayer mediaPlayer;

    private Handler audioHandler;
    private Thread audioThread;

    private Looper audioLooper;
    private final int PLAY_AUDIO_WHAT = 10;
    private int streamVolume;
    private int audioStreamType = AudioManager.STREAM_MUSIC;
    private SharedUtlis sharedUtlis;
    private AssetManager assets;
    private FileInputStream is;

    boolean isFirst = false;
    private Vibrator vibrator;

    public IBinder onBind(Intent arg0) {
        return new AudioBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String from = intent.getStringExtra("from");
        boolean beep = intent.getBooleanExtra("beep", false);
        boolean testbeep = intent.getBooleanExtra("testbeep", false);
        if (from != null && from.equals("redpacket")) {
            playMediaAodio("", true);
        }

        if (beep) {
            long[] pattern = {10, 150, 80, 100, 80, 200, 80, 300, 100, 150, 80, 100, 80, 200, 80, 300}; // 停止 开启 停止 开启
            vibrator.vibrate(pattern, -1);
        }
        if (testbeep) {
            long[] pattern = {10, 150, 80, 100, 80, 200, 80, 300}; // 停止 开启 停止 开启
            vibrator.vibrate(pattern, -1);
        }


        return super.onStartCommand(intent, flags, startId);
    }

    public void onCreate() {
        super.onCreate();
        checkAudio();
        LogUtil.e(TAG, " onCreate ");
        LogUtil.e(TAG, " audioservice Thread number " + Thread.currentThread().toString());
        sharedUtlis = new SharedUtlis(this, Shared.config);
        assets = getAssets();
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//		streamVolume =sharedUtlis.getInt(Shared.aud,audioManager.getStreamVolume(audioStreamType)) ;
//		audioManager.setStreamVolume(audioStreamType, streamVolume, 0);

    }

    private void checkAudio() {

        AssetManager assets = getAssets();

        try {
            String[] images = assets.list("audio");

            if (null != images) {
                for (String name : images) {
                    File file = new File(Path.audioFolder + "/" + name);

                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    } else {
                        file.delete();
                        file.createNewFile();
                    }

                    FileUtils.copyAssiets(assets.open("audio/" + file.getName()), file.getAbsolutePath().toString());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    class AudioHandler extends Handler {

        public AudioHandler(Looper audioLooper) {
            // TODO Auto-generated constructor stub

            super(audioLooper);
        }

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case PLAY_AUDIO_WHAT:
                    // play
//				LogUtil.e(TAG, "  开始播放 " + audioList.toString());
                    // 播放音频
                    if (audioList != null && audioList.size() > 0) {
                        if (mediaPlayer != null) {
                            mediaPlayer.release();
                            isPlayer = false;
                        }
                        String path = audioList.get(0);
                        playMediaAodio(path, false);
                        audioList.remove(0);
                    }
                    break;
            }
        }
    }

    ArrayList<String> audioList = new ArrayList<String>();
    boolean isPlayer = false;
    private AudioManager audioManager;

    private void playMediaAodio(final String path, boolean isAssest) {
        try {
            if (!isPlayer) {
                mediaPlayer = new MediaPlayer();
                if (isAssest) {
                    AssetFileDescriptor openFd = assets.openFd("raw/tip1.ogg");
                    mediaPlayer.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                } else {
                    is = new FileInputStream(path);
                    mediaPlayer.setDataSource(is.getFD());
                }

                mediaPlayer.setAudioStreamType(audioStreamType);
                mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer arg0) {
                        Log.e(TAG, "onPrepared :");
                        mediaPlayer.start();
                    }
                });

                mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                                                        public void onCompletion(MediaPlayer arg0) {
                                                            Log.e(TAG, "onCompletion :");
                                                            // TODO Auto-generated method stub
                                                            if (mediaPlayer != null) {
                                                                mediaPlayer.reset();
                                                                mediaPlayer.release();
                                                            }
                                                            isPlayer = false;

                                                        }
                                                    }
                );

                mediaPlayer.prepareAsync();
                isPlayer = true;
            } else {
            }
        } catch (Exception e) {

            e.printStackTrace();
            LogUtil.e(TAG, "  音频播放 异常  " + e.toString());
            if (mediaPlayer != null) {
                mediaPlayer.release();
                isPlayer = false;
            }
        } finally {

        }
    }


    public void start() {

    }

    public void pause() {

    }

    public void stop() {

        if (isPlayer && mediaPlayer != null) {
            isPlayer = false;
            mediaPlayer.reset();

        }

    }

    public void newxt() {

    }

    public void restart() {

    }

    public void close() {

    }

    @Override
    public void playAudio(String path) {
        // TODO Auto-generated method stub
        LogUtil.e(TAG, " playAudio    __");
        if (path == null || path.isEmpty())
            return;
        audioList.add(path);
        boolean b = audioHandler.sendEmptyMessage(PLAY_AUDIO_WHAT);
        LogUtil.e(TAG, "  playAudio send message  = " + b);
        if (!b) {
            audioHandler = new AudioHandler(audioLooper);
            b = audioHandler.sendEmptyMessage(PLAY_AUDIO_WHAT);
        }

    }

    @Override
    public void clearAudio(String path) {
        // TODO Auto-generated method stub

        if (audioMap != null && soundPool != null) {

            int load = audioMap.remove(path);
            soundPool.unload(load);

            // soundPool.play(soundID, leftVolume, rightVolume, priority, loop,
            // rate);

        }

    }

    public class AudioBinder extends Binder implements AudioHandle {

        public AudioBinder() {
        }

        public AudioService getService() {

            return AudioService.this;
        }

        public void start() {

            AudioService.this.start();
        }

        public void pause() {
            // TODO Auto-generated method stub
            AudioService.this.pause();
        }

        public void stop() {
            // TODO Auto-generated method stub
            AudioService.this.stop();
        }

        public void newxt() {
            // TODO Auto-generated method stub
            AudioService.this.newxt();
        }

        public void restart() {
            // TODO Auto-generated method stub
            AudioService.this.restart();
        }

        public void close() {

            AudioService.this.close();

        }

        @Override
        public void playAudio(String path) {
            // TODO Auto-generated method stub
            AudioService.this.playAudio(path);

        }

        @Override
        public void clearAudio(String path) {
            // TODO Auto-generated method stub
            AudioService.this.clearAudio(path);
        }
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        LogUtil.e(TAG, "onDestroy");

        if (audioHandler != null)

        {
            audioHandler.removeMessages(PLAY_AUDIO_WHAT);

        }
        if (audioThread != null && !audioThread.isInterrupted())

        {
            audioThread.interrupt();
        }
    }

    @Override
    public String toString() {
        return "AudioService [soundPool=" + soundPool + ", audioMap="
                + audioMap + ", TAG=" + TAG + ", audioHandler=" + audioHandler
                + ", audioThread=" + audioThread + ", audioLooper="
                + audioLooper + ", PLAY_AUDIO_WHAT=" + PLAY_AUDIO_WHAT
                + ", audioList=" + audioList + ", isPlayer=" + isPlayer
                + ", audioManager=" + audioManager + "]";
    }

}
