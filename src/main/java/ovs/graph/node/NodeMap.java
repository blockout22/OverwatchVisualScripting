package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodeMap extends Node{

    private PinVar output = new PinVar();

    private ComboBox selectedMap = new ComboBox();

    public NodeMap(Graph graph) {
        super(graph);
        setName("Map");

        //Assault Maps
        selectedMap.addOption("Hanamura");
        selectedMap.addOption("Hanamura Winter");
        selectedMap.addOption("Horizon Lunar Colony");
        selectedMap.addOption("Paris");
        selectedMap.addOption("Temple Of Anubis");
        selectedMap.addOption("Volskaya Industries");

        //Bounty Hunter Maps
        selectedMap.addOption("Black Forest");
        selectedMap.addOption("Black Forest Winter");
        selectedMap.addOption("Blizzard World");
        selectedMap.addOption("Blizzard World Winter");
        selectedMap.addOption("Castillo");
        selectedMap.addOption("Château Guillard");
        selectedMap.addOption("Château Guillard Halloween");
        selectedMap.addOption("Dorado");
        selectedMap.addOption("EcoPoint: Antarctica");
        selectedMap.addOption("Eichenwalde");
        selectedMap.addOption("Eichenwalde Halloween");
        selectedMap.addOption("Havana");
        selectedMap.addOption("Hollywood");
        selectedMap.addOption("Ilios Ruins");
        selectedMap.addOption("Ilois Well");
        selectedMap.addOption("Kanezeka");
        selectedMap.addOption("King's Row");
        selectedMap.addOption("King's Row Winter");
        selectedMap.addOption("Lijiang Control Center");
        selectedMap.addOption("Lijiang Control Center Lunar New Year");
        selectedMap.addOption("Lijiang Garden");
        selectedMap.addOption("Lijiang Garden Lunar New Year");
        selectedMap.addOption("Lijiang Night Market");
        selectedMap.addOption("Lijiang Night Market Lunar New Year");
        selectedMap.addOption("Malevento");
        selectedMap.addOption("Necropolis");
        selectedMap.addOption("Nepal Sanctum");
        selectedMap.addOption("Nepal Shrine");
        selectedMap.addOption("Nepal Village");
        selectedMap.addOption("Oasis City Center");
        selectedMap.addOption("Oasis Gardens");
        selectedMap.addOption("Oasis University");
        selectedMap.addOption("Petra");
        selectedMap.addOption("Workshop Chamber");
        selectedMap.addOption("Workshop Expanse");
        selectedMap.addOption("Workshop Expanse Night");
        selectedMap.addOption("Workshop Green Screen");
        selectedMap.addOption("Workshop Island");
        selectedMap.addOption("Workshop Island Night");

        //CTF Maps
        selectedMap.addOption("Ayutthaya");
        selectedMap.addOption("Busan Downtown Lunar New Year");
        selectedMap.addOption("Busan Sanctuary Lunar New Year");

        //Control
        selectedMap.addOption("Busan");
        selectedMap.addOption("Ilios");
        selectedMap.addOption("Lijiang Tower");
        selectedMap.addOption("Lijiang Tower Winter");
        selectedMap.addOption("Nepal");
        selectedMap.addOption("Oasis");

        //Deathmatch
        selectedMap.addOption("Route 66");

        //Escort
        selectedMap.addOption("Circuit Royal");
        selectedMap.addOption("Junkertown");
        selectedMap.addOption("Rialto");
        selectedMap.addOption("Watchpoint: Gibraltar");

        // Hybrid
        selectedMap.addOption("Midtown");
        selectedMap.addOption("Numbani");
        selectedMap.addOption("Paraíso");

        //Push
        selectedMap.addOption("Colosseo");
        selectedMap.addOption("Esperança");
        selectedMap.addOption("New Queen Street");

        selectedMap.sort();


        selectedMap.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Map:" + selectedMap.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Map")){
                selectedMap.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> data = output.getData();
        data.getValue().set("Map(" + selectedMap.getSelectedValue() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> data = output.getData();
        return data.getValue().get();
    }

    @Override
    public void UI() {
        selectedMap.show();
    }
}
