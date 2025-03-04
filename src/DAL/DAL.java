package DAL;

import java.util.ArrayList;

public interface DAL<Object> {
	// Methods
	// - Hàm thêm một đối tượng
	public int insert(Object t);

	// - Hàm cập nhật một đối tượng
	public int update(Object t);

	// - Hàm khoá một đối tượng
	public int lock(Object t);

	// - Hàm lấy ra danh sách các đối tượng
	public ArrayList<Object> selectAll();

	// - Hàm lấy ra danh sách các đối tượng dựa trên 1 điều kiện
	public ArrayList<Object> selectAllByCondition(String[] join, String condition, String order);

	// - Hàm lấy ra một đối tượng dựa trên mã đối tượng đó
	public Object selectOneById(String id);
}
