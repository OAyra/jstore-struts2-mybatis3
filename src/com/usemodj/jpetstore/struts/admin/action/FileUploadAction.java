package com.usemodj.jpetstore.struts.admin.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.FileAttach;
import com.usemodj.jpetstore.domain.Item;
import com.usemodj.jpetstore.service.FileAttachService;
import com.usemodj.struts.Constants;
import com.usemodj.struts.ImageUtils;
import com.usemodj.struts.SHA1Utils;
import com.usemodj.struts.action.AdminAction;

public class FileUploadAction extends AdminAction {
	private static Logger logger = Logger.getLogger( FileUploadAction.class);
	
    private File upload;//The actual file
    private String uploadContentType; //The content type of the file
    private String uploadFileName; //The uploaded file name
    private String caption;//The caption of the file entered by user
    private Item  item = new Item();
    protected FileAttachService attachService = new FileAttachService();
    private List<FileAttach> itemFileList = null;
    
	public String execute() {
		SqlSession session = null;
		try {
			String[] scales = this.request.getParameterValues("scale");
			
			String realPath = context.getRealPath("/");
			logger.debug("Server path:" + realPath);
			logger.debug("uploadContentType: "+ uploadContentType);
			logger.debug("uploadFileName: "+ uploadFileName);
			logger.debug("caption: "+ caption);
			logger.debug("upload.getCanonicalPath: "+ upload.getCanonicalPath());
			logger.debug("contentLength:" + request.getContentLength());
			FileAttach attach = new FileAttach();
			attach.setItemId(item.getItemId());
			attach.setCaption(caption);
			attach.setContentType(uploadContentType);
			attach.setFilename(uploadFileName);
			attach.setFilesize( request.getContentLength());
			attach.setFilepath( uploadPath(this.item.getItemId(),this.uploadFileName));

			File fileToCreate = new File(realPath, attach.getFilepath());
			//file is saved to disk
			FileUtils.copyFile(this.upload, fileToCreate);
			logger.debug(" fileToCreate:"+ fileToCreate.getCanonicalPath());
			session = this.getSqlSessionFactory().openSession();
			//save to database
			attachService.insertFileAttach( session, attach);
			logger.debug("fileToCreate.getName(): "+ fileToCreate.getName());
			if( isImage( fileToCreate.getName()) && null != scales){
				String filePath = attach.getFilepath();
				for(int i=0; i< scales.length; i++){
					logger.debug("scale["+i + "]: "+ scales[i]);
					String[] xyscale =scales[i].split("x");
					if(null != xyscale){
						logger.debug("scale x:"+ xyscale[0] + ", y:"+ xyscale[1]);
						int width = Integer.parseInt(xyscale[0]);
						int height = Integer.parseInt(xyscale[1]);
						attach.setFilepath( filePath);
						createSaveThumb( attach, realPath, width, height);
						attachService.insertFileAttach( session, attach);
					}
				}
			}
			
			session.commit();
		} catch (IOException e) {
			logger.error("--FileUploadAction.execute() IOException:"+ e.getMessage());
			this.addActionError( e.getMessage());
			session.rollback();
		} catch (Exception e) {
			logger.error("--FileUploadAction.execute() Exception:"+ e.getMessage());
			this.addActionError( e.getMessage());
			e.printStackTrace();
		} finally {
			session.close();
		}
		    	
    	return Constants.REDIRECT;
    }
    public String input(){
    	
    	return INPUT;
    }
    public String itemFileList(){
    	SqlSession session = null;
    	try {
			session = this.getSqlSessionFactory().openSession();
			this.itemFileList = attachService.selectItemFileList( session, this.item.getItemId());
			return Constants.LIST;
		} catch (Exception e) {
			this.addActionError( e.getMessage());
			logger.error( "FileUploadAtion.itemFiles() Exception:"+ e.getMessage());
		} finally {
			session.close();
		}
		return Constants.LIST;
    }
    
    protected String uploadPath(String itemId, String filename){
    	String name = filename.substring(0, filename.lastIndexOf("."));
    	String extension = filename.substring(filename.lastIndexOf(".")+1);
    	try {
			name = SHA1Utils.SHA1(name);
		} catch (NoSuchAlgorithmException e) {
			name = String.valueOf((long)name.hashCode());
			logger.error( "-- SHA1Utils.SHA1() NoSuchAlgorithmException:"+ e.getMessage());
		} catch (UnsupportedEncodingException e) {
			name = String.valueOf((long)name.hashCode());
			logger.error( "-- SHA1Utils.SHA1() UnsupportedEncodingException:"+ e.getMessage());
		}
    	String filepath = "upload/"+itemId +"/"+ name +"."+ extension;
    	return filepath;
    }
	protected boolean isImage(String filename) {
    	String extension = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
		
		return ("jpg".equals(extension) || "jpeg".equals(extension) 
				|| "gif".equals(extension) || "png".equals(extension));
	}
	/**
	 * 
	 * @param imgName
	 * @param width
	 * @param height
	 * @return  thumbnail file path
	 * @throws Exception
	 */
	protected void createSaveThumb( FileAttach attach, String realPath, int width, int height) throws Exception {
		String filePath =attach.getFilepath();
		BufferedImage image = ImageUtils.resizeImage( realPath+ filePath, ImageUtils.IMAGE_JPEG, 
			width,height);
		String thumPath = new StringBuffer(filePath.substring(0, filePath.lastIndexOf(".")))
		.append("_").append( width).append("x").append(height)
		.append( filePath.substring( filePath.lastIndexOf("."))).toString();
		if(!ImageUtils.saveImage(image, realPath+thumPath, ImageUtils.IMAGE_JPEG)){
			throw new Exception("ImageUtils.saveImage() Exception");
		}
		
		attach.setFilepath(thumPath);
		attach.setFilesize(new File(realPath+thumPath).length());
	}
    
    //--- getter/setter ---
    
	public List<FileAttach> getItemFileList() {
		return itemFileList;
	}
	public void setItemFileList(List<FileAttach> itemFileList) {
		this.itemFileList = itemFileList;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
    public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}

}
