package me.edufalcao.manager.plugins.capacitycontroller.fairnessdriven;

public class HillClimbingAlgorithm {
	
	private boolean increasing;
	private double deltaC;
	private double minimumThreshold, maximumThreshold;
	private double maximumCapacityToSupply;
	
	private final double MAXIMUM_CAPACITY;
	
	public HillClimbingAlgorithm(double deltaC,
			double minimumThreshold, double maximumThreshold, double maximumCapacityOfPeer) {
		increasing = false;
		
		if(minimumThreshold < 0 || maximumThreshold < 0 || deltaC > 1 || deltaC < 0)
			throw new IllegalArgumentException("Unexpected argument for the FDController: "+this+"\n"
					+ "Any of this conditions were (but shouldn't be) satisfied: minimumThreshold < 0 || maximumThreshold < 0 || deltaC > 1 || deltaC < 0");
		this.deltaC = deltaC;
		this.minimumThreshold = minimumThreshold;
		this.maximumThreshold = maximumThreshold;
		this.maximumCapacityToSupply = maximumCapacityOfPeer;
		this.MAXIMUM_CAPACITY = maximumCapacityOfPeer;
	}
	
	@Override
	public String toString() {
		return "Params of HillClimbing Algorithm - minimumThreshold: "+minimumThreshold+", "
				+ "maximumThreshold: "+maximumThreshold+", deltaC: "+deltaC+".";
	}
	
	public double getMaxCapacityFromFairness(double lastFairness, double currentFairness){
		if(currentFairness < minimumThreshold)
			increasing = false;
		else if(currentFairness > maximumThreshold)
			increasing = true;
		else if(currentFairness <= lastFairness)
				increasing = !increasing;
		
		if(increasing)
			maximumCapacityToSupply = Math.min(MAXIMUM_CAPACITY, maximumCapacityToSupply + (deltaC * MAXIMUM_CAPACITY));
		else
			maximumCapacityToSupply = Math.max(0, maximumCapacityToSupply - (deltaC * MAXIMUM_CAPACITY));
		
		return maximumCapacityToSupply;				
	}
	
	public double getMaximumCapacityToSupply() {
		return maximumCapacityToSupply;
	}

}
