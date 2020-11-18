import { CompatClient, Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export const connect = (): Promise<CompatClient> =>
  new Promise((resolve, reject) => {
    const socket = new SockJS("http://localhost:8080/ws", undefined);
    const stompClient = Stomp.over(socket);
    try {
      stompClient.connect({}, (frame: string) => {
        console.log("Connected: " + frame);
        resolve(stompClient);
      });
    } catch (error) {
      reject(error);
    }
  });
