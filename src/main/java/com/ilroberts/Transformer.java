package com.ilroberts;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.atlassian.fugue.Option;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

import static java.nio.file.Files.readAllBytes;

public class Transformer {

    public Option<String> readJsonFromFile(String filePath) {

        try {
            String output = new String(readAllBytes(Paths.get(filePath)));
            String noNewline = output.replace("\n","");
            return Option.some(noNewline);
        } catch (IOException e) {
            return Option.none();
        }
    }

    public Option<String> copyJson(String input) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.readTree(input);
            JsonNode out = mapper.createObjectNode();

            Iterator<Map.Entry<String,JsonNode>> fieldsIterator = node.fields();
            while(fieldsIterator.hasNext()) {

                Map.Entry<String, JsonNode> field = fieldsIterator.next();
                ((ObjectNode) out).set(field.getKey(), field.getValue());
            }

            return Option.some(mapper.writeValueAsString(out));
        } catch (IOException e) {
            return Option.none();
        }
    }

}
