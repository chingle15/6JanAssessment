package vttp2022.paf.assessment.eshop.controllers;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
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
import jakarta.json.JsonObjectBuilder;
import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
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

	@PostMapping(value = "/dispatch", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> dispatchOrder(@RequestBody MultiValueMap<String, String> form) {
		System.out.println(form.getFirst("name"));
		String name = form.getFirst("name");

		// Step a: Query to see if customer exists
		Optional<Customer> opt = custRepo.findCustomerByName(name);

		JsonObjectBuilder bld = Json.createObjectBuilder();

		if (opt.isEmpty()) {
			bld.add("error", "Customer <%s> not found".formatted(name));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(bld.build().toString());
		}

		Customer c = opt.get();
		return null;

		String order_Id = UUID.randomUUID().toString().substring(0,8);
		System.out.println(order_Id);

		Order o = new Order();
		o.setOrderId(order_Id);
		o.setCustomer(c);

		String item_name = form.getFirst("item");
		Integer quantity = Integer.parseInt(form.getFirst("quantity"));

		List<LineItem> items = new LinkedList<>();
		LineItem li = new LineItem();
		li.setItem(item_name);
		li.setQuantity(quantity);
		System.out.println(item_name);

		o.setLineItems(items);

		try {
			orderRepo.saveOrder(o);
			} catch (Exception ex) {
				bld.add("error", ex.getMessage());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(bld.toString());
			}


		OrderStatus os = warehouseSvc.dispatch(o);


}}