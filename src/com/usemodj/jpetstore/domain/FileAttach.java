package com.usemodj.jpetstore.domain;

import java.io.Serializable;

public class FileAttach implements Serializable {
	private int attachId;
	private String itemId;
	private String filename;
	private String filepath;
	private String contentType;
	private float filesize;
	private String caption;
	
	public FileAttach(){
		
	}

	public int getAttachId() {
		return attachId;
	}

	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public float getFilesize() {
		return filesize;
	}

	public void setFilesize(float filesize) {
		this.filesize = filesize;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	
}
