package zc.twocolor.util;

import java.util.HashMap;

import zc.twocolor.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;



public class PlaySoundPool {
	
  private SoundPool soundPool;
  private HashMap<Integer,Integer> spMap;
  private Context context;
  
  public PlaySoundPool(Context context){
	  this.context = context;
	  soundPool = new SoundPool(2,AudioManager.STREAM_MUSIC,0);
	    spMap = new HashMap<Integer,Integer>();
	    spMap.put(1, soundPool.load(context, R.raw.btn_sound, 1));  //添加第一个声音，代号1
	    
	  //添加第二个声音，代号2
	    //spMap.put(2, soundPool.load(context, R.raw.btn_sound, 1));  
  }
  
  public void playSound(int sound){
		
  AudioManager am = (AudioManager)context.getSystemService(context.AUDIO_SERVICE);
  float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
  float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
  float volumnRatio = audioCurrentVolumn/audioMaxVolumn;
  
  //选择代号为    sound  的声音
  soundPool.play(spMap.get(sound), volumnRatio, volumnRatio, 1, 0, 1);
  
  }
  
  

	
	

}