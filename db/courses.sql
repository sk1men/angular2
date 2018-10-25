CREATE TABLE Courses (
  id                INT IDENTITY PRIMARY KEY,
  name              VARCHAR(255) NOT NULL,
  description       VARCHAR(255) NOT NULL,
  type              VARCHAR(255) NOT NULL,
  date              VARCHAR(10)  NOT NULL,
  durationInSeconds INT          NOT NULL,
  topRated          BOOLEAN      NOT NULL
);


INSERT INTO Courses (name, description, type, date, durationInSeconds, topRated)
VALUES
  ('Angular 2 Basics', 'Introduction to Angular 2', 'video', '2019-01-01',
   9000, TRUE);
INSERT INTO Courses (name, description, type, date, durationInSeconds, topRated)
VALUES
  ('Angular Materials Basics', 'Introduction to Angular Materials', 'video',
   '2019-04-01', 2700, FALSE);
INSERT INTO Courses (name, description, type, date, durationInSeconds, topRated)
VALUES
  ('TypeScript Basics', 'Introduction to TypeScript', 'video', '2019-03-01',
   5400, TRUE);
INSERT INTO Courses (name, description, type, date, durationInSeconds, topRated)
VALUES
  ('JavaScript Basics', 'Introduction to JavaScript', 'video', '2018-12-01',
   3600, FALSE);
INSERT INTO Courses (name, description, type, date, durationInSeconds, topRated)
VALUES
  ('Unrelated course', 'Outdated description', 'text', '2017-12-01', 1800,
   FALSE);
INSERT INTO Courses (name, description, type, date, durationInSeconds, topRated)
VALUES
  ('RxJS In Practice', 'Observables and Subjects', 'video', '2019-01-01',
   14400, FALSE);
INSERT INTO Courses (name, description, type, date, durationInSeconds, topRated)
VALUES ('Ngrx Architecture', 'Ngrx style application pros and cons', 'video',
        '2019-06-01', 7200, FALSE);
