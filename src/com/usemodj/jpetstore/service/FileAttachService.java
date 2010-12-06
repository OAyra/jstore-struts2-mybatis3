package com.usemodj.jpetstore.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.FileAttach;
import com.usemodj.jpetstore.mappers.FileAttachMapper;

public class FileAttachService {
	private static Logger logger = Logger.getLogger(FileAttachService.class);

	public void insertFileAttach(SqlSession session, FileAttach attach) throws Exception {
		FileAttachMapper fMapper = session.getMapper(FileAttachMapper.class);
		fMapper.insertFileAttach( attach);
	}

	public List<FileAttach> selectItemFileList(SqlSession session, String itemId) throws Exception {
		FileAttachMapper fMapper = session.getMapper(FileAttachMapper.class);
		return fMapper.selectItemFileList( itemId);
	}

	public void deleteItemFiles(SqlSession session, String[] attachIds, String filePath) throws Exception{
		FileAttachMapper fMapper = session.getMapper(FileAttachMapper.class);
		List<FileAttach> attachList = fMapper.selectFileAttachIN( attachIds);
		try {
			for( FileAttach attach: attachList){
				//delete the files from disk
				File file = new File(filePath, attach.getFilepath());
				FileUtils.forceDelete(file);
				logger.info("-- File delete: " + file.getCanonicalPath());
			}
			//delete the file informations from database table
			fMapper.deleteFileAttachIN( attachIds);
		} catch (NullPointerException n){ //- if the directory is null
			logger.debug("NullPointerException: " + n.getMessage());
		} catch (FileNotFoundException f) { //- if the file was not found 
			logger.debug("FileNotFoundException: " + f.getMessage());
		} catch (IOException i){  //- in case deletion is unsuccessful
			logger.debug("Deletion is unsuccessful IOException: " + i.getMessage());
			session.rollback();
			throw new Exception(i.getMessage());
		} 
	}
	
	
}
