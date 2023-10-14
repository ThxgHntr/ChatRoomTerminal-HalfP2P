import java.io.*;
import java.util.List;

public class ModelSerializationUtil {
    public static byte[] serializeModelList(List<ClientModel> modelList) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(modelList);
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static List<ClientModel> deserializeModelList(byte[] serializedModelList) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedModelList);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        List<ClientModel> modelList = (List<ClientModel>) objectInputStream.readObject();
        objectInputStream.close();
        return modelList;
    }
}
