package com.hbl.approvalrequests;

public class StatusCode {
	
	public String statusCode(int code)
	{
		String message="";
		
		if (code==200)
		{
			message="The request is successfully completed";
		}
		else if (code==201)
		{
			message="The request has been fulfilled";
		}
		else if (code==400)
		{
			message="The request could not be processed because it contains missing or invalid information";
		}
		else if (code==401)
		{
			message="The request is not authorized";
		}
		else if (code==403)
		{
			message="The user cannot be authenticated";
		}
		else if (code==404)
		{
			message="The request includes a resource URI that does not exist";
		}
		else if (code==406)
		{
			message="The resource identified by this request is not capable of generating request";
		}
		else if (code==408)
		{
			message="The request timed out and could not be processed";
		}
		else if (code==409)
		{
			message="The request could not be processed because of a conflict in the request";
		}
		else if (code==410)
		{
			message="The entity no longer exists";
		}
		else if (code==415)
		{
			message="The media type is not supported";
		}
		else if (code==500)
		{
			message="The server encountered an unexpected condition that prevented it from fulfilling the request";
		}
		return message;
	}

	public String rejectStatusCode(int code)
	{
		String message="";
		
		if (code==200)
		{
			message="The request is rejected successfully";
		}
		else if (code==201)
		{
			message="The request has been fulfilled";
		}
		else if (code==400)
		{
			message="The request could not be processed because it contains missing or invalid information";
		}
		else if (code==401)
		{
			message="The request is not authorized";
		}
		else if (code==403)
		{
			message="The user cannot be authenticated";
		}
		else if (code==404)
		{
			message="The request includes a resource URI that does not exist";
		}
		else if (code==406)
		{
			message="The resource identified by this request is not capable of generating request";
		}
		else if (code==408)
		{
			message="The request timed out and could not be processed";
		}
		else if (code==409)
		{
			message="The request could not be processed because of a conflict in the request";
		}
		else if (code==410)
		{
			message="The entity no longer exists";
		}
		else if (code==415)
		{
			message="The media type is not supported";
		}
		else if (code==500)
		{
			message="The server encountered an unexpected condition that prevented it from fulfilling the request";
		}
		return message;
	}
	
	public String claimStatusCode(int code)
	{
		String message="";
		
		if (code==200)
		{
			message="The request is successfully claimed";
		}
		else if (code==201)
		{
			message="The request has been fulfilled";
		}
		else if (code==400)
		{
			message="The request could not be processed because it contains missing or invalid information";
		}
		else if (code==401)
		{
			message="The request is not authorized";
		}
		else if (code==403)
		{
			message="The user cannot be authenticated";
		}
		else if (code==404)
		{
			message="The request includes a resource URI that does not exist";
		}
		else if (code==406)
		{
			message="The resource identified by this request is not capable of generating request";
		}
		else if (code==408)
		{
			message="The request timed out and could not be processed";
		}
		else if (code==409)
		{
			message="The request could not be processed because of a conflict in the request";
		}
		else if (code==410)
		{
			message="The entity no longer exists";
		}
		else if (code==415)
		{
			message="The media type is not supported";
		}
		else if (code==500)
		{
			message="The server encountered an unexpected condition that prevented it from fulfilling the request";
		}
		return message;
	}
	
	public String reassignStatusCode(int code)
	{
		String message="";
		
		if (code==200)
		{
			message="The request is successfully re-assigned";
		}
		else if (code==201)
		{
			message="The request has been fulfilled";
		}
		else if (code==400)
		{
			message="The request could not be processed because it contains missing or invalid information";
		}
		else if (code==401)
		{
			message="The request is not authorized";
		}
		else if (code==403)
		{
			message="The user cannot be authenticated";
		}
		else if (code==404)
		{
			message="The request includes a resource URI that does not exist";
		}
		else if (code==406)
		{
			message="The resource identified by this request is not capable of generating request";
		}
		else if (code==408)
		{
			message="The request timed out and could not be processed";
		}
		else if (code==409)
		{
			message="The request could not be processed because of a conflict in the request";
		}
		else if (code==410)
		{
			message="The entity no longer exists";
		}
		else if (code==415)
		{
			message="The media type is not supported";
		}
		else if (code==500)
		{
			message="The server encountered an unexpected condition that prevented it from fulfilling the request";
		}
		return message;
	}
}

