package BLL;

import java.util.ArrayList;

import DAL.ProductTypeDAL;
import DTO.ProductTypeDTO;

public class ProductTypeBLL {
    // - Properties
    private ProductTypeDAL productTypeDAL;

    // - Constructors
    public ProductTypeBLL()
    {
        productTypeDAL = new ProductTypeDAL();
    }

    // - Methods
    // - Hàm lấy ra danh sách các loại sản phẩm hiện có trong CSDL
    public ArrayList<ProductTypeDTO> getAllProductType()
    {
        return productTypeDAL.selectAll();
    }
    
    // - Hàm lấy ra danh sách các loại sản phẩm hiện có trong CSDL
    public String getOneProductTypeIdByTypeName(String name)
    {
        return productTypeDAL.selectOneId(name);
    }

    // - Hàm lấy ra danh sách các loại sản phẩm hiện có với 1 điều kiện trong CSDL
	public ArrayList<ProductTypeDTO> getAllProductTypeByCondition(String[] join, String condition, String order) {
		return productTypeDAL.selectAllByCondition(join, condition, order);
	}
}
