package persistence;
import domain.model.HangDienMay;
import domain.model.HangHoa;
import domain.model.HangSanhSu;
import domain.model.HangThucPham;

import java.util.List;

/**
 * Interface for persistence operations on HangHoa objects.
 */
public interface HangHoaDAO {
    void add(HangHoa hangHoa);
    void delete(String hangHoaCheck, String maHang);
    void update(HangHoa hangHoa, String maHang);
    int tongSoLuong(String hangHoaCheck);
    List<HangHoa> getAll(String hangHoaCheck);
}

