# :feet: Проект с автотестами для сайта pet-yes.com 

<img src="images/PetYes.png" width="800" height="600"> 

## :gear: Стек технологий:
| GitHub | IDEA | Java | Junit5 | Gradle | Selenide | Selenoid | Allure Report | Allure TO | Jenkins | Jira |
|:--------:|:-------------:|:---------:|:-------:|:----:|:------:|:----:|:----:|:------:|:------:|:--------:|
| <img src="images/Intelij_IDEA.svg" width="40" height="40"> | <img src="images/JAVA.svg" width="40" height="40"> | <img src="images/Selenide.svg" width="40" height="40"> | <img src="images/Selenoid.svg" width="40" height="40"> | <img src="images/Allure_Report.svg" width="40" height="40"> | <img src="images/Gradle.svg" width="40" height="40"> | <img src="images/Junit5.svg" width="40" height="40"> | <img src="images/GitHub.svg" width="40" height="40"> | <img src="images/Jenkins.svg" width="40" height="40"> | <img src="images/Allure_TO.svg" width="40" height="40"> | <img src="images/Jira.svg" width="40" height="40"> |
___

## :pushpin: В качестве CI системы использован Jenkins 
[Pet-Yes tests](https://jenkins.autotests.cloud/job/08-levikss-PetYes/)

<img src="images/Jenkins.png" width="800" height="600"> 

## :arrow_forward: Запуск тестов

Для запуска тестов необходимо выполнить следующую команду:

```bash
clean
test
-Dbrowser=${BROWSER}
-DbrowserVersion=${BROWSER_VERSION}
-DbrowserSize=${BROWSER_SIZE}
-DbrowserMobileView="${BROWSER_MOBILE}"
-DremoteDriverUrl=https://user1:1234@${REMOTE_DRIVER_URL}/wd/hub/
-DvideoStorage=https://${REMOTE_DRIVER_URL}/video/
-Dthreads=${THREADS}
```
- в параметре Dbrowser - указываем браузер, в котором будут выполняться тесты
- в параметре DbrowserVersion - указываем версию браузера
- в параметре DbrowserSize - указываем размер окна браузера
- в параметре DbrowserMobileView - указываем мобильное устройство, на котором будут выполняться тесты
- в параметре DremoteDriverUrl - указываем логин, пароль и адрес удаленного сервера, где будут проходить тесты 
- в параметре DvideoStorage указываем место для сохранения видео
- в параметре Dthreads задаем количетство потоков

## :bar_chart: Генерация отчета происходит в Allure Report

Для генерации отчета необходимо выполнить следующую команду:

```bash
allure serve build/allure-results
```
<img src="images/Allure_report1.png" width="800" height="600">

<img src="images/Allure_report2.png" width="800" height="600">
___

## :bar_chart: Результаты прохождения тестов записываются в Allure TestOps

<img src="images/Allure_TO.png" width="800" height="600">

## :heavy_check_mark: Уведомления о прохождение тестов отправляются в Telegram

<img src="images/Telegramm_notification.png" width="800" height="600"> 

## :movie_camera: Видеотчет теста "Создание питомца"

<img src="images/CreatePetVideo.gif" width="800" height="600"> 
