package Sunday_CC_Week35_SnakesAndLadders_SpringApp.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Player {
    private String id;
    private String name;
    private int position;
}