package pnodder.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement
public class Breakfast {

    private Set<Food> foods;

    public Set<Food> getFoods() {
        return foods;
    }

    @XmlElement(name = "food")
    public void setFoods(Set<Food> foods) {
        this.foods = foods;
    }
}
