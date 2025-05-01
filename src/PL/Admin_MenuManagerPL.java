// package PL;

// import java.awt.Color;
// import java.awt.Component;
// import java.awt.Dimension;
// import java.awt.FlowLayout;
// import java.awt.Font;
// import java.awt.Image;
// import java.awt.event.KeyEvent;
// import java.awt.event.WindowAdapter;
// import java.math.BigInteger;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.Comparator;
// import java.util.List;
// import java.util.Vector;

// import javax.swing.BorderFactory;
// import javax.swing.ImageIcon;
// import javax.swing.JButton;
// import javax.swing.JCheckBox;
// import javax.swing.JComboBox;
// import javax.swing.JDialog;
// import javax.swing.JLabel;
// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTextField;
// import javax.swing.ScrollPaneConstants;
// import javax.swing.SwingConstants;
// import javax.swing.SwingUtilities;
// import javax.swing.Timer;
// import javax.swing.UIManager;

// import BLL.FoodBLL;
// import BLL.ProductBLL;
// import BLL.ProductDetailBLL;
// import BLL.ProductTypeBLL;
// import DTO.FoodDTO;
// import DTO.ProductDTO;
// import DTO.ProductDetailDTO;
// import PL.CommonPL.CustomTextField;

// public class Admin_MenuManagerPL extends JPanel 
// {
// 	// Các đối tượng từ tầng Bussiness Logical Layer
// 	private ProductBLL productBLL;
// 	private ProductTypeBLL productTypeBLL;
// 	private ProductDetailBLL productDetailBLL;
// 	private FoodBLL ingredientBLL;
// 	private Object[][] datas;
// 	ArrayList<ProductDTO> productList;
// 	// Các Font
// 	private Font fontParagraph = new Font("Arial", Font.PLAIN, 14);
// 	private Font fontButton = new Font("Arial", Font.BOLD, 14);
// 	private Font fontTitleInProductInfor = new Font("Arial", Font.BOLD, 16);
// 	private Font fontParagraphInProductInfor = new Font("Arial", Font.PLAIN, 14);
// 	private Font fontInIngredientPanel = new Font("Arial", Font.PLAIN, 15);
// 	// Các Component
// 	private JLabel titleLabel;
// 	// - Các Component của Product Panel
// 	private JTextField findInputTextField;
// 	private JComboBox sortComboBox;
// 	private JComboBox groupComboBox;
// 	private JComboBox statusComboBox;
// 	private JButton filterButton;
// 	private JButton addButton;
// 	private JPanel listPanel;
// 	private JScrollPane listScrollPane;
// 	private JPanel productPanel;
// 	// - Các Component của Add Or Update Product Dialog
// 	private JLabel addOrUpdateAvatarImage;
// 	private JLabel addOrUpdateAvatarLabel;
// 	private JButton addOrUpdateAvatarButton;
// 	private JTextField addOrUpdateAvatarTextField;
// 	private JLabel addOrUpdateIdLabel;
// 	private JTextField addOrUpdateIdTextField;
// 	private JLabel addOrUpdateGroupLabel;
// 	private JComboBox<String> addOrUpdateGroupComboBox;
// 	private JLabel addOrUpdateNameLabel;
// 	private JTextField addOrUpdateNameTextField;
// 	private JLabel addOrUpdatePriceLabel;
// 	private JTextField addOrUpdatePriceTextField;
// 	private JLabel addOrUpdateStatusLabel;
// 	private JComboBox<String> addOrUpdateStatusComboBox;
// 	private JLabel addOrUpdateIngredientLabel;
// 	private JPanel addOrUpdateLine1IngredientPanel;
// 	private JPanel addOrUpdateLine2IngredientPanel;
// 	private JLabel addOrUpdateIngredientIdLabel;
// 	private JLabel addOrUpdateIngredientNameLabel;
// 	private JLabel addOrUpdateIngredientUnitLabel;
// 	private JLabel addOrUpdateIngredientInventoryLabel;
// 	private JLabel addOrUpdateIngredientSelectLabel;
// 	private JLabel addOrUpdateIngredientQuantityLabel;
// 	private JPanel addOrUpdateIngredientPanel;
// 	private JScrollPane addOrUpdateIngredientScrollPane;
// 	private JLabel addOrUpdateTimeLabel;
// 	private JLabel addOrUpdateTimeDetailLabel;
// 	private JButton addOrUpdateButton;
// 	private JPanel addOrUpdateBlockPanel;
// 	private JDialog addOrUpdateDialog;

// 	// - Các thông tin cần thiết
// 	private final String[] sortsString = { "Chọn Sắp xếp", "Tên tăng dần", "Tên giảm dần", "Giá tăng dần", "Giá giảm dần" };
// 	private final String[] sortsSQL = {"", "tenSanPham ASC", "tenSanPham DESC", "giaBan ASC", "giaBan DESC"};
// 	private final String[] statusStringForFilter = { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" };
// 	private final String[] statusSQL = { "", "trangThai = 1", "trangThai = 0" };
// 	private final String[] typeSQL = { "", "maLoaiSanPham = 'KHO'", "maLoaiSanPham = 'NUOC'",
// 			"maLoaiSanPham = 'KHAC'", "maLoaiSanPham = 'TRANGMIENG'",  "maLoaiSanPham = 'DOUONG'", "maLoaiSanPham = 'CHATCOHAI'"};
// 	private final String[] typeStringForFilter = { "Chọn Nhóm", "Món khô", "Món nước", "Khác", "Món tráng miệng", "Đồ uống", "Chất có hại"};
// 	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
// 	private Boolean[] valueSelected = { null };

// 	public Admin_MenuManagerPL() {
// 		// <===== Các đối tượng từ tầng Bussiness Logical Layer =====>
// 		productBLL = new ProductBLL();
// 		productTypeBLL = new ProductTypeBLL();
// 		productDetailBLL = new ProductDetailBLL();
// 		ingredientBLL = new FoodBLL();
		
// 		// <==================== ====================>

// 		// <===== Cấu trúc của Title Label =====>
// 		// - Tuỳ chỉnh Title Label
// 		titleLabel = CommonPL.getTitleLabel("Thực đơn", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
// 				SwingConstants.CENTER);
// 		titleLabel.setBounds(30, 0, 1140, 115);
// 		// <==================== ====================>

// 		// <===== Cấu trúc của Product Panel =====>
// 		// - Tuỳ chỉnh Find Input Text Field
// 		findInputTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên sản phẩm", Color.LIGHT_GRAY, Color.BLACK,
// 				fontParagraph);
// 		findInputTextField.setBounds(15, 15, 180, 30);

// 		// - Tuỳ chỉnh Filter Type ComboBox
// 		Vector<String> types = CommonPL.getVectorHasValues(sortsString);
// 		sortComboBox = CommonPL.CustomComboBox(types, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE,
// 				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
// 		sortComboBox.setBounds(210, 15, 180, 30);

// 		// - Tuỳ chỉnh Filter Group ComboBox
// 		Vector<String> groups = CommonPL.getVectorHasValues(typeStringForFilter);
// 		groupComboBox = CommonPL.CustomComboBox(groups, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE,
// 				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
// 		groupComboBox.setBounds(405, 15, 180, 30);

// 		// - Tuỳ chỉnh Filter Status ComboBox
// 		Vector<String> status = CommonPL
// 				.getVectorHasValues(statusStringForFilter);
// 		statusComboBox = CommonPL.CustomComboBox(status, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE,
// 				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
// 		statusComboBox.setBounds(600, 15, 180, 30);

// 		// - Tuỳ chỉnh Filter Button
// 		filterButton = CommonPL.getRoundedBorderButton(14, "Lọc", Color.decode("#007bff"), Color.WHITE, fontButton);
// 		filterButton.setBounds(795, 15, 75, 30);
// 		filterButton.addActionListener(e -> {
// 			filterProducts();
// 		});
	
// 		// - Tuỳ chỉnh Add Button
// 		addButton = CommonPL.getRoundedBorderButton(14, "Thêm", Color.decode("#699f4c"), Color.WHITE, fontButton);
// 		addButton.setBounds(1005, 15, 120, 30);
// 		addButton.addActionListener(e -> {
// 			// + Hiển thị modal để sửa thông tin sản phẩm
// 			boolean added = showAddOrUpdateDialog("Thêm Sản phẩm", "Thêm", new Vector<Object>());
// 			// + Cập nhật lại giao diện nếu thêm/sửa thành công
// 			if (added) {
// 				refreshProductData(null,null,null);
// 			}
// 			valueSelected[0] = false;
// 		});

// 		// - Tuỳ chỉnh List Panel
// 		listPanel = new JPanel();
// 		listPanel.setLayout(null);
// 		listPanel.setBackground(Color.WHITE);

// 		// - Tuỳ chỉnh List Scroll Pane
// 		listScrollPane = new JScrollPane(listPanel);
// 		listScrollPane.setBounds(15, 60, 1125, 630);
// 		listScrollPane.setBorder(null);
// 		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
// 		listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

// 		// - Tuỳ chỉnh Product Panel;
// 		productPanel = new CommonPL.RoundedPanel(12);
// 		productPanel.setLayout(null);
// 		productPanel.setBackground(Color.WHITE);
// 		productPanel.setBounds(30, 115, 1140, 705);
// 		productPanel.add(findInputTextField);
// 		productPanel.add(sortComboBox);
// 		productPanel.add(groupComboBox);
// 		productPanel.add(statusComboBox);
// 		productPanel.add(filterButton);
// 		productPanel.add(addButton);
// 		productPanel.add(listScrollPane);
// 		// <==================== ====================>

// 		// Đĩnh nghĩa tính chất cho Dashboard Manager PL
// 		this.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
// 		this.setLayout(null);
// 		this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
// 		this.setBackground(Color.decode("#E3F2FD"));
// 		this.setLayout(null);
// 		this.add(titleLabel);
// 		this.add(productPanel);

// 		// Thiết lập sự kiện cập nhật danh sách sản phẩm
// 		refreshProductData(null,null,null);
// 	}

// 	// - Hàm khởi tạo lại giao diện 
// 	private void refreshProductData(String[] join, String condition, String order) {
// 		// - Truy vấn dữ liệu về tất cả sản phẩm hiện có
// 		productList = productBLL.getAllProductByCondition(join,condition,order);
// 		datas = new Object[productList.size()][6];
		
// 		listPanel.removeAll();
// 		for (int i = 0; i < productList.size(); i++) {
// 			datas[i][0] = (Object) productList.get(i).getId();
// 			datas[i][1] = (Object) productList.get(i).getName();
// 			datas[i][2] = (Object) productTypeBLL.getOneProductTypeById(productList.get(i).getProductTypeId()).getName();
// 			datas[i][3] = (Object) productList.get(i).getPrice();
// 			datas[i][4] = (Object) productList.get(i).getImage();
// 			datas[i][5] = (Object) (productList.get(i).getStatus() ? "Hoạt động" : "Tạm dừng");
// 		} 
// 		renderListPanel();
// 		listPanel.revalidate();
// 		listPanel.repaint();
// 	}
	
// 	// - Hàm cập nhật lại danh sách sản phẩm
// 	private void renderListPanel() 
// 	{
// 		// - Duyệt qua từng đối tượng
// 		int x = 0, y = 0;
// 		for (int i = 0; i < productList.size(); i++) 
// 		{
// 			// + Biến tạm giữ vị trí đối tượng được duyệt
// 			int j = i;
// 			// + Ảnh sản phẩm
// 			String s = (datas[i][4]!= null) ? String.valueOf(datas[i][4]) : "null";
// 			JLabel imageLabel = CommonPL.getImageLabel(210, 190,
// 					CommonPL.getMiddlePathToShowProductImage() + s);
// 			imageLabel.setBounds(2, 2, 206, 186);
// 			// + Mã sản phẩm
// 			JLabel idLabel = CommonPL.getParagraphLabel(String.valueOf(datas[i][0]), Color.BLACK,
// 					fontParagraphInProductInfor);
// 			idLabel.setBounds(0, 0, 210, 20);
// 			// + Tên sản phẩm
// 			JLabel nameLabel = CommonPL.getTitleLabel(String.valueOf(datas[i][1]), Color.BLACK, fontTitleInProductInfor,
// 					SwingConstants.CENTER, SwingConstants.CENTER);
// 			nameLabel.setBounds(0, 200, 210, 20);
// 			// + Nhóm sản phẩm
// 			JLabel groupLabel = CommonPL.getTitleLabel(String.valueOf(datas[i][2]), Color.LIGHT_GRAY,
// 					fontParagraphInProductInfor, SwingConstants.CENTER, SwingConstants.CENTER);
// 			groupLabel.setBounds(0, 220, 210, 20);
// 			// + Giá sản phẩm
// 			JLabel priceLabel = CommonPL.getTitleLabel(
// 					CommonPL.moneyLongToMoneyFormat(new BigInteger(String.valueOf(datas[i][3]))) + " VNĐ", Color.BLACK,
// 					fontParagraphInProductInfor, SwingConstants.CENTER, SwingConstants.CENTER);
// 			priceLabel.setBounds(0, 240, 210, 20);
// 			// + Nút cập nhật sản phẩm
// 			JButton updateButton = CommonPL.getRoundedBorderButton(14, "Sửa", Color.decode("#bf873e"), Color.WHITE,
// 					new Font("Arial", Font.BOLD, 14));
// 			updateButton.setBounds(10, 270, 90, 30);
// 			updateButton.addActionListener(e -> {
// 				// + Truy vấn thông tin sản phẩm hiện tại
// 				Vector<Object> object = new Vector<>();
// 				for (int k = 0; k < 6; k++) {
// 					object.add(datas[j][k]);
// 				}
// 				// + Hiển thị modal để sửa thông tin sản phẩm
// 				boolean updated = showAddOrUpdateDialog("Sửa Sản phẩm", "Sửa", object);
// 				// + Cập nhật lại giao diện nếu thêm/sửa thành công
// 				if (updated) {
// 					refreshProductData(null,null,null);
// 				}
// 				valueSelected[0] = false;
// 			});

// 			// + Nút khoá sản phẩm
// 			JButton lockButton = CommonPL.getRoundedBorderButton(14, "Khoá", Color.decode("#9f4d4d"), Color.WHITE,
// 					new Font("Arial", Font.BOLD, 14));
// 			lockButton.setBounds(110, 270, 90, 30);
// 			lockButton.addActionListener(e -> {
// 				// Truy vấn thông tin sản phẩm hiện tại từ datas
// 				Vector<Object> currentObject = new Vector<>();
// 				for (int k = 0; k < 6; k++) {
// 					currentObject.add(datas[j][k]);
// 				}
			
// 				// Xác định hành động (Khoá hoặc Mở khoá)
// 				boolean isActive = currentObject.get(5).equals("Hoạt động");
// 				String action = isActive ? "khoá" : "mở khoá";
			
// 				// Hiển thị hộp thoại xác nhận
// 				CommonPL.createSelectionsDialog("Thông báo lựa chọn",
// 						String.format("Có chắc chắn muốn %s sản phẩm này?", action),
// 						valueSelected);
			
// 				if (valueSelected[0]) {
// 					// Gọi BLL để thay đổi trạng thái sản phẩm
// 					String inform = productBLL.lockProduct(String.valueOf(currentObject.get(0)), CommonPL.getCurrentDate());
			
// 					if (inform.equals("Có thể khoá một sản phẩm")) {
			
// 						// Hiển thị thông báo thành công với trạng thái mới
// 						CommonPL.createSuccessDialog("Thông báo thành công",
// 								datas[j][5].equals("Hoạt động") ? "Khóa thành công" :"Mở khóa thành công");
			
// 						// Cập nhật giao diện
// 						refreshProductData(null,null,null);
// 					} else {
// 						// Hiển thị thông báo lỗi nếu có vấn đề
// 						CommonPL.createErrorDialog("Thông báo lỗi", inform);
// 					}
// 				}
// 			});

// 			// + Khối chứa sản phẩm
// 			JPanel blockPanel = new JPanel();
// 			blockPanel.setLayout(null);
// 			blockPanel.setBounds(x, y, 210, 310);
// 			blockPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#DEDEDE"), 2));
// 			blockPanel.setBackground(Color.WHITE);
// 			blockPanel.add(idLabel);
// 			blockPanel.add(imageLabel);
// 			blockPanel.add(nameLabel);
// 			blockPanel.add(groupLabel);
// 			blockPanel.add(priceLabel);
// 			blockPanel.add(updateButton);
// 			blockPanel.add(lockButton);

// 			// + Thêm vào danh sách sản phẩm
// 			listPanel.add(blockPanel);

// 			// + Cập nhật lại vị trí của từng sản phẩm
// 			if (x == 900) {
// 				x = 0;
// 				y += 325;
// 			} else {
// 				x += 225;
// 			}
// 		}

// 		listPanel.setPreferredSize(new Dimension(1110, (int) ((Math.ceil(1.0 * datas.length / 5) * 325))));
// 	}

// 	private void filterProducts() 
// 	{
//     	// Giá trị ô tìm kiếm
// 		String findValue = !findInputTextField.getText().equals("Nhập Tên sản phẩm") ? findInputTextField.getText()
// 				: null;
//     	// Giá trị ô sắp xếp
// 		String sortValue = sortsSQL[sortComboBox.getSelectedIndex()];
// 		// Giá trị ô loại sản phẩm
//     	String typeValue = typeSQL[groupComboBox.getSelectedIndex()];
//     	// Giá trị ô trạng thái
// 		String statusValue = statusSQL[statusComboBox.getSelectedIndex()];
		
//     	// Gán lệnh SQL tương ứng để truy vấn rồi cập nhật bảng
// 		String condition = (findValue != null ? String.format(
// 			"(maSanPham LIKE '%%%s%%' OR tenSanPham LIKE '%%%s%%')", findValue, findValue) : "")
// 			+ (typeValue != "" ? (findValue != null ? (" AND " + typeValue) : typeValue) : "")
// 			+ (statusValue != ""
// 					? (findValue != null || typeValue != "" ? " AND " + statusValue
// 							: statusValue)
// 							: "");
// 		if (condition.length() == 0)
// 			condition = null;
// 		String order = (sortValue =="") ? null : sortValue;
//     	// Cập nhật dữ liệu hiển thị
//     	refreshProductData(null,condition,order);
// 	}

// 	//Hàm tạo giao diện cho dialog thêm hoặc sửa
// 	private void createDialog(String title)
// 	{
// 		// Định nghĩa tính chất cho Add Or Update Dialog
// 		addOrUpdateDialog = new JDialog();
// 		addOrUpdateDialog.setTitle(title);
// 		addOrUpdateDialog.setLayout(null);
// 		addOrUpdateDialog.setSize(980, 790);
// 		addOrUpdateDialog.setResizable(false);
// 		addOrUpdateDialog.setLocationRelativeTo(null);
// 		addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
// 		addOrUpdateDialog.setModal(true);
// 		// ======================================================================//

// 		// - Tuỳ chỉnh Add Or Update Avatar Label
// 		addOrUpdateAvatarLabel = CommonPL.getParagraphLabel("Hình ảnh", Color.BLACK, CommonPL.getFontParagraphPlain());
// 		addOrUpdateAvatarLabel.setBounds(20, 10, 82, 40);

// 		// - Tuỳ chỉnh Add Or Update Avatar Image
// 		addOrUpdateAvatarImage = CommonPL.getImageLabel(200, 200,
// 				CommonPL.getMiddlePathToShowIcon() + "no-image-icon.png");
// 		addOrUpdateAvatarImage.setBounds(155, 20, 190, 190);
// 		addOrUpdateAvatarImage.setOpaque(true);

// 		// - Tuỳ chỉnh Add Or Update Avatar Button
// 		addOrUpdateAvatarButton = CommonPL.getButtonDefaultForm("Thay đổi", new Font("Arial", Font.BOLD, 14));
// 		addOrUpdateAvatarButton.setBounds(380, 196, 100, 28);
// 		addOrUpdateAvatarButton.addActionListener(e -> {
// 			String src = addOrUpdateAvatarTextField.getText();
// 			if (!src.equals(CommonPL.getMiddlePathToShowIcon() + "no-image-icon.png")) {
// 				ImageIcon image = new ImageIcon(src);
// 				Image scaledImage = image.getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH);
// 				ImageIcon scaledIcon = new ImageIcon(scaledImage);
// 				addOrUpdateAvatarImage.setIcon(scaledIcon);
// 			}
// 		});

// 		// - Tuỳ chỉnh Add Or Update Avatar TextField
// 		addOrUpdateAvatarTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập đường dẫn ảnh", Color.LIGHT_GRAY,
// 				Color.BLACK, CommonPL.getFontParagraphPlain());
// 		addOrUpdateAvatarTextField.setBounds(20, 230, 460, 40);

// 		// - Tuỳ chỉnh Add Or Update Id Label
// 		addOrUpdateIdLabel = CommonPL.getParagraphLabel("<html>Mã sản phẩm&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
// 		addOrUpdateIdLabel.setBounds(500, 10, 220, 40);

// 		// - Tuỳ chỉnh Add Or Update Id TextField
// 		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã sản phẩm", Color.LIGHT_GRAY,
// 				Color.BLACK, CommonPL.getFontParagraphPlain());
// 		addOrUpdateIdTextField.setBounds(500, 50, 220, 40);

// 		// - Tuỳ chỉnh Add Or Update Group Label
// 		addOrUpdateGroupLabel = CommonPL.getParagraphLabel("<html>Nhóm&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
// 		addOrUpdateGroupLabel.setBounds(740, 10, 220, 40);

// 		// - Tuỳ chỉnh Add Or Update Group ComboBox
// 		Vector<String> groups = CommonPL.getVectorHasValues(new String[] {"Chọn Nhóm", "Món khô", "Món nước",
// 				"Khác", "Món tráng miệng", "Đồ uống", "Chất có hại"});
// 		addOrUpdateGroupComboBox = CommonPL.CustomComboBox(groups, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
// 				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
// 		addOrUpdateGroupComboBox.setBounds(740, 50, 220, 40);

// 		// - Tuỳ chỉnh Add Or Update Price Label
// 		addOrUpdatePriceLabel = CommonPL.getParagraphLabel("<html>Giá bán&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK, CommonPL.getFontParagraphPlain());
// 		addOrUpdatePriceLabel.setBounds(500, 100, 220, 40);

// 		// - Tuỳ chỉnh Add Or Update Price TextField
// 		addOrUpdatePriceTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Giá bán", Color.LIGHT_GRAY, Color.BLACK,
// 				CommonPL.getFontParagraphPlain());
// 		addOrUpdatePriceTextField.setBounds(500, 140, 220, 40);

// 		// - Tuỳ chỉnh Add Or Update Status Label
// 		addOrUpdateStatusLabel = CommonPL.getParagraphLabel("<html>Trạng thái&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
// 				CommonPL.getFontParagraphPlain());
// 		addOrUpdateStatusLabel.setBounds(740, 100, 220, 40);

// 		// - Tuỳ chỉnh Add Or Update Status ComboBox
// 		Vector<String> status = CommonPL
// 				.getVectorHasValues(new String[] { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" });
// 		addOrUpdateStatusComboBox = CommonPL.CustomComboBox(status, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
// 				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
// 		addOrUpdateStatusComboBox.setBounds(740, 140, 220, 40);

// 		// - Tuỳ chỉnh Add Or Update Name Label
// 		addOrUpdateNameLabel = CommonPL.getParagraphLabel("<html>Tên sản phẩm&nbsp;<span style='color: red; font-size: 20px;'>*</span></html>", Color.BLACK,
// 				CommonPL.getFontParagraphPlain());
// 		addOrUpdateNameLabel.setBounds(500, 190, 460, 40);

// 		// - Tuỳ chỉnh Add Or Update Name TextField
// 		addOrUpdateNameTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên sản phẩm", Color.LIGHT_GRAY,
// 				Color.BLACK, CommonPL.getFontParagraphPlain());
// 		addOrUpdateNameTextField.setBounds(500, 230, 460, 40);

// 		// - Tuỳ chỉnh Add Or Update Ingredient Label
// 		addOrUpdateIngredientLabel = CommonPL.getTitleLabel("Nguyên liệu cần thiết", Color.BLACK,
// 				CommonPL.getFontParagraphPlain(), SwingConstants.CENTER, SwingConstants.CENTER);
// 		addOrUpdateIngredientLabel.setBounds(0, 300, 980, 40);

// 		// - Tuỳ chỉnh Add Or Update Line 1 Ingredient Panel
// 		addOrUpdateLine1IngredientPanel = new CommonPL.RoundedPanel(8);
// 		addOrUpdateLine1IngredientPanel.setBounds(20, 318, 360, 4);
// 		addOrUpdateLine1IngredientPanel.setBackground(Color.BLACK);

// 		// - Tuỳ chỉnh Add Or Update Line 2 Ingredient Panel
// 		addOrUpdateLine2IngredientPanel = new CommonPL.RoundedPanel(8);
// 		addOrUpdateLine2IngredientPanel.setBounds(600, 318, 360, 4);
// 		addOrUpdateLine2IngredientPanel.setBackground(Color.BLACK);

// 		// - Tuỳ chỉnh Add Or Update Ingredient Id Label
// 		addOrUpdateIngredientIdLabel = CommonPL.getTitleLabel("Mã nguyên liệu", Color.BLACK, fontInIngredientPanel,
// 				SwingConstants.CENTER, SwingConstants.CENTER);
// 		addOrUpdateIngredientIdLabel.setBounds(20, 340, 140, 40);

// 		// - Tuỳ chỉnh Add Or Update Ingredient Name Label
// 		addOrUpdateIngredientNameLabel = CommonPL.getTitleLabel("Tên nguyên liệu", Color.BLACK, fontInIngredientPanel,
// 				SwingConstants.CENTER, SwingConstants.CENTER);
// 		addOrUpdateIngredientNameLabel.setBounds(160, 340, 360, 40);

// 		// - Tuỳ chỉnh Add Or Update Ingredient Unit Label
// 		addOrUpdateIngredientUnitLabel = CommonPL.getTitleLabel("Đơn vị", Color.BLACK, fontInIngredientPanel,
// 				SwingConstants.CENTER, SwingConstants.CENTER);
// 		addOrUpdateIngredientUnitLabel.setBounds(520, 340, 100, 40);

// 		// - Tuỳ chỉnh Add Or Update Ingredient Inventory Label
// 		addOrUpdateIngredientInventoryLabel = CommonPL.getTitleLabel("Tồn kho", Color.BLACK, fontInIngredientPanel,
// 				SwingConstants.CENTER, SwingConstants.CENTER);
// 		addOrUpdateIngredientInventoryLabel.setBounds(620, 340, 100, 40);

// 		// - Tuỳ chỉnh Add Or Update Ingredient Select Label
// 		addOrUpdateIngredientSelectLabel = CommonPL.getTitleLabel("Chọn", Color.BLACK, fontInIngredientPanel,
// 				SwingConstants.CENTER, SwingConstants.CENTER);
// 		addOrUpdateIngredientSelectLabel.setBounds(720, 340, 60, 40);

// 		// - Tuỳ chỉnh Add Or Update Ingredient Quantity Label
// 		addOrUpdateIngredientQuantityLabel = CommonPL.getTitleLabel("Định lượng", Color.BLACK, fontInIngredientPanel,
// 				SwingConstants.CENTER, SwingConstants.CENTER);
// 		addOrUpdateIngredientQuantityLabel.setBounds(780, 340, 180, 40);
// 	}

// 	// Hàm xử lí dialog cho phép thêm hoặc cập nhật một sản phẩm
// 	private boolean showAddOrUpdateDialog(String title, String button, Vector<Object> object) 
// 	{
// 		// - Tạo biến kiểm tra kết quả thêm/sửa
// 		final boolean[] kq = {false};
// 		// Kiểm tra là nút thêm hay nút sửa
// 		boolean check = (title == "Sửa Sản phẩm") ? true : false;

// 		createDialog(title);
		
// 		// - Truy vấn bảng dữ liệu nguyên liệu và chi tiết sản phẩm
// 		ArrayList<FoodDTO> ingredientList = ingredientBLL.getAllIngredients();
// 		Object[][] datas = new Object[ingredientList.size()][6];
// 		ArrayList<JTextField> quantityFields = new ArrayList<>();
// 		ArrayList<JCheckBox> selectCheckbox = new ArrayList<>();
// 		// - Tuỳ chỉnh Add Or Update Ingredient Panel
// 		addOrUpdateIngredientPanel = new JPanel();
// 		addOrUpdateIngredientPanel.setLayout(null);
// 		addOrUpdateIngredientPanel.setPreferredSize(new Dimension(940, datas.length * 40));
// 		for (int i = 0; i < datas.length; i++) 
// 		{
// 			datas[i][0] = (Object) ingredientList.get(i).getId();
// 			datas[i][1] = (Object) ingredientList.get(i).getName();
// 			datas[i][2] = (Object) ingredientList.get(i).getUnit();
// 			datas[i][3] = (Object) ingredientList.get(i).getInventory();
			
// 			// Kiểm tra xem ingredientId có trong productDetail không
// 			boolean isSelected;
// 			if(check)
// 			{
// 				String s = String.format("maSanPham = '%s' AND maNguyenLieu = '%s' ", object.get(0), ingredientList.get(i).getId());
// 				ArrayList<ProductDetailDTO> detail = productDetailBLL.getAllProductDetailByCondition(null,s, null);

// 				isSelected = (detail != null && !detail.isEmpty());
//     			datas[i][4] = isSelected; 
//     			datas[i][5] = (isSelected) ? detail.get(0).getQuantity() : 0;
// 			}
// 			else 
// 			{
// 				datas[i][4] = isSelected = false; 
//     			datas[i][5] = 0;
// 			}
			
			
// 			// + Mã sản phẩm
// 			JLabel id = CommonPL.getTitleLabel(String.valueOf(datas[i][0]), Color.LIGHT_GRAY, fontInIngredientPanel,
// 					SwingConstants.CENTER, SwingConstants.CENTER);
// 			id.setBounds(0, 0, 140, 40);
// 			// + Tên sản phẩm
// 			JLabel name = CommonPL.getParagraphLabel(String.valueOf(datas[i][1]), Color.LIGHT_GRAY,
// 					fontInIngredientPanel);
// 			name.setBounds(140, 0, 360, 40);
// 			// + Đơn vị
// 			JLabel unit = CommonPL.getTitleLabel(String.valueOf(datas[i][2]), Color.LIGHT_GRAY, fontInIngredientPanel,
// 					SwingConstants.CENTER, SwingConstants.CENTER);
// 			unit.setBounds(500, 0, 100, 40);
// 			// + Tồn kho
// 			JLabel inventory = CommonPL.getTitleLabel(String.valueOf(datas[i][3]), Color.LIGHT_GRAY,
// 					fontInIngredientPanel, SwingConstants.CENTER, SwingConstants.CENTER);
// 			inventory.setBounds(600, 0, 100, 40);
// 			// + Chọn
// 			JCheckBox select = new JCheckBox();
// 			select.setSelected((boolean) datas[i][4]);
// 			select.setBounds(715, 0, 30, 40);
// 			select.setAlignmentX(Component.CENTER_ALIGNMENT);
// 			select.setFont(fontInIngredientPanel);
// 			selectCheckbox.add(select);
// 			// + Định lượng
// 			JTextField quantity = new JTextField();
// 			quantity.setEnabled(false);
// 			quantity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
// 			quantity.setBounds(760, 2, 180, 36);
// 			quantity.setBackground(Color.WHITE);
// 			quantity.setText(String.valueOf(datas[i][5]));
// 			quantity.setFont(fontInIngredientPanel);
// 			quantity.setForeground(Color.LIGHT_GRAY);
// 			quantityFields.add(quantity); // Lưu JTextField vào danh sách
// 			// + Đường kẻ ngang
// 			JPanel linePanel = new CommonPL.RoundedPanel(8);
// 			linePanel.setBounds(0, 0, 940, 2);
// 			// + Dòng chứa các dữ liệu
// 			JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
// 			rowPanel.setLayout(null);
// 			rowPanel.setBounds(0, i * 40, 940, 40);
// 			rowPanel.setBackground(Color.WHITE);
// 			if (i >= 1)
// 				rowPanel.add(linePanel);
// 			rowPanel.add(id);
// 			rowPanel.add(name);
// 			rowPanel.add(unit);
// 			rowPanel.add(inventory);
// 			rowPanel.add(select);
// 			rowPanel.add(quantity);

// 			// + Nếu mục đã được chọn từ trước
// 			if (isSelected) {
// 				id.setForeground(Color.BLACK);
// 				name.setForeground(Color.BLACK);
// 				unit.setForeground(Color.BLACK);
// 				inventory.setForeground(Color.BLACK);
// 				quantity.setEnabled(true);
// 				quantity.setForeground(Color.BLACK);
// 				quantity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
// 			}

// 			// + Gán sự kiện thay đổi khi chọn vào mục nguyên liệu
// 			select.addActionListener(e -> {
// 				boolean selected = select.isSelected();
//         		id.setForeground(selected ? Color.BLACK : Color.LIGHT_GRAY);
//         		name.setForeground(selected ? Color.BLACK : Color.LIGHT_GRAY);
//         		unit.setForeground(selected ? Color.BLACK : Color.LIGHT_GRAY);
//         		inventory.setForeground(selected ? Color.BLACK : Color.LIGHT_GRAY);
//         		quantity.setEnabled(selected);
//         		quantity.setForeground(selected ? Color.BLACK : Color.LIGHT_GRAY);
//         		quantity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, selected ? Color.BLACK : Color.LIGHT_GRAY));
// 			});

// 			// + Thêm vào addOrUpdateIngredientPanel
// 			addOrUpdateIngredientPanel.setBackground(Color.WHITE);
// 			addOrUpdateIngredientPanel.add(rowPanel);
// 		}

// 		// - Tuỳ chỉnh Add Or Update Ingredient Scroll Pane
// 		addOrUpdateIngredientScrollPane = new JScrollPane(addOrUpdateIngredientPanel);
// 		addOrUpdateIngredientScrollPane.setBounds(20, 390, 960, 250);
// 		addOrUpdateIngredientScrollPane.setBorder(null);
// 		addOrUpdateIngredientScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
// 		addOrUpdateIngredientScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

// 		// - Tuỳ chỉnh Add Or Update Time Label
// 		addOrUpdateTimeLabel = CommonPL.getParagraphLabel("Cập nhật gần đây:", Color.GRAY,
// 				new Font("Arial", Font.ITALIC, 18));
// 		addOrUpdateTimeLabel.setBounds(260, 660, 148, 40);

// 		// - Tuỳ chỉnh Add Or Update Time Detail Label
// 		addOrUpdateTimeDetailLabel = CommonPL.getTitleLabel("yyyy-MM-dd HH:mm:ss", Color.GRAY,
// 				new Font("Arial", Font.ITALIC, 18), SwingConstants.RIGHT, SwingConstants.CENTER);
// 		addOrUpdateTimeDetailLabel.setBounds(413, 660, 307, 40);
// 		// -- Tạo Timer cập nhật thời gian
// 		Timer timer = new Timer(1000, e -> {
// 			LocalDateTime currentDateTime = LocalDateTime.now();
// 			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
// 			String formattedDateTime = currentDateTime.format(formatter);
// 			addOrUpdateTimeDetailLabel.setText(formattedDateTime);
// 		});
// 		timer.start();

// 		// - Tuỳ chỉnh Add Or Update Block Button
// 		addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button,
// 				button == "Thêm" ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
// 				CommonPL.getFontParagraphBold());
// 		addOrUpdateButton.setBounds(260, 700, 460, 40);
// 		SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());
// 		addOrUpdateButton.addActionListener(e -> {
// 			try {
// 				// - Lấy các giá trị hiện tại
// 				// + Mã sản phẩm
// 				String productId = !addOrUpdateIdTextField.getText().equals("Nhập Mã sản phẩm")
// 				? addOrUpdateIdTextField.getText().trim()
// 				: null;
// 				// + Tên sản phẩm
// 				String productName = !addOrUpdateNameTextField.getText().equals("Nhập Tên sản phẩm")
// 				? addOrUpdateNameTextField.getText().trim()
// 				: null;
// 				// + Giá sản phẩm
// 				String productPrice = !addOrUpdatePriceTextField.getText().equals("Nhập Giá bán")
// 				? addOrUpdatePriceTextField.getText().trim()
// 				: null;
// 				// + Hình ảnh sản phẩm
// 				String productImage = !addOrUpdateAvatarTextField.getText().equals("Nhập đường dẫn ảnh")
// 				? addOrUpdateAvatarTextField.getText().trim()
// 				: null;
// 				// + Nhóm sản phẩm		
// 				String productGroup = !String.valueOf(addOrUpdateGroupComboBox.getSelectedItem()).equals("Chọn Nhóm")
// 				? String.valueOf(addOrUpdateGroupComboBox.getSelectedItem())
// 				: null;
// 				// + Trạng thái sản phẩm
// 				String productStatus = !String.valueOf(addOrUpdateStatusComboBox.getSelectedItem()).equals("Chọn Trạng thái")
// 				? String.valueOf(addOrUpdateStatusComboBox.getSelectedItem())
// 				: null;
// 				// + Ngày cập nhật
// 				String dateUpdate = CommonPL.getCurrentDate();

// 				// + Kiểm tra bảng nguyên liệu
// 				int ingredient = checkCTSP(selectCheckbox, quantityFields, datas);
// 				String inform="";
// 				if(ingredient == 0) inform = "Sản phẩm phải có ít nhất một nguyên liệu";
// 				else if(ingredient == -1) inform = "Định lượng phải là số nguyên lớn hơn 0";
// 				else inform = "Thỏa"; 

// 				if(ingredient == 2)  productStatus = String.valueOf(false);

// 				if(inform.equals("Thỏa"))
// 				{
// 					// - Nếu là thêm
// 					if(!check)
// 					{
// 						inform = productBLL.insertProduct(productId, productName, productGroup, productPrice, productImage, productStatus, dateUpdate);
// 						if(inform.equals("Có thể thêm một sản phẩm"))
// 						{
// 							// - Hoàn thành việc thêm thì thông báo và cập nhật lại trên giao diện	
// 							if(ingredient == 1) {
// 								CommonPL.createSuccessDialog("Thông báo thành công", "Thêm thành công");
// 							}
// 							else {
// 								CommonPL.createSuccessDialog("Thông báo thành công", "Thêm thành công, mặc định trạng thái sản phẩm tạm dừng do không đủ định lượng");
// 							}
// 							updateCTSP(selectCheckbox, quantityFields, productId, datas);
// 							kq[0] = true;
// 							addOrUpdateDialog.dispose();
// 						}
// 						else {
// 							CommonPL.createErrorDialog("Thông báo lỗi", inform);
// 						}
// 					}
// 					// - Nếu là sửa 
// 					else 
// 					{
// 						inform = productBLL.updateProduct(productId, productName, productGroup, productPrice, productImage, productStatus, dateUpdate);
// 						if(inform.equals("Có thể thay đổi một sản phẩm"))
// 						{
// 							// - Hoàn thành việc thêm thì thông báo và cập nhật lại trên giao diện	
// 							if(ingredient == 1) {
// 								CommonPL.createSuccessDialog("Thông báo thành công", "Thay đổi thành công");
// 							}
// 							else {
// 								CommonPL.createSuccessDialog("Thông báo thành công", "Thay đổi thành công, mặc định trạng thái sản phẩm tạm dừng do không đủ định lượng");
// 							}
// 							updateCTSP(selectCheckbox, quantityFields, productId, datas);
// 							kq[0] = true;
// 							addOrUpdateDialog.dispose();
							
// 						}
// 						else {
// 							CommonPL.createErrorDialog("Thông báo lỗi", inform);
// 						}
// 					}
// 				} else {
// 					CommonPL.createErrorDialog("Thông báo lỗi", inform);
// 				}
// 			} catch (Exception ex) {
// 				JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
//         		ex.printStackTrace();
//     		}
// 		});

// 		// - Nếu đã chọn sửa thông tin sản phẩm
// 		if (check) 
// 		{
// 			// + Cập nhật lại hình ảnh
// 			String s = (object.get(4) != null) ? String.valueOf(object.get(4)) : "null";
// 			ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowProductImage() + s);
// 			Image scaledImage = image.getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH);
// 			ImageIcon scaledIcon = new ImageIcon(scaledImage);
// 			addOrUpdateAvatarImage.setIcon(scaledIcon);
// 			addOrUpdateAvatarTextField
// 					.setText(s);
// 			addOrUpdateAvatarTextField.setForeground(Color.BLACK);

// 			// + Cập nhật lại mã sản phẩm
// 			addOrUpdateIdTextField.setText(String.valueOf(object.get(0)));
// 			addOrUpdateIdTextField.setEnabled(false);
// 			addOrUpdateIdTextField.setCaretPosition(0);
// 			((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
// 			addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));

// 			// + Cập nhật lại tên sản phẩm
// 			addOrUpdateNameTextField.setText(String.valueOf(object.get(1)));
// 			addOrUpdateNameTextField.setCaretPosition(0);
// 			addOrUpdateNameTextField.setForeground(Color.BLACK);

// 			// + Cập nhật lại nhóm sản phẩm
// 			String group = String.valueOf(object.get(2));
// 			addOrUpdateGroupComboBox.setSelectedItem(group);
// 			((JTextField) addOrUpdateGroupComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
// 			addOrUpdateGroupComboBox.setForeground(Color.BLACK);
			
// 			// + Cập nhật lại giá sản phẩm
// 			addOrUpdatePriceTextField.setText(String.valueOf(object.get(3)));
// 			addOrUpdatePriceTextField.setCaretPosition(0);
// 			addOrUpdatePriceTextField.setForeground(Color.BLACK);

// 			// + Cập nhật lại trạng thái sản phẩm
// 			addOrUpdateStatusComboBox.setSelectedItem(String.valueOf(object.get(5)));
// 			addOrUpdateStatusComboBox.setEnabled(false);
// 			((JTextField) addOrUpdateStatusComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
// 			UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
// 			addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));

// 			// + Cập nhật lại chi tiết sản phẩm (nguyên liệu cần thiết)
// 			// Ở phía trên phần nguyên liệu
// 		}

// 		// - Tuỳ chỉnh Add Or Update Block Panel
// 		addOrUpdateBlockPanel = new JPanel();
// 		addOrUpdateBlockPanel.setLayout(null);
// 		addOrUpdateBlockPanel.setBounds(0, 0, 980, 790);
// 		addOrUpdateBlockPanel.setBackground(Color.WHITE);
// 		addOrUpdateBlockPanel.add(addOrUpdateAvatarLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateAvatarImage);
// 		addOrUpdateBlockPanel.add(addOrUpdateAvatarButton);
// 		addOrUpdateBlockPanel.add(addOrUpdateAvatarTextField);
// 		addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
// 		addOrUpdateBlockPanel.add(addOrUpdateGroupLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateGroupComboBox);
// 		addOrUpdateBlockPanel.add(addOrUpdateNameLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateNameTextField);
// 		addOrUpdateBlockPanel.add(addOrUpdatePriceLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdatePriceTextField);
// 		addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateStatusComboBox);
// 		addOrUpdateBlockPanel.add(addOrUpdateIngredientLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateLine1IngredientPanel);
// 		addOrUpdateBlockPanel.add(addOrUpdateLine2IngredientPanel);
// 		addOrUpdateBlockPanel.add(addOrUpdateIngredientIdLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateIngredientNameLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateIngredientUnitLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateIngredientInventoryLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateIngredientSelectLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateIngredientQuantityLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateButton);
// 		addOrUpdateBlockPanel.add(addOrUpdateTimeLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateTimeDetailLabel);
// 		addOrUpdateBlockPanel.add(addOrUpdateIngredientScrollPane);
// 		// <==================== ====================>

		

// 		addOrUpdateDialog.add(addOrUpdateBlockPanel);
// 		addOrUpdateDialog.setVisible(true);
// 		return kq[0];
// 	}

// 	public int checkCTSP(ArrayList<JCheckBox> selectCheckbox, ArrayList<JTextField> quantityFields, Object[][] data) {
// 		boolean hasSelectedIngredient = false;
// 		boolean hasQuantityBigger = false;
	
// 		// - Kiểm tra dữ liệu nhập vào
// 		for (int i = 0; i < data.length; i++) 
// 		{
// 			boolean isSelected = (selectCheckbox.get(i).isSelected() ? true :false);
// 			if (!isSelected) continue; 
// 			hasSelectedIngredient = true; // Đánh dấu có nguyên liệu được chọn
// 			String quantityText = quantityFields.get(i).getText().trim();
	
// 			// Kiểm tra nếu chưa nhập số lượng
// 			if (quantityText.isEmpty()) return -1;
	
// 			try {
// 				int quantity = Integer.parseInt(quantityText);
// 				if (quantity <= 0) return -1; // Nếu số lượng <= 0
// 				if (quantity > (Integer) data[i][3]) hasQuantityBigger = true; // Kiểm tra tồn kho
// 			} catch (NumberFormatException e) 
// 			{
// 				return -1; // Nếu nhập sai định dạng (chữ cái, ký tự đặc biệt)
// 			}
// 		}
	
// 		// Nếu không có nguyên liệu nào được chọn
// 		if (!hasSelectedIngredient) return 0;
	
// 		// Nếu có nguyên liệu vượt quá tồn kho, trả về 2
// 		return hasQuantityBigger ? 2 : 1;
// 	}	

// 	public void updateCTSP(ArrayList<JCheckBox> selectCheckbox, ArrayList<JTextField> quantityFields, String productId, Object[][] data)
// 	{
// 		for (int i = 0; i < data.length; i++) 
// 		{
// 			boolean isSelected = (selectCheckbox.get(i).isSelected() ? true :false); 
// 			String ingredientId = String.valueOf(data[i][0]);
// 			String quantity = quantityFields.get(i).getText().trim();
// 			if (isSelected) {
// 				productDetailBLL.updateProductDetail(productId, ingredientId, quantity);
				
// 			} else {
// 				productDetailBLL.deleteProductDetail(productId, ingredientId);
// 			}
// 		}
// 	}
// }

