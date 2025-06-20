package com.mysbproject.dto.grade;

import lombok.Getter;

@Getter
public class AddGradeResult {
    public AddGradeResult(String message){
        this.message = message;
    }
    String message;
}
