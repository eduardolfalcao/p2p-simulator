package me.edufalcao.manager.model;

public class Request {
	
	private String requestId;
	private Peer consumer, provider;
	private int submitTime, runtime;
	
	private int lastAccountingTime;
	
	public Request(String requestId, Peer consumer, Peer provider,
			int submitTime, int runtime){
		this.requestId = requestId;
		this.consumer = consumer;
		this.provider = provider;
		this.submitTime = submitTime;
		this.runtime = runtime;
		lastAccountingTime = 0;
	}
	
	public String getRequestId() {
		return requestId;
	}
	
	public Peer getConsumer() {
		return consumer;
	}
	
	public Peer getProvider() {
		return provider;
	}
	
	public int getSubmitTime() {
		return submitTime;
	}
	
	public int getRuntime() {
		return runtime;
	}
	
	public int getLastAccountingTime() {
		return lastAccountingTime;
	}
	
	public void setLastAccountingTime(int lastAccountingTime) {
		this.lastAccountingTime = lastAccountingTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Request){
			Request other = (Request) obj;
			if(requestId.equals(other.getRequestId()))
				return true;
		}
		return false;
	}
	
}
