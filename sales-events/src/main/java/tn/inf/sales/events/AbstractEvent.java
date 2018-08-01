package tn.inf.sales.events;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public abstract class AbstractEvent implements Serializable {

	private String id;
	
	private Date eventDate;

	public AbstractEvent() {
		this.id = UUID.randomUUID().toString();
		this.eventDate=new Date();
	}

	public String getId() {
		return id;
	}

}
