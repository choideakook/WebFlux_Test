package com.reactive.reactive.myApp;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class MyEntity {

    @Id
    private Long id;
    private String name;

    public static MyEntity create(String name) {
        MyEntity myEntity = new MyEntity();
        myEntity.name = name;
        return myEntity;
    }
}
