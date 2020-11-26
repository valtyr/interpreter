import { ActivationState, CompatClient, Stomp } from "@stomp/stompjs";
import SockJS from "sockjs-client";

export const connect = (): Promise<CompatClient> =>
  new Promise((resolve, reject) => {
    const stompClient = Stomp.over(() => {
      return new SockJS("https://interpreter.finndu.vin/ws", undefined);
    });
    stompClient.reconnectDelay = 5000;
    stompClient.onStompError = (f) => {
      console.warn("error", f);
    };

    try {
      stompClient.connect({}, (frame: string) => {
        console.log("Connected: " + frame);
        resolve(stompClient);
      });
    } catch (error) {
      reject(error);
    }
  });
