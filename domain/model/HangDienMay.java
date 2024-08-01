package domain.model;

public class HangDienMay extends HangHoa {
    private double congSuat;
    private int thoiGianBaoHanh;

    
    public HangDienMay(String maHang, String tenHang, double donGia, int soLuongTon, double congSuat, int thoiGianBaoHanh) {
        super(maHang, tenHang, donGia, soLuongTon);
        this.congSuat = congSuat;
        this.thoiGianBaoHanh = thoiGianBaoHanh;
    }

    public double getCongSuat() {
        return congSuat;
    }

    public void setCongSuat(double congSuat) {
        this.congSuat = congSuat;
    }

    public int getThoiGianBaoHanh() {
        return thoiGianBaoHanh;
    }

    public void setThoiGianBaoHanh(int thoiGianBaoHanh) {
        this.thoiGianBaoHanh = thoiGianBaoHanh;
    }
}
