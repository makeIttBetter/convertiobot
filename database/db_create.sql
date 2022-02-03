drop
    database IF EXISTS convertio_bot_db;

create
    DATABASE convertio_bot_db CHARACTER SET utf8 COLLATE utf8_bin;

USE
    convertio_bot_db;

create TABLE users
(
    id             BIGINT primary key AUTO_INCREMENT,
    chatId         VARCHAR(20) UNIQUE                                 NOT NULL,
--     uniqueBotId    INT UNIQUE                                         NOT NULL,
    name           VARCHAR(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    userName       VARCHAR(40),
    activityDate   DATETIME                                           NOT NULL,
    registerDate   DATETIME                                           NOT NULL,
    role           ENUM ('ADMIN', 'CLIENT', 'OWNER') DEFAULT 'CLIENT' NOT NULL,
    language       ENUM ('EN', 'RU')                 DEFAULT 'RU'     NOT NULL,
    adminBlock     BOOLEAN                           DEFAULT false    NOT NULL,
    autoBlock      BOOLEAN                           DEFAULT false    NOT NULL,
--     sells          INT                               DEFAULT 0        NOT NULL,
--     buys           INT                               DEFAULT 0        NOT NULL,
    botState       VARCHAR(50)                       DEFAULT 'NONE'   NOT NULL,
    saveHistory    BOOLEAN                           DEFAULT true     NOT NULL,
    requestAmount  INT                               DEFAULT 0        NOT NULL,
--     idRevoked      DATETIME                                           NOT NULL,
    blockDate      DATETIME                                           NOT NULL,
    overloadPeriod DATETIME                          DEFAULT NOW()    NOT NULL

);
alter table users
    ENGINE = MyISAM;


create TABLE messages
(
    id        BIGINT primary key AUTO_INCREMENT,
    user      BIGINT                                                         NOT NULL,
    messageId INT                                                            NOT NULL,
    text      VARCHAR(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    UNIQUE (user, messageId)
);
alter table messages
    ENGINE = MyISAM;



create TABLE requests
(
    id          BIGINT primary key AUTO_INCREMENT,
    user        BIGINT                                                         NOT NULL,
    messageId   INT                                                            NOT NULL,
    messageText VARCHAR(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    input       VARCHAR(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    inputType   ENUM ('TEXT','DATA')                                           NOT NULL,
    botState    VARCHAR(50) DEFAULT 'NONE'                                     NOT NULL # поменять на ENUM !!!!!!!!
);
alter table requests
    ENGINE = MyISAM;



create TABLE advertising
(
    id       SMALLINT primary key AUTO_INCREMENT,
    fileType ENUM ('PHOTO', 'GIF', 'VIDEO', 'AUDIO', 'DOCUMENT'),
    text     VARCHAR(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
    fileId   VARCHAR(300)
);
alter table advertising
    ENGINE = MyISAM;

create TABLE buttons
(
    id          INT primary key AUTO_INCREMENT,
    advertising SMALLINT                                                     NOT NULL,
    text        VARCHAR(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    callback    VARCHAR(45),
    link        VARCHAR(1000)
);
alter table advertising
    ENGINE = MyISAM;


create TABLE referrals
(
    id           BIGINT primary key AUTO_INCREMENT,
    payload      VARCHAR(64) UNIQUE   NOT NULL,
    uniqueClicks INT     DEFAULT 0    NOT NULL,
    allClicks    INT     DEFAULT 0    NOT NULL,
    newUsers     INT     DEFAULT 0    NOT NULL,
    active       BOOLEAN DEFAULT true NOT NULL
);
alter table referrals
    ENGINE = MyISAM;


-- for statistics
create TABLE referralUsers
(
    id       BIGINT primary key AUTO_INCREMENT,
    user     BIGINT NOT NULL,
    referral BIGINT NOT NULL,
    UNIQUE (user, referral)
);
alter table referralUsers
    ENGINE = MyISAM;


create TABLE channels
(
    id           BIGINT primary key AUTO_INCREMENT,
    chatId       VARCHAR(20) UNIQUE                                            NOT NULL,
    channelTitle VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    channelLink  VARCHAR(100)                                                  NOT NULL,
    channelType  ENUM ('PRIVATE', 'PUBLIC')                                    NOT NULL
);
alter table channels
    ENGINE = MyISAM;



create TABLE replenishments
(
    id               BIGINT             NOT NULL,
    receive_curr     SMALLINT           NOT NULL,
    replenish_amount DOUBLE DEFAULT 0.0 NOT NULL,
    receive_amount   DOUBLE DEFAULT 0.0 NOT NULL,
    fee              DOUBLE DEFAULT 0.0 NOT NULL,
    date             DATETIME           NOT NULL,
    paywayMethod     SMALLINT           NOT NULL,
    status           SMALLINT           NOT NULL
);
alter table replenishments
    ENGINE = MyISAM;


create TABLE wallets
(
    id            BIGINT primary key AUTO_INCREMENT,
    owner         BIGINT             NOT NULL,
    currency      SMALLINT           NOT NULL,
    balance       DOUBLE DEFAULT 0.0 NOT NULL,
    cryptoAddress VARCHAR(50),
    confirms      INT    DEFAULT 0   NOT NULL,
    UNIQUE (owner, currency)
);
alter table wallets
    ENGINE = MyISAM;



create TABLE currencies
(
    id       SMALLINT primary key AUTO_INCREMENT,
    currency VARCHAR(7) UNIQUE NOT NULL
);
alter table currencies
    ENGINE = MyISAM;


create TABLE payways
(
    id           SMALLINT primary key AUTO_INCREMENT,
    paywayMethod VARCHAR(20) UNIQUE NOT NULL
);
alter table payways
    ENGINE = MyISAM;

create TABLE operation_statuses
(
    id     SMALLINT primary key AUTO_INCREMENT,
    status VARCHAR(20) UNIQUE NOT NULL
);
alter table operation_statuses
    ENGINE = MyISAM;


create TABLE operations
(
    id          BIGINT primary key AUTO_INCREMENT,
    user        BIGINT             NOT NULL,
    external_id VARCHAR(40) UNIQUE NOT NULL
);
alter table operations
    ENGINE = MyISAM;


create TABLE file_categories
(
    id   SMALLINT primary key AUTO_INCREMENT,
    name VARCHAR(200) UNIQUE NOT NULL
);
alter table file_categories
    ENGINE = MyISAM;


create TABLE file_types
(
    id          SMALLINT primary key AUTO_INCREMENT,
    code        VARCHAR(50) UNIQUE    NOT NULL,
    category    SMALLINT              NOT NULL,
    readable    BOOLEAN default true  NOT NULL,
    writeable   BOOLEAN default false NOT NULL,
    description VARCHAR(200)          NOT NULL
);
alter table file_types
    ENGINE = MyISAM;

create TABLE file2file
(
    id             INT primary key AUTO_INCREMENT,
    type           SMALLINT NOT NULL,
    writeable_type SMALLINT NOT NULL,
    UNIQUE (type, writeable_type)
);
alter table file2file
    ENGINE = MyISAM;

create TABLE files
(
    id     BIGINT primary key AUTO_INCREMENT,
    fileId VARCHAR(200) UNIQUE NOT NULL,
    link   VARCHAR(500)
);
alter table files
    ENGINE = MyISAM;


# users
alter table messages
    add CONSTRAINT FOREIGN KEY (user)
        REFERENCES users (id) ON delete CASCADE ON update RESTRICT;
# referral users
alter table referralUsers
    add CONSTRAINT FOREIGN KEY (user)
        REFERENCES users (id) ON delete CASCADE ON update RESTRICT;
alter table referralUsers
    add CONSTRAINT FOREIGN KEY (referral)
        REFERENCES referrals (id) ON delete CASCADE ON update RESTRICT;
#wallets
alter table wallets
    add CONSTRAINT FOREIGN KEY (owner)
        REFERENCES users (id) ON delete CASCADE ON update RESTRICT;
alter table wallets
    add CONSTRAINT FOREIGN KEY (currency)
        REFERENCES currencies (id) ON delete CASCADE ON update RESTRICT;
# operation
alter table operations
    add CONSTRAINT FOREIGN KEY (user)
        REFERENCES users (id) ON delete CASCADE ON update RESTRICT;
# replenishments
alter table replenishments
    add CONSTRAINT FOREIGN KEY (id)
        REFERENCES operations (id) ON delete CASCADE ON update RESTRICT;
alter table replenishments
    add CONSTRAINT FOREIGN KEY (receive_curr)
        REFERENCES currencies (id) ON delete CASCADE ON update RESTRICT;
alter table replenishments
    add CONSTRAINT FOREIGN KEY (paywayMethod)
        REFERENCES payways (id) ON delete CASCADE ON update RESTRICT;
# file2file
alter table file2file
    add CONSTRAINT FOREIGN KEY (type)
        REFERENCES file_types (id) ON delete CASCADE ON update RESTRICT;
alter table file2file
    add CONSTRAINT FOREIGN KEY (writeable_type)
        REFERENCES file_types (id) ON delete CASCADE ON update RESTRICT;
