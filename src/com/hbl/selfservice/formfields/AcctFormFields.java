package com.hbl.selfservice.formfields;

import java.util.Map;

import javax.servlet.http.HttpSession;

import com.hbl.provisioning.ProvisioningAccounts;

import oracle.iam.platform.OIMClient;

public class AcctFormFields {

	private String defaultValue = null;
	private String filedLookupName=null;
	public String getFiledLookupName() {
		return filedLookupName;
	}

	public void setFiledLookupName(String filedLookupName) {
		System.out.println("filedLookupName:: "+filedLookupName);
		this.filedLookupName = filedLookupName;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public AcctFormFields() {}
	public AcctFormFields(String FieldApiName,String fieldLabel, 
			String fieldType, Long fieldKey )
	{
		this.FieldApiName = FieldApiName;
		this.fieldLabel = fieldLabel;
		this.fieldType = fieldType;
		this.fieldKey = fieldKey;
	}
	private String FieldApiName = null;
	public String getFieldApiName() {
		return FieldApiName;
	}

	public void setFieldApiName(String fieldApiName) {
		FieldApiName = fieldApiName;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		if(fieldType.equals("TextField") ||fieldType.equals("DOField")  )
		{
			fieldType = "text";
		}
		else if(fieldType.equals("PasswordField"))
		{
			fieldType = "password";
		}
		else if(fieldType.equals("ITResourceLookupField"))
		{
			fieldType = "text";
		}
		else if(fieldType.equals("CheckBox"))
		{
			fieldType = "checkbox";
		}
		else if(fieldType.equals("LookupField") || fieldType.equals("ComboBox") )
		{
			fieldType = "lookup";
		}
		else if(fieldType.equals("TextArea"))
		{
			fieldType = "text";
		}
		
		this.fieldType = fieldType;
	}

	public Long getFieldKey() {
		return fieldKey;
	}

	public void setFieldKey(Long fieldKey) {
		this.fieldKey = fieldKey;
	}

	private String fieldLabel = null;
	private String fieldType = null;
	private Long fieldKey = null;
	 
	public String makeFieldHTML(OIMClient oimClient, ProvisioningAccounts pa)
	{
		String fieldHtml="Kashif";
		
		if(getFieldType().equals("checkbox"))
		{
			fieldHtml = "<div class='form-group col-md-6 '>"
					+"<div class='form-group col-md-3 rt'>"
					+"<label for='"+getFieldApiName()+"'>"+getFieldLabel()+"</label>"
					+"</div>"
					+"<div class='form-group col-md-8'>"
						+"<input type='"+getFieldType()+"' id='"+getFieldApiName()+"' name='"+getFieldApiName()+"'>"
					+"</div>"	
				+"</div>";
		} 
		else if(getFieldType().equals("lookup"))
		{
			String optionsValHTML ="";
			
			Map<String, String> lookupValues = pa.getLookupMap(getFiledLookupName(), oimClient);
			for(Map.Entry<String, String> lookupEntrySet: lookupValues.entrySet() )
			{
				optionsValHTML +="<option value='"+lookupEntrySet.getKey()+"'>"+lookupEntrySet.getValue()+"</option>"; 
				
			}
			fieldHtml = "<div class='form-group col-md-6 '>"
					+"<div class='form-group col-md-3 rt'>"
					+"<label for='"+getFieldApiName()+"'>"+getFieldLabel()+"</label>"
					+"</div>"
					+"<div class='form-group col-md-8'>"
						+"<input list='"+(getFieldApiName()+1)+"' class='form-control' id='"+getFieldApiName()+"' name='"+getFieldApiName()+"'/>"
						+"<datalist id='"+(getFieldApiName()+1)+"'>"
						+optionsValHTML
						+"</datalist>"
					+"</div>"	
				+"</div>";
		} 
		else
		{
			
			fieldHtml = "<div class='form-group col-md-6 '>"
					+"<div class='form-group col-md-3 rt'>"
					+"<label for='"+getFieldApiName()+"'>"+getFieldLabel()+"</label>"
					+"</div>"
					+"<div class='form-group col-md-8'>"
						+"<input type='text' class='form-control'  id='"+getFieldApiName()+"' name='"+getFieldApiName()+"'>"
					+"</div>"	
				+"</div>";
		} 
		
		return fieldHtml;
		 
	}
	public String makeHTMLNew(OIMClient oimClient, ProvisioningAccounts pa)
	{
		String fieldHTML ="";
		if(getFieldType().equals("checkbox"))
		{
			fieldHTML="<div class='form-group col-md-4'>"
			+"<label for='"+getFieldApiName()+"'>"+getFieldLabel()+"</label>"
			+"<input type='"+getFieldType()+"' id='"+getFieldApiName()+"' name='"+getFieldApiName()+"'>"
			+"</div>";
			
		}
		else if(getFieldType().equals("lookup"))
		{
			String optionsValHTML ="";
			
			Map<String, String> lookupValues = pa.getLookupMap(getFiledLookupName(), oimClient);
			for(Map.Entry<String, String> lookupEntrySet: lookupValues.entrySet() )
			{
				optionsValHTML +="<option value='"+lookupEntrySet.getKey()+"' label='"+lookupEntrySet.getValue()+"'>"+lookupEntrySet.getValue()+"</option>"; 
				
			}
			fieldHTML = "<div class='form-group col-md-4'>" + 
					"		 <label for='"+getFieldApiName()+"'>"+getFieldLabel()+"</label>\r\n" + 
					"		 <input list='"+(getFieldApiName()+1)+"' class='form-control' id='"+getFieldApiName()+"' name='"+getFieldApiName()+"'/>" + 
					"		 <datalist id='"+(getFieldApiName()+1)+"'>" + 
							 optionsValHTML +
					"		 </datalist>" + 
				 			 " </div>";
		} else
		{
			if(!(getFieldLabel().contains("Unique ID") || getFieldLabel().contains("Justification"))) {
			fieldHTML = "<div class='form-group col-md-4'>\r\n" + 
					"		 <label for='"+getFieldApiName()+"'>"+getFieldLabel()+"</label>\r\n" + 
					"		 <input type='text' class='form-control' id='"+getFieldApiName()+"' name='"+getFieldApiName()+"' placeholder='Enter Here' enabled>\r\n" + 
					"	 </div>";
			}
		}  
		
		
		 
				 
		
		return fieldHTML;
	}

}
