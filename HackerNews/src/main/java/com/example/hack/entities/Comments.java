package com.example.hack.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comments {

    private long id;
    private String by;
    private String text;

    private List kids;

}
