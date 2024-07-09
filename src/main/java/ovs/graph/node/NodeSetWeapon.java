package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetWeapon extends Node {

	PinVar pinPlayer = new PinVar();
	PinVar pinWeapon = new PinVar();
	PinAction output = new PinAction();

	public NodeSetWeapon(Graph graph) {
		super(graph);
		setName("Set Weapon");

		 pinPlayer.setNode(this);
		 pinPlayer.setName("Player");
		 addCustomInput(pinPlayer);

		 pinWeapon.setNode(this);
		 pinWeapon.setName("Weapon");
		 addCustomInput(pinWeapon);

		output.setNode(this);
		addCustomOutput(output);
	}

	public void execute() {
		PinData<ImString> playerData = pinPlayer.getData();
		PinData<ImString> weaponData = pinWeapon.getData();
		PinData<ImString> outputData = output.getData();

		handlePinStringConnection(pinPlayer, playerData, "Event Player");
		handlePinStringConnection(pinWeapon, weaponData, "1");

		outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + weaponData.getValue().get() + ");");
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
		return "Sets the weapon of one or more players.";
	}
}