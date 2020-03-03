import java.util.HashMap;

public class IPAddress {
    private int [] ip;
    private HashMap<Integer, Port> ports;

    public IPAddress(int [] ip) {
        this.ip = ip;
        this.ports = new HashMap<Integer, Port>();
    }

    public void insertPorts(String direction, String protocol, String port) {
        if (port.indexOf("-") == -1) {
            int id = Integer.parseInt(port);
            generatePortsHelper(id, direction, protocol);
        } else {
            String [] range = port.split("-");
            int min = Integer.parseInt(range[0]);
            int max = Integer.parseInt(range[1]);
            for (int id = min; id <= max; id++) {
                generatePortsHelper(id, direction, protocol);
            }
        }
    }


    private void generatePortsHelper (int id, String direction, String protocol) {
        if (ports.containsKey(id)) {
            Port port = ports.get(id);
            updatePort(direction, protocol, port);
        } else {
            Port port = new Port(id);
            updatePort(direction, protocol, port);
            ports.put(id, port);
        }
    }

    private void updatePort (String direction, String protocol, Port port) {
        if ((direction.equals("inbound")) && (protocol.equals("tcp"))) {
            port.setInboundAndTcp(true);
        } else if ((direction.equals("inbound")) && (protocol.equals("udp"))) {
            port.setInboundAndUdp(true);
        } else if ((direction.equals("outbound")) && (protocol.equals("tcp"))) {
            port.setOutboundAndTcp(true);
        } else if ((direction.equals("outbound")) && (protocol.equals("udp"))) {
            port.setOutboundAndUdp(true);
        }
    }

    public boolean checkRules(String direction, String protocol, int port) {
        if (ports.containsKey(port)) {
            Port portObject = ports.get(port);
            return portObject.checkRules(direction, protocol);
        }
        return false;
    }

}
