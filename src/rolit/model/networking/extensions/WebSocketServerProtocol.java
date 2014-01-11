package rolit.model.networking.extensions;

import rolit.model.networking.ServerProtocol;

/**
 * @author Pieter Bos
 *
 * Abstract class voor het protocol van een rolit WebSocket server.
 *
 * Dit type server kan een extra commando ontvangen, GET. Deze moet als eerste worden verstuurd in plaats van de
 * handshake, of helemaal niet. Als dit commando wordt verstuurd wordt de connectie omgezet naar een HTTP-verbinding.
 * De server verwacht dan een "Upgrade: websocket" header en verder alle andere headers zoals gespecificeerd in
 * rfc #6455. Daarna reageert de server of met een 501 Not Implemented of een 101 Switching Protocols, afhankelijk van
 * of de request correct is. In het tweede geval wordt de connectie verheven naar een JSON-RPC 2.0 WebSocket server,
 * zoals hier gespecificeerd: http://www.jsonrpc.org/specification.
 * Elk commando is dan een JSON-RPC methode, met als argumenten gewoon de argumenten. Uitzondering is het chat-bericht,
 * die als één argument wordt verzonden. Daarnaast zijn de typen gewoon native JSON typen, namelijk strings, numbers en
 * booleans.
 *
 * Redenen voor dit protocol:
 * - Webstandaard die geïmplementeerd is in alle moderne browsers: IE10+, FF4+, Chrome14+, Safari5+, Opera11+ en deze
 *   browsers voor mobiele platforms.
 * - Heeft libraries voor bijna alle talen
 * - Is tekst-gebaseerd in het begin, zodat het redelijk vloeiend gaat tussen een WebSocket client en een gewone
 *   server.
 */
public abstract class WebSocketServerProtocol extends ServerProtocol {

}
