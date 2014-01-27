import os
import sys
import socket

if len(sys.argv) == 3:
    HOST = sys.argv[1]
    PORT = int(sys.argv[2])
else:
    HOST = "localhost"
    PORT = 1234

def is_test(filename):
    return filename[-5:] == ".test"

def connect():
    sock = socket.socket()
    sock.connect((HOST, PORT))
    return sock.makefile()

def do_test(name, test):
    print "Running test {}...".format(name),
    lines = [ line.strip().split(" ") for line in test.split("\n") if line != "" and line[0] != "#" ]
    clients = { line[0] for line in lines }
    connections = { client: connect() for client in clients }
    
    for line in lines:
        if line[1] == ">":
            s = " ".join(line[2:])
            connections[line[0]].write(s + "\r\n")
            connections[line[0]].flush()
        else:
            received = connections[line[0]].readline().strip().split(" ")
            expected = line[2:]

            if len(received) != len(expected):
                print "fail:"
                print "Length of packets is not equal."
                print "Received:", " ".join(received)
                print "Expected:", " ".join(expected)
                return

            for received_arg, expected_arg in zip(received, expected):
                if expected_arg == "*":
                    continue

                if received_arg != expected_arg:
                    print "fail:"
                    print "Arguments do not match."
                    print "Received:", " ".join(received)
                    print "Expected:", " ".join(expected)
                    return

    print "pass."

def main():
    tests = set(filter(is_test, os.listdir(".")))

    for i, test in enumerate(tests):
        do_test(test[:-5], open(test).read())

if __name__ == "__main__":
    main()