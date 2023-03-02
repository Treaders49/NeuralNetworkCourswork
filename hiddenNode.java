import java.util.ArrayList;
public class hiddenNode {
	private int label;
	private ArrayList<inputNode> linkedNodes;
	private ArrayList<Double> linkedWeights;
	private double bias;
	private double weight;
	private double activationVal;
	private double error;
	
	public hiddenNode() {
		this.bias = (Math.round(Math.random() * 100)) / 100.0;
		this.weight = (Math.round(Math.random() * 100)) / 100.0;
		activationVal = 0;
		setError(0);
		linkedNodes = new ArrayList<inputNode>();
		linkedWeights = new ArrayList<Double>();
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
	
}
