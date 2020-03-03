public class Main {

    public static void main(String[] args) {
        String filepath = "./inputs.csv";
        Firewall fw = new Firewall(filepath);
        fw.readCSV();
        //firewall.displayIPRange("192.168.1.99-192.168.1.101");
        fw.accept_packet("inbound", "tcp", 80, "192.168.1.2");
        fw.accept_packet("inbound", "udp", 53, "192.168.2.1");
        fw.accept_packet("outbound", "tcp", 10234, "192.168.10.11");
        fw.accept_packet("inbound", "tcp", 81, "192.168.1.2");
        fw.accept_packet("inbound", "udp", 24, "52.12.48.92");
    }
}
