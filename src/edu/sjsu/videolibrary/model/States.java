package edu.sjsu.videolibrary.model;

public class States {

	private String [] states;
	
	public States () { 
		 String [] s = { "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FM", "FL", "GA", "HI", "ID", "IL", "IN",
				 "IA", "KS", "KY", "LA","ME", "MH", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", 
				 "NJ", "NM", "NY","NC", "ND", "OK", "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT","VI", 
				 "VA", "WA", "WV", "WI", "WY" };
		 this.states = s; 
	}
	
	public String[] getStates() {
		return states;
	}
	
}
