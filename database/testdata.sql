USE convertio_bot_db;

#  currencies
insert into currencies
values (default, 'RUB');
insert into currencies
values (default, 'UAH');
insert into currencies
values (default, 'USDT');


insert into operation_statuses
values (default, 'IN_PROCESS');
insert into operation_statuses
values (default, 'DONE');
insert into operation_statuses
values (default, 'FAILED');
