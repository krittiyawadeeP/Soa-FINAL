package controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.CustomerDAO;
import model.Customer;
import model.Phonenumber;

@Path("/services")
public class CustomerService {
	
	  private CustomerDAO dao = new CustomerDAO();

	@GET
	@Path("/customers") 
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getAll() {

		CustomerDAO dao = new CustomerDAO();
		return dao.getAllCustomers();
	}

	
	@POST
	@Path("/customers") 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Customer c) {

	    // 🔹 link Phonenumbers กับ Customer
	    if(c.getPhonenumbers() != null) {
	        for (Phonenumber p : c.getPhonenumbers()) {
	            p.setCustomer(c); // 🔥 สำคัญ
	        }
	    }

	    CustomerDAO dao = new CustomerDAO();
	    boolean result = dao.addCustomer(c); // ต้องมี cascade = ALL ใน Customer.phonenumbers

	    if (result) {
	        return Response.status(201).entity("Created").build();
	    } else {
	        return Response.status(500).entity("Error").build();
	    }
	}
	
	
	@GET
    @Path("/customers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        Customer c = dao.getCustomerById(id);
        if (c != null) {
            return Response.ok(c).build();
        } else {
            return Response.status(404).entity("Customer not found").build();
        }
    }
	
	@PUT
    @Path("/customers/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int id, Customer updatedCustomer) {

        Customer existing = dao.getCustomerById(id);
        if (existing == null) {
            return Response.status(404).entity("Customer not found").build();
        }

        // update basic info
        existing.setName(updatedCustomer.getName());
        existing.setSername(updatedCustomer.getSername());
        existing.setUsername(updatedCustomer.getUsername());
        existing.setPwd(updatedCustomer.getPwd());
        existing.setUserroles(updatedCustomer.getUserroles());

        // update phone numbers
        if (updatedCustomer.getPhonenumbers() != null) {
            existing.getPhonenumbers().clear(); // remove old numbers
            for (Phonenumber p : updatedCustomer.getPhonenumbers()) {
                existing.addPhonenumber(p); // helper sets customer automatically
            }
        }

        boolean result = dao.updateCustomer(existing);
        if (result) {
            return Response.ok("Updated").build();
        } else {
            return Response.status(500).entity("Update failed").build();
        }
    }
	
	
    @DELETE
    @Path("/customers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
        Customer c = dao.getCustomerById(id);
        if (c == null) {
            return Response.status(404).entity("Customer not found").build();
        }

        boolean result = dao.deleteCustomer(c.getCusId());
        if (result) {
            return Response.ok("Deleted").build();
        } else {
            return Response.status(500).entity("Delete failed").build();
        }
    }
}
	

