package Program;

import PL.Main_Frame;

public class Program {
	public static void main(String[] args) {
		Main_Frame mainFrame = Main_Frame.getInstance();

// 		try {
//			// Tên và đường dẫn file
//			String fileName = System.getProperty("user.dir") + File.separator + "src" + File.separator + "Others"
//					+ File.separator + "123.txt";
//
//			// Khởi tạo đối tượng File
//			File file = new File(fileName);
//
//			// Tạo file mới
//			if (file.createNewFile()) {
//				System.out.println("File đã được tạo thành công: " + file.getAbsolutePath());
//			} else {
//				File target = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator
//						+ "Images" + File.separator + "123.txt");
//				file.renameTo(target);
////				System.out.println("File đã tồn tại: " + file.getAbsolutePath());
//			}
//		} catch (IOException e) {
//			// Xử lý lỗi nếu xảy ra
//			System.out.println("Đã xảy ra lỗi khi tạo file.");
//			e.printStackTrace();
//		}

		// Đường dẫn thư mục bạn muốn duyệt
//		String directoryPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "Others";

		// Tạo đối tượng File đại diện cho thư mục
//		File folder = new File(directoryPath);

		// Lấy tất cả các file có trong thư mục
//		File[] files = folder.listFiles();

		// Duyệt qua tất cả các file hiện có
//		for (File file : files) {
//			// In tên file và kiểm tra xem đó là file hay thư mục
//			if (file.isFile()) {
//				System.out.println("File: " + file.getName());
//			} else if (file.isDirectory()) {
//				System.out.println("Thư mục: " + file.getName());
//			}
//			if (file.getName().contains("123")) {
//				File fileRemoved = new File(directoryPath + File.separator + file.getName());
//				fileRemoved.delete();
//			}
//		

//		for (File file : files) {
//			// In tên file và kiểm tra xem đó là file hay thư mục
//			if (file.isFile()) {
//				System.out.println("File: " + file.getName());
//			} else if (file.isDirectory()) {
//				System.out.println("Thư mục: " + file.getName());
//			}
//		}
	}
}
