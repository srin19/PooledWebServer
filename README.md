# Multi threaded Pooled Web Server
A multi threaded web server with thread pooling.

# Request handling flow
1. Pooled Web Server receives a http request.
2. Pooled Web Server creates a Request Handler for this request.
2. Request Handler sends the request to Request class for parsing.
3. Request class parses the request.
4. Response class takes the request object from Request class and constructs the response.
5. Request Handler closes the output streams.

`PooledWebServer` - This class starts the server and listens for connections. It also delegates them to worker threads.

`RequestHandler` - handles a single request, by passing it to Request/Response classes.

`MonitorThread` - outputs pooled server status once every 10 seconds.


## Installation
`gradle build`
## Usage

PooledWebServer class contains main method. You can start the server by running the main method in PooledWebServer.
