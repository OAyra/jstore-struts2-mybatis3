package com.usemodj.jpetstore.mappers;

import java.util.List;

import com.usemodj.jpetstore.domain.FileAttach;

public interface FileAttachMapper {

	void insertFileAttach(FileAttach attach) throws Exception;

	List<FileAttach> selectItemFileList(String itemId) throws Exception;

	void deleteFileAttachIN(String[] attachIds) throws Exception;

	List<FileAttach> selectFileAttachIN(String[] attachIds) throws Exception;

}
