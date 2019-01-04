package com.it_tao.smartlock.service.audio.handle;

public interface AudioHandle {
	
	
	public void start();
	public void pause();
	public void stop();
	public void newxt();
	public void restart();
	public void close();
	public void playAudio(String path);
	public void clearAudio(String path);

}
