package com.example.hack.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stories {

    private long id;
    private String title;

    private String url;
    private long score;

    private Timestamp time;

    private String by;

    private List kids;

}
