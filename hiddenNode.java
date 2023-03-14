import java.util.ArrayList;
public class hiddenNode {
	private int label;
	private ArrayList<inputNode> linkedNodes;
	private ArrayList<Double> linkedWeights;
	private double bias;
	private double weight;
	private double activationVal;
	private double error;
	private double initialBias;
	private double initialWeight;
	private ArrayList<Double> initialLinkedWeights;
	
	
	public hiddenNode(double nodeWeight, double nodeBias) {
		this.bias = nodeBias;
		this.initialBias = nodeBias;
		this.weight = nodeWeight;
		this.initialWeight = nodeWeight;
		activationVal = 0;
		setError(0);
		linkedNodes = new ArrayList<inputNode>();
		linkedWeights = new ArrayList<Double>();
		initialLinkedWeights = new ArrayList<Double>();
	}
	
	public ArrayList<inputNode> getLinkedNodes() {
		return linkedNodes;
	}
	public ArrayList<Double> getLinkedWeights() {
		return linkedWeights;
	}
	
	public void setLinkedWeights(ArrayList<Double> newWeights) {
		System.out.print("Old weights for input nodes: ");
		System.out.println(linkedWeights);
		linkedWeights = newWeights;
		System.out.print("New weights for input nodes: ");
		System.out.println(linkedWeights);
	}
	
	public double getBias() {
		return bias;
	}
	public void setBias(double newBias) {
		System.out.print("old bias: ");
		System.out.println(this.bias);
		this.bias = newBias;
		System.out.print("new bias: ");
		System.out.println(this.bias);
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double newWeight) {
		System.out.print("old weight: ");
		System.out.println(this.weight);
		this.weight = newWeight;
		System.out.print("new weight: ");
		System.out.println(this.weight);
	}

	public void addLinkedNode(inputNode inptN) {
		linkedNodes.add(inptN);
		
	}
	public void addLinkedWeight(double weight) {
		linkedWeights.add(weight);
	}
	
	public void addInitialLinkedWeight(double weight) {
		initialLinkedWeights.add(weight);
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	public double getActivationVal() {
		return activationVal;
	}

	public void setActivationVal(double activationVal) {
		this.activationVal = activationVal;
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public ArrayList<Double> getInitialLinkedWeights() {
		return initialLinkedWeights;
	}

	public void setInitialLinkedWeights(ArrayList<Double> initialLinkedWeights) {
		this.initialLinkedWeights = initialLinkedWeights;
	}

	public double getInitialWeight() {
		return initialWeight;
	}

	public void setInitialWeight(double initialWeight) {
		this.initialWeight = initialWeight;
	}

	public double getInitialBias() {
		return initialBias;
	}

	public void setInitialBias(double initialBias) {
		this.initialBias = initialBias;
	}
	
}
