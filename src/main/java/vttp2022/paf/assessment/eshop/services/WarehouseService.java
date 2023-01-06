package vttp2022.paf.assessment.eshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.respositories.CustomerRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;

@Service
public class WarehouseService {

	@Autowired
	private CustomerRepository CustRepo;

	public Customer findCustomerByName(String name) {
		return CustRepo.findCustomerByName(name);
	}

	@Autowired
	private OrderRepository OrderRepo;

	public Order insertOrder(final Order order) {
        return OrderRepo.insertOrder(order);
    }

    public boolean updateOrder(final Order order) {
        return OrderRepo.updateOrder(order); }

	// You cannot change the method's signature
	// You may add one or more checked exceptions
	public static OrderStatus dispatch(Order order) {

		// TODO: Task 4

	}
}
