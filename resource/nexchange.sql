-- 创建用户表
CREATE TABLE Users (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL, -- 建议使用较长的长度以存储哈希后的密码
    account VARCHAR(100) NOT NULL,
    regDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- 其他个人信息字段可以根据需求添加
    UNIQUE(username),
    UNIQUE(account)
);

-- 创建商品表
CREATE TABLE Products (
    productID INT AUTO_INCREMENT PRIMARY KEY,
    productName VARCHAR(100) NOT NULL,
    description TEXT,
    sellerID INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status ENUM('待售', '已售', '下架') DEFAULT '待售',
    publicationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    imageURL VARCHAR(255),
    FOREIGN KEY (sellerID) REFERENCES Users(userID)
);

-- 创建交易记录表
CREATE TABLE Transactions (
    transactionID INT AUTO_INCREMENT PRIMARY KEY,
    productID INT NOT NULL,
    buyerID INT NOT NULL,
    sellerID INT NOT NULL,
    transactionDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transactionStatus ENUM('待付款', '已付款', '已发货', '已完成') DEFAULT '待付款',
    FOREIGN KEY (productID) REFERENCES Products(ProductID),
    FOREIGN KEY (buyerID) REFERENCES Users(userID),
    FOREIGN KEY (sellerID) REFERENCES Users(userID)
);