JChat
=====

Java chat application over sockets. Also includes a built in MP3 player.

DISCLAIMER: It is still in beta and any bugs that are found and reported would be appreciated. Also, it is compiled
in Java version 1.6 so that is the minimal JRE version required to run this application.

How to use:

1. Open server which is located under the "JServer" directory as a Jar and then press start server after opening Jar.

It will be hosted on port 44444 and is pre-defined in code.

2. Then open a client and connect to server using the host IP address of the server computer and type in a username before
pressing connect. Ensure that the IP address is typed in correctly in the format "127.0.0.1" without the quotes before pressing Connect,
in order to host server on a computer
outside of LAN, please port forward port 44444 on host computer.

3. You are then connected, other users will be displayed in the JList to the right, select either "All", or if you would like to send
a private message, select their name only. Then type into the message field and press send or hit the Enter key.

IMPORTANT: In order to use the MP3 function of the client, you must keep the external libraries in the same folder as the jar.
