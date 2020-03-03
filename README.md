# IllumioFirewall
Code for the Illumio Firewall interview question along with an explaination of the design

1. Design Choices - In designing a scalable solution for the firewall for 500,000+ entries, keeping the runtime of 
  accept_packet as low as possible was critical. Rather than check each of the 500,000 rules, the use of Objects and hashmaps 
  reduced the runtime significantly. The primary structure for the firewall was an IP Address. IP Addresses (Objects) have 
  ports and ports (Objects) have fields for rules including direction and protocol. When a new rule is parsed, ip addresses
  are generated for all IP's within range. If the IP address exists (checked against a hashmap of existing ip addresses), the
  existing IP object is pulled else a new one is created. If the IP address exists, the port, direction, and protocol are
  passed through to the port object that determines if the port exists for the given IP. If the port exists (also checked by
  a hashmap of ports), the port is pulled and a field/flag (boolean) for the direction and protocol is set to true; if the port
  does not exist, the port is generated and the field/flag is set to true before the port is placed in the hashmap of ports
  for the IP address Object. In checking if a packet is to be accepted, the code checks if the IP address exists in the hashmap
  of IP's; if it does, the code checks if the port exists in the IP address' hashmap of ports. If both exist, the field of the
  port object corresponding to the direction and protocol is checked and the result is returned. If any of the aforementioned
  conditions are not met, false is returned. The use of nested hashmaps in tandem with IP address and port objects drastically
  reduce the runtime of accept_packet since the only rule checks are O(1) (the entire check should be constant time); however, 
  the initialization of rules will take longer due to the time required to generate a large number of IP addresses and ports.
2. Refinements - If time permitted, a basic repl would be utilized to increase usability. The java application currently
   requires all accept_packet calls to be placed in the main class after the reading of the CSV. Additionally, there exists a
   redundant step in the generation of a rule between converting the integers to strings and back; with more time, the method
   could be modified to avoid redundancy.
3. Using the Code - All rules may be copied and pasted into the inputs.csv file. All accept_packet calls may be placed in the
   main class after the readCSV method has been executed.
