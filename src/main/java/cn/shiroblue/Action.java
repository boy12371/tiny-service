package cn.shiroblue;

import cn.shiroblue.exceptions.HaltException;


public class Action {

    private Action() {
    }

    public static void halt() {
        throw new HaltException();
    }

    public static void halt(int status) {
        throw new HaltException(status);
    }

    public static void halt(String body) {
        throw new HaltException(body);
    }

    public static void halt(int status, String body) {
        throw new HaltException(status, body);
    }
}
