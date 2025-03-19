package DTO;

public class InputTicketDetailDTO {
    private Integer id;           // maPhieuNhap
    private String ingredientId;  // maNguyenLieu
    private Long inputPrice;      // giaNhap
    private Integer inputQuantity;// soLuong

    // Constructor
    public InputTicketDetailDTO(Integer id, String ingredientId, Long inputPrice, Integer inputQuantity) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.inputPrice = inputPrice;
        this.inputQuantity = inputQuantity;
    }

    // Getters và Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getIngredientId() { return ingredientId; }
    public void setIngredientId(String ingredientId) { this.ingredientId = ingredientId; }

    public Long getInputPrice() { return inputPrice; }
    public void setInputPrice(Long inputPrice) { this.inputPrice = inputPrice; }

    public Integer getInputQuantity() { return inputQuantity; }
    public void setInputQuantity(Integer inputQuantity) { this.inputQuantity = inputQuantity; }
}