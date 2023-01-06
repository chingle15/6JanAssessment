package vttp2022.paf.assessment.eshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.respositories.CustomerRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;
import vttp2022.paf.assessment.eshop.services.WarehouseService;

@RestController
@RequestMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

	@Autowired
	private CustomerRepository custRepo;

	@Autowired
	private OrderRepository orderRepo;

    @Autowired
    private WarehouseService wareSvc;

// takes in a string json value, produce an object(rsvp)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postOrder(@RequestBody String json) {
        Order order = null;
        Order orderResult = null;
        JsonObject resp;
        //create a json 
        try {
            order = Order.create(json);
        } catch (Exception e) {
            e.printStackTrace();
            resp = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(resp.toString());
        }

        orderResult = wareSvc.insertOrder(order);
        resp = Json.createObjectBuilder()
                .add("orderId", orderResult.getOrderId())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(resp.toString());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putOrder(@RequestBody String json) {
        Order order = null;
        boolean orderResult = false;
        JsonObject resp;
        try {
            order = Order.create(json);
        } catch (Exception e) {
            e.printStackTrace();
            resp = Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(resp.toString());
        }

        orderResult = wareSvc.updateOrder(order);
        resp = Json.createObjectBuilder()
                .add("updated", orderResult)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(resp.toString());
    }

}
