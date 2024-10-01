package models;

public class Policy {
	private String id;
    private String customerId;
    private String type;
    private double premium;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPremium() {
		return premium;
	}
	public void setPremium(double premium) {
		this.premium = premium;
	}
	public Policy(String id, String customerId, String type, double premium) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.type = type;
		this.premium = premium;
	}
	public Policy() {
		super();
	}
	@Override
	public String toString() {
		return "Policy [id=" + id + ", customerId=" + customerId + ", type=" + type + ", premium=" + premium + "]";
	}
    
    
}
