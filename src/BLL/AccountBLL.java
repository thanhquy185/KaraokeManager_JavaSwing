package BLL;

import java.util.ArrayList;

import DAL.AccountDAL;
import DTO.AccountDTO;
import PL.CommonPL;

public class AccountBLL {
	// Properties
	private PrivilegeBLL privilegeBLL;
	private AccountDAL accountDAL;

	// Constructors
	public AccountBLL() {
		privilegeBLL = new PrivilegeBLL();
		accountDAL = new AccountDAL();
	}

	// Methods
	// - Hàm kiểm tra mã người dùng đã được nhập hay chưa ?
	public boolean isInputedId(String id) {
		if (id == null) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra tên tài khoản đã được nhập hay chưa ?
	public boolean isInputedUsername(String username) {
		if (username == null) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra mật khẩu đã được nhập hay chưa ?
	public boolean isInputedPassword(String password) {
		if (password == null) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra quyền đã được chọn hay chưa ?
	public boolean isSelectedPrivilege(String privilege) {
		if (privilege == null) {
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

	// - Hàm kiểm tra mã người dùng đã hợp lệ hay chưa ?
	public boolean isValidId(String id) {
		if (!CommonBLL.isValidStringType04(id)) {
			return false;
		}
		return true;
	}

	// - Hàm kiểm tra họ và tên đã hợp lệ hay chưa ?
	public boolean isValidFullname(String fullname) {
		if (fullname != null && !CommonBLL.isValidStringType01(fullname)) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra số điện thoại đã hợp lệ hay chưa ?
	public boolean isValidPhone(String phone) {
		if (phone != null && (phone.length() != 10 || !CommonBLL.isValidStringType04(phone))) {
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

	// - Hàm kiểm tra tên tài khoản đã hợp lệ hay chưa ?
	public boolean isValidUsername(String username) {
		if (!CommonBLL.isValidStringType02(username)) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra mật khẩu đã hợp lệ hay chưa ?
	public boolean isValidPassword(String password) {
		if (!CommonBLL.isValidStringType02(password)) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra quyền đã hợp lệ hay chưa ?
	public boolean isValidPrivilege(String privilegeId) {
		PrivilegeBLL privilegeBLL = new PrivilegeBLL();
		if (privilegeBLL.getOnePrivilegeById(privilegeId) == null) {
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

	// - Hàm kiểm tra mã người dùng đã tồn tại hay chưa ?
	public boolean isExistsId(String id) {
		String[] join = null;
		String condition = String.format("maNguoiDung = '%s'", id);
		String order = null;
		if ((accountDAL.selectAllByCondition(join, condition, order)).size() == 0) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra số điện thoại đã tồn tại hay chưa ?
	public boolean isExistsPhone(String phone) {
		String[] join = null;
		String condition = String.format("soDienThoai = '%s'", phone);
		String order = null;
		if ((accountDAL.selectAllByCondition(join, condition, order)).size() == 0) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra email đã tồn tại hay chưa ?
	public boolean isExistsEmail(String email) {
		String[] join = null;
		String condition = String.format("email = '%s'", email);
		String order = null;
		if ((accountDAL.selectAllByCondition(join, condition, order)).size() == 0) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra tên tài khoản đã tồn tại hay chưa ?
	public boolean isExistsUsername(String username) {
		String[] join = null;
		String condition = String.format("tenTaiKhoan = '%s'", username);
		String order = null;
		if ((accountDAL.selectAllByCondition(join, condition, order)).size() == 0) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra tài khoản có tồn tại hay không ?
	public boolean isExistsAccount(String username, String password) {
		String[] join = null;
		String condition = String.format("tenTaiKhoan = '%s' AND matKhau = '%s'", username, password);
		String order = null;
		if ((accountDAL.selectAllByCondition(join, condition, order)).size() == 0) {
			return false;
		}

		return true;
	}

	// - Hàm kiểm tra tài khoản có đang bị khoá hay không ?
	public boolean isAccountLocked(String username, String password) {
		String[] join = null;
		String condition = String.format("tenTaiKhoan = '%s' AND matKhau = '%s'", username, password);
		String order = null;
		if (!(accountDAL.selectAllByCondition(join, condition, order)).get(0).getStatus()) {
			return false;
		}

		return true;
	}

	// - Hàm đăng nhập tài khoản
	public String loginApp(String username, String password) {
		// - Kiểm tra các trường hợp
		if (!isInputedUsername(username) && !isInputedPassword(password)) {
			return "Chưa nhập thông tin tài khoản";
		}
		if (!isInputedUsername(username)) {
			return "Chưa nhập tên tài khoản";
		}
		if (!isInputedPassword(password)) {
			return "Chưa nhập mật khẩu";
		}
		if (!isValidUsername(username) && !isValidPassword(password)) {
			return "Nhập sai định dạng thông tin tài khoản";
		}
		if (!isValidUsername(username)) {
			return "Nhập sai định dạng tên tài khoản";
		}
		if (!isValidPassword(password)) {
			return "Nhập sai định dạng mật khẩu";
		}
		if (!isExistsAccount(username, password)) {
			return "Tài khoản không tồn tại vì có thể nhập sai tên tài khoản hoặc mật khẩu, yêu cầu nhập lại";
		}
		if (!isAccountLocked(username, password)) {
			return "Tài khoản hiện tại đang bị khoá, vui lòng đợi mở khoá rồi đăng nhập lại";
		}

		// - Thoả hết thì tài khoản có thể đăng nhập
		String[] join = null;
		String condition = String.format("tenTaiKhoan = '%s' AND matKhau = '%s'", username, password);
		String order = null;
		CommonPL.setAccountUsingApp(accountDAL.selectAllByCondition(join, condition, order).get(0));

		return "Có thể đăng nhập";
	}

	// - Hàm tạo một người dùng
	public String insertAccount(String id, String fullname, String phone, String email, String address, String username,
			String password, String privilegeId, String status, String timeUpdate) {
		// - Kiểm tra các trường hợp
		if (!isInputedId(id) && !isInputedUsername(username) && !isInputedPassword(password)
				&& !isSelectedPrivilege(privilegeId) && !isSelectedStatus(status)) {
			return "Chưa nhập đầy đủ thông tin người dùng cần thiết";
		}
		if (!isInputedId(id)) {
			return "Chưa nhập mã người dùng";
		}
		if (!isInputedUsername(username)) {
			return "Chưa nhập tên tài khoản";
		}
		if (!isInputedPassword(password)) {
			return "Chưa nhập mật khẩu";
		}
		if (!isSelectedPrivilege(privilegeId)) {
			return "Chưa chọn quyền";
		}
		if (!isSelectedStatus(status)) {
			return "Chưa chọn trạng thái";
		}
		if (!isValidId(id) && !isValidFullname(fullname) && !isValidPhone(phone) && !isValidEmail(email)
				&& !isValidAddress(address) && !isValidUsername(username) && !isValidPassword(password)
				&& !isValidPrivilege(privilegeId) && !isValidStatus(status)) {
			return "Nhập sai định dạng thông tin người dùng";
		}
		if (!isValidId(id)) {
			return "Nhập sai định dạng mã người dùng";
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
		if (!isValidUsername(username)) {
			return "Nhập sai định dạng tên tài khoản";
		}
		if (!isValidPassword(password)) {
			return "Nhập sai định dạng mật khẩu";
		}
		if (!isValidPrivilege(privilegeId)) {
			return "Chọn sai định dạng quyền";
		}
		if (!isValidStatus(status)) {
			return "Chọn sai định dạng trạng thái";
		}
		if (isExistsId(id) && isExistsPhone(phone) && isExistsEmail(email) && isExistsUsername(username)) {
			return "Nhiều thông tin người dùng đã tồn tại";
		}
		if (isExistsId(id)) {
			return "Mã người dùng đã tồn tại";
		}
		if (isExistsPhone(phone)) {
			return "Số điện thoại đã tồn tại";
		}
		if (isExistsEmail(email)) {
			return "Email đã tồn tại";
		}
		if (isExistsUsername(username)) {
			return "Tên tài khoản đã tồn tại";
		}

		// - Nếu thoả mãn hết thì thêm vào CSDL
		int accountId = Integer.parseInt(id);
		String accountFullname = fullname;
		String accountPhone = phone;
		String accountEmail = email;
		String accountAddress = address;
		String accountUsername = username;
		String accountPassword = password;
		String accountPrivilegeId = privilegeId;
		boolean accountStatus = status.equals("Hoạt động") ? true : false;
		String accountTimeUpdate = timeUpdate;
		AccountDTO newAccountDTO = new AccountDTO(accountId, accountFullname, accountPhone, accountEmail,
				accountAddress, accountUsername, accountPassword, accountPrivilegeId, accountStatus, accountTimeUpdate);
		accountDAL.insert(newAccountDTO);

		return "Có thể thêm một người dùng";
	}

	// - Hàm cập nhật một người dùng
	public String updateAccount(String id, String fullname, String phone, String email, String address, String password,
			String privilegeId, String timeUpdate, String oldPhone, String oldEmail) {
		// - Kiểm tra các trường hợp
		if (!isInputedPassword(password) && !isSelectedPrivilege(privilegeId)) {
			return "Chưa nhập đầy đủ thông tin người dùng cần thiết";
		}
		if (!isInputedPassword(password)) {
			return "Chưa nhập mật khẩu";
		}
		if (!isSelectedPrivilege(privilegeId)) {
			return "Chưa chọn quyền";
		}
		if (!isValidFullname(fullname) && !isValidPhone(phone) && !isValidEmail(email) && !isValidAddress(address)
				&& !isValidPassword(password) && !isValidPrivilege(privilegeId)) {
			return "Nhập sai định dạng thông tin người dùng";
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
		if (!isValidPassword(password)) {
			return "Nhập sai định dạng mật khẩu";
		}
		if (!isValidPrivilege(privilegeId)) {
			return "Chọn sai định dạng quyền";
		}
		if (phone != null && oldPhone != null && !phone.equals(oldPhone) && isExistsPhone(phone) && email != null
				&& oldEmail != null && email.equals(oldEmail) && isExistsEmail(email)) {
			return "Nhiều thông tin người dùng đã tồn tại";
		}
		if (phone != null && oldPhone != null && !phone.equals(oldPhone) && isExistsPhone(phone)) {
			return "Số điện thoại đã tồn tại";
		}
		if (email != null
				&& oldEmail != null && email.equals(oldEmail) && isExistsEmail(email)) {
			return "Email đã tồn tại";
		}

		// - Nếu thoả mãn hết thì thay đổi vào CSDL
		int accountId = Integer.parseInt(id);
		String accountFullname = fullname;
		String accountPhone = phone;
		String accountEmail = email;
		String accountAddress = address;
		String accountUsername = null;
		String accountPassword = password;
		String accountPrivilegeId = privilegeId;
		Boolean accountStatus = null;
		String accountTimeUpdate = timeUpdate;
		AccountDTO updateAccountDTO = new AccountDTO(accountId, accountFullname, accountPhone, accountEmail,
				accountAddress, accountUsername, accountPassword, accountPrivilegeId, accountStatus, accountTimeUpdate);
		accountDAL.update(updateAccountDTO);

		return "Có thể thay đổi một người dùng";
	}

	// - Hàm khoá một người dùng
	public String lockAccount(String id, String timeUpdate) {
		// - Khoá hoặc mở khoá tuỳ vào trạng thái hiện tại
		AccountDTO lockAccountDTO = getOneAccountById(id);
		lockAccountDTO.setStatus(lockAccountDTO.getStatus() ? false : true);
		lockAccountDTO.setTimeUpdate(timeUpdate);
		accountDAL.lock(lockAccountDTO);

		return "Có thể khoá một người dùng";
	}

	// - Hàm lấy ra danh sách các người dùng hiện có trong CSDL
	public ArrayList<AccountDTO> getAllAccount() {
		return accountDAL.selectAll();
	}

	// - Hàm lấy ra danh sách các người dùng hiện có với 1 điều kiện trong CSDL
	public ArrayList<AccountDTO> getAllAccountByCondition(String[] join, String condition, String order) {
		return accountDAL.selectAllByCondition(join, condition, order);
	}

	// - Hàm lấy ra một người dùng với mã người dùng tương ứng
	public AccountDTO getOneAccountById(String id) {
		return accountDAL.selectOneById(id);
	}
}
