import { CompatClient, Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export const connect = (): Promise<CompatClient> =>
  new Promise((resolve, reject) => {
    const stompClient = Stomp.over(() => {
      return new SockJS("http://localhost:8080/ws", undefined);
    });
    try {
      stompClient.connect({}, (frame: string) => {
        console.log("Connected: " + frame);
        resolve(stompClient);
      });
    } catch (error) {
      reject(error);
    }
  });
