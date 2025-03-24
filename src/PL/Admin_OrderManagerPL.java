package PL;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import BLL.CommonBLL;
import BLL.OrderBLL;
import BLL.OrderDetailBLL;
import BLL.ProductBLL;
import BLL.ProductTypeBLL;
import BLL.RoomBLL;
import DTO.OrderDTO;
import DTO.ProductDTO;
import DTO.RoomDTO;

public class Admin_OrderManagerPL extends JPanel {
	// Các đối tượng từ tầng Bussiness Logical Layer
	private ProductBLL productBLL;
	private ProductTypeBLL productTypeBLL;
	private OrderBLL orderBLL;
	private OrderDetailBLL orderDetailBLL;
	private RoomBLL roomBLL;
	private Object[][] datas;
	private String[] roomNameList;
	private String[] roomIdList;
	ArrayList<ProductDTO> productList;
	ArrayList<OrderDTO> orderList;

	// Các Font
	private Font fontParagraph = new Font("Arial", Font.PLAIN, 14);
	private Font fontButton = new Font("Arial", Font.BOLD, 14);
	private Font fontTitleInBill = new Font("Arial", Font.BOLD, 24);
	private Font fontParagraphInBill = new Font("Arial", Font.ITALIC, 18);
	private Font fontParagraphPlainInBill = new Font("Arial", Font.PLAIN, 14);
	private Font fontParagraphBoldInBill = new Font("Arial", Font.BOLD, 16);
	private Font fontParagraphProductInBill = new Font("Arial", Font.BOLD, 14);
	private Font fontTitleInProductInfor = new Font("Arial", Font.BOLD, 16);
	private Font fontParagraphInProductInfor = new Font("Arial", Font.PLAIN, 14);
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Product Panel
	private JTextField findInputTextField;
	private JComboBox sortComboBox;
	private JComboBox groupComboBox;
	private JButton filterButton;
	private JPanel listPanel;
	private JScrollPane listScrollPane;
	private JPanel productPanel;
	// - Các Component của Bill Panel
	private JLabel shoppingCartLabel;
	private JLabel orderDateLabel;
	private JPanel blockPanel;
	private JPanel orderListPanel;
	private JScrollPane orderListScrollPane;
	private JPanel linePanel;
	private JLabel summaryCostLabel;
	private JLabel summaryCostDetailLabel;
	private JComboBox<String> roomNamesComboBox;
	private JButton orderButton;
	private JButton resetButton;
	private JPanel billPanel;
	// - Các Component của Quantity Input Dialog
	private JLabel quantityInputLabel;
	private JTextField quantityInputTextField;
	private JButton quantityInputButton;
	private JPanel quantityInputBlockPanel;
	private JDialog quantityInputDialog;
	// - Giỏ hàng chứa các sản phẩm hiện tại ở đơn
	private Vector<Object[]> shoppingCart = new Vector<>();
	// - Các thông tin cần thiết
	private final String[] sortsString = { "Chọn Sắp xếp", "Tên tăng dần", "Tên giảm dần", "Giá tăng dần", "Giá giảm dần" };
	private final String[] sortsSQL = {"", "tenSanPham ASC", "tenSanPham DESC", "giaBan ASC", "giaBan DESC"};
	private final String[] typeSQL = { "", "maLoaiSanPham = 'KHO'", "maLoaiSanPham = 'NUOC'",
			"maLoaiSanPham = 'KHAC'", "maLoaiSanPham = 'TRANGMIENG'",  "maLoaiSanPham = 'DOUONG'", "maLoaiSanPham = 'CHATCOHAI'"};
	private final String[] typeStringForFilter = { "Chọn Nhóm", "Món khô", "Món nước", "Khác", "Món tráng miệng", "Đồ uống", "Chất có hại"};
	BigInteger totalPrice;

	public Admin_OrderManagerPL() {
		// <===== Các đối tượng từ tầng Bussiness Logical Layer =====>
		productBLL = new ProductBLL();
		productTypeBLL = new ProductTypeBLL();
		orderBLL = new OrderBLL();
		orderDetailBLL = new OrderDetailBLL();
		roomBLL = new RoomBLL();
		orderList = orderBLL.getAllOrderByCondition(null,"trangThai = 0",null);
		if (orderList == null || orderList.isEmpty()) 
		{
			roomNameList = new String[] { "Chọn Phòng" };
			roomIdList = new String[] {};
		} else
		{
			roomNameList = new String[orderList.size() + 1];
			roomIdList = new String[orderList.size()];
			roomNameList[0]="Chọn Phòng";
			for(int i=0;i<orderList.size();i++)
			{
				roomIdList[i] = orderList.get(i).getRoomId();
				RoomDTO room = roomBLL.getOneRoomById(orderList.get(i).getRoomId());
				roomNameList[i+1]=room.getName();
			}
		}
		// <==================== ====================>

		// <===== Cấu trúc của Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Đặt món", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 70);
		// <==================== ====================>

		// <===== Cấu trúc của Product Panel =====>
		// - Tuỳ chỉnh Find Input Text Field
		findInputTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên sản phẩm", Color.LIGHT_GRAY, Color.BLACK,
				fontParagraph);
		findInputTextField.setBounds(15, 15, 180, 30);

		// - Tuỳ chỉnh Sort ComboBox
		Vector<String> types = CommonPL.getVectorHasValues(sortsString);
		sortComboBox = CommonPL.CustomComboBox(types, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		sortComboBox.setBounds(210, 15, 180, 30);

		// - Tuỳ chỉnh Add Or Update Group ComboBox
		Vector<String> groups = CommonPL.getVectorHasValues(typeStringForFilter);
		groupComboBox = CommonPL.CustomComboBox(groups, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		groupComboBox.setBounds(405, 15, 180, 30);

		// - Tuỳ chỉnh Filter Button
		filterButton = CommonPL.getRoundedBorderButton(14, "Lọc", Color.decode("#007bff"), Color.WHITE, fontButton);
		filterButton.setBounds(600, 15, 75, 30);
		filterButton.addActionListener(e -> {
			filterProducts();
		});

		// - Tuỳ chỉnh List Panel
		listPanel = new JPanel();
		listPanel.setLayout(null);
		listPanel.setBackground(Color.WHITE);

		// - Tuỳ chỉnh List Scroll Pane
		listScrollPane = new JScrollPane(listPanel);
		listScrollPane.setBounds(15, 60, 675, 630);
		listScrollPane.setBorder(null);
		listScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// - Tuỳ chỉnh Product Panel;
		productPanel = new CommonPL.RoundedPanel(12);
		productPanel.setLayout(null);
		productPanel.setBackground(Color.WHITE);
		productPanel.setBounds(30, 115, 690, 705);
		productPanel.add(findInputTextField);
		productPanel.add(sortComboBox);
		productPanel.add(groupComboBox);
		productPanel.add(filterButton);
		productPanel.add(listScrollPane);
		// <==================== ====================>

		// <===== Cấu trúc của Bill Panel =====>
		// - Tuỳ chỉnh New Order Label
		shoppingCartLabel = CommonPL.getTitleLabel("Giỏ hàng", Color.WHITE, fontTitleInBill, SwingConstants.LEFT,
				SwingConstants.CENTER);
		shoppingCartLabel.setBounds(20, 10, 180, 40);

		// - Tuỳ chỉnh Order Date Label
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, yyyy-MM-dd", new Locale("vi", "VN"));
		String formattedDate = today.format(formatter);
		orderDateLabel = CommonPL.getTitleLabel(String.valueOf(formattedDate), Color.WHITE, fontParagraphInBill,
				SwingConstants.RIGHT, SwingConstants.CENTER);
		orderDateLabel.setBounds(200, 10, 185, 40);

		// - Tuỳ chỉnh Block Panel
		blockPanel = new JPanel();
		blockPanel.setLayout(null);
		blockPanel.setBackground(Color.decode("#1976D2"));
		blockPanel.setBounds(15, 15, 405, 60);
		blockPanel.add(shoppingCartLabel);
		blockPanel.add(orderDateLabel);

		// - Tuỳ chỉnh Order List Panel
		orderListPanel = new JPanel();
		orderListPanel.setLayout(null);
		orderListPanel.setBackground(Color.WHITE);

		// - Tuỳ chỉnh Order List Scroll Pane
		orderListScrollPane = new JScrollPane(orderListPanel);
		orderListScrollPane.setBounds(15, 75, 420, 440);
		orderListScrollPane.setBorder(null);
		orderListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		orderListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// - Tuỳ chỉnh Line Panel
		linePanel = new CommonPL.RoundedPanel(8);
		linePanel.setBackground(Color.decode("#1976D2"));
		linePanel.setBounds(15, 542, 405, 3);

		// - Tuỳ chỉnh Summary Cost Label
		summaryCostLabel = CommonPL.getParagraphLabel("Tổng cộng:", Color.GRAY, fontParagraphPlainInBill);
		summaryCostLabel.setBounds(15, 560, 100, 30);

		// - Tuỳ chỉnh Summary Cost Detail Label
		summaryCostDetailLabel = CommonPL.getTitleLabel(
				CommonPL.moneyLongToMoneyFormat(new BigInteger(String.valueOf("0"))) + " VNĐ", Color.decode("#1976D2"),
				fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		summaryCostDetailLabel.setBounds(120, 560, 300, 30);

		// - Tuỳ chỉnh Pay Methods ComboBox
		Vector<String> roomNames = CommonPL.getVectorHasValues(roomNameList);
		roomNamesComboBox = CommonPL.CustomComboBox(roomNames, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK, Color.WHITE,
				Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		roomNamesComboBox.setBounds(15, 620, 405, 30);

		// - Tuỳ chỉnh Order Button
		orderButton = CommonPL.getRoundedBorderButton(14, "Tạo đơn", Color.decode("#1976D2"), Color.WHITE, fontButton);
		orderButton.setBounds(15, 660, 195, 30);
		orderButton.addActionListener(e -> {
			if(roomNamesComboBox.getSelectedIndex() == 0) CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn số phòng!");
			else 
			{
				String roomId = roomIdList[roomNamesComboBox.getSelectedIndex()-1];
				String s = String.format("maPhong = '%s' AND trangThai = 0",roomId);
				orderList = orderBLL.getAllOrderByCondition(null,s,null);
				
				String orderId = String.valueOf(orderList.get(0).getId());
				String date1 = String.valueOf(orderList.get(0).getDateOrder());
				String room = String.valueOf(orderList.get(0).getRoomId());
				String employeeid = String.valueOf(orderList.get(0).getEmployeeId());
				String customer = String.valueOf(orderList.get(0).getCustomerId());
				String discount = String.valueOf(orderList.get(0).getDiscountId());
				String t = String.valueOf(orderList.get(0).getTime());
				String gia = String.valueOf(orderList.get(0).getCost() + totalPrice.longValue());
				String trangThai = String.valueOf(orderList.get(0).getStatus());
				String date2 = String.valueOf(orderList.get(0).getDateUpdate());

				// - Cập nhật lại tổng tiền trong hóa đơn
				String inform = orderBLL.updateOrder(orderId, date1, room, employeeid, customer, discount, t, gia, trangThai, date2);
				if(inform != "Có thể thay đổi một hóa đơn") CommonPL.createErrorDialog("Thông báo lỗi", inform);
				else
				{
					// - Cập nhật CTHD cho hóa đơn
					boolean c = true;
					for(int i=0;i<shoppingCart.size();i++)
					{
						inform = orderDetailBLL.updateOrderDetail(orderId, String.valueOf(shoppingCart.get(i)[0]), String.valueOf(shoppingCart.get(i)[2]));
						if(inform != "Có thể cập nhật một CTHD") { CommonPL.createErrorDialog("Thông báo lỗi", inform); c = false;}
					}
					if(c) CommonPL.createSuccessDialog("Thông báo thành công", "Đặt món thành công!");
					resetProduct();
				}
			}
		});

		// - Tuỳ chỉnh Reset Button
		resetButton = CommonPL.getRoundedBorderButton(14, "Đặt lại", Color.decode("#1976D2"), Color.WHITE, fontButton);
		resetButton.setBounds(225, 660, 195, 30);
		resetButton.addActionListener(e -> 
		{
			resetProduct();
		});

		// - Tuỳ chỉnh Bill Panel;
		billPanel = new CommonPL.RoundedPanel(12);
		billPanel.setLayout(null);
		billPanel.setBackground(Color.WHITE);
		billPanel.setBounds(735, 115, 435, 705);
		billPanel.add(blockPanel);
		billPanel.add(orderListScrollPane);
		billPanel.add(linePanel);
		billPanel.add(summaryCostLabel);
		billPanel.add(summaryCostDetailLabel);
		billPanel.add(roomNamesComboBox);
		billPanel.add(orderButton);
		billPanel.add(resetButton);
		// <==================== ====================>

		// Đĩnh nghĩa tính chất cho Dashboard Manager PL
		this.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setBackground(Color.decode("#E3F2FD"));
		this.setLayout(null);
		this.add(titleLabel);
		this.add(productPanel);
		this.add(billPanel);

		// Thiết lập sự kiện cập nhật danh sách sản phẩm
		renderListPanel(null,null,null);

		// Thiết lập sự kiện cập nhật danh sách sản phẩm ở giỏ hàng
		renderOrderListPanel();

	}

	// - Hàm lọc sản phẩm
	private void filterProducts() 
	{
    	// Giá trị ô tìm kiếm
		String findValue = !findInputTextField.getText().equals("Nhập Tên sản phẩm") ? findInputTextField.getText()
				: null;
    	// Giá trị ô sắp xếp
		String sortValue = sortsSQL[sortComboBox.getSelectedIndex()];
		// Giá trị ô loại sản phẩm
    	String typeValue = typeSQL[groupComboBox.getSelectedIndex()];
    	// Gán lệnh SQL tương ứng để truy vấn rồi cập nhật bảng
		String condition = (findValue != null ? String.format(
			"(maSanPham LIKE '%%%s%%' OR tenSanPham LIKE '%%%s%%')", findValue, findValue) : "")
			+ (typeValue != "" ? (findValue != null ? (" AND " + typeValue) : typeValue) : "");
		
		if (condition.length() == 0)
			condition = null;
		String order = (sortValue =="") ? null : sortValue;
    	// Cập nhật dữ liệu hiển thị
    	renderListPanel(null,condition,order);
	}

	// Hàm reset giao diện
	private void resetProduct()
	{
		// + Đặt lại text của findTextField về mặc định
		findInputTextField.setText("Nhập Tên sản phẩm");
		// + Đặt lại các comboBox về giá trị mặc định
		sortComboBox.setSelectedIndex(0);
		groupComboBox.setSelectedIndex(0);
		roomNamesComboBox.setSelectedIndex(0);
		// + Xoá tất cả sản phẩm hiện có trong Giỏ hàng
		shoppingCart.clear();

		// + Cập nhật lại list sản phẩm
		renderListPanel(null,null,null);
		// + Cập nhật lại giỏ hàng trên giao diện
		renderOrderListPanel();
	}

	// Hàm cập nhật sản phẩm trong Giỏ hàng
	private void updateShoppingCart(Object[] productSelected, boolean isAdd) {
		// - Xử lý việc thêm hoặc xoá sản phẩm
		if (isAdd) {
			// + Nếu sản phẩm tồn tại trong giỏ hàng
			boolean isExists = false;
			for (int i = 0; i < shoppingCart.size(); i++) {
				if (shoppingCart.get(i)[0].equals(productSelected[0])) {
					// + Cập nhật lại số lượng
					shoppingCart.get(i)[2] = String.valueOf(Integer.parseInt((String) shoppingCart.get(i)[2])
							+ Integer.parseInt((String) productSelected[2]));

					// + Sản phẩm có tồn tại
					isExists = true;
					break;
				}
			}

			// + Nếu sản phẩm chưa tồn tại trong giỏ hàng
			if (!isExists) {
				shoppingCart.add(productSelected);
			}
		} else {
			int indexRemoved = -1;
			for (int i = 0; i < shoppingCart.size(); i++) {
				if (shoppingCart.get(i)[0].equals(productSelected[0])) {
					indexRemoved = i;
					break;
				}
			}
			shoppingCart.remove(indexRemoved);
		}

		// - Cập nhật lại giỏ hàng trên Order List Panel
		renderOrderListPanel();
	}

	// Hàm hiện modal cho phép nhật số lượng sản phẩm
	private void showQuantityInputDialog(Vector<Object> productSelected) {
		// <===== Các Component của Quantity Input Block Panel =====>
		// - Tuỳ chỉnh Quantity Input Label
		quantityInputLabel = CommonPL.getParagraphLabel("Số lượng sản phẩm cần đặt ?", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		quantityInputLabel.setBounds(20, 10, 460, 40);

		// - Tuỳ chỉnh Quantity Input Text Field
		quantityInputTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Số lượng", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		quantityInputTextField.setBounds(20, 50, 460, 40);
		quantityInputTextField.setText("1");
		quantityInputTextField.setForeground(Color.BLACK);

		// - Tuỳ chỉnh Quantity Input Button
		quantityInputButton = CommonPL.getButtonDefaultForm("Đồng ý", CommonPL.getFontParagraphBold());
		quantityInputButton.setBounds(20, 130, 460, 40);
		quantityInputButton.addActionListener(e -> {
			String quantity = quantityInputTextField.getText();
			if(!CommonBLL.isValidStringType04(quantity)) 
			{
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng nhập số lượng là một số tự nhiên !!!");
			}
			else 
			{
				Object[] product = new Object[] { productSelected.get(0), productSelected.get(1),
					quantityInputTextField.getText(), productSelected.get(2) };
				updateShoppingCart(product, true);
				quantityInputDialog.dispose();
			}
		});
		SwingUtilities.invokeLater(() -> quantityInputButton.requestFocusInWindow());

		// - Tuỳ chỉnh Quantity Input Block Panel
		quantityInputBlockPanel = new JPanel();
		quantityInputBlockPanel.setLayout(null);
		quantityInputBlockPanel.setBounds(0, 0, 500, 220);
		quantityInputBlockPanel.setBackground(Color.WHITE);
		quantityInputBlockPanel.add(quantityInputLabel);
		quantityInputBlockPanel.add(quantityInputTextField);
		quantityInputBlockPanel.add(quantityInputButton);
		// <==================== ====================>

		// Định nghĩa tính chất cho Quantity Input Dialog
		quantityInputDialog = new JDialog();
		quantityInputDialog.setTitle("Nhập số lượng sản phẩm");
		quantityInputDialog.setLayout(null);
		quantityInputDialog.setSize(500, 220);
		quantityInputDialog.setResizable(false);
		quantityInputDialog.setLocationRelativeTo(null);
		quantityInputDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		quantityInputDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				// Đóng Dialog khi mất focus (nhấn ngoài)
				quantityInputDialog.dispose();
			}
		});
		quantityInputDialog.add(quantityInputBlockPanel);
		quantityInputDialog.setModal(true);
		quantityInputDialog.setVisible(true);
	}

	// Hàm cập nhật lại danh sách sản phẩm
	private void renderListPanel(String[] join, String condition, String order) {
		listPanel.removeAll();
		// - Truy vấn dữ liệu về tất cả sản phẩm hiện có
		if(condition == null) condition = "trangThai = 1";
		else condition += " AND trangThai = 1";
		productList = productBLL.getAllProductByCondition(join,condition,order);
		datas = new Object[productList.size()][5];
		for (int i = 0; i < productList.size(); i++) {
			datas[i][0] = (Object) productList.get(i).getId();
			datas[i][1] = (Object) productList.get(i).getName();
			datas[i][2] = (Object) productTypeBLL.getOneProductTypeById(productList.get(i).getProductTypeId()).getName();
			datas[i][3] = (Object) productList.get(i).getPrice();
			datas[i][4] = (Object) productList.get(i).getImage();
		}

		// - Duyệt qua từng đối tượng
		int x = 0, y = 0;
		for (int i = 0; i < productList.size(); i++) {
			// + Biến tạm giữ vị trí đối tượng được duyệt
			int j = i;

			// + Ảnh sản phẩm
			JLabel imageLabel = CommonPL.getImageLabel(210, 190,
					CommonPL.getMiddlePathToShowProductImage() + String.valueOf(datas[i][4]));
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
			JButton orderButton = CommonPL.getRoundedBorderButton(14, "Đặt", Color.decode("#1976D2"), Color.WHITE,
					fontButton);
			orderButton.setBounds(10, 270, 190, 30);
			orderButton.addActionListener(e -> {
				// + Truy vấn thông tin sản phẩm hiện tại
				Vector<Object> product = new Vector<Object>();
				product.add(datas[j][0]); // + Mã sản phẩm
				product.add(datas[j][1]); // + Tên sản phẩm
				product.add(datas[j][3]); // + Giá sản phẩm

				// + Hỏi xem cần bao nhiêu sản phẩm ?
				showQuantityInputDialog(product);
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
			blockPanel.add(orderButton);

			// + Thêm vào danh sách sản phẩm
			listPanel.add(blockPanel);

			// + Cập nhật lại vị trí của từng sản phẩm
			if (x == 450) {
				x = 0;
				y += 325;
			} else {
				x += 225;
			}
		}

		// - Cập nhật lại kích thước
		listPanel.setPreferredSize(new Dimension(675, (int) ((Math.ceil(1.0 * datas.length / 3) * 325))));
		listPanel.revalidate();
		listPanel.repaint();
	}

	// Hàm cập nhật danh sách sản phẩm ở giỏ hàng
	private void renderOrderListPanel() {
		// - Xoá tất cả Component hiện có trong Order List Panel
		orderListPanel.removeAll();

		// - Duyệt qua từng đối tượng
		for (int i = 0; i < shoppingCart.size(); i++) {
			// + Biến tạm giữ sản phẩm được duyệt trong giỏ hàng
			Object[] productSelected = shoppingCart.get(i);

			// + Biểu tượng thùng rác
			JLabel trash = CommonPL.getImageLabel(20, 20, CommonPL.getMiddlePathToShowIcon() + "trash-icon.png");
			trash.setBounds(10, 10, 20, 20);
			trash.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					updateShoppingCart(productSelected, false);
				}
			});
			// + Tên sản phẩm
			JLabel name = CommonPL.getParagraphLabel(String.valueOf(productSelected[1]), Color.BLACK,
					fontParagraphProductInBill);
			name.setBounds(40, 0, 200, 40);
			// + Số lượng
			JLabel quantity = CommonPL.getTitleLabel(String.valueOf(productSelected[2]), Color.BLACK,
					fontParagraphProductInBill, SwingConstants.CENTER, SwingConstants.CENTER);
			quantity.setBounds(240, 0, 65, 40);
			// + Thành tiền
			JLabel finalCost = CommonPL.getTitleLabel(String.valueOf(productSelected[3]), Color.BLACK,
					fontParagraphProductInBill, SwingConstants.CENTER, SwingConstants.CENTER);
			finalCost.setBounds(305, 0, 100, 40);
			// + Đường kẻ ngang
			JPanel linePanel = new CommonPL.RoundedPanel(8);
			linePanel.setBounds(0, 0, 405, 2);
			linePanel.setBackground(Color.BLACK);
			// + Dòng chứa các dữ liệu
			JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
			rowPanel.setLayout(null);
			rowPanel.setBounds(0, i * 40, 405, 40);
			rowPanel.setBackground(Color.WHITE);
			if (i >= 1)
				rowPanel.add(linePanel);
			rowPanel.add(trash);
			rowPanel.add(name);
			rowPanel.add(quantity);
			rowPanel.add(finalCost);

			// + Thêm vào listPanel
			orderListPanel.add(rowPanel);
		}

		// - Cập nhật lại Order List Panel
		orderListPanel.setPreferredSize(new Dimension(405, shoppingCart.size() * 40));
		orderListPanel.revalidate();
		orderListPanel.repaint();

		// - Tính tổng giá sản phẩm hiện có trong Giỏ hàng
		// (sử dụng BigInteger để chứa số lớn)
		totalPrice = new BigInteger("0");
		for (int i = 0; i < shoppingCart.size(); i++) {
			BigInteger quantity = new BigInteger(shoppingCart.get(i)[2].toString());
			BigInteger price = new BigInteger(shoppingCart.get(i)[3].toString());
			totalPrice = totalPrice.add(quantity.multiply(price));
		}
		// - Cập nhật lại tổng giá trên giao diện
		summaryCostDetailLabel
				.setText(CommonPL.moneyLongToMoneyFormat(new BigInteger((String.valueOf(totalPrice)))) + " VNĐ");
	}	
}
