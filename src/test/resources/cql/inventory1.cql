CREATE TABLE inventory(
    id text,
    supply int,
    demand int,
    PRIMARY KEY(id));

INSERT INTO inventory(id, supply, demand) values('Princess Magazine', 0, 0);

INSERT INTO inventory(id, supply, demand) values('red matchbox car', 2, 0);
INSERT INTO inventory(id, supply, demand) values('x', 5, 0);
