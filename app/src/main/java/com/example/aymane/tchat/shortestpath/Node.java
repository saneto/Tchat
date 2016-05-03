package com.example.aymane.tchat.shortestpath;

import org.mapsforge.core.model.LatLong;


public class Node extends LatLong {

	public long id;
	public String name;
	public int level;

	public Node(long id, String name, double longitude, double latitude,
			int level) {
		super(latitude,longitude);
		this.id = id;
		this.name = name;
		this.level = level;
	}
	
//	 @Override
//	  public int hashCode() {
//	    final int prime = 31;
//	    int result = 1;
//	    result = prime * result + ((id+"" == null) ? 0 : (id+"").hashCode());
//	    return result;
//	  }
	 
	 
	  
	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    Node other = (Node) obj;
	    if (id != other.id)
	      return false;
	    return true;
	  }
		public double longitude()
		{
			return this.longitude;

		}
	public LatLong getlatLong()
	{
		return new LatLong(this.latitude, this.longitude);
	}
	public double latitude()
	{
		return  this.latitude;
	}
//	  @Override
//	  public String toString() {
//	    return name;
//	  }
	
}
