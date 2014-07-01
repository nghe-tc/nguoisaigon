package com.nguoisaigon.entity;

public class MusicDataInfo {
	
	private String playListId;
	private byte[] musicData;
	/**
	 * @return the playListId
	 */
	public String getPlayListId() {
		return playListId;
	}
	/**
	 * @param playListId the playListId to set
	 */
	public void setPlayListId(String playListId) {
		this.playListId = playListId;
	}
	/**
	 * @return the musicData
	 */
	public byte[] getMusicData() {
		return musicData;
	}
	/**
	 * @param musicData the musicData to set
	 */
	public void setMusicData(byte[] musicData) {
		this.musicData = musicData;
	}

}
