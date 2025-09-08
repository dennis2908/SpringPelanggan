package com.example.live.log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LogData {

	@Id
	private String id;
	private String operation;
    private String table;
	private Date creationDate = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getTable() {
		return operation;
	}

	public void setTable(String operation) {
		this.table = table;
	}

	public Date getCreationDate() {
		return creationDate;
	}
}