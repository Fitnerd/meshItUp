package com.serverless;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.models.ApiGatewayResponse;
import com.serverless.models.Element;
import com.serverless.models.Input;
import com.serverless.models.Node;
import com.serverless.models.Value;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.SneakyThrows;


public class HandlerTest {
    private static final Logger LOG = LogManager.getLogger(HandlerTest.class);

    @Test
    public void shouldExceptInputModel(){
        LOG.info("shouldExceptInputModel TEST");

        Handler handler = new Handler();
        ObjectMapper mapper = new ObjectMapper();
        TestContext testContext = new TestContext();

        Input inputMesh = new Input();
        try {
            inputMesh = mapper.readValue(Paths.get("testmesh.json").toFile(), Input.class);
            ApiGatewayResponse apiGatewayResponse = handler.handleRequest(inputMesh, testContext);

            Assertions.assertEquals(200, apiGatewayResponse.getStatusCode());
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldFailWithNullInputModel() {
        LOG.info("shouldExceptInputModel TEST");
        Handler handler = new Handler();
        TestContext testContext = new TestContext();
        Input inputMesh = new Input();

        ApiGatewayResponse apiGatewayResponse = handler.handleRequest(inputMesh, testContext);

        Assertions.assertEquals(403, apiGatewayResponse.getStatusCode());
    }

    @Test
    public void shouldMergeElementsAndValues() {
        LOG.info("shouldMergeElementsAndValues TEST");
        List<Element> elements = new ArrayList<>();
        Element element = new Element();
        element.id = 0;
        element.nodes = new ArrayList<Integer>();
        elements.add(element);

        List<Value> values = new ArrayList<>();
        Value value = new Value();
        value.element_id = 0;
        value.value = "-0.123012358";
        values.add(value);

        Handler handler = new Handler();

        List<Element> reuslt = handler.mergeElementsAndValues(elements, values);
        Assertions.assertEquals(value.value, reuslt.get(0).value);
    }

    @Test
    public void shouldFindAllNeighbours() {
        LOG.info("shouldFindALlNeighbours");
        List<Element> elements = new ArrayList<>();
        Element element = new Element();
        element.id = 0;
        element.nodes = new ArrayList<>();
        element.nodes.add(0);
        element.nodes.add(1);
        element.nodes.add(2);

        List<Node> nodes = new ArrayList<>();
        for(int i = 0; i < 3; ++i) {
            Node n = new Node();
            n.id = i;
            n.x = i;
            n.y = i+i;
            nodes.add(n);
        }

        Handler handler = new Handler();
        Element result = handler.findNodes(element, nodes);

        Assertions.assertNotNull(result.neighbours);
        Assertions.assertTrue(result.neighbours.size() != 0);
    }

    @Test
    public void shouldFindHighestSpots() {
        LOG.info("shouldFindHighestSpots");
        List<Element> elements = new ArrayList<>();

        Element element1 = new Element();
        element1.id = 0;
        element1.value = "0.123235523";
        element1.neighbours = new ArrayList<>();

        Element element2 = new Element();
        element2.id = 1;
        element2.value = "0.1232353";
        element2.neighbours = new ArrayList<>();

        element1.neighbours.add(element2);
        element2.neighbours.add(element1);

        elements.add(element1);
        elements.add(element2);

        Handler handler = new Handler();

        List<Element> result = handler.findHighestSpots(elements);

        Assertions.assertEquals(element1.id, result.get(0).id);

    }

    @Test
    public void shouldPrintViewspotsByNumberN() {
        LOG.info("shouldPrintViewspotsByNumberN");

    }

}