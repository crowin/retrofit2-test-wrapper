package com.alexanders.retrotest.api.services.people;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author astolnikov: 03.07.2018
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class PeopleEntity implements Serializable {
    private String name;
    private String height;
    private String mass;
    private String hairColor;
    private String skinColor;
    private String eyeColor;
    private String birthYear;
    private String gender;
    private String homeworld;
    private List<String> films = null;
    private List<String> species = null;
    private List<String> vehicles = null;
    private List<String> starships = null;
    private String created;
    private String edited;
    private String url;
}
