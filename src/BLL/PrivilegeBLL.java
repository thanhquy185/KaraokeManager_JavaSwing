package BLL;

import java.util.ArrayList;

import DAL.PrivilegeDAL;
import DTO.PrivilegeDTO;

public class PrivilegeBLL {
	// Properties
	private PrivilegeDAL privilegeDAL;

	// Constructors
	public PrivilegeBLL() {
		privilegeDAL = new PrivilegeDAL();
	}

	// Methods
	// - Hàm lấy ra danh sách các chức năng hiện có trong CSDL
	public ArrayList<PrivilegeDTO> getAllPrivilege() {
		return privilegeDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các chức năng hiện có với 1 điều kiện trong CSDL
	public ArrayList<PrivilegeDTO> getAllPrivilegeByCondition(String[] join, String condition, String order) {
		return privilegeDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một người dùng với mã chức năng tương ứng
	public PrivilegeDTO getOnePrivilegeById(String id) {
		return privilegeDAL.selectOneById(id);
	}
}
