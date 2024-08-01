package domain.service;

import java.util.List;
import domain.model.HangHoa;

public interface HangHoaService  {
   void add(HangHoa hangHoa);
   void delete(String hangHoaCheck, String maHang);
   void update(HangHoa hangHoa, String maHang);
   int tongSoLuong(String hangHoaCheck);
   List<HangHoa> getAll(String hangHoaCheck);
}
