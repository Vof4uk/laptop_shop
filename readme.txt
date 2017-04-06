1) Может не работать загрузка картинок если деплоить WAR, поможет
exploded из идеи.
2) Асинхронные запросы не делал.
3) Скрипты бд находятся в модуле Repository в ресурсах
4)Настройки jdbc там же. по умолчанию:
jdbc.driver.className=org.postgresql.Driver
jdbc.url=jdbc:postgresql://localhost:5432/laptop_shop
jdbc.username=user
jdbc.password=password

5) Аккааунты без регистрации
    user - user1
    admin - admin1