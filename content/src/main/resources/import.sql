-- Mob
insert into "manga" ("id", "name", "genre") values (-1, 'Mob pshyco 100', 'Shounen');

insert into "chapter" ("id", "team_id", "name", "manga_id") values (-1, -1, 'Chapter 1', -1);
insert into "chapter" ("id", "team_id", "name", "manga_id") values (-2, -1, 'Chapter 2', -1);
insert into "chapter" ("id", "team_id", "name", "manga_id") values (-3, -1, 'Chapter 3', -1);

insert into "page" ("id", "number", "chapter_id") values (-1, 1, -1);
insert into "page" ("id", "number", "chapter_id") values (-2, 2, -1);
insert into "page" ("id", "number", "chapter_id") values (-3, 3, -1);
insert into "page" ("id", "number", "chapter_id") values (-4, 1, -2);
insert into "page" ("id", "number", "chapter_id") values (-5, 2, -2);
insert into "page" ("id", "number", "chapter_id") values (-6, 3, -2);
insert into "page" ("id", "number", "chapter_id") values (-7, 1, -3);
insert into "page" ("id", "number", "chapter_id") values (-8, 2, -3);
insert into "page" ("id", "number", "chapter_id") values (-9, 3, -3);

-- Promar
insert into "manga" ("id", "name", "genre") values (-11, 'Promar', 'Shounen');

insert into "chapter" ("id", "team_id", "name", "manga_id") values (-11, -11, 'Chapter 1', -11);

insert into "page" ("id", "number", "chapter_id") values (-11, 1, -11);
insert into "page" ("id", "number", "chapter_id") values (-12, 2, -11);
insert into "page" ("id", "number", "chapter_id") values (-13, 3, -11);
insert into "page" ("id", "number", "chapter_id") values (-14, 1, -11);