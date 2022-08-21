CREATE TABLE IF NOT EXISTS BLOCK
(
    block_namespacedkey VARCHAR(50) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS COMMANDREWARD
(
    block_namespacedKey   VARCHAR(50)  NOT NULL,
    commandreward_chance  TINYINT      NOT NULL,
    commandreward_command VARCHAR(200) NOT NULL,
    commandreward_message TEXT         NOT NULL,
    foreign key (block_namespacedKey) references BLOCK (block_namespacedkey) on delete cascade,
    PRIMARY KEY (commandreward_command, block_namespacedKey)
);

CREATE TABLE IF NOT EXISTS MONEYREWARD
(
    block_namespacedkey VARCHAR(50) NOT NULL,
    moneyreward_chance  TINYINT     NOT NULL,
    moneyreward_bal_min INT         NOT NULL,
    moneyreward_bal_max INT         NOT NULL,
    foreign key (block_namespacedkey) references BLOCK (block_namespacedkey) on delete cascade,
    primary key (block_namespacedkey)
);