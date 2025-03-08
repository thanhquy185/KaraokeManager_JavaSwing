package BLL;

import java.util.ArrayList;

import DAL.CustomerDAL;
import DTO.CustomerDTO;
import PL.CommonPL;

public class CustomerBLL {
    private CustomerDAL customerDAL;

    // - Constructors
    public CustomerBLL()
    {
        customerDAL = new CustomerDAL();
    }

    // - Hàm kiểm tra CCCD đã được nhập hay chưa ?
	public boolean isInputedId(String id) {
		if (id == null) {
			return false;
		}
		return true;
	}

    // - Hàm kiểm tra CCCD đã hợp lệ hay chưa ?
	public boolean isValidId(String id) {
		if (id.length() != 10 || !CommonBLL.isValidStringType04(id)) {
			return false;
		}
		return true;
	}

    // - Hàm kiểm tra mã loại khách hàng đã được chọn hay chưa ?
	public boolean isSelectedIdCard(String idcard) {
		if (idcard == null) {
			return false;
		}
	    return true;
	}

    // - Hàm kiểm tra mã loại khách hàng đã hợp lệ hay chưa ?
	public boolean isValidIdCard(String idcard) {
		if (!idcard.equals("Thường") && !idcard.equals("Bạc") && !idcard.equals("Vàng") && !idcard.equals("Kim cương")) {
			return false;
		}
		return true;
	}

    // - Hàm kiểm tra số điện thoại đã được nhập hay chưa ?
	public boolean isInputedPhone(String phone) {
		if (phone == null) {
			return false;
		}
		return true;
	}

    // - Hàm kiểm tra số điện thoại đã hợp lệ hay chưa ?
	public boolean isValidPhone(String phone) {
		if (phone.length() != 10 || !CommonBLL.isValidStringType04(phone)) {
			return false;
		}
		return true;
	}

    // - Hàm kiểm tra trạng thái đã được chọn hay chưa ?
	public boolean isSelectedStatus(String status) {
		if (status == null) {
			return false;
		}
		return true;
	}

    // - Hàm kiểm tra trạng thái đã hợp lệ hay chưa ?
	public boolean isValidStatus(String status) {
		if (!status.equals("Hoạt động") && !status.equals("Tạm dừng")) {
			return false;
		}

		return true;
	}

    // - Hàm kiểm tra họ và tên đã được nhập hay chưa ?
	public boolean isInputedFullname(String fullname) {
		if (fullname == null) {
			return false;
		}
		return true;
	}

    // - Hàm kiểm tra họ và tên đã hợp lệ hay chưa ?
	public boolean isValidFullname(String fullname) {
		if (!CommonBLL.isValidStringType01(fullname)) {
			return false;
		}
		return true;
	}

    // - Hàm kiểm tra email đã hợp lệ hay chưa ?
	public boolean isValidEmail(String email) {
		if (email != null && !CommonBLL.isValidStringType06(email)) {
			return false;
		}
		return true;
	}

    // - Hàm kiểm tra địa chỉ đã hợp lệ hay chưa ?
	public boolean isValidAddress(String address) {
		if (address != null && !CommonBLL.isValidStringType05(address)) {
			return false;
		}
		return true;
	}

    // - Hàm kiểm tra CCCD đã tồn tại hay chưa ?
	public boolean isExistsId(String id) 
    {
		String[] join = null;
		String condition = String.format("cccd = '%s'", id);
		String order = null;
		if ((customerDAL.selectAllByCondition(join, condition, order)).size() == 0) 
        {
			return false;
        }
		return true;
	}

    // - Hàm kiểm tra số điện thoại đã tồn tại hay chưa ?
	public boolean isExistsPhone(String phone) {
		String[] join = null;
		String condition = String.format("soDienThoai = '%s'", phone);
		String order = null;
		if ((customerDAL.selectAllByCondition(join, condition, order)).size() == 0) {
			return false;
		}
		return true;
	}

    // - Hàm kiểm tra email đã tồn tại hay chưa ?
	public boolean isExistsEmail(String email) {
		String[] join = null;
		String condition = String.format("email = '%s'", email);
		String order = null;
		if ((customerDAL.selectAllByCondition(join, condition, order)).size() == 0) {
			return false;
		}
		return true;
	}

    // - Hàm tạo một khách hàng
	public String insertCustomer(String id, String idcard, String fullname, String  birthday, String gender, String phone, String email, 
            String address, String status, String dateUpdate) 
    {
	    // - Kiểm tra các trường hợp
		if (!isInputedId(id) && !isInputedPhone(phone) && !isInputedFullname(fullname)
				&& !isSelectedIdCard(idcard) && !isSelectedStatus(status)) {
			return "Chưa nhập đầy đủ thông tin khách hàng cần thiết";
		}
		if (!isInputedId(id)) {
			return "Chưa nhập căn cước công dân";
		}
		if (!isInputedPhone(phone)) {
			return "Chưa nhập số điện thoại";
		}
		if (!isInputedFullname(fullname)) {
			return "Chưa nhập họ và tên";
		}
		if (!isSelectedIdCard(idcard)) {
			return "Chưa chọn loại khách hàng";
		}
		if (!isSelectedStatus(status)) {
			return "Chưa chọn trạng thái";
		}
		if (!isValidId(id) && !isValidFullname(fullname) && !isValidPhone(phone) && !isValidEmail(email)
				&& !isValidAddress(address) && !isValidIdCard(idcard) && !isValidStatus(status)) {
			return "Nhập sai định dạng thông tin khách hàng";
		}
		if (!isValidId(id)) {
			return "Nhập sai định dạng CCCD";
		}
		if (!isValidFullname(fullname)) {
			return "Nhập sai định dạng họ và tên";
		}
		if (!isValidPhone(phone)) {
			return "Nhập sai định dạng số điện thoại";
		}
		if (!isValidEmail(email)) {
			return "Nhập sai định dạng email";
		}
		if (!isValidAddress(address)) {
			return "Nhập sai định dạng địa chỉ";
		}
		if (!isValidIdCard(idcard)) {
			return "Chọn sai định dạng mã loại khách hàng";
		}
		if (!isValidStatus(status)) {
			return "Chọn sai định dạng trạng thái";
		}
		if (isExistsId(id) && isExistsPhone(phone) && isExistsEmail(email)) {
			return "Nhiều thông tin khách hàng đã tồn tại";
		}
		if (isExistsId(id)) {
			return "CCCD của khách hàng đã tồn tại";
		}
		if (isExistsPhone(phone)) {
			return "Số điện thoại đã tồn tại";
		}
		if (isExistsEmail(email)) {
			return "Email đã tồn tại";
		}

		// - Nếu thoả mãn hết thì thêm vào CSDL
		String customerId = id;
		String customerFullname = fullname;
		String customerPhone = phone;
		String customerEmail = email;
		String customerAddress = address;
		String customerBirthday = birthday;
		Boolean customerGender = gender.equals("Nam") ? true : false;
		String customerIdcard = idcard;
		boolean customerStatus = status.equals("Hoạt động") ? true : false;
		String customerDateUpdate = dateUpdate;
		CustomerDTO newCustomerDTO = new CustomerDTO(customerId, customerIdcard, customerFullname, customerBirthday, customerGender, 
                customerPhone, customerEmail, customerAddress, customerStatus, customerDateUpdate);
		customerDAL.insert(newCustomerDTO);
		return "Có thể thêm một khách hàng";
	}

    // - Hàm cập nhật một khách hàng
	public String updateCustomer(String id, String idcard, String fullname, String  birthday, String gender, String phone, String email, 
            String address, String status, String dateUpdate) 
    {
		// - Kiểm tra các trường hợp
		if (!isInputedPhone(phone) && !isInputedFullname(fullname)
				&& !isSelectedIdCard(idcard)) {
			return "Chưa nhập đầy đủ thông tin khách hàng cần thiết";
		}
		if (!isInputedPhone(phone)) {
			return "Chưa nhập số điện thoại";
		}
		if (!isInputedFullname(fullname)) {
			return "Chưa nhập họ và tên";
		}
		if (!isSelectedIdCard(idcard)) {
			return "Chưa chọn loại khách hàng";
		}
		if (!isValidId(id) && !isValidFullname(fullname) && !isValidPhone(phone) && !isValidEmail(email)
				&& !isValidAddress(address) && !isValidIdCard(idcard) && !isValidStatus(status)) {
			return "Nhập sai định dạng thông tin khách hàng";
		}
		if (!isValidFullname(fullname)) {
			return "Nhập sai định dạng họ và tên";
		}
		if (!isValidPhone(phone)) {
			return "Nhập sai định dạng số điện thoại";
		}
		if (!isValidEmail(email)) {
			return "Nhập sai định dạng email";
		}
		if (!isValidAddress(address)) {
			return "Nhập sai định dạng địa chỉ";
		}
		if (!isValidIdCard(idcard)) {
			return "Chọn sai định dạng mã loại khách hàng";
		}
		if (isExistsPhone(phone) && isExistsEmail(email)) {
			return "Nhiều thông tin khách hàng đã tồn tại";
		}
		if (isExistsPhone(phone)) {
			return "Số điện thoại đã tồn tại";
		}
		if (isExistsEmail(email)) {
			return "Email đã tồn tại";
		}

		// - Nếu thoả mãn hết thì thêm vào CSDL
		String customerId = id;
		String customerFullname = fullname;
		String customerPhone = phone;
		String customerEmail = email;
		String customerAddress = address;
		String customerBirthday = birthday;
		Boolean customerGender = gender.equals("Nam") ? true : false;
		String customerIdcard = idcard;
		boolean customerStatus = status.equals("Hoạt động") ? true : false;
		String customerDateUpdate = dateUpdate;
		CustomerDTO newCustomerDTO = new CustomerDTO(customerId, customerIdcard, customerFullname, customerBirthday, customerGender, 
                customerPhone, customerEmail, customerAddress, customerStatus, customerDateUpdate);
		customerDAL.insert(newCustomerDTO);
		return "Có thể thay đổi một khách hàng";
	}

    // - Hàm khoá một khách hàng
	public String lockCustomer(String id, String dateUpdate) {
		// - Khoá hoặc mở khoá tuỳ vào trạng thái hiện tại
		CustomertDTO lockCustomerDTO = getOneCustomerById(id);
		lockCustomerDTO.setStatus(lockCustomerDTO.getStatus() ? false : true);
		lockCustomerDTO.setDateUpdate(dateUpdate);
		customerDAL.lock(lockCustomerDTO);

		return "Có thể khoá một khách hàng";
	}

    // - Hàm lấy ra danh sách các khách hàng hiện có trong CSDL
	public ArrayList<CustomerDTO> getAllCustomer() {
		return customerDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các khách hàng hiện có với 1 điều kiện trong CSDL
	public ArrayList<CustomerDTO> getAllCustomerByCondition(String[] join, String condition, String order) {
		return customerDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một khách hàng với CCCD tương ứng
	public CustomerDTO getOneCustomerById(String id) {
		return customerDAL.selectOneById(id);
	}
}
