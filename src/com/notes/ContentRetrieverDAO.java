package com.notes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class ContentRetrieverDAO {

	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
	public String getContent(String username) throws IOException {
		
		StringBuilder sbContent = new StringBuilder();
		sbContent.append(getSideBarContent(username));
		//String content = getSideBarContent(username);
		
		for (String tabname : getTabNames(username)) {
			
			//content = content + getTabContent(username, tabname);
			sbContent.append(getTabContent(username, tabname));
		}
		System.out.println("Content! " + sbContent.toString());
		//if(content.trim().equals("")) {
		if(sbContent.toString().trim().equals("")) {
				
			System.out.println("Get empty content!");
			
			sbContent = new StringBuilder().append(CommonUtils.getEmptyContent());
			//content = CommonUtils.getEmptyContent();
		}

		return sbContent.toString();
	}
	
	public ArrayList<String> getTabNames(String username){
		
		ArrayList<String> tabNames = new ArrayList<String>();
		Filter userNameFilter = new Query.FilterPredicate("username", FilterOperator.EQUAL, username);
		Query query = new Query("TabTable").setFilter(userNameFilter);
		PreparedQuery pQuery = ds.prepare(query);
		List<Entity> tabs = pQuery.asList(FetchOptions.Builder.withLimit(100));
		
		for(Entity tab : tabs) {
			
			tabNames.add((String)tab.getProperty("tabname"));
		}
		
		return tabNames;
	}
	
	public String getTabContent(String username, String tabname){
		
		String tabContent = "";

		Filter userNameFilter = new Query.FilterPredicate("username", FilterOperator.EQUAL, username);
		Filter tabNameFilter = new Query.FilterPredicate("tabname", FilterOperator.EQUAL, tabname);
		Filter whereClause = CompositeFilterOperator.and(userNameFilter, tabNameFilter);
		Query query = new Query("TabPartTable").setFilter(whereClause);
		query.addSort("partorder", SortDirection.ASCENDING);
		PreparedQuery pQuery = ds.prepare(query);
		List<Entity> tabparts = pQuery.asList(FetchOptions.Builder.withLimit(100));
		
		for(Entity tabpart : tabparts) {
			
			tabContent = tabContent + ((Text)tabpart.getProperty("partcontent")).getValue();
		}
		
		return tabContent;
	}
	
	public String getSideBarContent(String username) throws IOException {

		String sideBarContent = "";
		Filter userNameFilter =  new Query.FilterPredicate("username", FilterOperator.EQUAL, username);
		Query q = new Query("SideBarTable").setFilter(userNameFilter);
		PreparedQuery pq = ds.prepare(q);
		Entity sideBar = pq.asSingleEntity();
		
		if (sideBar != null) {

			sideBarContent = ((Text)sideBar.getProperty("content")).getValue();
		} 
		
		return sideBarContent;
	}
}
