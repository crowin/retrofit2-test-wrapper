package model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author astolnikov: 12.04.2018
 */
@Getter @Setter @ToString @AllArgsConstructor
public class ErrorMessage {
    private String message;
}
