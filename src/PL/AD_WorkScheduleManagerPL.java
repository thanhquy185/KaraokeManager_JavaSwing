package PL;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import javax.swing.Timer;

public class AD_WorkScheduleManagerPL extends JPanel {
	// Các Component
	private JLabel titleLabel;
	// - Các Component của Filter Panel
	private JLabel findLabel;
	private JTextField findInputTextField;
	private JButton findInformButton;
	private JLabel sortLabel;
	private Map<String, Boolean> sortCheckboxs;
	private JButton sortButton;
	private JLabel dayOfWeekLabel;
	private Map<String, Boolean> dayOfWeekCheckboxs;
	private JButton dayOfWeekButton;
	private JLabel timeOfDayLabel;
	private JComboBox<String> timeOfDayComboBox;
	private JLabel timeStartLabel;
	private JComboBox<String> timeStartComboBox;
	private JLabel timeEndLabel;
	private JComboBox<String> timeEndComboBox;
	private JLabel numbersOfRowLabel;
	private JTextField numbersOfRowTextField;
	private JButton filterApplyButton;
	private JButton filterResetButton;
	private JPanel filterPanel;
	// - Các Component của Data Panel
	private JButton addButton;
	private JButton updateButton;
	private JButton deleteButton;
	private JTable tableData;
	private JScrollPane tableScrollPane;
	private JPanel dataPanel;
	// - Các Component của Add Or Update Dialog
	private JLabel addOrUpdateIdLabel;
	private JComboBox addOrUpdateIdComboBox;
//		private JLabel addOrUpdateFullnameLabel;
//		private JTextField addOrUpdateFullnameTextField;
	private JLabel addOrUpdateDayOfWeekLabel;
	private JComboBox addOrUpdateDayOfWeekComboBox;
	private JLabel addOrUpdateTimeOfDayLabel;
	private JComboBox addOrUpdateTimeOfDayComboBox;
	private JLabel addOrUpdateTimeStartLabel;
	private JComboBox addOrUpdateTimeStartComboBox;
	private JLabel addOrUpdateTimeEndLabel;
	private JComboBox addOrUpdateTimeEndComboBox;
	private JLabel addOrUpdateTimeLabel;
	private JLabel addOrUpdateTimeDetailLabel;
	private JButton addOrUpdateButton;
	private JPanel addOrUpdateBlockPanel;
	private JDialog addOrUpdateDialog;

	// - Các thông tin cần có của Table Data và Table Scroll Pane
	// + Tiêu đề các cột
	private final String[] columns = { "Mã Nhân viên", "Buổi", "Ca", "Thời gian bắt đầu", "Thời gian kết thúc" };
	// + Chiều rộng các cột
	private final int[] widthColumns = { 270, 210, 210, 210, 210 };
	// + Dữ liệu
	private Object[][] datas = { { "NV0001", "Buổi hai", "Sáng", "06:00", "09:00" },
			{ "NV0001", "Buổi hai", "Chiều", "14:00", "17:00" }, { "NV0004", "Chủ nhật", "Sáng", "06:00", "09:00" } };
	// + Dòng hiện tại đang được chọn
	private int rowSelected = -1;
	// + Giá trị (true / false) khi "Xoá" dòng dữ liệu
	private Boolean[] valueSelected = { null };

	public AD_WorkScheduleManagerPL() {
		// <===== Cấu trúc Title Label =====>
		// - Tuỳ chỉnh Title Label
		titleLabel = CommonPL.getTitleLabel("Lịch làm việc", Color.BLACK, CommonPL.getFontTitle(),
				SwingConstants.CENTER, SwingConstants.CENTER);
		titleLabel.setBounds(30, 0, 1140, 115);
		// <==================== ====================>

		// <===== Cấu trúc của Filter Panel =====>
		// - Tuỳ chỉnh Find Input Label
		findLabel = CommonPL.getParagraphLabel("Tìm kiếm", Color.BLACK, CommonPL.getFontParagraphPlain());
		findLabel.setBounds(15, 15, 90, 24);

		// - Tuỳ chỉnh Find Input Inform Button
		findInformButton = CommonPL.getQuestionIconForm("?", "Thông tin bạn có thể tìm kiếm",
				"Bạn có thể tìm kiếm bằng các thông tin như: Mã nhân viên, Nhân viên lập phiếu", Color.BLACK,
				CommonPL.getFontQuestionIcon());
		findInformButton.setBounds(110, 15, 24, 24);

		// - Tuỳ chỉnh Find Input Text Field
		findInputTextField = new CommonPL.CustomTextField(0, 20, 20, "Nhập thông tin", Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		findInputTextField.setBounds(15, 45, 360, 40);

		// - Tuỳ chỉnh Sort Label
		sortLabel = CommonPL.getParagraphLabel("Sắp xếp", Color.BLACK, CommonPL.getFontParagraphPlain());
		sortLabel.setBounds(390, 15, 235, 24);

		// - Tuỳ chỉnh Sort Checkboxs
		String[] sorts = new String[] { "Mã nhân viên tăng dần", "Mã nhân viên giảm dần", "Buổi tăng dần",
				"Buổi giảm dần", "Ca tăng dần", "Ca giảm dần", "Thời gian bắt đầu tăng dần",
				"Thời gian bắt đầu giảm dần", "Thời gian kết thúc tăng dần", "Thời gian kết thúc giảm dần" };
		sortCheckboxs = CommonPL.getMapHasValues(sorts);

		// - Tuỳ chỉnh Sort Button
		sortButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(sortCheckboxs, sorts[0], Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		sortButton.setBounds(390, 45, 360, 40);

		// - Tuỳ chỉnh Day Of Week Label
		dayOfWeekLabel = CommonPL.getParagraphLabel("Buổi", Color.BLACK, CommonPL.getFontParagraphPlain());
		dayOfWeekLabel.setBounds(765, 15, 90, 24);

		// - Tuỳ chỉnh Day Of Week Checkboxs (Đặc biệt)
		String[] daysOfWeek = new String[] { "Buổi hai", "Buổi ba", "Buổi bốn", "Buổi năm", "Buổi sáu", "Buổi bảy",
				"Chủ nhật" };
		dayOfWeekCheckboxs = new LinkedHashMap<>();
		for (int i = 0; i < daysOfWeek.length; i++) {
			dayOfWeekCheckboxs.put(daysOfWeek[i], true);
		}

		// - Tuỳ chỉnh Day Of Week Button
		dayOfWeekButton = CommonPL.ButtonHasCheckboxs.createButtonHasCheckboxs(dayOfWeekCheckboxs,
				"Đã chọn nhiều tiêu chí", Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		dayOfWeekButton.setBounds(765, 45, 360, 40);

		// - Tuỳ chỉnh Time Of Day Label
		timeOfDayLabel = CommonPL.getParagraphLabel("Ca", Color.BLACK, CommonPL.getFontParagraphPlain());
		timeOfDayLabel.setBounds(15, 100, 90, 24);

		// - Tuỳ chỉnh Time Of Day ComboBox
		String[] timesOfDay = { "Tất cả", "Sáng", "Chiều", "Tối", "Đêm" };
		Vector<String> timesOfDayVector = CommonPL.getVectorHasValues(timesOfDay);
		timeOfDayComboBox = CommonPL.CustomComboBox(timesOfDayVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		timeOfDayComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		timeOfDayComboBox.setForeground(Color.BLACK);
		timeOfDayComboBox.setBounds(15, 130, 235, 40);

		// - Tuỳ chỉnh Time Start Label
		timeStartLabel = CommonPL.getParagraphLabel("Thời gian bắt đầu", Color.BLACK, CommonPL.getFontParagraphPlain());
		timeStartLabel.setBounds(265, 100, 235, 24);

		// - Tuỳ chỉnh Time Start ComboBox
		String[] timeStarts = { "Chọn Thời gian" };
		Vector<String> timeStartsVector = CommonPL.getVectorHasValues(timeStarts);
		timeStartComboBox = CommonPL.CustomComboBox(timeStartsVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		timeStartComboBox.setBounds(265, 130, 235, 40);

		// - Tuỳ chỉnh Time End Label
		timeEndLabel = CommonPL.getParagraphLabel("Thời gian kết thúc", Color.BLACK, CommonPL.getFontParagraphPlain());
		timeEndLabel.setBounds(515, 100, 235, 24);

		// - Tuỳ chỉnh Time End ComboBox
		String[] timeEnds = { "Chọn Thời gian" };
		Vector<String> timeEndsVector = CommonPL.getVectorHasValues(timeEnds);
		timeEndComboBox = CommonPL.CustomComboBox(timeEndsVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		timeEndComboBox.setBounds(515, 130, 235, 40);

		// - Thiết lập sự kiện thay đổi thời gian theo Ca
		renderTimesAfterTimesOfDaySelected(timeOfDayComboBox, timeStartComboBox, timeEndComboBox);

		// - Tuỳ chỉnh Numbers Of Row Label
		numbersOfRowLabel = CommonPL.getParagraphLabel("Số dòng:", Color.BLACK, new Font("Airal", Font.PLAIN, 14));
		numbersOfRowLabel.setBounds(765, 100, 62, 24);

		// - Tuỳ chỉnh Numbers Of Row Text Field
		numbersOfRowTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập số dòng", Color.LIGHT_GRAY, Color.BLACK,
				new Font("Airal", Font.PLAIN, 14));
		numbersOfRowTextField.setBounds(832, 100, 293, 24);

		// - Tuỳ chỉnh Filter Apply Button
		filterApplyButton = CommonPL.getLastButtonForm("Lọc", Color.decode("#007bff"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		filterApplyButton.setBounds(765, 130, 170, 40);

		// - Tuỳ chỉnh Filter Reset Button
		filterResetButton = CommonPL.getLastButtonForm("Đặt lại", Color.decode("#f44336"), Color.WHITE,
				CommonPL.getFontParagraphBold());
		filterResetButton.setBounds(955, 130, 170, 40);

		// - Tuỳ chỉnh Filter Panel
		filterPanel = new CommonPL.RoundedPanel(12);
		filterPanel.setLayout(null);
		filterPanel.setBounds(30, 115, 1140, 185);
		filterPanel.setBackground(Color.WHITE);
		filterPanel.add(findLabel);
		filterPanel.add(findInformButton);
		filterPanel.add(findInputTextField);
		filterPanel.add(sortLabel);
		filterPanel.add(sortButton);
		filterPanel.add(dayOfWeekLabel);
		filterPanel.add(dayOfWeekButton);
		filterPanel.add(timeOfDayLabel);
		filterPanel.add(timeOfDayComboBox);
		filterPanel.add(timeStartLabel);
		filterPanel.add(timeStartComboBox);
		filterPanel.add(timeEndLabel);
		filterPanel.add(timeEndComboBox);
//		filterPanel.add(numbersOfRowLabel);
//		filterPanel.add(numbersOfRowTextField);
		filterPanel.add(filterResetButton);
		filterPanel.add(filterApplyButton);
		// <==================== ====================>

		// <===== Cấu trúc của Data Panel =====>
		// - Tuỳ chỉnh Add Or Update Button
		addButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "add-icon.png", "Thêm", Color.BLACK, Color.decode("#699f4c"),
				Color.BLACK, Color.decode("#699f4c"), CommonPL.getFontParagraphBold());
		addButton.setBounds(15, 15, 210, 40);

		// - Tuỳ chỉnh Update Button
		updateButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "update-icon.png", "Thay đổi", Color.BLACK,
				Color.decode("#bf873e"), Color.BLACK, Color.decode("#bf873e"), CommonPL.getFontParagraphBold());
		updateButton.setBounds(240, 15, 210, 40);

		// - Tuỳ chỉnh Delete Button
		deleteButton = CommonPL.getButtonHasIcon(210, 40, 30, 30, 20, 5,
				CommonPL.getMiddlePathToShowIcon() + "delete-icon.png", "Xoá", Color.BLACK, Color.decode("#9f4d4d"),
				Color.BLACK, Color.decode("#9f4d4d"), CommonPL.getFontParagraphBold());
		deleteButton.setBounds(465, 15, 210, 40);

		// - Tuỳ chỉnh Table Scroll Pane
		tableData = CommonPL.createTableData(columns, widthColumns, datas, "work schedule manager");
		tableScrollPane = CommonPL.createScrollPane(true, false, tableData);
		tableScrollPane.setBounds(15, 70, 1110, 400);

		// - Tuỳ chỉnh Data Pane;
		dataPanel = new CommonPL.RoundedPanel(12);
		dataPanel.setLayout(null);
		dataPanel.setBackground(Color.WHITE);
		dataPanel.setBounds(30, 330, 1140, 485);
		dataPanel.add(addButton);
		dataPanel.add(updateButton);
		dataPanel.add(deleteButton);
		dataPanel.add(tableScrollPane);
		// <==================== ====================>

		// Đĩnh nghĩa tính chất cho Employee Schedule Manager PL
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
			showAddOrUpdateDialog("Thêm Lịch làm việc", "Thêm", new Vector<Object>());
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

				showAddOrUpdateDialog("Thay đổi Lịch làm việc", "Thay đổi", currentObject);
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần thay đổi");
			}
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});
		deleteButton.addActionListener(e -> {
			if (rowSelected != -1) {
				CommonPL.createSelectionsDialog("Thông báo lựa chọn", "Có chắc chắn muốn xoá dòng dữ liệu này ?",
						valueSelected);
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", "Vui lòng chọn 1 dòng dữ liệu cần xoá");
			}
			rowSelected = -1;
			valueSelected[0] = false;
			tableData.clearSelection();
		});
	}

	// Hàm cập nhật thời gian tương ứng cho Thời gian bắt đầu hoặc Thời gian kết
	// thúc khi chọn
	// Ca
	private void renderTimesAfterTimesOfDaySelected(JComboBox<String> timeOfDay, JComboBox<String> timeStart,
			JComboBox<String> timeEnd) {
		// Mảng chứa thời gian theo từng buổi: "Buổi sáng", "Buổi chiều" và "Buổi tối"
		String[] timesInMorning = new String[] { "06:00", "06:15", "06:30", "06:45", "07:00", "07:15", "07:30", "07:45",
				"08:00", "08:15", "08:30", "08:45", "09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30",
				"10:45", "11:00", "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45" };
		String[] timesInAfternoon = new String[] { "13:00", "13:15", "13:30", "13:45", "14:00", "14:15", "14:30",
				"14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "17:00", "17:15", "17:30", "17:45" };
		String[] timesInEvenning = new String[] { "18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30",
				"19:45", "20:00", "20:15", "20:30", "20:45", "21:00", "21:15", "21:30", "21:45", "22:00", "22:15",
				"22:30", "22:45", "23:00", "23:15", "23:30", "23:45" };
		String[] timesInAfterEvenning = new String[] { "24:00", "24:15", "24:30", "24:45", "01:00", "01:15", "01:30",
				"01:45", "02:00", "02:15", "02:30", "02:45", "03:00", "03:15", "03:30", "03:45", "04:00", "04:15",
				"04:30", "04:45", "05:00" };

		// Ban đầu Ca là "Tất cả"
		for (String s : timesInMorning) {
			timeStart.addItem(s);
			timeEnd.addItem(s);
		}
		for (String s : timesInAfternoon) {
			timeStart.addItem(s);
			timeEnd.addItem(s);
		}
		for (String s : timesInEvenning) {
			timeStart.addItem(s);
			timeEnd.addItem(s);
		}
		for (String s : timesInAfterEvenning) {
			timeStart.addItem(s);
			timeEnd.addItem(s);
		}

		// Sau khi thay đổi Ca
		timeOfDay.addActionListener(e -> {
			// Ca đang được chọn hiện tại
			String valueSelected = (String) timeOfDay.getSelectedItem();

			// Nếu chọn đúng dữ liệu
			if (valueSelected.equals("Chọn Ca") || valueSelected.equals("Tất cả") || valueSelected.equals("Sáng")
					|| valueSelected.equals("Chiều") || valueSelected.equals("Tối") || valueSelected.equals("Đêm")) {
				// Tạm thời gỡ bỏ
				ActionListener[] listenersStart = timeStart.getActionListeners();
				for (ActionListener listener : listenersStart) {
					timeStart.removeActionListener(listener);
				}
				ActionListener[] listenersEnd = timeEnd.getActionListeners();
				for (ActionListener listener : listenersEnd) {
					timeEnd.removeActionListener(listener);
				}

				// Xoá các dữ liệu hiện có
				timeStart.removeAllItems();
				timeEnd.removeAllItems();

				// Định dạng lại giao diện
				timeStart.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				timeStart.setForeground(Color.LIGHT_GRAY);
				timeEnd.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
				timeEnd.setForeground(Color.LIGHT_GRAY);

				// Luôn thêm giá trị "Chọn Thời gian" để tránh gặp lỗi
				timeStart.addItem("Chọn Thời gian");
				timeEnd.addItem("Chọn Thời gian");

				// Thêm lại các ActionListener
				for (ActionListener listener : listenersStart) {
					timeStart.addActionListener(listener);
				}
				for (ActionListener listener : listenersEnd) {
					timeEnd.addActionListener(listener);
				}
			}

			// Cập nhật các thời gian tương ứng theo ca được chọn
			if (valueSelected.equals("Tất cả")) {
				for (String s : timesInMorning) {
					timeStart.addItem(s);
					timeEnd.addItem(s);
				}
				for (String s : timesInAfternoon) {
					timeStart.addItem(s);
					timeEnd.addItem(s);
				}
				for (String s : timesInEvenning) {
					timeStart.addItem(s);
					timeEnd.addItem(s);
				}
				for (String s : timesInAfterEvenning) {
					timeStart.addItem(s);
					timeEnd.addItem(s);
				}
			} else if (valueSelected.equals("Sáng")) {
				for (String s : timesInMorning) {
					timeStart.addItem(s);
					timeEnd.addItem(s);
				}
			} else if (valueSelected.equals("Chiều")) {
				for (String s : timesInAfternoon) {
					timeStart.addItem(s);
					timeEnd.addItem(s);
				}
			} else if (valueSelected.equals("Tối")) {
				for (String s : timesInEvenning) {
					timeStart.addItem(s);
					timeEnd.addItem(s);
				}
			} else if (valueSelected.equals("Đêm")) {
				for (String s : timesInAfterEvenning) {
					timeStart.addItem(s);
					timeEnd.addItem(s);
				}
			}
		});
	}

	private JTextField addOrUpdateIdTextField;

	// Hàm tạo một modal cho việc "Thêm" hoặc "Thay đổi" Lịch làm việc
	private void showAddOrUpdateDialog(String title, String button, Vector<Object> object) {
		// <===== Các Component của Add Or Update Block Panel =====>
		// - Tuỳ chỉnh Add Or Update Id Label
		addOrUpdateIdLabel = CommonPL.getParagraphLabel("Mã Nhân viên", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdLabel.setBounds(20, 10, 460, 40);

		// - Tuỳ chỉnh Add Or Update Id Text Field
		addOrUpdateIdTextField = new CommonPL.CustomTextField(0, 0, 0, "Nhập Mã nhân viên", Color.LIGHT_GRAY,
				Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdTextField.setBounds(20, 50, 460, 40);
		addOrUpdateIdTextField.setVisible(false);

		// - Tuỳ chỉnh Add Or Update Id ComboBox
		String[] ids = { "Chọn Mã", "Mã Nhân viên 1" };
		Vector<String> idsVector = CommonPL.getVectorHasValues(ids);
		addOrUpdateIdComboBox = CommonPL.CustomComboBox(idsVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateIdComboBox.setBounds(20, 50, 460, 40);

		// - Tuỳ chỉnh Add Or Update Day Of Week Label
		addOrUpdateDayOfWeekLabel = CommonPL.getParagraphLabel("Buổi", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateDayOfWeekLabel.setBounds(20, 100, 220, 40);

		// - Tuỳ chỉnh Add Or Update Day Of Week ComboBox
		String[] daysOfWeek = { "Chọn Buổi", "Buổi hai", "Buổi ba", "Buổi bốn", "Buổi năm", "Buổi sáu", "Buổi bảy",
				"Chủ nhật" };
		Vector<String> daysOfWeekVector = CommonPL.getVectorHasValues(daysOfWeek);
		addOrUpdateDayOfWeekComboBox = CommonPL.CustomComboBox(daysOfWeekVector, Color.WHITE, Color.LIGHT_GRAY,
				Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateDayOfWeekComboBox.setBounds(20, 140, 220, 40);

		// - Tuỳ chỉnh Add Or Update Time Of Day Label
		addOrUpdateTimeOfDayLabel = CommonPL.getParagraphLabel("Ca", Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateTimeOfDayLabel.setBounds(260, 100, 220, 40);

		// - Tuỳ chỉnh Add Or Update Time Of Day ComboBox
		String[] timesOfDay = { "Chọn Ca", "Sáng", "Chiều", "Tối", "Đêm" };
		Vector<String> timesOfDayVector = CommonPL.getVectorHasValues(timesOfDay);
		addOrUpdateTimeOfDayComboBox = CommonPL.CustomComboBox(timesOfDayVector, Color.WHITE, Color.LIGHT_GRAY,
				Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateTimeOfDayComboBox.setBounds(260, 140, 220, 40);

		// - Tuỳ chỉnh Add Or Update Time Start Label
		addOrUpdateTimeStartLabel = CommonPL.getParagraphLabel("Thời gian bắt đầu", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateTimeStartLabel.setBounds(20, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Time Start ComboBox
		String[] timeStarts = { "Chọn Thời gian" };
		Vector<String> timeStartsVector = CommonPL.getVectorHasValues(timeStarts);
		addOrUpdateTimeStartComboBox = CommonPL.CustomComboBox(timeStartsVector, Color.WHITE, Color.LIGHT_GRAY,
				Color.BLACK, Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateTimeStartComboBox.setBounds(20, 230, 220, 40);

		// - Tuỳ chỉnh Add Or Update Time End Label
		addOrUpdateTimeEndLabel = CommonPL.getParagraphLabel("Thời gian kết thúc", Color.BLACK,
				CommonPL.getFontParagraphPlain());
		addOrUpdateTimeEndLabel.setBounds(260, 190, 220, 40);

		// - Tuỳ chỉnh Add Or Update Time End ComboBox
		String[] timeEnds = { "Chọn Thời gian" };
		Vector<String> timeEndsVector = CommonPL.getVectorHasValues(timeEnds);
		addOrUpdateTimeEndComboBox = CommonPL.CustomComboBox(timeEndsVector, Color.WHITE, Color.LIGHT_GRAY, Color.BLACK,
				Color.WHITE, Color.LIGHT_GRAY, Color.LIGHT_GRAY, Color.BLACK, CommonPL.getFontParagraphPlain());
		addOrUpdateTimeEndComboBox.setBounds(260, 230, 220, 40);

		// - Thiết lập sự kiện thay đổi thời gian theo Ca
		renderTimesAfterTimesOfDaySelected(addOrUpdateTimeOfDayComboBox, addOrUpdateTimeStartComboBox,
				addOrUpdateTimeEndComboBox);

		// - Tuỳ chỉnh Add Or Update Status Label
		addOrUpdateTimeLabel = CommonPL.getParagraphLabel("Cập nhật gần đây:", Color.GRAY,
				new Font("Arial", Font.ITALIC, 18));
		addOrUpdateTimeLabel.setBounds(20, 280, 148, 40);

		// - Tuỳ chỉnh Add Or Update Time Detail Label
		addOrUpdateTimeDetailLabel = CommonPL.getTitleLabel("yyyy-MM-dd HH:mm:ss", Color.GRAY,
				new Font("Arial", Font.ITALIC, 18), SwingConstants.RIGHT, SwingConstants.CENTER);
		addOrUpdateTimeDetailLabel.setBounds(173, 280, 307, 40);
		// -- Tạo Timer cập nhật thời gian
		Timer timer = new Timer(1000, e -> {
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = currentDateTime.format(formatter);
			addOrUpdateTimeDetailLabel.setText(formattedDateTime);
		});
		timer.start();

		// - Tuỳ chỉnh Add Or Update Button
		addOrUpdateButton = CommonPL.getButtonDefaultForm(button, CommonPL.getFontParagraphBold());
		addOrUpdateButton.setBounds(20, 320, 460, 40);
		SwingUtilities.invokeLater(() -> addOrUpdateButton.requestFocusInWindow());

		// Khi "Thay đổi" một Lịch làm việc
		if (title.equals("Thay đổi Lịch làm việc") && button.equals("Thay đổi") && object.size() != 0) {
			// - Gán dữ liệu là "Mã nhân viên"
			addOrUpdateIdComboBox.setVisible(false);
			addOrUpdateIdTextField.setText((String) object.get(0));
			addOrUpdateIdTextField.setEnabled(false);
			addOrUpdateIdTextField.setBackground(Color.decode("#dedede"));
			addOrUpdateIdTextField.setVisible(true);

			// - Gán dữ liệu là "Buổi"
			addOrUpdateDayOfWeekComboBox.setSelectedItem((String) object.get(1));
			addOrUpdateDayOfWeekComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

			// - Gán dữ liệu là "Ca"
			addOrUpdateTimeOfDayComboBox.setSelectedItem((String) object.get(2));
			addOrUpdateTimeOfDayComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

			// - Gán dữ liệu là "Thời gian bắt đầu"
			addOrUpdateTimeStartComboBox.setSelectedItem((String) object.get(3));
			addOrUpdateTimeStartComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

			// - Gán dữ liệu là "Thời gian kết thúc"
			addOrUpdateTimeEndComboBox.setSelectedItem((String) object.get(4));
			addOrUpdateTimeEndComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

			// - Gán dữ liệu là "Thời gian cập nhật gần đây"
//									addOrUpdateTimeDetailLabel.setText((String) object.get(11));
		}

		// - Tuỳ chỉnh Add Or Update Block Panel
		addOrUpdateBlockPanel = new JPanel();
		addOrUpdateBlockPanel.setLayout(null);
		addOrUpdateBlockPanel.setBounds(0, 0, 500, 410);
		addOrUpdateBlockPanel.setBackground(Color.WHITE);
		addOrUpdateBlockPanel.add(addOrUpdateIdLabel);
		addOrUpdateBlockPanel.add(addOrUpdateIdTextField);
		addOrUpdateBlockPanel.add(addOrUpdateIdComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateDayOfWeekLabel);
		addOrUpdateBlockPanel.add(addOrUpdateDayOfWeekComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateTimeOfDayLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTimeOfDayComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateTimeStartLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTimeStartComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateTimeEndLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTimeEndComboBox);
		addOrUpdateBlockPanel.add(addOrUpdateTimeLabel);
		addOrUpdateBlockPanel.add(addOrUpdateTimeDetailLabel);
		addOrUpdateBlockPanel.add(addOrUpdateButton);
		// <==================== ====================>

		// Định nghĩa tính chất cho Add Or Update Dialog
		addOrUpdateDialog = new JDialog();
		addOrUpdateDialog.setTitle(title);
		addOrUpdateDialog.setLayout(null);
		addOrUpdateDialog.setSize(500, 410);
		addOrUpdateDialog.setResizable(false);
		addOrUpdateDialog.setLocationRelativeTo(null);
		addOrUpdateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		addOrUpdateDialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				addOrUpdateDialog.dispose(); // Đóng Dialog khi mất focus (nhấn ngoài)
			}
		});
		addOrUpdateDialog.add(addOrUpdateBlockPanel);
		addOrUpdateDialog.setModal(true);
		addOrUpdateDialog.setVisible(true);
	}
}
