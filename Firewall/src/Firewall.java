import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Queue;

public class Firewall {
    private String filepath;
    private HashMap<String, String> directions;
    private HashMap<String, String> protocols;
    private int portMin;
    private int portMax;
    private int [] ipMin;
    private int [] ipMax;
    private HashMap<String, IPAddress> ips;



    public Firewall(String filepath) {
        this.filepath = filepath;
        this.directions = new HashMap<>();
        this.protocols = new HashMap<>();
        this.ips = new HashMap<String, IPAddress>();

    }

    public void readCSV () {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line = br.readLine();
            while (line != null) {
                String [] input = line.split(",");
                generateIpRules(input[0], input[1], input[2], input[3]);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int [] convertIP(String ip) {
        String [] ipStringArr = ip.split("[.]");
        int [] ipIntArr = new int [ipStringArr.length];
        for (int i = 0; i < ipStringArr.length; i++) {
            ipIntArr[i] = Integer.parseInt(ipStringArr[i]);
        }
        return  ipIntArr;
    }

    public boolean generateIpRules(String direction, String protocol, String port, String ip) {
        if (ip.indexOf("-") == -1) {
            insertRule(direction, protocol, port, ip);
            return true;
        } else {
            String []ips = ip.split("-");
            int [] min = convertIP(ips[0]);
            int [] max = convertIP(ips[1]);
            boolean firstReached = (min[0] == max[0]);
            boolean secondReached = (min[1] == max[1]);
            boolean thirdReached = (min[2] == max[2]);
            boolean fourthReached = (min[3] == max[3]);
            boolean secondStartVal = false;
            boolean thirdStartVal = false;
            boolean fourthStartVal = false;
            for (int first = min[0]; first <= max[0]; first++) {
                firstReached = (first == max[0]);
                if (firstReached && secondReached && thirdReached && fourthReached) {
                    return true;
                }
                for (int second = (secondStartVal) ? 0 : min[1]; second <= 256; second++) {
                    secondReached = (second == max[1]);
                    if (firstReached && secondReached && thirdReached && fourthReached) {
                        return true;
                    }
                    for (int third = (thirdStartVal) ? 0 : min[2]; third <= 256; third++) {
                        thirdReached = (third == max[2]);
                        if (firstReached && secondReached && thirdReached && fourthReached) {
                            return true;
                        }
                        for (int fourth = (fourthStartVal) ? 0 : min[3]; fourth <= 256; fourth++) {
                            String str = first +"." +second +"." +third +"." +fourth;
                            //System.out.println(str +port +direction + protocol);
                            //IPAddress ipAddress = new IPAddress(new int [] {first, second, third, fourth});
                            insertRule(direction, protocol, port, str);
                            fourthReached = (fourth == max[3]);
                            if (firstReached && secondReached && thirdReached && fourthReached) {
                                return true;
                            }
                        }

                    }
                }
            }
        }
        return  false;
    }


    public void insertRule(String direction, String protocol, String port, String ip) {
        if (ips.containsKey(ip)) {
            IPAddress ipAddress = ips.get(ip);
            ipAddress.insertPorts(direction, protocol, port);
        } else {
            IPAddress ipAddress = new IPAddress(convertIP(ip));
            ipAddress.insertPorts(direction, protocol, port);
            ips.put(ip, ipAddress);
        }
    }

    public boolean accept_packet(String direction, String protocol, int port, String ip_address) {
        if (ips.containsKey(ip_address)) {
            IPAddress ipAddress = ips.get(ip_address);
            boolean result = ipAddress.checkRules(direction, protocol, port);
            //System.out.println(result);
            return result;
        }
        //System.out.println("false");
        return false;
    }

}
