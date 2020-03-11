package com.kuaqu.reader.module_common_ui.utils;

import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author vveng
 * @version version 1.0.0
 * @date 2018/7/24 16:03.
 * @email vvengstuggle@163.com
 * @instructions 说明
 * @descirbe 描述
 * @features 功能
 */
public class AudioRecordManager {

    private static final String TAG = "AudioRecordManager";
    private static final String DIR_NAME = "arm";
    private static String AudioFolderFile; //音频文件路径
    private static AudioRecordManager mAudioRecordManager;
    private File PcmFile = null ; //pcm音频文件
    private File WavFile = null;  //wav格式的音频文件
    private AudioRecordThread mAudioRecordThead; //录制线程
    private AudioRecordPlayThead mAudioRecordPlayThead;//播放线程
    private boolean isRecord = false;
    /**
     * 采样率，现在能够保证在所有设备上使用的采样率是44100Hz, 但是其他的采样率（22050, 16000, 11025）在一些设备上也可以使用。
     */
    public static final int SAMPLE_RATE_HERTZ = 44100;

    /**
     * 声道数。CHANNEL_IN_MONO and CHANNEL_IN_STEREO. 其中CHANNEL_IN_MONO是可以保证在所有设备能够使用的。
     */
    public static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_STEREO;

    /**
     * 返回的音频数据的格式。 ENCODING_PCM_8BIT, ENCODING_PCM_16BIT, and ENCODING_PCM_FLOAT.
     */
    public static final int AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;


    public static AudioRecordManager NewInstance() {
        if (mAudioRecordManager == null) {
            synchronized (AudioRecordManager.class) {
                if (mAudioRecordManager == null) {
                    mAudioRecordManager = new AudioRecordManager();
                }
            }
        }
        return mAudioRecordManager;
    }


    /**
     * 播放音频
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public synchronized void playRecord() {
        //可防止重复点击录制
        if (true == isRecord) {
            Log.d(TAG, "无法开始播放，当前状态为：" + isRecord);
            return;
        }
        isRecord = true;
        mAudioRecordPlayThead = new AudioRecordPlayThead(PcmFile);
        mAudioRecordPlayThead.start();
    }

    /**
     * 停止播放
     */
    public void stopPlayRecord() {
        if (null != mAudioRecordPlayThead) {
            mAudioRecordPlayThead.interrupt();
            mAudioRecordPlayThead = null;
        }
        isRecord = false;
    }

    /**
     * 播放音频线程
     */
    private class AudioRecordPlayThead extends Thread {
        AudioTrack mAudioTrack;
        int BufferSize = 10240;
        File autoFile = null; //要播放的文件

        @RequiresApi(api = Build.VERSION_CODES.M)
        AudioRecordPlayThead(File file) {
            setPriority(MAX_PRIORITY);
            autoFile = file;
            //播放缓冲的最小大小
            BufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE_HERTZ,
                    AudioFormat.CHANNEL_OUT_STEREO, AUDIO_FORMAT);
            // 创建用于播放的 AudioTrack

            mAudioTrack= new AudioTrack(AudioManager.STREAM_MUSIC,
                    44100,
                    AudioFormat.CHANNEL_IN_STEREO, AudioFormat.ENCODING_PCM_16BIT, BufferSize,
                    AudioTrack.MODE_STREAM);

        }

        @Override
        public void run() {

            Log.d(TAG, "播放开始");
            try {
                FileInputStream fis = new FileInputStream(autoFile);
                mAudioTrack.play();
                byte[] bytes = new byte[BufferSize];

                while(true == isRecord) {
                    int read = fis.read(bytes);
                    //若读取有错则跳过
                    if (AudioTrack.ERROR_INVALID_OPERATION == read
                            || AudioTrack.ERROR_BAD_VALUE == read) {
                        continue;
                    }

                    if (read != 0 && read != -1) {
                        mAudioTrack.write(bytes, 0, BufferSize);
                    }
                }
                mAudioTrack.stop();
                mAudioTrack.release();//释放资源
                fis.close();//关流

            } catch (Exception e) {
                e.printStackTrace();
            }

            isRecord = false;
            Log.d(TAG, "播放停止");
        }
    }


    /**
     * 开始录制
     */
    public synchronized void startRecord() {
        //可防止重复点击录制
        if (true == isRecord) {
            Log.d(TAG, "无法开始录制，当前状态为：" + isRecord);
            return;
        }
        isRecord = true;
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd_HHmmss", Locale.CHINA);
        //源pcm数据文件
        PcmFile = new File(AudioFolderFile + File.separator + sdf.format(new Date())+".pcm");
        //wav文件
        WavFile = new File(PcmFile.getPath().replace(".pcm",".wav"));

        Log.d(TAG, "PcmFile:"+ PcmFile.getName()+"WavFile:"+WavFile.getName());

        if (null != mAudioRecordThead) {
            //若线程不为空,则中断线程
            mAudioRecordThead.interrupt();
            mAudioRecordThead = null;
        }
        mAudioRecordThead = new AudioRecordThread();
        mAudioRecordThead.start();
    }

    /**
     * 停止录制
     */
    public synchronized void stopRecord() {
        if (null != mAudioRecordThead) {
            mAudioRecordThead.interrupt();
            mAudioRecordThead = null;
        }

        isRecord = false;
    }

    /**
     * 录制线程
     */
    private class AudioRecordThread extends Thread {
        AudioRecord mAudioRecord;
        int BufferSize = 10240;

        AudioRecordThread() {
            /**
             * 获取音频缓冲最小的大小
             */
            BufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_HERTZ,
                    CHANNEL_CONFIG, AUDIO_FORMAT);
            /**
             * 参数1：音频源
             * 参数2：采样率 主流是44100
             * 参数3：声道设置 MONO单声道 STEREO立体声
             * 参数4：编码格式和采样大小 编码格式为PCM,主流大小为16BIT
             * 参数5：采集数据需要的缓冲区大小
             */
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE_HERTZ, CHANNEL_CONFIG, AUDIO_FORMAT, BufferSize);
        }

        @Override
        public void run() {
            //将状态置为录制

            Log.d(TAG, "录制开始");
            try {
                byte[] bytes = new byte[BufferSize];

                FileOutputStream PcmFos = new FileOutputStream(PcmFile);

                //开始录制
                mAudioRecord.startRecording();

                while (true == isRecord && !isInterrupted()) {
                    int read = mAudioRecord.read(bytes, 0, bytes.length);
                    //若读取数据没有出现错误，将数据写入文件
                    if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                        PcmFos.write(bytes, 0, read);
                        PcmFos.flush();
                    }
                }
                mAudioRecord.stop();//停止录制
                PcmFos.close();//关流

            } catch (Exception e) {
                e.printStackTrace();

            }
            isRecord = false;
            //当录制完成就将Pcm编码数据转化为wav文件，也可以直接生成.wav
            PcmtoWav(PcmFile.getPath(),WavFile.getPath(),new byte[BufferSize]);
            Log.d(TAG, "录制结束");
        }

    }


    /**
     * 初始化目录
     */
    public static void init() {
        //文件目录
        AudioFolderFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                + File.separator + DIR_NAME;
        File WavDir = new File(AudioFolderFile);
        if (!WavDir.exists()) {
            boolean flag = WavDir.mkdirs();
            Log.e(TAG,"文件路径:"+AudioFolderFile+"创建结果:"+flag);
        } else {
            Log.e(TAG,"文件路径:"+AudioFolderFile+"创建结果: 已存在");
        }
    }

    /**
     * 将pcm文件转化为可点击播放的wav文件
     * @param inputPath pcm路径
     * @param outPath wav存放路径
     * @param data
     */
    private void PcmtoWav(String inputPath ,String outPath ,byte[] data){
        FileInputStream in;
        FileOutputStream out;
        try{
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outPath);
            //添加头部信息
            writeWavFileHeader(out,in.getChannel().size(),SAMPLE_RATE_HERTZ,CHANNEL_CONFIG);
            while(in.read(data)!= -1){
                out.write(data);
            }
            in.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @param out            wav音频文件流
     * @param totalAudioLen  不包括header的音频数据总长度
     * @param longSampleRate 采样率,也就是录制时使用的频率
     * @param channels       audioRecord的频道数量
     * @throws IOException 写文件错误
     */
    private void writeWavFileHeader(FileOutputStream out, long totalAudioLen, long longSampleRate,
                                    int channels) throws IOException {
        byte[] header = generateWavFileHeader(totalAudioLen, longSampleRate, channels);
        out.write(header, 0, header.length);

    }

    /**
     * 任何一种文件在头部添加相应的头文件才能够确定的表示这种文件的格式，
     * wave是RIFF文件结构，每一部分为一个chunk，其中有RIFF WAVE chunk，
     * FMT Chunk，Fact chunk,Data chunk,其中Fact chunk是可以选择的
     *
     * @param totalAudioLen  不包括header的音频数据总长度
     * @param longSampleRate 采样率,也就是录制时使用的频率
     * @param channels       audioRecord的频道数量
     */
    private byte[] generateWavFileHeader(long totalAudioLen, long longSampleRate, int channels) {
        long totalDataLen = totalAudioLen + 36;
        long byteRate = longSampleRate * 2 * channels;
        byte[] header = new byte[44];
        header[0] = 'R'; // RIFF
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);//数据大小
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';//WAVE
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        //FMT Chunk
        header[12] = 'f'; // 'fmt '
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';//过渡字节
        //数据大小
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        //编码方式 10H为PCM编码格式
        header[20] = 1; // format = 1
        header[21] = 0;
        //通道数
        header[22] = (byte) channels;
        header[23] = 0;
        //采样率，每个通道的播放速度
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        //音频数据传送速率,采样率*通道数*采样深度/8
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        // 确定系统一次要处理多少个这样字节的数据，确定缓冲区，通道数*采样位数
        header[32] = (byte) (2 * channels);
        header[33] = 0;
        //每个样本的数据位数
        header[34] = 16;
        header[35] = 0;
        //Data chunk
        header[36] = 'd';//data
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        return header;
    }
}

