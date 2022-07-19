require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start || sessionResult = "Сценарий начинается отсюда", sessionResultColor = "#143AD1"
        q!: $regex</start>
        image: https://248305.selcdn.ru/zfl_prod/64069/64072/Y6nDSc64tgJWac7N.png
        a: Добрый день! Я - виртуальный секретарь Компании. Могу помочь уточнить статус Вашего заказа или рассказать о времени работы офиса. || htmlEnabled = true, html = "Добрый день! Я - виртуальный секретарь <b>Компании</b>. Могу помочь уточнить статус Вашего заказа или рассказать о времени работы офиса."
        buttons:
            "Статус заказа" -> /Статус заказа
            "Часы работы" -> /Часы работы
            "Оставить отзыв" -> /Отзыв о работе
        intent: /Статус заказа || onlyThisState = false, toState = "/Статус заказа"
        intent: /Часы работы || onlyThisState = false, toState = "/Часы работы"
        intent: /Оставить отзыв || onlyThisState = false, toState = "/Отзыв о работе"
        intent: /пока || onlyThisState = false, toState = "/Bye"

    state: Bye
        a: Пока пока

    state: NoMatch || sessionResult = "Тут обрабатываем непонятные запросы", sessionResultColor = "#3E8080"
        event!: noMatch
        a: Простите, я вас не поняла. Не могли бы вы уточнить вопрос? || html = "Простите, я вас не поняла. Не могли бы вы уточнить вопрос?"
        go!: /Меню

    state: Статус заказа || sessionResult = "Статус заказа", sessionResultColor = "#7E47D1"
        a: Скажите, пожалуйста, номер вашего заказа || htmlEnabled = false, html = "Скажите, пожалуйста, номер вашего заказа"
        intent: /Номер заказа || onlyThisState = false, toState = "/Номер сохранен"
        event: noMatch || onlyThisState = false, toState = "/Неправильный номер"

    state: Часы работы || sessionResult = "Отвечаем про часы работы", sessionResultColor = "#15952F"
        a: Мы работает с 10 утра до 8 вечера по будням и с 11 до 17 в субботу. Воскресенье - выходной. || htmlEnabled = false, html = "Мы работает с 10 утра до 8 вечера по будням и с 11 до 17 в субботу. Воскресенье - выходной."
        go!: /Меню

    state: Отзыв о работе || sessionResult = "Пример работы с системными интентами", sessionResultColor = "#FFFFFF"
        a: Мы будем благодарны услышать ваш отзыв о работе. || htmlEnabled = false, html = "Мы будем благодарны услышать ваш отзыв о работе."
        intent: /sys/aimylogic/ru/approval || onlyThisState = false, toState = "/Отзыв о работе/Спасибо за оценку"
        intent: /sys/aimylogic/ru/insults || onlyThisState = false, toState = "/Отзыв о работе/Не хами"
        intent: /sys/aimylogic/ru/negative || onlyThisState = false, toState = "/Отзыв о работе/Извиниться"
        intent: /sys/aimylogic/ru/normal || onlyThisState = false, toState = "/Отзыв о работе/Будем стараться"
        event: noMatch || onlyThisState = false, toState = "/Отзыв о работе/Сохранить отзыв"

        state: Не хами
            a: Пожалуйста, сдерживайте ваши эмоции || htmlEnabled = false, html = "Пожалуйста, сдерживайте ваши эмоции"
            go!: /Меню

        state: Будем стараться
            a: Спасибо. В следующий раз мы постараемся быть лучше. || htmlEnabled = false, html = "Спасибо. В следующий раз мы постараемся быть лучше."
            go!: /Меню

        state: Извиниться
            a: Я сожалею, что мы доставили вам неудобства. От имени компании приношу вам свои извинения. 
                
                Обязательно передам вашу жалобу руководству  || htmlEnabled = false, html = "Я сожалею, что мы доставили вам неудобства. От имени компании приношу вам свои извинения. <br><br>Обязательно передам вашу жалобу руководству "
            go!: /Меню

        state: Спасибо за оценку
            a: Спасибо за высокую оценку! Мы рады стараться для вас! || htmlEnabled = false, html = "Спасибо за высокую оценку! Мы рады стараться для вас!"
            go!: /Меню

        state: Сохранить отзыв || sessionResult = "Тут мы поместили работу с отзывом в подсценарий", sessionResultColor = "#143AD1"
            a: Ок, я поняла. Ваш отзыв:
                
                
                {{$request.query}}
                
                
                Обязательно передам куда надо! || htmlEnabled = true, html = "Ок, я поняла. Ваш отзыв: <br><br><br>{{$request.query}}  <br><br><br>Обязательно передам куда надо!"
            go!: /Меню

    state: Меню
        a: Как еще я могу вам помочь? || htmlEnabled = false, html = "Как еще я могу вам помочь?"
        buttons:
            "Статус заказа" -> /Статус заказа
            "Часы работы" -> /Часы работы
            "Оставить отзыв" -> /Отзыв о работе
        intent: /Статус заказа || onlyThisState = false, toState = "/Статус заказа"
        intent: /Оставить отзыв || onlyThisState = false, toState = "/Отзыв о работе"
        intent: /Часы работы || onlyThisState = false, toState = "/Часы работы"
        intent: /sys/aimylogic/ru/parting || onlyThisState = false, toState = "/Bye"
        event: noMatch || onlyThisState = false, toState = "./"

    state: Номер сохранен || sessionResult = "Статус заказа", sessionResultColor = "#7E47D1"
        a: Ок, записала {{$request.query}}. Секундочку, утоняю || htmlEnabled = false, html = "Ок, записала {{$request.query}}. Секундочку, утоняю"
        HttpRequest: 
            url = https://example.com?query=${request.query}
            method = GET
            dataType = 
            body = 
            okState = /Получили данные
            errorState = /Ошибка получения
            timeout = 0
            headers = [{"name":"","value":""}]
            vars = [{"name":"quoteText","value":"$httpResponse.quoteText"}]

    state: Ошибка получения || sessionResult = "Сервер недоступен", sessionResultColor = "#CD4C2B"
        a: К сожалению, я никак не могу связать с сервером. Попробуйте позже, пожалуйста. || htmlEnabled = false, html = "К сожалению, я никак не могу связать с сервером. Попробуйте позже, пожалуйста."
        go!: /Меню

    state: Неправильный номер || sessionResult = "Статус заказа", sessionResultColor = "#7E47D1"
        a: Кажется, это не номер заказа. || htmlEnabled = false, html = "Кажется, это не номер заказа."
        go!: /Статус заказа

    state: Получили данные || sessionResult = "Здесь надо будет научить бота разбирать ответ сервера", sessionResultColor = "#CD4C2B"
        a: Ой, кажется я не поняла что мне ответили. Научите меня разбираться с этим, пожалуйста :) || htmlEnabled = false, html = "Ой, кажется я не поняла что мне ответили. Научите меня разбираться с этим, пожалуйста :)"
        go!: /Меню