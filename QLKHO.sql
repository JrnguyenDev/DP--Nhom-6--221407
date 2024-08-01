CREATE DATABASE QuanLyKho;
USE QuanLyKho;

CREATE TABLE HangHoa (
    MaHang VARCHAR(50) PRIMARY KEY,
    TenHang VARCHAR(255) NOT NULL,
    SoLuongTon INT CHECK (SoLuongTon >= 0),
    DonGia DOUBLE CHECK (DonGia > 0)
);

CREATE TABLE HangThucPham (
    MaHang VARCHAR(50) PRIMARY KEY,
    NgaySanXuat DATE NOT NULL,
    NgayHetHan DATE NOT NULL,
    NhaCungCap VARCHAR(255) NOT NULL,
    FOREIGN KEY (MaHang) REFERENCES HangHoa(MaHang)
);

CREATE TABLE HangDienMay (
    MaHang VARCHAR(50) PRIMARY KEY,
    ThoiGianBaoHanh INT CHECK (ThoiGianBaoHanh >= 0),
    CongSuat DOUBLE CHECK (CongSuat > 0),
    FOREIGN KEY (MaHang) REFERENCES HangHoa(MaHang)
);

CREATE TABLE HangSanhSu (
    MaHang VARCHAR(50) PRIMARY KEY,
    NhaSanXuat VARCHAR(255) NOT NULL,
    NgayNhapKho DATE NOT NULL,
    FOREIGN KEY (MaHang) REFERENCES HangHoa(MaHang)
);

-- Thêm dữ liệu vào bảng HangHoa
INSERT INTO HangHoa (MaHang, TenHang, SoLuongTon, DonGia) VALUES
('HH001', 'Gạo Lứt', 50, 15000),
('HH002', 'Máy Xay Sinh Tố', 20, 1200000),
('HH003', 'Bát Sứ Cao Cấp', 100, 50000),
('HH004', 'Thịt Bò Úc', 30, 250000),
('HH005', 'Tủ Lạnh Samsung', 10, 7000000),
('HH006', 'Chén Sứ Nhật', 80, 30000),
('HH007', 'Sữa Tươi Vinamilk', 200, 15000),
('HH008', 'Máy Giặt LG', 15, 5000000),
('HH009', 'Đĩa Sứ Trung Quốc', 150, 20000),
('HH010', 'Rau Sạch Đà Lạt', 100, 10000);


-- Thêm dữ liệu vào bảng HangThucPham
INSERT INTO HangThucPham (MaHang, NgaySanXuat, NgayHetHan, NhaCungCap) VALUES
('HH001', '2024-01-01', '2025-01-01', 'Công ty Gạo Sạch'),
('HH004', '2024-02-01', '2024-03-01', 'Công ty Thịt Sạch'),
('HH007', '2024-03-01', '2024-09-01', 'Công ty Vinamilk'),
('HH010', '2024-01-15', '2024-01-22', 'Trang trại Đà Lạt');


-- Thêm dữ liệu vào bảng HangDienMay
INSERT INTO HangDienMay (MaHang, ThoiGianBaoHanh, CongSuat) VALUES
('HH002', 24, 1.5),
('HH005', 36, 2.0),
('HH008', 24, 1.2);

-- Thêm dữ liệu vào bảng HangSanhSu
INSERT INTO HangSanhSu (MaHang, NhaSanXuat, NgayNhapKho) VALUES
('HH003', 'Công ty Sứ Việt', '2024-06-01'),
('HH006', 'Công ty Sứ Nhật', '2024-01-10'),
('HH009', 'Công ty Sứ Trung Quốc', '2024-03-05');


SELECT * FROM HangHoa;
SELECT * FROM HangThucPham;
SELECT * FROM HangDienMay;
SELECT * FROM HangSanhSu;

