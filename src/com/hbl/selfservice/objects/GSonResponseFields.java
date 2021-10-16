package com.hbl.selfservice.objects;

import java.io.Serializable;

public class GSonResponseFields implements Serializable{
	public GSonResponseFields(String fieldLabel, String fieldName, String fieldValue )
	{
		this.fieldLabel =fieldLabel;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	private String fieldLabel = null;
	public String getFieldLabel() {
		return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	private String fieldName = null;
	private String fieldValue = null;
}
