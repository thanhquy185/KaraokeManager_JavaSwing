package PL;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import javax.swing.Timer;

import BLL.RoomBLL;
import DTO.RoomDTO;
import PL.CommonPL.CustomTextField;

public class Admin_KaraokeRoomManagerPL extends JPanel {
	// Các đối tượng từ tầng Bussiness Logical Layer
	private RoomBLL roomBLL;
	// Các Font
	private Font fontParagraph = new Font("Arial", Font.PLAIN, 14);
	private Font fontTitleInBill = new Font("Arial", Font.BOLD, 28);
	private Font fontSubTitleInBill = new Font("Arial", Font.BOLD, 20);
	private Font fontParagraphBoldInBill = new Font("Arial", Font.BOLD, 16);
	private Font fontParagraphPlainInBill = new Font("Arial", Font.PLAIN, 16);
	private Font fontParagraphProductInBill = new Font("Arial", Font.PLAIN, 12);
	private Font fontButton = new Font("Arial", Font.BOLD, 14);
	private Font fontTitleInProductInfor = new Font("Arial", Font.BOLD, 20);
	private Font fontParagraphInProductInfor = new Font("Arial", Font.PLAIN, 14);
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Product Panel
	private JPanel block1PanelInRoomPanel;
	private JLabel block1LabelInRoomPanel;
	private JPanel block2PanelInRoomPanel;
	private JLabel block2LabelInRoomPanel;
	private JPanel block3PanelInRoomPanel;
	private JLabel block3LabelInRoomPanel;
	private JButton addButtonInRoomPanel;
	private JButton updateButtonInRoomPanel;
	private JButton lockButtonInRoomPanel;
	private JTextField findInputTextFieldInRoomPanel;
	private JComboBox<String> typesComboBoxInRoomPanel;
	private JComboBox<String> statusComboBoxInRoomPanel;
	private JButton filterButtonInRoomPanel;
	private JPanel listPanelInRoomPanel;
	private JScrollPane listScrollPaneInRoomPanel;
	private JPanel roomPanel;
	// - Các Component của Bill Panel
	private JLabel roomNameLabelInBillPanel;
	private JLabel customerIdLabelInBillPanel;
	private JLabel customerIdDetailLabelInBillPanel;
	private JLabel employeeIdLabelInBillPanel;
	private JLabel employeeIdDetailLabelInBillPanel;
	private JLabel orderDateLabelInBillPanel;
	private JLabel orderDateDetailLabelInBillPanel;
	private JLabel roomTypeLabelInBillPanel;
	private JLabel roomTypeDetailLabelInBillPanel;
	private JLabel orderTimeLabelInBillPanel;
	private JLabel orderTimeDetailLabelInBillPanel;
	private JLabel roomCostLabelInBillPanel;
	private JLabel roomCostDetailLabelInBillPanel;
	private JPanel line1PanelInBillPanel;
	private JLabel productNameLabelInBillPanel;
	private JLabel productQuantityLabelInBillPanel;
	private JLabel productFinalCostLabelInBillPanel;
	private JPanel productPanelInBillPanel;
	private JScrollPane productScrollPaneInBillPanel;
	private JPanel line2PanelInBillPanel;
	private JLabel summaryCostLabelInBillPanel;
	private JLabel summaryCostDetailLabelInBillPanel;
	private JLabel promotionCostLabelInBillPanel;
	private JLabel promotionCostDetailLabelInBillPanel;
	private JLabel finalCostLabelInBillPanel;
	private JLabel finalCostDetailLabelInBillPanel;
	private JComboBox<String> payMethodsComboBoxInBillPanel;
	private JButton payButtonInBillPanel;
	private JButton printBillButtonInBillPanel;
	private JPanel billPanel;
	// - Các Component của Order Panel
	private JLabel roomInfoLabelInOrderPanel;
	private JPanel roomLinePanelInOrderPanel;
	private JLabel roomIdLabelInOrderPanel;
	private JTextField roomIdTextFieldInOrderPanel;
	private JLabel roomTypeLabelInOrderPanel;
	private JTextField roomTypeTextFieldInOrderPanel;
	private JLabel roomNameLabelInOrderPanel;
	private JTextField roomNameTextFieldInOrderPanel;
	private JLabel roomStatusLabelInOrderPanel;
	private JTextField roomStatusTextFieldInOrderPanel;
	private JLabel orderInfoLabelInOrderPanel;
	private JPanel orderLinePanelInOrderPanel;
	private JLabel billIdLabelInOrderPanel;
	private JTextField billIdTextFieldInOrderPanel;
	private JLabel orderDateLabelInOrderPanel;
	private JTextField orderDateTextFieldInOrderPanel;
	private JLabel customerIdLabelInOrderPanel;
	private JTextField customerIdTextFieldInOrderPanel;
	private JLabel employeeIdLabelInOrderPanel;
	private JTextField employeeIdTextFieldInOrderPanel;
	private JLabel billHoursLabelInOrderPanel;
	private JTextField billHoursTextFieldInOrderPanel;
	private JLabel billCostLabelInOrderPanel;
	private JTextField billCostTextFieldInOrderPanel;
	private JLabel billStatusLabelInOrderPanel;
	private JTextField billStatusTextFieldInOrderPanel;
	private JButton orderButtonInOrderPanel;
	private JPanel orderPanel;
	// - Các Component của Lock Panel
	private JLabel lockLabelInLockPanel;
	private JPanel lockPanel;

	int counter = 0;

	public Admin_KaraokeRoomManagerPL() {
		// <===== Các đối tượng từ tầng Bussiness Logical Layer =====>
		roomBLL = new RoomBLL();
		// <==================== ====================>

		// <===== Cấu trúc của Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Phòng hát", Color.BLACK, CommonPL.getFontTitle(), SwingConstants.CENTER,
				SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);
		// <==================== ====================>

		// <===== Cấu trúc của Room Panel =====>
		// - Tuỳ chỉnh Block 1 Panel In Room Panel
		block1PanelInRoomPanel = new JPanel();
		block1PanelInRoomPanel.setBounds(15, 15, 30, 30);
		block1PanelInRoomPanel.setBackground(Color.decode("#fe6d73"));

		// - Tuỳ chỉnh Block 1 Label In Room Panel
		block1LabelInRoomPanel = CommonPL.getParagraphLabel("Sử dụng", Color.decode("#fe6d73"), fontParagraph);
		block1LabelInRoomPanel.setBounds(50, 15, 55, 30);

		// - Tuỳ chỉnh Block 2 Panel In Room Panel
		block2PanelInRoomPanel = new JPanel();
		block2PanelInRoomPanel.setBounds(120, 15, 30, 30);
		block2PanelInRoomPanel.setBackground(Color.decode("#17c3b2"));

		// - Tuỳ chỉnh Block 2 Label In Room Panel
		block2LabelInRoomPanel = CommonPL.getParagraphLabel("Trống", Color.decode("#17c3b2"), fontParagraph);
		block2LabelInRoomPanel.setBounds(155, 15, 40, 30);

		// - Tuỳ chỉnh Block 3 Panel In Room Panel
		block3PanelInRoomPanel = new JPanel();
		block3PanelInRoomPanel.setBounds(210, 15, 30, 30);
		block3PanelInRoomPanel.setBackground(Color.decode("#DEDEDE"));

		// - Tuỳ chỉnh Block 3 Label In Room Panel
		block3LabelInRoomPanel = CommonPL.getParagraphLabel("Bảo trì", Color.decode("#DEDEDE"), fontParagraph);
		block3LabelInRoomPanel.setBounds(245, 15, 55, 30);

		// - Tuỳ chỉnh Order Button In Room Panel
		addButtonInRoomPanel = CommonPL.getRoundedBorderButton(14, "Thêm", Color.decode("#699f4c"), Color.WHITE,
				fontButton);
		addButtonInRoomPanel.setBounds(300, 15, 115, 30);

		// - Tuỳ chỉnh Update Button
		updateButtonInRoomPanel = CommonPL.getRoundedBorderButton(14, "Sửa", Color.decode("#bf873e"), Color.WHITE,
				fontButton);
		updateButtonInRoomPanel.setBounds(430, 15, 115, 30);

		// - Tuỳ chỉnh Lock Button In Room Panel
		lockButtonInRoomPanel = CommonPL.getRoundedBorderButton(14, "Xoá", Color.decode("#9f4d4d"), Color.WHITE,
				fontButton);
		lockButtonInRoomPanel.setBounds(560, 15, 115, 30);

		// - Tuỳ chỉnh Find Input Text Field In Room Panel
		findInputTextFieldInRoomPanel = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên phòng", Color.LIGHT_GRAY,
				Color.BLACK, fontParagraph);
		findInputTextFieldInRoomPanel.setBounds(15, 60, 180, 30);

		// - Tuỳ chỉnh Type ComboBox In Room Panel
		Vector<String> types = CommonPL.getVectorHasValues(new String[] { "Chọn Loại phòng", "Thường", "Cao cấp" });
		typesComboBoxInRoomPanel = CommonPL.CustomComboBox(types, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		typesComboBoxInRoomPanel.setBounds(210, 60, 180, 30);

		// - Tuỳ chỉnh Status ComboBox In Room Panel
		Vector<String> status = CommonPL.getVectorHasValues(new String[] { "Chọn Trạng thái", "Sử dụng", "Trống" });
		statusComboBoxInRoomPanel = CommonPL.CustomComboBox(status, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		statusComboBoxInRoomPanel.setBounds(405, 60, 180, 30);

		// - Tuỳ chỉnh Filter Button In Room Panel
		filterButtonInRoomPanel = CommonPL.getRoundedBorderButton(14, "Lọc", Color.decode("#007bff"), Color.WHITE,
				fontButton);
		filterButtonInRoomPanel.setBounds(600, 60, 75, 30);

		// - Tuỳ chỉnh List Panel In Room Panel
		listPanelInRoomPanel = new JPanel();
		listPanelInRoomPanel.setLayout(null);
		listPanelInRoomPanel.setBackground(Color.WHITE);

		// - Tuỳ chỉnh List Scroll Pane In Room Panel
		listScrollPaneInRoomPanel = new JScrollPane(listPanelInRoomPanel);
		listScrollPaneInRoomPanel.setBounds(15, 105, 675, 585);
		listScrollPaneInRoomPanel.setBorder(null);
		listScrollPaneInRoomPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScrollPaneInRoomPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// - Tuỳ chỉnh Product Panel;
		roomPanel = new CommonPL.RoundedPanel(12);
		roomPanel.setLayout(null);
		roomPanel.setBackground(Color.WHITE);
		roomPanel.setBounds(30, 115, 690, 705);
		roomPanel.add(block1PanelInRoomPanel);
		roomPanel.add(block1LabelInRoomPanel);
		roomPanel.add(block2PanelInRoomPanel);
		roomPanel.add(block2LabelInRoomPanel);
		roomPanel.add(block3PanelInRoomPanel);
		roomPanel.add(block3LabelInRoomPanel);
		roomPanel.add(addButtonInRoomPanel);
		roomPanel.add(updateButtonInRoomPanel);
		roomPanel.add(lockButtonInRoomPanel);
		roomPanel.add(findInputTextFieldInRoomPanel);
		roomPanel.add(typesComboBoxInRoomPanel);
		roomPanel.add(statusComboBoxInRoomPanel);
		roomPanel.add(filterButtonInRoomPanel);
		roomPanel.add(listScrollPaneInRoomPanel);
		// <==================== ====================>

		// <===== Cấu trúc của Order Panel =====>
		// - Tuỳ chỉnh Room Info Label In Order Panel
		roomInfoLabelInOrderPanel = CommonPL.getTitleLabel("Thông tin phòng", Color.decode("#1976D2"),
				fontSubTitleInBill, SwingConstants.CENTER, SwingConstants.CENTER);
		roomInfoLabelInOrderPanel.setBounds(15, 7, 405, 30);

		// - Tuỳ chỉnh Room Line Panel In Order Panel
		roomLinePanelInOrderPanel = new CommonPL.RoundedPanel(8);
		roomLinePanelInOrderPanel.setBackground(Color.decode("#1976D2"));
		roomLinePanelInOrderPanel.setBounds(15, 47, 405, 3);

		// - Tuỳ chỉnh Room Id Label In Order Panel
		roomIdLabelInOrderPanel = CommonPL.getParagraphLabel("Mã phòng", Color.BLACK, fontParagraphPlainInBill);
		roomIdLabelInOrderPanel.setBounds(15, 60, 195, 30);

		// - Tuỳ chỉnh Room Id Text Field In Order Panel
		roomIdTextFieldInOrderPanel = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã phòng", Color.LIGHT_GRAY,
				Color.BLACK, fontParagraphPlainInBill);
		roomIdTextFieldInOrderPanel.setEnabled(false);
		((CustomTextField) roomIdTextFieldInOrderPanel).setBorderColor(Color.decode("#dedede"));
		roomIdTextFieldInOrderPanel.setBackground(Color.decode("#dedede"));
		roomIdTextFieldInOrderPanel.setBounds(15, 90, 195, 30);

		// - Tuỳ chỉnh Room Type Label In Order Panel
		roomTypeLabelInOrderPanel = CommonPL.getParagraphLabel("Loại phòng", Color.BLACK, fontParagraphPlainInBill);
		roomTypeLabelInOrderPanel.setBounds(225, 60, 195, 30);

		// - Tuỳ chỉnh Room Type Text Field In Order Panel
		roomTypeTextFieldInOrderPanel = new CommonPL.CustomTextField(0, 0, 0, "Chọn Loại phòng", Color.LIGHT_GRAY,
				Color.BLACK, fontParagraphPlainInBill);
		roomTypeTextFieldInOrderPanel.setEnabled(false);
		((CustomTextField) roomTypeTextFieldInOrderPanel).setBorderColor(Color.decode("#dedede"));
		roomTypeTextFieldInOrderPanel.setBackground(Color.decode("#dedede"));
		roomTypeTextFieldInOrderPanel.setBounds(225, 90, 195, 30);

		// - Tuỳ chỉnh Room Name Label In Order Panel
		roomNameLabelInOrderPanel = CommonPL.getParagraphLabel("Tên phòng", Color.BLACK, fontParagraphPlainInBill);
		roomNameLabelInOrderPanel.setBounds(15, 130, 405, 30);

		// - Tuỳ chỉnh Room Name Text Field In Order Panel
		roomNameTextFieldInOrderPanel = new CommonPL.CustomTextField(0, 0, 0, "Nhập Tên phòng", Color.LIGHT_GRAY,
				Color.BLACK, fontParagraphPlainInBill);
		roomNameTextFieldInOrderPanel.setEnabled(false);
		((CustomTextField) roomNameTextFieldInOrderPanel).setBorderColor(Color.decode("#dedede"));
		roomNameTextFieldInOrderPanel.setBackground(Color.decode("#dedede"));
		roomNameTextFieldInOrderPanel.setBounds(15, 160, 405, 30);

		// - Tuỳ chỉnh Room Status Label In Order Panel
		roomStatusLabelInOrderPanel = CommonPL.getParagraphLabel("Trạng thái phòng", Color.BLACK,
				fontParagraphPlainInBill);
		roomStatusLabelInOrderPanel.setBounds(15, 200, 405, 30);

		// - Tuỳ chỉnh Room Name Text Field In Order Panel
		roomStatusTextFieldInOrderPanel = new CommonPL.CustomTextField(0, 0, 0, "Trống", Color.LIGHT_GRAY, Color.BLACK,
				fontParagraphPlainInBill);
		roomStatusTextFieldInOrderPanel.setEnabled(false);
		((CustomTextField) roomStatusTextFieldInOrderPanel).setBorderColor(Color.decode("#dedede"));
		roomStatusTextFieldInOrderPanel.setBackground(Color.decode("#dedede"));
		roomStatusTextFieldInOrderPanel.setBounds(15, 230, 405, 30);

		// - Tuỳ chỉnh Order Info Label In Order Panel
		orderInfoLabelInOrderPanel = CommonPL.getTitleLabel("Thông tin đặt phòng", Color.decode("#1976D2"),
				fontSubTitleInBill, SwingConstants.CENTER, SwingConstants.CENTER);
		orderInfoLabelInOrderPanel.setBounds(15, 287, 405, 30);

		// - Tuỳ chỉnh Order Line Panel In Order Panel
		orderLinePanelInOrderPanel = new CommonPL.RoundedPanel(8);
		orderLinePanelInOrderPanel.setBackground(Color.decode("#1976D2"));
		orderLinePanelInOrderPanel.setBounds(15, 317, 405, 3);

		// - Tuỳ chỉnh Customer Id Label In Order Panel
		customerIdLabelInOrderPanel = CommonPL.getParagraphLabel("Khách hàng", Color.BLACK, fontParagraphPlainInBill);
		customerIdLabelInOrderPanel.setBounds(15, 330, 195, 30);

		// - Tuỳ chỉnh Customer Id Text Field In Order Panel
		customerIdTextFieldInOrderPanel = new CommonPL.CustomTextField(0, 0, 0, "Nhập Khách hàng", Color.LIGHT_GRAY,
				Color.BLACK, fontParagraphPlainInBill);
		customerIdTextFieldInOrderPanel.setBounds(15, 360, 195, 30);

		// - Tuỳ chỉnh Employee Id Label In Order Panel
		employeeIdLabelInOrderPanel = CommonPL.getParagraphLabel("Nhân viên", Color.BLACK, fontParagraphPlainInBill);
		employeeIdLabelInOrderPanel.setBounds(225, 330, 195, 30);

		// - Tuỳ chỉnh Employee Id Text Field In Order Panel
		employeeIdTextFieldInOrderPanel = new CommonPL.CustomTextField(0, 0, 0, "Chọn Nhân viên", Color.LIGHT_GRAY,
				Color.BLACK, fontParagraphPlainInBill);
		employeeIdTextFieldInOrderPanel.setEnabled(false);
		((CustomTextField) employeeIdTextFieldInOrderPanel).setBorderColor(Color.decode("#dedede"));
		employeeIdTextFieldInOrderPanel.setBackground(Color.decode("#dedede"));
		employeeIdTextFieldInOrderPanel.setBounds(225, 360, 195, 30);

		// - Tuỳ chỉnh Bill Id Label In Order Panel
		billIdLabelInOrderPanel = CommonPL.getParagraphLabel("Mã hoá đơn", Color.BLACK, fontParagraphPlainInBill);
		billIdLabelInOrderPanel.setBounds(15, 400, 195, 30);

		// - Tuỳ chỉnh Bill Id Text Field In Order Panel
		billIdTextFieldInOrderPanel = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã hoá đơn", Color.LIGHT_GRAY,
				Color.BLACK, fontParagraphPlainInBill);
		billIdTextFieldInOrderPanel.setEnabled(false);
		((CustomTextField) billIdTextFieldInOrderPanel).setBorderColor(Color.decode("#dedede"));
		billIdTextFieldInOrderPanel.setBackground(Color.decode("#dedede"));
		billIdTextFieldInOrderPanel.setBounds(15, 430, 195, 30);

		// - Tuỳ chỉnh Order Date Label In Order Panel
		orderDateLabelInOrderPanel = CommonPL.getParagraphLabel("Ngày đặt phòng", Color.BLACK,
				fontParagraphPlainInBill);
		orderDateLabelInOrderPanel.setBounds(225, 400, 195, 30);

		// - Tuỳ chỉnh Order Date Text Field In Order Panel
		orderDateTextFieldInOrderPanel = new CommonPL.CustomTextField(0, 0, 0, "Chọn Ngày đặt phòng", Color.LIGHT_GRAY,
				Color.BLACK, fontParagraphPlainInBill);
		orderDateTextFieldInOrderPanel.setEnabled(false);
		((CustomTextField) orderDateTextFieldInOrderPanel).setBorderColor(Color.decode("#dedede"));
		orderDateTextFieldInOrderPanel.setBackground(Color.decode("#dedede"));
		orderDateTextFieldInOrderPanel.setBounds(225, 430, 195, 30);

		// - Tuỳ chỉnh Bill Hours Label In Order Panel
		billHoursLabelInOrderPanel = CommonPL.getParagraphLabel("Tổng số giờ", Color.BLACK, fontParagraphPlainInBill);
		billHoursLabelInOrderPanel.setBounds(15, 470, 195, 30);

		// - Tuỳ chỉnh Bill Hours Text Field In Order Panel
		billHoursTextFieldInOrderPanel = new CommonPL.CustomTextField(0, 0, 0, "Đang cập nhật", Color.LIGHT_GRAY,
				Color.BLACK, fontParagraphPlainInBill);
		billHoursTextFieldInOrderPanel.setEnabled(false);
		((CustomTextField) billHoursTextFieldInOrderPanel).setBorderColor(Color.decode("#dedede"));
		billHoursTextFieldInOrderPanel.setBackground(Color.decode("#dedede"));
		billHoursTextFieldInOrderPanel.setBounds(15, 500, 195, 30);

		// - Tuỳ chỉnh Bill Cost Label In Order Panel
		billCostLabelInOrderPanel = CommonPL.getParagraphLabel("Tổng tiền (VNĐ)", Color.BLACK,
				fontParagraphPlainInBill);
		billCostLabelInOrderPanel.setBounds(225, 470, 195, 30);

		// - Tuỳ chỉnh Bill Hours Text Field In Order Panel
		billCostTextFieldInOrderPanel = new CommonPL.CustomTextField(0, 0, 0, "Đang cập nhật", Color.LIGHT_GRAY,
				Color.BLACK, fontParagraphPlainInBill);
		billCostTextFieldInOrderPanel.setEnabled(false);
		((CustomTextField) billCostTextFieldInOrderPanel).setBorderColor(Color.decode("#dedede"));
		billCostTextFieldInOrderPanel.setBackground(Color.decode("#dedede"));
		billCostTextFieldInOrderPanel.setBounds(225, 500, 195, 30);

		// - Tuỳ chỉnh Bill Status Label In Order Panel
		billStatusLabelInOrderPanel = CommonPL.getParagraphLabel("Trạng thái đơn", Color.BLACK,
				fontParagraphPlainInBill);
		billStatusLabelInOrderPanel.setBounds(15, 540, 405, 30);

		// - Tuỳ chỉnh Bill Status Text Field In Order Panel
		billStatusTextFieldInOrderPanel = new CommonPL.CustomTextField(0, 0, 0, "Đang chờ đặt phòng", Color.LIGHT_GRAY,
				Color.BLACK, fontParagraphPlainInBill);
		billStatusTextFieldInOrderPanel.setEnabled(false);
		((CustomTextField) billStatusTextFieldInOrderPanel).setBorderColor(Color.decode("#dedede"));
		billStatusTextFieldInOrderPanel.setBackground(Color.decode("#dedede"));
		billStatusTextFieldInOrderPanel.setBounds(15, 570, 405, 30);

		// - Tuỳ chỉnh Order Button In Order Panel
		orderButtonInOrderPanel = CommonPL.getRoundedBorderButton(14, "Đặt phòng", Color.decode("#1976D2"), Color.WHITE,
				fontButton);
		orderButtonInOrderPanel.setBounds(15, 660, 405, 30);

		// - Tuỳ chỉnh Order Panel;
		orderPanel = new CommonPL.RoundedPanel(12);
		orderPanel.setLayout(null);
		orderPanel.setBackground(Color.WHITE);
		orderPanel.setBounds(735, 115, 435, 705);
		orderPanel.add(roomInfoLabelInOrderPanel);
		orderPanel.add(roomLinePanelInOrderPanel);
		orderPanel.add(roomIdLabelInOrderPanel);
		orderPanel.add(roomIdTextFieldInOrderPanel);
		orderPanel.add(roomTypeLabelInOrderPanel);
		orderPanel.add(roomTypeTextFieldInOrderPanel);
		orderPanel.add(roomNameLabelInOrderPanel);
		orderPanel.add(roomNameTextFieldInOrderPanel);
		orderPanel.add(roomStatusLabelInOrderPanel);
		orderPanel.add(roomStatusTextFieldInOrderPanel);
		orderPanel.add(orderInfoLabelInOrderPanel);
		orderPanel.add(orderLinePanelInOrderPanel);
		orderPanel.add(customerIdLabelInOrderPanel);
		orderPanel.add(customerIdTextFieldInOrderPanel);
		orderPanel.add(employeeIdLabelInOrderPanel);
		orderPanel.add(employeeIdTextFieldInOrderPanel);
		orderPanel.add(billIdLabelInOrderPanel);
		orderPanel.add(billIdTextFieldInOrderPanel);
		orderPanel.add(orderDateLabelInOrderPanel);
		orderPanel.add(orderDateTextFieldInOrderPanel);
		orderPanel.add(billHoursLabelInOrderPanel);
		orderPanel.add(billHoursTextFieldInOrderPanel);
		orderPanel.add(billCostLabelInOrderPanel);
		orderPanel.add(billCostTextFieldInOrderPanel);
		orderPanel.add(billStatusLabelInOrderPanel);
		orderPanel.add(billStatusTextFieldInOrderPanel);
		orderPanel.add(orderButtonInOrderPanel);
		orderPanel.setVisible(true);
		// <===================== ====================>

		// <===== Cấu trúc của Bill Panel =====>
		// - Tuỳ chỉnh Room Name Label In Bill Panel
		roomNameLabelInBillPanel = CommonPL.getTitleLabel("<html><p style='text-align: center'>Phòng 101</p></html>",
				Color.decode("#1976D2"), fontTitleInBill, SwingConstants.CENTER, SwingConstants.CENTER);
		roomNameLabelInBillPanel.setBounds(15, 0, 105, 210);

		// - Tuỳ chỉnh Customer Id Label In Bill Panel
		customerIdLabelInBillPanel = CommonPL.getParagraphLabel("Khách hàng:", Color.GRAY, fontParagraphPlainInBill);
		customerIdLabelInBillPanel.setBounds(135, 15, 100, 30);

		// - Tuỳ chỉnh Customer Detail Id Label In Bill Panel
		customerIdDetailLabelInBillPanel = CommonPL.getTitleLabel("079205019936", Color.decode("#1976D2"),
				fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		customerIdDetailLabelInBillPanel.setBounds(240, 15, 180, 30);

		// - Tuỳ chỉnh Employee Id Label In Bill Panel
		employeeIdLabelInBillPanel = CommonPL.getParagraphLabel("Nhân viên:", Color.GRAY, fontParagraphPlainInBill);
		employeeIdLabelInBillPanel.setBounds(135, 45, 100, 30);

		// - Tuỳ chỉnh Employee Id Detail Label In Bill Panel
		employeeIdDetailLabelInBillPanel = CommonPL.getTitleLabel("10 - Trần Thanh Quy", Color.decode("#1976D2"),
				fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		employeeIdDetailLabelInBillPanel.setBounds(240, 45, 180, 30);

		// - Tuỳ chỉnh Room Type Label In Bill Panel
		roomTypeLabelInBillPanel = CommonPL.getParagraphLabel("Loại phòng:", Color.GRAY, fontParagraphPlainInBill);
		roomTypeLabelInBillPanel.setBounds(135, 75, 100, 30);

		// - Tuỳ chỉnh Room Type Detail Label In Bill Panel
		roomTypeDetailLabelInBillPanel = CommonPL.getTitleLabel("Thường", Color.decode("#1976D2"),
				fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		roomTypeDetailLabelInBillPanel.setBounds(240, 75, 180, 30);

		// - Tuỳ chỉnh Order Date Label In Bill Panel
		orderDateLabelInBillPanel = CommonPL.getParagraphLabel("Ngày đặt:", Color.GRAY, fontParagraphPlainInBill);
		orderDateLabelInBillPanel.setBounds(135, 105, 100, 30);

		// - Tuỳ chỉnh Order Date Detail Label In Bill Panel
		orderDateDetailLabelInBillPanel = CommonPL.getTitleLabel(String.valueOf(LocalDate.now()),
				Color.decode("#1976D2"), fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		orderDateDetailLabelInBillPanel.setBounds(240, 105, 180, 30);

		// - Tuỳ chỉnh Order Time Label In Bill Panel
		orderTimeLabelInBillPanel = CommonPL.getParagraphLabel("Tổng giờ:", Color.GRAY, fontParagraphPlainInBill);
		orderTimeLabelInBillPanel.setBounds(135, 135, 100, 30);

		// - Tuỳ chỉnh Order Time Detail Label In Bill Panel
		orderTimeDetailLabelInBillPanel = CommonPL.getTitleLabel("00:00:00", Color.decode("#1976D2"),
				fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		orderTimeDetailLabelInBillPanel.setBounds(240, 135, 180, 30);
		// + Tạo Timer cập nhật thời gian
		Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				counter++; // Tăng số giây lên 1
				int hours = counter / 3600;
				int minutes = (counter % 3600) / 60;
				int seconds = counter % 60;

				String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
				orderTimeDetailLabelInBillPanel.setText(formattedTime);
			}
		});
		timer.start();

		// - Tuỳ chỉnh Room Cost Label In Bill Panel
		roomCostLabelInBillPanel = CommonPL.getParagraphLabel("Tiền phòng:", Color.GRAY, fontParagraphPlainInBill);
		roomCostLabelInBillPanel.setBounds(135, 165, 100, 30);

		// - Tuỳ chỉnh Room Cost Detail Label In Bill Panel
		roomCostDetailLabelInBillPanel = CommonPL.getTitleLabel(
				CommonPL.moneyLongToMoneyFormat(new BigInteger(String.valueOf("10000000"))) + " VNĐ",
				Color.decode("#1976D2"), fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		roomCostDetailLabelInBillPanel.setBounds(240, 165, 180, 30);

		// - Tuỳ chỉnh Line 1 Panel In Bill Panel
		line1PanelInBillPanel = new CommonPL.RoundedPanel(8);
		line1PanelInBillPanel.setBackground(Color.decode("#1976D2"));
		line1PanelInBillPanel.setBounds(15, 210, 405, 3);

		// - Tuỳ chỉnh Product Name Label In Bill Panel
		productNameLabelInBillPanel = CommonPL.getTitleLabel("	Tên sản phẩm", Color.GRAY, fontParagraphProductInBill,
				SwingConstants.CENTER, SwingConstants.CENTER);
		productNameLabelInBillPanel.setBounds(15, 225, 200, 30);

		// - Tuỳ chỉnh Unit Product Label In Bill Panel
		productQuantityLabelInBillPanel = CommonPL.getTitleLabel("Số lượng", Color.GRAY, fontParagraphProductInBill,
				SwingConstants.CENTER, SwingConstants.CENTER);
		productQuantityLabelInBillPanel.setBounds(215, 225, 65, 30);

		// - Tuỳ chỉnh Product Final Cost Label In Bill Panel
		productFinalCostLabelInBillPanel = CommonPL.getTitleLabel("Thành tiền", Color.GRAY, fontParagraphProductInBill,
				SwingConstants.CENTER, SwingConstants.CENTER);
		productFinalCostLabelInBillPanel.setBounds(280, 225, 140, 30);

		// - Truy vấn bảng dữ liệu nguyên liệu và chi tiết sản phẩm
		Object[][] datas = { { "Mì trứng", "2", "40000" }, { "Chai Pepsi", "5", "50000" },
				{ "Chai Cocacola", "19", "190000" }, };
		// - Tuỳ chỉnh Add Or Update Product Panel In Bill Panel
		productPanelInBillPanel = new JPanel();
		productPanelInBillPanel.setLayout(null);
		productPanelInBillPanel.setPreferredSize(new Dimension(405, datas.length * 34));
		for (int i = 0; i < datas.length; i++) {
			// + Tên sản phẩm
			JLabel name = CommonPL.getParagraphLabel(String.valueOf(datas[i][0]), Color.GRAY,
					fontParagraphProductInBill);
			name.setBounds(0, 0, 200, 34);
			// + Số lượng
			JLabel quantity = CommonPL.getTitleLabel(String.valueOf(datas[i][1]), Color.GRAY,
					fontParagraphProductInBill, SwingConstants.CENTER, SwingConstants.CENTER);
			quantity.setBounds(200, 0, 65, 34);
			// + Thành tiền
			JLabel finalCost = CommonPL.getTitleLabel(String.valueOf(datas[i][2]), Color.GRAY,
					fontParagraphProductInBill, SwingConstants.CENTER, SwingConstants.CENTER);
			finalCost.setBounds(265, 0, 140, 34);
			// + Đường kẻ ngang
			JPanel linePanel = new CommonPL.RoundedPanel(8);
			linePanel.setBounds(0, 0, 405, 2);
			// + Dòng chứa các dữ liệu
			JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
			rowPanel.setLayout(null);
			rowPanel.setBounds(0, i * 34, 405, 34);
			rowPanel.setBackground(Color.WHITE);
			if (i >= 1)
				rowPanel.add(linePanel);
			rowPanel.add(name);
			rowPanel.add(quantity);
			rowPanel.add(finalCost);

			// + Thêm vào Product Panel In Bill Panel
			productPanelInBillPanel.setBackground(Color.WHITE);
			productPanelInBillPanel.add(rowPanel);
		}

		// - Tuỳ chỉnh Product Scroll Pane In Bill Panel
		productScrollPaneInBillPanel = new JScrollPane(productPanelInBillPanel);
		productScrollPaneInBillPanel.setBounds(15, 255, 420, 204);
		productScrollPaneInBillPanel.setBorder(null);
		productScrollPaneInBillPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		productScrollPaneInBillPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// - Tuỳ chỉnh Line 2 Panel In Bill Panel
		line2PanelInBillPanel = new CommonPL.RoundedPanel(8);
		line2PanelInBillPanel.setBackground(Color.decode("#1976D2"));
		line2PanelInBillPanel.setBounds(15, 482, 405, 3);

		// - Tuỳ chỉnh Summary Cost Label In Bill Panel
		summaryCostLabelInBillPanel = CommonPL.getParagraphLabel("Tổng cộng:", Color.GRAY, fontParagraphPlainInBill);
		summaryCostLabelInBillPanel.setBounds(15, 500, 100, 30);

		// - Tuỳ chỉnh Summary Cost Detail Label In Bill Panel
		summaryCostDetailLabelInBillPanel = CommonPL.getTitleLabel(
				CommonPL.moneyLongToMoneyFormat(new BigInteger(String.valueOf("10280000"))) + " VNĐ",
				Color.decode("#1976D2"), fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		summaryCostDetailLabelInBillPanel.setBounds(120, 500, 300, 30);

		// - Tuỳ chỉnh Promotion Cost Label In Bill Panel
		promotionCostLabelInBillPanel = CommonPL.getParagraphLabel("Khuyến mãi:", Color.GRAY, fontParagraphPlainInBill);
		promotionCostLabelInBillPanel.setBounds(15, 530, 100, 30);

		// - Tuỳ chỉnh Promotion Cost Detail Label In Bill Panel
		promotionCostDetailLabelInBillPanel = CommonPL.getTitleLabel(
				"-" + CommonPL.moneyLongToMoneyFormat(new BigInteger(String.valueOf("10000000"))) + " VNĐ",
				Color.decode("#1976D2"), fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		promotionCostDetailLabelInBillPanel.setBounds(120, 530, 300, 30);

		// - Tuỳ chỉnh Final Cost Label In Bill Panel
		finalCostLabelInBillPanel = CommonPL.getParagraphLabel("Thành tiền:", Color.GRAY, fontParagraphPlainInBill);
		finalCostLabelInBillPanel.setBounds(15, 560, 100, 30);

		// - Tuỳ chỉnh Final Cost Detail Label In Bill Panel
		finalCostDetailLabelInBillPanel = CommonPL.getTitleLabel(
				CommonPL.moneyLongToMoneyFormat(new BigInteger(String.valueOf("280000"))) + " VNĐ",
				Color.decode("#1976D2"), fontParagraphBoldInBill, SwingConstants.RIGHT, SwingConstants.CENTER);
		finalCostDetailLabelInBillPanel.setBounds(120, 560, 300, 30);

		// - Tuỳ chỉnh Pay Methods ComboBox In Bill Panel
		Vector<String> payMethods = CommonPL.getVectorHasValues(new String[] { "Chọn Phương thức thanh toán",
				"Thanh toán bằng tiền mặt", "Thanh toán bằng chuyển khoản", "Thanh toán bằng thẻ ngân hàng" });
		payMethodsComboBoxInBillPanel = CommonPL.CustomComboBox(payMethods, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, fontParagraph);
		payMethodsComboBoxInBillPanel.setBounds(15, 620, 405, 30);

		// - Tuỳ chỉnh Pay Button In Bill Panel
		payButtonInBillPanel = CommonPL.getRoundedBorderButton(14, "Thanh toán", Color.decode("#1976D2"), Color.WHITE,
				fontButton);
		payButtonInBillPanel.setBounds(15, 660, 195, 30);

		// - Tuỳ chỉnh Print Bill Button In Bill Panel
		printBillButtonInBillPanel = CommonPL.getRoundedBorderButton(14, "In hoá đơn", Color.decode("#1976D2"),
				Color.WHITE, fontButton);
		printBillButtonInBillPanel.setBounds(225, 660, 195, 30);

		// - Tuỳ chỉnh Bill Panel;
		billPanel = new CommonPL.RoundedPanel(12);
		billPanel.setLayout(null);
		billPanel.setBackground(Color.WHITE);
		billPanel.setBounds(735, 115, 435, 705);
		billPanel.add(roomNameLabelInBillPanel);
		billPanel.add(customerIdLabelInBillPanel);
		billPanel.add(customerIdDetailLabelInBillPanel);
		billPanel.add(employeeIdLabelInBillPanel);
		billPanel.add(employeeIdDetailLabelInBillPanel);
		billPanel.add(orderDateLabelInBillPanel);
		billPanel.add(orderDateDetailLabelInBillPanel);
		billPanel.add(roomTypeLabelInBillPanel);
		billPanel.add(roomTypeDetailLabelInBillPanel);
		billPanel.add(orderTimeLabelInBillPanel);
		billPanel.add(orderTimeDetailLabelInBillPanel);
		billPanel.add(roomCostLabelInBillPanel);
		billPanel.add(roomCostDetailLabelInBillPanel);
		billPanel.add(line1PanelInBillPanel);
		billPanel.add(productNameLabelInBillPanel);
		billPanel.add(productQuantityLabelInBillPanel);
		billPanel.add(productFinalCostLabelInBillPanel);
		billPanel.add(productScrollPaneInBillPanel);
		billPanel.add(line2PanelInBillPanel);
		billPanel.add(summaryCostLabelInBillPanel);
		billPanel.add(summaryCostDetailLabelInBillPanel);
		billPanel.add(promotionCostLabelInBillPanel);
		billPanel.add(promotionCostDetailLabelInBillPanel);
		billPanel.add(finalCostLabelInBillPanel);
		billPanel.add(finalCostDetailLabelInBillPanel);
		billPanel.add(payMethodsComboBoxInBillPanel);
		billPanel.add(payButtonInBillPanel);
		billPanel.add(printBillButtonInBillPanel);
		billPanel.setVisible(false);
		// <==================== ====================>

		// <===== Cấu trúc của Lock Panel =====>
		// - Tuỳ chỉnh Lock Label In Lock Panel
		lockLabelInLockPanel = CommonPL.getTitleLabel(
				"<html><p style='text-align: center'>Phòng đang bảo trì</p></html>", Color.decode("#1976D2"),
				fontTitleInBill, SwingConstants.CENTER, SwingConstants.CENTER);
		lockLabelInLockPanel.setBounds(15, 0, 405, 705);

		// - Tuỳ chỉnh Lock Panel;
		lockPanel = new CommonPL.RoundedPanel(12);
		lockPanel.setLayout(null);
		lockPanel.setBackground(Color.WHITE);
		lockPanel.setBounds(735, 115, 435, 705);
		lockPanel.add(lockLabelInLockPanel);
		lockPanel.setVisible(false);
		// <===================== ====================>

		// Đĩnh nghĩa tính chất cho Dashboard Manager PL
		this.setBounds(CommonPL.getLeftMenuWidth(), 0, CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.setSize(CommonPL.getMainWidth(), CommonPL.getScreenHeightByOwner());
		this.setBackground(Color.decode("#E3F2FD"));
		this.setLayout(null);
		this.add(titleLabel);
		this.add(roomPanel);
		this.add(billPanel);
		this.add(orderPanel);
		this.add(lockPanel);

//		tableData.getSelectionModel().addListSelectionListener(e -> {
//			if (!e.getValueIsAdjusting()) {
//				rowSelected = tableData.getSelectedRow();
//			}
//		});
		addButtonInRoomPanel.addActionListener(e -> {
			showAddOrUpdateRoomDialog("Thêm Phòng", "Thêm", new Vector<Object>());
//			rowSelected = -1;
//			valueSelected[0] = false;
//			tableData.clearSelection();
		});
		updateButtonInRoomPanel.addActionListener(e -> {
			showAddOrUpdateRoomDialog("Thay đổi Phòng", "Thay đổi", new Vector<Object>());
//			if (rowSelected != -1) {
//				Vector<Object> currentObject = new Vector<>();
//				for (int i = 0; i < widthColumns.length; i++) {
//					currentObject.add(tableData.getValueAt(rowSelected, i));
//				}
//
//				showAddOrUpdateDialog("Thay đổi Người dùng", "Thay đổi", currentObject);
//			} else {
//				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần thay đổi");
//			}
//			rowSelected = -1;
//			valueSelected[0] = false;
//			tableData.clearSelection();
		});
		lockButtonInRoomPanel.addActionListener(e -> {
//			if (rowSelected != -1) {
//				Vector<Object> currentObject = new Vector<>();
//				for (int i = 0; i < widthColumns.length; i++) {
//					currentObject.add(tableData.getValueAt(rowSelected, i));
//				}
//
//				CommonPL.createSelectionsDialog("Thông báo lựa chọn",
//						String.format("Có chắc chắn muốn %s dòng dữ liệu này ?",
//								currentObject.get(currentObject.size() - 1).equals("Hoạt động") ? "khoá" : "mở khoá"),
//						valueSelected);
//
//				if (valueSelected[0]) {
//					String inform = accountBLL.lockAccount(String.valueOf(currentObject.get(0)),
//							CommonPL.getCurrentDate());
//					if (inform.equals("Có thể khoá một người dùng")) {
//						CommonPL.createSuccessDialog("Thông báo thành công", String.format("%s thành công",
//								currentObject.get(currentObject.size() - 1).equals("Hoạt động") ? "Khoá" : "Mở khoá"));
//						renderTableData(null, null, null);
//					} else {
//						CommonPL.createErrorDialog("Thông báo lỗi", inform);
//					}
//					renderTableData(null, null, null);
//				}
//			} else {
//				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần khoá");
//			}
//			rowSelected = -1;
//			valueSelected[0] = false;
//			tableData.clearSelection();
		});

		// Thiết lập sự kiện cập nhật danh sách phòng
		renderRoomData();

		//
//		buttonEventsInListPanel();

	}

	// - Các Component của Add Or Update Room Dialog (dialog thêm một phòng)
	private JLabel addOrUpdateRoomIdLabel;
	private JTextField addOrUpdateRoomIdTextField;
	private JLabel addOrUpdateRoomNameLabel;
	private JTextField addOrUpdateRoomNameTextField;
	private JLabel addOrUpdateRoomTypeLabel;
	private JComboBox<String> addOrUpdateRoomTypeComboBox;
	private JLabel addOrUpdateRoomStatusLabel;
	private JComboBox<String> addOrUpdateRoomStatusComboBox;
	private JButton addOrUpdateRoomButton;
	private JPanel addOrUpdateRoomBlockPanel;
	private JDialog addOrUpdateRoomDialog;

	private void showAddOrUpdateRoomDialog(String title, String button, Vector<Object> object) {
		// <===== Các Component của Add Or Update Room Block Panel =====>
		// - Tuỳ chỉnh Add Or Update Room Id Label
		addOrUpdateRoomIdLabel = CommonPL.getParagraphLabel("Mã phòng", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateRoomIdLabel.setBounds(20, 10, 460, 40);

		// - Tuỳ chỉnh Add Or Update Room Id Text Field
		addOrUpdateRoomIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã người dùng", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateRoomIdTextField.setEnabled(false);
		((CustomTextField) addOrUpdateRoomIdTextField).setBorderColor(Color.decode("#dedede"));
		addOrUpdateRoomIdTextField.setBackground(Color.decode("#dedede"));
		addOrUpdateRoomIdTextField.setBounds(20, 50, 460, 40);

		// - Tuỳ chỉnh Add Or Update Room Name Label
		addOrUpdateRoomNameLabel = CommonPL.getParagraphLabel("Tên phòng", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateRoomNameLabel.setBounds(20, 100, 460, 40);

		// - Tuỳ chỉnh Add Or Update Room Name Text Field
		addOrUpdateRoomNameTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã người dùng", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateRoomNameTextField.setBounds(20, 140, 460, 40);

		// - Tuỳ chỉnh Add Or Update Room Type Label
		addOrUpdateRoomTypeLabel = CommonPL.getParagraphLabel("Loại phòng", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateRoomTypeLabel.setBounds(20, 190, 460, 40);

		// - Tuỳ chỉnh Add Or Update Room Type ComboBox
		Vector<String> typesVector = CommonPL.getVectorHasValues(new String[] { "Chọn Loại phòng" });
		addOrUpdateRoomTypeComboBox = CommonPL.CustomComboBox(typesVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateRoomTypeComboBox.setBounds(20, 230, 460, 40);

		// - Tuỳ chỉnh Add Or Update Room Status Label
		addOrUpdateRoomStatusLabel = CommonPL.getParagraphLabel("Trạng thái", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateRoomStatusLabel.setBounds(20, 280, 460, 40);

		// - Tuỳ chỉnh Add Or Update Room Status ComboBox
		Vector<String> statusVector = CommonPL.getVectorHasValues(new String[] { "Chọn Trạng thái" });
		addOrUpdateRoomStatusComboBox = CommonPL.CustomComboBox(statusVector, Color.WHITE, Color.LIGHT_GRAY,
				Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateRoomStatusComboBox.setBounds(20, 320, 460, 40);

		// - Tuỳ chỉnh Add Or Update Button
		addOrUpdateRoomButton = CommonPL.getRoundedBorderButton(20, button,
				button == "Thêm" ? Color.decode("#699f4c") : Color.decode("#bf873e"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		addOrUpdateRoomButton.setBounds(20, 410, 460, 40);
		SwingUtilities.invokeLater(() -> addOrUpdateRoomButton.requestFocusInWindow());

		// - Tuỳ chỉnh Add Or Update Room Block Panel
		addOrUpdateRoomBlockPanel = new JPanel();
		addOrUpdateRoomBlockPanel.setLayout(null);
		addOrUpdateRoomBlockPanel.setBounds(0, 0, 500, 500);
		addOrUpdateRoomBlockPanel.setBackground(Color.WHITE);
		addOrUpdateRoomBlockPanel.add(addOrUpdateRoomIdLabel);
		addOrUpdateRoomBlockPanel.add(addOrUpdateRoomIdTextField);
		addOrUpdateRoomBlockPanel.add(addOrUpdateRoomNameLabel);
		addOrUpdateRoomBlockPanel.add(addOrUpdateRoomNameTextField);
		addOrUpdateRoomBlockPanel.add(addOrUpdateRoomTypeLabel);
		addOrUpdateRoomBlockPanel.add(addOrUpdateRoomTypeComboBox);
		addOrUpdateRoomBlockPanel.add(addOrUpdateRoomStatusLabel);
		addOrUpdateRoomBlockPanel.add(addOrUpdateRoomStatusComboBox);
		addOrUpdateRoomBlockPanel.add(addOrUpdateRoomButton);
		// <==================== ====================>

		// Định nghĩa tính chất cho Add Or Update Dialog
		addOrUpdateRoomDialog = new JDialog();
		addOrUpdateRoomDialog.setTitle(title);
		addOrUpdateRoomDialog.setLayout(null);
		addOrUpdateRoomDialog.setSize(500, 500);
		addOrUpdateRoomDialog.setResizable(false);
		addOrUpdateRoomDialog.setLocationRelativeTo(null);
		addOrUpdateRoomDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addOrUpdateRoomDialog.addWindowListener(new WindowAdapter() {
//					@Override
//					public void windowDeactivated(WindowEvent e) {
//						// Đóng Dialog khi mất focus (nhấn ngoài)
//						addOrUpdateRoomDialog.dispose();
//					}
		});
		addOrUpdateRoomDialog.add(addOrUpdateRoomBlockPanel);
		addOrUpdateRoomDialog.setModal(true);
		addOrUpdateRoomDialog.setVisible(true);
	}

	//
	private void buttonEventsInListPanel() {
		//
		ArrayList<JButton> buttonsInListPanel = CommonPL.getAllButtons(listPanelInRoomPanel);
		ArrayList<JButton> buttons = new ArrayList<>();
		buttons.addAll(buttonsInListPanel);

		for (JButton button : buttons) {
			System.out.println(((JLabel) button.getComponent(2)).getText());
		}

		// Thực hiện việc thêm sự kiện hoạt ảnh cho nút tương ứng
		for (int i = 0; i < buttons.size(); i++) {
			JButton buttonClicked = buttons.get(i);
			buttonClicked.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent evt) {
					// Tuỳ theo giá trị của nút được chọn mà khởi tạo Panel tương ứng
					String valueSelected = ((JLabel) buttonClicked.getComponent(1)).getText();

					// Duyệt qua các nút (lấy ra các nút không được nhấn)
					for (int j = 0; j < buttons.size(); j++) {
						JButton button = buttons.get(j);

						// Xoá tất cả các sự kiện của các nút
						MouseListener[] listeners = button.getMouseListeners();
						for (MouseListener listener : listeners) {
							button.removeMouseListener(listener);
						}

						// Lấy ra Label bên trong các nút
						String valueApproved = ((JLabel) button.getComponent(1)).getText();

						// Nếu không phải nút đã được nhấn
						if (!valueApproved.equals(valueSelected)) {
							// Các nút chuyển về mặc định
							// + Cập nhật lại icon
							ImageIcon image = new ImageIcon(
									CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
							Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
							ImageIcon scaledIcon = new ImageIcon(scaledImage);
							((JLabel) button.getComponent(0)).setIcon(scaledIcon);
							// + Cập nhật lại màu tên phòng
							((JLabel) button.getComponent(2)).setForeground(Color.WHITE);
							// + Cập nhật lại màu nền
							button.setBackground(Color.decode("#1976D2"));

							// Thiết lập sự kiện di chuyển chuột vào các nút
							button.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseEntered(MouseEvent evt) {
									// + Cập nhật lại icon
									ImageIcon image = new ImageIcon(
											CommonPL.getMiddlePathToShowIcon() + "micro-blue-icon.png");
									Image scaledImage = image.getImage().getScaledInstance(210, 210,
											Image.SCALE_SMOOTH);
									ImageIcon scaledIcon = new ImageIcon(scaledImage);
									((JLabel) button.getComponent(0)).setIcon(scaledIcon);
									// + Cập nhật lại màu tên phòng
									((JLabel) button.getComponent(2)).setForeground(Color.decode("#1976D2"));
									// + Cập nhật lại màu nền
									button.setBackground(Color.WHITE);
								}

								@Override
								public void mouseExited(MouseEvent evt) {
									// + Cập nhật lại icon
									ImageIcon image = new ImageIcon(
											CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
									Image scaledImage = image.getImage().getScaledInstance(210, 210,
											Image.SCALE_SMOOTH);
									ImageIcon scaledIcon = new ImageIcon(scaledImage);
									((JLabel) button.getComponent(0)).setIcon(scaledIcon);
									// + Cập nhật lại màu tên phòng
									((JLabel) button.getComponent(2)).setForeground(Color.WHITE);
									// + Cập nhật lại màu nền
									button.setBackground(Color.decode("#1976D2"));
								}
							});
						}
					}

					// Nút được nhấn được đổi màu
					// + Cập nhật lại icon
					ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-blue-icon.png");
					Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
					ImageIcon scaledIcon = new ImageIcon(scaledImage);
					((JLabel) buttonClicked.getComponent(0)).setIcon(scaledIcon);
					// + Cập nhật lại màu tên phòng
					((JLabel) buttonClicked.getComponent(2)).setForeground(Color.decode("#1976D2"));
					// + Cập nhật lại màu nền
					buttonClicked.setBackground(Color.WHITE);

					// Gọi lại sự kiện
					buttonEventsInListPanel();
				}
			});
		}
	}

	// Hàm cập nhật lại danh sách sản phẩm
	private void renderRoomData() {
		// - Truy vấn dữ liệu về tất cả phòng hiện có
		ArrayList<RoomDTO> roomList = roomBLL.getAllRoom();

		// - Duyệt qua từng đối tượng
		int x = 0, y = 0;
		for (RoomDTO roomDTO : roomList) {
			// + Ảnh phòng
			JLabel imageLabel = CommonPL.getImageLabel(210, 210,
					CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
			imageLabel.setBounds(2, 2, 206, 206);
			// + Mã phòng
			JLabel idLabel = CommonPL.getParagraphLabel(String.valueOf(roomDTO.getId()), Color.BLACK,
					fontParagraphInProductInfor);
			idLabel.setBounds(0, 0, 210, 20);
			// + Tên phòng
			JLabel nameLabel = CommonPL.getTitleLabel(String.valueOf(roomDTO.getName()), Color.WHITE,
					fontTitleInProductInfor, SwingConstants.LEFT, SwingConstants.CENTER);
			nameLabel.setBounds(10, 0, 210, 30);

			// + Khối chứa sản phẩm
			JButton blockPanel = new JButton();
			blockPanel.setLayout(null);
			blockPanel.setOpaque(true);
			blockPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
			blockPanel.setBounds(x, y, 210, 210);
			blockPanel.setBackground(Color.decode("#1976D2"));
			blockPanel.add(imageLabel);
			blockPanel.add(idLabel);
			blockPanel.add(nameLabel);

			if (roomDTO.getStatus()) {
				// + Cập nhật lại icon
				ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
				Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
				ImageIcon scaledIcon = new ImageIcon(scaledImage);
				imageLabel.setIcon(scaledIcon);
				// + Cập nhật lại màu tên phòng
				nameLabel.setForeground(Color.WHITE);
				// + Cập nhật lại màu nền
				blockPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#17c3b2"), 8));
				blockPanel.setBackground(Color.decode("#17c3b2"));
			} else {
				// + Cập nhật lại icon
				ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
				Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
				ImageIcon scaledIcon = new ImageIcon(scaledImage);
				imageLabel.setIcon(scaledIcon);
				// + Cập nhật lại màu tên phòng
				nameLabel.setForeground(Color.WHITE);
				// + Cập nhật lại màu nền
				blockPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 8));
				blockPanel.setBackground(Color.decode("#dedede"));
			}

//			if (datas[i][2] != null) {
//				// + Cập nhật lại icon
//				ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
//				Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
//				ImageIcon scaledIcon = new ImageIcon(scaledImage);
//				imageLabel.setIcon(scaledIcon);
//				// + Cập nhật lại màu tên phòng
//				nameLabel.setForeground(Color.WHITE);
//				// + Cập nhật lại màu nền
//				blockPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#fe6d73"), 8));
//				blockPanel.setBackground(Color.decode("#fe6d73"));
//			} else {
//				if (datas[i][3] == "1") {
//					// + Cập nhật lại icon
//					ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
//					Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
//					ImageIcon scaledIcon = new ImageIcon(scaledImage);
//					imageLabel.setIcon(scaledIcon);
//					// + Cập nhật lại màu tên phòng
//					nameLabel.setForeground(Color.WHITE);
//					// + Cập nhật lại màu nền
//					blockPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#17c3b2"), 8));
//					blockPanel.setBackground(Color.decode("#17c3b2"));
//				} else {
//					// + Cập nhật lại icon
//					ImageIcon image = new ImageIcon(CommonPL.getMiddlePathToShowIcon() + "micro-white-icon.png");
//					Image scaledImage = image.getImage().getScaledInstance(210, 210, Image.SCALE_SMOOTH);
//					ImageIcon scaledIcon = new ImageIcon(scaledImage);
//					imageLabel.setIcon(scaledIcon);
//					// + Cập nhật lại màu tên phòng
//					nameLabel.setForeground(Color.WHITE);
//					// + Cập nhật lại màu nền
//					blockPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#dedede"), 8));
//					blockPanel.setBackground(Color.decode("#dedede"));
//				}
//			}

			// + Thêm vào danh sách sản phẩm
			listPanelInRoomPanel.add(blockPanel);

			// + Cập nhật lại vị trí của từng sản phẩm
			if (x == 450) {
				x = 0;
				y += 225;
			} else {
				x += 225;
			}
		}

		listPanelInRoomPanel.setPreferredSize(new Dimension(675, (int) ((Math.ceil(1.0 * roomList.size() / 3) * 225))));
	}
}
