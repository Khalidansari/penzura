package com.notes;

import java.util.List;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Text;

public class ContentSaverDAO {
	
	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
	public void saveHTMLContent(String infoBlock, String htmlContent, String username) {

		if(infoBlock.equals("sideBar")) {
			
			saveSideBar(username, htmlContent);
			
		} else {

			setContentByName(username, infoBlock, htmlContent);
		}
		
	}
	
	public void saveSideBar(String username, String htmlContent) {

		Entity sideBar;
		
		Filter userNameFilter =  new Query.FilterPredicate("username", FilterOperator.EQUAL, username);

		Query q = new Query("SideBarTable").setFilter(userNameFilter);

		PreparedQuery pq = ds.prepare(q);
		sideBar = pq.asSingleEntity();

		if (sideBar == null) {

			sideBar = new Entity("SideBarTable");
			sideBar.setProperty("username", username);
			sideBar.setProperty("content", new Text(htmlContent));

		} else {

			sideBar.setProperty("content", new Text(htmlContent));
		}
		
		ds.put(sideBar);
	}
	
	public void setContentByName(String username, String contentName, String content) {
		
		//Set TabTable
		Filter userNameFilter =  new Query.FilterPredicate("userName", FilterOperator.EQUAL, username);
		Filter tabNameFilter =  new Query.FilterPredicate("tabName", FilterOperator.EQUAL, contentName);
		Filter whereClause = CompositeFilterOperator.and(userNameFilter, tabNameFilter);
		Query q = new Query("TabTable").setFilter(whereClause);

		PreparedQuery pq = ds.prepare(q);
		Entity tab = pq.asSingleEntity();
		
		if (tab == null) {
			
			tab = new Entity("TabTable");
			tab.setProperty("userName", username);
			tab.setProperty("tabName", contentName);	
			ds.put(tab);
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
				tabContent.setProperty("userName", username);
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
			
			deleteExtraParts(username, contentName, currentTotalParts);
		}
	}
	
	public void deleteExtraParts(String username, String contentName, Integer currentTotalParts) {
		
		Filter userNameFilter =  new Query.FilterPredicate("userName", FilterOperator.EQUAL, username);
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
