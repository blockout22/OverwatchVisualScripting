package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeStartAssist extends Node {

	PinVar pinAssisters = new PinVar();
	PinVar pinTargets = new PinVar();
	PinCombo pinReevaluation = new PinCombo();
	PinAction output = new PinAction();

	public NodeStartAssist(Graph graph) {
		super(graph);
		setName("Start Assist");

		 pinAssisters.setNode(this);
		 pinAssisters.setName("Assisters");
		 addCustomInput(pinAssisters);

		 pinTargets.setNode(this);
		 pinTargets.setName("Targets");
		 addCustomInput(pinTargets);

		 pinReevaluation.setNode(this);
		 pinReevaluation.setName("Reevaluation");
		 addCustomInput(pinReevaluation);

		 pinReevaluation.getComboBox().addOption("Assisters And Targets");
		 pinReevaluation.getComboBox().addOption("None");
		 pinReevaluation.select(0);

		output.setNode(this);
		addCustomOutput(output);
	}

	public void execute() {
		PinData<ImString> assistersData = pinAssisters.getData();
		PinData<ImString> targetsData = pinTargets.getData();
		PinData<ImString> reevaluationData = pinReevaluation.getData();
		PinData<ImString> outputData = output.getData();

		handlePinStringConnection(pinAssisters, assistersData, "Event Player");
		handlePinStringConnection(pinTargets, targetsData);
		handlePinStringConnection(pinReevaluation, reevaluationData, reevaluationData.getValue().get());

		outputData.getValue().set(getName() + "(" + assistersData.getValue().get() + ", " + targetsData.getValue().get() + ", " + reevaluationData.getValue().get() + ");");
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
		return "Starts granting assist credit to one or more assisters when one or more targets are eliminated. a reference to this damage modification can be obtained from the last assist id value. this action will fail if too many assists have been started.";
	}
}