package BLL;

import java.math.BigInteger;
import java.util.ArrayList;

import DAL.DiscountDAL;
import DTO.AccountDTO;
import DTO.DiscountDTO;

public class DiscountBLL {
	// Properties
	private DiscountDAL discountDAL;

	// Constructors
	public DiscountBLL() {
		discountDAL = new DiscountDAL();
	}

	// Methods
	// - Hàm kiểm tra đã nhập mã khuyến mãi hay chưa ?
	private boolean isInputedId(String id) {
		if (id.equals("Nhập Mã khuyến mãi")) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra đã nhập tên khuyến mãi hay chưa ?
	private boolean isInputedName(String name) {
		if (name == null) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra đã nhập phần trăm hay chưa ?
	private boolean isInputedPercent(String percent) {
		if (percent == null) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra đã nhập mức áp dụng hay chưa ?
	private boolean isInputedRoomCost(String roomCost) {
		if (roomCost == null) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra đã chọn ngày bắt đầu hay chưa ?
	private boolean isSelectedDateStart(String roomCost) {
		if (roomCost == null) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra đã chọn ngày kết thúc hay chưa ?
	private boolean isSelectedDateEnd(String roomCost) {
		if (roomCost == null) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra đã chọn trạng thái hay chưa ?
	private boolean isSelectedStatus(String roomCost) {
		if (roomCost == null) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra mã khuyến mãi có hợp lệ hay không ?
	private boolean isValidId(String id) {
		if (id.length() != 7) {
			return false;
		}

		if (!id.substring(0, 2).equals("KM")) {
			return false;
		}

		if (!CommonBLL.isValidStringType04(id.substring(2))) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra tên khuyến mãi có hợp lệ hay không ?
	private boolean isValidName(String name) {
		if (!CommonBLL.isValidStringType01(name)) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra phần trăm có hợp lệ hay không ?
	private boolean isValidPercent(String percent) {
		if (!CommonBLL.isValidStringType04(percent)) {
			return false;
		}
		if (Integer.parseInt(percent) < 0 || Integer.parseInt(percent) > 100) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra phần trăm có hợp lệ hay không ?
	private boolean isValidRoomCost(String roomCost) {
		if (!CommonBLL.isValidStringType04(roomCost)) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra có phải ngày bắt đầu sau ngày kết thúc
	private boolean isDateStartAfterDateEnd(String dateStart, String dateEnd) {
		if (dateStart.compareTo(dateEnd) != -1) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra có phải ngày bắt đầu sau ngày kết thúc
	private boolean isDateStartEqualDateEnd(String dateStart, String dateEnd) {
		if (dateStart.compareTo(dateEnd) != 0) {
			return false;
		}

		return true;
	}

	// - Hàm thêm một khuyến mãi (kiểm tra trước khi thêm)
	public String insertDiscount(String id, String name, String percent, String roomCost, String dateStart,
			String dateEnd, String status, String dateUpdate) {
		if (!isInputedId(id) && !isInputedName(name) && !isInputedPercent(percent) && !isInputedRoomCost(roomCost)
				&& !isSelectedDateStart(dateStart) && !isSelectedDateEnd(dateEnd) && !isSelectedStatus(status)) {
			return "Chưa nhập đủ thông tin cần thiết";
		}
		if (!isInputedId(id)) {
			return "Chưa nhập mã khuyến mãi";
		}
		if (!isInputedName(name)) {
			return "Chưa nhập tên khuyến mãi";
		}
		if (!isInputedPercent(percent)) {
			return "Chưa nhập phần trăm (%)";
		}
		if (!isInputedRoomCost(roomCost)) {
			return "Chưa nhập mức áp dụng (VNĐ)";
		}
		if (!isSelectedDateStart(dateStart)) {
			return "Chưa chọn ngày bắt đầu";
		}
		if (!isSelectedDateEnd(dateEnd)) {
			return "Chưa chọn ngày kết thúc";
		}
		if (!isSelectedStatus(status)) {
			return "Chưa chọn trạng thái";
		}
		if (!isValidId(id) && !isValidName(name) && !isValidPercent(percent) && !isValidRoomCost(roomCost)
				&& !isDateStartAfterDateEnd(dateStart, dateEnd) && !isDateStartEqualDateEnd(dateStart, dateEnd)) {
			return "Nhập sai định thông tin khuyến mãi";
		}
		if (!isValidId(id)) {
			return "Nhập sai định dạng mã khuyến mãi";
		}
		if (!isValidName(name)) {
			return "Nhập sai định dạng tên khuyến mãi";
		}
		if (!isValidPercent(percent)) {
			return "Nhập sai định dạng phần trăm (%)";
		}
		if (!isValidRoomCost(roomCost)) {
			return "Nhập sai định dạng mức áp dụng (VNĐ)";
		}
		if (CommonBLL.compareBetweenTwoDate(dateStart, dateEnd) == 1) {
			return "Ngày bắt đầu cần phải là trước hoặc bằng ngày kết thúc";
		}

		// - Nếu thoả mãn hết thì thêm vào CSDL
		String discountId = id;
		String discountName = name;
		int discountPercent = Integer.parseInt(percent);
		long discountRoomCost = Long.parseLong(roomCost);
		String discountDateStart = dateStart;
		String discountDateEnd = dateEnd;
		boolean discountStatus = status.equals("Hoạt động") ? true : false;
		String discountDateUpdate = dateUpdate;
		DiscountDTO newdiscountDTO = new DiscountDTO(discountId, discountName, discountPercent, discountRoomCost,
				discountDateStart, discountDateEnd, discountStatus, discountDateUpdate);
		discountDAL.insert(newdiscountDTO);

		return "Có thể thêm một khuyến mãi";
	}

	// - Hàm thay đổi một khuyến mãi (kiểm tra trước khi thay đổi)
	public String updateDiscount(String id, String name, String percent, String roomCost, String dateStart,
			String dateEnd, String status, String dateUpdate) {
		if (!isInputedName(name) && !isInputedPercent(percent) && !isInputedRoomCost(roomCost)
				&& !isSelectedDateStart(dateStart) && !isSelectedDateEnd(dateEnd) && !isSelectedStatus(status)) {
			return "Chưa nhập đủ thông tin cần thiết";
		}
		if (!isInputedName(name)) {
			return "Chưa nhập tên khuyến mãi";
		}
		if (!isInputedPercent(percent)) {
			return "Chưa nhập phần trăm (%)";
		}
		if (!isInputedRoomCost(roomCost)) {
			return "Chưa nhập mức áp dụng (VNĐ)";
		}
		if (!isSelectedDateStart(dateStart)) {
			return "Chưa chọn ngày bắt đầu";
		}
		if (!isSelectedDateEnd(dateEnd)) {
			return "Chưa chọn ngày kết thúc";
		}
		if (!isValidName(name) && !isValidPercent(percent) && !isValidRoomCost(roomCost) && !isDateStartAfterDateEnd(dateStart, dateEnd) && !isDateStartEqualDateEnd(dateStart, dateEnd)) {
			return "Nhập sai định thông tin khuyến mãi";
		}
		if (!isValidName(name)) {
			return "Nhập sai định dạng tên khuyến mãi";
		}
		if (!isValidPercent(percent)) {
			return "Nhập sai định dạng phần trăm (%)";
		}
		if (!isValidRoomCost(roomCost)) {
			return "Nhập sai định dạng mức áp dụng (VNĐ)";
		}
		if (CommonBLL.compareBetweenTwoDate(dateStart, dateEnd) == 1) {
			return "Ngày bắt đầu cần phải là trước hoặc bằng ngày kết thúc";
		}

		// - Nếu thoả mãn hết thì thêm vào CSDL
		String discountId = id;
		String discountName = name;
		int discountPercent = Integer.parseInt(percent);
		long discountRoomCost = Long.parseLong(roomCost);
		String discountDateStart = dateStart;
		String discountDateEnd = dateEnd;
		boolean discountStatus = status.equals("Hoạt động") ? true : false;
		String discountDateUpdate = dateUpdate;
		DiscountDTO updatediscountDTO = new DiscountDTO(discountId, discountName, discountPercent, discountRoomCost,
				discountDateStart, discountDateEnd, discountStatus, discountDateUpdate);
		discountDAL.update(updatediscountDTO);

		return "Có thể thay đổi một khuyến mãi";
	}

	// - Hàm khoá một khuyến mãi
	public String lockDiscount(String id, String dateUpdate) {
		// - Khoá hoặc mở khoá tuỳ vào trạng thái hiện tại
		DiscountDTO lockDiscount = getOneDiscountById(id);
		lockDiscount.setStatus(lockDiscount.getStatus() ? false : true);
		lockDiscount.setDateUpdate(dateUpdate);
		discountDAL.lock(lockDiscount);

		return "Có thể khoá một người dùng";
	}

	// - Hàm lấy ra danh sách các khuyến mãi hiện có trong CSDL
	public ArrayList<DiscountDTO> getAllDiscount() {
		return discountDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các khuyến mãi hiện có với 1 điều kiện trong CSDL
	public ArrayList<DiscountDTO> getAllDiscountByCondition(String[] join, String condition, String order) {
		return discountDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một người dùng với mã khuyến mãi tương ứng
	public DiscountDTO getOneDiscountById(String id) {
		return discountDAL.selectOneById(id);
	}
}
