package PL;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import PL.CommonPL.CustomTextField;

public class Admin_MenuManagerPL extends JPanel {
	// Các Font
	private Font fontParagraph = new Font("Arial", Font.PLAIN, 14);
	private Font fontButton = new Font("Arial", Font.BOLD, 14);
	private Font fontTitleInProductInfor = new Font("Arial", Font.BOLD, 16);
	private Font fontParagraphInProductInfor = new Font("Arial", Font.PLAIN, 14);
	private Font fontInIngredientPanel = new Font("Arial", Font.PLAIN, 15);
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Product Panel
	private JTextField findInputTextField;
	private JComboBox sortComboBox;
	private JComboBox groupComboBox;
	private JComboBox statusComboBox;
	private JButton filterButton;
	private JButton addButton;
	private JPanel listPanel;
	private JScrollPane listScrollPane;
	private JPanel productPanel;
	// - Các Component của Add Or Update Product Dialog
	private JLabel addOrUpdateAvatarImage;
	private JLabel addOrUpdateAvatarLabel;
	private JButton addOrUpdateAvatarButton;
	private JTextField addOrUpdateAvatarTextField;
	private JLabel addOrUpdateIdLabel;
	private JTextField addOrUpdateIdTextField;
	private JLabel addOrUpdateGroupLabel;
	private JComboBox<String> addOrUpdateGroupComboBox;
	private JLabel addOrUpdateNameLabel;
	private JTextField addOrUpdateNameTextField;
	private JLabel addOrUpdatePriceLabel;
	private JTextField addOrUpdatePriceTextField;
	private JLabel addOrUpdateStatusLabel;
	private JComboBox<String> addOrUpdateStatusComboBox;
	private JLabel addOrUpdateIngredientLabel;
	private JPanel addOrUpdateLine1IngredientPanel;
	private JPanel addOrUpdateLine2IngredientPanel;
	private JLabel addOrUpdateIngredientIdLabel;
	private JLabel addOrUpdateIngredientNameLabel;
	private JLabel addOrUpdateIngredientUnitLabel;
	private JLabel addOrUpdateIngredientInventoryLabel;
	private JLabel addOrUpdateIngredientSelectLabel;
	private JLabel addOrUpdateIngredientQuantityLabel;
	private JPanel addOrUpdateIngredientPanel;
	private JScrollPane addOrUpdateIngredientScrollPane;
	private JLabel addOrUpdateTimeLabel;
	private JLabel addOrUpdateTimeDetailLabel;
	private JButton addOrUpdateButton;
	private JPanel addOrUpdateBlockPanel;
	private JDialog addOrUpdateDialog;

	// + Số thứ tự sản phẩm được chọn
//	private int productSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] valueSelected = { null };

	public Admin_MenuManagerPL() {
		// <===== Cấu trúc của Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Thực đơn", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);
		// <==================== ====================>

		// <===== Cấu trúc của Product Panel =====>
		// - Tuỳ chỉnh Find Input Text Field
		findInputTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên sản phẩm", Color.LIGHT_GRAY, Color.BLACK,
				fontParagraph);
		findInputTextField.setBounds(15, 15, 180, 30);

		// - Tuỳ chỉnh Add Or Update Type ComboBox
		Vector<String> types = CommonPL.getVectorHasValues(
				new String[] { "Chọn Sắp xếp", "Tên tăng dần", "Tên giảm dần", "Giá tăng dần", "Giá giảm dần" });
		sortComboBox = CommonPL.CustomComboBox(types, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		sortComboBox.setBounds(210, 15, 180, 30);

		// - Tuỳ chỉnh Add Or Update Group ComboBox
		Vector<String> groups = CommonPL.getVectorHasValues(new String[] { "Chọn Nhóm", "Món khô", "Món nước",
				"Món ăn kèm", "Món tráng miệng", "Đồ uống", "Thuốc lá" });
		groupComboBox = CommonPL.CustomComboBox(groups, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		groupComboBox.setBounds(405, 15, 180, 30);

		// - Tuỳ chỉnh Add Or Update Status ComboBox
		Vector<String> status = CommonPL
				.getVectorHasValues(new String[] { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" });
		statusComboBox = CommonPL.CustomComboBox(status, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		statusComboBox.setBounds(600, 15, 180, 30);

		// - Tuỳ chỉnh Filter Button
		filterButton = CommonPL.getRoundedBorderButton(14, "Lọc", Color.decode("#007bff"), Color.WHITE, fontButton);
		filterButton.setBounds(795, 15, 75, 30);

		// - Tuỳ chỉnh Add Button
		addButton = CommonPL.getRoundedBorderButton(14, "Thêm", Color.decode("#699f4c"), Color.WHITE, fontButton);
		addButton.setBounds(1005, 15, 120, 30);
		addButton.addActionListener(e -> {
			showAddOrUpdateDialog("Thêm Sản phẩm", "Thêm", new Vector<Object>());
			valueSelected[0] = false;
		});

		// - Tuỳ chỉnh List Panel
		listPanel = new JPanel();
		listPanel.setLayout(null);
		listPanel.setBackground(Color.WHITE);

		// - Tuỳ chỉnh List Scroll Pane
		listScrollPane = new JScrollPane(listPanel);
		listScrollPane.setBounds(15, 60, 1125, 630);
		listScrollPane.setBorder(null);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// - Tuỳ chỉnh Product Panel;
		productPanel = new CommonPL.RoundedPanel(12);
		productPanel.setLayout(null);
		productPanel.setBackground(Color.WHITE);
		productPanel.setBounds(30, 115, 1140, 705);
		productPanel.add(findInputTextField);
		productPanel.add(sortComboBox);
		productPanel.add(groupComboBox);
		productPanel.add(statusComboBox);
		productPanel.add(filterButton);
		productPanel.add(addButton);
		productPanel.add(listScrollPane);
		// <==================== ====================>

		// Đĩnh nghĩa tính chất cho Dashboard Manager PL
		this.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setBackground(Color.decode("#E3F2FD"));
		this.setLayout(null);
		this.add(titleLabel);
		this.add(productPanel);

		// Thiết lập sự kiện cập nhật danh sách sản phẩm
		renderListPanel();
	}

	// Hàm cập nhật lại danh sách sản phẩm
	private void renderListPanel() {
		// - Truy vấn dữ liệu về tất cả sản phẩm hiện có
		Object[][] datas = { { "1", "Chai Cocacola", "Đồ uống", "10000", "Hoạt động", "cocacola-chai-image.jpg" },
				{ "2", "Chai Pepsi", "Đồ uống", "10000", "Tạm dừng", "pepsi-chai-image.jpg" },
				{ "3", "Mì trứng", "Món khô", "24000", "Hoạt động", "mi-trung-image.jpg" }, };

		// - Duyệt qua từng đối tượng
		int x = 0, y = 0;
		for (int i = 0; i < datas.length; i++) {
			// + Biến tạm giữ vị trí đối tượng được duyệt
			int j = i;

			// + Ảnh sản phẩm
			JLabel imageLabel = CommonPL.getImageLabel(210, 190,
					CommonPL.getMiddlePathToShowProductImage() + String.valueOf(datas[i][5]));
			imageLabel.setBounds(2, 2, 206, 186);
			// + Mã sản phẩm
			JLabel idLabel = CommonPL.getParagraphLabel(String.valueOf(datas[i][0]), Color.BLACK,
					fontParagraphInProductInfor);
			idLabel.setBounds(0, 0, 210, 20);
			// + Tên sản phẩm
			JLabel nameLabel = CommonPL.getTitleLabel(String.valueOf(datas[i][1]), Color.BLACK, fontTitleInProductInfor,
					SwingConstants.CENTER, SwingConstants.CENTER);
			nameLabel.setBounds(0, 200, 210, 20);
			// + Nhóm sản phẩm
			JLabel groupLabel = CommonPL.getTitleLabel(String.valueOf(datas[i][2]), Color.LIGHT_GRAY,
					fontParagraphInProductInfor, SwingConstants.CENTER, SwingConstants.CENTER);
			groupLabel.setBounds(0, 220, 210, 20);
			// + Giá sản phẩm
			JLabel priceLabel = CommonPL.getTitleLabel(
					CommonPL.moneyLongToMoneyFormat(new BigInteger(String.valueOf(datas[i][3]))) + " VNĐ", Color.BLACK,
					fontParagraphInProductInfor, SwingConstants.CENTER, SwingConstants.CENTER);
			priceLabel.setBounds(0, 240, 210, 20);
			// + Nút cập nhật sản phẩm
			JButton updateButton = CommonPL.getRoundedBorderButton(14, "Sửa", Color.decode("#bf873e"), Color.WHITE,
					new Font("Arial", Font.BOLD, 14));
			updateButton.setBounds(10, 270, 90, 30);
			updateButton.addActionListener(e -> {
				// + Truy vấn thông tin sản phẩm hiện tại
				Vector<Object> object = new Vector<Object>();
				object.add(datas[j][0]);
				object.add(datas[j][1]);
				object.add(datas[j][2]);
				object.add(datas[j][3]);
				object.add(datas[j][4]);
				object.add(datas[j][5]);

				// + Hiển thị modal để sửa thông tin sản phẩm
				showAddOrUpdateDialog("Sửa Sản phẩm", "Sửa", object);
				valueSelected[0] = false;
			});
			// + Nút khoá sản phẩm
			JButton lockButton = CommonPL.getRoundedBorderButton(14, "Khoá", Color.decode("#9f4d4d"), Color.WHITE,
					new Font("Arial", Font.BOLD, 14));
			lockButton.setBounds(110, 270, 90, 30);
			lockButton.addActionListener(e -> {

			});

			// + Khối chứa sản phẩm
			JPanel blockPanel = new JPanel();
			blockPanel.setLayout(null);
			blockPanel.setBounds(x, y, 210, 310);
			blockPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#DEDEDE"), 2));
			blockPanel.setBackground(Color.WHITE);
			blockPanel.add(idLabel);
			blockPanel.add(imageLabel);
			blockPanel.add(nameLabel);
			blockPanel.add(groupLabel);
			blockPanel.add(priceLabel);
			blockPanel.add(updateButton);
			blockPanel.add(lockButton);

			// + Thêm vào danh sách sản phẩm
			listPanel.add(blockPanel);

			// + Cập nhật lại vị trí của từng sản phẩm
			if (x == 900) {
				x = 0;
				y += 325;
			} else {
				x += 225;
			}
		}

		listPanel.setPreferredSize(new Dimension(1110, (int) ((Math.ceil(1.0 * datas.length / 5) * 325))));
	}

	// Hàm tạo dialog cho phép thêm hoặc cập nhật một sản phẩm
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
		// - Tuỳ chỉnh Add Or Update Avatar Label
		addOrUpdateAvatarLabel = CommonPL.getParagraphLabel("Hình ảnh", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAvatarLabel.setBounds(20, 10, 82, 40);

		// - Tuỳ chỉnh Add Or Update Avatar Image
		addOrUpdateAvatarImage = CommonPL.getImageLabel(200, 200,
				CommonPL.getMiddlePathToShowIcon() + "no-image-icon.png");
		addOrUpdateAvatarImage.setBounds(155, 20, 190, 190);
		addOrUpdateAvatarImage.setOpaque(true);

		// - Tuỳ chỉnh Add Or Update Avatar Button
		addOrUpdateAvatarButton = CommonPL.getButtonDefaultForm("Thay đổi", new Font("Arial", Font.BOLD, 14));
		addOrUpdateAvatarButton.setBounds(380, 196, 100, 28);
		addOrUpdateAvatarButton.addActionListener(e -> {
			String src = addOrUpdateAvatarTextField.getText();
			if (!src.equals(CommonPL.getMiddlePathToShowIcon() + "no-image-icon.png")) {
				ImageIcon image = new ImageIcon(src);
				Image scaledImage = image.getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH);
				ImageIcon scaledIcon = new ImageIcon(scaledImage);
				addOrUpdateAvatarImage.setIcon(scaledIcon);
			}
		});

		// - Tuỳ chỉnh Add Or Update Avatar TextField
		addOrUpdateAvatarTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập đường dẫn ảnh", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateAvatarTextField.setBounds(20, 230, 460, 40);

		// - Tuỳ chỉnh Add Or Update Id Label
		addOrUpdateIdLabel = CommonPL.getParagraphLabel("Mã sản phẩm", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdLabel.setBounds(500, 10, 220, 40);

		// - Tuỳ chỉnh Add Or Update Id TextField
		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã sản phẩm", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdTextField.setBounds(500, 50, 220, 40);

		// - Tuỳ chỉnh Add Or Update Group Label
		addOrUpdateGroupLabel = CommonPL.getParagraphLabel("Nhóm", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateGroupLabel.setBounds(740, 10, 220, 40);

		// - Tuỳ chỉnh Add Or Update Group ComboBox
		Vector<String> groups = CommonPL.getVectorHasValues(new String[] { "Chọn Nhóm", "Món khô", "Món nước",
				"Món ăn kèm", "Món tráng miệng", "Đồ uống", "Thuốc lá" });
		addOrUpdateGroupComboBox = CommonPL.CustomComboBox(groups, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateGroupComboBox.setBounds(740, 50, 220, 40);

		// - Tuỳ chỉnh Add Or Update Price Label
		addOrUpdatePriceLabel = CommonPL.getParagraphLabel("Giá bán", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdatePriceLabel.setBounds(500, 100, 220, 40);

		// - Tuỳ chỉnh Add Or Update Price TextField
		addOrUpdatePriceTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Giá bán", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdatePriceTextField.setBounds(500, 140, 220, 40);

		// - Tuỳ chỉnh Add Or Update Status Label
		addOrUpdateStatusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateStatusLabel.setBounds(740, 100, 220, 40);

		// - Tuỳ chỉnh Add Or Update Status ComboBox
		Vector<String> status = CommonPL
				.getVectorHasValues(new String[] { "Chọn Trạng thái", "Hoạt động", "Tạm dừng" });
		addOrUpdateStatusComboBox = CommonPL.CustomComboBox(status, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateStatusComboBox.setBounds(740, 140, 220, 40);

		// - Tuỳ chỉnh Add Or Update Name Label
		addOrUpdateNameLabel = CommonPL.getParagraphLabel("Tên sản phẩm", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateNameLabel.setBounds(500, 190, 460, 40);

		// - Tuỳ chỉnh Add Or Update Name TextField
		addOrUpdateNameTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên sản phẩm", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateNameTextField.setBounds(500, 230, 460, 40);

		// - Tuỳ chỉnh Add Or Update Ingredient Label
		addOrUpdateIngredientLabel = CommonPL.getTitleLabel("Nguyên liệu cần thiết", Color.BLACK,
				CommonPL.getFontParagraphPlain(), SwingConstants.CENTER, SwingConstants.CENTER);
		addOrUpdateIngredientLabel.setBounds(0, 300, 980, 40);

		// - Tuỳ chỉnh Add Or Update Line 1 Ingredient Panel
		addOrUpdateLine1IngredientPanel = new CommonPL.RoundedPanel(8);
		addOrUpdateLine1IngredientPanel.setBounds(20, 318, 360, 4);
		addOrUpdateLine1IngredientPanel.setBackground(Color.BLACK);

		// - Tuỳ chỉnh Add Or Update Line 2 Ingredient Panel
		addOrUpdateLine2IngredientPanel = new CommonPL.RoundedPanel(8);
		addOrUpdateLine2IngredientPanel.setBounds(600, 318, 360, 4);
		addOrUpdateLine2IngredientPanel.setBackground(Color.BLACK);

		// - Tuỳ chỉnh Add Or Update Ingredient Id Label
		addOrUpdateIngredientIdLabel = CommonPL.getTitleLabel("Mã nguyên liệu", Color.BLACK, fontInIngredientPanel,
				SwingConstants.CENTER, SwingConstants.CENTER);
		addOrUpdateIngredientIdLabel.setBounds(20, 340, 140, 40);

		// - Tuỳ chỉnh Add Or Update Ingredient Name Label
		addOrUpdateIngredientNameLabel = CommonPL.getTitleLabel("Tên nguyên liệu", Color.BLACK, fontInIngredientPanel,
				SwingConstants.CENTER, SwingConstants.CENTER);
		addOrUpdateIngredientNameLabel.setBounds(160, 340, 360, 40);

		// - Tuỳ chỉnh Add Or Update Ingredient Unit Label
		addOrUpdateIngredientUnitLabel = CommonPL.getTitleLabel("Đơn vị", Color.BLACK, fontInIngredientPanel,
				SwingConstants.CENTER, SwingConstants.CENTER);
		addOrUpdateIngredientUnitLabel.setBounds(520, 340, 100, 40);

		// - Tuỳ chỉnh Add Or Update Ingredient Inventory Label
		addOrUpdateIngredientInventoryLabel = CommonPL.getTitleLabel("Tồn kho", Color.BLACK, fontInIngredientPanel,
				SwingConstants.CENTER, SwingConstants.CENTER);
		addOrUpdateIngredientInventoryLabel.setBounds(620, 340, 100, 40);

		// - Tuỳ chỉnh Add Or Update Ingredient Select Label
		addOrUpdateIngredientSelectLabel = CommonPL.getTitleLabel("Chọn", Color.BLACK, fontInIngredientPanel,
				SwingConstants.CENTER, SwingConstants.CENTER);
		addOrUpdateIngredientSelectLabel.setBounds(720, 340, 60, 40);

		// - Tuỳ chỉnh Add Or Update Ingredient Quantity Label
		addOrUpdateIngredientQuantityLabel = CommonPL.getTitleLabel("Định lượng", Color.BLACK, fontInIngredientPanel,
				SwingConstants.CENTER, SwingConstants.CENTER);
		addOrUpdateIngredientQuantityLabel.setBounds(780, 340, 180, 40);

		// - Truy vấn bảng dữ liệu nguyên liệu và chi tiết sản phẩm
		Object[][] datas = { { "NL0001", "Mì gói Hảo Hảo", "Gói", "2", true, "1" },
				{ "NL0002", "Trứng gà", "Quả", "10", true, "1" }, { "NL0003", "Rau xanh", "Bó", "10", true, "0" },
				{ "NL0004", "Thuốc lá", "Hộp", "10", false, "0" }, { "NL0005", "Táo", "Quả", "10", false, "1" } };
		// - Tuỳ chỉnh Add Or Update Ingredient Panel
		addOrUpdateIngredientPanel = new JPanel();
		addOrUpdateIngredientPanel.setLayout(null);
		addOrUpdateIngredientPanel.setPreferredSize(new Dimension(940, datas.length * 40));
		for (int i = 0; i < datas.length; i++) {
			// + Mã sản phẩm
			JLabel id = CommonPL.getTitleLabel(String.valueOf(datas[i][0]), Color.LIGHT_GRAY, fontInIngredientPanel,
					SwingConstants.CENTER, SwingConstants.CENTER);
			id.setBounds(0, 0, 140, 40);
			// + Tên sản phẩm
			JLabel name = CommonPL.getParagraphLabel(String.valueOf(datas[i][1]), Color.LIGHT_GRAY,
					fontInIngredientPanel);
			name.setBounds(140, 0, 360, 40);
			// + Đơn vị
			JLabel unit = CommonPL.getTitleLabel(String.valueOf(datas[i][2]), Color.LIGHT_GRAY, fontInIngredientPanel,
					SwingConstants.CENTER, SwingConstants.CENTER);
			unit.setBounds(500, 0, 100, 40);
			// + Tồn kho
			JLabel inventory = CommonPL.getTitleLabel(String.valueOf(datas[i][3]), Color.LIGHT_GRAY,
					fontInIngredientPanel, SwingConstants.CENTER, SwingConstants.CENTER);
			inventory.setBounds(600, 0, 100, 40);
			// + Chọn
			JCheckBox select = new JCheckBox();
			select.setSelected((boolean) datas[i][4]);
			select.setBounds(715, 0, 30, 40);
			select.setAlignmentX(Component.CENTER_ALIGNMENT);
			select.setFont(fontInIngredientPanel);
			// + Định lượng
			JTextField quantity = new JTextField();
			quantity.setEnabled(false);
			quantity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
			quantity.setBounds(760, 2, 180, 36);
			quantity.setBackground(Color.WHITE);
			quantity.setText(String.valueOf(datas[i][5]));
			quantity.setFont(fontInIngredientPanel);
			quantity.setForeground(Color.LIGHT_GRAY);
			// + Đường kẻ ngang
			JPanel linePanel = new CommonPL.RoundedPanel(8);
			linePanel.setBounds(0, 0, 940, 2);
			// + Dòng chứa các dữ liệu
			JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
			rowPanel.setLayout(null);
			rowPanel.setBounds(0, i * 40, 940, 40);
			rowPanel.setBackground(Color.WHITE);
			if (i >= 1)
				rowPanel.add(linePanel);
			rowPanel.add(id);
			rowPanel.add(name);
			rowPanel.add(unit);
			rowPanel.add(inventory);
			rowPanel.add(select);
			rowPanel.add(quantity);

			// + Nếu mục đã được chọn từ trước
			if ((boolean) datas[i][4]) {
				id.setForeground(Color.BLACK);
				name.setForeground(Color.BLACK);
				unit.setForeground(Color.BLACK);
				inventory.setForeground(Color.BLACK);
				quantity.setEnabled(true);
				quantity.setForeground(Color.BLACK);
				quantity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
			}

			// + Gán sự kiện thay đổi khi chọn vào mục nguyên liệu
			select.addActionListener(e -> {
				if (select.isSelected() == true) {
					id.setForeground(Color.BLACK);
					name.setForeground(Color.BLACK);
					unit.setForeground(Color.BLACK);
					inventory.setForeground(Color.BLACK);
					quantity.setEnabled(true);
					quantity.setForeground(Color.BLACK);
					quantity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
				} else {
					id.setForeground(Color.LIGHT_GRAY);
					name.setForeground(Color.LIGHT_GRAY);
					unit.setForeground(Color.LIGHT_GRAY);
					inventory.setForeground(Color.LIGHT_GRAY);
					quantity.setEnabled(false);
					quantity.setText("0");
					quantity.setDisabledTextColor(Color.LIGHT_GRAY);
					quantity.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
				}
			});

			// + Thêm vào addOrUpdateIngredientPanel
			addOrUpdateIngredientPanel.setBackground(Color.WHITE);
			addOrUpdateIngredientPanel.add(rowPanel);
		}

		// - Tuỳ chỉnh Add Or Update Ingredient Scroll Pane
		addOrUpdateIngredientScrollPane = new JScrollPane(addOrUpdateIngredientPanel);
		addOrUpdateIngredientScrollPane.setBounds(20, 390, 960, 250);
		addOrUpdateIngredientScrollPane.setBorder(null);
		addOrUpdateIngredientScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		addOrUpdateIngredientScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// - Tuỳ chỉnh Add Or Update Time Label
		addOrUpdateTimeLabel = CommonPL.getParagraphLabel("Cập nhật gần đây:", Color.GRAY,
				new Font("Arial", Font.ITALIC, 18));
		addOrUpdateTimeLabel.setBounds(260, 660, 148, 40);

		// - Tuỳ chỉnh Add Or Update Time Detail Label
		addOrUpdateTimeDetailLabel = CommonPL.getTitleLabel("yyyy-MM-dd HH:mm:ss", Color.GRAY,
				new Font("Arial", Font.ITALIC, 18), SwingConstants.RIGHT, SwingConstants.CENTER);
		addOrUpdateTimeDetailLabel.setBounds(413, 660, 307, 40);
		// -- Tạo Timer cập nhật thời gian
		Timer timer = new Timer(1000, e -> {
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = currentDateTime.format(formatter);
			addOrUpdateTimeDetailLabel.setText(formattedDateTime);
		});
		timer.start();

		// - Tuỳ chỉnh Add Or Update Block Button
		addOrUpdateButton = CommonPL.getRoundedBorderButton(20, button,
				button == "Thêm" ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		addOrUpdateButton.setBounds(260, 700, 460, 40);
		SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

		// - Nếu đã chọn sửa thông tin sản phẩm
		if (object.size() != 0) {
			// + Cập nhật lại hình ảnh
			ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowProductImage() + String.valueOf(object.get(5)));
			Image scaledImage = image.getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH);
			ImageIcon scaledIcon = new ImageIcon(scaledImage);
			addOrUpdateAvatarImage.setIcon(scaledIcon);
			addOrUpdateAvatarTextField
					.setText(CommonPL.getMiddlePathToShowProductImage() + String.valueOf(object.get(5)));
			addOrUpdateAvatarTextField.setForeground(Color.BLACK);

			// + Cập nhật lại mã sản phẩm
			addOrUpdateIdTextField.setText(String.valueOf(object.get(0)));
			addOrUpdateIdTextField.setEnabled(false);
			addOrUpdateIdTextField.setCaretPosition(0);
			((CustomTextField) addOrUpdateIdTextField).setBorderColor(Color.decode("#dedede"));
			addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));

			// + Cập nhật lại tên sản phẩm
			addOrUpdateNameTextField.setText(String.valueOf(object.get(1)));
			addOrUpdateNameTextField.setCaretPosition(0);
			addOrUpdateNameTextField.setForeground(Color.BLACK);

			// + Cập nhật lại nhóm sản phẩm
			addOrUpdateGroupComboBox.setSelectedItem(String.valueOf(object.get(2)));
			((JTextField) addOrUpdateGroupComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
			addOrUpdateGroupComboBox.setForeground(Color.BLACK);

			// + Cập nhật lại giá sản phẩm
			addOrUpdatePriceTextField.setText(String.valueOf(object.get(3)));
			addOrUpdatePriceTextField.setCaretPosition(0);
			addOrUpdatePriceTextField.setForeground(Color.BLACK);

			// + Cập nhật lại trạng thái sản phẩm
			addOrUpdateStatusComboBox.setSelectedItem(String.valueOf(object.get(4)));
			addOrUpdateStatusComboBox.setEnabled(false);
			((JTextField) addOrUpdateStatusComboBox.getEditor().getEditorComponent()).setCaretPosition(0);
			UIManager.put("ComboBox.disabledBackground", Color.decode("#dedede"));
			addOrUpdateStatusComboBox.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 1));

			// + Cập nhật lại chi tiết sản phẩm (nguyên liệu cần thiết)
		}

		// - Tuỳ chỉnh Add Or Update Block Panel
		addOrUpdateBlockPanel = new JPanel();
		addOrUpdateBlockPanel.setLayout(null);
		addOrUpdateBlockPanel.setBounds(0, 0, 980, 790);
		addOrUpdateBlockPanel.setBackground(Color.WHITE);
		addOrUpdateBlockPanel.add(addOrUpdateAvatarLabel);
		addOrUpdateBlockPanel.add(addOrUpdateAvatarImage);
		addOrUpdateBlockPanel.add(addOrUpdateAvatarButton);
		addOrUpdateBlockPanel.add(addOrUpdateAvatarTextField);
		addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
		addOrUpdateBlockPanel.add(addOrUpdateGroupLabel);
		addOrUpdateBlockPanel.add(addOrUpdateGroupComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateNameLabel);
		addOrUpdateBlockPanel.add(addOrUpdateNameTextField);
		addOrUpdateBlockPanel.add(addOrUpdatePriceLabel);
		addOrUpdateBlockPanel.add(addOrUpdatePriceTextField);
		addOrUpdateBlockPanel.add(addOrUpdateStatusLabel);
		addOrUpdateBlockPanel.add(addOrUpdateStatusComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateIngredientLabel);
		addOrUpdateBlockPanel.add(addOrUpdateLine1IngredientPanel);
		addOrUpdateBlockPanel.add(addOrUpdateLine2IngredientPanel);
		addOrUpdateBlockPanel.add(addOrUpdateIngredientIdLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIngredientNameLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIngredientUnitLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIngredientInventoryLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIngredientSelectLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIngredientQuantityLabel);
		addOrUpdateBlockPanel.add(addOrUpdateButton);
		addOrUpdateBlockPanel.add(addOrUpdateTimeLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTimeDetailLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIngredientScrollPane);
		// <==================== ====================>

		// Định nghĩa tính chất cho Add Or Update Dialog
		addOrUpdateDialog = new JDialog();
		addOrUpdateDialog.setTitle(title);
		addOrUpdateDialog.setLayout(null);
		addOrUpdateDialog.setSize(980, 790);
		addOrUpdateDialog.setResizable(false);
		addOrUpdateDialog.setLocationRelativeTo(null);
		addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addOrUpdateDialog.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowDeactivated(WindowEvent e) {
//				if (!addOrUpdateDialog.isVisible()) {
////					addOrUpdateDialog.setVisible(true);
//					addOrUpdateDialog.dispose(); // Đóng Dialog khi mất focus (nhấn ngoài)
//				}
//			}
		});
		addOrUpdateDialog.add(addOrUpdateBlockPanel);
		addOrUpdateDialog.setModal(true);
		addOrUpdateDialog.setVisible(true);
	}
}
