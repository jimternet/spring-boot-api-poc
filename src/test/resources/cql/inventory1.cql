CREATE TABLE inventory(
    inventory_id text,
    supply int,
    demand int,
    PRIMARY KEY(inventory_id));

INSERT INTO inventory(inventory_id, supply, demand) values('asdf', 0, 0);

INSERT INTO inventory(inventory_id, supply, demand) values('uiop', 2, 0);
INSERT INTO inventory(inventory_id, supply, demand) values('x', 5, 0);
