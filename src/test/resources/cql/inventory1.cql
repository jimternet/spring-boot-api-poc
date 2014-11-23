CREATE TABLE inventory(
    inventory_id text,
    supply int,
    demand int,
    PRIMARY KEY(inventory_id));

INSERT INTO inventory(inventory_id, supply, demand) values('1', 0, 0);

INSERT INTO inventory(inventory_id, supply, demand) values('2', 2, 0);
INSERT INTO inventory(inventory_id, supply, demand) values('3', 5, 0);
