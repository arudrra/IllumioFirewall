public class Port {
    private int id;
    private boolean inboundAndTcp, inboundAndUdp, outboundAndTcp, outboundAndUdp;

    public Port (int id) {
        this.id = id;
        this.inboundAndTcp = false;
        this.inboundAndUdp = false;
        this.outboundAndTcp = false;
        this.outboundAndUdp = false;
    }

    public boolean isInboundAndTcp() {
        return inboundAndTcp;
    }

    public boolean isInboundAndUdp() {
        return inboundAndUdp;
    }

    public boolean isOutboundAndTcp() {
        return outboundAndTcp;
    }

    public boolean isOutboundAndUdp() {
        return outboundAndUdp;
    }

    public void setInboundAndTcp(boolean inboundAndTcp) {
        this.inboundAndTcp = inboundAndTcp;
    }

    public void setInboundAndUdp(boolean inboundAndUdp) {
        this.inboundAndUdp = inboundAndUdp;
    }

    public void setOutboundAndTcp(boolean outboundAndTcp) {
        this.outboundAndTcp = outboundAndTcp;
    }

    public void setOutboundAndUdp(boolean outboundAndUdp) {
        this.outboundAndUdp = outboundAndUdp;
    }

    public boolean checkRules(String direction, String protocol) {
        if ((direction.equals("inbound")) && (protocol.equals("tcp"))) {
            return this.inboundAndTcp;
        } else if ((direction.equals("inbound")) && (protocol.equals("udp"))) {
            return this.inboundAndUdp;
        } else if ((direction.equals("outbound")) && (protocol.equals("tcp"))) {
            return this.outboundAndTcp;
        } else if ((direction.equals("outbound")) && (protocol.equals("udp"))) {
            return this.outboundAndUdp;
        }
        return false;
    }
}
