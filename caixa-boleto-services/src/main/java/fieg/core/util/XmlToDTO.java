package fieg.core.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;

public class XmlToDTO {

    public static  <T> T transformeDTO(String xml, Class<T> clazz) {
        try {
            StringReader sr = new StringReader(xml);
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (T) unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
