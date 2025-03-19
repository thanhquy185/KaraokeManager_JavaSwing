package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXDatePicker;

import BLL.AccountBLL;
import BLL.InputTicketBLL;
import BLL.InputTicketDetailBLL;
import BLL.PrivilegeDetailBLL;
import BLL.SupplierBLL;
import DTO.AccountDTO;
import DTO.InputTicketDTO;
import DTO.InputTicketDetailDTO;
import DTO.IngredientDTO;
import DTO.PrivilegeDetailDTO;
import DTO.SupplierDTO;
import PL.CommonPL.CustomTextField;
import BLL.IngredientBLL;

public class Admin_InputTicketManagerPL extends JPanel {
    private InputTicketBLL inputTicketBLL;
    private InputTicketDetailBLL inputTicketDetailBLL;
    private IngredientBLL ingredientBLL;
    private SupplierBLL supplierBLL;
    private AccountBLL accountBLL;
    private PrivilegeDetailBLL privilegeDetailBLL;

    private JLabel titleLabel;
    private JLabel findLabel;
    private JTextField findInputTextField;
    private JButton findInformButton;
    private JLabel sortLabel;
    private Map<String, Boolean> sortCheckboxs;
    private JButton sortButton;
    private JLabel dateCreateLabel;
    private JButton dateCreateInformButton;
    private JXDatePicker dateCreateBeforeDatePicker;
    private JLabel dateCreateRectangleLabel;
    private JXDatePicker dateCreateAfterDatePicker;
    private JLabel dateContractLabel;
    private JButton dateContractInformButton;
    private JXDatePicker dateContractBeforeDatePicker;
    private JLabel dateContractRectangleLabel;
    private JXDatePicker dateContractAfterDatePicker;
    private JLabel statusLabel;
    private Map<String, Boolean> statusRadios;
    private JButton statusButton;
    private JButton filterApplyButton;
    private JButton filterResetButton;
    private JPanel filterPanel;

    private JButton addButton;
    private JButton updateButton;
    private JTable tableData;
    private JScrollPane tableScrollPane;
    private JPanel dataPanel;

    private final String[] columns = { "Mã phiếu", "Nhà cung cấp", "Ngày lập phiếu", "Ngày cập nhật", "Nhân viên lập phiếu", "Tổng tiền (VNĐ)", "Trạng thái" };
    private final int[] widthColumns = { 150, 300, 150, 150, 300, 300, 150 };
    private Object[][] datas = {};
    private int rowSelected = -1;
    private Boolean[] valueSelected = { null };

    private JLabel addOrUpdateIdLabel;
    private JTextField addOrUpdateIdTextField;
    private JLabel addOrUpdateDateCreateLabel;
    private JXDatePicker addOrUpdateDateCreateDatePicker;
    private JLabel addOrUpdateDateContractLabel;
    private JXDatePicker addOrUpdateDateContractDatePicker;
    private JLabel addOrUpdateEmployeeLabel;
    private JComboBox<String> addOrUpdateEmployeeComboBox;
    private JLabel addOrUpdateSupplierLabel;
    private JComboBox<String> addOrUpdateSupplierComboBox;
    private JLabel addOrUpdateCostLabel;
    private JTextField addOrUpdateCostTextField;
    private JLabel addOrUpdateStatusLabel;
    private JTextField addOrUpdateStatusTextField;
    private JButton addOrUpdateCompleteButton;
    private JButton addOrUpdateDeleteButton;
    private JButton addOrUpdateButton;
    private JPanel addOrUpdateLinePanel;
    private JLabel addOrUpdateDataLabel;
    private JButton addOrUpdateAddUnitButton;
    private JButton addOrUpdateDeleteUnitButton;
    private JTable addOrUpdateTableData;
    private JScrollPane addOrUpdateTableScrollPane;
    private JPanel addOrUpdateBlockPanel;
    private JDialog addOrUpdateDialog;

    private final String[] addOrUpdateColumns = { "Mã nguyên liệu", "Tên nguyên liệu", "Giá nhập (VNĐ)", "Số lượng", "Thành tiền (VNĐ)" };
    private final int[] addOrUpdateWidthColumns = { 150, 250, 200, 200, 250 };
    private Object[][] addOrUpdateDatas = {};
    private int addOrUpdateRowSelected = -1;
    private Boolean[] addOrUpdateValueSelected = { null };

    private Vector<String> suppliersVector;
    private Vector<String> ingredientsVector;
    private Vector<String> employeesVector;

    private final String[] sortsString = { "Mã phiếu tăng dần", "Mã phiếu giảm dần", "Tổng tiền tăng dần", "Tổng tiền giảm dần" };
    private final String[] sortsSQL = { "maPhieuNhap ASC", "maPhieuNhap DESC", "tongTien ASC", "tongTien DESC" };
    private final String[] statusString = { "Tất cả", "Đã hoàn thành", "Đang chờ xác nhận", "Đã huỷ phiếu" };
    private final String[] statusSQL = { "", "trangThai = 1", "trangThai = 0 AND isCancelled = 0", "isCancelled = 1" };

    public Admin_InputTicketManagerPL() {
        inputTicketBLL = new InputTicketBLL();
        inputTicketDetailBLL = new InputTicketDetailBLL();
        ingredientBLL = new IngredientBLL();
        supplierBLL = new SupplierBLL();
        accountBLL = new AccountBLL();
        privilegeDetailBLL = new PrivilegeDetailBLL();

        // Khởi tạo danh sách nguyên liệu
        ingredientsVector = new Vector<>();
        ingredientsVector.add("Chọn nguyên liệu");
        ArrayList<IngredientDTO> ingredients = ingredientBLL.getAllIngredients();
        for (IngredientDTO ingredient : ingredients) {
            if (ingredient.getStatus()) {
                ingredientsVector.add(ingredient.getId() + " - " + ingredient.getName());
            }
        }

        // Khởi tạo danh sách nhà cung cấp
        suppliersVector = new Vector<>();
        suppliersVector.add("Chọn Nhà cung cấp");
        ArrayList<SupplierDTO> suppliers = supplierBLL.getAllSuppliers();
        for (SupplierDTO supplier : suppliers) {
            if (supplier.getStatus()) {
                suppliersVector.add(supplier.getId() + " - " + supplier.getName());
            }
        }

        // Khởi tạo danh sách nhân viên
        employeesVector = new Vector<>();
        employeesVector.add("Chọn Nhân viên");
        ArrayList<AccountDTO> accounts = accountBLL.getAllAccount();
        for (AccountDTO account : accounts) {
            if (account.getStatus()) {
                String privilegeId = account.getPrivilegeId();
                if (privilegeId.equals("QUANLY")) {
                    employeesVector.add(account.getId() + " - " + account.getFullname());
                } else {
                    String[] join = null;
                    String condition = String.format("maNguoiDung = '%s' AND maChucNang = 'PHIEUNHAP'", account.getId());
                    String order = null;
                    ArrayList<PrivilegeDetailDTO> privilegeDetails = privilegeDetailBLL.getAllPrivilegeDetailByCondition(join, condition, order);
                    if (!privilegeDetails.isEmpty() && privilegeDetails.get(0).getStatus()) {
                        employeesVector.add(account.getId() + " - " + account.getFullname());
                    }
                }
            }
        }

        // Cấu hình giao diện
        titleLabel = CommonPL.getTitleLabel("Phiếu nhập", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER, SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, 1110, 115);

        findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
        findLabel.setBounds(15, 15, 90, 24);

        findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm", "Bạn có thể tìm kiếm bằng mã phiếu nhập", Color.BLACK, CommonPL.getFontQuestionIcon());
        findInformButton.setBounds(110, 15, 24, 24);

        findInputTextField = new CommonPL.CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        findInputTextField.setBounds(15, 45, 360, 40);

        sortLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
        sortLabel.setBounds(390, 15, 360, 24);

        sortCheckboxs = CommonPL.getMapHasValues(sortsString);
        sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sortsString[0], Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        sortButton.setBounds(390, 45, 360, 40);

        dateCreateLabel = CommonPL.getParagraphLabel("Ngày lập phiếu", Color.BLACK, CommonPL.getFontParagraphPlain());
        dateCreateLabel.setBounds(765, 15, 135, 24);

        dateCreateInformButton = CommonPL.getQuestionIconForm("?", "Chú thích sử dụng lọc Ngày lập phiếu", "Lọc những ngày trong khoảng từ Ngày trước đến Ngày sau", Color.BLACK, CommonPL.getFontQuestionIcon());
        dateCreateInformButton.setBounds(913, 15, 24, 24);

        dateCreateBeforeDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(), CommonPL.getDateFormat(), "Ngày trước", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(), 20, 40);
        dateCreateBeforeDatePicker.setBounds(765, 45, 170, 40);

        dateCreateRectangleLabel = CommonPL.getTitleLabel("-", Color.BLACK, CommonPL.getFontParagraphPlain(), SwingConstants.CENTER, SwingConstants.CENTER);
        dateCreateRectangleLabel.setBounds(935, 45, 20, 40);

        dateCreateAfterDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(), CommonPL.getDateFormat(), "Ngày sau", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(), 20, 40);
        dateCreateAfterDatePicker.setBounds(955, 45, 170, 40);

        dateContractLabel = CommonPL.getParagraphLabel("Ngày cập nhật", Color.BLACK, CommonPL.getFontParagraphPlain());
        dateContractLabel.setBounds(15, 100, 138, 24);

        dateContractInformButton = CommonPL.getQuestionIconForm("?", "Chú thích sử dụng lọc Ngày cập nhật", "Lọc những ngày trong khoảng từ Ngày trước đến Ngày sau", Color.BLACK, CommonPL.getFontQuestionIcon());
        dateContractInformButton.setBounds(165, 100, 24, 24);

        dateContractBeforeDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(), CommonPL.getDateFormat(), "Ngày trước", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(), 20, 40);
        dateContractBeforeDatePicker.setBounds(15, 130, 170, 40);

        dateContractRectangleLabel = CommonPL.getTitleLabel("-", Color.BLACK, CommonPL.getFontParagraphPlain(), SwingConstants.CENTER, SwingConstants.CENTER);
        dateContractRectangleLabel.setBounds(185, 130, 20, 40);

        dateContractAfterDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(10, CommonPL.getLocale(), CommonPL.getDateFormat(), "Ngày sau", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(), 20, 40);
        dateContractAfterDatePicker.setBounds(205, 130, 170, 40);

        statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
        statusLabel.setBounds(390, 100, 360, 24);

        statusRadios = CommonPL.getMapHasValues(statusString);
        statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusRadios, statusString[0], Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        statusButton.setBounds(390, 130, 360, 40);

        filterApplyButton = CommonPL.getRoundedBorderButton(20, "Lọc", Color.decode("#007bff"), Color.WHITE, CommonPL.getFontParagraphBold());
        filterApplyButton.setBounds(765, 130, 170, 40);

        filterResetButton = CommonPL.getRoundedBorderButton(20, "Đặt lại", Color.decode("#f44336"), Color.WHITE, CommonPL.getFontParagraphBold());
        filterResetButton.setBounds(955, 130, 170, 40);

        filterPanel = new CommonPL.RoundedPanel(12);
        filterPanel.setLayout(null);
        filterPanel.setBounds(30, 115, 1140, 185);
        filterPanel.setBackground(Color.WHITE);
        filterPanel.add(findLabel);
        filterPanel.add(findInformButton);
        filterPanel.add(findInputTextField);
        filterPanel.add(sortLabel);
        filterPanel.add(sortButton);
        filterPanel.add(dateCreateLabel);
        filterPanel.add(dateCreateInformButton);
        filterPanel.add(dateCreateBeforeDatePicker);
        filterPanel.add(dateCreateRectangleLabel);
        filterPanel.add(dateCreateAfterDatePicker);
        filterPanel.add(dateContractLabel);
        filterPanel.add(dateContractInformButton);
        filterPanel.add(dateContractBeforeDatePicker);
        filterPanel.add(dateContractRectangleLabel);
        filterPanel.add(dateContractAfterDatePicker);
        filterPanel.add(statusLabel);
        filterPanel.add(statusButton);
        filterPanel.add(filterApplyButton);
        filterPanel.add(filterResetButton);

        addButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5, CommonPL.getMiddlePathToShowIcon() + "add-icon.png", "Thêm", Color.BLACK, Color.decode("#699f4c"), Color.BLACK, Color.decode("#699f4c"), CommonPL.getFontParagraphBold());
        addButton.setBounds(15, 15, 210, 40);

        updateButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5, CommonPL.getMiddlePathToShowIcon() + "update-icon.png", "Thay đổi", Color.BLACK, Color.decode("#bf873e"), Color.BLACK, Color.decode("#bf873e"), CommonPL.getFontParagraphBold());
        updateButton.setBounds(240, 15, 210, 40);

        tableData = CommonPL.createTableData(columns, widthColumns, datas, "input ticket manager");
        tableScrollPane = CommonPL.createScrollPane(true, true, tableData);
        tableScrollPane.setBounds(15, 70, 1110, 400);

        dataPanel = new CommonPL.RoundedPanel(12);
        dataPanel.setLayout(null);
        dataPanel.setBackground(Color.WHITE);
        dataPanel.setBounds(30, 330, 1140, 485);
        dataPanel.add(addButton);
        dataPanel.add(updateButton);
        dataPanel.add(tableScrollPane);

        this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
        this.setLayout(null);
        this.add(titleLabel);
        this.add(filterPanel);
        this.add(dataPanel);

        tableData.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                rowSelected = tableData.getSelectedRow();
            }
        });

        addButton.addActionListener(e -> {
            showAddOrUpdateDialog("Thêm Phiếu nhập", "Thêm", new Vector<>());
            rowSelected = -1;
            valueSelected[0] = false;
            tableData.clearSelection();
        });

        updateButton.addActionListener(e -> {
            if (rowSelected != -1) {
                Vector<Object> currentObject = new Vector<>();
                for (int i = 0; i < widthColumns.length; i++) {
                    currentObject.add(tableData.getValueAt(rowSelected, i));
                }
                showAddOrUpdateDialog("Thay đổi Phiếu nhập", "Thay đổi", currentObject);
            } else {
                CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần thay đổi");
            }
            rowSelected = -1;
            valueSelected[0] = false;
            tableData.clearSelection();
        });

        filterDatasInTable();
        renderTableData(null, null, null);
    }

    private void renderTableData(String[] join, String condition, String order) {
        ArrayList<InputTicketDTO> ticketList = inputTicketBLL.getAllInputTicketByCondition(join, condition, order);
        Object[][] datasQuery = new Object[ticketList.size()][columns.length];
        for (int i = 0; i < ticketList.size(); i++) {
            InputTicketDTO ticket = ticketList.get(i);
            String supplierId = ticket.getSupplierId();
            String supplierName = supplierBLL.getSupplierNameById(supplierId);
            String employeeInfo = accountBLL.getFullNameById(ticket.getEmployeeId());
            if (employeeInfo == null) employeeInfo = "Chưa xác định";

            datasQuery[i][0] = String.valueOf(ticket.getId());
            datasQuery[i][1] = supplierId + " - " + (supplierName != null ? supplierName : "Không xác định");
            datasQuery[i][2] = ticket.getDateCreate();
            datasQuery[i][3] = ticket.getDateUpdate();
            datasQuery[i][4] = ticket.getEmployeeId() + " - " + employeeInfo;
            datasQuery[i][5] = CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(ticket.getCost()));
            datasQuery[i][6] = ticket.getIsCancelled() ? "Đã huỷ phiếu" :
                              (ticket.getStatus() ? "Đã hoàn thành" : "Đang chờ xác nhận");
        }
        datas = datasQuery;
        CommonPL.updateRowsInTableData(tableData, datas);
    }

    private void filterDatasInTable() {
        filterApplyButton.addActionListener(e -> {
            String findValue = !findInputTextField.getText().equals("Nhập thông tin") ? findInputTextField.getText() : null;
            String sortValue = CommonPL.getSQLFromCheckboxs(sortCheckboxs, sortsSQL);
            String statusValue = CommonPL.getSQLFromRadios(statusRadios, statusSQL);

            String dateCreateCondition = "";
            if (dateCreateBeforeDatePicker.getDate() != null && dateCreateAfterDatePicker.getDate() != null) {
                dateCreateCondition = String.format("ngayLapPN BETWEEN '%s' AND '%s'",
                        CommonPL.getDateFormat().format(dateCreateBeforeDatePicker.getDate()),
                        CommonPL.getDateFormat().format(dateCreateAfterDatePicker.getDate()));
            }
            String dateContractCondition = "";
            if (dateContractBeforeDatePicker.getDate() != null && dateContractAfterDatePicker.getDate() != null) {
                dateContractCondition = String.format("ngayCapNhat BETWEEN '%s' AND '%s'",
                        CommonPL.getDateFormat().format(dateContractBeforeDatePicker.getDate()),
                        CommonPL.getDateFormat().format(dateContractAfterDatePicker.getDate()));
            }

            String condition = (findValue != null ? String.format("maPhieuNhap LIKE '%%%s%%'", findValue) : "")
                    + (dateCreateCondition.length() > 0 ? (findValue != null ? " AND " : "") + dateCreateCondition : "")
                    + (dateContractCondition.length() > 0 ? (findValue != null || dateCreateCondition.length() > 0 ? " AND " : "") + dateContractCondition : "")
                    + (statusValue != null ? (findValue != null || dateCreateCondition.length() > 0 || dateContractCondition.length() > 0 ? " AND " : "") + statusValue : "");
            if (condition.length() == 0) condition = null;

            renderTableData(null, condition, sortValue != null && !sortValue.isEmpty() ? sortValue : null);
        });

        filterResetButton.addActionListener(e -> {
            findInputTextField.setText("Nhập thông tin");
            findInputTextField.setForeground(Color.LIGHT_GRAY);
            CommonPL.resetMapForFilter(sortCheckboxs, sortsString, sortButton);
            CommonPL.resetMapForFilter(statusRadios, statusString, statusButton);
            dateCreateBeforeDatePicker.setDate(null);
            dateCreateAfterDatePicker.setDate(null);
            dateContractBeforeDatePicker.setDate(null);
            dateContractAfterDatePicker.setDate(null);
            renderTableData(null, null, null);
        });
    }

    private void showAddIngredientDialog() {
        JDialog addIngredientDialog = new JDialog();
        addIngredientDialog.setTitle("Thêm Nguyên liệu");
        addIngredientDialog.setSize(500, 320);
        addIngredientDialog.setLayout(null);
        addIngredientDialog.setModal(true);
        addIngredientDialog.setLocationRelativeTo(addOrUpdateDialog);

        JLabel ingredientLabel = CommonPL.getParagraphLabel("Nguyên liệu", Color.BLACK, CommonPL.getFontParagraphPlain());
        ingredientLabel.setBounds(20, 10, 460, 40);

        JComboBox<String> ingredientComboBox = CommonPL.CustomComboBox(ingredientsVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        ingredientComboBox.setBounds(20, 50, 460, 40);

        JLabel priceLabel = CommonPL.getParagraphLabel("Giá nhập", Color.BLACK, CommonPL.getFontParagraphPlain());
        priceLabel.setBounds(20, 100, 220, 40);

        JTextField priceField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Giá nhập", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        priceField.setBounds(20, 140, 220, 40);

        JLabel quantityLabel = CommonPL.getParagraphLabel("Số lượng", Color.BLACK, CommonPL.getFontParagraphPlain());
        quantityLabel.setBounds(260, 100, 220, 40);

        JTextField quantityField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Số lượng", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        quantityField.setBounds(260, 140, 220, 40);

        JButton confirmButton = CommonPL.getButtonDefaultForm("Thêm", CommonPL.getFontParagraphBold());
        confirmButton.setBounds(20, 230, 460, 40);

        confirmButton.addActionListener(evt -> {
            String selectedIngredient = (String) ingredientComboBox.getSelectedItem();
            if (selectedIngredient.equals("Chọn nguyên liệu")) {
                CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn nguyên liệu");
                return;
            }
            String[] parts = selectedIngredient.split(" - ", 2);
            String ingredientId = parts[0];
            String ingredientName = parts.length > 1 ? parts[1] : "Không xác định";
            Long price;
            Integer quantity;
            try {
                price = Long.parseLong(priceField.getText());
                quantity = Integer.parseInt(quantityField.getText());
            } catch (NumberFormatException ex) {
                CommonPL.createErrorDialog("Thông báo lỗi", "Giá nhập và số lượng phải là số hợp lệ");
                return;
            }
            if (price < 0 || quantity < 0) {
                CommonPL.createErrorDialog("Thông báo lỗi", "Giá nhập và số lượng không được âm");
                return;
            }

            Long totalCost = price * quantity;
            Vector<Object> newRow = new Vector<>();
            newRow.add(ingredientId);
            newRow.add(ingredientName);
            newRow.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(price)));
            newRow.add(quantity);
            newRow.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(totalCost)));
            ((DefaultTableModel) addOrUpdateTableData.getModel()).addRow(newRow);

            long total = 0;
            for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
                total += CommonPL.moneyFormatToMoneyLong((String) addOrUpdateTableData.getValueAt(i, 4));
            }
            addOrUpdateCostTextField.setText(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(total)));

            addIngredientDialog.dispose();
        });

        JPanel addIngredientPanel = new JPanel();
        addIngredientPanel.setLayout(null);
        addIngredientPanel.setBackground(Color.WHITE);
        addIngredientPanel.setBounds(0, 0, 500, 320);
        addIngredientPanel.add(ingredientLabel);
        addIngredientPanel.add(ingredientComboBox);
        addIngredientPanel.add(priceLabel);
        addIngredientPanel.add(priceField);
        addIngredientPanel.add(quantityLabel);
        addIngredientPanel.add(quantityField);
        addIngredientPanel.add(confirmButton);

        addIngredientDialog.add(addIngredientPanel);
        addIngredientDialog.setVisible(true);
    }

    private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
        addOrUpdateIdLabel = CommonPL.getParagraphLabel("Mã phiếu", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateIdLabel.setBounds(20, 10, 140, 40);

        addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateIdTextField.setBounds(20, 50, 140, 40);

        addOrUpdateDateCreateLabel = CommonPL.getParagraphLabel("Ngày lập phiếu", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateDateCreateLabel.setBounds(180, 10, 220, 40);

        addOrUpdateDateCreateDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(0, CommonPL.getLocale(), CommonPL.getDateFormat(), "Chọn Ngày", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(), 40, 40);
        addOrUpdateDateCreateDatePicker.setBounds(180, 50, 220, 40);

        addOrUpdateDateContractLabel = CommonPL.getParagraphLabel("Ngày cập nhật", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateDateContractLabel.setBounds(420, 10, 220, 40);

        addOrUpdateDateContractDatePicker = CommonPL.CustomCornerDatePicker.CustomDatePicker(0, CommonPL.getLocale(), CommonPL.getDateFormat(), "Chọn Ngày", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain(), 40, 40);
        addOrUpdateDateContractDatePicker.setBounds(420, 50, 220, 40);

        addOrUpdateEmployeeLabel = CommonPL.getParagraphLabel("Nhân viên lập phiếu", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateEmployeeLabel.setBounds(660, 10, 460, 40);

        addOrUpdateEmployeeComboBox = CommonPL.CustomComboBox(employeesVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateEmployeeComboBox.setBounds(660, 50, 460, 40);

        addOrUpdateLinePanel = new CommonPL.RoundedPanel(8);
        addOrUpdateLinePanel.setBounds(20, 118, 1100, 4);
        addOrUpdateLinePanel.setBackground(Color.LIGHT_GRAY);

        addOrUpdateDataLabel = CommonPL.getParagraphLabel("Danh sách nguyên liệu", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateDataLabel.setBounds(20, 140, 220, 40);

        addOrUpdateAddUnitButton = CommonPL.getButtonHasIcon(40, 28, 20, 20, 10, 4, CommonPL.getMiddlePathToShowIcon() + "plus-icon.png", null, null, null, null, null, new Font("Arial", Font.BOLD, 14));
        addOrUpdateAddUnitButton.setOpaque(true);
        addOrUpdateAddUnitButton.setBackground(Color.BLACK);
        addOrUpdateAddUnitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                addOrUpdateAddUnitButton.setBorder(BorderFactory.createLineBorder(Color.decode("#42A5F5"), 2));
                addOrUpdateAddUnitButton.setBackground(Color.decode("#42A5F5"));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                addOrUpdateAddUnitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                addOrUpdateAddUnitButton.setBackground(Color.BLACK);
            }
        });
        addOrUpdateAddUnitButton.setBounds(553, 146, 40, 28);

        addOrUpdateDeleteUnitButton = CommonPL.getButtonHasIcon(40, 28, 20, 20, 10, 4, CommonPL.getMiddlePathToShowIcon() + "minus-icon.png", null, null, null, null, null, new Font("Arial", Font.BOLD, 14));
        addOrUpdateDeleteUnitButton.setOpaque(true);
        addOrUpdateDeleteUnitButton.setBackground(Color.BLACK);
        addOrUpdateDeleteUnitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                addOrUpdateDeleteUnitButton.setBorder(BorderFactory.createLineBorder(Color.decode("#42A5F5"), 2));
                addOrUpdateDeleteUnitButton.setBackground(Color.decode("#42A5F5"));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                addOrUpdateDeleteUnitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                addOrUpdateDeleteUnitButton.setBackground(Color.BLACK);
            }
        });
        addOrUpdateDeleteUnitButton.setBounds(600, 146, 40, 28);

        addOrUpdateTableData = CommonPL.createTableData(addOrUpdateColumns, addOrUpdateWidthColumns, addOrUpdateDatas, "add or update unit table");
        addOrUpdateTableScrollPane = CommonPL.createScrollPane(true, true, addOrUpdateTableData);
        addOrUpdateTableScrollPane.setBounds(20, 180, 620, 400);

        addOrUpdateSupplierLabel = CommonPL.getParagraphLabel("Nhà cung cấp", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateSupplierLabel.setBounds(660, 140, 460, 40);

        addOrUpdateSupplierComboBox = CommonPL.CustomComboBox(suppliersVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateSupplierComboBox.setBounds(660, 180, 460, 40);

        addOrUpdateCostLabel = CommonPL.getParagraphLabel("Tổng tiền nhập (VNĐ)", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateCostLabel.setBounds(660, 230, 460, 40);

        addOrUpdateCostTextField = new CommonPL.CustomTextField(0, 0, 0, "Đang cập nhật", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateCostTextField.setBounds(660, 270, 460, 40);
        addOrUpdateCostTextField.setEnabled(false);
        addOrUpdateCostTextField.setBackground(Color.decode("#dedede"));

        addOrUpdateStatusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateStatusLabel.setBounds(660, 320, 460, 40);

        addOrUpdateStatusTextField = new CommonPL.CustomTextField(0, 0, 0, "Đang cập nhật", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateStatusTextField.setBounds(660, 360, 460, 40);
        addOrUpdateStatusTextField.setEnabled(false);
        addOrUpdateStatusTextField.setBackground(Color.decode("#dedede"));

        addOrUpdateCompleteButton = CommonPL.getRoundedBorderButton(20, "Hoàn thành", Color.decode("#33CC00"), Color.WHITE, CommonPL.getFontParagraphBold());
        addOrUpdateCompleteButton.setBounds(660, 540, 220, 40);
        addOrUpdateCompleteButton.setVisible(false);

        addOrUpdateDeleteButton = CommonPL.getRoundedBorderButton(20, "Huỷ phiếu", Color.decode("#EE0000"), Color.WHITE, CommonPL.getFontParagraphBold());
        addOrUpdateDeleteButton.setBounds(900, 540, 220, 40);
        addOrUpdateDeleteButton.setVisible(false);

        addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button, button.equals("Thêm") ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE, CommonPL.getFontParagraphBold());
        addOrUpdateButton.setBounds(660, 540, 460, 40);
        SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

        addOrUpdateAddUnitButton.addActionListener(e -> showAddIngredientDialog());

        addOrUpdateDeleteUnitButton.addActionListener(e -> {
            int selectedRow = addOrUpdateTableData.getSelectedRow();
            if (selectedRow != -1) {
                ((DefaultTableModel) addOrUpdateTableData.getModel()).removeRow(selectedRow);
                long total = 0;
                for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
                    total += CommonPL.moneyFormatToMoneyLong((String) addOrUpdateTableData.getValueAt(i, 4));
                }
                addOrUpdateCostTextField.setText(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(total)));
            } else {
                CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn một nguyên liệu để xóa");
            }
        });

        if (title.equals("Thêm Phiếu nhập") && button.equals("Thêm") && object.isEmpty()) {
            InputTicketDTO lastTicket = inputTicketBLL.getLastInputTicket();
            int newId = lastTicket.getId() + 1; // Tăng mã phiếu tự động
            addOrUpdateIdTextField.setText(String.valueOf(newId));
            addOrUpdateIdTextField.setEnabled(false);
            ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
            addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
            addOrUpdateDateCreateDatePicker.setDate(new Date());
            addOrUpdateDateContractDatePicker.setDate(new Date());
            addOrUpdateStatusTextField.setText("Đang chờ xác nhận");
            AccountDTO currentUser = CommonPL.getAccountUsingApp();
            if (currentUser != null) {
                addOrUpdateEmployeeComboBox.setSelectedItem(currentUser.getId() + " - " + currentUser.getFullname());
            } else {
                addOrUpdateEmployeeComboBox.setSelectedIndex(0);
            }
            addOrUpdateSupplierComboBox.setSelectedIndex(0);
        }

        if (title.equals("Thay đổi Phiếu nhập") && button.equals("Thay đổi") && !object.isEmpty()) {
            addOrUpdateButton.setVisible(false);
            Integer ticketId = Integer.parseInt((String) object.get(0));
            addOrUpdateIdTextField.setText(String.valueOf(ticketId));
            addOrUpdateIdTextField.setEnabled(false);
            ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
            addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));

            try {
                Date dateCreate = CommonPL.getDateFormat().parse((String) object.get(2));
                addOrUpdateDateCreateDatePicker.setDate(dateCreate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                Date dateContract = CommonPL.getDateFormat().parse((String) object.get(3));
                addOrUpdateDateContractDatePicker.setDate(dateContract);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            addOrUpdateEmployeeComboBox.setSelectedItem((String) object.get(4));
            if (addOrUpdateEmployeeComboBox.getSelectedIndex() == -1) {
                addOrUpdateEmployeeComboBox.setSelectedIndex(0);
            }
            addOrUpdateSupplierComboBox.setSelectedItem((String) object.get(1));
            addOrUpdateCostTextField.setText((String) object.get(5));
            addOrUpdateStatusTextField.setText((String) object.get(6));

            ArrayList<InputTicketDetailDTO> details = inputTicketDetailBLL.getAllInputTicketDetailByCondition(
                    null, "maPhieuNhap = " + ticketId, null);
            for (InputTicketDetailDTO detail : details) {
                IngredientDTO ingredient = ingredientBLL.getOneIngredientById(detail.getIngredientId());
                String ingredientName = ingredient != null ? ingredient.getName() : "Không xác định";
                Vector<Object> row = new Vector<>();
                row.add(detail.getIngredientId());
                row.add(ingredientName);
                row.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(detail.getInputPrice())));
                row.add(detail.getInputQuantity());
                row.add(CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(detail.getInputPrice() * detail.getInputQuantity())));
                ((DefaultTableModel) addOrUpdateTableData.getModel()).addRow(row);
            }

            if (object.get(6).equals("Đã hoàn thành") || object.get(6).equals("Đã huỷ phiếu")) {
                addOrUpdateAddUnitButton.setVisible(false);
                addOrUpdateDeleteUnitButton.setVisible(false);
                addOrUpdateCompleteButton.setVisible(false);
                addOrUpdateDeleteButton.setVisible(false);
            } else if (object.get(6).equals("Đang chờ xác nhận")) {
                addOrUpdateCompleteButton.setVisible(true);
                addOrUpdateDeleteButton.setVisible(true);
            }
        }

        addOrUpdateButton.addActionListener(e -> {
            String id = addOrUpdateIdTextField.getText();
            Integer ticketId;
            try {
                ticketId = Integer.parseInt(id);
            } catch (NumberFormatException ex) {
                CommonPL.createErrorDialog("Thông báo lỗi", "Mã phiếu phải là số hợp lệ");
                return;
            }

            String dateCreate = addOrUpdateDateCreateDatePicker.getDate() != null ?
                    CommonPL.getDateFormat().format(addOrUpdateDateCreateDatePicker.getDate()) : null;
            String supplier = (String) addOrUpdateSupplierComboBox.getSelectedItem();
            String supplierId = supplier.equals("Chọn Nhà cung cấp") ? null : supplier.split(" - ")[0];
            Long cost;
            try {
                cost = CommonPL.moneyFormatToMoneyLong(addOrUpdateCostTextField.getText());
            } catch (NumberFormatException ex) {
                CommonPL.createErrorDialog("Thông báo lỗi", "Tổng tiền phải là số hợp lệ");
                return;
            }
            Boolean status = addOrUpdateStatusTextField.getText().equals("Đã hoàn thành");
            String dateUpdate = addOrUpdateDateContractDatePicker.getDate() != null ?
                    CommonPL.getDateFormat().format(addOrUpdateDateContractDatePicker.getDate()) : null;
            String employee = (String) addOrUpdateEmployeeComboBox.getSelectedItem();
            Integer employeeId = employee.equals("Chọn Nhân viên") ? null : Integer.parseInt(employee.split(" - ")[0]);
            Boolean isCancelled = addOrUpdateStatusTextField.getText().equals("Đã huỷ phiếu");

            if (employeeId == null || dateCreate == null || supplierId == null || dateUpdate == null) {
                CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng nhập đầy đủ thông tin bắt buộc (Nhân viên, Ngày lập, Nhà cung cấp, Ngày cập nhật)");
                return;
            }

            if (((DefaultTableModel) addOrUpdateTableData.getModel()).getRowCount() == 0) {
                CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng thêm ít nhất một nguyên liệu");
                return;
            }

            String result;
            if (title.equals("Thêm Phiếu nhập")) {
                result = inputTicketBLL.insertInputTicket(ticketId, dateCreate, supplierId, cost, status, dateUpdate, employeeId, isCancelled);
                if (result.equals("Thêm phiếu nhập thành công")) {
                    for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
                        String ingredientId = (String) addOrUpdateTableData.getValueAt(i, 0);
                        Long price = CommonPL.moneyFormatToMoneyLong((String) addOrUpdateTableData.getValueAt(i, 2));
                        Integer quantity = (Integer) addOrUpdateTableData.getValueAt(i, 3);
                        String detailResult = inputTicketDetailBLL.insertInputTicketDetail(ticketId, ingredientId, price, quantity);
                        if (!detailResult.equals("Thêm chi tiết phiếu nhập thành công")) {
                            CommonPL.createErrorDialog("Thông báo lỗi", "Lỗi khi thêm chi tiết phiếu nhập: " + detailResult);
                            return;
                        }
                    }
                    CommonPL.createSuccessDialog("Thông báo thành công", "Thêm phiếu nhập và chi tiết thành công");
                    addOrUpdateDialog.dispose();
                    renderTableData(null, null, null);
                } else {
                    CommonPL.createErrorDialog("Thông báo lỗi", result);
                }
            } else {
                result = inputTicketBLL.updateInputTicket(ticketId, dateCreate, supplierId, cost, status, dateUpdate, employeeId, isCancelled);
                if (result.equals("Cập nhật phiếu nhập thành công")) {
                    ArrayList<InputTicketDetailDTO> oldDetails = inputTicketDetailBLL.getAllInputTicketDetailByCondition(
                            null, "maPhieuNhap = " + ticketId, null);
                    for (InputTicketDetailDTO detail : oldDetails) {
                        inputTicketDetailBLL.updateInputTicketDetail(detail.getId(), detail.getIngredientId(), 0L, 0);
                    }
                    for (int i = 0; i < addOrUpdateTableData.getRowCount(); i++) {
                        String ingredientId = (String) addOrUpdateTableData.getValueAt(i, 0);
                        Long price = CommonPL.moneyFormatToMoneyLong((String) addOrUpdateTableData.getValueAt(i, 2));
                        Integer quantity = (Integer) addOrUpdateTableData.getValueAt(i, 3);
                        String detailResult = inputTicketDetailBLL.insertInputTicketDetail(ticketId, ingredientId, price, quantity);
                        if (!detailResult.equals("Thêm chi tiết phiếu nhập thành công")) {
                            CommonPL.createErrorDialog("Thông báo lỗi", "Lỗi khi cập nhật chi tiết phiếu nhập: " + detailResult);
                            return;
                        }
                    }
                    CommonPL.createSuccessDialog("Thông báo thành công", "Cập nhật phiếu nhập và chi tiết thành công");
                    addOrUpdateDialog.dispose();
                    renderTableData(null, null, null);
                } else {
                    CommonPL.createErrorDialog("Thông báo lỗi", result);
                }
            }
        });

        addOrUpdateCompleteButton.addActionListener(e -> {
            String id = addOrUpdateIdTextField.getText();
            Integer ticketId;
            try {
                ticketId = Integer.parseInt(id);
            } catch (NumberFormatException ex) {
                CommonPL.createErrorDialog("Thông báo lỗi", "Mã phiếu phải là số hợp lệ");
                return;
            }

            String dateCreate = CommonPL.getDateFormat().format(addOrUpdateDateCreateDatePicker.getDate());
            String supplier = (String) addOrUpdateSupplierComboBox.getSelectedItem();
            String supplierId = supplier.split(" - ")[0];
            Long cost;
            try {
                cost = CommonPL.moneyFormatToMoneyLong(addOrUpdateCostTextField.getText());
            } catch (NumberFormatException ex) {
                CommonPL.createErrorDialog("Thông báo lỗi", "Tổng tiền phải là số hợp lệ");
                return;
            }
            String dateUpdate = CommonPL.getCurrentDate();
            String employee = (String) addOrUpdateEmployeeComboBox.getSelectedItem();
            Integer employeeId = Integer.parseInt(employee.split(" - ")[0]);

            String result = inputTicketBLL.updateInputTicket(ticketId, dateCreate, supplierId, cost, true, dateUpdate, employeeId, false);
            if (result.equals("Cập nhật phiếu nhập thành công")) {
                CommonPL.createSuccessDialog("Thông báo thành công", "Phiếu nhập đã hoàn thành");
                addOrUpdateDialog.dispose();
                renderTableData(null, null, null);
            } else {
                CommonPL.createErrorDialog("Thông báo lỗi", result);
            }
        });

        addOrUpdateDeleteButton.addActionListener(e -> {
            CommonPL.createSelectionsDialog("Thông báo lựa chọn", "Có chắc chắn muốn huỷ phiếu này?", valueSelected);
            if (valueSelected[0]) {
                String id = addOrUpdateIdTextField.getText();
                Integer ticketId;
                try {
                    ticketId = Integer.parseInt(id);
                } catch (NumberFormatException ex) {
                    CommonPL.createErrorDialog("Thông báo lỗi", "Mã phiếu phải là số hợp lệ");
                    return;
                }
                String result = inputTicketBLL.lockInputTicket(ticketId, CommonPL.getCurrentDate());
                if (result.equals("Thay đổi trạng thái phiếu nhập thành công")) {
                    CommonPL.createSuccessDialog("Thông báo thành công", "Huỷ phiếu thành công");
                    addOrUpdateDialog.dispose();
                    renderTableData(null, null, null);
                } else {
                    CommonPL.createErrorDialog("Thông báo lỗi", result);
                }
            }
            valueSelected[0] = false;
        });

        addOrUpdateBlockPanel = new JPanel();
        addOrUpdateBlockPanel.setLayout(null);
        addOrUpdateBlockPanel.setBounds(0, 0, 1140, 630);
        addOrUpdateBlockPanel.setBackground(Color.WHITE);
        addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
        addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
        addOrUpdateBlockPanel.add(addOrUpdateDateCreateLabel);
        addOrUpdateBlockPanel.add(addOrUpdateDateCreateDatePicker);
        addOrUpdateBlockPanel.add(addOrUpdateDateContractLabel);
        addOrUpdateBlockPanel.add(addOrUpdateDateContractDatePicker);
        addOrUpdateBlockPanel.add(addOrUpdateEmployeeLabel);
        addOrUpdateBlockPanel.add(addOrUpdateEmployeeComboBox);
        addOrUpdateBlockPanel.add(addOrUpdateLinePanel);
        addOrUpdateBlockPanel.add(addOrUpdateDataLabel);
        addOrUpdateBlockPanel.add(addOrUpdateAddUnitButton);
        addOrUpdateBlockPanel.add(addOrUpdateDeleteUnitButton);
        addOrUpdateBlockPanel.add(addOrUpdateTableScrollPane);
        addOrUpdateBlockPanel.add(addOrUpdateSupplierLabel);
        addOrUpdateBlockPanel.add(addOrUpdateSupplierComboBox);
        addOrUpdateBlockPanel.add(addOrUpdateCostLabel);
        addOrUpdateBlockPanel.add(addOrUpdateCostTextField);
        addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
        addOrUpdateBlockPanel.add(addOrUpdateStatusTextField);
        addOrUpdateBlockPanel.add(addOrUpdateCompleteButton);
        addOrUpdateBlockPanel.add(addOrUpdateDeleteButton);
        addOrUpdateBlockPanel.add(addOrUpdateButton);

        addOrUpdateDialog = new JDialog();
        addOrUpdateDialog.setTitle(title);
        addOrUpdateDialog.setLayout(null);
        addOrUpdateDialog.setSize(1140, 630);
        addOrUpdateDialog.setResizable(false);
        addOrUpdateDialog.setLocationRelativeTo(null);
        addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addOrUpdateDialog.add(addOrUpdateBlockPanel);
        addOrUpdateDialog.setModal(true);
        addOrUpdateDialog.setVisible(true);
    }
}