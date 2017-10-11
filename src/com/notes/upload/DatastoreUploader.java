package com.notes.upload;

import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class DatastoreUploader {
	
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
	public void saveData(String userName, String contentName, String content) {
		
		System.out.println("detailed userName: " + userName + " contentName: " + contentName + " content: " + content);
		
		//Set TabTable
		Filter userNameFilter =  new Query.FilterPredicate("userName", FilterOperator.EQUAL, userName);
		Filter tabNameFilter =  new Query.FilterPredicate("tabName", FilterOperator.EQUAL, contentName);
		Filter whereClause = CompositeFilterOperator.and(userNameFilter, tabNameFilter);
		Query q = new Query("TabTable").setFilter(whereClause);

		PreparedQuery pq = ds.prepare(q);
		Entity tab = pq.asSingleEntity();
		
		if (tab == null) {
			
			tab = new Entity("TabTable");
			tab.setProperty("userName", userName);
			tab.setProperty("tabName", contentName);	
			ds.put(tab);
			System.out.println("------------------------Saving----------------------------");
		}
		
		Integer OneMB = 1000*1000;
		Integer currentTotalParts = (content.length()/OneMB);
		Integer previousTotalParts = ds.prepare(new Query("TabPartTable").setFilter(whereClause)).countEntities(FetchOptions.Builder.withDefaults());
		
		//Set TabPartTable
		for(int i = 0; i < currentTotalParts + 1; i++) {
			
			Integer startIndex = i * OneMB;
			Integer endIndex = (i+1) * OneMB;

			if (content.length() < endIndex) {
				
				endIndex = content.length();
			}
			
			Entity tabContent;
			Filter tabOrderFilter =  new Query.FilterPredicate("partOrder", FilterOperator.EQUAL, i);
			whereClause = CompositeFilterOperator.and(userNameFilter, tabNameFilter, tabOrderFilter);
			q = new Query("TabPartTable").setFilter(whereClause);
			pq = ds.prepare(q);
			tabContent = pq.asSingleEntity();
			
			if (tabContent == null) {
				
				tabContent = new Entity("TabPartTable");
				tabContent.setProperty("userName", userName);
				tabContent.setProperty("tabName", contentName);
				tabContent.setProperty("partOrder", i);
				tabContent.setProperty("partContent", new Text(content.substring(startIndex, endIndex)));
	
			} else {
				
				tabContent.setProperty("partContent", new Text(content.substring(startIndex, endIndex)));
			}
			
			ds.put(tabContent);
		}
		
		//Delete extra previous parts
		if (currentTotalParts < previousTotalParts) {
			
			deleteExtraParts(userName, contentName, currentTotalParts);
		}
	}
	
	public void deleteExtraParts(String userName, String contentName, Integer currentTotalParts) {
		
		Filter userNameFilter =  new Query.FilterPredicate("userName", FilterOperator.EQUAL, userName);
		Filter tabNameFilter =  new Query.FilterPredicate("contentName", FilterOperator.EQUAL, contentName);
		Filter extraPartFilter =  new Query.FilterPredicate("partOrder", FilterOperator.GREATER_THAN, currentTotalParts);
		Filter whereClause = CompositeFilterOperator.and(userNameFilter, tabNameFilter, extraPartFilter);
		Query q = new Query("TabPartTable").setFilter(whereClause);

		PreparedQuery pq = ds.prepare(q);
		List<Entity> extraParts = pq.asList(FetchOptions.Builder.withDefaults());
		
		for (Entity entity : extraParts) {
			
			ds.delete(entity.getKey());
		}
	}
}
