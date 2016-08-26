package com.example.nazarko.taskalarmmanager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by nazarko on 8/26/16.
 */
public class NotificationID {
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }
}
