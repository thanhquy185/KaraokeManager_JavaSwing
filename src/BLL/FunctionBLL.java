package BLL;

import java.util.ArrayList;

import DAL.FunctionDAL;
import DTO.FunctionDTO;

public class FunctionBLL {
	// Properties
	private FunctionDAL functionDAL;

	// Constructors
	public FunctionBLL() {
		functionDAL = new FunctionDAL();
	}

	// Methods
	// - Hàm lấy ra danh sách các chức năng hiện có trong CSDL
	public ArrayList<FunctionDTO> getAllFunction() {
		return functionDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các chức năng hiện có với 1 điều kiện trong CSDL
	public ArrayList<FunctionDTO> getAllFunctionByCondition(String[] join, String condition, String order) {
		return functionDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một người dùng với mã chức năng tương ứng
	public FunctionDTO getOneFunctionById(String id) {
		return functionDAL.selectOneById(id);
	}
}
