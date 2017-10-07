package pnodder;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import pnodder.config.AppConfig;
import pnodder.model.Breakfast;
import pnodder.model.Food;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Application {

    private static final String INPUT_FILE = "./src/main/resources/xml/menu.xml";
    private static final String OUTPUT_FILE = "./menu-jaxb.xml";
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public void saveMenu(Breakfast breakfast) throws IOException {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(OUTPUT_FILE);
            marshaller.marshal(breakfast, new StreamResult(os));
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    public Breakfast loadMenu() throws IOException {
        FileInputStream is = null;
        Breakfast breakfast;
        try {
            is = new FileInputStream(INPUT_FILE);
            breakfast = (Breakfast) unmarshaller.unmarshal(new StreamSource(is));
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return breakfast;
    }

    public static void main(String[] args) throws IOException {
        Food food1 = new Food();
        food1.setName("Eggs");
        food1.setPrice(1.99);
        food1.setCalories(500);
        food1.setHealthy(true);

        Food food2 = new Food();
        food2.setName("Donuts");
        food2.setPrice(2.50);
        food2.setCalories(250);
        food2.setHealthy(false);

        Set<Food> foods = new HashSet<>();
        foods.add(food1);
        foods.add(food2);

        Breakfast breakfast = new Breakfast();
        breakfast.setFoods(foods);

        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Application application = (Application) appContext.getBean("application");

        // Marshall to XML
        application.saveMenu(breakfast);

        // Unmarshall to java objects
        breakfast = application.loadMenu();
        for (Food f : breakfast.getFoods()) {
            System.out.println("Got foods: " + f.getName());
        }
    }
}