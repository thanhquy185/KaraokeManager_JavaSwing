package BLL;

import java.util.ArrayList;
import java.util.Map;

import DAL.PrivilegeDetailDAL;
import DTO.PrivilegeDetailDTO;

public class PrivilegeDetailBLL {
	// Properties
	private PrivilegeDetailDAL privilegeDetailDAL;

	// Constructors
	public PrivilegeDetailBLL() {
		privilegeDetailDAL = new PrivilegeDetailDAL();
	}

	// Methods
	// - Hàm kiểm tra mã

	// - Hàm thêm một chi tiết quyền
	public String insertPrivilegeDetail(String accountId, String privilegeId, Map<String, Boolean> privilegeDetail) {
		// - Xoá hết các chi tiết quyền cũ
		privilegeDetailDAL.deleteAllByAccountId(accountId);

		// - Cập nhật chi tiết quyền mới
		FunctionBLL functionBLL = new FunctionBLL();
		for (Map.Entry<String, Boolean> entry : privilegeDetail.entrySet()) {
			if (entry.getValue()) {
				String functionId = functionBLL
						.getAllFunctionByCondition(null, String.format("tenChucNang = '%s'", entry.getKey()), null)
						.get(0)
						.getId();
				PrivilegeDetailDTO newPrivilegeDetailDTO = new PrivilegeDetailDTO(Integer.parseInt(accountId),
						functionId);
				privilegeDetailDAL.insert(newPrivilegeDetailDTO);
			}
		}

		return "Có thể thêm các chi tiết quyền";
	}

	// - Hàm thêm thay đổi một chi tiết quyền
	// public String updatePrivilegeDetail(String accountId, String privilegeId,
	// Map<String, Boolean> privilegeDetail) {
	// FunctionBLL functionBLL = new FunctionBLL();
	// for (Map.Entry<String, Boolean> entry : privilegeDetail.entrySet()) {
	// String functionId = functionBLL
	// .getAllFunctionByCondition(null, String.format("tenChucNang = '%s'",
	// entry.getKey()), null).get(0)
	// .getId();

	// PrivilegeDetailDTO updatePrivilegeDetailDTO = new
	// PrivilegeDetailDTO(Integer.parseInt(accountId),
	// functionId);
	// privilegeDetailDAL.update(updatePrivilegeDetailDTO);
	// }

	// return "Có thể thay đổi một chi tiết quyền";
	// }

	// - Hàm lấy ra danh sách các chi tiết quyền hiện có trong CSDL
	public ArrayList<PrivilegeDetailDTO> getAllPrivilegeDetail() {
		return privilegeDetailDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các chi tiết quyền hiện có với 1 điều kiện trong CSDL
	public ArrayList<PrivilegeDetailDTO> getAllPrivilegeDetailByCondition(String[] join, String condition,
			String order) {
		return privilegeDetailDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra danh sách các chi tiết quyền hiện có với 1 điều kiện trong CSDL
	public ArrayList<PrivilegeDetailDTO> getAllPrivilegeDetailByAccountId(String accountId) {
		return privilegeDetailDAL.selectAllByAccountId(accountId);
	}

	// - Hàm lấy ra một người dùng với chi tiết quyền tương ứng
	public PrivilegeDetailDTO getOnePrivilegeDetailById(String id) {
		return privilegeDetailDAL.selectOneById(id);
	}
}
