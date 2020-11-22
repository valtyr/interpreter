import { Middleware } from "@reduxjs/toolkit";
import { RootState } from "../store";
import { Actions, ActionTypes } from "../store/actions";
import { notification } from "antd";

const notificationMiddleware: Middleware<{}, RootState> = (store) => (next) => (
  action: Actions
) => {
  switch (action.type) {
    case ActionTypes.ErrorRecieved: {
      const { message, title } = action.payload;
      notification.error({
        message: title,
        description: message,
      });
      return next(action);
    }
    default:
      return next(action);
  }
  next(action);
};

export default notificationMiddleware;
