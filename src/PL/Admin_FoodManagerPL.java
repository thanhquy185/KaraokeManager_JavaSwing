package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import BLL.CategoryBLL;
import BLL.FoodBLL;
import DTO.CategoryDTO;
import DTO.FoodDTO;
import DTO.PrivilegeDTO;
import PL.CommonPL.CustomTextField;

public class Admin_FoodManagerPL extends JPanel {
	// Đối tượng từ tầng Business Logic Layer
	private FoodBLL foodBLL;
	private CategoryBLL categoryBLL;

	// Các Component
	private JLabel titleLabel;
	// - Các Component của Filter Panel
	private JLabel findLabel;
	private JTextField findInputTextField;
	private JButton findInformButton;
	private JLabel sortsLabel;
	private Map<String, Boolean> sortsCheckboxs;
	private JButton sortsButton;
	private JLabel categoriesLabel;
	private Map<String, Boolean> categoriesRadios;
	private JButton categoriesButton;
	private JLabel inventoryLabel;
	private Map<String, Boolean> inventoryRadios;
	private JButton inventoryButton;
	private JLabel statusLabel;
	private Map<String, Boolean> statusRadios;
	private JButton statusButton;
	private JButton filterApplyButton;
	private JButton filterResetButton;
	private JPanel filterPanel;
	// - Các Component của Data Panel
	private JButton addButton;
	private JButton updateButton;
	private JButton lockButton;
	private JTable tableData;
	private JScrollPane tableScrollPane;
	private JPanel dataPanel;
	// - Các Component của Add Or Update Dialog
	private JLabel addOrUpdateIdLabel;
	private JTextField addOrUpdateIdTextField;
	private JLabel addOrUpdateAvatarLabel;
	private JLabel addOrUpdateAvatarImage;
	private JTextField addOrUpdateAvatarTextField;
	private JButton addOrUpdateAvatarButton;
	private JLabel addOrUpdateNameLabel;
	private JTextField addOrUpdateNameTextField;
	private JLabel addOrUpdateCategoryLabel;
	private JComboBox<String> addOrUpdateCategoryComboBox;
	private JLabel addOrUpdateInventoryLabel;
	private JTextField addOrUpdateInventoryTextField;
	private JLabel addOrUpdatePriceLabel;
	private JTextField addOrUpdatePriceTextField;
	private JLabel addOrUpdateStatusLabel;
	private JComboBox<String> addOrUpdateStatusComboBox;
	private JButton addOrUpdateButton;
	private JPanel addOrUpdateBlockPanel;
	private JDialog addOrUpdateDialog;

	// - Các thông tin cần có của Table Data và Table Scroll Pane
	private final String[] columns = { "Mã món ăn", "Tên món ăn", "Loại món ăn", "Tồn kho", "Giá bán", "Trạng thái" };
	private final int[] widthColumns = { 150, 500, 200, 150, 150, 150 };
	private Object[][] datas = {};
	private int rowSelected = -1;
	private Boolean[] valueSelected = { null };

	// - Các giá trị mặc định cho text field, text area hoặc combobox để tìm kiếm,
	// thêm, sửa và xoá
	private final Map<String, String> defaultValuesForCrud = new LinkedHashMap<String, String>() {
		{
			put("id", "Nhập Mã món ăn");
			put("name", "Nhập Tên món ăn");
			put("category", "Chọn Loại món ăn");
			put("price", "Nhập Giá bán");
			put("inventory", "Nhập Tồn kho");
			put("image", "Chọn Hình ảnh");
			put("status", "Chọn Trạng thái");
		}
	};

	// - Biến chứa file ảnh của món ăn
	private File fileImage;

	// - Các thông tin cần thiết cho lọc và sắp xếp
	// + Sắp xếp
	private final String[] sortsString = { "Mã món ăn tăng dần", "Mã món ăn giảm dần",
			"Tên món ăn tăng dần", "Tên món ăn giảm dần", "Giá bán tăng dần", "Giá bán giảm dần" };
	private final String[] sortsSQL = { "maMonAn ASC", "maMonAn DESC", "tenMonAn ASC",
			"tenMonAn DESC", "giaBan ASC", "giaBan DESC", };
	// + Loại món ăn
	private final String[] categoriesStringForFilter;
	private final String[] categoriesStringForAddOrUpdate;
	private final String[] categoriesSQL;
	// + Tồn kho
	private final String[] inventoryString = { "Tất cả", "Còn hàng", "Hết hàng" };
	private final String[] inventorySQL = { "", "tonKho > 0", "tonKho = 0" };
	// + Trạng thái
	private final String[] statusStringForFilter = { "Tất cả", "Hoạt động", "Tạm dừng" };
	private final String[] statusStringForAddOrUpdate = { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" };
	private final String[] statusSQL = { "", "trangThai = 1", "trangThai = 0" };

	public Admin_FoodManagerPL() {
		// Khởi tạo đối tượng BLL
		foodBLL = new FoodBLL();
		categoryBLL = new CategoryBLL();

		// Cập nhật dữ liệu cần thiết
		categoriesStringForFilter = renderCategoriesString("Tìm kiếm");
		categoriesStringForAddOrUpdate = renderCategoriesString("Thêm hoặc sửa");
		categoriesSQL = renderCategoriesString("Truy vấn SQL");

		// <===== Cấu trúc Title Label =====>
		titleLabel = CommonPL.getTitleLabel("Món ăn", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);

		// <===== Cấu trúc Filter Panel =====>
		findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
		findLabel.setBounds(15, 15, 90, 24);

		findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
				"Bạn có thể tìm kiếm bằng các thông tin như: mã món ăn, tên món ăn", Color.BLACK,
				CommonPL.getFontQuestionIcon());
		findInformButton.setBounds(110, 15, 24, 24);

		findInputTextField = new CommonPL.CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		findInputTextField.setBounds(15, 45, 360, 40);

		sortsLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
		sortsLabel.setBounds(390, 15, 360, 24);

		sortsCheckboxs = CommonPL.getMapHasValues(sortsString);
		sortsButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortsCheckboxs, sortsString[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		sortsButton.setBounds(390, 45, 360, 40);

		categoriesLabel = CommonPL.getParagraphLabel("Loại món ăn", Color.BLACK, CommonPL.getFontParagraphPlain());
		categoriesLabel.setBounds(765, 15, 360, 24);

		categoriesRadios = CommonPL.getMapHasValues(categoriesStringForFilter);
		categoriesButton = CommonPL.ButtonHasRadios.createButtonHasRadios(categoriesRadios,
				categoriesStringForFilter[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		categoriesButton.setBounds(765, 45, 360, 40);

		inventoryLabel = CommonPL.getParagraphLabel("Tồn kho", Color.BLACK, CommonPL.getFontParagraphPlain());
		inventoryLabel.setBounds(15, 100, 360, 24);

		inventoryRadios = CommonPL.getMapHasValues(inventoryString);
		inventoryButton = CommonPL.ButtonHasRadios.createButtonHasRadios(inventoryRadios, inventoryString[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		inventoryButton.setBounds(15, 130, 360, 40);

		statusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK, CommonPL.getFontParagraphPlain());
		statusLabel.setBounds(390, 100, 360, 24);

		statusRadios = CommonPL.getMapHasValues(statusStringForFilter);
		statusButton = CommonPL.ButtonHasRadios.createButtonHasRadios(statusRadios, statusStringForFilter[0],
				Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		statusButton.setBounds(390, 130, 360, 40);

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
		filterPanel.add(sortsLabel);
		filterPanel.add(sortsButton);
		filterPanel.add(categoriesLabel);
		filterPanel.add(categoriesButton);
		filterPanel.add(inventoryLabel);
		filterPanel.add(inventoryButton);
		filterPanel.add(statusLabel);
		filterPanel.add(statusButton);
		filterPanel.add(filterApplyButton);
		filterPanel.add(filterResetButton);

		// <===== Cấu trúc Data Panel =====>
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

		tableData = CommonPL.createTableData(columns, widthColumns, datas, "food manager");
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

		// Định nghĩa tính chất cho PL
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
			showAddOrUpdateDialog("Thêm món ăn", "Thêm", new Vector<>());
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});

		// Sự kiện nút "Thay đổi"
		updateButton.addActionListener(e -> {
			if (rowSelected != -1) {
				Vector<Object> currentObject = new Vector<>();
				FoodDTO foodSelected = foodBLL.getOneFoodById(String.valueOf(tableData.getValueAt(rowSelected, 0)));
				currentObject.add(foodSelected.getId());
				currentObject.add(foodSelected.getName());
				currentObject.add(foodSelected.getCategory());
				currentObject.add(foodSelected.getInventory());
				currentObject.add(foodSelected.getPrice());
				currentObject.add(foodSelected.getImage());
				currentObject.add(foodSelected.getStatus());

				System.out.println(foodSelected.getImage());

				showAddOrUpdateDialog("Thay đổi món ăn", "Thay đổi", currentObject);
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
				FoodDTO foodSelected = foodBLL.getOneFoodById(String.valueOf(tableData.getValueAt(rowSelected, 0)));
				currentObject.add(foodSelected.getId());
				currentObject.add(foodSelected.getName());
				currentObject.add(foodSelected.getCategory());
				currentObject.add(foodSelected.getInventory());
				currentObject.add(foodSelected.getPrice());
				currentObject.add(foodSelected.getImage());
				currentObject.add(foodSelected.getStatus());

				CommonPL.createSelectionsDialog("Thông báo lựa chọn",
						String.format("Có chắc chắn muốn %s món ăn này?",
								currentObject.get(6).equals("Hoạt động") ? "khóa" : "mở khóa"),
						valueSelected);

				if (valueSelected[0]) {
					String result = foodBLL.lockFood((String) currentObject.get(0),
							CommonPL.getCurrentDatetime());
					if (result.equals("Có thể thay đổi trạng thái món ăn")) {
						CommonPL.createSuccessDialog("Thông báo thành công",
								currentObject.get(6).equals("Hoạt động") ? "Khóa thành công" : "Mở khóa thành công");
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

	// Hàm cập nhật dữ liệu loại món ăn cho việc tìm kiếm, thêm và sửa
	private String[] renderCategoriesString(String action) {
		ArrayList<CategoryDTO> categoryList = categoryBLL.getAllCategory();

		int validLength = 0;
		for (int i = 0; i < categoryList.size(); i++) {
			if (categoryList.get(i).getStatus()) {
				validLength++;
			}
		}

		String[] result = new String[validLength + 1];
		if (action.equals("Tìm kiếm")) {
			result[0] = "Tất cả";
		} else if (action.equals("Thêm hoặc sửa")) {
			result[0] = defaultValuesForCrud.get("category");
		} else if (action.equals("Truy vấn SQL")) {
			result[0] = "";
		}
		for (int i = 0; i < categoryList.size(); i++) {
			if (categoryList.get(i).getStatus()) {
				if (action.equals("Tìm kiếm")) {
					result[i + 1] = String.format("%s", categoryList.get(i).getName());
				} else if (action.equals("Thêm hoặc sửa")) {
					result[i + 1] = String.format("%s - %s", categoryList.get(i).getId(),
							categoryList.get(i).getName());
				} else if (action.equals("Truy vấn SQL")) {
					result[i + 1] = String.format("maLoaiMonAn = '%s'", categoryList.get(i).getId());
				}
			}
		}

		return result;
	}

	// Hàm cập nhật dữ liệu cho bảng từ BLL
	private void renderTableData(String[] join, String condition, String order) {
		ArrayList<FoodDTO> foodList = foodBLL.getAllFoodByCondition(join, condition, order);
		Object[][] datasQuery = new Object[foodList.size()][columns.length];
		for (int i = 0; i < foodList.size(); i++) {
			datasQuery[i][0] = foodList.get(i).getId();
			datasQuery[i][1] = foodList.get(i).getName();
			datasQuery[i][2] = categoryBLL.getOneCategoryById(foodList.get(i).getCategory()).getName();
			datasQuery[i][3] = foodList.get(i).getInventory();
			datasQuery[i][4] = CommonPL.moneyLongToMoneyFormat(BigInteger.valueOf(foodList.get(i).getPrice()));
			datasQuery[i][5] = foodList.get(i).getStatus() ? "Hoạt động" : "Tạm dừng";
		}
		datas = datasQuery;
		CommonPL.updateRowsInTableData(tableData, datas);
	}

	// Hàm reset trang
	private void resetPage() {
		findInputTextField.setText("Nhập thông tin");
		findInputTextField.setForeground(Color.LIGHT_GRAY);
		CommonPL.resetMapForFilter(sortsCheckboxs, sortsString, sortsButton);
		CommonPL.resetMapForFilter(categoriesRadios, categoriesStringForFilter, categoriesButton);
		CommonPL.resetMapForFilter(inventoryRadios, inventoryString, inventoryButton);
		CommonPL.resetMapForFilter(statusRadios, statusStringForFilter, statusButton);
		renderTableData(null, null, null);
	}

	// Hàm xử lý sự kiện lọc dữ liệu
	private void filterDatasInTable() {
		filterApplyButton.addActionListener(e -> {
			String findValue = !findInputTextField.getText().equals("Nhập thông tin") ? findInputTextField.getText()
					: null;
			String sortsValue = CommonPL.getSQLFromCheckboxs(sortsCheckboxs, sortsSQL);
			String categoriesValue = CommonPL.getSQLFromRadios(categoriesRadios, categoriesSQL);
			String inventoryValue = CommonPL.getSQLFromRadios(inventoryRadios, inventorySQL);
			String statusValue = CommonPL.getSQLFromRadios(statusRadios, statusSQL);

			String condition = (findValue != null
					? String.format("(maMonAn LIKE '%%%s%%' OR tenMonAn LIKE '%%%s%%')", findValue, findValue)
					: "")
					+ (categoriesValue != null ? (findValue != null ? " AND " + categoriesValue : categoriesValue) : "")
					+ (inventoryValue != null
							? (findValue != null || categoriesValue != null ? " AND " + inventoryValue : inventoryValue)
							: "")
					+ (statusValue != null
							? (findValue != null || categoriesValue != null || inventoryValue != null
									? " AND " + statusValue
									: statusValue)
							: "");
			if (condition.length() == 0)
				condition = null;
			String orderStr = sortsValue;

			renderTableData(null, condition, orderStr);
		});

		filterResetButton.addActionListener(e -> {
			// Gọi hàm reset trang
			resetPage();
		});
	}

	// Hàm hiển thị Dialog thêm hoặc cập nhật món ăn
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
		// <===== Cấu trúc Add Or Update Block Panel =====>
		addOrUpdateIdLabel = CommonPL.getParagraphLabel(
				"<html>Mã món ăn <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateIdLabel.setBounds(500, 10, 460, 40);

		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("id"), Color.LIGHT_GRAY,
				Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateIdTextField.setEnabled(false);
		((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
		addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
		addOrUpdateIdTextField.setBounds(500, 50, 460, 40);

		// - Tuỳ chỉnh Add Or Update Avatar Label
		addOrUpdateAvatarLabel = CommonPL.getParagraphLabel("Hình ảnh", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAvatarLabel.setBounds(20, 10, 460, 40);

		// - Tuỳ chỉnh Add Or Update Avatar Image
		addOrUpdateAvatarImage = CommonPL.getImageLabel(300, 230,
				CommonPL.getMiddlePathToShowIcon() + "no-image-icon.png");
		addOrUpdateAvatarImage.setBounds(100, 115 - 10, 300, 230);
		addOrUpdateAvatarImage.setOpaque(true);

		// - Tuỳ chỉnh Add Or Update Avatar Text Field
		addOrUpdateAvatarTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("image"),
				Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAvatarTextField.setEnabled(false);
		((CustomTextField) addOrUpdateAvatarTextField).setBorderColor(Color.decode("#dedede"));
		addOrUpdateAvatarTextField.setBackground(Color.decode("#dedede"));
		addOrUpdateAvatarTextField.setBounds(20, 410, 460, 40);

		// - Tuỳ chỉnh Add Or Update Avatar Button
		addOrUpdateAvatarButton = CommonPL.getRoundedBorderButton(20, "Chọn ảnh",
				Color.decode("#42A5F5"), Color.WHITE,
				new Font("Arial", Font.BOLD, 14));
		addOrUpdateAvatarButton.setBounds(340, 376, 140, 28);
		addOrUpdateAvatarButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.addChoosableFileFilter(
					new javax.swing.filechooser.FileNameExtensionFilter("Ảnh", "jpg", "jpeg", "png", "gif"));

			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				fileImage = fileChooser.getSelectedFile();
				if (fileImage.exists()) {
					// Thay đổi image
					ImageIcon image = new ImageIcon(fileImage.getAbsolutePath());
					Image scaledImage = image.getImage().getScaledInstance(300, 230, Image.SCALE_SMOOTH);
					addOrUpdateAvatarImage.setIcon(new ImageIcon(scaledImage));
					addOrUpdateAvatarImage.setText("");

					// Thay đổi text field
					addOrUpdateAvatarTextField.setText(fileImage.getAbsolutePath());
					((CustomTextField) addOrUpdateAvatarTextField).setBorderColor(Color.decode("#dedede"));
					addOrUpdateAvatarTextField.setCaretPosition(0);

					// // Giả sử ấn là lưu ảnh
					// CommonPL.updateFoodImage(selectedFile, addOrUpdateIdTextField.getText());
				} else {
					JOptionPane.showMessageDialog(this, "Ảnh không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		addOrUpdateNameLabel = CommonPL.getParagraphLabel(
				"<html>Tên món ăn <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateNameLabel.setBounds(500, 100, 460, 40);

		addOrUpdateNameTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("name"),
				Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateNameTextField.setBounds(500, 140, 460, 40);

		addOrUpdateCategoryLabel = CommonPL.getParagraphLabel(
				"<html>Loại món ăn <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateCategoryLabel.setBounds(500, 190, 460, 40);

		Vector<String> categoriesVector = CommonPL.getVectorHasValues(categoriesStringForAddOrUpdate);
		addOrUpdateCategoryComboBox = CommonPL.CustomComboBox(categoriesVector, Color.WHITE, Color.LIGHT_GRAY,
				Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateCategoryComboBox.setBounds(500, 230, 460, 40);

		addOrUpdateInventoryLabel = CommonPL.getParagraphLabel("Tồn kho", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateInventoryLabel.setBounds(500, 280, 220, 40);

		addOrUpdateInventoryTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("inventory"),
				Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateInventoryTextField.setEnabled(false);
		((CustomTextField) addOrUpdateInventoryTextField).setBorderColor(Color.decode("#dedede"));
		addOrUpdateInventoryTextField.setBackground(Color.decode("#dedede"));
		addOrUpdateInventoryTextField.setBounds(500, 320, 220, 40);

		addOrUpdatePriceLabel = CommonPL.getParagraphLabel(
				"<html>Giá bán <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePriceLabel.setBounds(740, 280, 220, 40);

		addOrUpdatePriceTextField = new CommonPL.CustomTextField(0, 0, 0, defaultValuesForCrud.get("price"),
				Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdatePriceTextField.setBounds(740, 320, 220, 40);

		addOrUpdateStatusLabel = CommonPL.getParagraphLabel(
				"<html>Trạng thái <span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateStatusLabel.setBounds(500, 370, 460, 40);

		Vector<String> statusVector = CommonPL.getVectorHasValues(statusStringForAddOrUpdate);
		addOrUpdateStatusComboBox = CommonPL.CustomComboBox(statusVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateStatusComboBox.setBounds(500, 410, 460, 40);

		addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button,
				button.equals("Thêm") ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		addOrUpdateButton.setBounds(260, 500, 460, 40);
		SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

		// Khi "Thêm" món ăn
		if (title.equals("Thêm món ăn") && button.equals("Thêm") && object.isEmpty()) {
			String id = "MA" + String.format("%05d", Integer.parseInt(foodBLL.getLastfood().getId().substring(2)) + 1);
			addOrUpdateIdTextField.setText(id);
			((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));

			addOrUpdateInventoryTextField.setText("0");
			((CustomTextField) addOrUpdateInventoryTextField).setBorderColor(Color.decode("#dedede"));
		}

		// Khi "Thay đổi" món ăn
		if (title.equals("Thay đổi món ăn") && button.equals("Thay đổi") && !object.isEmpty()) {
			if (object.get(0) != null) {
				addOrUpdateIdTextField.setText(String.valueOf(object.get(0)));
				((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
				addOrUpdateIdTextField.setCaretPosition(0);
			}

			if (object.get(1) != null) {
				addOrUpdateNameTextField.setText(String.valueOf(object.get(1)));
				addOrUpdateNameTextField.setForeground(Color.BLACK);
				addOrUpdateNameTextField.setCaretPosition(0);
			}

			if (object.get(2) != null) {
				for (int i = 0; i < categoriesStringForAddOrUpdate.length; i++) {
					if (categoriesStringForAddOrUpdate[i].startsWith(String.valueOf(object.get(2)))) {
						addOrUpdateCategoryComboBox.setSelectedIndex(i);
						addOrUpdateCategoryComboBox.setForeground(Color.BLACK);
						((JTextField) addOrUpdateCategoryComboBox.getEditor().getEditorComponent()).setCaretPosition(0);

						break;
					}
				}
			}

			if (object.get(3) != null) {
				addOrUpdateInventoryTextField.setText(String.valueOf(object.get(3)));
				addOrUpdateInventoryTextField.setForeground(Color.BLACK);
				((CustomTextField) addOrUpdateInventoryTextField).setBorderColor(Color.decode("#dedede"));
				addOrUpdateInventoryTextField.setCaretPosition(0);
			}

			if (object.get(4) != null) {
				addOrUpdatePriceTextField.setText(String.valueOf(object.get(4)));
				addOrUpdatePriceTextField.setForeground(Color.BLACK);
				addOrUpdatePriceTextField.setCaretPosition(0);
			}

			if (object.get(5) != null) {
				// - Cập nhật image
				ImageIcon image = new ImageIcon(
						CommonPL.getMiddlePathToShowFoodImage() + String.valueOf(object.get(5)));
				Image scaledImage = image.getImage().getScaledInstance(300, 230, Image.SCALE_SMOOTH);
				addOrUpdateAvatarImage.setIcon(new ImageIcon(scaledImage));
				addOrUpdateAvatarImage.setText("");

				// - Cập nhật text field
				addOrUpdateAvatarTextField
						.setText(CommonPL.getMiddlePathToShowFoodImage() + String.valueOf(object.get(5)));
				((CustomTextField) addOrUpdateAvatarTextField).setBorderColor(Color.decode("#dedede"));
				addOrUpdateAvatarTextField.setCaretPosition(0);
			}

			if (object.get(6) != null) {
				addOrUpdateStatusComboBox.setSelectedItem(String.valueOf(object.get(6)));
				addOrUpdateStatusComboBox.setEnabled(false);
				UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
				addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));
				((JTextField) addOrUpdateStatusComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
			}

		}

		// Sự kiện nút "Thêm" hoặc "Thay đổi"
		addOrUpdateButton.addActionListener(e -> {
			String id = addOrUpdateIdTextField.getText();
			String name = !addOrUpdateNameTextField.getText().equals(defaultValuesForCrud.get("name"))
					? addOrUpdateNameTextField.getText()
					: null;
			String category = !addOrUpdateCategoryComboBox.getSelectedItem()
					.equals(defaultValuesForCrud.get("category"))
							? String.valueOf(addOrUpdateCategoryComboBox.getSelectedItem()).split(" - ")[0]
							: null;
			// String inventoryStr =
			// !addOrUpdateInventoryTextField.getText().equals(defaultValuesForCrud.get("inventory"))
			// ? addOrUpdateInventoryTextField.getText()
			// : "0";
			String priceStr = !addOrUpdatePriceTextField.getText().equals(defaultValuesForCrud.get("price"))
					? addOrUpdatePriceTextField.getText()
					: null;
			// String image =
			// !addOrUpdateAvatarTextField.getText().equals(defaultValuesForCrud.get("image"))
			// ? addOrUpdateAvatarTextField.getText()
			// : "0";
			String statusStr = !addOrUpdateStatusComboBox.getSelectedItem().equals(defaultValuesForCrud.get("status"))
					? (String) addOrUpdateStatusComboBox.getSelectedItem()
					: null;
			String timeUpdate = CommonPL.getCurrentDatetime();

			// - Biến chứa thông báo trả về
			String inform = null;
			// - Tuỳ vào tác vụ thêm hoặc thay đổi mà gọi đến hàm ở tầng BLL tương ứng
			if (title.equals("Thêm món ăn") && button.equals("Thêm")) {
				inform = foodBLL.insertFood(id, name, category, priceStr, fileImage, statusStr,
						timeUpdate);
			} else if (title.equals("Thay đổi món ăn") && button.equals("Thay đổi")) {
				inform = foodBLL.updateFood(id, name, category, priceStr, fileImage, timeUpdate);
			}
			// - Tuỳ vào kết quả của thông báo trả về mà thông báo và cập nhật bảng dữ liệu
			if (inform.equals("Có thể thêm một món ăn")
					|| inform.equals("Có thể thay đổi một món ăn")) {
				CommonPL.createSuccessDialog("Thông báo thành công", inform);
				addOrUpdateDialog.dispose();
				resetPage();
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", inform);
			}
			// - Thành công hay không thì cũng phải cập nhật lại file image
			fileImage = null;
		});

		// Cấu hình Add Or Update Block Panel
		addOrUpdateBlockPanel = new JPanel();
		addOrUpdateBlockPanel.setLayout(null);
		addOrUpdateBlockPanel.setBounds(0, 0, 980, 590);
		addOrUpdateBlockPanel.setBackground(Color.WHITE);
		addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
		addOrUpdateBlockPanel.add(addOrUpdateAvatarLabel);
		addOrUpdateBlockPanel.add(addOrUpdateAvatarImage);
		addOrUpdateBlockPanel.add(addOrUpdateAvatarButton);
		addOrUpdateBlockPanel.add(addOrUpdateAvatarTextField);
		addOrUpdateBlockPanel.add(addOrUpdateNameLabel);
		addOrUpdateBlockPanel.add(addOrUpdateNameTextField);
		addOrUpdateBlockPanel.add(addOrUpdateCategoryLabel);
		addOrUpdateBlockPanel.add(addOrUpdateCategoryComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateInventoryLabel);
		addOrUpdateBlockPanel.add(addOrUpdateInventoryTextField);
		addOrUpdateBlockPanel.add(addOrUpdatePriceLabel);
		addOrUpdateBlockPanel.add(addOrUpdatePriceTextField);
		addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
		addOrUpdateBlockPanel.add(addOrUpdateStatusComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateButton);

		// Định nghĩa tính chất cho Dialog
		addOrUpdateDialog = new JDialog();
		addOrUpdateDialog.setTitle(title);
		addOrUpdateDialog.setLayout(null);
		addOrUpdateDialog.setSize(980, 590);
		addOrUpdateDialog.setResizable(false);
		addOrUpdateDialog.setLocationRelativeTo(null);
		addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addOrUpdateDialog.add(addOrUpdateBlockPanel);
		addOrUpdateDialog.setModal(true);
		addOrUpdateDialog.setVisible(true);
	}
}