package com.mobila.project.today.model;

import java.util.UUID;

class KeyGenerator {
    private KeyGenerator(){}

    static String getUniqueKey(){
        return UUID.randomUUID().toString();
    }
}
