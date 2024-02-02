-- 创建用户表
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) NOT NULL,
    Password VARCHAR(255) NOT NULL, -- 建议使用较长的长度以存储哈希后的密码
    Account VARCHAR(100) NOT NULL,
    RegistrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- 其他个人信息字段可以根据需求添加
    UNIQUE(Username),
    UNIQUE(Account)
);

-- 创建商品表
CREATE TABLE Products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(100) NOT NULL,
    Description TEXT,
    SellerID INT NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Status ENUM('待售', '已售', '下架') DEFAULT '待售',
    PublicationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ImageURL VARCHAR(255),
    FOREIGN KEY (SellerID) REFERENCES Users(UserID)
);

-- 创建交易记录表
CREATE TABLE Transactions (
    TransactionID INT AUTO_INCREMENT PRIMARY KEY,
    ProductID INT NOT NULL,
    BuyerID INT NOT NULL,
    SellerID INT NOT NULL,
    TransactionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    TransactionStatus ENUM('待付款', '已付款', '已发货', '已完成') DEFAULT '待付款',
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID),
    FOREIGN KEY (BuyerID) REFERENCES Users(UserID),
    FOREIGN KEY (SellerID) REFERENCES Users(UserID)
);