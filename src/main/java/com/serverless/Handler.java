package com.serverless;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.models.ApiGatewayResponse;
import com.serverless.models.Element;
import com.serverless.models.Input;
import com.serverless.models.Node;
import com.serverless.models.Response;
import com.serverless.models.Value;


/**
 * Project meshItUp.
 * 
 * <p>
 * There is a map excerpt of a hilly landscape. We call it a mesh.
 * The mesh is partitioned in triangles; we call them elements.
 * For each element a scalar value is assigned,
 * which represents the average spot height in this triangle as compared to the sea level.
 * </p>
 * 
 * 
 * @author Enno Münsterberg
 * @version 1.0
 */
public class Handler implements RequestHandler<Input, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	/*static enum ByName implements Comparator<Element> {
		INSTANCE;
	
		@Override
		public int compare(Element c1, Element c2) {
			return c1.value.compareTo(c2.value);
		}
	}*/
	//Arrays.sort(elements, ByName.INSTANCE);

    /**
     * AWS Lambda handler for the meshItUp function.
     * 
     * @param input  AWS Lambda input model.
     * @param context   AWS Lambda context.
     */
	@Override
	public ApiGatewayResponse handleRequest(Input input, Context context) {
		LOG.info("Starting Viewspots finder!");
        if(input == null || input.nodes == null || input.elements == null || input.values == null) {
            return ApiGatewayResponse.builder()
				.setStatusCode(403)
				.setObjectBody(new Response("Wrong input!", input))
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
        }
		Response responseBody = new Response("Go Serverless v1.x! Your function executed successfully!", input);
		return ApiGatewayResponse.builder()
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}

    /**
     * This function will add all hight values to the specific element determind by id.
     * 
     * @param elements  List of all elements.
     * @param values   List of all values, which represent a hight.
     */
	public List<Element> mergeElementsAndValues(List<Element> elements, List<Value> values) {
		for(int i = 0; i < elements.size(); ++i) {
			elements.get(i).value = values.get(i).value;
		}
		return elements;
	}

    /**
     * This function will construct all nodes from integer values by looking them up
     * in a list of nodes.
     * 
     * @param element  Element to construct nodes for.
     * @param nodes   List of all nodes.
     */
    public Element findNodes(Element element, List<Node> nodes) {
        element.construcedNodes = new ArrayList<>();
        for(int en : element.nodes) {
            for(Node n : nodes) {
                if(n.x == en || n.y == en) {
                    element.construcedNodes.add(n);
                }
            }
        }
        return e;
    }

    /**
     * This function will find all neighbours for an element from a list of elements.
     * 
     * @param element  Element to find all neighbours for.
     * @param elements   List of all elements.
     */
    public List<Element> findNeighbours(Element element, List<Element> elements) {
        List<Element> neighbours = new ArrayList<>();
        for(Element e : elements) {
            for(Node n : element.construcedNodes) {
                for(Node neighbour : e.construcedNodes) {
                    if(n.id == neighbour.id) {
                        neighbours.add(e);
                    }
                }
            }
        }
        return neighbours;
    }

    /**
     * This functions takes a list of all elements and finds all highest viewspots,
     * by checking all neighbours.
     * 
     * @param elements  List of all elements.
     */
    public List<Element> findHighestSpots(List<Element> elements) {
        List<Element> result = new ArrayList<>();
        int counter = 0;

        for(Element e : elements) {
            for(Element neigElement : e.neighbours) {
                if(new BigInteger(e.value).compareTo(new BigInteger(neigElement.value)) > 0) {
                    ++counter;
                }
            }

            if(counter == e.construcedNodes.size()) {
                result.add(e);
            }
            counter = 0;
        }
        return result;
    }

    /**
     * .
     * 
     * @param elements  List of all elements.
     */
    public void createOutputViewspots(List<Element> element) {

    }
}
