CREATE TABLE account (
    accountId serial primary key,
    accountUserName varchar(100) not null,
    accountPassword varchar(100) not null
);

CREATE TABLE task (
    taskId serial primary key,
    taskOrder int not null,
    taskText varchar(100) not null,
    userId  int not null REFERENCES account(accountId)
);
