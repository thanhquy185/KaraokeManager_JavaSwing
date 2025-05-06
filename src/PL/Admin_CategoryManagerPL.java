package PL;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import javax.swing.UIManager;

import BLL.CategoryBLL;
import DTO.CategoryDTO;
import PL.CommonPL.CustomTextField;

public class Admin_CategoryManagerPL extends JPanel {
    // Các đối tượng từ tầng Bussiness Logical Layer
    private CategoryBLL categoryBLL;
    // Các Component
    private JLabel titleLabel;
    private JLabel findLabel;
    private JTextField findInputTextField;
    private JButton findInformButton;
    private JLabel sortLabel;
    private Map<String, Boolean> sortRadios;
    private JButton sortButton;
    private JLabel statusLabel;
    private Map<String, Boolean> statusRadios;
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
    private JLabel addOrUpdateStatusLabel;
    private JComboBox<String> addOrUpdateStatusComboBox;
    private JButton addOrUpdateButton;
    private JPanel addOrUpdateBlockPanel;
    private JDialog addOrUpdateDialog;

    // Thông tin bảng
    private final String[] columns = { "Mã loại món ăn", "Tên loại món ăn", "Trạng thái" };
    private final int[] widthColumns = { 300, 510, 300 };
    private Object[][] datas = {};
    private int rowSelected = -1;
    private Boolean[] valueSelected = { null };

    // Các giá trị mặc định cho text field, text area hoặc combobox để tìm kiếm,
    // thêm, sửa và xoá
    private final Map<String, String> defaultValuesForCrud = new LinkedHashMap<String, String>() {
        {
            put("id", "Nhập Mã loại món ăn");
            put("name", "Nhập Tên loại món ăn");
            put("status", "Chọn Trạng thái");
        }
    };

    // Thông tin lọc
    // - Sắp xếp
    private final String[] sortsString = { "Mã loại món ăn tăng dần", "Mã loại món ăn giảm dần",
            "Tên loại món ăn tăng dần", "Tên loại món ăn giảm dần" };
    private final String[] sortsSQL = { "maLoaiMonAn ASC", "maLoaiMonAn DESC", "tenLoaiMonAn ASC",
            "tenLoaiMonAn DESC" };
    // - Trạng thái
    private final String[] statusString = { "Tất cả", "Hoạt động", "Tạm dừng" };
    private final String[] statusSQL = { "", "trangThai = 1", "trangThai = 0" };
    private final String[] statusOptions = { defaultValuesForCrud.get("status"), "Hoạt động", "Tạm dừng" };

    public Admin_CategoryManagerPL() {
        categoryBLL = new CategoryBLL();

        // Tiêu đề
        titleLabel = CommonPL.getTitleLabel("Loại món ăn", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
                SwingConstants.CENTER);
        titleLabel.setBounds(30, 0, 1140, 115);

        // Filter Panel
        findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
        findLabel.setBounds(15, 15, 90, 24);

        findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
                "Bạn có thể tìm kiếm bằng các thông tin như: mã loại món ăn, tên loại món ăn", Color.BLACK,
                CommonPL.getFontQuestionIcon());
        findInformButton.setBounds(110, 15, 24, 24);

        findInputTextField = new CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY, Color.BLACK,
                CommonPL.getFontParagraphPlain());
        findInputTextField.setBounds(15, 45, 360, 40);

        sortLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
        sortLabel.setBounds(390, 15, 360, 24);

        sortRadios = CommonPL.getMapHasValues(sortsString);
        sortButton = CommonPL.ButtonHasRadios.createButtonHasRadios(sortRadios, sortsString[0],
                Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        sortButton.setBounds(390, 45, 360, 40);

        statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
        statusLabel.setBounds(765, 15, 360, 24);

        statusRadios = CommonPL.getMapHasValues(statusString);
        statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusRadios, statusString[0],
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

        tableData = CommonPL.createTableData(columns, widthColumns, datas, "category manager");
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
            showAddOrUpdateDialog("Thêm Loại món ăn", "Thêm", new Vector<>());
            rowSelected = -1;
            valueSelected[0] = false;
            tableData.clearSelection();
        });

        // Sự kiện nút "Thay đổi"
        updateButton.addActionListener(e -> {
            if (rowSelected != -1) {
                String categoryIdSelected = String.valueOf(tableData.getValueAt(rowSelected, 0));
                CategoryDTO categorySelected = categoryBLL.getOneCategoryById(categoryIdSelected);
                Vector<Object> currentObject = new Vector<>();
                currentObject.add(categorySelected.getId());
                currentObject.add(categorySelected.getName());
                currentObject.add(categorySelected.getStatus() ? "Hoạt động" : "Tạm dừng");

                showAddOrUpdateDialog("Thay đổi Loại món ăn", "Thay đổi", currentObject);
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
                String categoryIdSelected = String.valueOf(tableData.getValueAt(rowSelected, 0));
                CategoryDTO categorySelected = categoryBLL.getOneCategoryById(categoryIdSelected);

                CommonPL.createSelectionsDialog("Thông báo lựa chọn",
                        String.format("Có chắc chắn muốn %s loại món ăn này?",
                                categorySelected.getStatus() ? "khóa" : "mở khóa"),
                        valueSelected);
                if (valueSelected[0]) {
                    String result = categoryBLL.lockCategory(categorySelected.getId(),
                            CommonPL.getCurrentDatetime());
                    if (result.equals("Thay đổi trạng thái thành công")) {
                        CommonPL.createSuccessDialog("Thông báo thành công",
                                categorySelected.getStatus() ? "Khóa thành công"
                                        : "Mở khóa thành công");
                        resetPage();
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
        ArrayList<CategoryDTO> categoryList = categoryBLL.getAllCategoryByCondition(join, condition, order);
        Object[][] newData = new Object[categoryList.size()][columns.length];
        for (int i = 0; i < categoryList.size(); i++) {
            newData[i][0] = categoryList.get(i).getId();
            newData[i][1] = categoryList.get(i).getName();
            newData[i][2] = categoryList.get(i).getStatus() ? "Hoạt động" : "Tạm dừng";
        }
        datas = newData;
        CommonPL.updateRowsInTableData(tableData, datas);
    }

    // Hàm reset lại trang
    private void resetPage() {
        findInputTextField.setText("Nhập thông tin");
        findInputTextField.setForeground(Color.LIGHT_GRAY);

        CommonPL.resetMapForFilter(sortRadios, sortsString, sortButton);

        CommonPL.resetMapForFilter(statusRadios, statusString, statusButton);

        renderTableData(null, null, null);
    }

    // Xử lý sự kiện lọc dữ liệu
    private void filterDatasInTable() {
        filterApplyButton.addActionListener(e -> {
            String findValue = !findInputTextField.getText().equals("Nhập thông tin") ? findInputTextField.getText()
                    : null;
            String sortValue = CommonPL.getSQLFromRadios(sortRadios, sortsSQL);
            String statusValue = CommonPL.getSQLFromRadios(statusRadios, statusSQL);

            String condition = (findValue != null
                    ? String.format("(maLoaiMonAn = '%s' OR tenLoaiMonAn LIKE '%%%s%%')", findValue, findValue)
                    : "")
                    + (statusValue != null ? (findValue != null ? " AND " + statusValue : statusValue) : "");
            if (condition.length() == 0)
                condition = null;

            renderTableData(null, condition, sortValue);
        });

        filterResetButton.addActionListener(e -> {
            // Gọi hàm reset trang
            resetPage();
        });
    }

    // Hiển thị dialog thêm/cập nhật
    private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
        addOrUpdateIdLabel = CommonPL.getParagraphLabel(
                "<html>Mã Loại món ăn <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addOrUpdateIdLabel.setBounds(20, 10, 460, 40);

        addOrUpdateIdTextField = new CustomTextField(0, 0, 0, defaultValuesForCrud.get("id"), Color.LIGHT_GRAY,
                Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateIdTextField.setHorizontalAlignment(JTextField.CENTER);
        addOrUpdateIdTextField.setEnabled(false);
        addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
        addOrUpdateIdTextField.setBounds(20, 50, 460, 40);

        addOrUpdateNameLabel = CommonPL.getParagraphLabel(
                "<html>Tên Loại món ăn <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addOrUpdateNameLabel.setBounds(20, 100, 460, 40);

        addOrUpdateNameTextField = new CustomTextField(0, 0, 0, defaultValuesForCrud.get("name"), Color.LIGHT_GRAY,
                Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addOrUpdateNameTextField.setBounds(20, 140, 460, 40);

        addOrUpdateStatusLabel = CommonPL.getParagraphLabel(
                "<html>Trạng thái <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
                CommonPL.getFontParagraphPlain());
        addOrUpdateStatusLabel.setBounds(20, 190, 460, 40);

        Vector<String> statusVector = CommonPL.getVectorHasValues(statusOptions);
        addOrUpdateStatusComboBox = CommonPL.CustomComboBox(statusVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
                Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
        addOrUpdateStatusComboBox.setBounds(20, 230, 460, 40);

        addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button,
                button.equals("Thêm") ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
                CommonPL.getFontParagraphBold());
        addOrUpdateButton.setBounds(20, 300, 460, 40);
        SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

        // Khi "Thêm"
        if (title.equals("Thêm Loại món ăn") && button.equals("Thêm") && object.isEmpty()) {
            CategoryDTO lastcategory = categoryBLL.getLastCategory();
            // Lấy số cuối cùng từ mã (2 chữ số) và tăng lên 1
            int lastNumber = Integer.parseInt(lastcategory.getId().substring(3)); // Lấy phần số từ Loại món ănxx
            String newId = "LMA" + String.format("%02d", (lastNumber + 1) % 100); // Giới hạn 2 số (00-99)
            addOrUpdateIdTextField.setText(newId);
            ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
        }

        // Khi "Thay đổi"
        if (title.equals("Thay đổi Loại món ăn") && button.equals("Thay đổi") && !object.isEmpty()) {
            if (object.get(0) != null) {
                addOrUpdateIdTextField.setText(String.valueOf(object.get(0)));
                ((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
            }

            if (object.get(1) != null) {
                addOrUpdateNameTextField.setText(String.valueOf(object.get(1)));
                addOrUpdateNameTextField.setForeground(Color.BLACK);
            }

            if (object.get(2) != null) {
                addOrUpdateStatusComboBox.setSelectedItem(String.valueOf(object.get(2)));
                addOrUpdateStatusComboBox.setEnabled(false);
                UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
                addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));
            }
        }

        // Sự kiện nút "Thêm" hoặc "Thay đổi"
        addOrUpdateButton.addActionListener(e -> {
            CommonPL.createSelectionsDialog("Thông báo lựa chọn",
                    String.format("Có chắc chắn %s loại món ăn này?", button.toLowerCase()),
                    valueSelected);
            if (valueSelected[0]) {
                String id = addOrUpdateIdTextField.getText();
                String name = !addOrUpdateNameTextField.getText().equals(defaultValuesForCrud.get("name"))
                        ? addOrUpdateNameTextField.getText()
                        : null;
                String status = !addOrUpdateStatusComboBox.getSelectedItem().equals(defaultValuesForCrud.get("status"))
                        ? (String) addOrUpdateStatusComboBox.getSelectedItem()
                        : null;
                String timeUpdate = CommonPL.getCurrentDatetime();

                // - Biến chứa thông báo trả về
                String inform = null;
                // - Tuỳ vào tác vụ thêm hoặc thay đổi mà gọi đến hàm ở tầng BLL tương ứng
                if (title.equals("Thêm Loại món ăn") && button.equals("Thêm")) {
                    inform = categoryBLL.insertCategory(id, name, status, timeUpdate);
                } else if (title.equals("Thay đổi Loại món ăn") && button.equals("Thay đổi")) {
                    inform = categoryBLL.updateCategory(id, name, timeUpdate);
                }
                // - Tuỳ vào kết quả của thông báo trả về mà thông báo và cập nhật bảng dữ liệu
                if (inform.equals("Thêm loại món ăn thành công") || inform.equals("Cập nhật loại món ăn thành công")) {
                    CommonPL.createSuccessDialog("Thông báo thành công", inform);
                    addOrUpdateDialog.dispose();
                    resetPage();
                } else {
                    CommonPL.createErrorDialog("Thông báo lỗi", inform);
                }
            }
            valueSelected[0] = false;
        });

        // Cấu hình dialog
        addOrUpdateBlockPanel = new JPanel();
        addOrUpdateBlockPanel.setLayout(null);
        addOrUpdateBlockPanel.setBounds(0, 0, 500, 390);
        addOrUpdateBlockPanel.setBackground(Color.WHITE);
        addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
        addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
        addOrUpdateBlockPanel.add(addOrUpdateNameLabel);
        addOrUpdateBlockPanel.add(addOrUpdateNameTextField);
        addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
        addOrUpdateBlockPanel.add(addOrUpdateStatusComboBox);
        addOrUpdateBlockPanel.add(addOrUpdateButton);

        // Định nghĩa tính chất cho Add Or Update Dialog
        addOrUpdateDialog = new JDialog();
        addOrUpdateDialog.setTitle(title);
        addOrUpdateDialog.setLayout(null);
        addOrUpdateDialog.setSize(500, 390);
        addOrUpdateDialog.setResizable(false);
        addOrUpdateDialog.setLocationRelativeTo(null);
        addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        addOrUpdateDialog.add(addOrUpdateBlockPanel);
        addOrUpdateDialog.setModal(true);
        addOrUpdateDialog.setVisible(true);
    }
}