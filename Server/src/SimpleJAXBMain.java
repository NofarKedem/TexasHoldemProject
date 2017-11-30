import XMLobject.GameDescriptor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class SimpleJAXBMain {
    private static String FILE_NAME;

    public SimpleJAXBMain(String fileName)
    {
        FILE_NAME = fileName;
    }
    public static GameDescriptor fromXmlFileToObject() {
        //System.out.println("\nFrom File to Object");
        //DetailsFromXml game = new DetailsFromXml();
        try {

            File file = new File(FILE_NAME);
            JAXBContext jaxbContext = JAXBContext.newInstance(GameDescriptor.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            GameDescriptor game = (GameDescriptor) jaxbUnmarshaller.unmarshal(file);
           // System.out.println(DetailsFromXml);
            return game;
        }
        catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;

    }
}
