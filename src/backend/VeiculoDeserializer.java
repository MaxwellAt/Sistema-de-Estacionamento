package backend;

import com.google.gson.*;

import java.lang.reflect.Type;

public class VeiculoDeserializer implements JsonDeserializer<Veiculo> { // Implementa a interface JsonDeserializer para deserializar objetos Veiculo
//    Classe necessária para corrigir o bug JsonIOException: 'Abstract classes can't be instantiated!' (R8)
    @Override
    public Veiculo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject(); // Converte o JSON para um objeto JSON
        String tipo = jsonObject.get("tipo").getAsString(); // Obtém o tipo do veículo

        switch (tipo) {
            case "Carro": // Se o tipo for Carro
                return context.deserialize(json,  Carro.class); // Retorna um objeto Carro
            case "Caminhao":
                return context.deserialize(json, Caminhao.class); // Retorna um objeto Caminhao
            case "Moto":
                return context.deserialize(json, Moto.class); // Retorna um objeto Moto
            default:
                throw new JsonParseException("Unknown type of Veiculo: " + tipo);
        }
    }
}