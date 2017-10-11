package com.notes.serve;

import java.io.IOException;
import java.util.ArrayList;
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
import com.google.appengine.api.datastore.Query.SortDirection;

public class DatastoreServer {

	DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
	public String getContent(String username) throws IOException {
		
		StringBuilder sbContent = new StringBuilder();
		
		System.out.println("DatastoreServer.getContent() - Username - " + username);
		
		for (String tabname : getTabNames(username)) {
			
			System.out.println("DatastoreServer.getContent() - tabname - " + tabname);
			
			sbContent.append(getTabContent(username, tabname));
		}

		if(sbContent.toString().trim().equals("")) {
			
			sbContent = new StringBuilder().append(ServerUtils.getEmptyContent());
		}

		return sbContent.toString();
	}
	
	public ArrayList<String> getTabNames(String username){
		
		ArrayList<String> tabNames = new ArrayList<String>();
		Filter userNameFilter = new Query.FilterPredicate("userName", FilterOperator.EQUAL, username);
		Query query = new Query("TabTable").setFilter(userNameFilter);
		PreparedQuery pQuery = ds.prepare(query);
		List<Entity> tabs = pQuery.asList(FetchOptions.Builder.withLimit(100));
		
		for(Entity tab : tabs) {
			
			tabNames.add((String)tab.getProperty("tabName"));
		}
		
		return tabNames;
	}
	
	public String getTabContent(String username, String tabname){
		
		String tabContent = "";

		Filter userNameFilter = new Query.FilterPredicate("userName", FilterOperator.EQUAL, username);
		Filter tabNameFilter = new Query.FilterPredicate("tabName", FilterOperator.EQUAL, tabname);
		Filter whereClause = CompositeFilterOperator.and(userNameFilter, tabNameFilter);
		Query query = new Query("TabPartTable").setFilter(whereClause);
		query.addSort("partOrder", SortDirection.ASCENDING);
		PreparedQuery pQuery = ds.prepare(query);
		List<Entity> tabparts = pQuery.asList(FetchOptions.Builder.withLimit(100));
		
		for(Entity tabpart : tabparts) {
			
			tabContent = tabContent + ((Text)tabpart.getProperty("partContent")).getValue();
		}
		
		return tabContent;
	}
}