package domain.service;

import java.util.List;

import domain.model.HangDienMay;
import domain.model.HangHoa;
import domain.model.HangSanhSu;
import domain.model.HangThucPham;
import domain.observer.Publisher;
import persistence.HangHoaDAO;

public class HangHoaServiceImpl extends Publisher implements HangHoaService {
    private HangHoaDAO hangHoaDAO;

    public HangHoaServiceImpl(HangHoaDAO hangHoaDAO) {
        this.hangHoaDAO = hangHoaDAO;
    }

    @Override
    public void add(HangHoa hangHoa) {
        hangHoaDAO.add(hangHoa);
        notifySubcribers();
    }

    @Override
    public List<HangHoa> getAll(String hangHoaCheck) {
        return hangHoaDAO.getAll(hangHoaCheck);       
    }

    @Override
    public void delete(String hangHoaCheck, String maHang) {
        hangHoaDAO.delete(hangHoaCheck, maHang);
        notifySubcribers();
    }

    @Override
    public void update(HangHoa hangHoa, String maHang) {
        hangHoaDAO.update(hangHoa, maHang);
        notifySubcribers();
    }

    @Override
    public int tongSoLuong(String hangHoaCheck) {
        return hangHoaDAO.tongSoLuong(hangHoaCheck);
    }

}
