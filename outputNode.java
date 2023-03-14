
public class outputNode {
	
	private double bias;
	private double realOutput;
	private double estimatedOutput;
	private double error;
	private double initialBias;
	
	
	public outputNode(double realOutput, double bias) {
		this.bias = bias;
		this.initialBias = bias;
		error = 0;
		this.realOutput = realOutput;
	}
	public double getBias() {
		return bias;
	}
	public void setBias(double bias) {
		this.bias = bias;
	}
	public double getRealOutput() {
		return realOutput;
	}
	public void setRealOutput(double realOutput) {
		this.realOutput = realOutput;
	}
	public double getEstimatedOutput() {
		return estimatedOutput;
	}
	public void setEstimatedOutput(double estimatedOutput) {
		this.estimatedOutput = estimatedOutput;
	}
	public double getError() {
		return error;
	}
	public void setError(double error) {
		this.error = error;
	}
	public double getInitialBias() {
		return initialBias;
	}
	public void setInitialBias(double initialBias) {
		this.initialBias = initialBias;
	}
}
