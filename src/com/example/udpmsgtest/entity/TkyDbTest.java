package com.example.udpmsgtest.entity;

import com.example.udpmsgtest.BaseEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName="TkyDbTest")
public class TkyDbTest extends BaseEntity{

	private static final long serialVersionUID = 1L;
	@DatabaseField(id=true)
	private String id;
	@DatabaseField
	private String FileName;
	@DatabaseField
    private String FilePath;
	@DatabaseField
    private String FileClass;
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFilePath() {
		return FilePath;
	}
	public void setFilePath(String filePath) {
		FilePath = filePath;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileClass() {
		return FileClass;
	}
	public void setFileClass(String fileClass) {
		FileClass = fileClass;
	}
}