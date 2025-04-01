package in.wavity.ExpenseTracker.Response;

public class ExpenseResponse<t> {
	private t data;
	private String message;

	public t getData() {
		return data;
	}

	public void setData(t data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
