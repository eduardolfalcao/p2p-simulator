package me.edufalcao.manager.model.events.request;

import me.edufalcao.manager.model.events.Event;

public class RequestEvent extends Event {
	
	private String peerId, requestId;
	private int runtime;
	
	public RequestEvent(String peerId, int initialTime, int runtime, String requestId) {
		super(initialTime);
		this.peerId = peerId;
		this.runtime = runtime;
		this.requestId = requestId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
