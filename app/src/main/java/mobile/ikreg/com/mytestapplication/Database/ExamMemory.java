package mobile.ikreg.com.mytestapplication.Database;

public class ExamMemory {

    private String product;
    private String quantity;
    private long id;


    public ExamMemory(String product, String quantity, long id) {
        this.product = product;
        this.quantity = quantity;
        this.id = id;
    }


    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        String output = quantity + ", " + product;

        return output;
    }
}
