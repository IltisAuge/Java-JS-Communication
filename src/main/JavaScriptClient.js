var socket = null;

function connect(ip, port) {
	if (port == null) port = "";
	socket = new WebSocket("ws://" + ip + ":" + port);
	socket.onopen = event => onConnected(event);
	socket.onclose = event => onDisconnected(event);
	socket.onmessage = event => onMessage(event);
	socket.onerror = event => onError(event);
}

function onConnected(event) {
	console.log("Connected to WebSocketServer on " + socket.url + ".")
}

function onDisconnected(event) {
 console.log("Disconnected from WebSocketServer.");
}

function onMessage(event) {
	console.log("Message received from WebSocketServer: " + event.data);
}

function onError(event) {
	console.log("Error: " + event);
}
