// package PL;

// import java.awt.Color;
// import java.awt.Font;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;
// import java.math.BigInteger;
// import java.text.ParseException;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.LinkedHashMap;
// import java.util.Map;
// import java.util.Vector;

// import javax.swing.BorderFactory;
// import javax.swing.JButton;
// import javax.swing.JComboBox;
// import javax.swing.JDialog;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.JTextField;
// import javax.swing.SwingConstants;
// import javax.swing.SwingUtilities;
// import javax.swing.UIManager;
// import javax.swing.table.DefaultTableModel;

// import org.jdesktop.swingx.JXDatePicker;

// import BLL.AccountBLL;
// import BLL.InputTicketBLL;
// import BLL.InputTicketDetailBLL;
// import BLL.PrivilegeDetailBLL;
// import BLL.SupplierBLL;
// import DTO.AccountDTO;
// import DTO.InputTicketDTO;
// import DTO.InputTicketDetailDTO;
// import DTO.FoodDTO;
// import DTO.PrivilegeDetailDTO;
// import DTO.SupplierDTO;
// import PL.CommonPL.CustomTextField;
// import BLL.FoodBLL;

// public class Admin_InputTicketManagerPL extends JPanel {
//     private InputTicketBLL inputTicketBLL;
//     private InputTicketDetailBLL inputTicketDetailBLL;
//     private FoodBLL foodBLL;
//     private SupplierBLL supplierBLL;
//     private AccountBLL accountBLL;
//     private PrivilegeDetailBLL privilegeDetailBLL;

//     private JLabel titleLabel;
//     private JLabel findLabel;
//     private JTextField findInputTextField;
//     private JButton findInformButton;
//     private JLabel sortLabel;
//     private Map<String, Boolean> sortCheckboxs;
//     private JButton sortButton;
//     private JLabel dateCreateLabel;
//     private JButton dateCreateInformButton;
//     private JXDatePicker dateCreateBeforeDatePicker;
//     private JLabel dateCreateRectangleLabel;
//     private JXDatePicker dateCreateAfterDatePicker;
//     private JLabel statusLabel;
//     private Map<String, Boolean> statusRadios;
//     private JButton statusButton;
//     private JButton filterApplyButton;
//     private JButton filterResetButton;
//     private JPanel filterPanel;

//     private JButton addButton;
//     private JButton updateButton;
//     private JTable tableData;
//     private JScrollPane tableScrollPane;
//     private JPanel dataPanel;

//     private final String[] columns = { "Mã phiếu", "Thời gian tạo phiếu", "Nhân viên tạo phiếu", "Nhà cung cấp", "Tổng tiền nhập (VNĐ)", "Trạng thái" };
//     private final int[] widthColumns = { 150, 200, 400, 400, 500, 200 };
//     private Object[][] datas = {};
//     private int rowSelected = -1;
//     private Boolean[] valueSelected = { null };

//     private JLabel addOrUpdateIdLabel;
//     private JTextField addOrUpdateIdTextField;
//     private JLabel addOrUpdateTimeCreateLabel;
//     private JTextField addOrUpdateTimeCreateTextField;
//     private JLabel addOrUpdateEmployeeLabel;
//     private JTextField addOrUpdateEmployeeTextField;
//     private JLabel addOrUpdateSupplierLabel;
//     private JComboBox<String> addOrUpdateSupplierComboBox;
//     private JLabel addOrUpdateTotalPriceLabel;
//     private JTextField addOrUpdateTotalPriceTextField;
//     private JLabel addOrUpdateStatusLabel;
//     private JTextField addOrUpdateStatusTextField;
//     private JButton addOrUpdateCompleteButton;
//     private JButton addOrUpdateDeleteButton;
//     private JButton addOrUpdateButton;
//     private JLabel addOrUpdateDataLabel;
//     private JButton addOrUpdateAddUnitButton;
//     private JButton addOrUpdateDeleteUnitButton;
//     private JTable addOrUpdateTableData;
//     private JScrollPane addOrUpdateTableScrollPane;
//     private JPanel addOrUpdateBlockPanel;
//     private JDialog addOrUpdateDialog;

//     private final String[] addOrUpdateColumns = { "Mã nguyên liệu", "Tên nguyên liệu", "Giá nhập (VNĐ)", "Số lượng", "Thành tiền (VNĐ)" };
//     private final int[] addOrUpdateWidthColumns = { 150, 400, 200, 200, 300 };
//     private Object[][] addOrUpdateDatas = {};
//     private int addOrUpdateRowSelected = -1;
//     private Boolean[] addOrUpdateValueSelected = { null };

//     private Vector<String> suppliersVector;
//     private Vector<String> foodsVector;
//     private Vector<String> employeesVector;

//     // - Các giá trị mặc định cho text field, text area hoặc combobox để tìm kiếm, thêm, sửa và xoá 
// 	private final Map<String, String> defaultValuesForCrud = new LinkedHashMap<String, String>() {{
// 	    put("id", "Nhập Mã phiếu");
// 	    put("timeCreate", "Thời gian tạo phiếu");
// 	    put("employee", "Chọn Nhân viên tạo phiếu");
// 	    put("supplier", "Chọn Nhà cung cấp");
// 	    put("totalPrice", "Nhập Tổng tiền nhập");
// 	    put("status", "Chọn Trạng thái");
//         put("food", "Chọn Nguyên liệu");
// 	}};

//     private final String[] sortsString = { "Mã phiếu tăng dần", "Mã phiếu giảm dần", "Thời gian tạo phiếu tăng dần", 
//         "Thời gian tạo phiếu giảm dần", "Tổng tiền tăng dần", "Tổng tiền giảm dần" };
//     private final String[] sortsSQL = { "maPhieuNhap ASC", "maPhieuNhap DESC", "thoiGianTaoPhieu ASC", "thoiGianTaoPhieu DESC", "tongTien ASC", "tongTien DESC" };
//     private final String[] statusString = { "Tất cả", "Đã nhập hàng", "Đang chờ xác nhận", "Đã huỷ phiếu" };
//     private final String[] statusSQL = { "", "trangThai = 1", "trangThai = 0", "trangThai = 2" };

//     public Admin_InputTicketManagerPL() {
//         inputTicketBLL = new InputTicketBLL();
//         inputTicketDetailBLL = new InputTicketDetailBLL();
//         foodBLL = new FoodBLL();
//         supplierBLL = new SupplierBLL();
//         accountBLL = new AccountBLL();
//         privilegeDetailBLL = new PrivilegeDetailBLL();

//         // Khởi tạo danh sách nguyên liệu
//         foodsVector = new Vector<>();
//         foodsVector.add(defaultValuesForCrud.get("food"));
//         ArrayList<FoodDTO> foods = foodBLL.getAllfoods();
//         for (FoodDTO food : foods) {
//             if (food.getStatus()) {
//                 foodsVector.add(food.getId() + " - " + food.getName());
//             }
//         }

//         // Khởi tạo danh sách nhà cung cấp
//         suppliersVector = new Vector<>();
//         suppliersVector.add(defaultValuesForCrud.get("supplier"));
//         ArrayList<SupplierDTO> suppliers = supplierBLL.getAllSuppliers();
//         for (SupplierDTO supplier : suppliers) {
//             if (supplier.getStatus()) {
//                 suppliersVector.add(supplier.getId() + " - " + supplier.getName());
//             }
//         }

//         // Khởi tạo danh sách nhân viên
//         employeesVector = new Vector<>();
//         employeesVector.add(defaultValuesForCrud.get("employee"));
//         ArrayList<AccountDTO> accounts = accountBLL.getAllAccount();
//         for (AccountDTO account : accounts) {
//             if (account.getStatus()) {
//                 String privilegeId = account.getPrivilegeId();
//                 if (privilegeId.equals("QUANLY")) {
//                     employeesVector.add(account.getId() + " - " + account.getFullname());
//                 } else {
//                     String[] join = null;
//                     String condition = String.format("maNguoiDung = '%s' AND maChucNang = 'PHIEUNHAP'", account.getId());
//                     String order = null;
//                     ArrayList<PrivilegeDetailDTO> privilegeDetails = privilegeDetailBLL.getAllPrivilegeDetailByCondition(join, condition, order);
//                     if (!privilegeDetails.isEmpty() && privilegeDetails.get(0).getStatus()) {
//                         employeesVector.add(account.getId() + " - " + account.getFullname());
//                     }
//                 }
//             }
//         }

//         // Cấu hình giao diện
//         titleLabel = CommonPL.getTitleLabel("Phiếu nhập", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER, SwingConstants.CENTER);
//         titleLabel.setBounds(30, 0, 1110, 115);

//         findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
//         findLabel.setBounds(15, 15, 90, 24);

//         findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm", "Bạn có thể tìm kiếm bằng mã phiếu nhập", Color.BLACK, CommonPL.getFontQuestionIcon());
//         findInformButton.setBounds(110, 15, 24, 24);

//         findInputTextField = new CommonPL.CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         findInputTextField.setBounds(15, 45, 360, 40);

//         sortLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
//         sortLabel.setBounds(390, 15, 360, 24);

//         sortCheckboxs = CommonPL.getMapHasValues(sortsString);
//         sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sortsString[0], Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         sortButton.setBounds(390, 45, 360, 40);

//         dateCreateLabel = CommonPL.getParagraphLabel("Ngày lập phiếu", Color.BLACK, CommonPL.getFontParagraphPlain());
//         dateCreateLabel.setBounds(765, 15, 135, 24);

//         dateCreateInformButton = CommonPL.getQuestionIconForm("?", "Chú thích sử dụng lọc Ngày lập phiếu", "Lọc những ngày trong khoảng từ Ngày trước đến Ngày sau", Color.BLACK, CommonPL.getFontQuestionIcon());
//         dateCreateInformButton.setBounds(913, 15, 24, 24);

//         dateCreateBeforeDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(), CommonPL.getDateFormat(), "Ngày trước", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(), 20, 40);
//         dateCreateBeforeDatePicker.setBounds(765, 45, 170, 40);

//         dateCreateRectangleLabel = CommonPL.getTitleLabel("-", Color.BLACK, CommonPL.getFontParagraphPlain(), SwingConstants.CENTER, SwingConstants.CENTER);
//         dateCreateRectangleLabel.setBounds(935, 45, 20, 40);

//         dateCreateAfterDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(), CommonPL.getDateFormat(), "Ngày sau", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(), 20, 40);
//         dateCreateAfterDatePicker.setBounds(955, 45, 170, 40);

//         statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
//         statusLabel.setBounds(15, 100, 360, 24);

//         statusRadios = CommonPL.getMapHasValues(statusString);
//         statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusRadios, statusString[0], Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         statusButton.setBounds(15, 130, 360, 40);

//         filterApplyButton = CommonPL.getRoundedBorderButton(20, "Lọc", Color.decode("#007bff"), Color.WHITE, CommonPL.getFontParagraphBold());
//         filterApplyButton.setBounds(765, 130, 170, 40);

//         filterResetButton = CommonPL.getRoundedBorderButton(20, "Đặt lại", Color.decode("#f44336"), Color.WHITE, CommonPL.getFontParagraphBold());
//         filterResetButton.setBounds(955, 130, 170, 40);

//         filterPanel = new CommonPL.RoundedPanel(12);
//         filterPanel.setLayout(null);
//         filterPanel.setBounds(30, 115, 1140, 185);
//         filterPanel.setBackground(Color.WHITE);
//         filterPanel.add(findLabel);
//         filterPanel.add(findInformButton);
//         filterPanel.add(findInputTextField);
//         filterPanel.add(sortLabel);
//         filterPanel.add(sortButton);
//         filterPanel.add(dateCreateLabel);
//         filterPanel.add(dateCreateInformButton);
//         filterPanel.add(dateCreateBeforeDatePicker);
//         filterPanel.add(dateCreateRectangleLabel);
//         filterPanel.add(dateCreateAfterDatePicker);
//         filterPanel.add(statusLabel);
//         filterPanel.add(statusButton);
//         filterPanel.add(filterApplyButton);
//         filterPanel.add(filterResetButton);

//         addButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5, CommonPL.getMiddlePathToShowIcon() + "add-icon.png", "Thêm", Color.BLACK, Color.decode("#699f4c"), Color.BLACK, Color.decode("#699f4c"), CommonPL.getFontParagraphBold());
//         addButton.setBounds(15, 15, 210, 40);

//         updateButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5, CommonPL.getMiddlePathToShowIcon() + "update-icon.png", "Thay đổi", Color.BLACK, Color.decode("#bf873e"), Color.BLACK, Color.decode("#bf873e"), CommonPL.getFontParagraphBold());
//         updateButton.setBounds(240, 15, 210, 40);

//         tableData = CommonPL.createTableData(columns, widthColumns, datas, "input ticket manager");
//         tableScrollPane = CommonPL.createScrollPane(true, true, tableData);
//         tableScrollPane.setBounds(15, 70, 1110, 400);

//         dataPanel = new CommonPL.RoundedPanel(12);
//         dataPanel.setLayout(null);
//         dataPanel.setBackground(Color.WHITE);
//         dataPanel.setBounds(30, 330, 1140, 485);
//         dataPanel.add(addButton);
//         dataPanel.add(updateButton);
//         dataPanel.add(tableScrollPane);

//         this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
//         this.setLayout(null);
//         this.add(titleLabel);
//         this.add(filterPanel);
//         this.add(dataPanel);

//         tableData.getSelectionModel().addListSelectionListener(e -> {
//             if (!e.getValueIsAdjusting()) {
//                 rowSelected = tableData.getSelectedRow();
//             }
//         });

//         addButton.addActionListener(e -> {
//             showAddOrUpdateDialog("Thêm Phiếu nhập", "Thêm", new Vector<>());
//             rowSelected = -1;
//             valueSelected[0] = false;
//             tableData.clearSelection();
//         });

//         updateButton.addActionListener(e -> {
//             if (rowSelected != -1) {
//                 Vector<Object> currentObject = new Vector<>();
//                 for (int i = 0; i < widthColumns.length; i++) {
//                     currentObject.add(tableData.getValueAt(rowSelected, i));
//                 }
//                 showAddOrUpdateDialog("Thay đổi Phiếu nhập", "Thay đổi", currentObject);
//             } else {
//                 CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần thay đổi");
//             }
//             rowSelected = -1;
//             valueSelected[0] = false;
//             tableData.clearSelection();
//         });

//         filterDatasInTable();
//         renderTableData(null, null, null);
//     }

//     private void renderTableData(String[] join, String condition, String order) {
//         ArrayList<InputTicketDTO> ticketList = inputTicketBLL.getAllInputTicketByCondition(join, condition, order);
//         Object[][] datasQuery = new Object[ticketList.size()][columns.length];
//         for (int i = 0; i < ticketList.size(); i++) {
//             InputTicketDTO ticket = ticketList.get(i);
//             AccountDTO accountDTO = accountBLL.getOneAccountById(String.valueOf(ticket.getEmployeeId()));
//             SupplierDTO supplierDTO = supplierBLL.getOneSupplierById(ticket.getSupplierId());

//             datasQuery[i][0] = String.valueOf(ticket.getId());
//             datasQuery[i][1] = ticket.getTimeCreate();
//             datasQuery[i][2] = accountDTO.getId() + " - " + accountDTO.getFullname();
//             datasQuery[i][3] = supplierDTO.getId() + " - " + supplierDTO.getName();
//             datasQuery[i][4] = CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(ticket.getTotalPrice()));
//             datasQuery[i][5] = ticket.getStatus() == 2 ? "Đã nhập hàng" :
//                               (ticket.getStatus() == 1 ? "Đã huỷ phiếu" : "Đang chờ xác nhận");
//         }
//         datas = datasQuery;
//         CommonPL.updateRowsInTableData(tableData, datas);
//     }

//     private void resetPage() {
//         findInputTextField.setText("Nhập thông tin");
//         findInputTextField.setForeground(Color.LIGHT_GRAY);
//         CommonPL.resetMapForFilter(sortCheckboxs, sortsString, sortButton);
//         CommonPL.resetMapForFilter(statusRadios, statusString, statusButton);
//         dateCreateBeforeDatePicker.setDate(null);
//         dateCreateAfterDatePicker.setDate(null);
//         renderTableData(null, null, null);
//     }

//     private void filterDatasInTable() {
//         filterApplyButton.addActionListener(e -> {
//             String findValue = !findInputTextField.getText().equals("Nhập thông tin") ? findInputTextField.getText() : null;
//             String sortValue = CommonPL.getSQLFromCheckboxs(sortCheckboxs, sortsSQL);
//             String statusValue = CommonPL.getSQLFromRadios(statusRadios, statusSQL);

//             String dateCreateCondition = "";
//             if (dateCreateBeforeDatePicker.getDate() != null && dateCreateAfterDatePicker.getDate() != null) {
//                 dateCreateCondition = String.format("thoiGianTaoPhieu BETWEEN '%s' AND '%s'",
//                         CommonPL.getDateFormat().format(dateCreateBeforeDatePicker.getDate()),
//                         CommonPL.getDateFormat().format(dateCreateAfterDatePicker.getDate()));
//             }

//             String condition = (findValue != null ? String.format("maPhieuNhap = '%s'", findValue) : "")
//                     + (dateCreateCondition.length() > 0 ? (findValue != null ? " AND " : "") + dateCreateCondition : "")
//                     + (statusValue != null ? (findValue != null || dateCreateCondition.length() > 0 ? " AND " : "") + statusValue : "");
//             if (condition.length() == 0) condition = null;

//             renderTableData(null, condition, sortValue != null && !sortValue.isEmpty() ? sortValue : null);
//         });

//         filterResetButton.addActionListener(e -> {
//             resetPage();
//         });
//     }

//     private void showAddfoodDialog() {
//         JDialog addfoodDialog = new JDialog();
//         addfoodDialog.setTitle("Thêm Nguyên liệu");
//         addfoodDialog.setSize(500, 320);
//         addfoodDialog.setLayout(null);
//         addfoodDialog.setModal(true);
//         addfoodDialog.setLocationRelativeTo(addOrUpdateDialog);

//         JLabel foodLabel = CommonPL.getParagraphLabel("Nguyên liệu", Color.BLACK, CommonPL.getFontParagraphPlain());
//         foodLabel.setBounds(20, 10, 460, 40);

//         JComboBox<String> foodComboBox = CommonPL.CustomComboBox(foodsVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         foodComboBox.setBounds(20, 50, 460, 40);

//         JLabel priceLabel = CommonPL.getParagraphLabel("Giá nhập", Color.BLACK, CommonPL.getFontParagraphPlain());
//         priceLabel.setBounds(20, 100, 220, 40);

//         JTextField priceField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Giá nhập", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         priceField.setBounds(20, 140, 220, 40);

//         JLabel quantityLabel = CommonPL.getParagraphLabel("Số lượng", Color.BLACK, CommonPL.getFontParagraphPlain());
//         quantityLabel.setBounds(260, 100, 220, 40);

//         JTextField quantityField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Số lượng", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         quantityField.setBounds(260, 140, 220, 40);

//         JButton confirmButton = CommonPL.getButtonDefaultForm("Thêm", CommonPL.getFontParagraphBold());
//         confirmButton.setBounds(20, 230, 460, 40);

//         confirmButton.addActionListener(evt -> {
//             String selectedfood = (String) foodComboBox.getSelectedItem();
//             if (selectedfood.equals("Chọn nguyên liệu")) {
//                 CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn nguyên liệu");
//                 return;
//             }
//             String[] parts = selectedfood.split(" - ", 2);
//             String foodId = parts[0];
//             String foodName = parts.length > 1 ? parts[1] : "Không xác định";
//             Long price;
//             Integer quantity;
//             try {
//                 price = Long.parseLong(priceField.getText());
//                 quantity = Integer.parseInt(quantityField.getText());
//             } catch (NumberFormatException ex) {
//                 CommonPL.createErrorDialog("Thông báo lỗi", "Giá nhập và số lượng phải là số hợp lệ");
//                 return;
//             }
//             if (price < 0 || quantity < 0) {
//                 CommonPL.createErrorDialog("Thông báo lỗi", "Giá nhập và số lượng không được âm");
//                 return;
//             }

//             Long totaltotalPrice = price * quantity;
//             Vector<Object> newRow = new Vector<>();
//             newRow.add(foodId);
//             newRow.add(foodName);
//             newRow.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(price)));
//             newRow.add(quantity);
//             newRow.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(totaltotalPrice)));
//             ((DefaultTableModel) addOrUpdateTableData.getModel()).addRow(newRow);

//             long total = 0;
//             for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
//                 total += CommonPL.moneyFormatToMoneyLong((String) addOrUpdateTableData.getValueAt(i, 4));
//             }
//             addOrUpdateTotalPriceTextField.setText(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(total)));
//             ((CustomTextField) addOrUpdateTotalPriceTextField).setBorderColor(Color.decode("#dedede"));

//             addfoodDialog.dispose();
//         });

//         JPanel addfoodPanel = new JPanel();
//         addfoodPanel.setLayout(null);
//         addfoodPanel.setBackground(Color.WHITE);
//         addfoodPanel.setBounds(0, 0, 500, 320);
//         addfoodPanel.add(foodLabel);
//         addfoodPanel.add(foodComboBox);
//         addfoodPanel.add(priceLabel);
//         addfoodPanel.add(priceField);
//         addfoodPanel.add(quantityLabel);
//         addfoodPanel.add(quantityField);
//         addfoodPanel.add(confirmButton);

//         addfoodDialog.add(addfoodPanel);
//         addfoodDialog.setVisible(true);
//     }

//     private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
//         addOrUpdateIdLabel = CommonPL.getParagraphLabel("Mã phiếu", Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateIdLabel.setBounds(20, 10, 220, 40);

//         addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("id"), Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateIdTextField.setHorizontalAlignment(JTextField.CENTER);
//         addOrUpdateIdTextField.setEnabled(false);
//         addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
//         addOrUpdateIdTextField.setBounds(20, 50, 220 , 40);

//         addOrUpdateTimeCreateLabel = CommonPL.getParagraphLabel("Ngày lập phiếu", Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateTimeCreateLabel.setBounds(260, 10, 220, 40);

//         addOrUpdateTimeCreateTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("timeCreate"), Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateTimeCreateTextField.setHorizontalAlignment(JTextField.CENTER);
//         addOrUpdateTimeCreateTextField.setEnabled(false);
//         addOrUpdateTimeCreateTextField.setBackground(Color.decode("#dedede"));
//         addOrUpdateTimeCreateTextField.setBounds(260, 50, 220, 40);

//         addOrUpdateEmployeeLabel = CommonPL.getParagraphLabel("Nhân viên lập phiếu", Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateEmployeeLabel.setBounds(500, 10, 460, 40);

//         addOrUpdateEmployeeTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("employee"), Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateEmployeeTextField.setEnabled(false);
//         addOrUpdateEmployeeTextField.setBackground(Color.decode("#dedede"));
//         addOrUpdateEmployeeTextField.setBounds(500, 50, 460, 40);

//         addOrUpdateTotalPriceLabel = CommonPL.getParagraphLabel("Tổng tiền nhập (VNĐ)", Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateTotalPriceLabel.setBounds(20, 100, 220, 40);

//         addOrUpdateTotalPriceTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("totalPrice"), Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateTotalPriceTextField.setEnabled(false);
//         addOrUpdateTotalPriceTextField.setBackground(Color.decode("#dedede"));
//         ((CustomTextField) addOrUpdateTotalPriceTextField).setBorderColor(Color.decode("#dedede"));
//         addOrUpdateTotalPriceTextField.setBounds(20, 140, 220, 40);

//         addOrUpdateStatusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateStatusLabel.setBounds(260, 100, 220, 40);

//         addOrUpdateStatusTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("status"), Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateStatusTextField.setEnabled(false);
//         addOrUpdateStatusTextField.setBackground(Color.decode("#dedede"));
//         addOrUpdateStatusTextField.setBounds(260, 140, 220, 40);

//         addOrUpdateSupplierLabel = CommonPL.getParagraphLabel("Nhà cung cấp", Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateSupplierLabel.setBounds(500, 100, 460, 40);

//         addOrUpdateSupplierComboBox = CommonPL.CustomComboBox(suppliersVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateSupplierComboBox.setBounds(500, 140, 460, 40);

//         addOrUpdateDataLabel = CommonPL.getParagraphLabel("Danh sách nguyên liệu", Color.BLACK, CommonPL.getFontParagraphPlain());
//         addOrUpdateDataLabel.setBounds(20, 190, 940, 40);

//         addOrUpdateAddUnitButton = CommonPL.getButtonHasIcon(40, 28, 20, 20, 10, 4, CommonPL.getMiddlePathToShowIcon() + "plus-icon.png", null, null, null, null, null, new Font("Arial", Font.BOLD, 14));
//         addOrUpdateAddUnitButton.setOpaque(true);
//         addOrUpdateAddUnitButton.setBackground(Color.BLACK);
//         addOrUpdateAddUnitButton.addMouseListener(new MouseAdapter() {
//             @Override
//             public void mouseEntered(MouseEvent evt) {
//                 addOrUpdateAddUnitButton.setBorder(BorderFactory.createLineBorder(Color.decode("#42A5F5"), 2));
//                 addOrUpdateAddUnitButton.setBackground(Color.decode("#42A5F5"));
//             }

//             @Override
//             public void mouseExited(MouseEvent evt) {
//                 addOrUpdateAddUnitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//                 addOrUpdateAddUnitButton.setBackground(Color.BLACK);
//             }
//         });
//         addOrUpdateAddUnitButton.setBounds(874, 196, 40, 28);

//         addOrUpdateDeleteUnitButton = CommonPL.getButtonHasIcon(40, 28, 20, 20, 10, 4, CommonPL.getMiddlePathToShowIcon() + "minus-icon.png", null, null, null, null, null, new Font("Arial", Font.BOLD, 14));
//         addOrUpdateDeleteUnitButton.setOpaque(true);
//         addOrUpdateDeleteUnitButton.setBackground(Color.BLACK);
//         addOrUpdateDeleteUnitButton.addMouseListener(new MouseAdapter() {
//             @Override
//             public void mouseEntered(MouseEvent evt) {
//                 addOrUpdateDeleteUnitButton.setBorder(BorderFactory.createLineBorder(Color.decode("#42A5F5"), 2));
//                 addOrUpdateDeleteUnitButton.setBackground(Color.decode("#42A5F5"));
//             }

//             @Override
//             public void mouseExited(MouseEvent evt) {
//                 addOrUpdateDeleteUnitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//                 addOrUpdateDeleteUnitButton.setBackground(Color.BLACK);
//             }
//         });
//         addOrUpdateDeleteUnitButton.setBounds(920, 196, 40, 28);

//         addOrUpdateTableData = CommonPL.createTableData(addOrUpdateColumns, addOrUpdateWidthColumns, addOrUpdateDatas, "add or update unit table");
//         addOrUpdateTableScrollPane = CommonPL.createScrollPane(true, true, addOrUpdateTableData);
//         addOrUpdateTableScrollPane.setBounds(20, 230, 940, 400);

//         addOrUpdateCompleteButton = CommonPL.getRoundedBorderButton(20, "Hoàn thành", Color.decode("#33CC00"), Color.WHITE, CommonPL.getFontParagraphBold());
//         addOrUpdateCompleteButton.setBounds(260, 660, 220, 40);
//         addOrUpdateCompleteButton.setVisible(false);

//         addOrUpdateDeleteButton = CommonPL.getRoundedBorderButton(20, "Hủy phiếu", Color.decode("#EE0000"), Color.WHITE, CommonPL.getFontParagraphBold());
//         addOrUpdateDeleteButton.setBounds(500, 660, 220, 40);
//         addOrUpdateDeleteButton.setVisible(false);

//         addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button, button.equals("Thêm") ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE, CommonPL.getFontParagraphBold());
//         addOrUpdateButton.setBounds(260, 660, 460, 40);
//         SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

//         addOrUpdateAddUnitButton.addActionListener(e -> showAddfoodDialog());

//         addOrUpdateDeleteUnitButton.addActionListener(e -> {
//             int selectedRow = addOrUpdateTableData.getSelectedRow();
//             if (selectedRow != -1) {
//                 ((DefaultTableModel) addOrUpdateTableData.getModel()).removeRow(selectedRow);

//                 long total = 0;
//                 for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
//                     total += CommonPL.moneyFormatToMoneyLong((String) addOrUpdateTableData.getValueAt(i, 4));
//                 }
//                 addOrUpdateTotalPriceTextField.setText(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(total)));
//                 ((CustomTextField) addOrUpdateTotalPriceTextField).setBorderColor(Color.decode("#dedede"));
//             } else {
//                 CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn một nguyên liệu để xóa");
//             }
//         });

//         if (title.equals("Thêm Phiếu nhập") && button.equals("Thêm") && object.isEmpty()) {
//             addOrUpdateIdTextField.setText(String.valueOf(inputTicketBLL.getLastInputTicket().getId() + 1));
//             ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));

//             addOrUpdateTimeCreateTextField.setText(CommonPL.getCurrentDatetime());
//             ((CustomTextField) addOrUpdateTimeCreateTextField).setBorderColor(Color.decode("#dedede"));
            
//             AccountDTO currentUser = CommonPL.getAccountUsingApp();
//             addOrUpdateEmployeeTextField.setText(currentUser.getId() + " - " + currentUser.getFullname());
//             ((CustomTextField) addOrUpdateEmployeeTextField).setBorderColor(Color.decode("#dedede"));

//             addOrUpdateStatusTextField.setText("Đang chờ xác nhận");
//             ((CustomTextField) addOrUpdateStatusTextField).setBorderColor(Color.decode("#dedede"));
//         }

//         if (title.equals("Thay đổi Phiếu nhập") && button.equals("Thay đổi") && !object.isEmpty()) {
//             addOrUpdateButton.setVisible(false);

//             if(object.get(0) != null) {
//                 addOrUpdateIdTextField.setText(String.valueOf(object.get(0)));
//                 ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
//             }

//             if(object.get(1) != null) {
//                 addOrUpdateTimeCreateTextField.setText((String) object.get(1));
//                 ((CustomTextField) addOrUpdateTimeCreateTextField).setBorderColor(Color.decode("#dedede"));
//             }

//             if(object.get(2) != null) {
//                 addOrUpdateEmployeeTextField.setText((String) object.get(2));
//                 ((CustomTextField) addOrUpdateEmployeeTextField).setBorderColor(Color.decode("#dedede"));
//             }
        
//             if(object.get(3) != null) {
//                 addOrUpdateSupplierComboBox.setSelectedItem((String) object.get(3));
//             }

//             if(object.get(4) != null) {
//                 addOrUpdateTotalPriceTextField.setText((String) object.get(4));
//                 ((CustomTextField) addOrUpdateTotalPriceTextField).setBorderColor(Color.decode("#dedede"));
//             }

//             if(object.get(5) != null) {
//                 addOrUpdateStatusTextField.setText((String) object.get(5));
//                 ((CustomTextField) addOrUpdateStatusTextField).setBorderColor(Color.decode("#dedede"));

//                 String status = String.valueOf(object.get(5));
//                 addOrUpdateAddUnitButton.setVisible(false);
//                 addOrUpdateDeleteUnitButton.setVisible(false);
//                 if (status.equals("Đã nhập hàng") || status.equals("Đã huỷ phiếu")) {
//                     addOrUpdateCompleteButton.setVisible(false);
//                     addOrUpdateDeleteButton.setVisible(false);
//                 } else if (status.equals("Đang chờ xác nhận")) {
//                     addOrUpdateCompleteButton.setVisible(true);
//                     addOrUpdateDeleteButton.setVisible(true);
//                 }
//             }

//             ArrayList<InputTicketDetailDTO> details = inputTicketDetailBLL.getAllInputTicketDetailByCondition(
//                     null, String.format("maPhieuNhap = %s", object.get(0) != null ? object.get(0) : "0"), null);
//             for (InputTicketDetailDTO detail : details) {
//                 FoodDTO food = foodBLL.getOneFoodById(detail.getFoodId());
//                 String foodName = food != null ? food.getName() : "Không xác định";

//                 Vector<Object> row = new Vector<>();
//                 row.add(detail.getFoodId());
//                 row.add(foodName);
//                 row.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(detail.getInputPrice())));
//                 row.add(detail.getInputQuantity());
//                 row.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(detail.getInputPrice() * detail.getInputQuantity())));

//                 ((DefaultTableModel) addOrUpdateTableData.getModel()).addRow(row);
//             }
//         }

//         // addOrUpdateButton.addActionListener(e -> {
//         //     String id = addOrUpdateIdTextField.getText();
//         //     Integer ticketId;
//         //     try {
//         //         ticketId = Integer.parseInt(id);
//         //     } catch (NumberFormatException ex) {
//         //         CommonPL.createErrorDialog("Thông báo lỗi", "Mã phiếu phải là số hợp lệ");
//         //         return;
//         //     }

//         //     String dateCreate = addOrUpdateTimeCreateTextField.getDate() != null ?
//         //             CommonPL.getDateFormat().format(addOrUpdateTimeCreateTextField.getDate()) : null;
//         //     String supplier = (String) addOrUpdateSupplierComboBox.getSelectedItem();
//         //     String supplierId = supplier.equals("Chọn Nhà cung cấp") ? null : supplier.split(" - ")[0];
//         //     Long totalPrice;
//         //     try {
//         //         totalPrice = CommonPL.moneyFormatToMoneyLong(addOrUpdateTotalPriceTextField.getText());
//         //     } catch (NumberFormatException ex) {
//         //         CommonPL.createErrorDialog("Thông báo lỗi", "Tổng tiền phải là số hợp lệ");
//         //         return;
//         //     }
//         //     Integer status = addOrUpdateStatusTextField.getText().equals("Đang chờ xác nhận") ? 0 :
//         //                     (addOrUpdateStatusTextField.getText().equals("Đã nhập hàng") ? 1 : 2);
//         //     String employee = (String) addOrUpdateEmployeeTextField.getSelectedItem();
//         //     Integer employeeId = employee.equals("Chọn Nhân viên") ? null : Integer.parseInt(employee.split(" - ")[0]);

//         //     if (employeeId == null || dateCreate == null || supplierId == null) {
//         //         CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng nhập đầy đủ thông tin bắt buộc");
//         //         return;
//         //     }

//         //     if (((DefaultTableModel) addOrUpdateTableData.getModel()).getRowCount() == 0) {
//         //         CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng thêm ít nhất một nguyên liệu");
//         //         return;
//         //     }

//         //     String result;
//         //     if (title.equals("Thêm Phiếu nhập")) {
//         //         result = inputTicketBLL.insertInputTicket(ticketId, dateCreate, employeeId, supplierId, totalPrice, status);
//         //         if (result.equals("Thêm phiếu nhập thành công")) {
//         //             for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
//         //                 String foodId = (String) addOrUpdateTableData.getValueAt(i, 0);
//         //                 Long price = CommonPL.moneyFormatToMoneyLong((String) addOrUpdateTableData.getValueAt(i, 2));
//         //                 Integer quantity = (Integer) addOrUpdateTableData.getValueAt(i, 3);
//         //                 String detailResult = inputTicketDetailBLL.insertInputTicketDetail(ticketId, foodId, price, quantity);
//         //                 if (!detailResult.equals("Thêm chi tiết phiếu nhập thành công")) {
//         //                     CommonPL.createErrorDialog("Thông báo lỗi", "Lỗi khi thêm chi tiết: " + detailResult);
//         //                     return;
//         //                 }
//         //             }
//         //             CommonPL.createSuccessDialog("Thông báo thành công", "Thêm phiếu nhập thành công");
//         //             addOrUpdateDialog.dispose();
//         //             resetPage();
//         //         } else {
//         //             CommonPL.createErrorDialog("Thông báo lỗi", result);
//         //         }
//         //     } else {
//         //         result = inputTicketBLL.updateInputTicket(ticketId, dateCreate, employeeId, supplierId, totalPrice, status);
//         //         if (result.equals("Cập nhật phiếu nhập thành công")) {
//         //             ArrayList<InputTicketDetailDTO> oldDetails = inputTicketDetailBLL.getAllInputTicketDetailByCondition(
//         //                     null, "maPhieuNhap = " + ticketId, null);
//         //             for (InputTicketDetailDTO detail : oldDetails) {
//         //                 inputTicketDetailBLL.updateInputTicketDetail(detail.getId(), detail.getfoodId(), 0L, 0);
//         //             }
//         //             for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
//         //                 String foodId = (String) addOrUpdateTableData.getValueAt(i, 0);
//         //                 Long price = CommonPL.moneyFormatToMoneyLong((String) addOrUpdateTableData.getValueAt(i, 2));
//         //                 Integer quantity = (Integer) addOrUpdateTableData.getValueAt(i, 3);
//         //                 String detailResult = inputTicketDetailBLL.insertInputTicketDetail(ticketId, foodId, price, quantity);
//         //                 if (!detailResult.equals("Thêm chi tiết phiếu nhập thành công")) {
//         //                     CommonPL.createErrorDialog("Thông báo lỗi", "Lỗi khi cập nhật chi tiết: " + detailResult);
//         //                     return;
//         //                 }
//         //             }
//         //             CommonPL.createSuccessDialog("Thông báo thành công", "Cập nhật phiếu nhập thành công");
//         //             addOrUpdateDialog.dispose();
//         //             renderTableData(null, null, null);
//         //         } else {
//         //             CommonPL.createErrorDialog("Thông báo lỗi", result);
//         //         }
//         //     }
//         // });

//         addOrUpdateCompleteButton.addActionListener(e -> {
//             String id = addOrUpdateIdTextField.getText();
//             String result = inputTicketBLL.updateStatusInputTicket(id,2);
//             if (result.equals("Thay đổi trạng thái phiếu nhập thành công")) {
//                 CommonPL.createSuccessDialog("Thông báo thành công", "Phiếu nhập đã nhập hàng");
//                 addOrUpdateDialog.dispose();
//                 resetPage();
//             } else {
//                 CommonPL.createErrorDialog("Thông báo lỗi", result);
//             }
//         });

//         addOrUpdateDeleteButton.addActionListener(e -> {
//             CommonPL.createSelectionsDialog("Thông báo lựa chọn", "Có chắc chắn muốn hủy phiếu này?", valueSelected);
//             if (valueSelected[0]) {
//                 String id = addOrUpdateIdTextField.getText();
//                 String result = inputTicketBLL.updateStatusInputTicket(id,1);
//                 if (result.equals("Thay đổi trạng thái phiếu nhập thành công")) {
//                     CommonPL.createSuccessDialog("Thông báo thành công", "Phiếu nhập đã huỷ");
//                     addOrUpdateDialog.dispose();
//                     resetPage();
//                 } else {
//                     CommonPL.createErrorDialog("Thông báo lỗi", result);
//                 }
//             }
//             valueSelected[0] = false;
//         });

//         addOrUpdateBlockPanel = new JPanel();
//         addOrUpdateBlockPanel.setLayout(null);
//         addOrUpdateBlockPanel.setBounds(0, 0, 980, object.get(5).equals("Đang chờ xác nhận") ? 750 : 680);
//         addOrUpdateBlockPanel.setBackground(Color.WHITE);
//         addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
//         addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
//         addOrUpdateBlockPanel.add(addOrUpdateTimeCreateLabel);
//         addOrUpdateBlockPanel.add(addOrUpdateTimeCreateTextField);
//         addOrUpdateBlockPanel.add(addOrUpdateEmployeeLabel);
//         addOrUpdateBlockPanel.add(addOrUpdateEmployeeTextField);
//         addOrUpdateBlockPanel.add(addOrUpdateDataLabel);
//         addOrUpdateBlockPanel.add(addOrUpdateAddUnitButton);
//         addOrUpdateBlockPanel.add(addOrUpdateDeleteUnitButton);
//         addOrUpdateBlockPanel.add(addOrUpdateTableScrollPane);
//         addOrUpdateBlockPanel.add(addOrUpdateSupplierLabel);
//         addOrUpdateBlockPanel.add(addOrUpdateSupplierComboBox);
//         addOrUpdateBlockPanel.add(addOrUpdateTotalPriceLabel);
//         addOrUpdateBlockPanel.add(addOrUpdateTotalPriceTextField);
//         addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
//         addOrUpdateBlockPanel.add(addOrUpdateStatusTextField);
//         addOrUpdateBlockPanel.add(addOrUpdateCompleteButton);
//         addOrUpdateBlockPanel.add(addOrUpdateDeleteButton);
//         addOrUpdateBlockPanel.add(addOrUpdateButton);

//         addOrUpdateDialog = new JDialog();
//         addOrUpdateDialog.setTitle(title);
//         addOrUpdateDialog.setLayout(null);
//         addOrUpdateDialog.setSize(980, object.get(5).equals("Đang chờ xác nhận") ? 750 : 680);
//         addOrUpdateDialog.setResizable(false);
//         addOrUpdateDialog.setLocationRelativeTo(null);
//         addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//         addOrUpdateDialog.add(addOrUpdateBlockPanel);
//         addOrUpdateDialog.setModal(true);
//         addOrUpdateDialog.setVisible(true);
//     }
// }