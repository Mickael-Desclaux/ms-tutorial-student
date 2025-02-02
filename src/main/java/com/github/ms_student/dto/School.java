package com.github.ms_student.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class School {

    private int id;
    private String schoolName;
    private String location;
    private String principalName;
}
