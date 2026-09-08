DROP DATABASE IF EXISTS codegym_shop;
CREATE DATABASE codegym_shop CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE codegym_shop;

-- Bảng sản phẩm
CREATE TABLE san_pham (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    ten_san_pham  VARCHAR(150) NOT NULL,
    gia           DOUBLE NOT NULL,
    giam_gia      INT NOT NULL DEFAULT 0,   -- % giảm giá: 5,10,15,20
    ton_kho       INT NOT NULL DEFAULT 0
);

-- Bảng nhân viên
CREATE TABLE nhan_vien (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    ten_nhan_vien  VARCHAR(150) NOT NULL,
    ngay_sinh      DATE NOT NULL,
    dia_chi        VARCHAR(255)
);

-- Bảng khách hàng
CREATE TABLE khach_hang (
    id             INT AUTO_INCREMENT PRIMARY KEY,
    ten_khach_hang VARCHAR(150) NOT NULL,
    ngay_sinh      DATE,
    dien_thoai     VARCHAR(20),
    dia_chi        VARCHAR(255),
    email          VARCHAR(150)
);

-- Bảng đơn hàng
CREATE TABLE don_hang (
    id                    INT AUTO_INCREMENT PRIMARY KEY,
    phuong_thuc_thanh_toan VARCHAR(50) NOT NULL,
    khach_hang_id         INT NOT NULL,
    nhan_vien_id          INT NOT NULL,
    ngay_dat_hang         DATE NOT NULL,
    ngay_giao_hang        DATE,
    dia_chi_giao_hang     VARCHAR(255),
    CONSTRAINT fk_donhang_khachhang FOREIGN KEY (khach_hang_id) REFERENCES khach_hang(id),
    CONSTRAINT fk_donhang_nhanvien FOREIGN KEY (nhan_vien_id) REFERENCES nhan_vien(id)
);

-- Bảng chi tiết đơn hàng
CREATE TABLE chi_tiet_don_hang (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    don_hang_id   INT NOT NULL,
    san_pham_id   INT NOT NULL,
    so_luong      INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_ctdh_donhang FOREIGN KEY (don_hang_id) REFERENCES don_hang(id),
    CONSTRAINT fk_ctdh_sanpham FOREIGN KEY (san_pham_id) REFERENCES san_pham(id)
);

-- DỮ LIỆU MẪU (>= 10 record / bảng)
INSERT INTO san_pham (ten_san_pham, gia, giam_gia, ton_kho) VALUES
('Iphone X', 1000, 10, 20),
('Samsung Note 10', 1200, 5, 60),
('Samsung A5', 400, 5, 20),
('Iphone 10', 600, 10, 40),
('Iphone 8 Plus 128G', 500, 10, 10),
('Oppo F10', 350, 10, 50),
('Xiaomi Redmi Note 12', 300, 15, 30),
('Samsung S22', 900, 5, 25),
('Iphone 13', 1100, 5, 15),
('Oppo Reno 8', 450, 20, 35),
('Vivo V25', 380, 15, 22),
('Nokia G21', 220, 20, 45);

INSERT INTO nhan_vien (ten_nhan_vien, ngay_sinh, dia_chi) VALUES
('Nguyen Van A', '1995-01-10', 'Ha Noi'),
('Tran Thi B', '1992-03-22', 'Hai Phong'),
('Le Van C', '1998-07-15', 'Da Nang'),
('Pham Thi D', '1990-11-02', 'Ho Chi Minh'),
('Hoang Van E', '1993-05-19', 'Can Tho'),
('Vu Thi F', '1996-09-08', 'Ha Noi'),
('Do Van G', '1994-12-30', 'Bac Ninh'),
('Ngo Thi H', '1991-04-25', 'Hue'),
('Dang Van I', '1997-06-14', 'Nghe An'),
('Bui Thi K', '1999-02-18', 'Thanh Hoa');

INSERT INTO khach_hang (ten_khach_hang, ngay_sinh, dien_thoai, dia_chi, email) VALUES
('Khach A', '2000-01-01', '0900000001', 'Ha Noi', 'khacha@gmail.com'),
('Khach B', '1999-02-02', '0900000002', 'Hai Phong', 'khachb@gmail.com'),
('Khach C', '1998-03-03', '0900000003', 'Da Nang', 'khachc@gmail.com'),
('Khach D', '1997-04-04', '0900000004', 'Ho Chi Minh', 'khachd@gmail.com'),
('Khach E', '1996-05-05', '0900000005', 'Can Tho', 'khache@gmail.com'),
('Khach F', '1995-06-06', '0900000006', 'Ha Noi', 'khachf@gmail.com'),
('Khach G', '1994-07-07', '0900000007', 'Bac Ninh', 'khachg@gmail.com'),
('Khach H', '1993-08-08', '0900000008', 'Hue', 'khachh@gmail.com'),
('Khach I', '1992-09-09', '0900000009', 'Nghe An', 'khachi@gmail.com'),
('Khach K', '1991-10-10', '0900000010', 'Thanh Hoa', 'khachk@gmail.com');

INSERT INTO don_hang (phuong_thuc_thanh_toan, khach_hang_id, nhan_vien_id, ngay_dat_hang, ngay_giao_hang, dia_chi_giao_hang) VALUES
('Tien mat', 1, 1, '2026-01-05', '2026-01-08', 'Ha Noi'),
('Chuyen khoan', 2, 2, '2026-01-10', '2026-01-13', 'Hai Phong'),
('Tien mat', 3, 3, '2026-02-02', '2026-02-05', 'Da Nang'),
('Vi dien tu', 4, 4, '2026-02-14', '2026-02-17', 'Ho Chi Minh'),
('Chuyen khoan', 5, 5, '2026-03-01', '2026-03-04', 'Can Tho'),
('Tien mat', 6, 1, '2026-03-15', '2026-03-18', 'Ha Noi'),
('Vi dien tu', 7, 2, '2026-04-02', '2026-04-05', 'Bac Ninh'),
('Chuyen khoan', 8, 3, '2026-04-20', '2026-04-23', 'Hue'),
('Tien mat', 9, 4, '2026-05-06', '2026-05-09', 'Nghe An'),
('Chuyen khoan', 10, 5, '2026-05-25', '2026-05-28', 'Thanh Hoa'),
('Tien mat', 1, 2, '2026-06-01', '2026-06-04', 'Ha Noi'),
('Vi dien tu', 2, 3, '2026-06-10', '2026-06-13', 'Hai Phong');

INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong) VALUES
(1, 1, 2),
(1, 2, 1),
(2, 3, 5),
(3, 1, 1),
(3, 4, 3),
(4, 5, 2),
(5, 1, 4),
(5, 6, 1),
(6, 2, 2),
(7, 1, 3),
(8, 7, 2),
(9, 1, 1),
(9, 8, 1),
(10, 9, 2),
(11, 1, 6),
(12, 1, 2),
(12, 10, 1);
