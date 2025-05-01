package DTO;

public class InputTicketDetailDTO {
    private Integer inputTicketId;
    private String foodId;
    private Long price;
    private Long quantity;

    // Constructor
    public InputTicketDetailDTO(Integer inputTicketId, String foodId, Long price, Long quantity) {
        this.inputTicketId = inputTicketId;
        this.foodId = foodId;
        this.price = price;
        this.quantity = quantity;
    }

    // Getters và Setters
    public Integer getInputTicketId() {
        return inputTicketId;
    }

    public void setInputTicketId(Integer inputTicketId) {
        this.inputTicketId = inputTicketId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}