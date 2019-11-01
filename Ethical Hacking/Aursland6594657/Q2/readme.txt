Program is written in python2.7
Command to run program: python2.7 dnsSpoof.py

Address to ping: www.vg.no (big norwegian newspaper, nothing suspicious!)
Ping reply will come from facebook's IP: 157.240.8.35

The program starts by running some commands via subprocess that configure the iptables so that
we assign que number for packets being forwarded to and from the victim machine only.

It then starts the DNS-spoofing.

It starts by checking if there is a DNS response, if it is it accesses the qname in the DNSQR's. 
Then it checks if the qname is the same as the ping address (www.vg.no)
It then modifies rrname and rdata in DNSRR to the new values (www.facebook.com and IP 157.240.8.35).
Then it resets the length and checksum of IP and UDP packets before it sends the packet back to victim

When you cancel the program with ctrl-c it will flush the iptables and close the program
