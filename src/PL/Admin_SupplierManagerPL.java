package PL;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javax.swing.Timer;
import javax.swing.UIManager;

import BLL.SupplierBLL;
import DTO.SupplierDTO;
import PL.CommonPL.CustomTextField;

public class Admin_SupplierManagerPL extends JPanel {
    private SupplierBLL supplierBLL;

    // Các Component
    private JLabel titleLabel;
    private JLabel findLabel;
    private JTextField findInputTextField;
    private JButton findInformButton;
    private JLabel sortLabel;
    private Map<String, Boolean> sortCheckboxs;
    private JButton sortButton;
    private JLabel statusLabel;
    private Map<String, Boolean> statusCheckboxs;
    private JButton statusButton;
    private JButton filterApplyButton;
    private JButton filterResetButton;
    private JPanel filterPanel;
    private JButton addButton;
    private JButton updateButton;
    private JButton lockButton;
    private JTable tableData;
    private JScrollPane tableScrollPane;
    private JPanel dataPanel;
    private JLabel addOrUpdateIdLabel;
    private JTextField addOrUpdateIdTextField;
    private JLabel addOrUpdateNameLabel;
    private JTextField addOrUpdateNameTextField;
    private JLabel addOrUpdatePhoneLabel;
    private JTextField addOrUpdatePhoneTextField;
    private JLabel addOrUpdateEmailLabel;
    private JTextField addOrUpdateEmailTextField;
    private JLabel addOrUpdateAddressLabel;
    private JButton addOrUpdateAddressButton;
    private JTextField addOrUpdateAddressTextField;
    private JLabel addOrUpdateStatusLabel;
    private JComboBox<String> addOrUpdateStatusComboBox;
    private JLabel addOrUpdateTimeLabel;
    private JLabel addOrUpdateTimeDetailLabel;
    private JButton addOrUpdateButton;
    private JPanel addOrUpdateBlockPanel;
    private JDialog addOrUpdateDialog;
    // Các Component của Add Or Update Address Detail Dialog
    private JLabel addressDetailHouseNumberAndStreetNameLabel;
    private JTextField addressDetailHouseNumberAndStreetNameTextField;
    private JLabel addressDetailCityNameLabel;
    private JComboBox<String> addressDetailCityNameComboBox;
    private JLabel addressDetailDistrictNameLabel;
    private JComboBox<String> addressDetailDistrictNameComboBox;
    private JLabel addressDetailWardNameLabel;
    private JComboBox<String> addressDetailWardNameComboBox;
    private JButton addressDetailApplyButton;
    private JPanel addressDetailBlockPanel;
    private JDialog addressDetailDialog;
    private String addressDetailHouseNumberAndStreetNameInput = null;
    private String addressDetailWardNameSelected = null;
    private String addressDetailDistrictNameSelected = null;
    private String addressDetailCityNameSelected = null;

    // Thông tin bảng
    private final String[] columns = { "Mã NCC", "Tên NCC", "Số điện thoại", "Email", "Địa chỉ", "Trạng thái" };
    private final int[] widthColumns = { 150, 500, 150, 400, 600, 150 };
    private Object[][] datas = {};
    private int rowSelected = -1;
    private Boolean[] valueSelected = { null };
    private final String[] sortsString = { "Mã NCC tăng dần", "Mã NCC giảm dần", "Tên NCC tăng dần", "Tên NCC giảm dần" };
    private final String[] sortsSQL = { "maNCC ASC", "maNCC DESC", "tenNCC ASC", "tenNCC DESC" };
    private final String[] statusString = { "Tất cả", "Hoạt động", "Tạm dừng" };
    private final String[] statusSQL = { "", "trangThai = 1", "trangThai = 0" };
    private final String[] statusOptions = { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" };

    public Admin_SupplierManagerPL() {
        supplierBLL = new SupplierBLL();

        // Tiêu đề
        titleLabel = CommonPL.getTitleLabel("Nhà cung cấp", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER, SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, 1140, 115);

        // Filter Panel
        findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
        findLabel.setBounds(15, 15, 90, 24);

        findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
                "Bạn có thể tìm kiếm bằng các thông tin như: mã NCC, tên NCC, số điện thoại NCC", Color.BLACK,
                CommonPL.getFontQuestionIcon());
        findInformButton.setBounds(110, 15, 24, 24);

        findInputTextField = new CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY, Color.BLACK,
                CommonPL.getFontParagraphPlain());
        findInputTextField.setBounds(15, 45, 360, 40);

        sortLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
        sortLabel.setBounds(390, 15, 360, 24);

        sortCheckboxs = CommonPL.getMapHasValues(sortsString);
        sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sortsString[0],
                Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        sortButton.setBounds(390, 45, 360, 40);

        statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
        statusLabel.setBounds(765, 15, 360, 24);

        statusCheckboxs = CommonPL.getMapHasValues(statusString);
        statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusCheckboxs, statusString[0],
                Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        statusButton.setBounds(765, 45, 360, 40);

        filterApplyButton = CommonPL.getRoundedBorderButton(20, "Lọc", Color.decode("#007bff"), Color.WHITE,
                CommonPL.getFontParagraphBold());
        filterApplyButton.setBounds(765, 130, 170, 40);

        filterResetButton = CommonPL.getRoundedBorderButton(20, "Đặt lại", Color.decode("#f44336"), Color.WHITE,
                CommonPL.getFontParagraphBold());
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
        filterPanel.add(statusLabel);
        filterPanel.add(statusButton);
        filterPanel.add(filterApplyButton);
        filterPanel.add(filterResetButton);

        // Data Panel
        addButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
                CommonPL.getMiddlePathToShowIcon() + "add-icon.png", "Thêm", Color.BLACK, Color.decode("#699f4c"),
                Color.BLACK, Color.decode("#699f4c"), CommonPL.getFontParagraphBold());
        addButton.setBounds(15, 15, 210, 40);

        updateButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
                CommonPL.getMiddlePathToShowIcon() + "update-icon.png", "Thay đổi", Color.BLACK,
                Color.decode("#bf873e"), Color.BLACK, Color.decode("#bf873e"), CommonPL.getFontParagraphBold());
        updateButton.setBounds(240, 15, 210, 40);

        lockButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
                CommonPL.getMiddlePathToShowIcon() + "lock-icon.png", "Khoá", Color.BLACK, Color.decode("#9f4d4d"),
                Color.BLACK, Color.decode("#9f4d4d"), CommonPL.getFontParagraphBold());
        lockButton.setBounds(465, 15, 210, 40);

        tableData = CommonPL.createTableData(columns, widthColumns, datas, "supplier manager");
        tableScrollPane = CommonPL.createScrollPane(true, true, tableData);
        tableScrollPane.setBounds(15, 70, 1110, 400);

        dataPanel = new CommonPL.RoundedPanel(12);
        dataPanel.setLayout(null);
        dataPanel.setBackground(Color.WHITE);
        dataPanel.setBounds(30, 330, 1140, 485);
        dataPanel.add(addButton);
        dataPanel.add(updateButton);
        dataPanel.add(lockButton);
        dataPanel.add(tableScrollPane);

        // Định nghĩa panel chính
        this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
        this.setLayout(null);
        this.add(titleLabel);
        this.add(filterPanel);
        this.add(dataPanel);

        // Sự kiện chọn dòng trong bảng
        tableData.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                rowSelected = tableData.getSelectedRow();
            }
        });

        // Sự kiện nút "Thêm"
        addButton.addActionListener(e -> {
            addressDetailHouseNumberAndStreetNameInput = null;
            addressDetailCityNameSelected = null;
            addressDetailDistrictNameSelected = null;
            addressDetailWardNameSelected = null;
            showAddOrUpdateDialog("Thêm Nhà cung cấp", "Thêm", new Vector<>());
            rowSelected = -1;
            valueSelected[0] = false;
            tableData.clearSelection();
        });

        // Sự kiện nút "Thay đổi"
        updateButton.addActionListener(e -> {
            if (rowSelected != -1) {
                addressDetailHouseNumberAndStreetNameInput = null;
                addressDetailCityNameSelected = null;
                addressDetailDistrictNameSelected = null;
                addressDetailWardNameSelected = null;
                Vector<Object> currentObject = new Vector<>();
                for (int i = 0; i < widthColumns.length; i++) {
                    currentObject.add(tableData.getValueAt(rowSelected, i));
                }
                showAddOrUpdateDialog("Thay đổi Nhà cung cấp", "Thay đổi", currentObject);
            } else {
                CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần thay đổi");
            }
            rowSelected = -1;
            valueSelected[0] = false;
            tableData.clearSelection();
        });

        // Sự kiện nút "Khóa"
        lockButton.addActionListener(e -> {
            if (rowSelected != -1) {
                Vector<Object> currentObject = new Vector<>();
                for (int i = 0; i < widthColumns.length; i++) {
                    currentObject.add(tableData.getValueAt(rowSelected, i));
                }
                CommonPL.createSelectionsDialog("Thông báo lựa chọn",
                        String.format("Có chắc chắn muốn %s nhà cung cấp này?",
                                currentObject.get(5).equals("Hoạt động") ? "khóa" : "mở khóa"),
                        valueSelected);

                if (valueSelected[0]) {
                    String result = supplierBLL.lockSupplier((String) currentObject.get(0), CommonPL.getCurrentDate());
                    if (result.equals("Thay đổi trạng thái thành công")) {
                        CommonPL.createSuccessDialog("Thông báo thành công",
                                currentObject.get(5).equals("Hoạt động") ? "Khóa thành công" : "Mở khóa thành công");
                        renderTableData(null, null, null);
                    } else {
                        CommonPL.createErrorDialog("Thông báo lỗi", result);
                    }
                }
            } else {
                CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần khóa");
            }
            rowSelected = -1;
            valueSelected[0] = false;
            tableData.clearSelection();
        });

        // Thiết lập sự kiện lọc dữ liệu
        filterDatasInTable();

        // Hiển thị dữ liệu ban đầu
        renderTableData(null, null, null);
    }

    // Làm mới bảng dữ liệu
    private void renderTableData(String[] join, String condition, String order) {
        ArrayList<SupplierDTO> supplierList = supplierBLL.getAllSuppliersByCondition(join, condition, order);
        Object[][] newData = new Object[supplierList.size()][columns.length];
        for (int i = 0; i < supplierList.size(); i++) {
            newData[i][0] = supplierList.get(i).getId();
            newData[i][1] = supplierList.get(i).getName();
            newData[i][2] = supplierList.get(i).getPhone();
            newData[i][3] = supplierList.get(i).getEmail();
            newData[i][4] = supplierList.get(i).getAddress();
            newData[i][5] = supplierList.get(i).getStatus() ? "Hoạt động" : "Tạm dừng";
        }
        datas = newData;
        CommonPL.updateRowsInTableData(tableData, datas);
    }

    // Xử lý sự kiện lọc dữ liệu
    private void filterDatasInTable() {
        filterApplyButton.addActionListener(e -> {
            String findValue = !findInputTextField.getText().equals("Nhập thông tin") ? findInputTextField.getText() : null;
            String sortValue = CommonPL.getSQLFromCheckboxs(sortCheckboxs, sortsSQL);
            String statusValue = CommonPL.getSQLFromRadios(statusCheckboxs, statusSQL);

            String condition = (findValue != null
                    ? String.format("(maNCC LIKE '%%%s%%' OR tenNCC LIKE '%%%s%%' OR soDienThoai LIKE '%%%s%%')", findValue, findValue, findValue)
                    : "")
                    + (statusValue != null ? (findValue != null ? " AND " + statusValue : statusValue) : "");
            if (condition.length() == 0) condition = null;

            renderTableData(null, condition, sortValue);
        });

        filterResetButton.addActionListener(e -> {
            findInputTextField.setText("Nhập thông tin");
            findInputTextField.setForeground(Color.LIGHT_GRAY);
            CommonPL.resetMapForFilter(sortCheckboxs, sortsString, sortButton);
            CommonPL.resetMapForFilter(statusCheckboxs, statusString, statusButton);
            renderTableData(null, null, null);
        });
    }

    // Hàm tạo Dialog chọn địa chỉ chi tiết (giống Account)
    private void showAddressDetailDialog(String initialAddress) {
        addressDetailDialog = new JDialog();

        addressDetailHouseNumberAndStreetNameLabel = CommonPL.getParagraphLabel("Số nhà - tên đường", Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addressDetailHouseNumberAndStreetNameLabel.setBounds(20, 10, 460, 40);

        addressDetailHouseNumberAndStreetNameTextField = new CommonPL.CustomTextField(0, 0, 0,
                "Nhập Số nhà - tên đường", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addressDetailHouseNumberAndStreetNameTextField.setBounds(20, 50, 460, 40);

        addressDetailCityNameLabel = CommonPL.getParagraphLabel("Tỉnh / Thành phố", Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addressDetailCityNameLabel.setBounds(20, 100, 460, 40);

        Vector<String> citys = new Vector<>(); // Giả định danh sách tỉnh/thành phố từ CommonPL
        // Thêm các tỉnh/thành phố thực tế vào đây nếu cần
        citys.add("Chọn Tỉnh / Thành phố");
        // Ví dụ: citys.addAll(Arrays.asList("Hà Nội", "TP. Hồ Chí Minh", "Đà Nẵng"));

        addressDetailCityNameComboBox = CommonPL.CustomComboBox(citys, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
                Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addressDetailCityNameComboBox.setBounds(20, 140, 460, 40);

        addressDetailDistrictNameLabel = CommonPL.getParagraphLabel("Quận / Huyện", Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addressDetailDistrictNameLabel.setBounds(20, 190, 460, 40);

        Vector<String> districts = new Vector<>();
        districts.add("Chọn Quận / Huyện");
        addressDetailDistrictNameComboBox = CommonPL.CustomComboBox(districts, Color.WHITE, Color.LIGHT_GRAY,
                Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addressDetailDistrictNameComboBox.setBounds(20, 230, 460, 40);

        addressDetailWardNameLabel = CommonPL.getParagraphLabel("Phường / Xã", Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addressDetailWardNameLabel.setBounds(20, 280, 460, 40);

        Vector<String> wards = new Vector<>();
        wards.add("Chọn Phường / Xã");
        addressDetailWardNameComboBox = CommonPL.CustomComboBox(wards, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
                Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addressDetailWardNameComboBox.setBounds(20, 320, 460, 40);

        // Nếu có địa chỉ ban đầu (khi cập nhật), phân tích và điền sẵn
        if (initialAddress != null && !initialAddress.equals("Nhập Địa chỉ")) {
            String[] parts = initialAddress.split(", ");
            if (parts.length == 4) {
                addressDetailHouseNumberAndStreetNameTextField.setText(parts[0]);
                addressDetailHouseNumberAndStreetNameTextField.setForeground(Color.BLACK);
                addressDetailWardNameComboBox.addItem(parts[1]);
                addressDetailWardNameComboBox.setSelectedItem(parts[1]);
                addressDetailDistrictNameComboBox.addItem(parts[2]);
                addressDetailDistrictNameComboBox.setSelectedItem(parts[2]);
                addressDetailCityNameComboBox.addItem(parts[3]);
                addressDetailCityNameComboBox.setSelectedItem(parts[3]);
            }
        }

        // Gọi hàm từ CommonPL để render danh sách địa chỉ
        CommonPL.renderAllComponentToSelectAddress(addressDetailHouseNumberAndStreetNameInput,
                addressDetailHouseNumberAndStreetNameTextField, addressDetailCityNameSelected,
                addressDetailCityNameComboBox, addressDetailDistrictNameSelected, addressDetailDistrictNameComboBox,
                addressDetailWardNameSelected, addressDetailWardNameComboBox);

        addressDetailApplyButton = CommonPL.getButtonDefaultForm("Chọn địa chỉ", CommonPL.getFontParagraphBold());
        addressDetailApplyButton.setBounds(20, 390, 460, 40);
        addressDetailApplyButton.addActionListener(e -> {
            String houseNumberAndStreetName = addressDetailHouseNumberAndStreetNameTextField.getText();
            String cityName = (String) addressDetailCityNameComboBox.getSelectedItem();
            String districtName = (String) addressDetailDistrictNameComboBox.getSelectedItem();
            String wardName = (String) addressDetailWardNameComboBox.getSelectedItem();

            if (houseNumberAndStreetName.equals("Nhập Số nhà - tên đường") || wardName.equals("Chọn Phường / Xã")
                    || districtName.equals("Chọn Quận / Huyện") || cityName.equals("Chọn Tỉnh / Thành phố")) {
                addressDetailApplyButton.setBackground(Color.BLACK);
                CommonPL.createErrorDialog("Thông báo lỗi", "Cần cung cấp đầy đủ thông tin cần thiết");
            } else {
                addressDetailHouseNumberAndStreetNameInput = houseNumberAndStreetName;
                addressDetailCityNameSelected = cityName;
                addressDetailDistrictNameSelected = districtName;
                addressDetailWardNameSelected = wardName;

                String fullAddress = houseNumberAndStreetName + ", " + wardName + ", " + districtName + ", " + cityName;
                addOrUpdateAddressTextField.setText(fullAddress);
                addOrUpdateAddressTextField.setForeground(Color.BLACK);
                addressDetailDialog.dispose();
            }
        });
        SwingUtilities.invokeLater(() -> addressDetailApplyButton.requestFocusInWindow());

        addressDetailBlockPanel = new JPanel();
        addressDetailBlockPanel.setLayout(null);
        addressDetailBlockPanel.setBounds(0, 0, 500, 480);
        addressDetailBlockPanel.setBackground(Color.WHITE);
        addressDetailBlockPanel.add(addressDetailHouseNumberAndStreetNameLabel);
        addressDetailBlockPanel.add(addressDetailHouseNumberAndStreetNameTextField);
        addressDetailBlockPanel.add(addressDetailCityNameLabel);
        addressDetailBlockPanel.add(addressDetailCityNameComboBox);
        addressDetailBlockPanel.add(addressDetailDistrictNameLabel);
        addressDetailBlockPanel.add(addressDetailDistrictNameComboBox);
        addressDetailBlockPanel.add(addressDetailWardNameLabel);
        addressDetailBlockPanel.add(addressDetailWardNameComboBox);
        addressDetailBlockPanel.add(addressDetailApplyButton);

        addressDetailDialog.setTitle("Chọn địa chỉ");
        addressDetailDialog.setLayout(null);
        addressDetailDialog.setSize(500, 480);
        addressDetailDialog.setResizable(false);
        addressDetailDialog.setLocationRelativeTo(null);
        addressDetailDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addressDetailDialog.add(addressDetailBlockPanel);
        addressDetailDialog.setModal(true);
        addressDetailDialog.setVisible(true);
    }

    // Hiển thị dialog thêm/cập nhật
    private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
        addOrUpdateIdLabel = CommonPL.getParagraphLabel(
                "<html>Mã NCC <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateIdLabel.setBounds(20, 10, 460, 40);

        addOrUpdateIdTextField = new CustomTextField(0, 0, 0, "Tự động sinh", Color.LIGHT_GRAY, Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addOrUpdateIdTextField.setBounds(20, 50, 460, 40);
        addOrUpdateIdTextField.setEnabled(false);
        ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
        addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));

        addOrUpdateNameLabel = CommonPL.getParagraphLabel(
                "<html>Tên NCC <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateNameLabel.setBounds(20, 100, 460, 40);

        addOrUpdateNameTextField = new CustomTextField(0, 0, 0, "Nhập Tên NCC", Color.LIGHT_GRAY, Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addOrUpdateNameTextField.setBounds(20, 140, 460, 40);

        addOrUpdatePhoneLabel = CommonPL.getParagraphLabel(
                "<html>Số điện thoại <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdatePhoneLabel.setBounds(20, 190, 220, 40);

        addOrUpdatePhoneTextField = new CustomTextField(0, 0, 0, "Nhập SĐT", Color.LIGHT_GRAY, Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addOrUpdatePhoneTextField.setBounds(20, 230, 220, 40);
        addOrUpdatePhoneTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) || addOrUpdatePhoneTextField.getText().length() >= 10) {
                    evt.consume();
                }
            }
        });

        addOrUpdateEmailLabel = CommonPL.getParagraphLabel(
                "<html>Email <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateEmailLabel.setBounds(260, 190, 220, 40);

        addOrUpdateEmailTextField = new CustomTextField(0, 0, 0, "Nhập Email", Color.LIGHT_GRAY, Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addOrUpdateEmailTextField.setBounds(260, 230, 220, 40);

        addOrUpdateAddressLabel = CommonPL.getParagraphLabel(
                "<html>Địa chỉ <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateAddressLabel.setBounds(20, 280, 220, 40);

        addOrUpdateAddressButton = CommonPL.getButtonDefaultForm("Nhấn để chọn địa chỉ",
                new Font("Arial", Font.BOLD, 14));
        addOrUpdateAddressButton.setBounds(300, 286, 180, 28);
        addOrUpdateAddressButton.addActionListener(e -> {
            String initialAddress = addOrUpdateAddressTextField.getText();
            showAddressDetailDialog(initialAddress);
        });

        addOrUpdateAddressTextField = new CustomTextField(0, 0, 0, "Nhập Địa chỉ", Color.LIGHT_GRAY, Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addOrUpdateAddressTextField.setBounds(20, 320, 460, 40);
        addOrUpdateAddressTextField.setEnabled(true); // Chỉ cho phép chỉnh sửa qua dialog
        ((CustomTextField) addOrUpdateAddressTextField).setBorderColor(Color.decode("#dedede"));
        addOrUpdateAddressTextField.setBackground(Color.WHITE);

        addOrUpdateStatusLabel = CommonPL.getParagraphLabel(
                "<html>Trạng thái <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateStatusLabel.setBounds(20, 370, 460, 40);

        Vector<String> statusVector = CommonPL.getVectorHasValues(statusOptions);
        addOrUpdateStatusComboBox = CommonPL.CustomComboBox(statusVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
                Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateStatusComboBox.setBounds(20, 410, 460, 40);

        addOrUpdateTimeLabel = CommonPL.getParagraphLabel("Cập nhật gần đây:", Color.GRAY,
                new Font("Arial", Font.ITALIC, 18));
        addOrUpdateTimeLabel.setBounds(20, 460, 148, 40);

        addOrUpdateTimeDetailLabel = CommonPL.getTitleLabel("yyyy-MM-dd HH:mm:ss", Color.GRAY,
                new Font("Arial", Font.ITALIC, 18), SwingConstants.RIGHT, SwingConstants.CENTER);
        addOrUpdateTimeDetailLabel.setBounds(173, 460, 307, 40);
        Timer timer = new Timer(1000, e -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            addOrUpdateTimeDetailLabel.setText(formattedDateTime);
        });
        timer.start();

        addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button,
                button.equals("Thêm") ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
                CommonPL.getFontParagraphBold());
        addOrUpdateButton.setBounds(20, 500, 460, 40);
        SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

        // Khi "Thêm"
        if (title.equals("Thêm Nhà cung cấp") && button.equals("Thêm") && object.isEmpty()) {
            SupplierDTO lastSupplier = supplierBLL.getLastSupplier();
            String newId = "NCC" + String.format("%04d", Integer.parseInt(lastSupplier.getId().substring(3)) + 1);
            addOrUpdateIdTextField.setText(newId);
        }

        // Khi "Thay đổi"
        if (title.equals("Thay đổi Nhà cung cấp") && button.equals("Thay đổi") && !object.isEmpty()) {
            addOrUpdateIdTextField.setText((String) object.get(0));
            addOrUpdateIdTextField.setEnabled(false);
            ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
            addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));

            addOrUpdateNameTextField.setText((String) object.get(1));
            addOrUpdateNameTextField.setForeground(Color.BLACK);

            addOrUpdatePhoneTextField.setText((String) object.get(2));
            addOrUpdatePhoneTextField.setForeground(Color.BLACK);

            addOrUpdateEmailTextField.setText((String) object.get(3));
            addOrUpdateEmailTextField.setForeground(Color.BLACK);

            addOrUpdateAddressTextField.setText((String) object.get(4));
            addOrUpdateAddressTextField.setForeground(Color.BLACK);

            addOrUpdateStatusComboBox.setSelectedItem((String) object.get(5));
            addOrUpdateStatusComboBox.setEnabled(false);
            UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
            addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));
        }

        // Sự kiện nút "Thêm" hoặc "Thay đổi"
        addOrUpdateButton.addActionListener(e -> {
            String id = addOrUpdateIdTextField.getText();
            String name = !addOrUpdateNameTextField.getText().equals("Nhập Tên NCC") ? addOrUpdateNameTextField.getText() : null;
            String phone = !addOrUpdatePhoneTextField.getText().equals("Nhập SĐT") ? addOrUpdatePhoneTextField.getText() : null;
            String email = !addOrUpdateEmailTextField.getText().equals("Nhập Email") ? addOrUpdateEmailTextField.getText() : null;
            String address = !addOrUpdateAddressTextField.getText().equals("Nhập Địa chỉ") ? addOrUpdateAddressTextField.getText() : null;
            String status = !addOrUpdateStatusComboBox.getSelectedItem().equals("Chọn Trạng thái") ? (String) addOrUpdateStatusComboBox.getSelectedItem() : null;
            String dateUpdate = CommonPL.getCurrentDate();

            if (title.equals("Thêm Nhà cung cấp")) {
                String result = supplierBLL.insertSupplier(id, name, phone, email, address, status, dateUpdate);
                if (result.equals("Thêm nhà cung cấp thành công")) {
                    CommonPL.createSuccessDialog("Thông báo thành công", "Thêm nhà cung cấp thành công");
                    addOrUpdateDialog.dispose();
                    renderTableData(null, null, null);
                } else {
                    CommonPL.createErrorDialog("Thông báo lỗi", result);
                }
            } else if (title.equals("Thay đổi Nhà cung cấp")) {
                String result = supplierBLL.updateSupplier(id, name, phone, email, address, status, dateUpdate);
                if (result.equals("Cập nhật nhà cung cấp thành công")) {
                    CommonPL.createSuccessDialog("Thông báo thành công", "Cập nhật nhà cung cấp thành công");
                    addOrUpdateDialog.dispose();
                    renderTableData(null, null, null);
                } else {
                    CommonPL.createErrorDialog("Thông báo lỗi", result);
                }
            }
        });

        // Cấu hình dialog
        addOrUpdateBlockPanel = new JPanel();
        addOrUpdateBlockPanel.setLayout(null);
        addOrUpdateBlockPanel.setBounds(0, 0, 500, 590);
        addOrUpdateBlockPanel.setBackground(Color.WHITE);
        addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
        addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
        addOrUpdateBlockPanel.add(addOrUpdateNameLabel);
        addOrUpdateBlockPanel.add(addOrUpdateNameTextField);
        addOrUpdateBlockPanel.add(addOrUpdatePhoneLabel);
        addOrUpdateBlockPanel.add(addOrUpdatePhoneTextField);
        addOrUpdateBlockPanel.add(addOrUpdateEmailLabel);
        addOrUpdateBlockPanel.add(addOrUpdateEmailTextField);
        addOrUpdateBlockPanel.add(addOrUpdateAddressLabel);
        addOrUpdateBlockPanel.add(addOrUpdateAddressButton);
        addOrUpdateBlockPanel.add(addOrUpdateAddressTextField);
        addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
        addOrUpdateBlockPanel.add(addOrUpdateStatusComboBox);
        addOrUpdateBlockPanel.add(addOrUpdateTimeLabel);
        addOrUpdateBlockPanel.add(addOrUpdateTimeDetailLabel);
        addOrUpdateBlockPanel.add(addOrUpdateButton);

        addOrUpdateDialog = new JDialog();
        addOrUpdateDialog.setTitle(title);
        addOrUpdateDialog.setLayout(null);
        addOrUpdateDialog.setSize(500, 590);
        addOrUpdateDialog.setResizable(false);
        addOrUpdateDialog.setLocationRelativeTo(null);
        addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addOrUpdateDialog.add(addOrUpdateBlockPanel);
        addOrUpdateDialog.setModal(true);
        addOrUpdateDialog.setVisible(true);
    }
}