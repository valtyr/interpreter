import { Middleware } from "@reduxjs/toolkit";
import { CompatClient, IMessage } from "@stomp/stompjs";
import { RootState } from "../store";
import { Actions, ActionTypes } from "../store/actions";
import { connect } from "../ws";

type Store = Parameters<Middleware<{}, RootState>>[0];

const socketMiddleware = (): Middleware<{}, RootState> => {
  let socket: CompatClient | null = null;

  const onClose = (store: Store) => () => {
    store.dispatch({
      type: ActionTypes.Disconnected,
    });
  };

  const onMessage = (store: Store) => (message: IMessage) => {
    const action = JSON.parse(message.body) as Actions;
    store.dispatch(action);
  };

  const sendMessage = (action: Actions) =>
    socket?.send("/app/message", undefined, JSON.stringify(action));

  return (store) => (next) => (action: Actions) => {
    switch (action.type) {
      case ActionTypes.Connect: {
        if (socket) return;
        (async () => {
          socket = await connect();

          socket.subscribe("/topic/messages", onMessage(store));
          socket.subscribe("/user/topic/messages", onMessage(store));
          socket.onWebSocketClose(onClose(store));

          store.dispatch({ type: ActionTypes.Connected });
        })();
        break;
      }
      case ActionTypes.SetUsername:
      case ActionTypes.Ping:
      case ActionTypes.JoinGame:
      case ActionTypes.CreateGame:
      case ActionTypes.AddWord:
      case ActionTypes.DeleteWord:
      case ActionTypes.StartGame:
      case ActionTypes.StartRound:
      case ActionTypes.StartTurn:
      case ActionTypes.EndTurn:
      case ActionTypes.Correct:
      case ActionTypes.Skip:
      case ActionTypes.PublishCardgroup:
        sendMessage(action);
        break;
      default:
        return next(action);
    }
    next(action);
  };
};

export default socketMiddleware();
