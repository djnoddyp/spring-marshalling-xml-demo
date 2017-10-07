package pnodder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import pnodder.Application;
import pnodder.model.Breakfast;
import pnodder.model.Food;

import javax.xml.bind.Marshaller;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public Application application() {
        Application application = new Application();
        application.setMarshaller(jaxb2Marshaller());
        application.setUnmarshaller(jaxb2Marshaller());
        return application;
    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setClassesToBeBound(new Class[]{Breakfast.class, Food.class});
        jaxb2Marshaller.setMarshallerProperties(getMarshallerProperties());
        return jaxb2Marshaller;
    }

    private Map<String, Boolean> getMarshallerProperties() {
        Map<String, Boolean> props = new HashMap<>();
        props.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        return props;
    }

}
