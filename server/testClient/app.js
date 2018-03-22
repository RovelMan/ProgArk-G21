"use strict";

var socket = io.connect('http://localhost:7676');

socket.on('connect', function() {
  console.log('yay');
  socket.emit('test', 'heiheihei');
});

socket.on('pos', function(data){
  console.log(data);
  if ((Date.now() - recentTimestamp) >= 60000) {
    recentTimestamp = Date.now();
    recentCheckouts = [];
  }
  render(data);
});

socket.on('disconnect', function(){
  console.log("Disconnected");
});

socket.on('connectionResponse', function(data){
  console.log(data);
});