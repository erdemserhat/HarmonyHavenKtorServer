package com.erdemserhat.service.configurations

val staticMainRouteHtmlContent = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Harmony Haven Server</title>
                    <style>
                        body {
                            margin: 0;
                            font-family: 'Courier New', Courier, monospace;
                            background: black;
                            color: #00ff00;
                            display: flex;
                            flex-direction: column;
                            justify-content: center;
                            align-items: center;
                            height: 100vh;
                            overflow: hidden;
                        }

                        header {
                            font-size: 2em;
                            font-weight: bold;
                            margin-bottom: 20px;
                            color: #00ff00;
                            text-shadow: 0 0 5px #00ff00, 0 0 20px #00ff00;
                            text-align: center;
                        }

                        .terminal {
                            width: 90%;
                            max-width: 800px;
                            height: 60%;
                            background: #000000;
                            border: 1px solid #00ff00;
                            border-radius: 5px;
                            padding: 10px;
                            box-shadow: 0 0 20px #00ff00, 0 0 40px #00ff00;
                            overflow: hidden;
                            display: flex;
                            flex-direction: column;
                            justify-content: flex-start;
                        }

                        .logs {
                            flex: 2;
                            overflow-y: hidden;
                            display: flex;
                            flex-direction: column;
                            font-size: 1em;
                            line-height: 1.4em;
                        }

                        .status-panel {
                            flex: 1;
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            border-top: 1px solid #00ff00;
                            padding-top: 10px;
                            margin-top: 10px;
                            font-size: 0.9em;
                        }

                        .status-item {
                            display: flex;
                            flex-direction: column;
                            align-items: center;
                        }

                        .status-item span {
                            font-weight: bold;
                        }

                        .log {
                            opacity: 0;
                            animation: fadeIn 1s forwards, scrollUp 20s linear infinite;
                        }

                        @keyframes fadeIn {
                            0% { opacity: 0; }
                            100% { opacity: 1; }
                        }

                        footer {
                            margin-top: 10px;
                            font-size: 0.8em;
                            color: #8b949e;
                        }
                    </style>
                </head>
                <body>
                    <header>Harmony Haven Server</header>
                    <div class="terminal">
                        <div class="logs" id="logs">
                            ${generateInitialLogs()}
                        </div>
                        <div class="status-panel">
                            <div class="status-item">
                                <span id="cpu-usage">CPU: 15%</span>
                            </div>
                            <div class="status-item">
                                <span id="memory-usage">Memory: 32%</span>
                            </div>
                            <div class="status-item">
                                <span id="connections">Clients: 2</span>
                            </div>
                        </div>
                    </div>
                    <footer>&copy; 2024 Harmony Haven</footer>
                    <script>
                        const logsContainer = document.getElementById('logs');
                        const logLines = [
                            "Initializing Harmony Haven Server...",
                            "Connecting to database...",
                            "Database connected successfully.",
                            "WebSocket service started on port 8080.",
                            "Listening for client connections...",
                            "Client connected: 192.168.1.100",
                            "Received message: { action: 'status' }",
                            "Response sent: { status: 'OK' }",
                            "Routine maintenance complete.",
                            "No issues detected.",
                            "Server running smoothly."
                        ];

                        const cpuUsage = document.getElementById('cpu-usage');
                        const memoryUsage = document.getElementById('memory-usage');
                        const connections = document.getElementById('connections');

                        // Function to update logs
                        setInterval(() => {
                            const logLine = document.createElement('div');
                            logLine.className = 'log';
                            logLine.textContent = logLines[Math.floor(Math.random() * logLines.length)];
                            logsContainer.appendChild(logLine);

                            if (logsContainer.children.length > 10) {
                                logsContainer.removeChild(logsContainer.children[0]);
                            }
                        }, 1500);

                        // Function to update server status
                        setInterval(() => {
                            cpuUsage.textContent = "CPU: " + (Math.random() * 100).toFixed(2) + "%";
                            memoryUsage.textContent = "Memory: " + (Math.random() * 100).toFixed(2) + "%";
                            connections.textContent = "Clients: " + Math.floor(Math.random() * 20);
                        }, 2000);
                    </script>
                </body>
                </html>
                """