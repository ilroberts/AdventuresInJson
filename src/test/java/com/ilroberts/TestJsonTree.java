package com.ilroberts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class TestJsonTree {

    private String json;

    @Before
    public void init() throws IOException {

        json = new String(Files.readAllBytes(Paths.get("./src/test/resources/test1.json")));
    }

    @Test
    public void testCreateTree() throws IOException{

        ObjectMapper mapper = new ObjectMapper();

        JsonNode rootNode = mapper.readTree(json);
        assertThat(rootNode).isNotNull();

    }

    public JsonNode createRootNode() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(json);
    }

    @Test
    public void testGetNode() throws IOException {

        JsonNode node = createRootNode();
        assertThat(node.get("name").asText()).isEqualTo("bilbo baggins");

    }

    @Test
    public void testTraverseTree() throws IOException {

        JsonNode node = createRootNode();
        assertThat(stream(node.elements()).count()).isEqualTo(2);

    }

    @Test
    public void testCopyTree() throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = createRootNode();
        JsonNode out = mapper.createObjectNode();

        Iterator<Map.Entry<String,JsonNode>> fieldsIterator = node.fields();
        while(fieldsIterator.hasNext()) {

            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            ((ObjectNode) out).set(field.getKey(), field.getValue());
        }
        assertThat(node.asText()).isEqualTo(out.asText());

    }

    public static <T> Stream<T> stream(Iterable<T> it){
        return StreamSupport.stream(it.spliterator(), false);
    }

    public static <T> Stream<T> stream(Iterator<T> it){
        return stream(()->it);
    }

}
