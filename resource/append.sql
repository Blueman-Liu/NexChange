-- 关注关系表
CREATE TABLE follows (
    followerID INT NOT NULL,
    followingID INT NOT NULL,
    followDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (followerID, followingID),
    FOREIGN KEY (followerID) REFERENCES users(userID),
    FOREIGN KEY (followingID) REFERENCES users(userID)
);

-- 收藏商品表
CREATE TABLE collections (
    userID INT NOT NULL,
    productID INT NOT NULL,
    collectDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (userID, productID),
    FOREIGN KEY (userID) REFERENCES users(userID),
    FOREIGN KEY (productID) REFERENCES products(productID)
);

-- 浏览历史表
CREATE TABLE histories (
    userID INT NOT NULL,
    productID INT NOT NULL,
    viewDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (userID, productID),
    FOREIGN KEY (userID) REFERENCES users(userID),
    FOREIGN KEY (productID) REFERENCES products(productID)
);