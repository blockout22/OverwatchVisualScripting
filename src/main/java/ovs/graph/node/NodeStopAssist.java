package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeStopAssist extends Node {

	PinVar pinAssistId = new PinVar();
	PinAction output = new PinAction();

	public NodeStopAssist(Graph graph) {
		super(graph);
		setName("StopAssist");

		 pinAssistId.setNode(this);
		 pinAssistId.setName("AssistId");
		 addCustomInput(pinAssistId);

		output.setNode(this);
		addCustomOutput(output);
	}

	public void execute() {
		PinData<ImString> assistIdData = pinAssistId.getData();
		PinData<ImString> outputData = output.getData();

		handlePinStringConnection(pinAssistId, assistIdData, "Last Assist ID");

		outputData.getValue().set(getName() + "(" + assistIdData.getValue().get() + ");");
	}

	public String getOutput() {
		PinData<ImString> outputData = output.getData();
		return outputData.getValue().get();
	}

	@Override
	public void UI() {

	}

	@Override
	public String getTooltip() {
		return "Stops an assist that was started by the start assist action.";
	}
}