DROP SCHEMA IF EXISTS prediction_data CASCADE;

CREATE SCHEMA prediction_data;

CREATE TABLE prediction_data.hamster_data (
                                              id SERIAL PRIMARY KEY,
                                              age_weeks INT,
                                              weight_grams FLOAT,
                                              breed VARCHAR(50),
                                              food_intake FLOAT,
                                              sex VARCHAR(10)
);
