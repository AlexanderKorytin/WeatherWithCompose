# Проект WeatherCompose

##### приложение для отображения текущей погоды по координатам или по названию города по часам и дням с использованием API Weather.com.
-----

#### для связи telegram: @koritin84

-----

#### совместимость: Android 10 (min SDK 29)

-----

#### Версия языка kotlin.android' version '1.8.0''
Gradle JDK version '17.0.9'
#### Зависимости: constraintlayout-compose, retrofit2, coroutines, accompanist-pager, coil-compose, glide:compose, koin, peko
-----
### Технологии:
Git , JSON,  Kotlin,  MVVM,  Android SDK,  Single Activity,  Fragments,  Retrofit2,  Permissions,  Coroutines, Flow,  LiveData , Jetpack Navigation Component , Koin, Glide, mutable state, jetpack compose, peko, view pager. 

-----
### Инструкция по установке

1. Откройте Android studio
2. Нажмите кнопку "Get from VCS"
3. В поле "URL:" вставте ссылку [github](https://github.com/AlexanderKorytin/WeatherWithCompose.git) на этот проект и нажмите "clone"
4. при необходимости скачайте нужную версию Gradle JDK

### Инструкция по эксплуатации

1 Для использования приложения необходимо зарегистрироваться на [сайте](https://www.weatherapi.com/my/) и получить Api key

2 При запуске приложения будет запрошено разрешение на использование геолокации далее откроется экран:

![Screenshot_20240319-210441](https://github.com/AlexanderKorytin/WeatherWithCompose/assets/124441554/28d53eef-2151-42b8-bcac-f5f21ca2f0dd)здесь необходимо ввести полученый в п. 1 ключ

далее
- если разрешение на геолокацию получено будет определено ваше местоположение и показана погода на день, при переключении на вкладку Days - погода на несколько дней (см п. 3)
- если разрешение на геолокацию не получено будет предложено ввести город (см п. 4) и далее показана погода на день, при переключении на вкладку Days - погода на несколько дней (см п. 3)

При нажатии на иконку поиска - будет предложено ввести город (см п. 4).

При нажатии на иконку определения местоположения - будет определен населенный пункт ближайщий к текущему местоположению.

3 Экраны погоды

![Screenshot_20240319-205634](https://github.com/AlexanderKorytin/WeatherWithCompose/assets/124441554/b61276a3-6591-462e-b929-3c68eae05c14) ![Screenshot_20240319-205634](https://github.com/AlexanderKorytin/WeatherWithCompose/assets/124441554/57a37609-234c-4597-8e91-10aed638f88a)

4 Экран поиска

![Screenshot_20240319-210944](https://github.com/AlexanderKorytin/WeatherWithCompose/assets/124441554/1af1bb90-3acd-4418-bf25-e6275677554b)




