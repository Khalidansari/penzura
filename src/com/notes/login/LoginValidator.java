package com.notes.login;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class LoginValidator {
	
	static DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
	public static boolean isValid(String userName, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		
		boolean isValid = false;

		String digestInBase64FromUI = DatatypeConverter.printBase64Binary(MessageDigest.getInstance("SHA-256").digest(password.getBytes("UTF-8")));
		String digestInBase64FromDB = getPasswordDigest(userName);
		
		System.out.println("passwordDigest - from UI: " + digestInBase64FromUI);
		
		if(digestInBase64FromDB != null && digestInBase64FromDB.equals(digestInBase64FromUI)) {
			
			isValid = true;
		}
		
		return isValid;
	}
	
	public static String getPasswordDigest(String userName) {
			
		Entity userEntity;
		String passwordDigest = null;
		
		Filter userNameFilter =  new Query.FilterPredicate("userName", FilterOperator.EQUAL, userName);
		Query q = new Query("userTable").setFilter(userNameFilter);

		PreparedQuery pq = ds.prepare(q);
		userEntity = pq.asSingleEntity();

		if (userEntity != null) {

			passwordDigest = (String)userEntity.getProperty("PasswordDigest");
			System.out.println("passwordDigest - in database: " + passwordDigest);
		}
		
		return passwordDigest;
	}
}
