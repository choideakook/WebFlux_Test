package com.reactive.reactive.myApp;

import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class MyEntity {

    private Long id;
    private String name;

    public static MyEntity create(String name) {
        MyEntity myEntity = new MyEntity();
        myEntity.name = name;
        return myEntity;
    }
}
