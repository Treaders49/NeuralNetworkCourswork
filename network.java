import java.util.ArrayList;
public class network {
	private ArrayList<inputNode> inputNodes;
	private ArrayList<hiddenNode> hiddenNodes;
	private outputNode output;

	public network(outputNode outputNode) {
		inputNodes = new ArrayList<inputNode>();
		hiddenNodes = new ArrayList<hiddenNode>();
		this.setOutput(outputNode);
	}
	
	public ArrayList<hiddenNode> getHiddenNodes() {
		return hiddenNodes;
	}
	
	public void addHiddenNode(hiddenNode hNode) {
		hiddenNodes.add(hNode);
	}

	public void setHiddenNodes(ArrayList<hiddenNode> hiddenNodes) {
		this.hiddenNodes = hiddenNodes;
	}

	public ArrayList<inputNode> getInputNodes() {
		return inputNodes;
	}

	public void setInputNodes(ArrayList<inputNode> inputNodes) {
		this.inputNodes = inputNodes;
	}

	public void addInputNode(inputNode inode) {
		inputNodes.add(inode);
		
	}

	public void forwardPass() {
		System.out.println(this.getHiddenNodes().size());
		for (hiddenNode hiddenN : this.getHiddenNodes()) {
			int nodeIndx = 0;
			int activation = 0;
			while (hiddenN.getLinkedNodes().size() > nodeIndx) {
				inputNode iNode = this.getInputNodes().get(nodeIndx);
				activation += Math.round(iNode.getVal() * hiddenN.getLinkedWeights().get(nodeIndx));
				nodeIndx++;
			}
			activation+= hiddenN.getBias();
			
			hiddenN.setActivationVal(sigmoyd(activation));
		}
		
		
		double finalOutputVal = 0;
		for (hiddenNode hiddenN : this.getHiddenNodes()) {
			finalOutputVal+= hiddenN.getActivationVal() * hiddenN.getWeight();
		}
		finalOutputVal+= this.getOutput().getBias();
		this.getOutput().setEstimatedOutput(sigmoyd(finalOutputVal));
	}
	
	
	public void backwardPass(double learningParameter) {
		//calculate error delta for output node and each hidden node formula (correct - estimated) x estimated(1 - estimated)
		outputNode oNode = this.getOutput(); //receives the outputNode object associated with the neural network
		double outputError = (oNode.getRealOutput() - oNode.getEstimatedOutput()) * (oNode.getEstimatedOutput() * (1 - oNode.getEstimatedOutput()));
		System.out.print("output error:");System.out.println(outputError);
		oNode.setError(outputError);
		//calculate error for hidden nodes then correct weights an biases relating to that node
		System.out.println(this.getHiddenNodes().size());
		for (hiddenNode hiddenN : this.getHiddenNodes()) {
			System.out.println(hiddenN);
			double hiddenError = (hiddenN.getWeight() * oNode.getError()) * (hiddenN.getActivationVal() * (1 - hiddenN.getActivationVal()));
			hiddenN.setError(hiddenError);
			System.out.println(hiddenError);
			hiddenN.setWeight(hiddenN.getWeight() + (learningParameter * oNode.getError() * hiddenN.getActivationVal()));
			hiddenN.setBias(hiddenN.getBias() + (learningParameter * hiddenN.getError()));
			ArrayList<Double> newWeights =  new ArrayList<Double>();
			int inputNodeIndx = 0;
			while (inputNodeIndx < hiddenN.getLinkedWeights().size()) {
				inputNode iNode = hiddenN.getLinkedNodes().get(inputNodeIndx);
				double iWeight = hiddenN.getLinkedWeights().get(inputNodeIndx);
				double newWeight = iWeight + (learningParameter * hiddenN.getActivationVal() * iNode.getVal());
				newWeights.add(newWeight);
				inputNodeIndx++;
			}
			hiddenN.setLinkedWeights(newWeights);
		}
		
		
		
	}
	

	public static double sigmoyd(double exponent) {
		exponent *= -1;
		final double e = 2.71828;
		return 1/(1 + (Math.pow(e, exponent)));
	}

	public outputNode getOutput() {
		return output;
	}

	public void setOutput(outputNode output) {
		this.output = output;
	}

	
	
	
}
