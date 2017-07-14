# Yield Triangle Generator

The goal of this project is to allow the generation of yield triangles for arbitrary stocks and indexes. For this purpose, the Yahoo Finance API is used to obtain the history for a stock symbol.
A yield triangle is generated out of the data to give an overview about the possible yields in the history.

Run it, using `./gradlew run -PappArgs="[SYMBOL, FROM_YEAR, TO_YEAR]"`, i.e. `./gradlew run -PappArgs="['AAPL', '2000' ,'2016']"`
