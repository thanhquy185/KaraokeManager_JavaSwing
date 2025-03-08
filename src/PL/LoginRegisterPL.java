package PL;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import BLL.AccountBLL;

public class LoginRegisterPL extends JPanel {
	// Các Font
	Font fontTitle = new Font("Arial", Font.BOLD, 28);
	Font fontInput = new Font("Arial", Font.PLAIN, 20);
	Font fontButton = new Font("Arial", Font.BOLD, 20);
	Font fontButtonSmall = new Font("Arial", Font.BOLD, 14);
	// Các Component
	private JPanel blockPanel;
	// - Các Component của Login Form
	private JLabel loginLabel;
	private JPanel linePanel;
	private JLabel usernameLabel;
	private JTextField usernameTextField;
	private JLabel passwordLabel;
	private JPasswordField passwordPasswordField;
	private JButton forgotPasswordButton;
	private JButton loginButton;
	private JPanel loginPanel;

	public LoginRegisterPL() {
		// // <===== Cấu trúc của Image Label =====>
//		imageLabel = CommonPL.getImageLabel(CommonPL.getScreenWidthByOwner() - 440, CommonPL.getScreenHeightByOwner(),
//				CommonPL.getMiddlePathToShowProductImage() + String.valueOf(datas[i][5]));
		// <==================== ====================>

		// // <===== Cấu trúc của Block Panel =====>
		// - Tuỳ chỉnh Login Label
		loginLabel = CommonPL.getTitleLabel("Đăng nhập", Color.BLACK, fontTitle, SwingConstants.CENTER,
				SwingConstants.CENTER);
		loginLabel.setBounds(0, 0, 440, 60);

		// - Tuỳ chỉnh Line Panel
		linePanel = new CommonPL.RoundedPanel(8);
		linePanel.setLayout(null);
		linePanel.setBackground(Color.BLACK);
		linePanel.setBounds(20, 60, 400, 5);

		// - Tuỳ chỉnh Username Label
		usernameLabel = CommonPL.getParagraphLabel("Tên tài khoản", Color.BLACK, fontInput);
		usernameLabel.setBounds(20, 80, 400, 40);

		// - Tuỳ chỉnh Username Text Field
		usernameTextField = new CommonPL.CustomTextField(0, 8, 8, "Nhập Tên tài khoản", Color.LIGHT_GRAY, Color.BLACK,
				fontInput);
		usernameTextField.setBounds(20, 120, 400, 40);

		// - Tuỳ chỉnh Password Label
		passwordLabel = CommonPL.getParagraphLabel("Mật khẩu", Color.BLACK, fontInput);
		passwordLabel.setBounds(20, 170, 400, 40);

		// - Tuỳ chỉnh Password Text Field
		passwordPasswordField = new CommonPL.CustomPasswordField(0, 8, 8, "Nhập Mật khẩu", Color.LIGHT_GRAY,
				Color.BLACK, fontInput);
		passwordPasswordField.setBounds(20, 210, 400, 40);

		// - Tuỳ chỉnh Forgot Password Button
//		forgotPasswordButton = new JButton("Bạn đã quên mật khẩu ?");
//		forgotPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
//		forgotPasswordButton.setContentAreaFilled(true);
//		forgotPasswordButton.setOpaque(false);
//		forgotPasswordButton.setBorderPainted(false);
//		forgotPasswordButton.setForeground(Color.decode("#dedede"));
//		forgotPasswordButton.setFont(fontButtonSmall);
//		forgotPasswordButton.setBounds(20, 280, 200, 40);

		// - Tuỳ chỉnh Login Button
		loginButton = CommonPL.getRoundedBorderButton(8, "Đăng nhập", Color.decode("#42A5F5"), Color.WHITE, fontButton);
		loginButton.setBounds(20, 300, 400, 40);
		SwingUtilities.invokeLater(() -> loginButton.requestFocusInWindow());
		loginButton.addActionListener(e -> {
			// Khai báo biến để kiểm tra thông tin tài khoản
			AccountBLL accountBLL = new AccountBLL();

			// Thông tin tài khoản đã nhập
			String usernameValue = !usernameTextField.getText().equals("Nhập Tên tài khoản")
					? usernameTextField.getText()
					: null;
			String passwordValue = !new String(passwordPasswordField.getPassword()).equals("Nhập Mật khẩu")
					? new String(passwordPasswordField.getPassword())
					: null;
			;

			// Kiểm tra, nếu sai thì thông báo lỗi
			String inform = accountBLL.loginApp(usernameValue, passwordValue);
			if (inform.equals("Có thể đăng nhập")) {
				// Khai báo các đối tượng để chuyển trang
				FramePL mainFrame = new FramePL();
				AdminCardPL adminCardPL = new AdminCardPL();
				mainFrame.changeFrameContent(adminCardPL);
			} else {
				CommonPL.createErrorDialog("Thông báo lỗi", inform);
			}
		});

		// - Tuỳ chỉnh Login Panel
		loginPanel = new CommonPL.RoundedPanel(12);
		loginPanel.setLayout(null);
		loginPanel.setBackground(Color.WHITE);
		loginPanel.setBounds((CommonPL.getScreenWidthByOwner() - 440) / 2,
				(CommonPL.getScreenHeightByOwner() - 360) / 2 - 20, 440, 360);
		loginPanel.add(loginLabel);
		loginPanel.add(linePanel);
		loginPanel.add(usernameLabel);
		loginPanel.add(usernameTextField);
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordPasswordField);
//		loginPanel.add(forgotPasswordButton);
		loginPanel.add(loginButton);

		// - Tuỳ chỉnh Block Panel
		blockPanel = new JPanel();
		blockPanel.setLayout(null);
		blockPanel.setBackground(Color.decode("#1976D2"));
		blockPanel.setBounds(0, 0, CommonPL.getScreenWidthByOwner(), CommonPL.getScreenHeightByOwner());
		blockPanel.add(loginPanel);
		// <==================== ====================>

		// Đĩnh nghĩa tính chất cho PL
		this.setSize(CommonPL.getScreenWidthByOwner(), CommonPL.getScreenHeightByOwner());
		this.setLayout(null);
		this.add(blockPanel);

	}
}
